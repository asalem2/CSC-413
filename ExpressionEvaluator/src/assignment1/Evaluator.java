/**
 * Created on Febuary 4, 2015
 * @author Eric Gonzalez
 */
package assignment1;

import java.util.*;
/**
 * Evaluator class that calculates results
 * @author Eric Gonzalez
 *
 */
public class Evaluator {

    private Stack<Operand> opdStack;
    private Stack<Operator> oprStack;

    /**
     * Constructor, that creates an Operand and Operator stack
     */
    public Evaluator() {
        opdStack = new Stack<Operand>();
        oprStack = new Stack<Operator>();
    } // end constructor

    /**
     * @param expr expression to calculate
     * @return calculated value
     * */
    public int eval(String expr) {
    	opdStack.clear();
    	oprStack.clear();
        String tok;
        expr = expr + "!";

        oprStack.push(new PoundOperator());

        String delimiters = "+-*/#! ";
        StringTokenizer st = new StringTokenizer(expr, delimiters, true);

        while (st.hasMoreTokens()){
            tok = st.nextToken();
            if (!tok.equals(" ")) {
                if (Operand.check(tok)) {
                    opdStack.push(new Operand(tok));
                } 
                else {
                    if (!Operator.check(tok)) {
                        System.out.println("*****invalid token******\n");
                        System.exit(1);
                    } // end if


                    Operator newOpr = (Operator) Operator.operators.get(tok);
                    
                    // check to see if the operator on the stakc has highest
                    //priority than the newOpr
                    if (newOpr.priority() <= oprStack.peek().priority()) {
                        while (newOpr.priority() <= oprStack.peek().priority() && opdStack.size() > 1) {
                            Operator stackOperator = oprStack.pop(); // Stack operator popped
                            Operand op1 = opdStack.pop(); // pop operand 1
                            Operand op2 = opdStack.pop(); // pop operand 2

                            opdStack.push(stackOperator.execute(op1, op2));
                        }
                        oprStack.push(newOpr);
                    } 
                    else {
                        oprStack.push(newOpr);
                    }
                } 
            }
           
        } 
        return opdStack.peek().getValue();
    }
} // end Evaluator

/**
 * Operator class, an abstract superclass for each individual operator 
 * @author Eric
 */
abstract class Operator {

    static HashMap operators = new HashMap();
    static{
        operators.put("#", new PoundOperator());
        operators.put("!", new ExclamationOperator());
        operators.put("+", new AdditionOperator());
        operators.put("-", new SubtractionOperator());
        operators.put("*", new MultiplicationOperator());
        operators.put("/", new DivisionOperator());
    }
    
    /**Class to return operators priority
     * @return priority returns the priority of a scanned operator
     */
    public abstract int priority();

    /**
     * Class to check is given token is a valid operator
     * @param tok Token to be checked if its a valid operator by searching for the correspending key in the hashmap
     * @return boolean Return true if key is found otherwise it returns false
     */
    static boolean check(String tok) {
        return operators.containsKey(tok);
    } // end check

    /**
     * Class that calls on the operators respected excecute method to calculate result
     * @param opd1 Operand 1
     * @param opd2 Operand 2
     * @return result Returns the operands calculated result
     */
    public abstract Operand execute(Operand opd1, Operand opd2);

} // end Operator

/**
 * Operand class
 * @author Eric
 *
 */
class Operand {

    private int opdValue;

    /**
     * Class to make a given tok into an integer
     * @param tok Makes the token an integer so it can be calculated
     */
    Operand(String tok) {
        opdValue = Integer.parseInt(tok);
    }

    /**
     * Assigns value to opdValue
     * @param value Integer that is passed to the method
     */
    Operand(int value) {
        opdValue = value;
    }

    /**
     * Check to see if given token is a valid operand
     * @param tok Token we are checking
     * @return boolean Return true if given token is an operand else it returns false
     */
    static boolean check(String tok) {
        try {
            Integer.parseInt(tok);
        } catch (NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    } // and check

    /**
     * Class that return the value of opdValue
     * @return opdValue The value of opdValue
     */
    int getValue() {
        return opdValue;
    } // end if

} // end Operand

/**
 * PoundOperator Class
 * @author Eric
 *
 */
class PoundOperator extends Operator {

    @Override
    public int priority() {
        return 0;
    } // end priority

    @Override
    public Operand execute(Operand opd1, Operand opd2) {
        return null;
    } // end execute
} // end PoundOperator

/**
 * ExclamationOperator Class
 * @author Eric
 *
 */
class ExclamationOperator extends Operator {

    @Override
    public int priority() {
        return 1;
    } // end priority

    @Override
    public Operand execute(Operand opd1, Operand opd2) {
        return null;
    } // end execute
} // end ExclamationOperator

/**
 * AdditionOperator Class
 * @author Eric
 *
 */
class AdditionOperator extends Operator {

    @Override
    public int priority() {
        return 2;
    } // end priority

    @Override
    public Operand execute(Operand opd1, Operand opd2) {
        Operand result = new Operand(opd1.getValue() + opd2.getValue());

        return result;
    } // end execute
} // end AdditionOperator

/**
 * SubtractionOperator Class
 * @author Eric
 *
 */
class SubtractionOperator extends Operator {

    @Override
    public int priority() {
        return 2;
    } // end priority

    @Override
    public Operand execute(Operand opd1, Operand opd2) {
        Operand result = new Operand(opd2.getValue() - opd1.getValue());

        return result;
    } // end execute
} // end SubtractionOperator

/**
 * MultiplicationOperator Class
 * @author Eric
 *
 */
class MultiplicationOperator extends Operator {

    @Override
    public int priority() {
        return 3;
    } // end priority

    @Override
    public Operand execute(Operand opd1, Operand opd2) {
        Operand result = new Operand(opd1.getValue() * opd2.getValue());

        return result;
    } // end execute
} // end MultiplicationOperator

/**
 * DivisionOperator Class
 * @author Eric
 *
 */
class DivisionOperator extends Operator {

    @Override
    public int priority() {
        return 3;
    } // end priority

    @Override
    public Operand execute(Operand opd1, Operand opd2) {
        Operand result = new Operand(opd2.getValue() / opd1.getValue());

        return result;
    } // end execute
} // end DivisionOperator