Java Logical Operators in Conditions

Logical Operators in Conditions
You can combine or reverse conditions using logical operators. These work together with if, else, and else if to build more complex decisions.

&& (AND) - all conditions must be true
|| (OR) - at least one condition must be true
! (NOT) - reverses a condition (true = false, false = true)
AND (&&)
Use AND (&&) when both conditions must be true:

ExampleGet your own Java Server
Test if a is greater than b, and if c is greater than a:

int a = 200;
int b = 33;
int c = 500;

if (a > b && c > a) {
  System.out.println("Both conditions are true");
}

OR (||)
Use OR (||) when at least one of the conditions can be true:

Example
Test if a is greater than b, or if a is greater than c:

int a = 200;
int b = 33;
int c = 500;

if (a > b || a > c) {
  System.out.println("At least one condition is true");
}

NOT (!)
Use NOT (!) to reverse a condition:

Example
Test if a is not greater than b:

int a = 33;
int b = 200;

if (!(a > b)) {
  System.out.println("a is NOT greater than b");
}
