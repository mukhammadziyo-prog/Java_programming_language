import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * JAVA SANDBOX CRAFT - ULTIMATE EDITION
 *
 * Kompilyatsiya:  javac MinecraftGame.java
 * Ishga tushirish: java MinecraftGame
 *
 * Boshqaruv:
 *   A/D yoki strelkalar - yurish, W/Space - sakrash/suzish/uchish, S - pastga (uchganda)
 *   Shift - yugurish, 1-9 yoki sichqoncha g'ildiragi - hotbar
 *   Sichqoncha chap tugma (bosib turing) - qazish / hujum, o'ng tugma - qo'yish / yeyish / TNT yoqish
 *   E - inventar va craft, Q - tashlash, G - creative rejim, F - uchish (creative)
 *   T - vaqtni o'tkazish (creative), M - minikarta, F3 - debug, F5 - saqlash, F9 - yuklash, ESC - pauza
 */
public class MinecraftGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Java Sandbox Craft - Ultimate Edition");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            GamePanel panel = new GamePanel();
            f.setContentPane(panel);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
            panel.requestFocusInWindow();
            panel.start();
        });
    }
}

// =====================================================================
// KONSTANTALAR
// =====================================================================
final class Cfg {
    static final int TILE = 32;
    static final int W = 480;
    static final int H = 128;
    static final int SEA = 48;
    static final int BASE_H = 42;
    static final int DAY_TICKS = 14400;
    static final double GRAVITY = 0.5;
    static final double MAX_FALL = 17.0;
    static final double REACH = 5.5;
    static final String SAVE_FILE = "sandbox_world.sav";
    private Cfg() {}
}

final class B {
    static final int AIR = 0, GRASS = 1, DIRT = 2, STONE = 3, SAND = 4, WOOD = 5, LEAVES = 6, WATER = 7,
            COAL_ORE = 8, IRON_ORE = 9, GOLD_ORE = 10, DIAMOND_ORE = 11, BEDROCK = 12, PLANKS = 13,
            GLASS = 14, TORCH = 15, BRICK = 16, SNOW = 17, LAVA = 18, FLOWER_R = 19, FLOWER_Y = 20,
            COBBLE = 21, TNT = 22, COUNT = 23;
    private B() {}
}

final class BlockDef {
    final int id, color, emit, tool, minTier;
    final String name;
    final double hardness;
    final boolean solid, opaque;
    int drop;
    boolean plant, liquid;

    BlockDef(int id, String name, int color, double hardness, boolean solid, boolean opaque, int emit, int tool, int minTier) {
        this.id = id; this.name = name; this.color = color; this.hardness = hardness;
        this.solid = solid; this.opaque = opaque; this.emit = emit; this.tool = tool; this.minTier = minTier;
        this.drop = id;
    }
}

final class Blocks {
    static final BlockDef[] DEFS = new BlockDef[B.COUNT];
    static final boolean[] OPAQUE = new boolean[B.COUNT];

    static {
        def(B.AIR, "Havo", 0x000000, 0, false, false, 0, 0, -1).drop = -1;
        def(B.GRASS, "Maysa", 0x5DA130, 0.6, true, true, 0, Items.T_SHOVEL, -1).drop = B.DIRT;
        def(B.DIRT, "Tuproq", 0x8B5A2B, 0.5, true, true, 0, Items.T_SHOVEL, -1);
        def(B.STONE, "Tosh", 0x7D7D7D, 1.6, true, true, 0, Items.T_PICK, 0).drop = B.COBBLE;
        def(B.SAND, "Qum", 0xDBD3A0, 0.5, true, true, 0, Items.T_SHOVEL, -1);
        def(B.WOOD, "Yog'och", 0x6B4F2A, 2.0, true, true, 0, Items.T_AXE, -1);
        def(B.LEAVES, "Barg", 0x2F7A2A, 0.25, true, false, 0, 0, -1).drop = -1;
        BlockDef w = def(B.WATER, "Suv", 0x3060D0, -1, false, false, 0, 0, -1);
        w.drop = -1; w.liquid = true;
        def(B.COAL_ORE, "Ko'mir rudasi", 0x2B2B2B, 3.0, true, true, 0, Items.T_PICK, 0).drop = Items.COAL;
        def(B.IRON_ORE, "Temir rudasi", 0xD8AF93, 3.2, true, true, 0, Items.T_PICK, 1);
        def(B.GOLD_ORE, "Oltin rudasi", 0xF5D93B, 3.2, true, true, 0, Items.T_PICK, 2);
        def(B.DIAMOND_ORE, "Olmos rudasi", 0x55E8F0, 4.0, true, true, 0, Items.T_PICK, 2).drop = Items.DIAMOND;
        def(B.BEDROCK, "Bedrok", 0x2A2A2A, -1, true, true, 0, 0, -1).drop = -1;
        def(B.PLANKS, "Taxta", 0xB8945F, 1.5, true, true, 0, Items.T_AXE, -1);
        def(B.GLASS, "Shisha", 0xBFE6F0, 0.35, true, false, 0, 0, -1).drop = -1;
        def(B.TORCH, "Mash'al", 0xFFC040, 0.05, false, false, 14, 0, -1);
        def(B.BRICK, "G'isht", 0xA0452F, 2.2, true, true, 0, Items.T_PICK, 0);
        def(B.SNOW, "Qor", 0xF4FAFF, 0.4, true, true, 0, Items.T_SHOVEL, -1);
        BlockDef lv = def(B.LAVA, "Lava", 0xE25A12, -1, false, false, 13, 0, -1);
        lv.drop = -1; lv.liquid = true;
        def(B.FLOWER_R, "Qizil gul", 0xD02020, 0.05, false, false, 0, 0, -1).plant = true;
        def(B.FLOWER_Y, "Sariq gul", 0xE8D020, 0.05, false, false, 0, 0, -1).plant = true;
        def(B.COBBLE, "Bulut tosh", 0x6A6A6A, 1.8, true, true, 0, Items.T_PICK, 0);
        def(B.TNT, "TNT", 0xC9302C, 0.05, true, true, 0, 0, -1);
    }

    private static BlockDef def(int id, String name, int color, double hardness, boolean solid, boolean opaque, int emit, int tool, int minTier) {
        BlockDef d = new BlockDef(id, name, color, hardness, solid, opaque, emit, tool, minTier);
        DEFS[id] = d;
        OPAQUE[id] = opaque;
        return d;
    }

    static BlockDef get(int id) { return (id >= 0 && id < DEFS.length) ? DEFS[id] : DEFS[0]; }
    private Blocks() {}
}

// =====================================================================
// BUYUMLAR, INVENTAR, RETSEPTLAR
// =====================================================================
final class Items {
    static final int STICK = 100, COAL = 101, IRON_INGOT = 102, GOLD_INGOT = 103, DIAMOND = 104,
            PORK = 105, COOKED_PORK = 106, BEEF = 107, COOKED_BEEF = 108, APPLE = 109, ROTTEN = 110;
    static final int T_NONE = 0, T_PICK = 1, T_AXE = 2, T_SHOVEL = 3, T_SWORD = 4;
    static final String[] TIER_NAMES = {"Yog'och", "Tosh", "Temir", "Olmos"};
    static final String[] TYPE_NAMES = {"", "Kirka", "Boltа", "Belkurak", "Qilich"};
    static final double[] SPEED = {2.0, 3.5, 5.5, 8.0};
    static final int[] DUR = {60, 130, 250, 1000};
    static final int[] TIER_COLOR = {0xA0703C, 0x8A8A8A, 0xE6E6E6, 0x4FE3E3};

    static int tool(int type, int tier) { return 120 + (type - 1) * 10 + tier; }
    static boolean isTool(int id) { return id >= 120 && id < 160 && (id - 120) % 10 < 4; }
    static int toolType(int id) { return isTool(id) ? (id - 120) / 10 + 1 : 0; }
    static int toolTier(int id) { return isTool(id) ? (id - 120) % 10 : -1; }
    static boolean isBlock(int id) { return id > 0 && id < B.COUNT; }
    static int maxStack(int id) { return isTool(id) ? 1 : 64; }
    static int maxDur(int id) { return isTool(id) ? DUR[toolTier(id)] : 0; }

    static int food(int id) {
        switch (id) {
            case PORK: return 3;
            case COOKED_PORK: return 8;
            case BEEF: return 3;
            case COOKED_BEEF: return 8;
            case APPLE: return 4;
            case ROTTEN: return 2;
            default: return 0;
        }
    }

    static double attackDamage(int id) {
        if (!isTool(id)) return 1;
        int t = toolTier(id);
        switch (toolType(id)) {
            case T_SWORD: return 4 + t * 1.5;
            case T_AXE: return 3 + t;
            case T_PICK: return 2 + t;
            default: return 1.5 + t * 0.5;
        }
    }

    static String name(int id) {
        if (isBlock(id)) return Blocks.get(id).name;
        if (isTool(id)) return TIER_NAMES[toolTier(id)] + " " + TYPE_NAMES[toolType(id)];
        switch (id) {
            case STICK: return "Tayoq";
            case COAL: return "Ko'mir";
            case IRON_INGOT: return "Temir quymasi";
            case GOLD_INGOT: return "Oltin quymasi";
            case DIAMOND: return "Olmos";
            case PORK: return "Xom cho'chqa go'shti";
            case COOKED_PORK: return "Pishgan cho'chqa go'shti";
            case BEEF: return "Xom mol go'shti";
            case COOKED_BEEF: return "Pishgan mol go'shti";
            case APPLE: return "Olma";
            case ROTTEN: return "Chirigan go'sht";
            default: return "Noma'lum";
        }
    }
    private Items() {}
}

final class ItemStack {
    int id, count, dur;
    ItemStack(int id, int count) { this.id = id; this.count = count; this.dur = Items.maxDur(id); }
    ItemStack copy() { ItemStack s = new ItemStack(id, count); s.dur = dur; return s; }
}

final class Inventory {
    final ItemStack[] slots = new ItemStack[36];

    int add(int id, int count, int dur) {
        int max = Items.maxStack(id);
        if (max > 1) {
            for (ItemStack s : slots) {
                if (count > 0 && s != null && s.id == id && s.count < max) {
                    int m = Math.min(max - s.count, count);
                    s.count += m; count -= m;
                }
            }
        }
        for (int i = 0; i < slots.length && count > 0; i++) {
            if (slots[i] == null) {
                int m = Math.min(max, count);
                ItemStack ns = new ItemStack(id, m);
                if (dur > 0) ns.dur = dur;
                slots[i] = ns;
                count -= m;
            }
        }
        return count;
    }

    boolean canFit(ItemStack st) {
        int max = Items.maxStack(st.id), room = 0;
        for (ItemStack s : slots) {
            if (s == null) room += max;
            else if (s.id == st.id && max > 1) room += max - s.count;
        }
        return room >= st.count;
    }

    int count(int id) {
        int c = 0;
        for (ItemStack s : slots) if (s != null && s.id == id) c += s.count;
        return c;
    }

    boolean has(int id, int n) { return count(id) >= n; }

    void remove(int id, int n) {
        for (int i = 0; i < slots.length && n > 0; i++) {
            ItemStack s = slots[i];
            if (s != null && s.id == id) {
                int m = Math.min(s.count, n);
                s.count -= m; n -= m;
                if (s.count <= 0) slots[i] = null;
            }
        }
    }
}

final class Recipe {
    final int[] in, inN;
    final int out, outN;
    Recipe(int out, int outN, int... pairs) {
        this.out = out; this.outN = outN;
        in = new int[pairs.length / 2];
        inN = new int[pairs.length / 2];
        for (int i = 0; i < in.length; i++) { in[i] = pairs[i * 2]; inN[i] = pairs[i * 2 + 1]; }
    }
}

final class Recipes {
    static final List<Recipe> ALL = new ArrayList<>();

    static {
        add(B.PLANKS, 4, B.WOOD, 1);
        add(Items.STICK, 4, B.PLANKS, 2);
        add(B.TORCH, 4, Items.COAL, 1, Items.STICK, 1);
        int[] mats = {B.PLANKS, B.COBBLE, Items.IRON_INGOT, Items.DIAMOND};
        for (int t = 0; t < 4; t++) {
            add(Items.tool(Items.T_PICK, t), 1, mats[t], 3, Items.STICK, 2);
            add(Items.tool(Items.T_AXE, t), 1, mats[t], 3, Items.STICK, 2);
            add(Items.tool(Items.T_SHOVEL, t), 1, mats[t], 1, Items.STICK, 2);
            add(Items.tool(Items.T_SWORD, t), 1, mats[t], 2, Items.STICK, 1);
        }
        add(Items.IRON_INGOT, 1, B.IRON_ORE, 1, Items.COAL, 1);
        add(Items.GOLD_INGOT, 1, B.GOLD_ORE, 1, Items.COAL, 1);
        add(B.STONE, 1, B.COBBLE, 1, Items.COAL, 1);
        add(B.GLASS, 2, B.SAND, 2, Items.COAL, 1);
        add(B.BRICK, 4, B.COBBLE, 4, Items.COAL, 1);
        add(B.TNT, 1, Items.COAL, 2, B.SAND, 2);
        add(Items.COOKED_PORK, 1, Items.PORK, 1, Items.COAL, 1);
        add(Items.COOKED_BEEF, 1, Items.BEEF, 1, Items.COAL, 1);
    }

    private static void add(int out, int outN, int... pairs) { ALL.add(new Recipe(out, outN, pairs)); }
    private Recipes() {}
}

// =====================================================================
// SHOVQIN (NOISE) VA DUNYO
// =====================================================================
final class Noise {
    private final long seed;
    Noise(long seed) { this.seed = seed; }

