// Tabea Ulm, Jakob Müller

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

        if (elementsLeftSide != elementsRightSide){
            if (isCompleteTreeStructure(elementsLeftSide)){
                node.setColor(true);
            }
        }

        return node;
    }

    private static boolean isCompleteTreeStructure(int nrElements){

        int compareNumber = nrElements+1;
        int binaryNumberCounter = 1;
        int binaryNumber = (int) Math.pow(2, binaryNumberCounter);

        while(compareNumber >= binaryNumber){
            binaryNumber = (int) Math.pow(2, binaryNumberCounter);
            if (binaryNumber == compareNumber) return true;
            binaryNumberCounter++;
        }

        return false;

    }


}
