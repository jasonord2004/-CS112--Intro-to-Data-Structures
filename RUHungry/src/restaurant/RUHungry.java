package restaurant;

import java.util.ArrayList;
import java.util.Hashtable;


/**
 * RUHungry is a fictitious restaurant. 
 * You will be running RUHungry for a day by seating guests, 
 * taking orders, donation requests and restocking the pantry as necessary.
 * 
 * Compiling and executing:
 * 1. use the run or debug function to run the driver and test your methods 
 * 
 * @author Mary Buist
 * @author Kushi Sharma
*/

public class RUHungry {
    
    /*
     * Instance variables
     */

    // Menu: two parallel arrays. The index in one corresponds to the same index in the other.
    private String[] categoryVar; // array where containing the name of menu categories (e.g. Appetizer, Dessert).
    private MenuNode[] menuVar;   // array of lists of MenuNodes where each index is a category.
    
    // Stock: hashtable using chaining to resolve collisions.
    private StockNode[] stockVar;  // array of linked lists of StockNodes (use hashfunction to organize Nodes: id % stockVarSize)
    private int stockVarSize;

    // Transactions: orders, donations, restock transactions are recorded 
    private TransactionNode transactionVar; // refers to the first front node in linked list

    // Queue keeps track of people who've left the restaurant
    private Queue<People> leftQueueVar;  

    // Tables
    private People[] tables;        // array for people who are currently sitting
    private int[][]  tablesInfo;    // 2-D integer array where the first row contains how many seats there are at each table (each index)
                                    // and the second row contains "0" or "1", where 1 is the table is not available and 0 the opposite

    /*
     * Default constructor
     */
    public RUHungry () {
        categoryVar    = null;
        menuVar        = null;
        stockVar       = null;
        stockVarSize   = 0;
        transactionVar = null;
        leftQueueVar   = null;
        tablesInfo     = null;
        tables         = null;
    }

    /*
     * Get/Set methods
     */
    public MenuNode[] getMenu() { return menuVar; }
    public String[] getCategoryArray() { return categoryVar;}
    public StockNode[] getStockVar() { return stockVar; } 
    public TransactionNode getFrontTransactionNode() { return transactionVar; } 
    public TransactionNode resetFrontNode() {return transactionVar = null;} // method to reset the transactions for a new day
    public Queue<People> getLeftQueueVar() { return leftQueueVar; } 
    public int[][] getTablesInfo() { return tablesInfo; }

    /*
     * Menu methods
     */

    /**
     * 
     * This method populates the two parallel arrays menuVar and categoryVar.
     * 
     * Each index of menuVar corresponds to the same index in categoryVar (a menu category like Appetizers).
     * If index 0 at categoryVar is Appetizers then menuVar at index 0 contains MenuNodes of appetizer dishes.
     * 
     * 1. read the input file:
     *      a) the first number corresponds to the number of categories (aka length of menuVar and categoryVar)
     *      b) the next line states the name of the category (populate CategoryVar as you read each category name)
     *      c) the next number represents how many dishes are in that category - this will be the size of the linked list in menuVar for this category
     *      d) the next line states the name of the dish
     *      e) the first number in the next line represents how many ingredient IDs there are
     *      f) the next few numbers (all in the 100s) are each the ingredient ID
     * 
     * 2. As you read through the input file:
     *      a) populate the categoryVar array
     *      b) populate menuVar depending on which index (aka which category) you are in
     *          i) make a dish object (with filled parameters -- don't worry about "price" and "profit" in the dish object for right now)
     *          ii) create menuNode and insert at the front of menuVar (NOTE! there will be multiple menuNodes in one index)
     * 
     * @param inputFile - use menu.in file which contains all the dishes
     */

