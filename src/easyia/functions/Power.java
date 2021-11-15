/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyia.functions;

/**
 * The class Power provides a simple implementation for
 * power of x functions. Implement the Function interface
 * @author aldo1
 */
public class Power implements Function{
    double[] coefficients = new double[2];

    /**
     * Constructor dunction a f(x) = A*x^B
     * @param A
     * @param B
     */
    public Power(double A, double B) {
        this.coefficients[0] = A;
        this.coefficients[1] = B;
    }
    
    @Override
    public double apply(double input) {
        return coefficients[0]*Math.pow(input, coefficients[1]);
    }

    @Override
    public Function getDerivative() {
        return new Power(coefficients[0]*coefficients[1], coefficients[1]-1);
    }

    @Override
    public Function getIntegral() {
        return new Power(coefficients[0]/(coefficients[1] + 1), coefficients[1] + 1);
    }
    
}