    private double rnd(int x, int y) {
        long h = seed + x * 0x9E3779B97F4A7C15L + y * 0xC2B2AE3D27D4EB4FL;
        h ^= h >>> 32; h *= 0xD6E8FEB86659FD93L; h ^= h >>> 32;
        return (h & 0xFFFFFF) / (double) 0x1000000;
    }

    private static double lerp(double a, double b, double t) { return a + (b - a) * t; }
    private static double smooth(double t) { return t * t * (3 - 2 * t); }

    double value(double x, double y) {
        int xi = (int) Math.floor(x), yi = (int) Math.floor(y);
        double u = smooth(x - xi), v = smooth(y - yi);
        double a = rnd(xi, yi), b = rnd(xi + 1, yi), c = rnd(xi, yi + 1), d = rnd(xi + 1, yi + 1);
        return lerp(lerp(a, b, u), lerp(c, d, u), v);
    }

    double fbm(double x, double y, int oct) {
        double sum = 0, amp = 0.5, freq = 1, tot = 0;
        for (int i = 0; i < oct; i++) {
            sum += value(x * freq, y * freq) * amp;
            tot += amp; amp *= 0.5; freq *= 2;
        }
        return sum / tot;
    }
}

final class World {
    final int[][] blocks = new int[Cfg.H][Cfg.W];
    final byte[][] sky = new byte[Cfg.H][Cfg.W];
    final byte[][] blk = new byte[Cfg.H][Cfg.W];
    final int[] surface = new int[Cfg.W];
    final long seed;
    final Noise noise;
    long ticks;
    double skyFactor = 1.0;
    boolean lightDirty = true;
    int spawnX = Cfg.W / 2, spawnY = 40;

    World(long seed, boolean generate) {
        this.seed = seed;
        this.noise = new Noise(seed);
        if (generate) generate();
        else computeSurface();
    }

    int biome(int x) {
        double b = noise.fbm(x * 0.007, 55.5, 2);
        return b < 0.42 ? 1 : (b > 0.58 ? 2 : 0);
    }

    String biomeName(int x) {
        int b = biome(Math.max(0, Math.min(Cfg.W - 1, x)));
        return b == 1 ? "Sahro" : (b == 2 ? "Qorli o'lka" : "Tekislik");
    }

    private void generate() {
        Random r = new Random(seed);
        int[] hgt = new int[Cfg.W];
        for (int x = 0; x < Cfg.W; x++) {
            double n = noise.fbm(x * 0.010, 3.0, 4), hl = noise.fbm(x * 0.05, 9.0, 2);
            int h = (int) (Cfg.BASE_H + (n - 0.5) * 60 + (hl - 0.5) * 8);
            hgt[x] = Math.max(14, Math.min(Cfg.H - 50, h));
        }
        // Yer qatlamlari
        for (int x = 0; x < Cfg.W; x++) {
            int h = hgt[x], bi = biome(x);
            boolean beach = h >= Cfg.SEA - 1;
            boolean sandy = beach || bi == 1;
            for (int y = 0; y < Cfg.H; y++) {
                int id;
                if (y < h) id = B.AIR;
                else if (y == h) id = sandy ? B.SAND : (bi == 2 ? B.SNOW : B.GRASS);
                else if (y <= h + 4) id = sandy ? B.SAND : B.DIRT;
                else id = B.STONE;
                if (y == Cfg.H - 1) id = B.BEDROCK;
                else if (y >= Cfg.H - 4 && r.nextInt(3) == 0) id = B.BEDROCK;
                blocks[y][x] = id;
            }
            for (int y = Cfg.SEA; y < h; y++) blocks[y][x] = B.WATER;
        }
        // G'orlar va rudalar
        for (int x = 0; x < Cfg.W; x++) {
            for (int y = hgt[x] + 6; y < Cfg.H - 2; y++) {
                if (blocks[y][x] != B.STONE) continue;
                double c1 = noise.value(x * 0.06, y * 0.06 + 500);
                double c2 = noise.value(x * 0.035 + 100, y * 0.035 + 900);
                if (Math.abs(c1 - 0.5) < 0.03 || c2 > 0.82) {
                    blocks[y][x] = (y > Cfg.H - 22) ? B.LAVA : B.AIR;
                    continue;
                }
                int depth = y - hgt[x];
                double p = r.nextDouble();
                int ore = 0;
                if (y > Cfg.H - 32 && p < 0.007) ore = B.DIAMOND_ORE;
                else if (depth > 22 && p < 0.018) ore = B.GOLD_ORE;
                else if (depth > 8 && p < 0.040) ore = B.IRON_ORE;
                else if (p < 0.075) ore = B.COAL_ORE;
                if (ore != 0) {
                    blocks[y][x] = ore;
                    if (r.nextBoolean() && x + 1 < Cfg.W && blocks[y][x + 1] == B.STONE) blocks[y][x + 1] = ore;
                }
            }
        }
        // Daraxtlar va gullar
        int lastTree = -10;
        for (int x = 3; x < Cfg.W - 3; x++) {
            int h = hgt[x], top = blocks[h][x];
            if (h >= Cfg.SEA - 1 || (top != B.GRASS && top != B.SNOW)) continue;
            double density = 0.05 + noise.fbm(x * 0.03, 50, 2) * 0.22;
            if (x - lastTree >= 4 && r.nextDouble() < density) {
                growTree(x, h, r);
                lastTree = x;
            } else if (top == B.GRASS && r.nextDouble() < 0.09 && blocks[h - 1][x] == B.AIR) {
                blocks[h - 1][x] = r.nextBoolean() ? B.FLOWER_R : B.FLOWER_Y;
            }
        }
        findSpawn(hgt);
        computeSurface();
    }

    private void growTree(int x, int h, Random r) {
        int th = 4 + r.nextInt(3), topY = h - th;
        int[] radii = {1, 2, 2, 1};
        for (int i = 0; i < 4; i++) {
            int ly = topY - 2 + i;
            for (int lx = x - radii[i]; lx <= x + radii[i]; lx++) {
                if (lx >= 0 && lx < Cfg.W && ly >= 0 && ly < Cfg.H && blocks[ly][lx] == B.AIR) blocks[ly][lx] = B.LEAVES;
            }
        }
        for (int y = topY; y < h; y++) blocks[y][x] = B.WOOD;
    }

    private void findSpawn(int[] hgt) {
        for (int d = 0; d < Cfg.W / 2 - 5; d++) {
            for (int s = -1; s <= 1; s += 2) {
                int x = Cfg.W / 2 + s * d, h = hgt[x];
                if (h >= Cfg.SEA - 1) continue;
                boolean clear = true;
                for (int y = h - 4; y < h; y++) {
                    int id = blocks[y][x];
                    if (id != B.AIR && id != B.FLOWER_R && id != B.FLOWER_Y) clear = false;
                }
                if (clear) { spawnX = x; spawnY = h; return; }
            }
        }
    }

    void computeSurface() { for (int x = 0; x < Cfg.W; x++) updateSurface(x); }

    private void updateSurface(int x) {
        for (int y = 0; y < Cfg.H; y++) if (Blocks.OPAQUE[blocks[y][x]]) { surface[x] = y; return; }
        surface[x] = Cfg.H;
    }

    int get(int x, int y) {
        if (x < 0 || x >= Cfg.W || y >= Cfg.H) return B.BEDROCK;
        if (y < 0) return B.AIR;
        return blocks[y][x];
    }

    void set(int x, int y, int id) {
        if (x < 0 || x >= Cfg.W || y < 0 || y >= Cfg.H) return;
        blocks[y][x] = id;
        updateSurface(x);
        lightDirty = true;
        if (y > 0 && Blocks.get(blocks[y - 1][x]).plant && !Blocks.get(id).solid) blocks[y - 1][x] = B.AIR;
    }

    boolean isSolid(int x, int y) { return Blocks.get(get(x, y)).solid; }
    boolean isSkyLit(int x, int y) { return x >= 0 && x < Cfg.W && y >= 0 && y < Cfg.H && sky[y][x] >= 14; }

    int lightAt(int x, int y) {
        if (x < 0 || x >= Cfg.W || y < 0 || y >= Cfg.H) return 0;
        int s = (int) (sky[y][x] * skyFactor + 0.5);
        return Math.max(s, blk[y][x]);
    }

    /** Yorug'likni qayta hisoblash: osmon nuri + mash'al/lava nuri. */
    void computeLight() {
        for (int y = 0; y < Cfg.H; y++) { Arrays.fill(sky[y], (byte) 0); Arrays.fill(blk[y], (byte) 0); }
        for (int x = 0; x < Cfg.W; x++) {
            int lvl = 15;
            for (int y = 0; y < Cfg.H; y++) {
                int id = blocks[y][x];
                if (Blocks.OPAQUE[id]) break;
                if (id == B.WATER) lvl -= 2;
                else if (id == B.LEAVES) lvl -= 1;
                if (lvl <= 0) break;
                sky[y][x] = (byte) lvl;
            }
        }
        for (int y = 0; y < Cfg.H; y++)
            for (int x = 0; x < Cfg.W; x++) {
                int e = Blocks.DEFS[blocks[y][x]].emit;
                if (e > 0) blk[y][x] = (byte) e;
            }
        spread(sky);
        spread(blk);
        lightDirty = false;
    }

    private void spread(byte[][] L) {
        for (int lvl = 15; lvl >= 2; lvl--) {
            for (int y = 0; y < Cfg.H; y++) {
                for (int x = 0; x < Cfg.W; x++) {
                    if (L[y][x] != lvl) continue;
                    // Qattiq bloklar ichida yorug'lik tezroq so'nadi (-3), havoda -1
                    int step = Blocks.OPAQUE[blocks[y][x]] ? 3 : 1;
                    if (lvl - step < 1) continue;
                    byte nl = (byte) (lvl - step);
                    if (x > 0 && L[y][x - 1] < nl) L[y][x - 1] = nl;
                    if (x < Cfg.W - 1 && L[y][x + 1] < nl) L[y][x + 1] = nl;
                    if (y > 0 && L[y - 1][x] < nl) L[y - 1][x] = nl;
                    if (y < Cfg.H - 1 && L[y + 1][x] < nl) L[y + 1][x] = nl;
                }
            }
        }
    }
}

// =====================================================================
// GRAFIK YORDAMCHILARI, TEKSTURA VA IKONALAR
// =====================================================================
final class Gfx {
    static int mixRgb(int a, int b, double t) {
        t = Math.max(0, Math.min(1, t));
        int r = (int) (((a >> 16) & 255) * (1 - t) + ((b >> 16) & 255) * t);
        int g = (int) (((a >> 8) & 255) * (1 - t) + ((b >> 8) & 255) * t);
        int bl = (int) ((a & 255) * (1 - t) + (b & 255) * t);
        return (r << 16) | (g << 8) | bl;
    }

    static Color col(int rgb, double b) {
        return new Color((int) (((rgb >> 16) & 255) * b), (int) (((rgb >> 8) & 255) * b), (int) ((rgb & 255) * b));
    }

    static double bright(int light) { return 0.1 + 0.9 * Math.pow(light / 15.0, 1.3); }

    static void rect(Graphics2D g, int rgb, double b, boolean hurt, int x, int y, int w, int h) {
        if (hurt) rgb = mixRgb(rgb, 0xFF2020, 0.55);
        g.setColor(col(rgb, b));
        g.fillRect(x, y, w, h);
    }

    static void text(Graphics2D g, String s, int x, int y, Color c) {
        g.setColor(Color.BLACK);
        g.drawString(s, x + 1, y + 1);
        g.setColor(c);
        g.drawString(s, x, y);
    }
    private Gfx() {}
}

final class Textures {
    static BufferedImage[] build() {
        BufferedImage[] t = new BufferedImage[B.COUNT];
        Random r = new Random(12345);
        for (int id = 1; id < B.COUNT; id++) t[id] = make(id, r);
        return t;
    }

    private static int clamp(int v) { return v < 0 ? 0 : Math.min(v, 255); }

    private static int shade(int rgb, int d) {
        return 0xFF000000 | (clamp(((rgb >> 16) & 255) + d) << 16) | (clamp(((rgb >> 8) & 255) + d) << 8) | clamp((rgb & 255) + d);
    }

    private static void noiseFill(BufferedImage img, int rgb, Random r, int cell, int amp) {
        int S = img.getWidth();
        for (int cy = 0; cy < S; cy += cell)
            for (int cx = 0; cx < S; cx += cell) {
                int c = shade(rgb, r.nextInt(amp * 2 + 1) - amp);
                for (int dy = 0; dy < cell; dy++) for (int dx = 0; dx < cell; dx++) img.setRGB(cx + dx, cy + dy, c);
            }
    }

    private static void blob(BufferedImage img, int rgb, Random r) {
        int S = img.getWidth();
        int bx = 2 + r.nextInt(S - 10), by = 2 + r.nextInt(S - 10), bw = 4 + r.nextInt(5), bh = 4 + r.nextInt(4);
        for (int y = by; y < by + bh; y++)
            for (int x = bx; x < bx + bw; x++) img.setRGB(x, y, shade(rgb, r.nextInt(31) - 15));
    }

