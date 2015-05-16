# Project 1. Expression Evaluator

A. Expression Evaluator

Write a Java program with a test harness class EvaluatorTester which calls on Evaluator for evaluating simple
expressions. You can use the following definition of the test harness (note the command line arguments are a series
of expressions, separated by spaces, to be evaluated by Evaluator):
    
    public class EvaluatorTester {
      public static void main(String[] args) {
        Evaluator anEvaluator = new Evaluator();
        for (String arg : args) {
          System.out.println(arg + " = " + anEvaluator.eval(arg));
        }
      }
    }
    
I have provided an almost correct version of the Evaluator class (Evaluator.java). You should program the utility
classes it uses - Operand and Operator - and then follow my suggestion on how to correct the Evaluator class. Note
that the expressions are composed of integer operands and operators drawn from +, -, * and /

Following is the almost correct version of Evaluator source code:

    import java.util.*;
    public class Evaluator {
     private Stack<Operand> opdStack;
     private Stack<Operator> oprStack;
     public Evaluator() {
     opdStack = new Stack<Operand>();
     oprStack = new Stack<Operator>();
     }
     public int eval(String expr) {
     String tok;
    // init stack - necessary with operator priority schema;
    // the priority of any operator in the operator stack other then
    // the usual operators - "+-*/" - should be less than the priority
    // of the usual operators
     oprStack.push(new Operator("#"));
    
     delimiters = "+-*/#! ";
     StringTokenizer st = new StringTokenizer(expr,delimiters,true);
    // the 3rd arg is true to indicate to use the delimiters as tokens, too
    // but we'll filter out spaces
     while (st.hasMoreTokens()) {
     if ( !(tok = st.nextToken()).equals(" ")) { // filter out spaces
     if (Operand.check(tok)) { // check if tok is an operand
     opdStack.push(new Operand(tok));
     } else {
     if (!Operator.check(tok)) {
     System.out.println("*****invalid token******");
     System.exit(1);
     }
     Operator newOpr = new Operator(tok); // POINT 1
     while ( ((Operator)oprStack.peek()).priority() >= newOpr.priority()) {
    // note that when we eval the expression 1 - 2 we will
    // push the 1 then the 2 and then do the subtraction operation
    // This means that the first number to be popped is the
    // second operand, not the first operand - see the following code
     Operator oldOpr = ((Operator)oprStack.pop());
     Operand op2 = (Operand)opdStack.pop();
     Operand op1 = (Operand)opdStack.pop();
     opdStack.push(oldOpr.execute(op1,op2));
     }
     oprStack.push(newOpr);
     }
     }
    // Control gets here when we've picked up all of the tokens; you must add
    // code to complete the evaluation - consider how the code given here
    // will evaluate the expression 1+2*3
    // When we have no more tokens to scan, the operand stack will contain 1 2
    // and the operator stack will have + * with 2 and * on the top;
    // In order to complete the evaluation we must empty the stacks (except
    // the init operator on the operator stack); that is, we should keep
    // evaluating the operator stack until it only contains the init operator;
    // Suggestion: create a method that takes an operator as argument and
    // then executes the while loop; also, move the stacks out of the main
    // method
     }
     }
    }
    
The algorithm processes operand and operator tokens. If an operand token is scanned then it is immediately pushed
on the operand stack. If a new operator token is scanned then its priority is compared to the priority of the operator
on top of the operator stack. As long as the priority of the new operator token is smaller than the priority of the
operator on the stack the following occurs:

1. The stack operator is popped

2. It's operands are popped from the operand stack

3. The operation is performed and the result is pushed onto the operand stack

We use two bogus operators new Operator("#") and new Operator(“!”) to avoid numerous checks for empty
operator stack. I have described above how to use “#” to assist in the algorithm. Consider how to use “!” as an
operator placed at the end of the input stream to facilitate the logic (recall, the use of “!” was described in lecture.

Use the following operator priorities:

    Operator Priority
    # 0
    ! 1
    + - 2
    * / 3
    
Hints:

Examine the Java documentation on the String and Stack classes.
Use the Netbeans debugger - it will provide a lot of help in this and other problems!

Include the following methods for the indicated classes:

* Operator

* abstract int priority()

* boolean check(String tok)

* abstract Operand execute(Operand opd1, Operand opd2)

* Operand

* Operand(String tok)

* Operand(int value)

* boolean check(String tok)

* int getValue()

Note: Make operator an abstract superclass; use one class per operator; init a HashMap with all of the operators
and their instances. You are required to create/use this operator hierarchy for this exercise.
Don't worry about poorly formed expressions - that is, no need to check for those cases (assume the input is correct).
You may assume that we are using integer division – truncate the result.
Following is some pseudocode that provides some ideas on solving the problem. Operator
should be an abstract class, with method declarations and members like these:

    abstract class Operator {
     public abstract Operand execute();
     .....
    }

This class is then extended to the various operators-- Addition, Subtraction etc.

    class AdditionOperator extends Operator{
     public Operand execute(Operand op1, Operand op2){
     //do an addition, return the sum
     }
     ...
    }

The Operator class should contain an instance of a HashMap. This map will use as keys the
tokens we're interested in, and values will be instances of the Operators. ex:

* HashMap operators = new HashMap();

* operators.put("+",new AdditionOperator());

* operators.put("-",new SubtractionOperator());

* etc....

Next, the Operator class will make use of the HashMap. Whenever we need a new operator we
will access the HashMap. Because the Operator class is abstract, it can't be instantiated. So
instead of
    
    Operator newOpr= new Operator(tok);

you might do this:

    Operator newOpr = (Operator) operators.get(tok);

newOpr now "knows" what type of operator it is, and can act accordingly when the execute()
method (or other relevant methods) is called on it.

ex:
    
    String tok="+";
    Operator newOpr = (Operator) operators.get(tok); //newOpr is an
    AdditionOperator
    opdStack.push(newOpr.execute(op1,op2)); //addition taking place

Notes:

* You are required to create an abstract Operator class with appropriate abstract methods. Create a concrete
subclass for each operator type that extends the Operator class. THIS IS NOT OPTIONAL.

* There are several ways to check if a token is a valid integer. One method is to use the method
Integer.parseInt() which throws an exception if the string is not a valid integer. Another possibility is to use
regular expression matching.

* If you use a hashmap and initialize it with one entry per operator, you can check if the hashmap contains
the token. If the hashmap does not contain the token then it is not a valid operator.

* hasMoreTokens() is used to ensure that before we try to get the next token (String) we
actually have one available.

* nextToken() is used to obtain the next token (String); note we should always check that there
are more items remaining prior to calling on nextToken() else we can have problems

* You should only use 2 files, one file for the test harness (EvaluatorTester) and the other file to
contain all other classes (Evaluator, etc.).
