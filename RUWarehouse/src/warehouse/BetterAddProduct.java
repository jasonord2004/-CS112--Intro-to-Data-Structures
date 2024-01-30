package warehouse;

/*
 * Use this class to test the betterAddProduct method.
 */ 
public class BetterAddProduct {
    public static void main(String[] args) {
        StdIn.setFile("betteraddproduct.in");
        StdOut.setFile("betteraddproduct.out");
        Warehouse w = new Warehouse();
        int n = Integer.parseInt(StdIn.readLine());
        for (int i = 0; i < n; i++){
            int day = StdIn.readInt();
            int id = StdIn.readInt();
            String name = StdIn.readString();
            int  stock = StdIn.readInt();
            int demand = StdIn.readInt();
            w.betterAddProduct(id, name, stock, day, demand);
        }
        StdOut.println(w);
        
        // Use this file to test betterAddProduct
    }
}
