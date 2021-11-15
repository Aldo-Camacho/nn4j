/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyia.neuralnetworks;

import easyia.linearalgebra.Matrix;
import easyia.linearalgebra.Vector;

/**
 * Provides a basic implementation for generic single-layer
 * neural networks
 * @author aldo1
 */
public class Layer {
    private Matrix weights;
    private Vector biases;
    private TransferFunction transferFunction;

    /**
     * Constructor for a single-layer neural network
     * specifying the weights, biases and transfer function
     * @param weightMatrix
     * @param biases
     * @param transferFunction
     */
    public Layer(Matrix weightMatrix, Vector biases, TransferFunction transferFunction) {
        this.weights = weightMatrix;
        this.biases = biases;
        this.transferFunction = transferFunction;
    }
    
    /**
     * Constructor for a single layer neural network 
     * specifying the number of inputs and number of neurons
     * @param numberOfInputs
     * @param numberOfNeurons
     */
    public Layer(int numberOfInputs, int numberOfNeurons){
        this(new Matrix(numberOfNeurons,numberOfInputs), new Vector(numberOfNeurons), TransferFunction.PURELIN);
    }

    /**
     * Returns the weights of the layer
     * @return
     */
    public Matrix getWeights() {
        return weights;
    }

    /**
     * Sets the weights of the layer
     * @param weigthMatrix
     */
    public void setWeights(Matrix weigthMatrix) {
        this.weights = weigthMatrix;
    }
    
    /**
     * Sets an element of the weights matrix
     * @param row
     * @param column
     * @param value
     */
    public void setWeightsElement(int row, int column, double value){
        this.weights.setElement(row, column, value);
    }

    /**
     * Returns the biases of the layer
     * @return
     */
    public Vector getBiases() {
        return biases;
    }

    /**
     * Sets the biases of the layer
     * @param biases
     */
    public void setBiases(Vector biases) {
        this.biases = biases;
    }
    
    /**
     * Sets an element of the biases vector
     * @param index
     * @param value
     */
    public void setBiasesElement(int index, double value){
        this.biases.setElement(index, value);
    }

    /**
     * Returns the layer's transfer function
     * @return
     */
    public TransferFunction getTransferFunction() {
        return transferFunction;
    }

    /**
     * Sets the layer's transfer function
     * @param transferFunction
     */
    public void setTransferFunction(TransferFunction transferFunction) {
        this.transferFunction = transferFunction;
    }
    
    /**
     * Sets the layer's weights to random values
     */
    public void setWeightsRandom(){
        for(int i=0; i<weights.rows(); i++){
            for (int j=0; j<weights.columns(); j++){
                weights.setElement(i, j, Math.random());
            }
        }
    }
    
    /**
     * Sets the layer's biases to random values
     */
    public void setBiasesRandom(){
        for (int i=0; i<biases.length();i++){
            biases.setElement(i, Math.random());
        }
    }
    
    /**
     * Returns the number of neurons in the layer
     * @return
     */
    public int getNumberOfNeurons(){
        return this.biases.length();
    }
    
    /**
     * Returns the layer's outputs before applying the transfer function
     * @param input
     * @return
     */
    public Vector getNeurons(Vector input){
        try{
        return weights.dot(input).add(biases);
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     * Returns the layer's outputs after applying the transfer function
     * @param input
     * @return
     */
    public Vector getAxons(Vector input){
        return transferFunction.apply(getNeurons(input));
    }
    
    /**
     * Returns the transfer function derivatives 
     * @param input is the output of the getAxons method
     * @return
     */
    public Vector getDerivatives(Vector input){
        return transferFunction.getDerivative(input);
    }
    
    /**
     * Uses the ADALINE algorithm to train the neural network using supervised
     * training.
     * The learning constant alpha must be between 0 and 1.
     * @param inputs Are the inputs to train the network
     * @param targets Are the desired outputs of the neuron
     * @param alpha Is the learning constant
     * @param epochs Is the number of epochs in which the learning will take place
     * @return Returns a Vector for each neuron in the network containing the 
     * mean squared error calculated across each epoch
     */
    public Vector[] learn(Vector[] inputs, Vector[] targets, double alpha, int epochs) {
        if (inputs.length != targets.length){
            return null;
        }
        
        setWeightsRandom();
        setBiasesRandom();
        
        Vector[] meanSquaredError = new Vector[biases.length()];
        for (int i=0; i<meanSquaredError.length;i++){
            meanSquaredError[i] = new Vector(epochs);
        }
        Vector mse = new Vector(biases.length());
        
        for (int i=0; i<epochs; i++){
            for (int j = 0; j<inputs.length;j++){
                Vector axons = getAxons(inputs[j]);
                Vector error = targets[j].subs(axons); 
                mse = mse.add(error.elementwiseProduct(error));   
                error = error.scalarProduct(alpha);
                weights = weights.add(error.outerProduct(inputs[j]));
                biases = biases.add(error);
            }
            for (int j = 0; j<biases.length(); j++){
            meanSquaredError[j].setElement(i, mse.getElement(j)/inputs.length);
            mse.setElement(j, 0);
            }
        }
        
        return meanSquaredError;
    }
    
    /**
     * Uses the ADALINE algorithm to train the neural network using supervised
     * training.
     * This methos is called without specifying the learning contant so this
     * parameter is calculated to get the maximum alpha recommended.
     * @param inputs Are the inputs to train the network
     * @param targets Are the desired outputs of the neuron
     * @param epochs Is the number of epochs in which the learning will take place
     * @return Returns a Vector for each neuron in the network containing the 
     * mean squared error calculated across each epoch
     */
    public Vector[] learn(Vector[] inputs, Vector[] targets, int epochs){
        double alpha;
        Matrix correlationMatrix = new Matrix(inputs[0].length());
        for (int i = 0; i < inputs.length; i++){
            correlationMatrix.add(inputs[i].outerProduct(inputs[i]));
        }
        correlationMatrix.scalarProduct(1/inputs.length);
        double maxEigen = correlationMatrix.eig(correlationMatrix.max());
        alpha = 0.9999 / (4 * maxEigen);
        return Layer.this.learn(inputs, targets, alpha, epochs);
    }

    @Override
    public String toString() {
        return "Layer{" + "weights=\n" + weights + "\nbiases=\n" + biases + "\ntransferFunction=\n" + transferFunction + '}';
    }
    
    
}