    public void menu(String inputFile) {

        StdIn.setFile(inputFile);
        // WRITE YOUR CODE HERE
        ArrayList<String> categoryVar2 = new ArrayList<>(StdIn.readInt()); 
        ArrayList<MenuNode> menuVar2 = new ArrayList<>(); 
        int numberIngredients;
        while(!StdIn.isEmpty())
        {
            //Create menuNodes to create a linked list 
            MenuNode categoryDishes = new MenuNode(null, null); 
            
            //Create the category
            String categoryName = StdIn.readString(); 
            categoryVar2.add(categoryName);

            //Adds all the dishes in the category 
            int numberOfDishes = StdIn.readInt(); 
            for(int i = 0; i < numberOfDishes; i++)
            {
                String nameOfDish = StdIn.readString() + StdIn.readLine(); 
                numberIngredients = StdIn.readInt(); 

                int[] ingredientIDs = new int[numberIngredients]; 
                for(int h = 0; h < numberIngredients; h++)
                {
                    ingredientIDs[h] = StdIn.readInt(); 
                }

                //Creates nodes to store these dishes into
                Dish dish = new Dish(categoryName, nameOfDish, ingredientIDs);
                MenuNode newNode = new MenuNode(dish, null);
                
                if (categoryDishes.getDish() == null) {
                    categoryDishes.setDish(dish);
                } else {
                    newNode.setNextMenuNode(categoryDishes);
                    categoryDishes = newNode;
                }
            }
            //Adds the linked lists to a arraylist
            menuVar2.add(categoryDishes);
        }

        //converts the arraylists into arrays. 
        String[] categoryVar3 = new String[categoryVar2.size()];
        MenuNode[] menuVar3 = new MenuNode[menuVar2.size()];
        for(int i = 0; i < categoryVar2.size(); i++)
        {
            categoryVar3[i] = categoryVar2.get(i);
        }
        for(int i = 0; i < menuVar2.size(); i++)
        {
            menuVar3[i] = menuVar2.get(i);
        }
        categoryVar = categoryVar3;
        menuVar = menuVar3; 
    }

    /** 
     * Find and return the MenuNode that contains the dish with dishName in the menuVar.
     * 
     *      ** GIVEN METHOD **
     *      ** DO NOT EDIT **
     * 
     * @param dishName - the name of the dish
     * @return the dish object corresponding to searched dish, null if dishName is not found.
     */

    public MenuNode findDish ( String dishName ) {

        MenuNode menuNode = null;

        // Search all categories since we don't know which category dishName is at
        for ( int category = 0; category < menuVar.length; category++ ) {

            MenuNode ptr = menuVar[category]; // set ptr at the front (first menuNode)
            
            while ( ptr != null ) { // while loop that searches the LL of the category to find the itemOrdered
                if ( ptr.getDish().getDishName().equals(dishName) ) {
                    return ptr;
                } else{
                    ptr = ptr.getNextMenuNode();
                }
            }
        }
        return menuNode;
    }

    /**
     * Find integer that corresponds to the index in menuVar and categoryVar arrays that has that category
     *              
     *      ** GIVEN METHOD **
     *      ** DO NOT EDIT **
     *
     * @param category - the category name
     * @return index of category in categoryVar
     */

    public int findCategoryIndex ( String category ) {
        int index = 0;
        for ( int i = 0; i < categoryVar.length; i++ ){
            if ( category.equalsIgnoreCase(categoryVar[i]) ) {
                index = i;
                break;
            }
        }
        return index;
    }

    /*
     * Stockroom methods
     */

    /**
     * PICK UP LINE OF THE METHOD:
     * *can I insert myself into your life? cuz you always help me sort 
     * out my problems and bring stability to my mine*
     * 
     * ***********
     * This method adds a StockNode into the stockVar hashtable.
     * 
     * 1. get the id of the given newNode and use a hash function to get the index at which the
     *    newNode is being inserted.
     * 
     * HASH FUNCTION: id % stockVarSize
     * 
     * 2. insert at the front of the linked list at the specific index
     * 
     * @param newNode - StockNode that needs to be inserted into StockVar
     */

    public void addStockNode ( StockNode newNode ) {

        // WRITE YOUR CODE HERE
        int index = newNode.getIngredient().getID(); 
        if(stockVar[index] != null)
        {
            newNode.setNextStockNode(stockVar[index]);
            stockVar[index] = newNode;
        }
        else if (stockVar[index] == null)
        {
            stockVar[index] = newNode;
        }
    }   

