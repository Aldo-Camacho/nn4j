/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyia.neuralnetworks;

import easyia.linearalgebra.Matrix;
import easyia.linearalgebra.Vector;

/**
 * The class Adaline provides a simple single-layer Adaline Neural Network
 * implementation
 * @author aldo1
 */
public class Adaline extends Layer {
    
    /**
     * Constructor for an instance of the Adaline Class 
     * @param numberOfInputs The number of inputs the layer will recieve
     * @param numberOfNeurons The number of neurons the layer will have
     */
    public Adaline(int numberOfInputs, int numberOfNeurons){
        super(numberOfInputs, numberOfNeurons);
        super.setTransferFunction(TransferFunction.PURELIN);
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
        return super.learn(inputs, targets, alpha, epochs);
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
        return super.learn(inputs, targets, epochs);
    }
    
}
