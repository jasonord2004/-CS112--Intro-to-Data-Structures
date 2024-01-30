package avengers;

/**
 * Given an adjacency matrix, use a random() function to remove half of the nodes. 
 * Then, write into the output file a boolean (true or false) indicating if 
 * the graph is still connected.
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * PredictThanosSnapInputFile name is passed through the command line as args[0]
 * Read from PredictThanosSnapInputFile with the format:
 *    1. seed (long): a seed for the random number generator
 *    2. p (int): number of people (vertices in the graph)
 *    2. p lines, each with p edges: 1 means there is a direct edge between two vertices, 0 no edge
 * 
 * Note: the last p lines of the PredictThanosSnapInputFile is an ajacency matrix for
 * an undirected graph. 
 * 
 * The matrix below has two edges 0-1, 0-2 (each edge appear twice in the matrix, 0-1, 1-0, 0-2, 2-0).
 * 
 * 0 1 1 0
 * 1 0 0 0
 * 1 0 0 0
 * 0 0 0 0
 * 
 * Step 2:
 * Delete random vertices from the graph. You can use the following pseudocode.
 * StdRandom.setSeed(seed);
 * for (all vertices, go from vertex 0 to the final vertex) { 
 *     if (StdRandom.uniform() <= 0.5) { 
 *          delete vertex;
 *     }
 * }
 * Answer the following question: is the graph (after deleting random vertices) connected?
 * Output true (connected graph), false (unconnected graph) to the output file.
 * 
 * Note 1: a connected graph is a graph where there is a path between EVERY vertex on the graph.
 * 
 * Note 2: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, isConnected is true if the graph is connected,
 *   false otherwise):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(isConnected);
 * 
 * @author Yashas Ravi
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/PredictThanosSnap predictthanossnap.in predictthanossnap.out
*/

public class PredictThanosSnap {
	 
    public static void main (String[] args) {
 
        if ( args.length < 2 ) {
            StdOut.println("Execute: java PredictThanosSnap <INput file> <OUTput file>");
            return;
        }
        
    	// WRITE YOUR CODE HERE
        String PredictThanosSnapInputFile = args[0];
        String PredictThanosSnapOutputFile = args[1];

        StdIn.setFile(PredictThanosSnapInputFile);
        StdOut.setFile(PredictThanosSnapOutputFile);

        long seed = StdIn.readLong();
        int vertices = StdIn.readInt();
        boolean[] connect = new boolean[vertices];
        boolean[] alive = new boolean[vertices];
        
        StdRandom.setSeed(seed);

        int[][] adj = new int[vertices][vertices];
        for(int i = 0; i < vertices; i++){
            for(int j = 0; j < vertices; j++){
                adj[i][j] = StdIn.readInt();
            }
        }

        for(int i = 0; i < vertices; i++){
            if(StdRandom.uniform() <= 0.5){
                //delete vertex
                System.out.println("dead: " + i);
                alive[i] = false;
                for(int j = 0; j < vertices; j++){
                    adj[i][j] = 0; //cut in-connections
                    adj[j][i] = 0; //cut out-connections
                }
            } else {
                alive[i] = true;
            }
        }
    
        int next = 0;
        for(int i = 0; i < connect.length; i++){
            //connect[next] = true;
            next = getNextVertex(next, i, adj, connect);
        
        }

        boolean answer = true;
        for(int i = 0; i < connect.length; i++){
            System.out.println("alive: " + alive[i] + " connected: " + connect[i]);
            if(connect[i] == false && alive[i] == true){
                answer = false;
            }
        }
        StdOut.print(answer);
    }

    public static int getNextVertex(int next, int i, int[][] adjacencyM, boolean[] connect){
        int index = 0;
        for(int j = 0; j < connect.length; j++){ 
            if(adjacencyM[i][j] == 0 || connect[index] == true){
                System.out.println("j: " + j);
                index++;
            } else {
                System.out.println("next: " + next);
                connect[j] = true; 
            }
        }
        System.out.println("i: " + i + " index: " + index);
        return index;
    }
}
