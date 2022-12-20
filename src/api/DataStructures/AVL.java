package api.DataStructures;

public class AVL {
    //properties
    private AVLnode root;

    //constructor
    public AVL() {
        this.root = null;
    }

    //getters
    public AVLnode getRoot() {
        return this.root;
    }

    //setters
    public void setRoot(AVLnode node) {
        this.root = node;
    }

    //functions
     /**
     * Recursive function that computes the height of the subtree with the given node as root. Null nodes have -1 height. Leaf
     * nodes have 0 height and all other nodes have a height equal to the maximum distance (number of edges) between them and a leaf node.
     * @param node
     * @return
     */
    public int computeHeight(AVLnode node) {
        if (node == null) {
            return -1;
        }
        int leftChildHeight = this.computeHeight(node.getLeftChild());
        int rightChildHeight = this.computeHeight(node.getRightChild());
        return Math.max(leftChildHeight, rightChildHeight) + 1;
    }

     /**
     * This function checks if the tree with the given node as root is balanced. An AVL tree is balanced if the height difference of its children
     * is in the interval [-1, 1].
     * @param node the root of the tree that we want to check if its balanced
     * @return the height difference of the given node's children
     */
    public int balanceFactor(AVLnode node) {
        int leftHeight = this.computeHeight(node.getLeftChild());
        int rightHeight = this.computeHeight(node.getRightChild());
        return leftHeight - rightHeight;
    }

     /**
     * Recursive functions that inserts the given node to the subtree with root, the given root. The idea is simple, if given node is
     * smaller than given root, then insert the node to the left subtree of the tree we are working on. If it's larger, then insert the node
     * to the right subtree of the tree we are working on. During this process, if we find that node already exists inside the tree, then stop the
     * procedure by returning the root. When we are done, make sure that tree remains balanced. If it's not balanced, call specific functions to balance it
     * @param root the root node of the subtree we are working on. We want to put the given node inside this subtree
     * @param node the node we want to insert at our tree
     * @return an AVLnode that will be the root of the subtree that called this particular frame of function
     */
    public AVLnode insertNode(AVLnode root, AVLnode node) {
        if (root == null) {
            //we reached the bottom of the tree. Put node there by returning it
            return node;
        } else {
            int equality = root.checkEquality(node);
            if (equality == 0) {
                //we found the same node inside the tree, so we don't want to put it again. Return the current root of the subtree
                return root;
            } else if (equality == -1) {
                //current root is 'larger' than node. Go left
                AVLnode leftChild = this.insertNode(root.getLeftChild(), node);
                root.setLeftChild(leftChild);
            } else if (equality == 1) {
                //current root is 'smaller' than node. Go right
                AVLnode rightChild = this.insertNode(root.getRightChild(), node);
                root.setRightChild(rightChild);
            }
        }

        //Make sure that tree remains balanced. The node might got inserted and spoiled the balance
        int balanceFactor = this.balanceFactor(root);

        if (balanceFactor > 1) {
            int equality = root.getLeftChild().checkEquality(node);
            if (equality == -1) {
                root = this.simpleRightRotation(root);
            } else if (equality == 1) {
                root = this.doubleLeftRotation(root);
            }
        } else if (balanceFactor < -1) {
            int equality = root.getRightChild().checkEquality(node);
            if (equality == 1) {
                root = this.simpleLeftRotation(root);
            } else if (equality == -1) {
                root = this.doubleRightRotation(root);
            }
        }
        return root;
    }


    ///////////////////////////////////
    //          A           B        //
    //         B    ===>   C A       //
    //        C                      //
    ///////////////////////////////////
    public AVLnode simpleRightRotation(AVLnode node) {
        AVLnode temp = node.getLeftChild();
        node.setLeftChild(temp.getRightChild());
        temp.setRightChild(node);
        return temp;
    }

    ///////////////////////////////////
    //       A             B         //
    //        B    ===>   A C        //
    //         C                     //
    ///////////////////////////////////
    public AVLnode simpleLeftRotation(AVLnode node) {
        AVLnode temp = node.getRightChild();
        node.setRightChild(temp.getLeftChild());
        temp.setLeftChild(node);
        return temp;
    }

    ///////////////////////////////////
    //   A           A          B    //
    //     c  ===>    B   ==>  A C   //
    //    B            C             //
    ///////////////////////////////////
    public AVLnode doubleRightRotation(AVLnode node) {
        AVLnode rightChild = this.simpleRightRotation(node.getRightChild());
        node.setRightChild(rightChild);
        AVLnode temp = this.simpleLeftRotation(node);
        return temp;
    }

    ///////////////////////////////////
    //    C          C          B    //
    //  A   ===>    B   ==>    A C   //
    //   B         A                 //
    ///////////////////////////////////
    public AVLnode doubleLeftRotation(AVLnode node) {
        AVLnode leftChild = this.simpleLeftRotation(node.getLeftChild());
        node.setLeftChild(leftChild);
        AVLnode temp = this.simpleRightRotation(node);
        return temp;
    }


    public void printTree(AVLnode node) {
        if (node == null) {
            System.out.println("brhka keno, paw panw");
        } else {
            System.out.println("paw aristera");;
            this.printTree(node.getLeftChild());
            System.out.println(node.getData() + " " + node.getId());
            System.out.println("paw deksia");
            this.printTree(node.getRightChild());
            System.out.println("paw panw");
        }
    }
}


