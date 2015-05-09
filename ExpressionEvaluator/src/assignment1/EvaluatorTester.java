/**
 * Created on Febuary 4, 2015
 * @author Eric Gonzalez
 */
package assignment1;
/**
 * Class to test the evaluator class
 * @author Eric Gonzalez
 *
 */
public class EvaluatorTester {
	public static void main(String[] args) {
        Evaluator anEvaluator = new Evaluator();
        for (String arg : args) {
        	System.out.println(arg + " = " + anEvaluator.eval(arg));
        }
    }
}
