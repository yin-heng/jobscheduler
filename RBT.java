import java.util.*;

// Class to implement RED BLACK TREE data structure;

public class RBT {
	
	private Node root;
	private static final boolean Red = true;
	private static final boolean Black = false;
	public int[] res = new int[3]; // this array is to return the result of command: Print(id1, id2)
	int N = 0; // this integer is to store the length of the res[]

	// this is to resize the res when it's full
	private void resize(int capacity){
		int tmp[] =  new int[capacity];
			for(int i = 0; i < res.length;i++)
		    tmp[i] = res[i];
		res = tmp;
		
	}
	// inner class to implement each node in RBT
	private class Node{
		private int id;
		private int totaltime;
		private int executedtime;
		private Node left, right;
		private boolean color;
		// Constructor of each Node in RBT
		public Node(int id, int totaltime, int executedtime, boolean color){
			this.id = id;
			this.totaltime = totaltime;
			this.executedtime = executedtime;
			this.color = color;
		}
	}
	// search the corresponding triplets with a given id;
		public int[] get(int id){
			int res[] = {0, 0, 0};
			Node x = root;
			while(x !=null){
				if(x.id > id) x = x.left;
				else if(x.id < id) x = x.right;
				else if(x.id == id){
				
					res[0] = id;
					res[1] = x.totaltime;
					res[2] = x.executedtime;
					return res;
				}
			}
			return res;
		}
	
		private boolean isRed(Node x){
			if (x == null) return false;
			return x.color;
		}
	
		 public boolean isEmpty() {
		        return root == null;
		    }
		 
		private Node rotateLeft(Node h){
			assert isRed(h.right);
			Node x = h.right;
			h.right = x.left;
			x.left = h;
			x.color = h.color;
			h.color = Red;
			return x;
		}
		
		private Node rotateRight(Node h){
			assert isRed(h.left);
			Node x = h.left;
			h.left = x.right;
			x.right = h;
			x.color = h.color;
			h.color = Red;
			return x;
		}
		
		private void flipColors(Node h){
		
			h.color = !h.color;
	        h.left.color = !h.left.color;
	        h.right.color = !h.right.color;
		}
		// delete a triplet with a given id;
		public void delete(int id) { 
	        
	        if (!isRed(root.left) && !isRed(root.right))
	            root.color = Red;

	        root = delete(root, id);
	        if (!isEmpty()) root.color = Black;
	    }
		private Node delete(Node h, int id) { 
	     
	        if (id < h.id)  {
	            if (!isRed(h.left) && !isRed(h.left.left))
	                h = moveRedLeft(h);
	            h.left = delete(h.left, id);
	        }
	        else {
	            if (isRed(h.left))
	                h = rotateRight(h);
	            if (id == h.id && (h.right == null))
	                return null;
	            if (!isRed(h.right) && !isRed(h.right.left))
	                h = moveRedRight(h);
	            if (id == h.id) {
	                Node x = min(h.right);
	                h.id = x.id;
	                h.totaltime = x.totaltime;
	                h.executedtime = x.executedtime;
	                h.right = deleteMin(h.right);
	            }
	            else h.right = delete(h.right, id);
	        }
	        return balance(h);
	    }
		
		public int min() {
	        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
	        return min(root).id;
	    } 
        private Node min(Node x) { 
	        if (x.left == null) return x; 
	        else                return min(x.left); 
	    } 
	    public void deleteMin() {
	        if (isEmpty()) throw new NoSuchElementException("BST underflow");

	        // if both children of root are black, set root to red
	        if (!isRed(root.left) && !isRed(root.right))
	            root.color = Red;

	        root = deleteMin(root);
	        if (!isEmpty()) root.color = Black;
	 
	    }

