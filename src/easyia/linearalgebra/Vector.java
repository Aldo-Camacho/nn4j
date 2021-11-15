/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyia.linearalgebra;

/**
 * The class Vector provides basic linear algebra methods for
 * 1-d double arrays
 * @author aldo1
 */
public class Vector{
    private double[] values;
    
    /**
     * Creates a Vector with a specified length
     * @param elements The number of elements
     */
    public Vector(int elements){
        this.values = new double[elements];
    }
    
    /**
     * Creates a Vector with the values specified value
     * as elements
     * @param values
     */
    public Vector(double... values){
        this.values = values.clone();
    }
    
    /**
     * Static constructor for a Vector of n ones 
     * @param length
     * @return
     */
    public static Vector ones(int length){
        Vector ones = new Vector(length);
        for (int i = 0; i < length; i++){
            ones.values[i] = 1;
        }
        return ones;
    }
    
    /**
     * Static constructor for a Vector with values between a range
     * separed by a specified step
     * @param start The start of the range, inclusive
     * @param end The end of the range, exclusive
     * @param step The step of separation
     * @return
     */
    public static Vector range(double start, double end, double step){
        int iterator = 0;
        Vector range = new Vector((int) Math.ceil((end-start)/step));
        for(double i = start; i < end; i += step){
            range.values[iterator] =  i;
            iterator++;
        }
        return range;
    }
    
    /**
     * Static constructor for a Vecor ranging from 0 to
     * end with a step of one
     * @param end end of the range, exclusive
     * @return
     */
    public static Vector range(int end){
        return range(0, end);
    }

    /**
     * Static constructor for a Vector containing a
     * range from start (inclusive) to end (exclusive)
     * with a step of one
     * @param start
     * @param end
     * @return
     */
    public static Vector range( int start, int end){
        return range((double) start, (double) end, 1);
    }
    
    /**
     * Returns the length of the Vector
     * @return
     */
    public int length(){
        return this.values.length;
    }
    
    /**
     * Returns the double array containing the elements values
     * @return
     */
    public double[] getValues() {
        return values;
    }

    /**
     * Sets the double array containing the elements values
     * @param value
     */
    public void setValues(double... value) {
        this.values = value.clone();
    }
    
    /**
     * Returns the value of the element contained in
     * a specified index
     * @param index The index of the element
     * @return
     */
    public double getElement(int index){
        return this.values[index];
    }
    
    /**
     * Sets the value of the element contained in
     * a specified index
     * @param index The index of the element
     * @param value The value to store
     */
    public void setElement(int index, double value){
        this.values[index] = value;
    }
    
    /**
     * Return a Vector with the result of multiplying
     * a Vector with a scalar
     * @param scalar
     * @return
     */
    public Vector scalarProduct(double scalar){
        Vector output = new Vector(this.length());
        for (int i = 0; i<this.length(); i++){
            output.values[i] = scalar*this.values[i];
        }
        return output;
    }
    
    /**
     * Returns a Vector of the dot product of this vector
     * and a vector b
     * @param b Another Vector
     * @return
     */
    public double dotProduct(Vector b){ // TODO:
        if (this.length() != b.length()){
            throw new IllegalArgumentException("Vectors must be the same length.");
        }
        double output = 0;
        for (int i=0; i<this.getValues().length;i++){
        output += this.values[i]*b.values[i];
        }
        return output;
    }
    
    /**
     * Returns the n-dimensional cross product of this Vector
     * with an array of n-2 vectors.
     * The result is another n-dimensional vector
     * @param b
     * @return
     */
    public Vector crossProduct(Vector[] b){
        if (this.length() != b[0].length()){
            throw new IllegalArgumentException("Vectors must be the same length.");
        }
        if (b.length != this.length()-2){
            throw new IllegalArgumentException("A n-2 number of vectors are needed as input");
        }
        if (this.length() < 3){
            throw new IllegalArgumentException("Unsupported for vectors with less than 3 dimmensions");
        }
        Vector output = new Vector(this.length());
        Matrix matrix = new Matrix(this.length());
        
        matrix.setRow(0, Vector.ones(this.length()));
        matrix.setRow(1, this);
        for (int i = 0; i < b.length; i++){
            matrix.setRow(i+2, b[i]);
        }
        System.out.println(matrix);
        for (int i = 0; i < matrix.columns(); i++){
            output.values[i] = matrix.cofactor(0, i);
        }
        return output;
    }
    
    /**
     * Returns a nxm Matrix result of the matricial product
     * Vector (this) is a nx1 matrix
     * Vector (b) in a 1xm matrix
     * this.outerProduct(b) = nx1 Matrix.product(1xm Matrix)
     * @param b
     * @return
     */
    public Matrix outerProduct(Vector b){
        Matrix M1 = new Matrix(this.length(),1);
        M1.setColumn(0,this);
        Matrix M2 = new Matrix(1,b.length());
        M2.setRow(0,b);
        return M1.product(M2);
    }
    
