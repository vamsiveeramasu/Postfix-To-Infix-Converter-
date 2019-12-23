/* Vamsi Veeramasu
 * 11/17/2019
 * 
 * This class contains the GUI of the project, as well as the main method where everything is created and executed. I used the same code
 * as I did in the last project, as the GUI specifications are almost identical. I simply changed some variable names and some labels. 
 */
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class P2GUI extends JFrame {
	private JLabel enter = new JLabel("Enter Postfix Expression");
	private JTextField input = new JTextField("");
	private JButton evaluate = new JButton("Construct Tree");
	private JLabel result = new JLabel("Infix Expresion");
	private JTextField displayResult = new JTextField("");
	public P2GUI() {
		
		super("Three Address Generator");
		setSize(450,200);
		setLayout(new GridLayout(3,1, 0, 5));
		
		displayResult.setEditable(false);
		displayResult.setBackground(getBackground());
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(0, 2));
		topPanel.add(enter);
		topPanel.add(input);
		
		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new FlowLayout());
		middlePanel.add(evaluate);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(0, 2));
		bottomPanel.add(result);
		bottomPanel.add(displayResult);
		
		add(topPanel); // Adding the panels in the right order in order to mimic the GUI from the project description 
		add(middlePanel);
		add(bottomPanel);
		
		PostfixEval postfix = new PostfixEval();
		
		evaluate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					StringBuffer str = new StringBuffer(); // Using a StringBuffer since it's not immutable like String in Java
					StringBuffer allAddresses = new StringBuffer();//Using a StringBuffer to store ALL the addresses to write to a file at once.
					
					TreeNode head = postfix.constructTree(input.getText()); //Creating the expression tree, and returning the head of the tree.
					postfix.getInfix(head, str); //Obtaining the infix expression from the created expression tree.
					int nodes = postfix.numNodes(head); //Calculating the number of nodes in the expression tree. 
					postfix.getAddressFormat(head, allAddresses);//Generating the 3-address instructions of the expression tree. 
					
					allAddresses.append("Total nodes in the tree: " + nodes + "\n"); //Adding the number of nodes line to the buffer to output to a file.
					displayResult.setText(str.toString());
					postfix.resetRegisters(); //Reallocates the registers to start from 0 again. 
					
					File output = new File("Results.txt"); //Writing the input and output results to Results.txt
					PrintWriter infile = new PrintWriter(output);
					infile.write(allAddresses.toString());
					infile.flush();
					infile.close();
					
				}
				catch(InvalidTokenException e1) {
					JOptionPane.showMessageDialog(null, e1); //Displays the specifc character causing the exception - the invalid token.
				}
				catch(DivideByZeroException e2) {
					JOptionPane.showMessageDialog(null, "Division By Zero Encountered. Please Try Again With Proper Format and Order.");
				}
				catch(EmptyStringException e3) {
					JOptionPane.showMessageDialog(null, "Empty Input Encountered. Please Enter a Valid Input.");
				}
				catch(FileNotFoundException e5){ // In case the file isn't found. 
					
				}
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void display() {
		setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		P2GUI gui = new P2GUI();
		gui.display();

	}
}