    /**
     * This method deletes an ingredient (aka the specific stockNode) from stockVar.
     * 
     * 1. find the node that corresponds to the ingredient (based on the ingredientName)
     * 
     * 2. delete the node from stockVar
     *      a) you have to visit each index and look at each node in the corresponding linked list 
     *      b) this is NOT efficient. Hashtables are not good if you can't use the key to find the item.
     * 
     * @param ingredientName - name of the ingredient
     */

     public void deleteStockNode(String ingredientName) {
        for (int i = 0; i < stockVar.length; i++) {
            StockNode current = stockVar[i];
            StockNode prev = null;
    
            while (current != null && current.getIngredient() != null) {
                if (current.getIngredient().getName().equals(ingredientName)) {
                    // Delete the node
                    if (prev == null) {
                        // If it's the first node in the list
                        stockVar[i] = current.getNextStockNode();
                    } else {
                        // If it's not the first node
                        prev.setNextStockNode(current.getNextStockNode());
                    }
                    return; // Exit the method after deletion
                }
    
                // Move to the next nodes
                prev = current;
                current = current.getNextStockNode();
            }
        }
    }

    /**
     * This method finds an ingredient from StockVar (given the ingredientID)
     * 
     * 1. find the node based upon the ingredient ID (you can go to the specific index using the hash function!)
     *      (a) this is an efficient search as it looks only at the linked list which the key hash to
     * 2. find and return the node
     *  
     * @param ingredientID - the ID of the ingredient
     * @return the StockNode corresponding to the ingredientID, null otherwise
     */
   
     public StockNode findStockNode(int ingredientID) {
        if (stockVarSize <= 0) {
            System.out.println("Error: stockVarSize is zero. Cannot perform modulo operation.");
            return null;
        }
    
        int index = ingredientID % stockVarSize;
        if (index < 0) {
            index += stockVarSize;  // Ensure a non-negative index
        }
    
        StockNode currentNode = stockVar[index];
    
        while (currentNode != null) {
            if (currentNode.getIngredient().getID() == ingredientID) {
                return currentNode;
            }
            currentNode = currentNode.getNextStockNode();
        }
    
        // If the ingredientID is not found, print a message and return null
        System.out.println("Ingredient with ID " + ingredientID + " not found in stockVar.");
        return null;
    }

    /**
     * This method is to find an ingredient from StockVar (given the ingredient name).
     * 
     *      ** GIVEN METHOD **
     *      ** DO NOT EDIT **
     * 
     * @param ingredientName - the name of the ingredient
     * @return the specific ingredient StockNode, null otherwise
     */

    public StockNode findStockNode (String ingredientName) {
        
        StockNode stockNode = null;
        
        for ( int index = 0; index < stockVar.length; index ++ ){
            
            StockNode ptr = stockVar[index];
            
            while ( ptr != null ){
                if ( ptr.getIngredient().getName().equalsIgnoreCase(ingredientName) ){
                    return ptr;
                } else {  
                    ptr = ptr.getNextStockNode();
                }
            }
        }
        return stockNode;
    }

    /**
     * This method updates the stock amount of an ingredient.
     * 
     * 1. you will be given the ingredientName **OR** the ingredientID:
     *      a) the ingredientName is NOT null: find the ingredient and add the given stock amount to the
     *         current stock amount
     *      b) the ingredientID is NOT -1: find the ingredient and add the given stock amount to the
     *         current stock amount
     * 
     * (FOR FUTURE USE) SOMETIMES THE STOCK AMOUNT TO ADD MAY BE NEGATIVE (TO REMOVE STOCK)
     * 
     * @param ingredientName - the name of the ingredient
     * @param ingredientID - the id of the ingredient
     * @param stockAmountToAdd - the amount to add to the current stock amount
     */
    