    private static BufferedImage make(int id, Random r) {
        final int S = Cfg.TILE;
        BufferedImage img = new BufferedImage(S, S, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        int base = Blocks.get(id).color;
        switch (id) {
            case B.GRASS:
                noiseFill(img, 0x8B5A2B, r, 4, 12);
                for (int x = 0; x < S; x++) {
                    int gh = 7 + r.nextInt(4);
                    for (int y = 0; y < gh; y++) img.setRGB(x, y, shade(0x5DA130, r.nextInt(21) - 10));
                }
                break;
            case B.COAL_ORE: case B.IRON_ORE: case B.GOLD_ORE: case B.DIAMOND_ORE:
                noiseFill(img, 0x7D7D7D, r, 4, 12);
                for (int i = 0; i < 5; i++) blob(img, base, r);
                break;
            case B.WOOD:
                noiseFill(img, base, r, 4, 6);
                for (int x = 0; x < S; x++) {
                    if (x % 8 < 2) for (int y = 0; y < S; y++) img.setRGB(x, y, shade(base, -28));
                    else if (x % 8 == 4) for (int y = 0; y < S; y++) img.setRGB(x, y, shade(base, 14));
                }
                break;
            case B.PLANKS:
                noiseFill(img, base, r, 8, 6);
                g.setColor(new Color(shade(base, -38)));
                for (int y = 7; y < S; y += 8) g.drawLine(0, y, S, y);
                g.fillRect(4, 3, 2, 2); g.fillRect(26, 3, 2, 2); g.fillRect(4, 19, 2, 2); g.fillRect(26, 19, 2, 2);
                break;
            case B.LEAVES:
                noiseFill(img, base, r, 4, 22);
                for (int cy = 0; cy < S; cy += 4)
                    for (int cx = 0; cx < S; cx += 4)
                        if (r.nextInt(9) == 0) for (int dy = 0; dy < 4; dy++) for (int dx = 0; dx < 4; dx++) img.setRGB(cx + dx, cy + dy, 0);
                break;
            case B.WATER:
                g.setColor(new Color(40, 100, 220, 150)); g.fillRect(0, 0, S, S);
                g.setColor(new Color(255, 255, 255, 40)); g.fillRect(0, 6, S, 2); g.fillRect(8, 20, S - 8, 2);
                break;
            case B.LAVA:
                noiseFill(img, base, r, 4, 30);
                for (int i = 0; i < 4; i++) blob(img, 0xFFC830, r);
                break;
            case B.GLASS:
                g.setColor(new Color(200, 235, 255, 70)); g.fillRect(0, 0, S, S);
                g.setColor(new Color(235, 248, 255, 210)); g.drawRect(0, 0, S - 1, S - 1);
                g.drawLine(5, 5, 12, 12); g.drawLine(8, 5, 15, 12);
                break;
            case B.TORCH:
                g.setColor(new Color(255, 200, 80, 50)); g.fillOval(4, -2, 24, 24);
                g.setColor(new Color(110, 75, 35)); g.fillRect(14, 12, 4, 20);
                g.setColor(new Color(255, 150, 30)); g.fillRect(13, 5, 6, 8);
                g.setColor(new Color(255, 230, 90)); g.fillRect(14, 7, 4, 5);
                break;
            case B.BRICK:
                g.setColor(new Color(0xC8C0B0)); g.fillRect(0, 0, S, S);
                for (int row = 0; row < 4; row++) {
                    int off = (row % 2 == 0) ? 0 : -8;
                    for (int bx = off; bx < S; bx += 16) {
                        g.setColor(new Color(shade(base, r.nextInt(25) - 12)));
                        g.fillRect(bx + 1, row * 8 + 1, 14, 6);
                    }
                }
                break;
            case B.SNOW: noiseFill(img, base, r, 4, 7); break;
            case B.FLOWER_R: case B.FLOWER_Y:
                g.setColor(new Color(40, 130, 40));
                g.fillRect(15, 14, 2, 18); g.fillRect(11, 22, 5, 2); g.fillRect(17, 18, 5, 2);
                g.setColor(new Color(base));
                g.fillOval(11, 5, 6, 6); g.fillOval(17, 5, 6, 6); g.fillOval(11, 11, 6, 6); g.fillOval(17, 11, 6, 6);
                g.setColor(id == B.FLOWER_R ? new Color(250, 220, 60) : new Color(150, 90, 30));
                g.fillOval(14, 8, 6, 6);
                break;
            case B.COBBLE:
                noiseFill(img, base, r, 4, 14);
                g.setColor(new Color(0, 0, 0, 80));
                g.drawRoundRect(1, 1, 14, 11, 6, 6); g.drawRoundRect(17, 2, 13, 9, 6, 6);
                g.drawRoundRect(2, 14, 10, 15, 6, 6); g.drawRoundRect(13, 13, 17, 16, 6, 6);
                break;
            case B.TNT:
                g.setColor(new Color(base)); g.fillRect(0, 0, S, S);
                g.setColor(new Color(0xA82520));
                for (int x = 3; x < S; x += 8) g.fillRect(x, 0, 2, S);
                g.setColor(Color.WHITE); g.fillRect(0, 11, S, 10);
                g.setColor(Color.BLACK); g.setFont(new Font("Arial", Font.BOLD, 10)); g.drawString("TNT", 6, 20);
                break;
            case B.SAND: noiseFill(img, base, r, 2, 10); break;
            case B.BEDROCK: noiseFill(img, base, r, 4, 22); break;
            default: noiseFill(img, base, r, 4, 12); break;
        }
        if (Blocks.OPAQUE[id]) { g.setColor(new Color(0, 0, 0, 40)); g.drawRect(0, 0, S - 1, S - 1); }
        g.dispose();
        return img;
    }
    private Textures() {}
}

final class Icons {
    static void draw(Graphics2D g, BufferedImage[] tex, int id, int x, int y, int sz) {
        if (id <= 0) return;
        if (Items.isBlock(id)) { g.drawImage(tex[id], x, y, sz, sz, null); return; }
        if (Items.isTool(id)) { tool(g, id, x, y, sz); return; }
        double s = sz / 32.0;
        Stroke old = g.getStroke();
        switch (id) {
            case Items.STICK:
                g.setColor(new Color(130, 90, 45)); g.setStroke(new BasicStroke((float) (4 * s)));
                g.drawLine(x + (int) (7 * s), y + (int) (26 * s), x + (int) (25 * s), y + (int) (6 * s));
                break;
            case Items.COAL:
                g.setColor(new Color(30, 30, 30)); g.fillOval(x + (int) (6 * s), y + (int) (7 * s), (int) (20 * s), (int) (18 * s));
                g.setColor(new Color(80, 80, 80)); g.fillOval(x + (int) (11 * s), y + (int) (10 * s), (int) (6 * s), (int) (5 * s));
                break;
            case Items.IRON_INGOT: case Items.GOLD_INGOT:
                g.setColor(id == Items.IRON_INGOT ? new Color(220, 220, 225) : new Color(250, 210, 50));
                g.fillPolygon(new int[]{x + (int) (4 * s), x + (int) (24 * s), x + (int) (29 * s), x + (int) (9 * s)},
                        new int[]{y + (int) (22 * s), y + (int) (22 * s), y + (int) (10 * s), y + (int) (10 * s)}, 4);
                g.setColor(new Color(0, 0, 0, 70)); g.drawRect(x + (int) (4 * s), y + (int) (14 * s), (int) (20 * s), (int) (8 * s));
                break;
            case Items.DIAMOND:
                g.setColor(new Color(80, 235, 240));
                g.fillPolygon(new int[]{x + (int) (16 * s), x + (int) (27 * s), x + (int) (16 * s), x + (int) (5 * s)},
                        new int[]{y + (int) (4 * s), y + (int) (14 * s), y + (int) (28 * s), y + (int) (14 * s)}, 4);
                g.setColor(new Color(255, 255, 255, 160)); g.fillRect(x + (int) (12 * s), y + (int) (10 * s), (int) (5 * s), (int) (5 * s));
                break;
            case Items.PORK: case Items.COOKED_PORK: case Items.BEEF: case Items.COOKED_BEEF: case Items.ROTTEN: {
                int c = id == Items.PORK ? 0xF0A0A0 : id == Items.COOKED_PORK ? 0xA8663A : id == Items.BEEF ? 0xB82828
                        : id == Items.COOKED_BEEF ? 0x6E3A22 : 0x7A8A3A;
                g.setColor(new Color(c)); g.fillOval(x + (int) (4 * s), y + (int) (8 * s), (int) (24 * s), (int) (17 * s));
                g.setColor(new Color(255, 255, 255, 90)); g.fillOval(x + (int) (9 * s), y + (int) (11 * s), (int) (9 * s), (int) (5 * s));
                break;
            }
            case Items.APPLE:
                g.setColor(new Color(210, 30, 30)); g.fillOval(x + (int) (6 * s), y + (int) (8 * s), (int) (20 * s), (int) (20 * s));
                g.setColor(new Color(50, 150, 40)); g.fillRect(x + (int) (15 * s), y + (int) (3 * s), (int) (6 * s), (int) (5 * s));
                break;
            default: break;
        }
        g.setStroke(old);
    }

    private static void tool(Graphics2D g, int id, int x, int y, int sz) {
        int type = Items.toolType(id);
        Color head = new Color(Items.TIER_COLOR[Items.toolTier(id)]);
        Color wood = new Color(120, 82, 40);
        double s = sz / 32.0;
        Stroke old = g.getStroke();
        if (type == Items.T_SWORD) {
            g.setColor(head); g.setStroke(new BasicStroke((float) (5 * s)));
            g.drawLine(x + (int) (26 * s), y + (int) (4 * s), x + (int) (11 * s), y + (int) (19 * s));
            g.setColor(wood); g.setStroke(new BasicStroke((float) (3 * s)));
            g.drawLine(x + (int) (10 * s), y + (int) (20 * s), x + (int) (5 * s), y + (int) (27 * s));
            g.drawLine(x + (int) (8 * s), y + (int) (14 * s), x + (int) (16 * s), y + (int) (22 * s));
        } else {
            g.setColor(wood); g.setStroke(new BasicStroke((float) (3.5 * s)));
            g.drawLine(x + (int) (6 * s), y + (int) (27 * s), x + (int) (22 * s), y + (int) (11 * s));
            g.setColor(head);
            if (type == Items.T_PICK) {
                g.setStroke(new BasicStroke((float) (4.5 * s)));
                g.drawLine(x + (int) (13 * s), y + (int) (6 * s), x + (int) (27 * s), y + (int) (20 * s));
            } else if (type == Items.T_AXE) {
                g.fillPolygon(new int[]{x + (int) (17 * s), x + (int) (28 * s), x + (int) (26 * s), x + (int) (20 * s)},
                        new int[]{y + (int) (6 * s), y + (int) (9 * s), y + (int) (19 * s), y + (int) (15 * s)}, 4);
            } else {
                g.fillOval(x + (int) (18 * s), y + (int) (4 * s), (int) (11 * s), (int) (13 * s));
            }
        }
        g.setStroke(old);
    }
    private Icons() {}
}

// =====================================================================
// OBYEKTLAR (ENTITY): O'YINCHI, MOBLAR, BUYUMLAR, TNT, ZARRACHALAR
// =====================================================================
abstract class Entity {
    double x, y, vx, vy, w, h, lastVy;
    boolean onGround, inWater, inLava, hitWall, removed;
    int health = 1, maxHealth = 1, hurtTimer, facing = 1;

    double cx() { return x + w / 2; }
    double cy() { return y + h / 2; }

    boolean collides(World wd) {
        int x0 = (int) Math.floor(x / Cfg.TILE), x1 = (int) Math.floor((x + w - 0.001) / Cfg.TILE);
        int y0 = (int) Math.floor(y / Cfg.TILE), y1 = (int) Math.floor((y + h - 0.001) / Cfg.TILE);
        for (int ty = y0; ty <= y1; ty++)
            for (int tx = x0; tx <= x1; tx++)
                if (wd.isSolid(tx, ty)) return true;
        return false;
    }

    boolean intersects(Entity o) { return x < o.x + o.w && x + w > o.x && y < o.y + o.h && y + h > o.y; }

    void updateFluids(World wd) {
        int tx = (int) Math.floor(cx() / Cfg.TILE);
        int id = wd.get(tx, (int) Math.floor((y + h * 0.6) / Cfg.TILE));
        inWater = id == B.WATER;
        inLava = id == B.LAVA || wd.get(tx, (int) Math.floor((y + h - 2) / Cfg.TILE)) == B.LAVA;
    }

    void applyGravity(double g) {
        if (inWater) { vy += g * 0.25; vy *= 0.85; vx *= 0.88; if (vy > 3) vy = 3; }
        else if (inLava) { vy += g * 0.15; vy *= 0.7; vx *= 0.7; }
        else { vy += g; if (vy > Cfg.MAX_FALL) vy = Cfg.MAX_FALL; }
    }

    void move(World wd) {
        final double T = Cfg.TILE;
        hitWall = false;
        x += vx;
        if (collides(wd)) {
            if (vx > 0) x = Math.floor((x + w) / T) * T - w - 0.01;
            else if (vx < 0) x = (Math.floor(x / T) + 1) * T + 0.01;
            vx = 0; hitWall = true;
        }
        lastVy = vy;
        y += vy;
        onGround = false;
        if (collides(wd)) {
            if (vy > 0) { y = Math.floor((y + h) / T) * T - h - 0.01; onGround = true; }
            else if (vy < 0) y = (Math.floor(y / T) + 1) * T + 0.01;
            vy = 0;
        }
    }

    void tickTimers() { if (hurtTimer > 0) hurtTimer--; }
}

final class Player extends Entity {
    final Inventory inv = new Inventory();
    int selected = 0, hunger = 20, air = 300, attackCd = 0, swing = 0;
    double exhaust = 0, anim = 0;
    boolean creative = false, flying = false, sprinting = false;

