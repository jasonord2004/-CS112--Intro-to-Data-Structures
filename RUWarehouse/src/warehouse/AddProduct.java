package warehouse;

/*
 * Use this class to test to addProduct method.
 */
public class AddProduct {
    public static void main(String[] args) {
        StdIn.setFile("addproduct.in");
        StdOut.setFile("addproduct.out");
        Warehouse w = new Warehouse();
        int n = Integer.parseInt(StdIn.readLine());
        for (int i = 0; i < n; i++){
            int day = StdIn.readInt();
            int id = StdIn.readInt();
            String name = StdIn.readString();
            int  stock = StdIn.readInt();
            int demand = StdIn.readInt();
            w.addProduct(id, name, stock, day, demand);
        }
        StdOut.println(w);
	// Use this file to test addProduct
    }
}
