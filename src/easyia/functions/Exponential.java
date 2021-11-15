/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyia.functions;

/**
 * The class Exponential provides a simple Function implementation
 * for an exponential function
 * @author aldo1
 */
public class Exponential implements Function{
    
    private double[] coefficients = new double[2];

    /**
     * Constructor for a function f(x)= A*e^(B*x) where A and B are
     * specified
     * @param coefficientA
     * @param coefficientB
     */
    public Exponential(double coefficientA, double coefficientB) {
        this.coefficients[0] = coefficientA;
        this.coefficients[1] = coefficientB;
    }
    
    /**
     * Returns the specified coefficient
     * @param coefficient
     * @return
     */
    public double getCoefficient(int coefficient){
        return coefficients[coefficient];
    }
    
    /**
     * Sets the specified coefficient
     * @param coefficient
     * @param value
     */
    public void setCoefficient(int coefficient, double value){
        coefficients[coefficient] = value;
    }
    
    @Override
    public double apply(double input){
        return coefficients[0]*Math.pow(Math.E, coefficients[1]*input);
    }
    
    @Override
    public Exponential getDerivative(){
        return new Exponential(coefficients[0]*coefficients[1], coefficients[1]);
    }

    @Override
    public String toString() {
        return coefficients[0] + "e^(" + coefficients[1] + "x)";
    }

    @Override
    public Function getIntegral() {
        return new Exponential(coefficients[0]/coefficients[1], coefficients[1]);
    }

}
