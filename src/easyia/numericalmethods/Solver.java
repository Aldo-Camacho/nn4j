/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyia.numericalmethods;

//import static java.lang.Double.NaN;
import easyia.functions.Polynomial;
import easyia.linearalgebra.Matrix;
import easyia.linearalgebra.Vector;
import easyia.functions.Function;

/**
 * The class Solver contains methods for performing
 * numerical methods for finding aproximations of function
 * roots, function coefficients or aproximate derivates
 * @author aldo1
 */
public class Solver {

    /**
     * Returns the real root of a function closest to the initial value.
     * If the function does not converge under 1000 iterations the method
     * throws an exception suposing the function has no real roots.
     * If the function root is zero or near the algorithm changes from relative error
     * to absolute error due to division by numbers close to zero.
     * 
     * @param function is the function of which the root is wanted.
     * @param maxRelativeError is the relative error selected as the convergence parameter.
     * @param initialValue is the first aproximation to the root value.
     * @return
     */
    public static double newtonraphson(Function function, double maxRelativeError, double initialValue){
        int iterations = 0;
        double actualValue = initialValue;
        double previousValue;

        Function derivative = function.getDerivative();
        Function secondDerivative = derivative.getDerivative();
        
        
        double actualRelativeError = 100;
        double f;
        double fp;
        double fpp;
        
        while(actualRelativeError > maxRelativeError){
            previousValue = actualValue;
            f = function.apply(actualValue);
            if (Math.abs(f) < 1E-15){
                break;
            }
            fp = derivative.apply(actualValue);
            fpp = secondDerivative.apply(actualValue);
            actualValue -= (f*fp)/(fp*fp-f*fpp);
            if (Math.abs(actualValue) > 1E-15){
                actualRelativeError = Math.abs((actualValue - previousValue)/actualValue);
            } else{
                actualRelativeError = Math.abs(actualValue - previousValue);
            }
            iterations++;
            if (iterations >= 1000 
                    || actualValue == Double.POSITIVE_INFINITY || actualValue == Double.NEGATIVE_INFINITY
                    || Double.isNaN(actualValue)){
                throw new IllegalArgumentException("Function has no real roots");
            }
        }
        return actualValue;
    }
    
    /**
     * Returns a Function instance that provide an aproximate derivative to 
     * a function using the secant method
     * @param function is the function of which the derivative should be aproximated;
     * @return
     */
    public static Function numericalDerivative(Function function){
        return new Function() {
            @Override
            public double apply(double input) {
                double h = 1E-6;
                return (function.apply(input+h)- function.apply(input))/h;
            }
            
            @Override 
            public Function getDerivative(){
                return Solver.numericalDerivative(this);
            }

            @Override
            public Function getIntegral() {
                return function;
            }
            
        };
    }
    
    /**
     * Returns a root of a Function if this root is contained in a range 
     * [a,b]. The Function must have a sign change between F(a) and F(b).
     * @param function
     * @param maxRelativeError
     * @param a
     * @param b
     * @return
     */
    public static double bisection(Function function , double maxRelativeError, double a, double b){
        if (a > b){
            double dummy = a;
            a = b;
            b = dummy;
        }
        double Fa = function.apply(a);
        double Fb = function.apply(b);
        
        if(Fa*Fb >= 0){
            throw new IllegalArgumentException("F must cross 0 bewteen a and b");
        }
        
        double Fc;
        double c = b;
        double previousC;
        double actualRelativeError = 100;
        
        do {
            previousC = c;
            c = (a + b) / 2;
            Fa = function.apply(a);
            Fc = function.apply(c);
            if (Math.abs(Fc) < 1e-24){
                break;
            }
            if (Fa*Fc < 0){
                b = c;
            }
            else {
                a = c;
            }
            actualRelativeError = Math.abs((c - previousC)/c);
        } while (actualRelativeError > maxRelativeError);
        return c;
    }
    
    /**
     * Returns a Polynomial that is the closest aproximation
     * to a cluster of two dimentional data.
     * @param x is a Vector representing the independient variable.
     * @param y is a Vector representing the dependient variable.
     * @param order is an int that specifies the polynomial order.
     * @return
     */
    public static Polynomial leastSquares(Vector x, Vector y, int order){
        if (x.length() != y.length()){
            throw new IllegalArgumentException("Vectors x and y must have the same lengths");
        }
        Polynomial poly = new Polynomial(order);
        
        Matrix Xn = new Matrix(2*order+1,x.length());
        for (int i = 0 ; i < Xn.rows(); i++){
            for (int j = 0; j < Xn.columns(); j++){
                Xn.setElement(i, j, Math.pow(x.getElement(j),i));
            }
        }
        
        Matrix XnY = new Matrix(order+1,y.length());
        for (int i = 0; i < XnY.rows(); i++){
            XnY.setRow(i, Xn.getRow(i).elementwiseProduct(y));
        }
        
        Matrix M = new Matrix(order+1);
        Vector b = new Vector(order+1);
        
        for (int i = 0; i < XnY.rows(); i++){
            for (int j = 0; j < XnY.rows(); j++){
                M.setElement(i, j, Xn.getRow(j+i).sum());
            }
            b.setElement(i, XnY.getRow(i).sum());
        }
        Vector output = M.solve(b);
        for (int i = 0; i < output.length(); i++){
            poly.setCoefficient(i, output.getElement(i));
        }
        return poly;
    }
    
}
