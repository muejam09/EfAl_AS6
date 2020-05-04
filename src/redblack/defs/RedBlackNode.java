package redblack.defs;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import redblack.Sorted2RedBlack;

/**
 * Node of a left-leaning red-black tree
 */
public class RedBlackNode {
	
	private int key;
	private boolean leftIsRed;
	private RedBlackNode left;
	private RedBlackNode right;
	
	/**
	 * Creates an empty node for the key.
	 */
	public RedBlackNode() {}
	
	/**
	 * Sets the key value stored in the nose.
	 * @param key key value 
	 */
	public void setKey(int key) {
		this.key = key;
	}
	
	/**
	 * Sets the left child of the node.
	 * @param left left child 
	 */
	public void setLeft(RedBlackNode left) {
		this.left = left;
	}
	
	/**
	 * Sets the right child of the node.
	 * @param right right child 
	 */
	public void setRight(RedBlackNode right) {
		this.right = right;
	}
	
	/**
	 * Sets the color of the left edge of the node.
	 * @param isRed true if the left edge is red
	 */
	public void setColor(boolean isRed) {
		this.leftIsRed = isRed;
	}
	
	/**
	 * @return key value stored in the node
	 */
	public int key() {
		return key;
	}
	
	/**
	 * @return true if the left edge of the node is red
	 */
	public boolean isLeftRed() {
		return leftIsRed;
	}
	
	/**
	 * @return left child of the node
	 */
	public RedBlackNode left() {
		return left;
	}
	
	/**
	 * @return right child of the node
	 */
	public RedBlackNode right() {
		return right;
	}
	
	/**
	 * Checks if the tree starting with this node is a correct red-black tree. 
	 * @return true if the node is root of a red-black tree
	 */
	public boolean isCorrect() {
		return !hasConsecutiveReds() && (getDepth(this,new HashSet<RedBlackNode>()) >= 0);
	}
	
	private boolean hasConsecutiveReds() {
		boolean result = false;
		if(left != null) {
			if(leftIsRed && left.leftIsRed) return true;
			result = left.hasConsecutiveReds();
		}
		if(right != null) result = result || right.hasConsecutiveReds();
		return result;
	}

	private static int getDepth(RedBlackNode root, Set<RedBlackNode> nodesOnPath) {
		if(nodesOnPath.contains(root)) {
			throw new RuntimeException("No tree, cycle found!");
		}
		nodesOnPath.add(root);
		int depthLeft = 0;
		if(root.left != null) {
			if(root.left.key >= root.key) {
				throw new RuntimeException("No search tree, order incorrect!");
			}
			depthLeft = getDepth(root.left,nodesOnPath);
			if(depthLeft >= 0 && !root.leftIsRed) depthLeft++;
		}
		int depthRight = 0;
		if(root.right != null) {
			if(root.right.key <= root.key) {
				throw new RuntimeException("No search tree, order incorrect!");
			}
			depthRight = getDepth(root.right,nodesOnPath);
			if(depthRight >= 0) depthRight++;
		}
		nodesOnPath.remove(root);
		if(depthLeft == depthRight) return depthLeft;
		else return -1;
	}
	
	/**
	 * Tests class Sorted2Black.
	 */
	public static void main(String[] args) {
		Sorted2RedBlack alg = new Sorted2RedBlack();
		for(int n=1; n<=10; n++) testSorted2Black(alg, n, TestrunType.PRINTING);
		for(int n=11; n<=2000; n++) testSorted2Black(alg, n, TestrunType.TESTING);
		System.out.println("\nTiming runs (take some minutes)");
		for(int n=1000; n<=1000000; n=10*n) testSorted2Black(alg, n, TestrunType.TIMING);
		System.out.println("All tests ok.");
	}
	
	private enum TestrunType {PRINTING, TIMING, TESTING};
	
	private static final int SECOND_SIZE = 10000000;
	
	private static void testSorted2Black(Sorted2RedBlack alg, int n, TestrunType trt) {	
		int[] a = new int[n];
		for(int i=1; i<=n; i++) a[i-1] = i;
		int rep = 1;
		if(trt == TestrunType.TIMING) rep = Math.max(5, SECOND_SIZE/n*60);
		double[] times = new double[rep];
		for(int i=0; i<times.length; i++) {
			long start = System.nanoTime();
			RedBlackNode root = alg.buildRedBlackTree(a);
			long end = System.nanoTime();
			if(!root.containsElements(n)) {
				throw new RuntimeException("Incorrect elements in the tree!");
			}
			if(!root.isCorrect()) {
				throw new RuntimeException("Coloring is not correct!");
			}
			times[i] = (end-start)*1e-9;
			if(trt == TestrunType.PRINTING) {
				System.out.println("\nTree size = " + n);
				print(root, "", 'o');
			}
		}
		if(trt == TestrunType.TIMING) {
			System.out.println(String.format("Time for n=%e : %e sec", 1.0*n,times[times.length/2]));
		}
	}

	private boolean containsElements(int n) {
		ArrayList<Integer> elements = new ArrayList<>();
		collect(elements);
		for(int i=0; i<n; i++) if(elements.get(i) != i+1) return false;
		return true;
	}

	private void collect(ArrayList<Integer> elements) {
		if(left != null) left.collect(elements);
		elements.add(key);
		if(right != null) right.collect(elements);
	}

	private static void print(RedBlackNode root, String text, char next) {
		System.out.println(text + next + " : " + root.key());
		if(root.left() != null) {
			if(root.isLeftRed()) print(root.left(),text,'*');
			else print(root.left(),text+next,'L');
		}
		if(root.right() != null) {
			print(root.right(),text+next,'R');
		}
	}

}
