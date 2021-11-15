/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyia.functions;

/**
 * Class Polynomial provides a simple implementation for
 * polynomial functions. Implement the Function interface
 * @author aldo1
 */
public class Polynomial implements Function{
    int order;
    double[] coefficients;
    
    /**
     * Constructor for a polynomial of order n
     * @param order
     */
    public Polynomial(int order){
        this.order = order;
        this.coefficients = new double[order+1];
    }
    
    /**
     * Constructor for a polynomial of order n
     * Accepts the coefficients for all powers
     * of x
     * @param coefficients
     */
    public Polynomial(double... coefficients){
        this.coefficients = coefficients.clone();
        this.order = coefficients.length - 1;
    }
    
    /**
     * Sets the coefficient C of the n power in Cx^n
     * @param order The power at which the coefficient is set
     * @param value The value of the coefficient
     */
    public void setCoefficient(int order, double value){
        this.coefficients[order] = value;
    }
    
    /**
     * Returns the coefficient C of the n power of x
     * @param order The power os which the coefficient is returned
     * @return
     */
    public double getCoefficient(int order){
        return coefficients[order];
    }

    /**
     * Returns the order of the Polynomial
     * @return
     */
    public int order() {
        return order;
    }
    
    /**
     * Returns a Polynomial result of adding this and 
     * another Polynomial b
     * @param b
     * @return
     */
    public Polynomial add(Polynomial b){
        Polynomial auxiliar;
        Polynomial output;
        if (this.order() > b.order()){
            auxiliar = b;
            output = new Polynomial(this.coefficients);
        } else {
            output = new Polynomial(b.coefficients);
            auxiliar = this;
        }
        for (int i=0; i <= auxiliar.order(); i++){
            output.coefficients[i] += auxiliar.coefficients[i];
        }
        return output;
    }
    
    /**
     * Returns a Polynomial result of multiplying this and 
     * another Polynomial b
     * @param b
     * @return
     */
    public Polynomial product(Polynomial b){
        Polynomial output = new Polynomial(this.order + b.order);
        for (int i=0; i <= this.order; i++){
            for (int j = 0; j <= b.order; j++) {
                output.coefficients[i+j] += this.coefficients[i]*b.coefficients[j];
                System.out.println(output);
            }
        }
        return output;
    }
    
    @Override
    public double apply(double input){
        double output = coefficients[0];
        for (int i=1; i <= order; i++){
            output += coefficients[i]*Math.pow(input, i);
        }
        return output;
    }
    
    @Override
    public Function getDerivative(){
        
        if (order > 1){
            Polynomial output;
            output = new Polynomial(order-1);
            for (int i=1; i<=order; i++){
                output.coefficients[i-1] = this.coefficients[i]*i;
            }
            return output;
        }
        else if (order == 1){
            return new Constant(coefficients[1]);
        }
        else {
            return new Constant(0);
        }
    }

    @Override
    public Function getIntegral() {
        Polynomial integral = new Polynomial(order+1);
        for(int i = 0; i <= order; i++){
            integral.coefficients[i+1] = coefficients[i]/(i+1);
        }
        return integral;
    }

    @Override
    public String toString() {
        String s = coefficients[0] + "";
        if (order > 0){
            for (int i=1; i<=order; i++){
                s = coefficients[i] + "x^" + i + (coefficients[i-1]<0 ? "":"+") + s ;
            }
        }
        return s; 
    }
}
