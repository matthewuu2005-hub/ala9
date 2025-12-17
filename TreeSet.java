import com.sun.source.tree.Tree;
import java.util.Comparator;
/**
 * Class to implement a binary search tree data structure using linked nodes
 */
public class TreeSet<E>{
    public static int cIterations, aIterations, rIterations;
    // data members
    private TreeNode root;
    private int size;
    private Comparator<E> comparator;
    // Inner class for the tree nodes
    private class TreeNode{
        E value;
        TreeNode left, right;
        TreeNode(E val){
            value = val;
            left = right = null;
        }
    }
    /**
     * private method to compare two E values using natural ordering or the comparator object
     * @param value1 the first value
     * @param value2 the second value
     * @return 0   if value1 and value2 are equal
     *         > 0 if value1 is after value2
     *         < 0 if value1 is before value2
     */
    private int compare(E value1, E value2){
        if(comparator == null){
            return ((Comparable) value1).compareTo(value2);
        }
        else{
            return comparator.compare(value1, value2);
        }
    }
    /**
     * Default constructor
     * Create an empty binary search tree
     */
    public TreeSet(){
        root = null;
        size = 0;
        comparator = null;
    }
    /**
     * Constructor with a comparator
     * Create an empty binary search tree
     */
    public TreeSet(Comparator<E> c){
        root = null;
        size = 0;
        comparator = c;
    }
    /**
     * Getter of the size of the tree
     * @return the number of nodes in the tree
     */
    public int size(){
        return size;
    }
    /**
     * @return true if the tree is empty, false otherwise
     */
    public boolean isEmpty(){
        return (size == 0);
    }
    /**
     * clears the tree
     */
    public void clear(){
        root = null;
        size = 0;
    }
    /**
     * Search method
     * @param element the value being looked up
     * @return true if element is found in the tree, false otherwise
     */
    public boolean contains(E element){
        cIterations = 0;
        if (root == null){
            return false;
        }
        TreeNode current = root;
        while(current != null){
            cIterations++;
            int c = compare(element,current.value);
            if(c == 0)
                return true;
            else if(c < 0)
                current = current.left;
            else
                current = current.right;
        }
        return false;   
    }
    /**
     * Ading a new element to the tree
     * @param element the value to add in the tree
     * @return true if element was added succesfully, false if element already exists in the tree
     */
    public boolean add(E element){
        aIterations = 0;
        TreeNode newNode = new TreeNode(element);
        if (root == null){
            root = newNode;
        }
        else{
            TreeNode current = root, parent = null;
            while(current != null){
                aIterations++;
                parent = current;
                int c = compare(element, current.value);
                if(c == 0)
                    return false;
                else if(c < 0)
                    current = current.left;
                else
                    current = current.right;
            }
            if(compare(element,parent.value) < 0)
                parent.left = newNode;
            else
                parent.right = newNode;
        }
        size++;
        return true;
    }
    /**
     * Removing an element from the tree
     * @param element the value to be removed from the tree
     * @return true if element was found and removed, false if element was not found
     */
    public boolean remove(E element){
        rIterations = 0;
        if (root == null){
            return false;
        }
        // First, find the node with value element
        TreeNode node = root, parent = null;
        while(node != null){
            rIterations++;
            int c = compare(element, node.value);
            if(c == 0)
                break;
            parent = node;
            if(c < 0)
                node = node.left;
            else
                node = node.right;
        }
        if (node == null){
            return false; // value not found
        }
        // case 1: Leaf node
        if(node.left == null && node.right == null){
            if(parent == null){ // the only node in the tree
                root = null; size = 0;
            }
            else{
                if(parent.left == node)
                    parent.left = null;
                else
                    parent.right = null;
            }
        }
        // case 2: node has a right child only
        else if(node.left == null){
            if(parent == null)
                root = node.right;
            else if (parent.left == node)
                parent.left = node.right;
            else   
                parent.right = node.right;
        }
        // case 2: node has a left child only
        else if(node.right == null){
            if(parent == null)
                root = node.left;
            else if(parent.left == node)
                parent.left = node.left;
            else
                parent.right = node.left;
        }
        // case 3: node has two children
        else{
            TreeNode rightMostCurrent = node.left;
            TreeNode rightMostParent = node;
            while(rightMostCurrent.right != null){
                rIterations++;
                rightMostParent = rightMostCurrent;
                rightMostCurrent = rightMostCurrent.right;
            }
            node.value = rightMostCurrent.value;
            if(rightMostParent.left == rightMostCurrent)
                rightMostParent.left = rightMostCurrent.left;
            else
                rightMostParent.right = rightMostCurrent.left;
        }
        size--;
        return true;
    }
    /**
     * Preorder Traversal method
     */
    public void preorder(){
        System.out.print("[");
        preorder(root);
        System.out.println("]");
    }
    /**
     * Preorder Traversal recursive helper method
     * @param node the node where the traversal starts
     */
    public void preorder(TreeNode node){
        if(node != null){
            System.out.print(node.value + " ");
            preorder(node.left);
            preorder(node.right);
        }
    }
    /**
     * Inorder Traversal method
     */
    public void inorder(){
        System.out.print("[");
        inorder(root);
        System.out.println("]");
    }
    /**
     * Inorder Traversal recursive helper method
     * @param node the node where the traversal starts
     */
    public void inorder(TreeNode node){
        if(node != null){
            inorder(node.left);
            System.out.print(node.value + " ");
            inorder(node.right);
        }
    }
    /**
     * Postorder Traversal method
     */
    public void postorder(){
        System.out.print("[");
        postorder(root);
        System.out.println("]");
    }
    /**
     * Postorder Traversal recursive helper method
     * @param node the node where the traversal starts
     */
    public void postorder(TreeNode node){
        if(node != null){
            postorder(node.left);
            postorder(node.right);
            System.out.print(node.value + " ");
        }
    }  

    public int height(){
        return heightHelper(root);
    }

    public int heightHelper(TreeNode node){
        //base case
        if(node == null){
            return 0;
        }
        int leftHeight = heightHelper(node.left);
        int rightHeight = heightHelper(node.right);

        return 1 + Math.max(leftHeight, rightHeight);
    }

    public boolean isBalanced(){
        return isBalancedHelper(root);
    }

    public boolean isBalancedHelper(TreeNode node){
        //base case
        if(node == null){
            return true;
        }
        int leftHeight = heightHelper(node.left);
        int rightHeight = heightHelper(node.right);
        //base case
        if(Math.abs(leftHeight - rightHeight) > 1){
            return false;
        }

        return isBalancedHelper(node.left) && isBalancedHelper(node.right);
    }
}