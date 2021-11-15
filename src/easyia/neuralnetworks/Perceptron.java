/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyia.neuralnetworks;

import easyia.linearalgebra.Matrix;
import easyia.linearalgebra.Vector;

/**
 * Class Perceptron provides a simple single-layer
 * Perceptron Neural Network
 * @author aldo1
 */
public class Perceptron extends Layer{

//    private TransferFunction  transferFunction = TransferFunction.HARDLIM;
//    private Matrix weights;
//    private Vector biases;
    private boolean isSymmetric;
    
    /**
     * Constructor for a Perceptron accepting the number
     * of inputs accepted and the number of neurons in the
     * layer.
     * The boolean parameter isSymmetric defines if the 
     * transfer function is regular hardlimit or
     * symmetric hardlimit
     * @param numberOfInputs The number of inputs in the layer
     * @param numberOfNeurons The number of Neurons in the layer
     * @param isSymmetric The symmetry of the transfer function.
     */
    public Perceptron(int numberOfInputs, int numberOfNeurons, boolean isSymmetric){
        super(numberOfInputs, numberOfNeurons);
        super.setTransferFunction(TransferFunction.HARDLIM);
        this.isSymmetric = isSymmetric;
        if (isSymmetric){
            super.setTransferFunction(TransferFunction.HARDLIMS);
        } 
    }

    /**
     * Returns wether the transfer function is symmetric.
     * @return
     */
    public boolean hasSymmetry(){
        return isSymmetric;
    }

    /**
     * Sets the transfer function according to symmetry
     * @param symmetry
     */
    public void setSymmetry(boolean symmetry){
        if (symmetry){
            super.setTransferFunction(TransferFunction.HARDLIMS);
        } else {
            super.setTransferFunction(TransferFunction.HARDLIM);
        }
        isSymmetric = symmetry;
    }

    /**
     * Uses the PERCEPTRON algorithm to train the network with
     * supervised training
     * @param inputs The array of input Vectors for training
     * @param targets The array of desired output Vectors for training
     * @param epochs The number of iterations for training
     * @return Returns a Vector for each neuron in the network containing the 
     * mean squared error calculated across each epoch
     */
    @Override
    public Vector[] learn(Vector[] inputs, Vector[] targets, int epochs) {
        return super.learn(inputs, targets, 1, epochs);
    }
    
}
