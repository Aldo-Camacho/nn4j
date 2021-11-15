/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyia.neuralnetworks;

import easyia.linearalgebra.Vector;

/**
 * The class Neuron provides a basic implementation for
 * single-neuron neural networks
 * @author aldo1
 */
public class Neuron {
    private Vector weights;
    private double bias = 0;
    private TransferFunction transferFunction = TransferFunction.PURELIN;

    /**
     * Contructor that accepts the number of inputs theat 
     * the neuron will accept
     * @param numberOfInputs
     */
    public Neuron (int numberOfInputs){
        weights = new Vector(numberOfInputs);
    }

    /**
     * Returns the weights vector
     * @return
     */
    public Vector getWeights() {
        return weights;
    }

    /**
     * Sets the weight vector
     * @param weightVector
     */
    public void setWeights(double... weightVector) {
        this.weights = new Vector(weightVector);
    }

    /**
     * Returns the bias of the neuron
     * @return
     */
    public double getBiases() {
        return bias;
    }

    /**
     * Sets the bias of the neuron
     * @param bias
     */
    public void setBias(double bias) {
        this.bias = bias;
    }

    /**
     * Returns the transfer function of the neuron
     * @return
     */
    public TransferFunction getTransferFunction() {
        return transferFunction;
    }

    /**
     * Sets the transfer function
     * @param transferFunction
     */
    public void setTransferFunction(TransferFunction transferFunction) {
        this.transferFunction = transferFunction;
    }
    
    /**
     * Returns the neuron output before applying the
     * transfer function
     * @param input
     * @return
     */
    public double getNeuron(Vector input) {
        try{
        return weights.dotProduct(input)+bias;
        } catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    /**
     * Returns the neuron output after applying the
     * transfer function
     * @param input
     * @return
     */
    public double getAxon(Vector input){
        try{
        return transferFunction.apply(getNeuron(input));
        } catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    /**
     * Sets all weights to a random value
     */
    public void setWeightsRandom(){
        for(int i=0; i<weights.length(); i++){
                weights.setElement(i, Math.random());
        }
    }
    
    /**
     * Set bias to a random value
     */
    public void setBiasRandom(){
        bias = Math.random();
    }
    
    /**
     * Uses the ADALINE algorithm to train the neuron
     * @param inputs
     * @param targets
     * @param alpha
     * @param epochs
     * @return
     */
    public Vector learn(Vector[] inputs, Vector targets, double alpha, int epochs) {
        if (inputs.length != targets.length()){
            return null;
        }
        
        setWeightsRandom();
        setBiasRandom();
        
        Vector meanSquaredError = new Vector(epochs);
        double mse = 0;
        
        for (int i=0; i<epochs; i++){
            for (int j = 0; j<inputs.length;j++){
                double axon = getAxon(inputs[j]);
                double error = targets.getElement(j)-axon; 
                mse = mse+ error*error;   
                error = error*alpha;
                weights = weights.add(inputs[j].scalarProduct(error));
                bias = bias + error;
            }
            meanSquaredError.setElement(i, mse/inputs.length);
            mse=0;
        }
        
        return meanSquaredError;
    }
    
}
