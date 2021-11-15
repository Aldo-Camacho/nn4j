/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyia.linearalgebra;

/**
 * The class Complex provides a simple linear algrebra implementation 
 * for complex numbers
 * @author aldo1
 */
public class Complex {
    private double realPart;
    private double imaginaryPart;
    
    /**
     * Constructor for 0+0i
     */
    public Complex(){
        realPart = 0;
        imaginaryPart = 0;
    }
    
    /**
     * Constructor for a complex number realPart + i*imaginaryPart
     * @param realPart
     * @param imaginaryPart
     */
    public Complex(double realPart, double imaginaryPart){
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }
    
    /**
     * Static constructor for 0+i
     * @return
     */
    public static Complex i(){
        return new Complex(0,1);
    }
    
    /**
     * Returns the real part of a complex number
     * @return
     */
    public double getRealPart() {
        return realPart;
    }

    /**
     * Set the real part of a complex number
     * @param realPart
     */
    public void setRealPart(double realPart) {
        this.realPart = realPart;
    }

    /**
     * Returns the imaginary part of a complex number
     * @return
     */
    public double getImaginaryPart() {
        return imaginaryPart;
    }

    /**
     * Sets the imaginary part of a complex number
     * @param imaginaryPart
     */
    public void setImaginaryPart(double imaginaryPart) {
        this.imaginaryPart = imaginaryPart;
    }
    
    /**
     * Returns the complex number resulting of adding
     * two complex numbers
     * @param b
     * @return
     */
    public Complex add(Complex b){
        return new Complex(this.realPart + b.realPart, this.imaginaryPart + b.imaginaryPart);
    }
    
    /**
     * Returns the complex number result of multiplying a
     * complex and a scalar
     * @param scalar
     * @return
     */
    public Complex scalarProduct(double scalar){
        return new Complex(scalar * realPart, scalar * imaginaryPart);
    }
    
    /**
     * Returns the difference of two complex numbers
     * @param b
     * @return
     */
    public Complex subs(Complex b){
        return this.add(b.scalarProduct(-1));
    }
    
    /**
     * Returns the complex result of the product of
     * two complex numbers
     * @param b
     * @return
     */
    public Complex product(Complex b){
        double modulus = this.modulus()*b.modulus();
        double argument = this.argument()+b.argument();
        return new Complex(modulus * Math.cos(argument), modulus * Math.sin(argument));
    }
    
    /**
     * Returns the complex result of the division of
     * two complex numbers
     * @param b
     * @return
     */
    public Complex division(Complex b){
        double modulus = this.modulus()/b.modulus();
        double argument = this.argument()-b.argument();
        return new Complex(modulus * Math.cos(argument), modulus * Math.sin(argument));
    }
    
    /**
     * Returns the complex conjugate of a complex number
     * @return
     */
    public Complex conjugate(){
        return new Complex(this.realPart, - this.imaginaryPart);
    }
    
    /**
     * Returns the modulus of a complex number
     * @return
     */
    public double modulus(){
        return Math.sqrt(this.realPart * this.realPart + this.imaginaryPart * this.imaginaryPart);
    }
    
    /**
     * Return the argument in radians of a complex number
     * @return
     */
    public double argument(){
        return Math.atan2(realPart, imaginaryPart);
    }

    @Override
    public String toString() {
        return ""+ realPart + (imaginaryPart < 0 ? "":"+") + imaginaryPart + "i";
    }
    
}
