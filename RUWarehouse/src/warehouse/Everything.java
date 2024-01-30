package warehouse;

/*
 * Use this class to put it all together.
 */ 
public class Everything {
    public static void main(String[] args) {
        StdIn.setFile("everything.in");
        StdOut.setFile("everything3.out");
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
            } else if (begin.equals("restock")){
                int id = StdIn.readInt();
                int stock = StdIn.readInt();
                w.restockProduct(id, stock);
            } else if (begin.equals("purchase")){
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                int amount = StdIn.readInt();
                w.purchaseProduct(id, day, amount);
            }
        }
        StdOut.println(w);
	// Use this file to test all methods
    }
}
