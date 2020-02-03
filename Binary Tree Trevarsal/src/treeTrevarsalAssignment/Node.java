/* A Node class that defines each node of the Binary tree class. 
 * the class holds three variables. An integer variables name value which holds the data for the each node,
 * and a left and right node which are pointer to the subtrees. and also accessors and mutators for the node
 * properties.
 */
package treeTrevarsalAssignment;

public class Node {
	int value; // Holds the integer data of a node.
	Node left, right; // Left and right subtree variables.

	// Initializing constructor.
	public Node(int item) {

		this.value = item;
		this.left = null;
		this.right = null;

	} // End Node (int item).

	// Mutators and Accessors.
	public int getValue() {
		return value;
	} // End getValue().

	public void setValue(int value) {
		this.value = value;
	} // End setValue(int value).

	public Node getLeft() {
		return left;
	} // End getLeft().

	public void setLeft(Node left) {
		this.left = left;
	} // End setLeft(Node left).

	public Node getRight() {
		return right;
	} // End getRight().

	public void setRight(Node right) {
		this.right = right;
	} // End setRight(Node right).

} // End class Node.
/***********************************************************************************/
