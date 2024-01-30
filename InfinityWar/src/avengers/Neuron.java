package avengers;

public class Neuron{
    private String name;
    private Neuron next;

    public Neuron(String n) {
        name = n;

    }

    public Neuron(){
        this.name = null;
        this.next = null;
    }

    public String getName () { return name; }
    public void setName (String n) { name = n; }

    public Neuron getNext () { return next; }
    public void setNext (Neuron nxt) { next = nxt; }
}