     public void updateStock (String ingredientName, int ingredientID, int stockAmountToAdd) {
        
        // WRITE YOUR CODE HERE
        if(ingredientName != null)
        {
            findStockNode(ingredientName).getIngredient().updateStockLevel(stockAmountToAdd);;
            
        }
        else if(ingredientID != -1)
        {
            findStockNode(ingredientID).getIngredient().updateStockLevel(stockAmountToAdd);
        }
    }
    /**
     * PICK UP LINE OF THE METHOD:
     * *are you a single ‘for’ loop? cuz i only have i’s for you*
     * 
     * ***********
     * This method goes over menuVar to update the price and profit of each dish,
     * using the stockVar hashtable to lookup for ingredient's costs.
     * 
     * 1. For each dish in menuVar, add up the cost for each ingredient (found in stockVar), 
     *    and multiply the total by 1.2 to get the final price.
     *      a) update the price of each dish
     *  HINT! --> you can use the methods you've previously made!
     * 
     * 2. Calculate the profit of each dish by getting the totalPrice of ingredients and subtracting from 
     *    the price of the dish itself.
     * 
     */

     public void updatePriceAndProfit() {
        for (int i = 0; i < menuVar.length; i++) {
            MenuNode currentMenuNode = menuVar[i];
    
            while (currentMenuNode != null) {
                Dish dish = currentMenuNode.getDish();
                int[] dishStock = dish.getStockID();
    
                double totalCost = 0.0;
                for (int h = 0; h < dishStock.length; h++) {
                    StockNode stockNode = findStockNode(dishStock[h]);
                    if (stockNode != null) {
                        totalCost += stockNode.getIngredient().getCost();
                    }
                }
    
                double finalPrice = totalCost * 1.2;
                dish.setPriceOfDish(finalPrice);
    
                // Calculate profit
                double profit = finalPrice - totalCost;
                dish.setProfit(profit);
                currentMenuNode = currentMenuNode.getNextMenuNode();
            }
        }
    }
    
    /**
     * PICK UP LINE OF THE METHOD:
     * *are you a decimal? cuz the thought of you 
     * always floats in my head and the two of use would make double*
     * 
     * ************
     * This method initializes and populates stockVar which is a hashtable where each index contains a 
     * linked list with StockNodes.
     * 
     * 1. set and read the inputFile (stock.in):
     *      a) first integer (on line 1) is the size of StockVar *** update stockVarSize AND create the stockVar array ***
     *      b) first integer of next line represents the ingredientID
     *          i) example: 101 on line 2
     *      c) use StdIn.readChar() to get rid of the space between the id and the name
     *      d) the string that follows is the ingredient name (NOTE! --> there are spaces between certain strings)
     *          i) example: Lettuce
     *      e) the double on the next line corresponds to the ingredient's cost
     *          i) example: 3.12 on line 3
     *      f) the next integer is the stock amount for that ingredient
     *          i) example: 30 on line 3
     * 
     * 2. create a Ingredient object followed by a StockNode then add to stockVar
     *      HINT! --> you may use previous methods written to help you!
     * 
     * @param inputFile - the input file with the ingredients and all their information (stock.in)
     */

    public void createStockHashTable (String inputFile){
        StdIn.setFile(inputFile);

        // Read the size of StockVar
        stockVarSize = StdIn.readInt();
        stockVar = new StockNode[stockVarSize];

        // Consume the newline after reading the size
        StdIn.readLine();

        // Iterate through the input file to read ingredient information
        while (!StdIn.isEmpty()) {
            int ingredientID = StdIn.readInt();
            StdIn.readChar(); // Skip the space between ID and name
            String ingredientName = StdIn.readLine().trim();
            double cost = StdIn.readDouble();
            int stockAmount = StdIn.readInt();

            // Create Ingredient object
            Ingredient ingredient = new Ingredient(ingredientID, ingredientName, stockAmount, cost);

            // Create StockNode
            StockNode stockNode = new StockNode(ingredient,null);

            // Add to stockVar array
            int index = ingredientID % stockVarSize; // Using ingredientID as the index
            if (stockVar[index] == null) {
                stockVar[index] = stockNode;
            } else {
                // Add to the beginning of the linked list
                stockNode.setNextStockNode(stockVar[index]);
                stockVar[index] = stockNode;
            }
        }
    }

