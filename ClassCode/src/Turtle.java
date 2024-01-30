import java.awt.Color;
import edu.princeton.cs.algs4.*;

public class Turtle {
    
    // Abstract Data Type (ADT) Turtle

    /****** The instance of a class is an object  *****/

    // Instance variables have unique values to each object (instance of the class)
    // Private modifier hides the instance variable from other classes
    // (x,y) coordinate
    private double x;
    private double y;

    // direction in which the turtle is facing
    private double angle;

    // turtle color
    private Color color;

    /******* Constructors *******/
    // Constructors have the same of the same name of the class
    // Constructors create and initialize the object (initializing the instance variables)

    // Default constructor: no argument constructor
    public Turtle () {
        x = y = 0.0;
        angle = 90.0;
        color = new Color(0,0,0); // black color
    }

    // 4 argument constructor
    public Turtle (double x, double y, double angle, Color color) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.color = color;
    }

    // Instance methods of the class Turtle
    // Instance methods DO NOT HAVE THE WORD static
    // Instance methods manipulate (have access) to instance variables

    // toString() method returns the string representation of an object
    public String toString() {
        return "Turtle (" + x +", "+y +") direction " + angle + " " + color.toString();
    }

    // turnLeft by updating the turtle angle
    public void turnLeft (double delta) {
        angle += delta;
    }

    // moves the turle forward by stepSize
    // draw a line from the old x,y to the new x,y
    public void moveForward (double stepSize) {
        double oldx = x;
        double oldy = y;
        x += stepSize * Math.cos(Math.toRadians(angle));
        y += stepSize * Math.sin(Math.toRadians(angle));

        StdDraw.setPenColor(color);
        StdDraw.line(oldx, oldy, x, y);
    }

    // accessor methods
    public double getX () {
        return x;
    }
    public double getY () {
        return y;
    }
    public double getAngle () {
        return angle;
    }
    public Color getColor () {
        return color;
    }
    public void setColor (Color color) {
        // update the turtle color
        this.color = color;
    }

    // equals method to compare this object with another one
    // Object class is a class in Java, all classes are derived from Object
    public boolean equals (Object other) {

        if (other instanceof Turtle) { // checks if other is an instance of the class Turtle
            // other is of Turtle type
            Turtle o = (Turtle)other;

            return this.x == o.x &&
                    this.y == o.y &&
                    this.angle == o.angle &&
                    this.color.equals(o.color); // cannot compare colors like this: this.color == o.color
        } else {
            // other is not of Turtle type
            return false;
        }
    }

    // static methods do not have access to any instance variables or methods
    public static void main (String[] args) {

       Turtle t1 = new Turtle(); // default constructor
       Turtle t2 = new Turtle(0.5, 0.5, 45, new Color(0,255,0));

       StdOut.println(t1);
       StdOut.println(t2);
       StdOut.println(t1.equals(t2));

       t2.moveForward(0.1);
       StdOut.println(t2);
    }
 }
