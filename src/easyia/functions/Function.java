/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyia.functions;

/**
 * Interface Function defines the methods needed for 
 * Function objects
 * @author aldo1
 */
public interface Function {

    /**
     * Returns the result of applying a function to an specified
     * input
     * @param input
     * @return
     */
    double apply(double input);

    /**
     * Returns a Function with the Derivative of this function
     * @return
     */
    Function getDerivative();

    /**
     * Returns a Function with the Integral of this function
     * @return
     */
    Function getIntegral();
}
