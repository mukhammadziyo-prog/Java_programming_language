
Finding a Character in a String
The indexOf() method returns the index (the position) of the first occurrence of a specified text in a string (including whitespace):

Example
String txt = "Please locate where 'locate' occurs!";
System.out.println(txt.indexOf("locate")); // Outputs 7



Java counts positions from zero.
0 is the first position in a string, 1 is the second, 2 is the third ...

You can use the charAt() method to access a character at a specific position in a string:

Example
String txt = "Hello";
System.out.println(txt.charAt(0));  // H
System.out.println(txt.charAt(4));  // o
