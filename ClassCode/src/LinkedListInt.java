public class LinkedListInt {
    
    // linked list of integers

    // private class: only visible inside LinkedListInt
    private class Node {
        // instance variables of the Node class
        private int data;  // data part
        private Node next; // link part

        //Constructor for the node class
        Node (int dataToInsert, Node link) {
            data = dataToInsert;
            next = link;
        }
    }

    // instance variables of the LinkedListInt class
    private Node front; // reference to the first node of the LL

    //Constructor for the LinkedListInt Class
    LinkedListInt () {
        front = null; // empty LL
    }

    // inserts a node to the front of the LL
    // Running time? f(n) = 2 => O(1)
    public void addToFront (int nodeData) {
        Node newNode = new Node(nodeData, front);
        front = newNode;
    }

    // Iterates through the LL and prints all its data
    // Running time?
    // Count ptr assignment: f(n) = n + 1 => O(n)
    public void print () {
        Node ptr = front; // ptr is poiting to the first node of LL
        while ( ptr != null ) {
            System.out.print(ptr.data + " -> ");
            ptr = ptr.next;  // updates ptr to point to the next node in the LL
        }
        System.out.println("\\");
    }

    public boolean search (int target) {
        Node ptr = front;
        while ( ptr != null ) {
            if ( ptr.data == target ) {
                return true;
            }
            ptr = ptr.next; // ptr points to the next node in the LL
        }
        return false;
    }

    public void deleteFront () {

        // BUG here, what if front is null?

        front = front.next; // front points to the second node of the LL
        // the old front will be removed when the garbage collector wakes up
    }

    public boolean insertAfter (int target, int dataToInsert) {

        Node ptr = front;
        while ( ptr != null ) {
            if ( ptr.data == target ) {
                // insert dataToInsert after the object that holds target
                Node newNode = new Node(dataToInsert, ptr.next);
                ptr.next = newNode;
                return true;
            }

            // ptr.data is not equal to target
            // move ptr to the next node
            ptr = ptr.next;
        }
        // target was not found
        return false;
    }

    // Running time:
    public boolean delete (int dataToDelete) {

        Node ptr = front;
        Node prev = null;

        while ( ptr != null ) {
            if ( ptr.data == dataToDelete ) {

                // BUG HERE, what if dataToDelete is the first node?
                
                prev.next = ptr.next; // remove the node pointed by ptr
                return true;
            }
            //  moving the two pointers together
            prev = ptr;
            ptr = ptr.next;
        }
        // dataToDelete not found
        return false;
    }

    // client: test the LinkedListInt class
    public static void main (String[] args) {

        LinkedListInt lli = new LinkedListInt();
        lli.addToFront(9);
        lli.addToFront(8);
        lli.addToFront(5);
        lli.addToFront(2);
        lli.addToFront(1);
        lli.print();
        lli.deleteFront();
        lli.print();
        lli.insertAfter(5, 6);
        lli.print();
    }
}
