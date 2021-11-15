/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyia.neuralnetworks;

import easyia.linearalgebra.Vector;

/**
 * Enum TransferFunction gives an implementation for neural networks
 * tranfer functions
 * @author aldo1
 */
public enum TransferFunction{
        HARDLIM,
        HARDLIMS,
        PURELIN,
        SATLIN,
        SATLINS,
        LOGSIG,
        TANSIG,
        POSLIN;

    /**
     * Returns the result of applying the transfer function to a determinate
     * input value
     * @param transferFunctionInput The input to evaluate
     * @return
     */
    
    public double apply(double transferFunctionInput){
       double trasferFunctionOutput = 0;
       switch (this){
            case HARDLIM:
                if (transferFunctionInput >=0){
                    trasferFunctionOutput = 1;
                } else{
                    trasferFunctionOutput = 0;
                }
                break;
            case HARDLIMS:
                if (transferFunctionInput >=0){
                    trasferFunctionOutput = 1;
                } else{
                    trasferFunctionOutput = -1;
                }
                break;
            case PURELIN:
                trasferFunctionOutput = transferFunctionInput;
                break;
            case SATLIN:
                if (transferFunctionInput < 0) {
                    trasferFunctionOutput = 0;
                } else if (transferFunctionInput > 1){
                    trasferFunctionOutput = 1;
                } else {
                    trasferFunctionOutput = transferFunctionInput;
                }
                break;
            case SATLINS:
                if (transferFunctionInput < 0) {
                    trasferFunctionOutput = -1;
                } else if (transferFunctionInput > 1){
                    trasferFunctionOutput = 1;
                } else {
                    trasferFunctionOutput = transferFunctionInput;
                }
                break;
            case LOGSIG:
                double exp = Math.pow(Math.E, -transferFunctionInput);
                trasferFunctionOutput = 1/(1 + exp);
                break;
            case TANSIG:
                double nExp = Math.pow(Math.E, -transferFunctionInput);
                double pExp = Math.pow(Math.E, transferFunctionInput);
                trasferFunctionOutput = (pExp - nExp)/(pExp + nExp);
                break;
            case POSLIN:
                if (transferFunctionInput < 0) {
                    trasferFunctionOutput = 0;
                } else {
                    trasferFunctionOutput = transferFunctionInput;
                }
                break;
        }
        return trasferFunctionOutput; 
    }
    
    /**
     * Returns the result of applying the transfer function to a determinate
     * input Vector
     * @param transferFunctionInputs The Vector at which the 
     * @return
     */
    public Vector apply(Vector transferFunctionInputs){
        Vector transerFunctionOutputs = new Vector(transferFunctionInputs.length());
        for (int i=0; i<transferFunctionInputs.length();i++){
            transerFunctionOutputs.setElement(i, TransferFunction.this.apply(transferFunctionInputs.getElement(i)));
        }
        return transerFunctionOutputs;
    }
    
    /**
     * Returns the evalauted derivative of the transfer function depending
     * of the function's output
     * @param transferFunctionOutput The output of the transfer function of which
     * the derivative is desired
     * @return
     */
    public double getDerivative(double transferFunctionOutput){
        double transferFunctionDerivative = 0;
       switch (this){
            case HARDLIM:
                if (transferFunctionOutput ==0){
                    transferFunctionDerivative = 1;
                } else{
                    transferFunctionDerivative = 0;
                }
                break;
            case HARDLIMS:
                if (transferFunctionOutput ==0){
                    transferFunctionDerivative = 1;
                } else{
                    transferFunctionDerivative = 0;
                }
                break;
            case PURELIN:
                transferFunctionDerivative = 1;
                break;
            case SATLIN:
                if (transferFunctionOutput < 0) {
                    transferFunctionDerivative = 0;
                } else if (transferFunctionOutput > 1){
                    transferFunctionDerivative = 0;
                } else {
                    transferFunctionDerivative = 1;
                }
                break;
            case SATLINS:
                if (transferFunctionOutput < 0) {
                    transferFunctionDerivative = 0;
                } else if (transferFunctionOutput > 1){
                    transferFunctionDerivative = 0;
                } else {
                    transferFunctionDerivative = 1;
                }
                break;
            case LOGSIG:
                transferFunctionDerivative = transferFunctionOutput*(1-transferFunctionOutput);
                break;
            case TANSIG:
                transferFunctionDerivative = 1-(transferFunctionOutput*transferFunctionOutput);
                break;
            case POSLIN:
                if (transferFunctionOutput < 0) {
                    transferFunctionDerivative = 0;
                } else {
                    transferFunctionDerivative = 1;
                }
                break;
        }
        return transferFunctionDerivative;
    }
    
    /**
     * Returns the evalauted derivative of the transfer function depending
     * of the function's outputs
     * @param transferFunctionOutputs The output vector of which the derivative
     * is desired
     * @return
     */
    public Vector getDerivative(Vector transferFunctionOutputs){
        Vector transferFunctionDerivatives = new Vector(transferFunctionOutputs.length());
        for (int i=0; i<transferFunctionOutputs.length();i++){
            transferFunctionDerivatives.setElement(i, getDerivative(transferFunctionOutputs.getElement(i)));
        }
        return transferFunctionDerivatives;
    }
    
}
