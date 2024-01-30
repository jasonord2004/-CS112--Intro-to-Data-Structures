package avengers;

public class Generators {
    
    private Integer genNumber;
    private Double funcVal;

    public Generators(int g, double f) {
        genNumber = g;
        funcVal = f;

    }

    public Generators(){
        this(0, 0);
    }

    public Integer getGenerator () { return genNumber; }
    public void setGenNumber (Integer g) { genNumber = g; }

    public Double getFuncVal () { return funcVal; }
    public void setFuncVal (Double f) { funcVal = f; }

}
