/* This class defines a binary tree. The class holds three properties, the root node, an int 
 * property for the number of nodes in the tree and an int height property that specifies the height of the
 * tree from the root node to the very last node in the tree. The class also holds methods to print the 
 * values in the nodes in inorder, preorder, and postorder. In addition, it also uses a search method to find
 * a specific value in the list and also max and min method to find maximum and minimum in the tree.
 */
package treeTrevarsalAssignment;

public class BinaryTree {

	private int size;		// Size of the tree.
	private int height;		// height of the tree.
	private Node root;		// Root node of the tree.

	// null constructor
	public BinaryTree() {
		
		size = 0;
		height = 0;
		root = null;

	} // end BinaryTree()

	// constructor - builds a tree from am array of data
	public BinaryTree(int[] data) {

		// put element 0 from the array in the tree as the root
		Node root = new Node(data[0]);
		this.root = root;

		// iterate the rest of the array, adding data to nodes and putting the nodes in
		// the tree
		for (int i = 1; i < data.length; i++) {

			Node newNode = new Node(data[i]);
			insert(root, newNode);
		} // end for
	} // BT(int[] data)

	// find height of tree
	public int height(Node p) {

		// Check if the root is empty, if not empty calculate the height.
		if (p == null) {
			int height = 0;
			this.height = height;
			return height;
		} // end if
		else {
			int height = 1 + Math.max(height(p.left), height(p.right));
			this.height = height;
			return height;
		} // end else
	} // End height().

	// see if a node with specific data is in the tree
	public boolean search(Node root, int data) {

		boolean found = false;
		while (root != null) {

			// Check to see if the node is in the tree using if-else statement.
			if (root.value > data) {
				root = root.left;

			} else if (root.value < data) {
				root = root.right;

			} else {
				found = true; // notify the user that the node was found.
				break;
			} // end else.

			found = search(root, data);
		} // end if else statement.

		return found; // notify the user that the node is not in the tree.
	} // End search(Node root, int data).

	// insert a node with data into the tree
	public Node insert(Node root, Node newNode) {

		if (root == null) {
			root = newNode;
			this.root = root;
			size++;
			return root;
		} // end if

		if (root.value > newNode.value) {
			if (root.left == null) {
				root.left = newNode;
				size++;
			} else {
				insert(root.left, newNode);
			} // end else
		} else if (root.value < newNode.value) {
			if (root.right == null) {
				root.right = newNode;
				size++;
			} else {
				insert(root.right, newNode);
			} // end else
		} // end else if

		// find height of the tree.
		height(this.root);

		// Return the root.
		return root;
	} // End insert(BinaryTreeNode root, BinaryTreeNode newNode).

	// Given a binary tree, print its nodes according to the "bottom-up" postorder
	// traversal.
	public void printPostorder(Node node) {

		// return if the node is empty.
		if (node == null) {
			return;
		} // End if statement.

		// first recur on left subtree
		printPostorder(node.left);

		// then recur on right subtree
		printPostorder(node.right);

		// now deal with the node
		System.out.print(node.value + " ");
	} // End printPostorder(Node node).

	// Given a binary tree, print its nodes in inorder.
	public void printInorder(Node node) {

		// return if the node is empty.
		if (node == null) {
			return;
		} // End if statement.

		// first recur on left child.
		printInorder(node.getLeft());

		// then print the data of node.
		System.out.print(node.getValue() + " ");

		// now recur on right child.
		printInorder(node.getRight());
	} // End printInorder(Node node)

	// Given a binary tree, print its nodes in preorder.
	public void printPreorder(Node node) {

		// return if the node is empty.
		if (node == null) {
			return;
		} // End if statement.

		// first print data of node.
		System.out.print(node.value + " ");

		// then recur on left subtree.
		printPreorder(node.left);

		// now recur on right subtree.
		printPreorder(node.right);
	} // End printPreorder(Node node).

	// Find the max value in the tree.
	public int max(Node root) {

		// Initialize the root as a current node.
		Node current = root;

		// Run a while loop to go through the tree to find the max.
		while (current.getRight() != null) {

			current = current.getRight();
		} // end while loop

		return current.getValue(); // return the max vaule
	}// end findMax()

	// Find the min value in the tree.
	public int min(Node root) {

		// Initialize the root as a current node.
		Node current = root;

		// Run a loop to go through the tree to find the min.
		while (current.getLeft() != null) {

			current = current.getLeft();
		} // end while loop

		return current.getValue(); // return the min vaule
	}// end findMin()

	// get root method
	public Node getRoot() {

		return root;
	} // end getRoot()

	// get the size of the tree
	public int getSize() {

		return size;
	} // end size()

	// get the height of the tree.
	public int getHeight() {

		return height;
	} // End getHeight().

} // End BinaryTree class.
/***********************************************************************************/