    /*
     * Transaction methods
     */

    /**
     * This method adds a TransactionNode to the END of the transactions linked list.
     * The front of the list is transactionVar.
     *
     * 1. create a new TransactionNode with the TransactionData paramenter.
     * 2. add the TransactionNode at the end of the linked list transactionVar.
     * 
     * @param data - TransactionData node to be added to transactionVar
     */

     public void addTransactionNode(TransactionData data) {
        if (transactionVar == null) {
            // If the list is empty, create a new node and make it the head
            transactionVar = new TransactionNode(data, null);
        } else {
            TransactionNode current = transactionVar;
    
            // Traverse the list until the last node
            while (current.getNext() != null) {
                current = current.getNext();
            }
    
            // Add the new node at the end
            current.setNext(new TransactionNode(data, null));
        }
    }

    /**
     * PICK UP LINE OF THE METHOD:
     * *are you the break command? cuz everything else stops when I see you*
     * 
     * *************
     * This method checks if there's enough in stock to prepare a dish.
     * 
     * 1. use findDish() method to find the menuNode node for dishName
     * 
     * 2. retrieve the Dish, then traverse ingredient array within the Dish
     * 
     * 3. return boolean based on whether you can sell the dish or not
     * HINT! --> once you determine you can't sell the dish, break and return
     * 
     * @param dishName - String of dish that's being requested
     * @param numberOfDishes - int of how many of that dish is being ordered
     * @return boolean
     */

    public boolean checkDishAvailability ( String dishName, int numberOfDishes ){
        
        // WRITE YOUR CODE HERE
        if(findDish(dishName) != null)
        {
            MenuNode dishFound = findDish(dishName); 
            int min = Integer.MAX_VALUE; 
            int ingredientStock; 
            int [] dishIngredients = dishFound.getDish().getStockID();
            for(int i = 0; i < dishIngredients.length; i++)
            {
                ingredientStock = findStockNode(dishIngredients[i]).getIngredient().getStockLevel(); 
                if(min > ingredientStock)
                {
                    min = ingredientStock; 
                }
            }
            if(min >= numberOfDishes)
            {
                return true; 
            }

        }
        return false; 
    }

    /**
     * PICK UP LINE OF THE METHOD:
     * *if you were a while loop and I were a boolean, we could run 
     * together forever because I’ll always stay true to you*
     * 
     * ***************
     * This method simulates a customer ordering a dish. Use the checkDishAvailability() method to check whether the dish can be ordered.
     * 
     * If the dish can be prepared
     *      - create a TransactionData object of type "order" where the item is the dishName, the amount is the quantity being ordered, and profit is the dish profit multiplied by quantity.
     *      - then add the transaction as a successful transaction (call addTransactionNode()) and updates the stock accordingly.
     * 
     * If the dish cannot be prepared
     *      - create a TransactionData object of type "order" where the item is the dishName, the amount is the quantity being ordered, and profit is 0 (zero).
     *      - then add the transaction as an UNsuccessful transaction and,
     *      - simulate the customer trying to order other dishes in the same category linked list:
     *          - if the dish that comes right after the dishName can be prepared, great. If not, try the next one and so on.
     *          - you might have to traverse through the entire category searching for a dish that can be prepared. If you reach the end of the list, start from the beginning until you have visited EVERY dish in the category.
     *          - It is possible that no dish in the entire category can be prepared.
     *          - Note: the next dish the customer chooses is always the one that comes right after the one that could not be prepared. 
     * 
     * 
     * @param dishName - String of dish that's been ordered
     * @param quantity - int of how many of that dish has been ordered
     */

