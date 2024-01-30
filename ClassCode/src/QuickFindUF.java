public class QuickFindUF {
    
    // Array stores the information that two vertices are connected.
    // Vertices that are connected have the same id
    private int[] id;


    // Initializes the data structure
    // Running time, how many array accesses?
    public QuickFindUF (int n) {
        id = new int[n]; // initialize an array of n vertices
        
        // set id of each vertex to itself
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
   }
   
   // Returns the representative of the set of vertices that contains p
   // How? return id of p
   // Running time, how many array accesses? f(n) = 1 = 0(1) constant
   public int find (int p) {  
    return id[p]; //read +1
   }

   // Connect vertices p and q
   // How? change all entries with id[p] to id[q]
   // Running time, how many array accesses? f(n) = 2 + n + n -1 = 2n+1 = 2n = O(n) linear
   public void union (int p, int q) {
    
    int pid = id[p];  //read +1 
    int qid = id[q];  //read +1
    for (int i = 0; i < id.length; i++) { 
        if (id[i] == pid) {  //read +n
            id[i] = qid;  //write +(n-1)
        }
    }
   }

   // Client code
   public static void main (String[] args) {
    QuickFindUF qf = new QuickFindUF(10);
    qf.union(0, 2);
    qf.union(3, 7);
    qf.union(7, 8);
    
    boolean conn = qf.find(3)==qf.find(8);
    System.out.println("Are 3 and 8 connected? " + conn);

   }
}
