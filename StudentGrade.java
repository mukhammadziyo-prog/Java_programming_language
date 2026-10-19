/*

 "Imtihondan oʻtish" (Murakkabroq daraja)

Shart: Talaba imtihondan muvaffaqiyatli oʻtishi uchun quyidagi shartlardan biri bajarilishi kerak:
• Asosiy yoʻl: Matematika (mathScore) VA Fizika (physicsScore) fanlarining har biridan kamida 60 ball toʻplashi kerak.
• Imtiyozli yoʻl: Agar talabada maxsus vaucher boʻlsa (hasVoucher = true), u holda fanlarning kamida bittasidan 50 yoki undan yuqori ball olsa kifoya.


*/


public class StudentGrade { 
    public static void main(String[]args) {

        int mathScore = 44;
        int physicsScore = 50;
        boolean hasVaucher = true;

        boolean finalResult = (mathScore >= 60 && physicsScore >= 60) || (hasVaucher && (mathScore >= 50 || physicsScore >= 50));

        System.out.println("Is student passed? "+ finalResult);
    }
}