    public void order ( String dishName, int quantity ){

        // WRITE YOUR CODE HERE

        //Checks if you can order it. 
        boolean orderPossible = checkDishAvailability(dishName, quantity);
        TransactionData orderTransaction = new TransactionData("order", dishName, quantity, quantity, orderPossible); 
        int [] ingredientIds; 
        MenuNode currentNode;
        if (orderPossible == true)
        {
            orderTransaction.setProfit(findDish(dishName).getDish().getProfit() * quantity);
            orderTransaction.setSuccess(orderPossible);
            ingredientIds = findDish(dishName).getDish().getStockID();
            for(int i = 0; i < ingredientIds.length; i++)
            {
                findStockNode(ingredientIds[i]).getIngredient().setStockLevel(findStockNode(ingredientIds[i]).getIngredient().getStockLevel() - quantity);

            }
            addTransactionNode(orderTransaction);

        }
        else if (orderPossible == false){
            //Adds the order to the transaction list 
            orderTransaction.setProfit(0);
            orderTransaction.setSuccess(orderPossible);
            addTransactionNode(orderTransaction);
            TransactionData nextOrderTrans = new TransactionData();

            //Finds the next available option in the category 
            if(findDish(dishName).getNextMenuNode() != null)
            {
                currentNode = findDish(dishName).getNextMenuNode();
            }
            else{
                currentNode = menuVar[findCategoryIndex(findDish(dishName).getDish().getCategory())];
            }
            
            MenuNode categoryHead = findDish(dishName); // Save the head of the category
            boolean found = false;
            orderPossible = checkDishAvailability(currentNode.getDish().getDishName(), quantity);
            while(currentNode != categoryHead)
            {
                if (orderPossible == true)
                {
                    TransactionData orderTransaction2 = new TransactionData("order", currentNode.getDish().getDishName(), quantity, currentNode.getDish().getProfit() * quantity, orderPossible); 
                    // orderTransaction2.setItem(currentNode.getDish().getDishName());
                    // orderTransaction2.setProfit(currentNode.getDish().getProfit() * quantity);
                    // orderTransaction2.setSuccess(orderPossible);
                    ingredientIds = currentNode.getDish().getStockID();
                    for(int i = 0; i < ingredientIds.length; i++)
                    {
                        findStockNode(ingredientIds[i]).getIngredient().setStockLevel(findStockNode(ingredientIds[i]).getIngredient().getStockLevel() - quantity);
                    }
                    addTransactionNode(orderTransaction2);
                    found = true; 
                    break; 
                }

                if(currentNode.getNextMenuNode() != null)
                {
                    currentNode = currentNode.getNextMenuNode(); 
                }
                else{
                    currentNode = menuVar[findCategoryIndex(findDish(dishName).getDish().getCategory())];
                }
            }
            if(!found)
            {
                TransactionData nextOrderTrans2;
                if(findDish(dishName).getNextMenuNode() != null)
                {
                    nextOrderTrans2 = new TransactionData("order", findDish(dishName).getNextMenuNode().getDish().getDishName(), quantity, 0, orderPossible); 
                    addTransactionNode(nextOrderTrans2);
                }
                else{
                    nextOrderTrans.setItem(menuVar[findCategoryIndex(findDish(dishName).getDish().getCategory())].getDish().getDishName());
                    addTransactionNode(nextOrderTrans);
                }
            }
        }

    }
    
    /**
     * This method returns the total profit for the day
     *
     * The profit is computed by traversing the transaction linked list (transactionVar) 
     * adding up all the profits for the day
     * 
     * @return profit - double value of the total profit for the day
     */

     public double profit() {
        // Use a local variable for iteration, so you don't modify the original list
        TransactionNode current = transactionVar;
    
        double profitValue = 0;
        while (current != null) {
            profitValue += current.getData().getProfit();
            current = current.getNext();
        }
    
        return profitValue;
    }

    /**
     * This method simulates donation requests, successful or not.
     * 
     * 1. check whether the profit is > 50 and whether there's enough ingredients in stock.
     * 
     * 2. add transaction to transactionVar
     * 
     * @param ingredientName - String of ingredient that's been requested
     * @param quantity - int of how many of that ingredient has been ordered
     * @return void
     */

