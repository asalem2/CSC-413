# Project 2 Modify the Lexer  & Parser Project

1. Use a command line argument for the input file for processing tokens – do not use simple.x

2. Recognize the new tokens: float <float> (floating literals conform to: d+.d* or d*.d+ that is,
either 1 or more digits followed by a dot and optionally followed by 0 or more digits or
0 or more digits followed by a dot followed by 1 or more digits). Implement a "greedy" policy -
that is, when you are recognizing a float try to grab as many characters as possible.
Thus, in 2.7.1 you can grab the 2.7 for the first float. That only leaves .1 for the second float...

3. Recognize the new tokens: scientificN <sciene+tificN> (floating literals don’t be represented by
normalized scientific notation such as -2.12E3, 3.45e5, -3.12e-3, 123.6e+3 are all valid ones.)

4. Recognize the new tokens (three more operators): “>” (greater), and “>=” (greaterEqual), “’”
(singleQuote)

5. Recognize a new token char <char> (literals conform to one alphabet char)
Be sure to change token file appropriately and then run TokenSetup as required

6. Include line number information within tokens for subsequent error reporting, etc.

7. Print each token with line number

      READLINE: program { int i int j
      
      program left: 0 right: 6 line: 1
      
      { left: 8 right: 8: line: 1
      
      int left: 10 right: 12 line: 1
      
      i left: 14 right: 14 line: 1
      
      int left: 16 right: 18 line: 1
      
      j left: 20 right: 20 line: 1
      .
      .
    
8. Output the source program with line numbers (since SourceReader reads the source lines it should
save the program in memory for printout after the tokens are scanned):

    e.g.
    1. program { int i int j
    2. i = 2
    3. j = 3
    4. i = write(j+4)
    5. }
    
If you encounter an error, e.g. you find a "%" on line 7 of the source file that contains 20 lines,
then you should:

1) report the error

2) stop processing tokens at that point

3) echo the lines of the source file with line numbers up to and including the error line - e.g.,
echo lines 1 through 7 inclusive in the case with the "%" on line 7 

4) exit

Comments on this lab..
* The main method in Lexer.java uses a specific file (e.g. simple.x) for processing
tokens. You are required to modify the code to use a command line argument for
the file for processing tokens.

*  TokenSetup1 should NOT be changed

*  The tokens file should be changed appropriately

*  Use the main method in Lexer.java for testing - DO NOT use other packages in
the compiler (e.g. Compiler.java) since there will always be complaints due to
not recognizing the new constructs

*  I will post your test case just prior to the due date.

