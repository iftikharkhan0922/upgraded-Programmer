/* This program holds three class. The main executable class, a node class and a Binary tree class.
 * The executable class is just a testing shell for the node node class and the binary tree class. The node 
 * class simply defines a node for the binary tree, and the binaryTree class creates a tree, initializes it 
 * with the array of numbers passed into it though an object in the executable class. The binaryTree class
 * also uses this array to manipulate the data and satisfy the test methods called by the executable class.
 * 
 */
package treeTrevarsalAssignment;

// Import the scanner class.
import java.util.Scanner;
public class TreeTrevarsalAssignment {

	public static void main(String[] args) {

		// Declare a scanner object.
		Scanner input = new Scanner(System.in);

		// An array to hold the numbers.
		int[] numbers = {55, 19, 60, 12, 73, 50, 58, 31, 17, 82}; 

		// create a tree
		BinaryTree tree = new BinaryTree(numbers);


		// Print the tree in inorder traversal.
		System.out.println("\nInorder traversal of binary tree is ");
		tree.printInorder(tree.getRoot());

		// Print the tree in pre-order traversal.
		System.out.println("\nPreorder traversal of binary tree is ");
		tree.printPreorder(tree.getRoot());

		// Print the tree in post-order traversal.
		System.out.println("\nPostorder traversal of binary tree is ");
		tree.printPostorder(tree.getRoot());

		// Find the maximum in a tree and print it on the console..
		System.out.println("\nThe maximum node value in the tree is " + tree.max(tree.getRoot()));

		// Find the minimum in a tree and print it on the console.
		System.out.println("The minimum node value in the tree is " + tree.min(tree.getRoot()));
		
		// Print the size and height of the tree.
		System.out.println("Tree size is " + tree.getSize() + " and height is " + tree.getHeight());

		// Prompt the user to enter a key value to find if it is in the tree.
		System.out.print("Enter an integer to see if it is in the tree or not: ");
		int value = input.nextInt();

		// Find the desired value in the tree and tell the user if it is in the tree or
		// not.
		System.out.print("The value entered was " + (tree.search(tree.getRoot(), value) ?
				"found in the tree" : "not found in the tree."));

	}// end main method

} // End class TreeTrevarsalAssignment.
/***********************************************************************************/