    public void donation ( String ingredientName, int quantity ){

        // WRITE YOUR CODE HERE
        boolean run = profit() > 50; 
        TransactionData donationTrans = new TransactionData("donation", ingredientName, quantity, 0, run);
        if(run)
        {
            int ingredientStock = findStockNode(ingredientName).getIngredient().getStockLevel();
            if (ingredientStock > quantity)
            {
                findStockNode(ingredientName).getIngredient().setStockLevel(findStockNode(ingredientName).getIngredient().getStockLevel() - quantity);
                
            }
            else{
                donationTrans.setSuccess(false);
            }
        }
        addTransactionNode(donationTrans);
        
    }

    /**
     * This method simulates restock orders
     * 
     * 1. check whether the profit is sufficient to pay for the total cost of ingredient
     *      a) (how much each ingredient costs) * (quantity)
     *      b) if there is enough profit, adjust stock and profit accordingly
     * 
     * 2. add transaction to transactionVar
     * 
     * @param ingredientName - ingredient that's been requested
     * @param quantity - how many of that ingredient needs to be ordered
     */

    public void restock ( String ingredientName, int quantity ){

        // WRITE YOUR CODE HERE
        double profitDay = profit(); 
        double cost = findStockNode(ingredientName).getIngredient().getCost() * quantity;
        TransactionData restockTrans = new TransactionData("restock", ingredientName, quantity, cost, true);
        if(profitDay > cost)
        {
            findStockNode(ingredientName).getIngredient().setStockLevel(findStockNode(ingredientName).getIngredient().getStockLevel() + quantity);
            restockTrans.setSuccess(true);
            restockTrans.setProfit(0 - cost);
            
        }
        else{
            restockTrans.setSuccess(false);
            restockTrans.setProfit(0);
        }
        addTransactionNode(restockTrans);
        profit(); 
    }

   /*
    * Seat guests/customers methods
    */

    /**
     * Method to populate tables (which is a 2d integer array) based upon input file
     * 
     * First row of tables[][]: contains the total number of seats available at a table (each table is an index in first row)
     * Second row of tables[][]: initializes all indices to 0
     *      0 --> table is available
     *      1 --> table is occupied
     * 
     *          ** GIVEN METHOD **
     *          ** DO NOT EDIT **
     * 
     * @param inputFile - tables1.in (contains all the tables in the RUHungry restaurant)
     * @return void (aka nothing)
     */

    public void createTables ( String inputFile ) { 

        StdIn.setFile(inputFile);
        int numberOfTables = StdIn.readInt();
        tablesInfo = new int[2][numberOfTables];
        tables = new People[numberOfTables];
        
        for ( int t = 0; t < numberOfTables; t++ ) {
            tablesInfo[0][t] = StdIn.readInt() * StdIn.readInt();
        }
    }

    /**
     * PICK UP LINE OF THE METHOD:
     * *are you a linked list? cuz nothing could stock up to you and 
     * you’re pretty queue(te)*
     * 
     * ***************
     * This method simulates seating guests at tables. You are guaranteed to be able to sit everyone from the waitingQueue eventually.
     *       
     * 1. initialize leftQueueVar a People queue that represents the people that have left the restaurant
     * 
     * 2. while there are people waiting to be sat:
     *      - Starting from index 0 (zero), seat the next party in the first available table that fits their party.
     *      - If there is no available table for the next party, kick a party out from the tables array:
     *          1. starting at index 0 (zero), find the first table big enough to hold the next party in line.
     *          2. remove the current party, add them to the leftQueueVar.
     *          3. seat the next party in line.
     * 
     * tableInfo contains the number of seats per table as well as if the table is occupied or not.
     * tables contains the People object (party) currently at the table.
     * 
     * Note: After everyone has been seated (waitingQueue is empty), remove all the parties from tables and add then to the leftQueueVar.
     * 
     * @param waitingQueue - queue containing people waiting to be seated (each element in queue is a People <-- you are given this class!)
     */

    public void seatAllGuests ( Queue<People> waitingQueue ) {

        // WRITE YOUR CODE HERE
    }
}