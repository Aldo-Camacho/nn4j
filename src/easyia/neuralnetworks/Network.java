/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyia.neuralnetworks;

import easyia.linearalgebra.Matrix;
import easyia.linearalgebra.Vector;

/**
 * The class Network provides a basic implementation of multy-layer
 * neural networks
 * @author aldo1
 */
public class Network {
    private Layer[] layers;
    
    /**
     * Constructor for a Network of a determined number of layers
     * @param numberOfLayers The number of layers. Must be greater than 2.
     */
    public Network(int numberOfLayers){
        if(numberOfLayers < 2){
            throw new IllegalArgumentException("Network must have al least two layers.");
        }
        this.layers = new Layer[numberOfLayers];
    }
    
    /**
     * Constructor for a Network, specifying the number of inputs
     * and the number of neuron for each layer
     * @param inputs The number of inputs that the network recieves
     * @param numberOfNeurons The number of neurons in each layer
     */
    public Network(int inputs, int... numberOfNeurons){
        if(numberOfNeurons.length < 2){
            throw new IllegalArgumentException("Network must have al least two layers.");
        }
        this.layers = new Layer[numberOfNeurons.length];
        this.layers[0] = new Layer(inputs, numberOfNeurons[0]);
        for (int i = 0; i < numberOfNeurons.length - 1; i++){
            this.layers[i+1] = new Layer(numberOfNeurons[i],numberOfNeurons[i+1]);
        }
    }
    
    /**
     * Sets the parameters of the layer in an specified index
     * @param numberOfLayer The index of the layer to set
     * @param numberOfNeurons The number of neurons in the layer
     * @param numberOfInputs The number of inputs of the layer
     * @param transferFunction the transfer function of the layer
     */
    public void setLayer(int numberOfLayer,int numberOfNeurons, int numberOfInputs, TransferFunction transferFunction){
        this.layers[numberOfLayer] = new Layer(numberOfNeurons,numberOfInputs);
        this.layers[numberOfLayer].setTransferFunction(transferFunction);
    }
    
    /**
     * Sets the weights Matrix of a layer
     * @param numberOfLayer
     * @param weights
     */
    public void setLayerWeights(int numberOfLayer, Matrix weights){
        this.layers[numberOfLayer].setWeights(weights);
    }
    
    /**
     * Sets de biases Vector of a layer
     * @param numberOfLayer
     * @param biases
     */
    public void setLayerBiases(int numberOfLayer, Vector biases){
        layers[numberOfLayer].setBiases(biases);
    }
    
    /**
     * Sets the transfer function of a layer
     * @param numberOfLayer
     * @param transferFunction
     */
    public void setLayerTransferFunction(int numberOfLayer, TransferFunction transferFunction){
        this.layers[numberOfLayer].setTransferFunction(transferFunction);
    }
    
    /**
     * Sets all weights Matrices and biases Vectors to random values
     */
    public void setAllRandom(){
        for(int i = 0; i < layers.length; i++){
            layers[i].setWeightsRandom();
            layers[i].setBiasesRandom();
        }
    }
    
    /**
     * Returns the network output for a certain input
     * @param input
     * @return
     */
    public Vector getOutput(Vector input){
        Vector output = new Vector(layers[layers.length-1].getNumberOfNeurons());
        for (Layer layer: layers){
            output = layer.getAxons(input);
            input = output;
        }
        return output;
    }
    
    /**
     * Returns the outputs of all the layers simultaneously
     * @param input
     * @return
     */
    public Vector[] getAxons(Vector input){
        Vector[] output = new Vector[layers.length];
        for (int i = 0; i < output.length; i++){
            output[i] = layers[i].getAxons(input);
            input = output[i];
        }
        return output;
    }
    
    /**
     * Returns the derivatives of all the layers outputs simultaneously
     * @param input
     * @return
     */
    public Vector[] getDerivatives(Vector[] input){
        Vector[] output = new Vector[layers.length];
        for (int i = 0; i < output.length; i++){
            output[i] = layers[i].getDerivatives(input[i]);
        }
        return output;
    }
    
    /**
     * Returns a certain layer
     * @param index
     * @return
     */
    public Layer getLayer(int index) {
        return this.layers[index];
    }
    
    /**
     * Returns the last layer
     * @return
     */
    public Layer lastLayer(){
        return layers[layers.length-1];
    }
    
    /**
     * Uses the BACKPROPAGATION algorithm to train the neural network using supervised
     * training.
     * The learning constant alpha must be between 0 and 1.
     * @param inputs Are the inputs to train the network
     * @param targets Are the desired outputs of the neuron
     * @param alpha Is the learning constant
     * @param epochs Is the number of epochs in which the learning will take place
     * @return Returns a Vector for each neuron in the last layer containing the 
     * mean squared error calculated across each epoch
     */
    public Vector[] learn(Vector[] inputs, Vector[] targets, double alpha, int epochs){
        if (inputs.length != targets.length){
            throw new IllegalArgumentException("Must have the same number of inputs and targets");
        }
        
        setAllRandom();
        
        Vector[] meanSquaredError = new Vector[lastLayer().getNumberOfNeurons()];
        for (int i = 0; i < meanSquaredError.length; i++){
            meanSquaredError[i] = new Vector(epochs);
        }
        
        Vector mse = new Vector(meanSquaredError.length);
        Vector sensitivity[] = new Vector[layers.length];
        for (int i = 0; i < epochs; i++){
            for(int j = 0; j < inputs.length; j++){
//                Some parameters are calculed first
                Vector[] axons = this.getAxons(inputs[j]);
                Vector[] derivatives = this.getDerivatives(axons);
                Vector error = targets[j].subs(axons[axons.length-1]);
                mse = mse.add(error.elementwiseProduct(error));
//                The sensitivities are calculed
                Matrix auxiliar = Matrix.diagonal(derivatives[layers.length-1]);
                sensitivity [layers.length-1] = auxiliar.dot(error).scalarProduct(-2);
                for (int k = layers.length-2; k > -1; k--){
                    auxiliar = Matrix.diagonal(derivatives[k]);
                    sensitivity[k] = auxiliar.dot(layers[k+1].getWeights().transpose().dot(sensitivity[k+1]));
                }
//                The weights and biases are updated
                layers[0].setWeights(layers[0].getWeights().subs(sensitivity[0].outerProduct(inputs[j]).scalarProduct(alpha)));
                layers[0].setBiases(layers[0].getBiases().subs(sensitivity[0].scalarProduct(alpha)));
                for(int k = 1; k < layers.length; k++){
                    layers[k].setWeights(layers[k].getWeights().subs(sensitivity[k].outerProduct(axons[k-1]).scalarProduct(alpha)));
                    layers[k].setBiases(layers[k].getBiases().subs(sensitivity[k].scalarProduct(alpha)));
                }
            }
            for (int j = 0; j<lastLayer().getNumberOfNeurons(); j++){
                meanSquaredError[j].setElement(i, mse.getElement(j)/inputs.length);
                mse.setElement(j, 0);
            }
        }
        return meanSquaredError;
    }
    
    @Override
    public String toString() {
        String s = "Network\n" + "Number of layers = " + layers.length;
        for (int i = 0; i < layers.length; i++){
            s += "\nLayer no: " + i + "\n" + layers[i];
        }
        return s;
    }
}
