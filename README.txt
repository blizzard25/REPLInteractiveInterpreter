Interactive interpreter written in Java

Interactive interpreter that stores input state until the end of the user input.

Variables are either integers or doubles. Variables can be initialized using an expressions such as 'x = 11'. Expressions can be evaluated using initialized variables such as 'y = x + 5'. Single sets of parentheses are supported. Nested parentheses are NOT supported.

Supported operators are ‘=‘, ‘+’, ‘-‘, ‘*’, ‘/‘, and ’%’

HOW TO USE:

Enter all code into the console. Pressing ‘ENTER’ will end the current line of code. To print all current variables and their values, use the command ‘Console.printAllVars’. To end the program, use the command ‘END’.

EXAMPLE:
x = 10
y = 5 * x
z = y / 2
Console.printAllVars
x = 10.0 y = 50.0 z = 25.0
v = z % 2
Console.printAllVars
v = 1.0 x = 10.0 y = 50.0 z = 25.0
END