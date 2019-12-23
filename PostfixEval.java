/* Vamsi Veeramasu
 * 11/17/2019
 * This class does the actual conversion of postfix to infix. It creates the expression tree, counts the number of nodes in the tree,
 * and generates the 3-address format of the tree. Some helper methods were created as well to ensure clean and efficient code, to avoid
 * code reuse. All the variables are private, and the variable and method names are created with readability in mind. 
 */
import java.util.Stack;
public class PostfixEval {
	//3 5 9 +- 2 3 * /
	private int registers = 0;
	public PostfixEval() {
		
	}
	//The constructTree method parses the given input postfix String to construct an expression tree
	public TreeNode constructTree(String s) throws InvalidTokenException, DivideByZeroException, EmptyStringException { 
		Stack<TreeNode> nodes = new Stack<TreeNode>(); //This stack is used to create the expression tree
		int length = s.length();
		if(s.length() == 0) //Checking for an empty input string, throwing an exception if one is found. 
			throw new EmptyStringException("Empty String");
		for(int k = 0; k < length; k++) { //Using a for loop to iterate through since it's a fixed container size. 
			char curr = s.charAt(k);
			if(curr == ' ') //This if statement ignores spaces in the given input string, allowing user flexibility to not concern spaces
				continue;
			else if(isOperand(curr)) {
				StringBuffer str = new StringBuffer(); //Using StringBuffer since StringBuffer isn't immutable like String. 
				while(k < s.length() && isOperand(s.charAt(k))) {
					str.append(s.charAt(k));
					k++; //incrementing k since I'm iterating through the string inside this while loop.
				}
				k--; //Decrementing k by one to keep the current value of k accurate. 
				nodes.push(new TreeNode(str.toString()));
			}
			else if(isOperator(curr)) { //Using temporary TreeNodes to contain the values of the pop() command, due to the ordering dilemna.
				TreeNode temp1 = nodes.pop();
				TreeNode temp2 = nodes.pop();
				nodes.push(new TreeNode("" + curr, temp2, temp1)); //Utilizing this constructor so I don't have to name a new TreeNode object.
			}
			else {
				throw new InvalidTokenException("Invalid Token " + curr); //Throwing an exception if an invalid token is found. 
			}
		}
		return nodes.pop(); //The algorithm ends with the stack containing just one node - the head of the entire expression tree.
	}
	/*The getInfix method parses the built expression tree via inorder traversal to get the infix expression, while adding the
	necessary parantheses and spacing to make the equation look readable and elegant. 
	*/
	public void getInfix(TreeNode head, StringBuffer s) {
		if(head != null) { //To avoid a nullpointerexception. 
			if(!(head.getLeft() == null && head.getRight() == null))
				s.append("( "); //Adding parantheses as specified in the project. 
			getInfix(head.getLeft(), s);
			s.append(head.getVal() + " "); //Adding spacing to match the output shown in the project description. 
			getInfix(head.getRight(), s);
			if(!(head.getLeft() == null && head.getRight() == null)) //Checking to see if we're at a leaf node. 
				s.append(" )");
		}
	}
	/*The getAddressFormat method is a recursive method to traverse the built expression tree to generate the 3-address 
	   instructions of the expression. The method does a postorder traversal of the tree.
	*/
	public void getAddressFormat(TreeNode head, StringBuffer str) throws DivideByZeroException {
		
		if(head == null) //To avoid nullpointerexception. 
			return;
		
		getAddressFormat(head.getLeft(), str); //Postorder traversal. 
		getAddressFormat(head.getRight(), str);
		
		if(head.getLeft() != null && head.getRight() != null) { //If both left and right children exist, as two values are needed for an operation. 
			head.setRegister(registers); //Assigning the appropriate register number to ensure registers in use aren't used again.
			write(head, str); //Using the helper write method to determine what operation the TreeNode contains.
			str.append("R" + head.getRegister() + " ");
			if(head.getLeft().isRegister()) //This code checks if the node contains a register, and accordingly writes the instructions. 
				str.append("R" + head.getLeft().getRegister());
			else
				str.append(head.getLeft().getVal());
			str.append(" ");
			if(head.getRight().isRegister()) 
				str.append("R" + head.getRight().getRegister());
			else
				str.append(head.getRight().getVal());
			str.append("\n"); //Starting a new line to separate each operation by line, as per the project specifications. 
			registers++; //Updating the register count so that I know what register number to use for the next operation. 
		}
		System.out.println(str.toString());
	}
	//The numNodes method recursively calculates the number of nodes in a given tree, recursing through the whole tree. 
	public int numNodes(TreeNode head) { 
		if(head == null)
			return 0;
		int count = 1;
		if(head.getLeft() != null) {
			count += numNodes(head.getLeft());
		}
		if(head.getRight() != null) {
			count += numNodes(head.getRight());
		}
		return count;	
	}
	/*This helper method determines which operation is contained in a TreeNode, and accordingly writes it to the StringBuffer keeping track
	of the 3-address instructions of the expression.*/
	public void write(TreeNode head, StringBuffer str) throws DivideByZeroException{ 
		//Since getVal() returns a one character string, I'm using charAt to obtain a char value to compare. 
		if(head.getVal().charAt(0) == '+')
			str.append("Add ");
		else if(head.getVal().charAt(0) == '-')
			str.append("Sub ");
		else if(head.getVal().charAt(0) == '*')
			str.append("Mul ");
		else {
			str.append("Div ");
			if (head.getRight().getVal().charAt(0) == '0')
				throw new DivideByZeroException("Can not divide by zero");
		}
			
	}
	public void resetRegisters() {
		registers = 0;
	}
	//Just like in the last project, this helper method determines if a character is a number between 1-9 (an operand).
	public boolean isOperand(Character c) {
		if (c >= '0' && c <= '9') {
			return true;
		}
		else {
			return false;
		}
	}
	//Just like in the last project, this helper method determines if a character is a mathematical operator. 
	public boolean isOperator(Character c) {
		if (c == '+' || c == '-' || c == '/' || c == '*') {
			return true;
		}
		else {
			return false;
		}
	}
}