    Player() { w = 24; h = 54; maxHealth = 20; health = 20; }
    ItemStack held() { return inv.slots[selected]; }
}

enum MobType {
    ZOMBIE(20, 26, 56, 1.7), PIG(10, 38, 24, 0.9), COW(14, 44, 36, 0.8);
    final int hp, w, h;
    final double speed;
    MobType(int hp, int w, int h, double speed) { this.hp = hp; this.w = w; this.h = h; this.speed = speed; }
}

final class Mob extends Entity {
    static final Random R = new Random();
    final MobType type;
    int aiTimer, dir, attackCd, fleeTimer, knockTimer, burnTimer;
    double anim;

    Mob(MobType t, double x, double y) {
        type = t; this.x = x; this.y = y; w = t.w; h = t.h; maxHealth = health = t.hp;
        facing = R.nextBoolean() ? 1 : -1;
    }

    void hurt(int dmg, double kx, boolean force) {
        if (!force && hurtTimer > 0) return;
        health -= dmg; hurtTimer = 12; knockTimer = 10; vx = kx; vy = -4.5;
        if (type != MobType.ZOMBIE) fleeTimer = 140;
    }

    private int wander() {
        if (--aiTimer <= 0) {
            aiTimer = 50 + R.nextInt(120);
            int r = R.nextInt(5);
            dir = r < 2 ? 0 : (r == 2 ? -1 : (r == 3 ? 1 : dir));
        }
        return dir;
    }

    void update(GamePanel g) {
        World wd = g.world;
        Player p = g.player;
        tickTimers();
        if (knockTimer > 0) knockTimer--;
        if (attackCd > 0) attackCd--;
        updateFluids(wd);
        applyGravity(Cfg.GRAVITY);
        if (inWater) { vy -= 0.45; if (vy < -2.2) vy = -2.2; }
        if (knockTimer == 0) {
            int want;
            double sp = type.speed;
            if (type == MobType.ZOMBIE) {
                double dx = p.cx() - cx(), dy = p.cy() - cy();
                boolean chase = !p.creative && g.state != GamePanel.ST_DEAD
                        && Math.abs(dx) < 24 * Cfg.TILE && Math.abs(dy) < 10 * Cfg.TILE;
                want = chase ? (dx > 0 ? 1 : -1) : wander();
                if (chase && Math.abs(dx) < 4) want = 0;
                if (g.daylight > 0.75 && !inWater && wd.isSkyLit((int) Math.floor(cx() / Cfg.TILE), (int) Math.floor(y / Cfg.TILE))) {
                    burnTimer++;
                    if (burnTimer % 30 == 0) { health -= 1; g.burst(cx(), y + 10, 0xFF8820, 6); }
                }
                if (attackCd == 0 && !p.creative && intersects(p)) {
                    g.damagePlayer(3, (p.cx() > cx() ? 1 : -1) * 5);
                    attackCd = 45;
                }
            } else if (fleeTimer > 0) {
                fleeTimer--;
                want = cx() > p.cx() ? 1 : -1;
                sp *= 2;
            } else {
                want = wander();
            }
            vx = want * sp;
            if (want != 0) facing = want;
            if (hitWall && onGround && want != 0) vy = -8.6;
        } else {
            vx *= 0.92;
        }
        move(wd);
        anim += Math.abs(vx) * 0.22;
    }

    void draw(Graphics2D g, double b) {
        int px = (int) x, py = (int) y, f = facing;
        boolean hurt = hurtTimer > 0 && (hurtTimer / 3) % 2 == 0;
        int leg = (int) (Math.sin(anim) * 5);
        switch (type) {
            case ZOMBIE:
                Gfx.rect(g, 0x2E2E7E, b, hurt, px + 4 + leg, py + 38, 8, 18);
                Gfx.rect(g, 0x2E2E7E, b, hurt, px + 14 - leg, py + 38, 8, 18);
                Gfx.rect(g, 0x2B8FA6, b, hurt, px + 4, py + 16, 18, 22);
                Gfx.rect(g, 0x4D9B4D, b, hurt, f > 0 ? px + 16 : px - 4, py + 18, 14, 6);
                Gfx.rect(g, 0x4D9B4D, b, hurt, px + 5, py, 16, 16);
                Gfx.rect(g, 0x111111, b, false, f > 0 ? px + 14 : px + 7, py + 5, 4, 3);
                Gfx.rect(g, 0x222222, b, false, f > 0 ? px + 11 : px + 6, py + 11, 9, 2);
                break;
            case PIG: {
                int bodyX = f > 0 ? px : px + 10, headX = f > 0 ? px + 26 : px;
                Gfx.rect(g, 0xD98A8A, b, hurt, px + 4 + leg, py + 16, 6, 8);
                Gfx.rect(g, 0xD98A8A, b, hurt, px + 28 - leg, py + 16, 6, 8);
                Gfx.rect(g, 0xF0A0A0, b, hurt, bodyX, py + 2, 28, 16);
                Gfx.rect(g, 0xF0A0A0, b, hurt, headX, py, 12, 14);
                Gfx.rect(g, 0xFFC0C0, b, hurt, f > 0 ? headX + 9 : headX - 2, py + 7, 5, 6);
                Gfx.rect(g, 0x111111, b, false, f > 0 ? headX + 6 : headX + 3, py + 3, 3, 3);
                break;
            }
            default: {
                int bodyX = f > 0 ? px : px + 10, headX = f > 0 ? px + 32 : px;
                Gfx.rect(g, 0xDDDDDD, b, hurt, px + 6 + leg, py + 24, 7, 12);
                Gfx.rect(g, 0xDDDDDD, b, hurt, px + 32 - leg, py + 24, 7, 12);
                Gfx.rect(g, 0xEDEDED, b, hurt, bodyX, py + 4, 34, 22);
                Gfx.rect(g, 0x4A3020, b, hurt, bodyX + 6, py + 6, 9, 8);
                Gfx.rect(g, 0x4A3020, b, hurt, bodyX + 20, py + 12, 10, 9);
                Gfx.rect(g, 0xE8E0D0, b, hurt, headX, py, 12, 14);
                Gfx.rect(g, 0xE0B0A0, b, hurt, f > 0 ? headX + 8 : headX - 1, py + 8, 5, 6);
                Gfx.rect(g, 0x5A4A3A, b, false, headX + 2, py - 3, 3, 4);
                Gfx.rect(g, 0x5A4A3A, b, false, headX + 8, py - 3, 3, 4);
                Gfx.rect(g, 0x111111, b, false, f > 0 ? headX + 7 : headX + 3, py + 4, 3, 3);
                break;
            }
        }
        if (health < maxHealth) {
            g.setColor(new Color(0, 0, 0, 150)); g.fillRect(px, py - 8, (int) w, 4);
            g.setColor(new Color(220, 40, 40)); g.fillRect(px, py - 8, (int) (w * health / maxHealth), 4);
        }
    }
}

final class ItemEntity extends Entity {
    final ItemStack stack;
    int age, delay = 20;

    ItemEntity(double x, double y, ItemStack s, Random r) {
        stack = s; this.x = x - 8; this.y = y - 8; w = 16; h = 16;
        vx = (r.nextDouble() - 0.5) * 3; vy = -3 - r.nextDouble() * 2;
    }

    void update(GamePanel g) {
        age++;
        if (delay > 0) delay--;
        updateFluids(g.world);
        applyGravity(Cfg.GRAVITY);
        move(g.world);
        vx *= onGround ? 0.75 : 0.99;
        if (age > 18000 || y > Cfg.H * Cfg.TILE) removed = true;
        Player p = g.player;
        if (delay == 0 && g.state != GamePanel.ST_DEAD && p.inv.canFit(stack)) {
            double dx = p.cx() - cx(), dy = p.cy() - cy(), d = Math.sqrt(dx * dx + dy * dy);
            if (d < 80 && d > 1) { vx += dx / d * 0.8; vy += dy / d * 0.7 - Cfg.GRAVITY * 0.9; }
            if (d < 24) {
                int left = p.inv.add(stack.id, stack.count, stack.dur);
                if (left == 0) removed = true; else stack.count = left;
            }
        }
    }
}

final class PrimedTnt extends Entity {
    int fuse;
    PrimedTnt(double x, double y, int fuse) { this.x = x; this.y = y; w = 28; h = 28; this.fuse = fuse; }

    void update(World wd) {
        updateFluids(wd);
        applyGravity(Cfg.GRAVITY);
        move(wd);
        vx *= onGround ? 0.8 : 0.99;
        fuse--;
    }
}

final class Particle {
    double x, y, vx, vy;
    int life, maxLife, color, size;

    Particle(double x, double y, double vx, double vy, int color, int life, int size) {
        this.x = x; this.y = y; this.vx = vx; this.vy = vy; this.color = color;
        this.life = this.maxLife = life; this.size = size;
    }

    void update() { x += vx; y += vy; vy += 0.3; vx *= 0.98; life--; }

