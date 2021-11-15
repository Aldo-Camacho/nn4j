/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyia.functions;


/**
 * The class Constant provides a simple Function implementation
 * for a constant function
 * @author aldo1
 */
public class Constant implements Function{
    private double value;

    /**
     * Constructor for a function f(x)=c where c is specified
     * @param value The constant value
     */
    public Constant(double value) {
        this.value = value;
    }

    /**
     * Returns the value of the constant.
     * @return
     */
    public double getValue() {
        return value;
    }

    /**
     * Sets the value of the constant
     * @param value
     */
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public double apply(double input) {
        return value;
    }

    @Override
    public Function getDerivative() {
        return new Constant(0);
    }

    @Override
    public Function getIntegral() {
        return new Power(value,1);
    }
    
    @Override
    public String toString() {
        return "" + value; 
    }
}
