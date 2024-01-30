package warehouse;

/*
 * Use this class to test the deleteProduct method.
 */ 
public class DeleteProduct {
    public static void main(String[] args) {
        StdIn.setFile("deleteproduct.in");
        StdOut.setFile("deleteproduct.out");
        Warehouse w = new Warehouse();
        int n = Integer.parseInt(StdIn.readLine());
        for(int i = 0; i < n; i++){
            String begin = StdIn.readString();
            if(begin.equals("add")){
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                String name = StdIn.readString();
                int  stock = StdIn.readInt();
                int demand = StdIn.readInt();
                w.addProduct(id, name, stock, day, demand);
            } else if (begin.equals("delete")){
                int id = StdIn.readInt();
                w.deleteProduct(id);
            }
        }
        StdOut.println(w);


	// Use this file to test deleteProduct
    }
}