    void draw(Graphics2D g) {
        int a = Math.max(0, Math.min(255, 255 * life / maxLife));
        g.setColor(new Color((color >> 16) & 255, (color >> 8) & 255, color & 255, a));
        g.fillRect((int) x, (int) y, size, size);
    }
}

// =====================================================================
// SAQLASH / YUKLASH
// =====================================================================
final class SaveManager {
    static boolean save(GamePanel g) {
        try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(
                new GZIPOutputStream(new FileOutputStream(Cfg.SAVE_FILE))))) {
            out.writeInt(0x53425831);
            out.writeLong(g.world.seed);
            out.writeLong(g.world.ticks);
            out.writeInt(g.world.spawnX);
            out.writeInt(g.world.spawnY);
            for (int y = 0; y < Cfg.H; y++) for (int x = 0; x < Cfg.W; x++) out.writeByte(g.world.blocks[y][x]);
            Player p = g.player;
            out.writeDouble(p.x); out.writeDouble(p.y);
            out.writeInt(p.health); out.writeInt(p.hunger); out.writeInt(p.selected);
            out.writeBoolean(p.creative);
            for (ItemStack s : p.inv.slots) {
                if (s == null) out.writeInt(-1);
                else { out.writeInt(s.id); out.writeInt(s.count); out.writeInt(s.dur); }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    static boolean load(GamePanel g) {
        File f = new File(Cfg.SAVE_FILE);
        if (!f.exists()) return false;
        try (DataInputStream in = new DataInputStream(new BufferedInputStream(
                new GZIPInputStream(new FileInputStream(f))))) {
            if (in.readInt() != 0x53425831) return false;
            World w = new World(in.readLong(), false);
            w.ticks = in.readLong();
            w.spawnX = in.readInt();
            w.spawnY = in.readInt();
            for (int y = 0; y < Cfg.H; y++) for (int x = 0; x < Cfg.W; x++) w.blocks[y][x] = in.readByte();
            w.computeSurface();
            Player p = new Player();
            p.x = in.readDouble(); p.y = in.readDouble();
            p.health = in.readInt(); p.hunger = in.readInt(); p.selected = in.readInt();
            p.creative = in.readBoolean();
            p.flying = p.creative;
            for (int i = 0; i < p.inv.slots.length; i++) {
                int id = in.readInt();
                if (id >= 0) {
                    ItemStack s = new ItemStack(id, in.readInt());
                    s.dur = in.readInt();
                    p.inv.slots[i] = s;
                }
            }
            g.world = w;
            g.player = p;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    private SaveManager() {}
}

// =====================================================================
// ASOSIY O'YIN PANELI
// =====================================================================
final class GamePanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    static final int ST_PLAY = 0, ST_PAUSE = 1, ST_INV = 2, ST_DEAD = 3;
    static final int PW = 900, PH = 500;
    static final Color CAVE_BG = new Color(34, 29, 26);
    static final Color[] SHADES = new Color[16];
    static {
        for (int l = 0; l < 16; l++)
            SHADES[l] = new Color(0, 0, 0, (int) Math.round((1 - Gfx.bright(l)) * 255));
    }

    World world;
    Player player;
    BufferedImage[] tex;
    final List<Mob> mobs = new ArrayList<>();
    final List<ItemEntity> drops = new ArrayList<>();
    final List<Particle> parts = new ArrayList<>();
    final List<PrimedTnt> tnts = new ArrayList<>();
    final List<Integer> palette = new ArrayList<>();
    final Random rnd = new Random();
    final boolean[] keys = new boolean[1024];

    int state = ST_PLAY, tick, mouseX, mouseY, shake, placeCd, mineX = -1, mineY = -1;
    int msgTimer, nameTimer, lastSel = -1, scroll, miniTimer, hoverX = -1, hoverY = -1;
    double mineProg, mineNeed = 1, camX, camY, daylight = 1;
    boolean lmb, rmb, showDebug, showMap = true, hoverReach;
    String msg = "";
    ItemStack cursor;
    long lastNanos, fpsTimer;
    int frames, fps;
    double acc;
    Timer timer;

    final Noise mtnNoise = new Noise(777);
    final double[] starX = new double[140], starY = new double[140];
    final double[] cloudX = new double[14], cloudY = new double[14], cloudW = new double[14];
    final BufferedImage minimap = new BufferedImage(120, 70, BufferedImage.TYPE_INT_RGB);

    GamePanel() {
        setPreferredSize(new Dimension(1280, 720));
        setFocusable(true);
        setBackground(Color.BLACK);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        tex = Textures.build();
        for (int i = 1; i < B.COUNT; i++) palette.add(i);
        for (int i = Items.STICK; i <= Items.ROTTEN; i++) palette.add(i);
        for (int ty = 1; ty <= 4; ty++) for (int t = 0; t < 4; t++) palette.add(Items.tool(ty, t));
        for (int i = 0; i < starX.length; i++) { starX[i] = rnd.nextDouble(); starY[i] = rnd.nextDouble(); }
        for (int i = 0; i < cloudX.length; i++) { cloudX[i] = rnd.nextDouble() * 2000; cloudY[i] = rnd.nextDouble(); cloudW[i] = 80 + rnd.nextDouble() * 100; }
        newWorld(System.nanoTime());
    }

    int vw() { return getWidth() > 0 ? getWidth() : 1280; }
    int vh() { return getHeight() > 0 ? getHeight() : 720; }
    boolean key(int c) { return c >= 0 && c < keys.length && keys[c]; }
    void toast(String s) { msg = s; msgTimer = 180; }

    void start() {
        updateCamera(true);
        lastNanos = System.nanoTime();
        timer = new Timer(8, e -> loop());
        timer.start();
    }

    // ------------------------------------------------------------- dunyo
    void newWorld(long seed) {
        boolean creative = player != null && player.creative;
        world = new World(seed, true);
        world.ticks = (long) (Cfg.DAY_TICKS * 0.08);
        player = new Player();
        player.creative = creative;
        player.flying = creative;
        clearEntities();
        Inventory inv = player.inv;
        inv.add(Items.tool(Items.T_PICK, 0), 1, 0);
        inv.add(Items.tool(Items.T_AXE, 0), 1, 0);
        inv.add(Items.tool(Items.T_SHOVEL, 0), 1, 0);
        inv.add(Items.tool(Items.T_SWORD, 0), 1, 0);
        inv.add(B.TORCH, 16, 0);
        inv.add(Items.APPLE, 4, 0);
        respawn();
        world.computeLight();
        updateCamera(true);
        state = ST_PLAY;
    }

    void clearEntities() { mobs.clear(); drops.clear(); parts.clear(); tnts.clear(); cursor = null; }

    void respawn() {
        Player p = player;
        p.x = world.spawnX * Cfg.TILE + 4;
        p.y = world.spawnY * Cfg.TILE - p.h - 2;
        p.vx = p.vy = 0;
        p.health = p.maxHealth; p.hunger = 20; p.air = 300; p.hurtTimer = 0;
        while (p.collides(world) && p.y > 0) p.y -= Cfg.TILE;
    }

    void respawnPlayer() { respawn(); state = ST_PLAY; lmb = rmb = false; }

    // ------------------------------------------------------------- tsikl
    void loop() {
        long now = System.nanoTime();
        acc += (now - lastNanos) / 1e9;
        lastNanos = now;
        if (acc > 0.25) acc = 0.25;
        while (acc >= 1.0 / 60) { update(); acc -= 1.0 / 60; }
        repaint();
    }

    void update() {
        if (state == ST_PAUSE) return;
        tick++;
        if (shake > 0) shake--;
        if (msgTimer > 0) msgTimer--;
        if (nameTimer > 0) nameTimer--;
        updateParticles();
        if (state == ST_DEAD) return;
        world.ticks++;
        double elev = Math.sin((world.ticks % Cfg.DAY_TICKS) / (double) Cfg.DAY_TICKS * Math.PI * 2);
        daylight = Math.max(0, Math.min(1, (elev + 0.25) / 0.6));
        world.skyFactor = 0.18 + 0.82 * daylight;
        if (world.lightDirty) world.computeLight();
        updatePlayer();
        updateMobs();
        updateDrops();
        updateTnt();
        trySpawn();
        if (player.selected != lastSel) { lastSel = player.selected; nameTimer = 120; }
        updateCamera(false);
    }

    void updateCamera(boolean snap) {
        double tx = player.cx() - vw() / 2.0, ty = player.cy() - vh() / 2.0 - 20;
        tx = Math.max(0, Math.min(Math.max(0, Cfg.W * Cfg.TILE - vw()), tx));
        ty = Math.max(0, Math.min(Math.max(0, Cfg.H * Cfg.TILE - vh()), ty));
        if (snap) { camX = tx; camY = ty; }
        else { camX += (tx - camX) * 0.12; camY += (ty - camY) * 0.12; }
    }

    // ------------------------------------------------------------- o'yinchi
    void updatePlayer() {
        Player p = player;
        boolean ctl = state == ST_PLAY;
        boolean left = ctl && (key(KeyEvent.VK_A) || key(KeyEvent.VK_LEFT));
        boolean right = ctl && (key(KeyEvent.VK_D) || key(KeyEvent.VK_RIGHT));
        boolean up = ctl && (key(KeyEvent.VK_W) || key(KeyEvent.VK_UP) || key(KeyEvent.VK_SPACE));
        boolean down = ctl && (key(KeyEvent.VK_S) || key(KeyEvent.VK_DOWN));
        p.sprinting = ctl && key(KeyEvent.VK_SHIFT) && (left != right) && (p.hunger > 3 || p.creative);
        double speed = p.sprinting ? 4.7 : 3.3;
        if (p.flying) speed *= 1.8;
        if (p.inWater) speed *= 0.65;
        double target = left == right ? 0 : (left ? -speed : speed);
        if (target != 0) p.facing = target > 0 ? 1 : -1;
        p.vx += (target - p.vx) * 0.4;
        p.updateFluids(world);
        boolean wasGround = p.onGround;
        if (p.flying) {
            p.vy = up ? -5.5 : (down ? 5.5 : 0);
        } else {
            p.applyGravity(Cfg.GRAVITY);
            if (p.inWater) { if (up) p.vy -= 0.8; if (p.vy < -3.2) p.vy = -3.2; }
            else if (p.inLava) { if (up) p.vy -= 0.55; }
            else if (up && p.onGround) p.vy = -8.8;
        }
        p.move(world);
        p.anim += Math.abs(p.vx) * 0.22;
        p.tickTimers();
        if (p.attackCd > 0) p.attackCd--;
        if (p.swing > 0) p.swing--;

        if (!p.flying && !p.creative && p.onGround && !wasGround && p.lastVy > 11.5 && !p.inWater)
            damagePlayer((int) ((p.lastVy - 10.5) / 1.2), 0);

        if (!p.creative) {
            p.exhaust += (p.sprinting ? 3 : 1) + (lmb ? 1 : 0);
            if (p.exhaust >= 900) { p.exhaust = 0; if (p.hunger > 0) p.hunger--; }
            if (p.hunger >= 18 && p.health < p.maxHealth && tick % 80 == 0) p.health++;
            if (p.hunger == 0 && tick % 120 == 0 && p.health > 1) damagePlayer(1, 0);
            boolean underwater = world.get((int) Math.floor(p.cx() / Cfg.TILE), (int) Math.floor((p.y + 6) / Cfg.TILE)) == B.WATER;
            if (underwater) {
                if (p.air > 0) p.air--;
                else if (tick % 30 == 0) { p.hurtTimer = 0; damagePlayer(2, 0); }
            } else p.air = Math.min(300, p.air + 10);
            if (p.inLava && tick % 10 == 0) { damagePlayer(2, 0); burst(p.cx(), p.cy(), 0xFF7010, 5); }
        } else p.air = 300;

        if (p.y > Cfg.H * Cfg.TILE + 300) { if (p.creative) respawn(); else { p.health = 0; die(); } }
        if (ctl) handleMouse(); else { hoverX = -1; mineProg = 0; }
    }

    void damagePlayer(int dmg, double kx) {
        Player p = player;
        if (p.creative || p.hurtTimer > 0 || state == ST_DEAD || dmg <= 0) return;
        p.health -= dmg;
        p.hurtTimer = 20;
        if (kx != 0) { p.vx = kx; p.vy = -4.5; }
        shake = 10;
        if (p.health <= 0) die();
    }

    void die() { state = ST_DEAD; lmb = rmb = false; burst(player.cx(), player.cy(), 0xCC2020, 30); }

    // ------------------------------------------------------------- sichqoncha amallari
    void handleMouse() {
        Player p = player;
        if (placeCd > 0) placeCd--;
        double wx = mouseX + camX, wy = mouseY + camY;
        int tx = (int) Math.floor(wx / Cfg.TILE), ty = (int) Math.floor(wy / Cfg.TILE);
        double reach = p.creative ? 9 : Cfg.REACH;
        double dist = Math.hypot(tx * Cfg.TILE + 16 - p.cx(), ty * Cfg.TILE + 16 - p.cy()) / Cfg.TILE;
        boolean inReach = dist <= reach;
        hoverX = tx; hoverY = ty; hoverReach = inReach;
        if (lmb) {
            Mob m = mobAt(wx, wy);
            if (m != null && Math.hypot(m.cx() - p.cx(), m.cy() - p.cy()) < 4.5 * Cfg.TILE) {
                if (p.attackCd <= 0) attack(m);
                mineProg = 0;
            } else if (inReach) mine(tx, ty);
            else mineProg = 0;
        } else mineProg = 0;
        if (rmb && placeCd <= 0) useItem(tx, ty, inReach);
    }

    Mob mobAt(double wx, double wy) {
        for (Mob m : mobs)
            if (wx >= m.x - 4 && wx <= m.x + m.w + 4 && wy >= m.y - 4 && wy <= m.y + m.h + 4) return m;
        return null;
    }

    void attack(Mob m) {
        Player p = player;
        ItemStack h = p.held();
        double dmg = h == null ? 1 : Items.attackDamage(h.id);
        if (!p.onGround && p.vy > 0) dmg *= 1.5;
        p.facing = m.cx() > p.cx() ? 1 : -1;
        m.hurt((int) Math.ceil(dmg), p.facing * 6, false);
        p.attackCd = 14; p.swing = 8;
        burst(m.cx(), m.cy(), 0xCC2222, 8);
        damageTool(1);
    }

    boolean canHarvest(BlockDef d, ItemStack held) {
        if (d.minTier < 0) return true;
        return held != null && Items.toolType(held.id) == Items.T_PICK && Items.toolTier(held.id) >= d.minTier;
    }

    double breakTime(BlockDef d, ItemStack held) {
        double t = d.hardness * 60;
        boolean match = held != null && d.tool != 0 && Items.toolType(held.id) == d.tool;
        if (!canHarvest(d, held)) t *= 3.3;
        return Math.max(2, t / (match ? Items.SPEED[Items.toolTier(held.id)] : 1.0));
    }

    void mine(int tx, int ty) {
        int id = world.get(tx, ty);
        BlockDef d = Blocks.get(id);
        if (id == B.AIR || d.hardness < 0 || d.liquid) { mineProg = 0; return; }
        if (tx != mineX || ty != mineY) { mineX = tx; mineY = ty; mineProg = 0; }
        mineNeed = player.creative ? 1 : breakTime(d, player.held());
        mineProg++;
        player.swing = 6;
        player.facing = (tx * Cfg.TILE + 16 > player.cx()) ? 1 : -1;
        if (tick % 5 == 0) burst(tx * Cfg.TILE + 16, ty * Cfg.TILE + 16, d.color, 2);
        if (mineProg >= mineNeed) { breakBlock(tx, ty, true); mineProg = 0; mineX = -1; }
    }

    void breakBlock(int tx, int ty, boolean byPlayer) {
        int id = world.get(tx, ty);
        BlockDef d = Blocks.get(id);
        if (id == B.AIR || id == B.BEDROCK) return;
        boolean harvest = byPlayer && !player.creative && canHarvest(d, player.held());
        int above = world.get(tx, ty - 1);
        if (Blocks.get(above).plant) {
            if (!player.creative) spawnDrop(tx, ty - 1, above, 1);
            world.set(tx, ty - 1, B.AIR);
        }
        world.set(tx, ty, B.AIR);
        burst(tx * Cfg.TILE + 16, ty * Cfg.TILE + 16, d.color, 10);
        if (harvest) {
            if (d.drop > 0) spawnDrop(tx, ty, d.drop, 1);
            if (id == B.LEAVES && rnd.nextInt(14) == 0) spawnDrop(tx, ty, Items.APPLE, 1);
        }
        if (byPlayer) damageTool(1);
    }

    void damageTool(int n) {
        ItemStack h = player.held();
        if (h == null || !Items.isTool(h.id) || player.creative) return;
        h.dur -= n;
        if (h.dur <= 0) { player.inv.slots[player.selected] = null; toast("Asbob sindi!"); }
    }

    void useItem(int tx, int ty, boolean inReach) {
        Player p = player;
        ItemStack h = p.held();
        if (inReach && world.get(tx, ty) == B.TNT) { ignite(tx, ty); placeCd = 15; p.swing = 6; return; }
        if (h == null) return;
        int food = Items.food(h.id);
        if (food > 0) {
            if (p.hunger < 20) {
                p.hunger = Math.min(20, p.hunger + food);
                consumeHeld();
                placeCd = 25;
                burst(p.cx(), p.y + 10, 0xC08040, 6);
            }
            return;
        }
        if (!inReach || !Items.isBlock(h.id)) return;
        int cur = world.get(tx, ty);
        BlockDef cd = Blocks.get(cur);
        if (!(cur == B.AIR || cd.liquid || cd.plant)) return;
        BlockDef nd = Blocks.get(h.id);
        if (nd.solid) {
            double bx = tx * Cfg.TILE, by = ty * Cfg.TILE;
            if (p.x < bx + Cfg.TILE && p.x + p.w > bx && p.y < by + Cfg.TILE && p.y + p.h > by) return;
        }
        boolean support = false;
        int[][] nb = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] o : nb) {
            int n = world.get(tx + o[0], ty + o[1]);
            BlockDef nbd = Blocks.get(n);
            if (n != B.AIR && !nbd.liquid && !nbd.plant) support = true;
        }
        if (!support) return;
        if (nd.plant) {
            int below = world.get(tx, ty + 1);
            if (below != B.GRASS && below != B.DIRT && below != B.SNOW) return;
        }
        world.set(tx, ty, h.id);
        if (!p.creative) consumeHeld();
        placeCd = 6; p.swing = 6;
    }

    void consumeHeld() {
        ItemStack h = player.held();
        if (h == null) return;
        if (--h.count <= 0) player.inv.slots[player.selected] = null;
    }

    void dropHeld() {
        ItemStack h = player.held();
        if (h == null) return;
        ItemEntity e = new ItemEntity(player.cx() + player.facing * 20, player.cy() - 10, new ItemStack(h.id, 1), rnd);
        e.stack.dur = h.dur;
        e.vx = player.facing * 4; e.vy = -2; e.delay = 60;
        drops.add(e);
        consumeHeld();
    }

    // ------------------------------------------------------------- dunyo obyektlari
    void spawnDrop(int tx, int ty, int id, int n) {
        drops.add(new ItemEntity(tx * Cfg.TILE + 16, ty * Cfg.TILE + 16, new ItemStack(id, n), rnd));
    }

    void burst(double x, double y, int color, int n) {
        for (int i = 0; i < n; i++)
            parts.add(new Particle(x + rnd.nextDouble() * 8 - 4, y + rnd.nextDouble() * 8 - 4,
                    (rnd.nextDouble() - 0.5) * 5, (rnd.nextDouble() - 0.5) * 5 - 2, color, 20 + rnd.nextInt(20), 3 + rnd.nextInt(3)));
    }

    void updateParticles() {
        for (Particle p : parts) p.update();
        parts.removeIf(p -> p.life <= 0);
    }

    void ignite(int tx, int ty) { world.set(tx, ty, B.AIR); spawnTnt(tx, ty, 80); }

    void spawnTnt(int tx, int ty, int fuse) {
        PrimedTnt t = new PrimedTnt(tx * Cfg.TILE + 2, ty * Cfg.TILE + 2, fuse);
        t.vy = -3; t.vx = (rnd.nextDouble() - 0.5) * 2;
        tnts.add(t);
    }

    void updateTnt() {
        for (int i = 0; i < tnts.size(); i++) {
            PrimedTnt t = tnts.get(i);
            t.update(world);
            if (t.fuse <= 0 && !t.removed) { t.removed = true; explode(t.cx(), t.cy(), 4); }
        }
        tnts.removeIf(t -> t.removed);
    }

    void explode(double cx, double cy, int radius) {
        int tcx = (int) Math.floor(cx / Cfg.TILE), tcy = (int) Math.floor(cy / Cfg.TILE);
        for (int dy = -radius; dy <= radius; dy++) {
            for (int dx = -radius; dx <= radius; dx++) {
                if (dx * dx + dy * dy > radius * radius + 1) continue;
                int tx = tcx + dx, ty = tcy + dy, id = world.get(tx, ty);
                if (id == B.AIR || id == B.BEDROCK) continue;
                BlockDef d = Blocks.get(id);
                if (d.liquid) continue;
                world.set(tx, ty, B.AIR);
                if (id == B.TNT) { spawnTnt(tx, ty, 10 + rnd.nextInt(20)); continue; }
                if (rnd.nextInt(4) == 0 && d.drop > 0) spawnDrop(tx, ty, d.drop, 1);
                if (rnd.nextBoolean()) burst(tx * Cfg.TILE + 16, ty * Cfg.TILE + 16, d.color, 2);
            }
        }
        double rpx = (radius + 2) * Cfg.TILE;
        for (Mob m : mobs) {
            double d = Math.hypot(m.cx() - cx, m.cy() - cy);
            if (d < rpx) m.hurt((int) ((1 - d / rpx) * 18) + 1, Math.signum(m.cx() - cx) * 7, true);
        }
        double pd = Math.hypot(player.cx() - cx, player.cy() - cy);
        if (pd < rpx) damagePlayer((int) ((1 - pd / rpx) * 14) + 1, Math.signum(player.cx() - cx) * 7);
        for (int i = 0; i < 70; i++) {
            int c = rnd.nextInt(3) == 0 ? 0xFFB020 : (rnd.nextBoolean() ? 0xFF5A10 : 0x555555);
            parts.add(new Particle(cx, cy, (rnd.nextDouble() - 0.5) * 12, (rnd.nextDouble() - 0.5) * 12, c, 25 + rnd.nextInt(25), 4 + rnd.nextInt(5)));
        }
        shake = 25;
    }

    void updateMobs() {
        Iterator<Mob> it = mobs.iterator();
        while (it.hasNext()) {
            Mob m = it.next();
            m.update(this);
            if (m.health <= 0 && !m.removed) killMob(m);
            if (Math.abs(m.cx() - player.cx()) > 90 * Cfg.TILE || m.y > Cfg.H * Cfg.TILE) m.removed = true;
            if (m.removed) it.remove();
        }
    }

    void killMob(Mob m) {
        m.removed = true;
        burst(m.cx(), m.cy(), 0xBB2222, 14);
        int id, n;
        switch (m.type) {
            case ZOMBIE: id = Items.ROTTEN; n = rnd.nextInt(3); break;
            case PIG: id = Items.PORK; n = 1 + rnd.nextInt(3); break;
            default: id = Items.BEEF; n = 1 + rnd.nextInt(3); break;
        }
        if (n > 0) drops.add(new ItemEntity(m.cx(), m.cy(), new ItemStack(id, n), rnd));
        if (m.type == MobType.ZOMBIE && rnd.nextInt(12) == 0)
            drops.add(new ItemEntity(m.cx(), m.cy(), new ItemStack(Items.IRON_INGOT, 1), rnd));
    }

    void updateDrops() {
        Iterator<ItemEntity> it = drops.iterator();
        while (it.hasNext()) {
            ItemEntity e = it.next();
            e.update(this);
            if (e.removed) it.remove();
        }
    }

    void trySpawn() {
        if (tick % 45 != 0) return;
        Player p = player;
        int zc = 0, pc = 0;
        for (Mob m : mobs) { if (m.type == MobType.ZOMBIE) zc++; else pc++; }
        int ptx = (int) Math.floor(p.cx() / Cfg.TILE), pty = (int) Math.floor(p.cy() / Cfg.TILE);
        boolean night = world.skyFactor < 0.5;
        if (!night && pc < 10 && rnd.nextInt(3) == 0) {
            int tx = ptx + (rnd.nextBoolean() ? 1 : -1) * (14 + rnd.nextInt(30));
            if (tx > 2 && tx < Cfg.W - 2) {
                int sy = world.surface[tx];
                if (world.get(tx, sy) == B.GRASS && world.get(tx, sy - 1) == B.AIR && world.get(tx, sy - 2) == B.AIR) {
                    MobType t = rnd.nextBoolean() ? MobType.PIG : MobType.COW;
                    mobs.add(new Mob(t, tx * Cfg.TILE + 2, sy * Cfg.TILE - t.h - 1));
                }
            }
        }
        if (zc < 8 && rnd.nextInt(2) == 0) {
            int tx, ty;
            if (night && rnd.nextBoolean()) {
                tx = ptx + (rnd.nextBoolean() ? 1 : -1) * (14 + rnd.nextInt(26));
                ty = tx > 0 && tx < Cfg.W ? world.surface[tx] - 1 : 0;
            } else {
                tx = ptx + rnd.nextInt(61) - 30;
                ty = pty + rnd.nextInt(29) - 14;
            }
            if (tx > 2 && tx < Cfg.W - 2 && ty > 2 && ty < Cfg.H - 3 && Math.abs(tx - ptx) >= 10
                    && world.get(tx, ty) == B.AIR && world.get(tx, ty - 1) == B.AIR && world.get(tx, ty - 2) == B.AIR
                    && world.isSolid(tx, ty + 1) && world.lightAt(tx, ty) <= 7)
                mobs.add(new Mob(MobType.ZOMBIE, tx * Cfg.TILE + 3, (ty + 1) * Cfg.TILE - 57));
        }
    }

    // ------------------------------------------------------------- inventar va craft
    void openInventory() { state = ST_INV; lmb = rmb = false; scroll = 0; }

    void closeInventory() {
        if (cursor != null) {
            int left = player.inv.add(cursor.id, cursor.count, cursor.dur);
            if (left > 0) drops.add(new ItemEntity(player.cx(), player.cy(), new ItemStack(cursor.id, left), rnd));
            cursor = null;
        }
        state = ST_PLAY;
    }

    Rectangle slotRect(int i, int px, int py) {
        if (i < 9) return new Rectangle(px + 32 + i * 48, py + 70 + 3 * 48 + 16, 44, 44);
        int c = (i - 9) % 9, r = (i - 9) / 9;
        return new Rectangle(px + 32 + c * 48, py + 70 + r * 48, 44, 44);
    }

    void invClick(int mx, int my, boolean right) {
        int px = (vw() - PW) / 2, py = (vh() - PH) / 2;
        for (int i = 0; i < 36; i++) {
            if (slotRect(i, px, py).contains(mx, my)) { clickSlot(i, right); return; }
        }
        int rx = px + 488, ry = py + 70;
        if (player.creative) {
            for (int r = 0; r < 8; r++)
                for (int c = 0; c < 8; c++) {
                    int idx = (scroll + r) * 8 + c;
                    if (idx < palette.size() && new Rectangle(rx + c * 46, ry + r * 46, 44, 44).contains(mx, my)) {
                        int id = palette.get(idx);
                        cursor = new ItemStack(id, Items.maxStack(id));
                        return;
                    }
                }
        } else {
            for (int r = 0; r < 9; r++) {
                int idx = scroll + r;
                if (idx < Recipes.ALL.size() && new Rectangle(rx, ry + r * 42, 380, 40).contains(mx, my)) {
                    craft(Recipes.ALL.get(idx));
                    return;
                }
            }
        }
    }

    void clickSlot(int i, boolean right) {
        ItemStack[] sl = player.inv.slots;
        ItemStack s = sl[i];
        if (!right) {
            if (cursor == null) { cursor = s; sl[i] = null; }
            else if (s == null) { sl[i] = cursor; cursor = null; }
            else if (s.id == cursor.id && Items.maxStack(s.id) > 1) {
                int m = Math.min(Items.maxStack(s.id) - s.count, cursor.count);
                s.count += m; cursor.count -= m;
                if (cursor.count <= 0) cursor = null;
            } else { sl[i] = cursor; cursor = s; }
        } else if (cursor == null && s != null) {
            int half = (s.count + 1) / 2;
            cursor = new ItemStack(s.id, half);
            cursor.dur = s.dur;
            s.count -= half;
            if (s.count <= 0) sl[i] = null;
        } else if (cursor != null) {
            boolean placed = false;
            if (s == null) { ItemStack n = new ItemStack(cursor.id, 1); n.dur = cursor.dur; sl[i] = n; placed = true; }
            else if (s.id == cursor.id && s.count < Items.maxStack(s.id)) { s.count++; placed = true; }
            if (placed && --cursor.count <= 0) cursor = null;
        }
    }

    boolean canCraft(Recipe r) {
        for (int i = 0; i < r.in.length; i++) if (!player.inv.has(r.in[i], r.inN[i])) return false;
        return true;
    }

    void craft(Recipe r) {
        if (!canCraft(r)) return;
        for (int i = 0; i < r.in.length; i++) player.inv.remove(r.in[i], r.inN[i]);
        int left = player.inv.add(r.out, r.outN, 0);
        if (left > 0) drops.add(new ItemEntity(player.cx(), player.cy(), new ItemStack(r.out, left), rnd));
        toast("Yasaldi: " + Items.name(r.out));
    }

    // ------------------------------------------------------------- pauza menyusi
    Rectangle[] pauseRects() {
        Rectangle[] r = new Rectangle[5];
        for (int i = 0; i < 5; i++) r[i] = new Rectangle(vw() / 2 - 150, vh() / 2 - 120 + i * 56, 300, 44);
        return r;
    }

    void pauseClick(int mx, int my) {
        Rectangle[] r = pauseRects();
        for (int i = 0; i < r.length; i++) {
            if (!r[i].contains(mx, my)) continue;
            switch (i) {
                case 0: state = ST_PLAY; break;
                case 1: toast(SaveManager.save(this) ? "Dunyo saqlandi" : "Saqlashda xatolik"); state = ST_PLAY; break;
                case 2: loadGame(); break;
                case 3: newWorld(System.nanoTime()); toast("Yangi dunyo yaratildi"); break;
                default: System.exit(0);
            }
        }
    }

    void loadGame() {
        if (SaveManager.load(this)) {
            clearEntities();
            world.computeLight();
            updateCamera(true);
            state = ST_PLAY;
            toast("Dunyo yuklandi");
        } else toast("Saqlangan dunyo topilmadi");
    }

    // =====================================================================
    // CHIZISH
    // =====================================================================
    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int W = getWidth(), H = getHeight();
        frames++;
        long now = System.currentTimeMillis();
        if (now - fpsTimer >= 1000) { fps = frames; frames = 0; fpsTimer = now; }

        drawSky(g, W, H);
        double sx = shake > 0 ? (rnd.nextDouble() - 0.5) * shake * 0.5 : 0;
        double sy = shake > 0 ? (rnd.nextDouble() - 0.5) * shake * 0.5 : 0;
        AffineTransform base = g.getTransform();
        int cx = (int) Math.round(camX + sx), cy = (int) Math.round(camY + sy);
        g.translate(-cx, -cy);
        drawWorld(g, cx, cy, W, H);
        drawEntities(g);
        g.setTransform(base);

        Player p = player;
        if (world.get((int) Math.floor(p.cx() / Cfg.TILE), (int) Math.floor((p.y + 6) / Cfg.TILE)) == B.WATER) {
            g.setColor(new Color(20, 70, 200, 70)); g.fillRect(0, 0, W, H);
        }
        if (p.hurtTimer > 10) { g.setColor(new Color(200, 0, 0, (p.hurtTimer - 10) * 8)); g.fillRect(0, 0, W, H); }
        drawHud(g, W, H);
        if (showMap) drawMinimap(g, W);
        if (showDebug) drawDebug(g);
        if (state == ST_INV) drawInventory(g, W, H);
        if (state == ST_PAUSE) drawPause(g, W, H);
        if (state == ST_DEAD) drawDead(g, W, H);
    }