*  REMOVE ALL DEBUG statements not required
Sample expected output for the case with no errors:

    READLINE: program { int i int j
    program left: 0 right: 6 line: 1
    { left: 8 right: 8: line: 1
    int left: 10 right: 12 line: 1
    i left: 14 right: 14 line: 1
    int left: 16 right: 18 line: 1
    j left: 20 right: 20 line: 1
    .
    .
    1. program { int i int j
    2. i = 2
    3. j = 3
    4. i = write(j+4)
    5. }
    

Notes:

Q. How do I add new token types to Lexer?
In order for the Lexer to recognize a new token type, the tokens file must be modified,
followed by running the main method in TokenSetup. TokenSetup reads the tokens file
and creates the two source files: TokenType.java and Sym.java. The tokens file is located
in the following package: lexer/setup.
 
Q. Does TokenSetup work on Mac/UNIX?
Mac/UNIX uses different file separators than Windows, so make sure that the following
part in the TokenSetup.java file to get it to work properly (for all platforms):

    TokenSetup() {
     try {
     String sep = System.getProperty("file.separator");
     in = new BufferedReader( new
    FileReader("lexer"+sep+"setup"+sep+"tokens"));
     table = new PrintWriter(new
    FileOutputStream("lexer"+sep+"TokenType.java"));
     symbols = new PrintWriter(new
    FileOutputStream("lexer"+sep+"Sym.java"));
     } catch (Exception e) {}
    }
    

Q. Should Token include the line number?
Yes. Modify the Token class to include a line number.

Q. How does the greedy policy work when scanning floats?
The greedy policy works by stopping only after all valid characters have been read in by
a float. That is, once it is determined that you are reading a float, don't stop until you are
completely done reading it. Remember that floats are defined as:

    d+.d* or d*.d+

Some examples of valid floats interpreted by the greedy policy:

*  1..2 valid 1. and .2

*  1...2 invalid 1. is ok, but the . (dot) without a digit preceding or following is
invalid

* 1.2.34 valid 1.2 and .34

* abc.3 valid abcand .3

* abc3. invalid abc3is a valid identifier, however the . (dot) without a digit preceding
or following is invalid

Q. Tell me more about floats and Floats
There are 2 int tokens - 1 for the int type and the other for the int literal.... the same
situation exists for floats - one token for the type and one token for the literal
    Float x = 1.2.5.3
    Should yield the following tokens:
    Float
    x
    =
    1.2
    .5
    .3

The lexer doesn't care what x is. it's the parser's job to figure out that .5 and .3 don't
belong at the end of the assignment operation "x=1.2".
We aren't working on the parser yet, so for now just pass the tokens on and we'll deal
with the error later. Actually, to digress a bit, conceivably a grammar could be defined
where 1.2.5.3 would mean 1.2 * .5 * .3.

That is, if no operator is found between two values, multiplication is implied. you do this
all the time in your calc class, right?

i.e. "5x+2y=z". why not enable the same rules in a programming language?

Actually, there are lots of reasons, but you get the idea.
For this lab you will only need to add new tokens (change tokens file) and modify
Lexer.java You'll have to modify my code in Lexer to recognize floats.

1 You should avoid changing the file path in TokenSetup by doing the following:

1. Right click on the project in Netbeans and select "Properties" in the drop-down menu

2. Select the "run" option

3. Set the "Working Directory" to the location of the src directory on your system
Now, the relative paths are correct and there is no need to change TokenSetup.java

Modify the Parser in the compiler package given for assignment 2 according to the notes below:
Parse the new rules specified in the modified grammar and build the indicated AST's (10 points) and
then print in text mode (2 points) and in graphics mode (8 points). Explanation and code skeleton for
printing tree in graphics mode will be provided during lecture.

    PROGRAM -> ‘program’ BLOCK ==> program
    BLOCK -> ‘{‘ D* S* ‘}’ ==> block
    D -> TYPE NAME ==> decl
     -> TYPE NAME FUNHEAD BLOCK ==> functionDecl
    TYPE -> ‘int’
     -> ‘boolean’
     -> ‘float’ <-------------NEW RULE
     -> ‘char’ <-------------NEW RULE
    FUNHEAD -> '(' (D list ',')? ')' ==> formals
    S -> ‘if’ E ‘then’ BLOCK ‘else’ BLOCK ==> if
    -> ‘if’ E ‘then’ BLOCK ==> if <-------------NEW RULE
    -> ‘while’ E BLOCK ==> while
    -> ‘return’ E ==> return
    -> BLOCK
    -> NAME ‘=’ E ==> assign
    -> ‘do’ BLOCK ‘while’ E <---NEW RULE
    E -> SE
    -> SE ‘==’ SE ==> =
    -> SE ‘!=’ SE => !=
    -> SE ‘<’ SE ==> <
    -> SE ‘<=’ SE ==> <=
    SE -> T
    -> SE ‘+’ T ==> +
    -> SE ‘-‘ T ==> -
    -> SE ‘|’ T ==> or
    T -> F
    -> T ‘*’ F ==> *
    -> T ‘/’ F ==> /
    -> T ‘&’ F ==> and
    F -> ‘(‘ E ‘)’
    -> NAME
    -> <int>
    -> NAME '(' (E list ',')? ')' ==> call
    -> <float> <-------------NEW RULE
    -> <char> <-------------NEW RULE
    -> <scientificN> <-------------NEW RULE
    -> ‘!’ E <------------ NEW RULE
    -> ‘-‘ E <------------ NEW RULE
    NAME -> <id>
    
Following demonstrates a test case (note that the token printout is no longer included):
    
    program {
    do {
    float f
    f = 1.2
    } while b
    }
    ---------------AST-------------
    Program
    Block
    do
    Block
    Decl
    FloatType
    Id: f
    Assign
    Id: f
    float: 1.2
    Id: b
    -----------------------------------------------------------------

Since you will not be using constrainer or codegen you must:
* Comment out the code, as indicated in the Compiler.java class.

* Comment out the constrainer/codegen import statements in Compiler.java

* When you build a project DO NOT include constrainer/codegen files

More notes:

Q. What kind of test cases should be used to test the Parser?
You should try to test for every conceivable possibility within the language. Of course, you cannot test
for every possible X language program, but try to make sure you test for as many scenarios as you can
think of, including things that you are sure will not work, and things that you may not be sure about.
There is probably a reason why this lab is worth more points than the previous one, given that it
probably took you less time to make the modifications required.

Q. Do any additional Lexer modifications need to be made?
Yes. You will have to make some changes to the lexer to handle the new reserved words.

Q. What main method should be run?
Use the main method in Compiler.java to test and produce output for this project. Follow the
instructions in the main method of Compiler.java and comment out (delete) the appropriate parts so
that it will run. If you don't comment the right things out, the other components called by the compiler
(constrainer, for example) will fail because they have not been modified to handle the changes that we
have made to the language.
