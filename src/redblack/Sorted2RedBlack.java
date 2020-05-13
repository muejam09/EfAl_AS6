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
        return arrayToRBBst(a, 0, a.length-1);
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

        int elementsLeftSide = middle - startIndex;
        int elementsRightSide = endIndex - middle;

        if (isCompleteTreeStructure(elementsLeftSide)){
            if (!isCompleteTreeStructure(elementsRightSide)) node.setColor(true);
        }

        return node;
    }

    private static boolean isCompleteTreeStructure(int nrElements){
        // Hilfsmethode um zu färben
        int completeNumber = 1;
        int i = 0;

        while (nrElements >= completeNumber){
            if(completeNumber == nrElements) return true;
            i++;
            completeNumber = completeNumber + 2*i;
        }
        return false;
    }


}