    void drawSky(Graphics2D g, int W, int H) {
        double t = (world.ticks % Cfg.DAY_TICKS) / (double) Cfg.DAY_TICKS;
        double ang = t * Math.PI * 2, elev = Math.sin(ang);
        int topC = Gfx.mixRgb(0x050818, 0x6EAAFF, daylight);
        int botC = Gfx.mixRgb(0x141A38, 0xBFE2FF, daylight);
        botC = Gfx.mixRgb(botC, 0xFF8A3C, Math.max(0, 1 - Math.abs(elev) / 0.3) * 0.65);
        g.setPaint(new GradientPaint(0, 0, new Color(topC), 0, H, new Color(botC)));
        g.fillRect(0, 0, W, H);
        double dy = camY - (Cfg.BASE_H * Cfg.TILE - H / 2.0);
        if (daylight < 0.7) {
            g.setColor(new Color(255, 255, 255, (int) ((1 - daylight / 0.7) * 255)));
            for (int i = 0; i < starX.length; i++) g.fillRect((int) (starX[i] * W), (int) (starY[i] * H * 0.6), 2, 2);
        }
        double hy = H * 0.55;
        if (elev > -0.15) {
            int sx = (int) (W / 2.0 - Math.cos(ang) * W * 0.42), sy = (int) (hy - Math.sin(ang) * H * 0.5);
            g.setColor(new Color(255, 230, 120, 70)); g.fillOval(sx - 52, sy - 52, 104, 104);
            g.setColor(new Color(255, 240, 150)); g.fillOval(sx - 32, sy - 32, 64, 64);
        }
        if (elev < 0.15) {
            int mx = (int) (W / 2.0 + Math.cos(ang) * W * 0.42), my = (int) (hy + Math.sin(ang) * H * 0.5);
            g.setColor(new Color(235, 240, 250)); g.fillOval(mx - 26, my - 26, 52, 52);
            g.setColor(new Color(190, 195, 210)); g.fillOval(mx - 12, my - 10, 10, 10); g.fillOval(mx + 5, my + 6, 12, 12);
        }
        for (int layer = 0; layer < 2; layer++) {
            double f = layer == 0 ? 0.12 : 0.25;
            int col = Gfx.mixRgb(0x0A0E1C, layer == 0 ? 0x7A93B8 : 0x587C94, 0.25 + 0.75 * daylight);
            double baseY = H * (layer == 0 ? 0.62 : 0.72) - dy * f;
            int n = W / 8 + 2;
            int[] xs = new int[n + 2], ys = new int[n + 2];
            for (int i = 0; i < n; i++) {
                double wx = i * 8 + camX * f;
                double hgt = mtnNoise.fbm(wx * 0.0045 + layer * 40, 3.0, 3) * (layer == 0 ? 260 : 190);
                xs[i] = i * 8; ys[i] = (int) (baseY - hgt + 60);
            }
            xs[n] = W + 8; ys[n] = H + 300; xs[n + 1] = 0; ys[n + 1] = H + 300;
            g.setColor(new Color(col)); g.fillPolygon(xs, ys, n + 2);
        }
        int ca = (int) (230 * (0.25 + 0.75 * daylight));
        g.setColor(new Color(255, 255, 255, ca));
        for (int i = 0; i < cloudX.length; i++) {
            double cxp = ((cloudX[i] + tick * 0.15 - camX * 0.35) % (W + 600) + (W + 600)) % (W + 600) - 300;
            int cyp = (int) (cloudY[i] * H * 0.4 + 20 - dy * 0.3);
            int cw = (int) cloudW[i];
            g.fillOval((int) cxp, cyp, cw, 28); g.fillOval((int) cxp + cw / 4, cyp - 12, cw / 2, 30); g.fillOval((int) cxp + cw / 2, cyp + 2, cw / 2, 24);
        }
    }

