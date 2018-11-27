// Authors: Kaleb Bello and Alexis Lozano
/**
 * AVLTree uses BinarySearchTree to implement an AVLTree
 * 
 * @param <E> The type of the data elements. Note that we have strengthened the
 *            constraints on E such that E is now a Comparable.
 *            
 * TODO implement AVL Tree using BST
 */
public class AVLTree<E extends Comparable<? super E>> extends BinarySearchTree<E> implements DataCounter<E> {

	public BSTNode overallRoot;
	
	
	// A utility function to get the height of the tree 
    private int height(BSTNode N) { 
        if (N == null) 
            return 0; 
        else return N.height; 
    } 
    
    /**
     * compares two heights and returns max
     * @param a height 1
     * @param b height 2
     * @return bigger height
     */
    private int max(int a, int b) { 
        return (a > b) ? a : b; 
    } 
    
    /**
     * Case 1 (outside left-left) 
     * Rebalance with a single right rotation
     * @param k2
     * @return New Root
     */
    private BSTNode singleRightRotate(BSTNode k2) { 
        BSTNode k1 = k2.left; 
        BSTNode T2 = k1.right; 
  
        // Perform rotation 
        k1.right = k2; 
        k2.left = T2; 
  
        // Update heights 
        k2.height = max(height(k2.left), height(k2.right)) + 1; 
        k1.height = max(height(k1.left), height(k1.right)) + 1; 
  
        return k1; 
    } 
    
    /**
     * Case 4 (outside right-right):
	 * Re-balance with a single left rotation.
     * @param k1 
     * @return
     */
    private BSTNode singleLeftRotate(BSTNode k1) { 
        BSTNode k2 = k1.right; 
        BSTNode T2 = k2.left; 
  
        // Perform rotation 
        k2.left = k1; 
        k1.right = T2; 
  
        //  Update heights 
        k1.height = max(height(k1.left), height(k1.right)) + 1; 
        k2.height = max(height(k2.left), height(k2.right)) + 1; 
  
        // Return new root 
        return k2; 
    } 
    
    private BSTNode doubleLeftRightRotation(BSTNode k3) {
    	k3.left = singleRightRotate(k3.left);
    	return singleRightRotate(k3);
    }
     
    @SuppressWarnings("unused")
	private BSTNode doubleRightLeftRotation(BSTNode k1) {
    	k1.right = singleRightRotate(k1.right);
    	return singleLeftRotate(k1);
    }
    
    // Get Balance factor of node N 
    private int getBalance(BSTNode N) { 
        if (N == null) 
            return 0; 
  
        return height(N.left) - height(N.right); 
    } 
    
    @SuppressWarnings("unused")
	private BSTNode insert(BSTNode node,E data) { 
    	  
        //Perform the normal BST insertion
        if (node == null) 
            return (new BSTNode(data)); 
  
        if (data.compareTo(node.data) < 0) 
            node.left = insert(node.left, data); 
        else if (data.compareTo(node.data) > 0) 
            node.right = insert(node.right, data); 
        else // Duplicate keys not allowed 
            return node; 
  
        //Update height of this ancestor node
        node.height = 1 + max(height(node.left), height(node.right)); 
  
        //Get the balance factor of this ancestor node to check whether this node became unbalanced 
        int balance = getBalance(node); 
       
  
        if(  balance > 1 )
            if( height( node.left.left ) >= height( node.left.right ) )
                node = singleRightRotate( node ); 
            else
                node = doubleLeftRightRotation( node );
        else
        if( balance < -1 ) // -1 to 1
            if( height( node.right.right ) >= height( node.right.left ) )
                node = singleLeftRotation( node ); 
            else
                node = doublerightleftrotation( node );
        return node;
    }

    private BSTNode doublerightleftrotation(BSTNode root) {
        root.right = singleRightRotate(root.right);
        return singleLeftRotation(root);
    }

    private BSTNode singleLeftRotation(BSTNode root) {
        BSTNode right = root.right;
        root.right = right.left;
        right.left = root;
        return right;

 
    } 
    
    @SuppressWarnings("unused")
	private void preOrder(BSTNode node) { 
        if (node != null) { 
            System.out.print(node.data + " "); 
            preOrder(node.left); 
            preOrder(node.right); 
        } 
    } 
}
    
	