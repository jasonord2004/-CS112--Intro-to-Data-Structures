public class WeightedQuickUnionUF {
    
  // Array stores the information that two vertices are connected.
  // Vertices that are connected have the same root
  private int[] parent;
  private int[] size;  
    
  // Initializes the data structure
  // Running time, how many array accesses?
  public WeightedQuickUnionUF(int n)   {      
      parent = new int[n];
      for (int i = 0; i < n; i++)  {        
        parent[i] = i;
        size[i]   = 1;
      }
  }

  // Returns the representative of the set of vertices that contains p
  // How? returns the root
  // The root is the vertex that is its own parent
  public int find (int p) {
     while (p != parent[p]) { 
       p = parent[p];
     }      
   return p;   
  } 
  
  // Connect vertices p and q
  // How? link the root of the smaller tree to the root of the larger tree
  // Running time, how many array accesses?
  public void union(int p, int q) {
     int rootP = find(p);
     int rootQ = find(q);
     if (rootP == rootQ) return;

     // Assume rootP is smaller than rootQ
     int rootSmaller = rootP, rootLarger = rootQ;

     // Now check
     if ( size[rootP] >= size[rootQ]) {
        rootSmaller = rootQ;
        rootLarger = rootP;
     }

     // link root of smaller tree to root of larger tree
     parent[rootSmaller] = rootLarger;   
  }
}