    void drawWorld(Graphics2D g, int cx, int cy, int W, int H) {
        int T = Cfg.TILE;
        int x0 = Math.max(0, cx / T - 1), x1 = Math.min(Cfg.W - 1, (cx + W) / T + 1);
        int y0 = Math.max(0, cy / T - 1), y1 = Math.min(Cfg.H - 1, (cy + H) / T + 1);
        for (int ty = y0; ty <= y1; ty++) {
            for (int tx = x0; tx <= x1; tx++) {
                int id = world.blocks[ty][tx];
                boolean under = ty >= world.surface[tx];
                int px = tx * T, py = ty * T;
                if (id == B.AIR) {
                    if (!under) continue;
                    g.setColor(CAVE_BG); g.fillRect(px, py, T, T);
                } else {
                    if (under && !Blocks.OPAQUE[id]) { g.setColor(CAVE_BG); g.fillRect(px, py, T, T); }
                    g.drawImage(tex[id], px, py, null);
                }
                int l = world.lightAt(tx, ty);
                if (l < 15) { g.setColor(SHADES[l]); g.fillRect(px, py, T, T); }
            }
        }
        if (state == ST_PLAY && hoverX >= 0 && hoverReach) {
            g.setColor(new Color(255, 255, 255, 210));
            g.setStroke(new BasicStroke(2));
            g.drawRect(hoverX * T + 1, hoverY * T + 1, T - 2, T - 2);
            g.setStroke(new BasicStroke(1));
        }
        if (mineX >= 0 && mineProg > 0) {
            double r = Math.min(1, mineProg / mineNeed);
            int px = mineX * T, py = mineY * T;
            g.setColor(new Color(0, 0, 0, (int) (r * 120))); g.fillRect(px, py, T, T);
            g.setColor(new Color(0, 0, 0, 170));
            Random cr = new Random(mineX * 7919L + mineY);
            int n = (int) (r * 9);
            for (int i = 0; i < n; i++) {
                int ax = px + cr.nextInt(T), ay = py + cr.nextInt(T);
                g.drawLine(ax, ay, ax + cr.nextInt(15) - 7, ay + cr.nextInt(15) - 7);
            }
        }
    }

    double lightOf(Entity e) {
        return Gfx.bright(world.lightAt((int) Math.floor(e.cx() / Cfg.TILE), (int) Math.floor(e.cy() / Cfg.TILE)));
    }

    void drawEntities(Graphics2D g) {
        for (ItemEntity e : drops) {
            int bob = (int) (Math.sin((tick + e.age) * 0.1) * 2);
            Icons.draw(g, tex, e.stack.id, (int) e.x - 1, (int) e.y - 1 + bob, 18);
        }
        for (PrimedTnt t : tnts) {
            g.drawImage(tex[B.TNT], (int) t.x, (int) t.y, 28, 28, null);
            if ((t.fuse / 5) % 2 == 0) { g.setColor(new Color(255, 255, 255, 170)); g.fillRect((int) t.x, (int) t.y, 28, 28); }
        }
        for (Mob m : mobs) m.draw(g, lightOf(m));
        if (state != ST_DEAD) drawPlayer(g, lightOf(player));
        for (Particle p : parts) p.draw(g);
    }

    void drawPlayer(Graphics2D g, double b) {
        Player p = player;
        int px = (int) p.x, py = (int) p.y, f = p.facing;
        boolean hurt = p.hurtTimer > 0 && (p.hurtTimer / 3) % 2 == 0;
        int leg = p.onGround || p.flying ? (int) (Math.sin(p.anim) * 5) : 4;
        Gfx.rect(g, 0x3C3C9C, b, hurt, px + 4 + leg, py + 38, 8, 16);
        Gfx.rect(g, 0x3C3C9C, b, hurt, px + 12 - leg, py + 38, 8, 16);
        Gfx.rect(g, 0x00A8A8, b, hurt, px + 4, py + 16, 16, 22);
        Gfx.rect(g, 0xF0C090, b, hurt, px + 4, py, 16, 16);
        Gfx.rect(g, 0x5A3A1A, b, hurt, px + 4, py, 16, 5);
        Gfx.rect(g, 0x222266, b, false, f > 0 ? px + 14 : px + 6, py + 7, 3, 3);
        int armUp = p.swing > 0 ? -p.swing * 2 : 0;
        Gfx.rect(g, 0xF0C090, b, hurt, f > 0 ? px + 14 : px + 2, py + 18 + armUp / 2, 6, 14);
        ItemStack h = p.held();
        if (h != null) Icons.draw(g, tex, h.id, f > 0 ? px + 14 + p.swing : px - 12 - p.swing, py + 14 + armUp, 22);
    }

    // ------------------------------------------------------------- HUD
    void drawHeart(Graphics2D g, int x, int y, int fill) {
        Path2D.Double heart = new Path2D.Double();
        heart.moveTo(x + 9, y + 16);
        heart.curveTo(x - 6, y + 5, x + 3, y - 4, x + 9, y + 3);
        heart.curveTo(x + 15, y - 4, x + 24, y + 5, x + 9, y + 16);
        heart.closePath();
        g.setColor(new Color(50, 10, 10, 200)); g.fill(heart);
        if (fill > 0) {
            Shape old = g.getClip();
            g.clip(new Rectangle(x - 8, y - 6, fill == 2 ? 40 : 17, 30));
            g.setColor(new Color(225, 30, 40)); g.fill(heart);
            g.setClip(old);
        }
        g.setColor(Color.BLACK); g.draw(heart);
    }

    void drawHunger(Graphics2D g, int x, int y, int fill) {
        g.setColor(new Color(60, 35, 10, 200)); g.fillOval(x + 2, y + 1, 14, 14);
        if (fill > 0) {
            Shape old = g.getClip();
            g.clip(new Rectangle(x, y, fill == 2 ? 20 : 9, 20));
            g.setColor(new Color(200, 120, 40)); g.fillOval(x + 2, y + 1, 14, 14);
            g.setClip(old);
        }
        g.setColor(new Color(235, 230, 215)); g.fillRect(x + 12, y + 11, 6, 3);
    }

    void drawStack(Graphics2D g, ItemStack s, int x, int y, int size) {
        if (s == null) return;
        Icons.draw(g, tex, s.id, x, y, size);
        if (s.count > 1) {
            g.setFont(new Font("SansSerif", Font.BOLD, 13));
            String c = String.valueOf(s.count);
            Gfx.text(g, c, x + size - g.getFontMetrics().stringWidth(c) + 2, y + size + 2, Color.WHITE);
        }
        if (Items.isTool(s.id) && s.dur < Items.maxDur(s.id)) {
            double r = s.dur / (double) Items.maxDur(s.id);
            g.setColor(Color.BLACK); g.fillRect(x, y + size - 3, size, 4);
            g.setColor(Color.getHSBColor((float) (r * 0.33), 1f, 1f)); g.fillRect(x, y + size - 3, (int) (size * r), 2);
        }
    }

    void drawHud(Graphics2D g, int W, int H) {
        Player p = player;
        int slot = 44, gap = 4, totalW = 9 * slot + 8 * gap;
        int hx = (W - totalW) / 2, hy = H - slot - 14;
        for (int i = 0; i < 9; i++) {
            int sx = hx + i * (slot + gap);
            g.setColor(new Color(0, 0, 0, 150)); g.fillRoundRect(sx, hy, slot, slot, 8, 8);
            boolean sel = i == p.selected;
            g.setColor(sel ? Color.WHITE : new Color(160, 160, 160, 180));
            g.setStroke(new BasicStroke(sel ? 3 : 1)); g.drawRoundRect(sx, hy, slot, slot, 8, 8); g.setStroke(new BasicStroke(1));
            drawStack(g, p.inv.slots[i], sx + 6, hy + 6, 32);
            g.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g.setColor(new Color(255, 255, 255, 140)); g.drawString(String.valueOf(i + 1), sx + 4, hy + 11);
        }
        if (!p.creative) {
            for (int i = 0; i < 10; i++) {
                int hp = p.health - i * 2;
                drawHeart(g, hx + i * 20, hy - 28, hp >= 2 ? 2 : (hp == 1 ? 1 : 0));
                int hg = p.hunger - i * 2;
                drawHunger(g, hx + totalW - 20 - i * 20, hy - 28, hg >= 2 ? 2 : (hg == 1 ? 1 : 0));
            }
            if (p.air < 300) {
                int bubbles = (int) Math.ceil(p.air / 30.0);
                g.setColor(new Color(170, 210, 255, 200));
                for (int i = 0; i < bubbles; i++) g.fillOval(hx + totalW - 20 - i * 20, hy - 50, 12, 12);
            }
        } else {
            g.setFont(new Font("SansSerif", Font.BOLD, 14));
            Gfx.text(g, "CREATIVE" + (p.flying ? " (uchish)" : ""), hx, hy - 12, new Color(255, 220, 80));
        }
        ItemStack h = p.held();
        if (h != null && nameTimer > 0) {
            g.setFont(new Font("SansSerif", Font.BOLD, 16));
            String nm = Items.name(h.id);
            int a = Math.min(255, nameTimer * 4);
            Gfx.text(g, nm, (W - g.getFontMetrics().stringWidth(nm)) / 2, hy - 56, new Color(255, 255, 255, a));
        }
        if (msgTimer > 0) {
            g.setFont(new Font("SansSerif", Font.BOLD, 18));
            Gfx.text(g, msg, (W - g.getFontMetrics().stringWidth(msg)) / 2, 80, new Color(255, 240, 160, Math.min(255, msgTimer * 4)));
        }
        if (tick < 900 && state == ST_PLAY) {
            g.setFont(new Font("SansSerif", Font.PLAIN, 13));
            String[] help = {"A/D - yurish, W - sakrash, Shift - yugurish", "Chap tugma - qazish/hujum, o'ng tugma - qo'yish/yeyish",
                    "E - inventar va craft, Q - tashlash, G - creative, M - xarita",
                    "F5 - saqlash, F9 - yuklash, F3 - debug, ESC - pauza", "TNT ustiga o'ng tugma bossangiz yonadi!"};
            for (int i = 0; i < help.length; i++) Gfx.text(g, help[i], 16, H - 110 + i * 18 - 40, new Color(255, 255, 255, Math.min(255, (900 - tick) / 2)));
        }
    }

