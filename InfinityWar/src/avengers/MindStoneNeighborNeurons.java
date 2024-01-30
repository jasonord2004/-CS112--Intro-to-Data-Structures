package avengers;

/**
 * Given a Set of Edges representing Vision's Neural Network, identify all of the 
 * vertices that connect to the Mind Stone. 
 * List the names of these neurons in the output file.
 * 
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * MindStoneNeighborNeuronsInputFile name is passed through the command line as args[0]
 * Read from the MindStoneNeighborNeuronsInputFile with the format:
 *    1. v (int): number of neurons (vertices in the graph)
 *    2. v lines, each with a String referring to a neuron's name (vertex name)
 *    3. e (int): number of synapses (edges in the graph)
 *    4. e lines, each line refers to an edge, each line has 2 (two) Strings: from to
 * 
 * Step 2:
 * MindStoneNeighborNeuronsOutputFile name is passed through the command line as args[1]
 * Identify the vertices that connect to the Mind Stone vertex. 
 * Output these vertices, one per line, to the output file.
 * 
 * Note 1: The Mind Stone vertex has out degree 0 (zero), meaning that there are 
 * no edges leaving the vertex.
 * 
 * Note 2: If a vertex v connects to the Mind Stone vertex m then the graph has
 * an edge v -> m
 * 
 * Note 3: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut:
 *     StdOut.setFile(outputfilename);
 *     //Call StdOut.print() for EVERY neuron (vertex) neighboring the Mind Stone neuron (vertex)
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/MindStoneNeighborNeurons mindstoneneighborneurons.in mindstoneneighborneurons.out
 *
 * @author Yashas Ravi
 * 
 */


public class MindStoneNeighborNeurons {
    
    public static void main (String [] args) {
        
    	if ( args.length < 2 ) {
            StdOut.println("Execute: java MindStoneNeighborNeurons <INput file> <OUTput file>");
            return;
        }
    	
    	// WRITE YOUR CODE HERE
        String mindStoneNeighborNeuronsInputFile = args[0];
        String mindStoneNeighborNeuronsOutputFile = args[1];

        StdIn.setFile(mindStoneNeighborNeuronsInputFile);
        StdOut.setFile(mindStoneNeighborNeuronsOutputFile);
        
        int n = StdIn.readInt();
        Neuron[] arr = new Neuron[n];

        for(int i = 0; i < n; i++){
            Neuron name = new Neuron(StdIn.readString());
            arr[i] = name;
        }

        int m = StdIn.readInt();
        for(int i = 0; i < m; i++){
            String name1 = StdIn.readString();
            String name2 = StdIn.readString();

            Neuron first = new Neuron(name1);        //split std out of the constructor and get rid of second for loop?
            Neuron second = new Neuron(name2);

            for(int j = 0; j < arr.length; j++){
                if(arr[j].getName().equals(first.getName())){
                    arr[j].setNext(second);
                    //System.out.println(arr[j].getNext().getName());
                }
            }
        }
    

        Neuron mindStone = arr[0];

        while(mindStone.getNext() != null){
           //System.out.println(mindStone.getName());
           for(int i = 0; i < arr.length; i++){
                if(mindStone.getNext().getName().equals(arr[i].getName())){
                    mindStone = arr[i];
                }
           } 
           //System.out.println(mindStone.getName());
        }

        for(int i = 0; i < arr.length; i++){
            if(arr[i].getNext() != null && arr[i].getNext().getName().equals(mindStone.getName())){
                //System.out.println(arr[i].getName());
                StdOut.println(arr[i].getName());
            }
        }
    }
}