	    // delete the triplets with the minimum id rooted at h
	    private Node deleteMin(Node h) { 
	        if (h.left == null)
	            return null;

	        if (!isRed(h.left) && !isRed(h.left.left))
	            h = moveRedLeft(h);

	        h.left = deleteMin(h.left);
	        return balance(h);
	    }
		 private Node moveRedLeft(Node h) {
		        flipColors(h);
		        if (isRed(h.right.left)) { 
		            h.right = rotateRight(h.right);
		            h = rotateLeft(h);
		            flipColors(h);
		        }
		        return h;
		    }
		private Node moveRedRight(Node h) {
	        
	        flipColors(h);
	        if (isRed(h.left.left)) { 
	            h = rotateRight(h);
	            flipColors(h);
	        }
	        return h;
	    }
		 private Node balance(Node h) {

		        if (isRed(h.right))                      h = rotateLeft(h);
		        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
		        if (isRed(h.left) && isRed(h.right))     flipColors(h);
		        return h;
		    }
		// insert a triplet to RBT;
		public void put(int id, int totaltime, int executedtime){
			root = put(root, id, totaltime, executedtime);
		}
		private Node put(Node h, int id, int totaltime, int executedtime){
			if(h == null) return new Node(id, totaltime, executedtime, Red);
			if(id < h.id) h.left = put(h.left, id, totaltime, executedtime);
			if(id > h.id) h.right = put(h.right, id, totaltime, executedtime);
			if(id == h.id) h.executedtime = executedtime;
			
			if(isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
			if(isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
			if(isRed(h.left)  &&  isRed(h.right)) flipColors(h);
			return h;
		}
		public int[] Previous(int id){
			int x = Previous(root, id);
			int res[] = get(x);
			return res;
		}
		public int Previous(Node x, int id){
			// Base case
	        if (x == null) {
	            return -1;
	        }
	       if (x.id >= id) {
	            return Previous(x.left, id);
	        }
	         // Else, either left subtree or root has the ceil value
	        int ceil = Previous(x.right, id);
	        
	        return (ceil != -1 && ceil < id) ? ceil : x.id;
		}
		 
		     
	     public int[] Next(int id){
            int x = Next(root, id);
			int[] res = get(x); ;
		    return res;
		}
		public int Next(Node x, int id){

	        // Base case
	        if (x == null) {
	            return -1;
	        }
	       // If root's key is smaller, ceil must be in right subtree
	       if (x.id <= id) {
	            return Next(x.right, id);
	        }
	         // Else, either left subtree or root has the ceil value
	        int ceil = Next(x.left, id);
	        return (ceil > id) ? ceil : x.id;
		}
		public int findMin(){
			Node x = root;
			while(x.left !=null) x = x.left;
			return x.id;
		}
		
		public int findMax(){
			Node x = root;
			while(x.right != null) x = x.right;
			return x.id;
		}
		// return the result of all triplets whose id between two given number;
		public int[] Range(int id1, int id2){
			if(root == null ||( id2 < findMin() || id1 > findMax())){
				
				int res[] = {0,0,0};
				return res;
			}
			else Range(root, id1, id2);
			return res;
		}
		// search all Nodes whose id lie between two given number;
		private  void Range(Node x, int id1, int id2){
			 if (x == null) {
		            return;
		        }
		        if (id1 < x.id) {
		            Range(x.left, id1, id2);
		        }
		        if (id1 <= x.id && id2 >= x.id) {
		        	if(N == res.length) resize(N + 3);
					res[N] = x.id;
				    res[N+1] = x.executedtime;
					res[N+2] = x.totaltime;
					N = N + 3;
		        }

		        if (id2 > x.id) {
		            Range(x.right,id1, id2);
		        }
			   
		}
		
	// to test the correctness of RBT
	   public static void main(String[] args){
		RBT rbt = new RBT();
		rbt.put(2,15,0);
		rbt.put(38,20, 0);
		rbt.put(6, 20, 5);
		rbt.put(20, 0, 10);
		rbt.put(4, 14, 5);
		
		System.out.println(rbt.Previous(rbt.root,39));
	}
	

}

