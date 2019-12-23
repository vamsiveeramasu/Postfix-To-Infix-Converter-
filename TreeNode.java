/* Vamsi Veeramasu
 * 11/17/2019
 * This is the TreeNode class that I created. This class is the class of which all the nodes in my expression tree will consist of. 
 * This implementation is a little different from textbook implementations due to the fact that I wanted to be able to use the class itself 
 * to keep track of registers. 
 */
public class TreeNode {

	private TreeNode left; //left child of the node
	private TreeNode right; // right child of the node
	private int registerNum;// if applicable, the register in which the node is evaluating two values
	private boolean hasRegister; // to determine if the node has an assigned register to which it evaluates values in memory. 
	private String val; // the actual value contained in the node - can be an operator or an operand. 
	//val is a String instead of a char to accomodate multiple digit numbers, such as 10 or 100.
	public TreeNode() { 
		hasRegister = false; //TreeNodes are by default not assigned registers. 
	}
	public TreeNode(String x) { //A constructor that allows me to create a new TreeNode with a predetermined value. 
		val = x;
		hasRegister = false;
	}
	public TreeNode(String v, TreeNode x, TreeNode y) { //A constructor allowing me to create a parent TreeNode of two existing child nodes.
		val = v;
		left = x;
		right = y;
		hasRegister = false;
	}
	//The "Getters" (accessors) - These methods allow me to access data from the course while maintaing data encapsulation. 
	//The accessor methods for the children nodes are checked for null when called in my code to avoid nullpointerexceptions. 
	public TreeNode getLeft() { // Returns the left child
		return left;
	}
	public TreeNode getRight() { // Returns the right child 
		return right;
	}
	public String getVal() {
		return val;
	}
	public int getRegister() { //If the node does have an assigned register in memory, this tells us what number register it is. 
		return registerNum;
	}
	public boolean isRegister() {
		return hasRegister;
	}
	// These methods allow us to modify the private variables of a TreeNode object from a different file. 
	public void setLeft(TreeNode x) { // Sets the left child of a TreeNode
		left = x;
	}
	public void setRight(TreeNode x) { // Sets the right child of a TreeNode
		right = x;
	}
	public void setRegister(int x) { // If a register is assigned to the operation of a TreeNode
		registerNum = x;
		hasRegister = true;
	}
}