    /**
     * Returns a Vector with the result of adding
     * two Vectors
     * @param b
     * @return
     */
    public Vector add(Vector b){
        if (this.length() != b.length()){
            throw new IllegalArgumentException("Vectors must be the same length.");
        }
        Vector output = new Vector(this.length());
        for (int i = 0; i<output.length(); i++){
            output.values[i] = this.values[i] + b.values[i];
        }
        return output;
    }
    
    /**
     * Returns a Vector with the difference of
     * two Vectors
     * @param b
     * @return
     */
    public Vector subs(Vector b){
        b = b.scalarProduct(-1);
        return this.add(b);
    }
    
    /**
     * Returnsa Vector with the element to elemnt 
     * multiplication of two vectors
     * @param b
     * @return
     */
    public Vector elementwiseProduct(Vector b){
        if (this.length() != b.length()){
            throw new IllegalArgumentException("Vectors must be the same length.");
        }
        Vector output = new Vector(this.length());
        for (int i = 0; i<output.length(); i++){
            output.values[i] = this.values[i] * b.values[i];
        }
        return output;
    }
    
    /**
     *Returnsa Vector with the element to elemnt 
     * multiplication of two vectors
     * @param b
     * @return
     */
    public Vector elementwiseDivision(Vector b){
        if (this.length() != b.length()){
            throw new IllegalArgumentException("Vectors must be the same length.");
        }
        Vector output = new Vector(this.length());
        for (int i = 0; i<output.length(); i++){
            output.values[i] = this.values[i] / b.values[i];
        }
        return output;
    }
    
    /**
     * Returns the mean of all the values in
     * this Vector
     * @return
     */
    public double mean(){
        return this.sum()/this.length();
    }
    
    /**
     * Returns the variance of all the values in
     * this Vector
     * @return
     */
    public double var(){
        double mean = this.mean();
        double var = 0;
        for (double x: values){
            var += Math.pow(x-mean, 2);
        }
        return var/this.length();
    }
    
    /**
     * Returns the standard deviation of all the
     * values in this vector
     * @return
     */
    public double std(){
        return Math.sqrt(this.var());
    }
    
    /**
     * Returns the sum of all the values in 
     * this vector
     * @return
     */
    public double sum(){
        double sum = 0;
        for (double x : values){
            sum += x;
        }
        return sum;
    }
    
    /**
     * Returns the gratest value in a Vector
     * @return
     */
    public double max(){
        double max = this.values[0];
        for(double x: values){
            if(x > max){
                max = x;
            }
        }
        return max;
    }
    
    /**
     * Returns the smallest value in a vector
     * @return
     */
    public double min(){
        double min = this.values[0];
        for(double x: values){
            if(x < min){
                min = x;
            }
        }
        return min;
    }
    
    /**
     * Returns an array with al the n elements of
     * m combinations without repeating.
     * The vector must have all its elements unrepeated
     * and sorted
     * @param elementsToCombine
     * @return
     */
    public Vector[] getCombinations(int elementsToCombine){
        Vector[] output = new Vector[(int) (factorial(this.length())/factorial(this.length()-elementsToCombine)/factorial(elementsToCombine))];
        for (int i = 0; i < output.length; i++){
            output[i] = new Vector(elementsToCombine);
        }
        int[] iterator = new int[]{0};
        buildCombinations(output, elementsToCombine, 0, 0, iterator, null);
        return output;
    }
    
    private void buildCombinations(Vector[] v, int elementsToCombine, int forLevel, int previousElement, int[] vectorIterator, int[] indexes){
        if (indexes == null){
            indexes = new int[elementsToCombine];
        }
        if (forLevel < elementsToCombine){
            for (int i = previousElement; i < this.length(); i++ ){
                indexes[forLevel] = i;
                buildCombinations(v, elementsToCombine, forLevel+1, i+1, vectorIterator, indexes);
            }
        }else{
            for (int i = 0; i < indexes.length; i++){
                v[vectorIterator[0]].values[i] = this.values[indexes[i]];
            }
            vectorIterator[0]++;
        }
    }
    
    private double factorial(int i){
        if(i == 0){
            return 1;
        } else {
            return (i * factorial(i-1));
        }
    }

    @Override
    public String toString() {
        String s = "";
        for (double x: values){
            s += " " + x;
        }
        return s;
    }
    
    /**
     * Returns true if this and a Vector b are equals
     * @param b
     * @return
     */
    public boolean equals(Vector b){
        if (b == this){
            return true;
        }
        if (this.length() == b.length()){
            for (int i=0; i<this.length(); i++){
               if (this.values[i] != b.values[i]){
                   return false;
               } 
            }
            return true;
        }
        return false;
    }  
    
    
}