    void drawMinimap(Graphics2D g, int W) {
        if (miniTimer-- <= 0) {
            miniTimer = 10;
            int ptx = (int) (player.cx() / Cfg.TILE), pty = (int) (player.cy() / Cfg.TILE);
            for (int my = 0; my < 70; my++)
                for (int mx = 0; mx < 120; mx++) {
                    int tx = ptx - 60 + mx, ty = pty - 35 + my, rgb;
                    if (tx < 0 || tx >= Cfg.W || ty < 0 || ty >= Cfg.H) rgb = 0x000000;
                    else {
                        int id = world.blocks[ty][tx];
                        if (id == B.AIR) rgb = ty >= world.surface[tx] ? 0x1E1A18 : 0x8EC8FF;
                        else rgb = Blocks.get(id).color;
                    }
                    minimap.setRGB(mx, my, rgb);
                }
            for (Mob m : mobs) {
                int mx = (int) (m.cx() / Cfg.TILE) - ptx + 60, my = (int) (m.cy() / Cfg.TILE) - pty + 35;
                if (mx >= 0 && mx < 120 && my >= 0 && my < 70) minimap.setRGB(mx, my, m.type == MobType.ZOMBIE ? 0x00FF00 : 0xFFFFFF);
            }
            minimap.setRGB(60, 35, 0xFF0000); minimap.setRGB(60, 34, 0xFF0000);
        }
        int x = W - 260, y = 12;
        g.setColor(new Color(0, 0, 0, 180)); g.fillRect(x - 4, y - 4, 248, 148);
        g.drawImage(minimap, x, y, 240, 140, null);
        g.setColor(Color.WHITE); g.drawRect(x - 4, y - 4, 248, 148);
    }

    void drawDebug(Graphics2D g) {
        Player p = player;
        int tx = (int) Math.floor(p.cx() / Cfg.TILE), ty = (int) Math.floor(p.cy() / Cfg.TILE);
        double t = (world.ticks % Cfg.DAY_TICKS) / (double) Cfg.DAY_TICKS;
        int minutes = (int) ((t * 24 + 6) % 24 * 60);
        String under = hoverX >= 0 ? Blocks.get(world.get(hoverX, hoverY)).name : "-";
        String[] lines = {
                "FPS: " + fps, "XY: " + tx + ", " + ty, "Biom: " + world.biomeName(tx), "Seed: " + world.seed,
                String.format("Vaqt: %02d:%02d  (kun yorug'ligi %.2f)", minutes / 60, minutes % 60, daylight),
                "Yorug'lik: " + world.lightAt(tx, ty), "Kursor ostida: " + under,
                "Moblar: " + mobs.size() + "  Buyumlar: " + drops.size() + "  TNT: " + tnts.size(),
                "Rejim: " + (p.creative ? "Creative" : "Survival")};
        g.setFont(new Font("Monospaced", Font.BOLD, 14));
        for (int i = 0; i < lines.length; i++) Gfx.text(g, lines[i], 10, 22 + i * 18, Color.WHITE);
    }

    void drawInventory(Graphics2D g, int W, int H) {
        g.setColor(new Color(0, 0, 0, 150)); g.fillRect(0, 0, W, H);
        int px = (W - PW) / 2, py = (H - PH) / 2;
        g.setColor(new Color(40, 40, 48, 245)); g.fillRoundRect(px, py, PW, PH, 16, 16);
        g.setColor(new Color(200, 200, 210)); g.drawRoundRect(px, py, PW, PH, 16, 16);
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        Gfx.text(g, "Inventar", px + 32, py + 44, Color.WHITE);
        Gfx.text(g, player.creative ? "Creative buyumlar" : "Craft retseptlari", px + 488, py + 44, Color.WHITE);
        int hoverId = -1;
        for (int i = 0; i < 36; i++) {
            Rectangle r = slotRect(i, px, py);
            g.setColor(new Color(70, 70, 80)); g.fillRect(r.x, r.y, r.width, r.height);
            g.setColor(new Color(150, 150, 160)); g.drawRect(r.x, r.y, r.width, r.height);
            drawStack(g, player.inv.slots[i], r.x + 6, r.y + 6, 32);
            if (r.contains(mouseX, mouseY)) {
                g.setColor(new Color(255, 255, 255, 60)); g.fillRect(r.x, r.y, r.width, r.height);
                if (player.inv.slots[i] != null) hoverId = player.inv.slots[i].id;
            }
        }
        int rx = px + 488, ry = py + 70;
        g.setFont(new Font("SansSerif", Font.BOLD, 14));
        if (player.creative) {
            for (int r = 0; r < 8; r++)
                for (int c = 0; c < 8; c++) {
                    int idx = (scroll + r) * 8 + c;
                    if (idx >= palette.size()) continue;
                    Rectangle rc = new Rectangle(rx + c * 46, ry + r * 46, 44, 44);
                    g.setColor(new Color(70, 70, 80)); g.fillRect(rc.x, rc.y, rc.width, rc.height);
                    g.setColor(new Color(150, 150, 160)); g.drawRect(rc.x, rc.y, rc.width, rc.height);
                    Icons.draw(g, tex, palette.get(idx), rc.x + 6, rc.y + 6, 32);
                    if (rc.contains(mouseX, mouseY)) { hoverId = palette.get(idx); g.setColor(new Color(255, 255, 255, 60)); g.fillRect(rc.x, rc.y, rc.width, rc.height); }
                }
        } else {
            for (int r = 0; r < 9; r++) {
                int idx = scroll + r;
                if (idx >= Recipes.ALL.size()) break;
                Recipe rc = Recipes.ALL.get(idx);
                Rectangle row = new Rectangle(rx, ry + r * 42, 380, 40);
                boolean ok = canCraft(rc);
                g.setColor(ok ? new Color(50, 110, 55, 230) : new Color(60, 60, 68, 230)); g.fillRoundRect(row.x, row.y, row.width, row.height, 8, 8);
                if (row.contains(mouseX, mouseY)) { g.setColor(new Color(255, 255, 255, 50)); g.fillRoundRect(row.x, row.y, row.width, row.height, 8, 8); hoverId = rc.out; }
                Icons.draw(g, tex, rc.out, row.x + 6, row.y + 4, 32);
                String nm = Items.name(rc.out) + (rc.outN > 1 ? " x" + rc.outN : "");
                g.setFont(new Font("SansSerif", Font.BOLD, 13));
                Gfx.text(g, nm, row.x + 46, row.y + 25, ok ? Color.WHITE : new Color(170, 170, 175));
                for (int i = 0; i < rc.in.length; i++) {
                    int ix = row.x + 250 + i * 60;
                    Icons.draw(g, tex, rc.in[i], ix, row.y + 8, 24);
                    boolean have = player.inv.has(rc.in[i], rc.inN[i]);
                    Gfx.text(g, "x" + rc.inN[i], ix + 26, row.y + 26, have ? new Color(180, 255, 180) : new Color(255, 150, 150));
                }
            }
        }
        if (cursor != null) drawStack(g, cursor, mouseX - 16, mouseY - 16, 32);
        else if (hoverId > 0) {
            String nm = Items.name(hoverId);
            g.setFont(new Font("SansSerif", Font.BOLD, 14));
            int tw = g.getFontMetrics().stringWidth(nm);
            g.setColor(new Color(20, 10, 40, 235)); g.fillRoundRect(mouseX + 14, mouseY - 6, tw + 14, 26, 8, 8);
            Gfx.text(g, nm, mouseX + 21, mouseY + 12, Color.WHITE);
        }
    }

    void drawPause(Graphics2D g, int W, int H) {
        g.setColor(new Color(0, 0, 0, 170)); g.fillRect(0, 0, W, H);
        g.setFont(new Font("SansSerif", Font.BOLD, 40));
        String t = "PAUZA";
        Gfx.text(g, t, (W - g.getFontMetrics().stringWidth(t)) / 2, H / 2 - 150, Color.WHITE);
        String[] labels = {"Davom etish", "Saqlash (F5)", "Yuklash (F9)", "Yangi dunyo", "Chiqish"};
        Rectangle[] rs = pauseRects();
        g.setFont(new Font("SansSerif", Font.BOLD, 18));
        for (int i = 0; i < rs.length; i++) {
            boolean hov = rs[i].contains(mouseX, mouseY);
            g.setColor(hov ? new Color(110, 110, 130) : new Color(70, 70, 85)); g.fillRoundRect(rs[i].x, rs[i].y, rs[i].width, rs[i].height, 12, 12);
            g.setColor(Color.WHITE); g.drawRoundRect(rs[i].x, rs[i].y, rs[i].width, rs[i].height, 12, 12);
            g.drawString(labels[i], rs[i].x + (rs[i].width - g.getFontMetrics().stringWidth(labels[i])) / 2, rs[i].y + 28);
        }
    }

    void drawDead(Graphics2D g, int W, int H) {
        g.setColor(new Color(120, 0, 0, 150)); g.fillRect(0, 0, W, H);
        g.setFont(new Font("SansSerif", Font.BOLD, 56));
        String t = "Siz halok bo'ldingiz!";
        Gfx.text(g, t, (W - g.getFontMetrics().stringWidth(t)) / 2, H / 2 - 10, Color.WHITE);
        g.setFont(new Font("SansSerif", Font.PLAIN, 20));
        String s = "Qayta tirilish uchun R tugmasini yoki sichqonchani bosing";
        Gfx.text(g, s, (W - g.getFontMetrics().stringWidth(s)) / 2, H / 2 + 36, Color.WHITE);
    }

    // =====================================================================
    // KIRITISH (KEYBOARD / MOUSE)
    // =====================================================================
    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        if (c >= 0 && c < keys.length) keys[c] = true;
        Player p = player;
        if (c == KeyEvent.VK_ESCAPE) {
            if (state == ST_PLAY) state = ST_PAUSE;
            else if (state == ST_PAUSE) state = ST_PLAY;
            else if (state == ST_INV) closeInventory();
            lmb = rmb = false;
        } else if (c == KeyEvent.VK_E) {
            if (state == ST_PLAY) openInventory(); else if (state == ST_INV) closeInventory();
        } else if (c >= KeyEvent.VK_1 && c <= KeyEvent.VK_9 && state != ST_PAUSE) {
            p.selected = c - KeyEvent.VK_1;
        } else if (c == KeyEvent.VK_Q && state == ST_PLAY) {
            dropHeld();
        } else if (c == KeyEvent.VK_F3) {
            showDebug = !showDebug;
        } else if (c == KeyEvent.VK_M) {
            showMap = !showMap;
        } else if (c == KeyEvent.VK_F5) {
            toast(SaveManager.save(this) ? "Dunyo saqlandi" : "Saqlashda xatolik");
        } else if (c == KeyEvent.VK_F9) {
            loadGame();
        } else if (c == KeyEvent.VK_G && state == ST_PLAY) {
            p.creative = !p.creative;
            p.flying = p.creative;
            p.health = p.maxHealth; p.hunger = 20;
            toast(p.creative ? "Creative rejim yoqildi" : "Survival rejim yoqildi");
        } else if (c == KeyEvent.VK_F && p.creative && state == ST_PLAY) {
            p.flying = !p.flying;
            toast(p.flying ? "Uchish yoqildi" : "Uchish o'chirildi");
        } else if (c == KeyEvent.VK_T && p.creative && state == ST_PLAY) {
            world.ticks += Cfg.DAY_TICKS / 4;
            toast("Vaqt o'tkazildi");
        } else if (c == KeyEvent.VK_R && state == ST_DEAD) {
            respawnPlayer();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int c = e.getKeyCode();
        if (c >= 0 && c < keys.length) keys[c] = false;
    }

    @Override public void keyTyped(KeyEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        mouseX = e.getX(); mouseY = e.getY();
        boolean right = SwingUtilities.isRightMouseButton(e);
        if (state == ST_PLAY) { if (right) rmb = true; else lmb = true; }
        else if (state == ST_INV) invClick(mouseX, mouseY, right);
        else if (state == ST_PAUSE) pauseClick(mouseX, mouseY);
        else if (state == ST_DEAD) respawnPlayer();
        requestFocusInWindow();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) rmb = false; else lmb = false;
    }

    @Override public void mouseMoved(MouseEvent e) { mouseX = e.getX(); mouseY = e.getY(); }
    @Override public void mouseDragged(MouseEvent e) { mouseX = e.getX(); mouseY = e.getY(); }
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int r = e.getWheelRotation();
        if (state == ST_PLAY) {
            player.selected = ((player.selected + r) % 9 + 9) % 9;
        } else if (state == ST_INV) {
            int max = player.creative ? Math.max(0, (palette.size() + 7) / 8 - 8) : Math.max(0, Recipes.ALL.size() - 9);
            scroll = Math.max(0, Math.min(max, scroll + r));
        }
    }
}