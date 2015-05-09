/**
 * Created on Febuary 4, 2015
 * @author Eric Gonzalez
 */
package calc;

import java.applet.Applet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

/**
 * Class that make a calculator GUI
 * @author Eric Gonzalez
 *
 */
public class CalcApplet extends Applet implements ActionListener {

	TextField tf = new TextField();
	Panel p = new Panel();
    Evaluator anEvaluator = new Evaluator();   
    String expr = "";
	Button b1 = new Button("1");
	Button b2 = new Button("2");
	Button b3 = new Button("3");
	Button b4 = new Button("4");
	Button b5 = new Button("5");
	Button b6 = new Button("6");
	Button b7 = new Button("7");
	Button b8 = new Button("8");
	Button b9 = new Button("9");
	Button b0 = new Button("0");
	Button bplus = new Button("+");
	Button bminus = new Button("-");
	Button bdivide = new Button("/");
	Button bmult = new Button("*");
	Button bequal = new Button("=");
	Button bclear = new Button("CE");
    String f1[] = new String[15];
    String operators[] = new String[15];
    int f1Count = 0, operatorsCount = 0;
		
    /**
     * Initilizes buttons for the GUI
     */
	public void init(){
		setLayout(new BorderLayout());
		add(tf, BorderLayout.NORTH);
		add(p, BorderLayout.CENTER);
		p.setLayout(new GridLayout(4,4));
		Button buttons[] = {b7,b8,b9,bplus,b4,b5,b6,bminus,b1,b2,b3,bmult,b0,bclear,bequal,bdivide};
		for (int i = 0; i<buttons.length; i++){
			p.add(buttons[i]);
			buttons[i].addActionListener(this);
		}
	}
	
	/**
	 * Sets the textfield, depending on what specific button is clicked by the user
	 * @param arg0 The button that is clicked on
	 */
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource()==b0)
			tf.setText(tf.getText()+ "0");
        else if (arg0.getSource()==b1)
            tf.setText(tf.getText()+ "1");
        else if (arg0.getSource()==b2)
            tf.setText(tf.getText()+ "2");
        else if (arg0.getSource()==b3)
            tf.setText(tf.getText()+ "3");
        else if (arg0.getSource()==b4)
            tf.setText(tf.getText()+ "4");
        else if (arg0.getSource()==b5)
            tf.setText(tf.getText()+ "5");
       else if (arg0.getSource()==b6)
        	tf.setText(tf.getText()+ "6");
       else if (arg0.getSource()==b7)
        	tf.setText(tf.getText()+ "7");
       else if (arg0.getSource()==b8)
        	tf.setText(tf.getText()+ "8");
       else if (arg0.getSource()==b9)
        	tf.setText(tf.getText()+ "9");
		
		if (arg0.getSource()==bplus){
			f1[f1Count] = tf.getText().toString();
			operators[operatorsCount] = "+";
			tf.setText("");
			f1Count++;
			operatorsCount++;
		}
		if (arg0.getSource()==bminus){
			f1[f1Count] = tf.getText().toString();
			operators[operatorsCount] = "-";
			tf.setText("");
			f1Count++;
			operatorsCount++;
		}
		if (arg0.getSource()==bmult){
			f1[f1Count] = tf.getText().toString();
			operators[operatorsCount] = "*";
			tf.setText("");
			f1Count++;
			operatorsCount++;
		}
		if (arg0.getSource()==bdivide){
			f1[f1Count] = tf.getText().toString();
			operators[operatorsCount] = "/";
			tf.setText("");
			f1Count++;
			operatorsCount++;
		}
		if (arg0.getSource()==bequal){
			f1[f1Count] = tf.getText().toString();
			operators[operatorsCount] = "!";
			operatorsCount++;
			f1Count++;
			for (int i = 0; i < f1Count; i++)
				expr = expr + f1[i]+ operators[i];
			tf.setText(Integer.toString(anEvaluator.eval(expr)));
			expr = "";
			f1Count = 0;
			operatorsCount = 0;
		}
		if (arg0.getSource()==bclear){
			f1Count = 0;
			operatorsCount = 0;
			expr = "";
			tf.setText("");
		}
	}
}







