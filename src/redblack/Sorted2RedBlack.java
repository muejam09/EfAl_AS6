package redblack;

import redblack.defs.RedBlackNode;

public class Sorted2RedBlack {

    /**
     * Constructs a left-leaning red-black tree from a sorted array in linear time.
     *
     * @param a non-empty sorted array (with strongly increasing values)
     * @return root of the red-black tree
     */
    public RedBlackNode buildRedBlackTree(int[] a) {
        return null;
    }

    public static RedBlackNode arrayToRBBst(int[] a, int startIndex, int endIndex) {
        int middle = (int) Math.ceil((double) (startIndex + endIndex) / 2);

        if (startIndex > endIndex) {
            return null;
        }

        RedBlackNode node = new RedBlackNode();
        node.setKey(a[middle]);
        node.setLeft(arrayToRBBst(a, startIndex, middle - 1));
        node.setRight(arrayToRBBst(a, middle + 1, endIndex));

        return node;
    }


}
