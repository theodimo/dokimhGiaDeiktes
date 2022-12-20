package api.DataStructures;

public class AVLnode {
    //properties
    private String data;
    private int id;
    private AVLnode leftChild;
    private AVLnode rightChild;

    //constructor
    public AVLnode(String data, int id) {
        this.data = data;
        this.id = id;
        this.leftChild = null;
        this.rightChild = null;
    }

    //getters
    public String getData() {
        return this.data;
    }

    public int getId() {
        return this.id;
    }

    public AVLnode getLeftChild() {
        return this.leftChild;
    }

    public AVLnode getRightChild() {
        return this.rightChild;
    }

    //setters
    public void setLeftChild(AVLnode leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(AVLnode rightChild) {
        this.rightChild = rightChild;
    }

    //functions
     /**
     * Compares the current node, (this) with the given node.
     * @param node the right part of the comparation
     * @return -1 if current node's data is larger that the given node's data, or they are the same but current node's id is larger.
     *          1 if current node's data is smaller than the given node's data, or they are the same but current node's id is smaller
      *         0 if current node's data and id are the same with the corresponding properties of given node.
      *         We can visualize this number like this:  -1 0 1 --> -1 given node goes left
      *                                                         -->  0 given node stays there
      *                                                         -->  1 given node goes right
     */
    public int checkEquality(AVLnode node) {
        if (this.data.compareTo(node.getData()) > 0) {
            return -1;
        } else if (this.data.compareTo(node.getData()) < 0) {
            return 1;
        } else if (this.id == node.getId()) {
            return 0;
        } else {
            if (this.id > node.id) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
