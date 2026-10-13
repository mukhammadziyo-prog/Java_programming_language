Strings - Special Characters
Because strings must be written within quotes, Java will misunderstand this string, and generate an error:
String txt = "We are the so-called "Vikings" from the north.";

The solution to avoid this problem, is to use the backslash escape character.

        The backslash (\) escape character turns special characters into string characters:

Escape character	Result	Description
\'	                '	    Single quote
\"	                "	    Double quote
\\	                \	    Backslash

The sequence \"  inserts a double quote in a string:

String txt = "We are the so-called \"Vikings\" from the north.";