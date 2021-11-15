/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyia.linearalgebra;

//import java.util.function.Function;
import easyia.numericalmethods.Solver;
import easyia.functions.Function;
import easyia.functions.Polynomial;

/**
 * The class Matrix provides basic linear algebra methods to
 * 2-dimensional double arrays
 * @author aldo1
 */
public class Matrix {
    private double[][] values;
    
    /** 
     * Constructor for matrix of size nxm
     * @param rows
     * @param columns
     */
    public Matrix(int rows,int columns){
        this.values = new double[rows][columns];
    }
    
    /**
     * Constructor for squared Matrix of size nxn
     * @param order
     */
    public Matrix(int order){
        this(order, order);
    }
    
    /**
     * Constructor to create a matrix from an nxm array
     * @param values
     */
    public Matrix(double[][] values){
        this.values = values.clone();
    }
    
    /**
     * Static constructor for an nxm zeros matriz
     * @param rows
     * @param columns
     * @return
     */
    public static Matrix zeros(int rows, int columns){
        return new Matrix(rows,columns);
    }
    
    /**
     * Static constructor for a squared nxn zeros matrix
     * @param order
     * @return
     */
    public static Matrix zeros(int order){
        return new Matrix(order);
    }
    
    /**
     * Static constructor for an nxm ones matrix
     * @param rows
     * @param columns
     * @return
     */
    public static Matrix ones(int rows, int columns){
        Matrix out = new Matrix(rows,columns);
        for (int i = 0; i<rows; i++){
            for (int j = 0; j<columns; j++){
                out.values[i][j] = 1;
            }
        }
        return out;
    }
    
    /**
     * Static constructor for a squared nxn ones matrix
     * @param order
     * @return
     */
    public static Matrix ones(int order){
        return Matrix.ones(order,order);
    }
    
    /**
     * Static constructor for a squared nxn identity matrix
     * @param order
     * @return
     */
    public static Matrix eye(int order){
        Matrix out = new Matrix(order,order);
        for (int i = 0; i<order; i++){
            out.values[i][i] = 1;
        }
        return out;
    }
    
    /**
     * Constructor for a nxn diagonal matrix accepting
     * a double array as the elements of the diagonal
     * @param diagonalElements
     * @return
     */
    public static Matrix diagonal(double... diagonalElements){
        Matrix output = Matrix.eye(diagonalElements.length);
        for (int i = 0; i < output.order(); i++) {
            output.values[i][i] = diagonalElements[i];
        }
        return output;
    }
    
    /**
     * Constructor for a nxn diagonal matrix accepting
     * a Vector as the elements of the diagonal
     * @param diagonalElements
     * @return
     */
    public static Matrix diagonal(Vector diagonalElements){
        Matrix output = Matrix.eye(diagonalElements.length());
        for (int i = 0; i < output.order(); i++) {
            output.values[i][i] = diagonalElements.getElement(i);
        }
        return output;
    }
    
    /**
     * Returns a Matrix with the rows and columns selected
     * by the vectors elements.
     * @param rows is a Vector containing the indexes of the desired rows
     * @param columns is a Vector containing the indexes of the desired columns
     * @return
     */
    public Matrix submatrix(Vector rows, Vector columns){
        Matrix output = new Matrix(rows.length(), columns.length());
        for(int i = 0; i < rows.length(); i++){
            for(int j = 0; j < columns.length(); j++) {
                output.values[i][j] = this.values[(int) rows.getElement(i)][(int) columns.getElement(j)];
            }
        }
        return output;
    }
    
    /**
     * Returns the double array containing the matrix elements
     * @return
     */
    public double[][] getValues() {
        return values;
    }

    /**
     * Sets the double array containing the matrix elements
     * @param values
     */
    public void setValues(double[][] values) {
        this.values = values.clone();
    }
    
    /**
     * Sets an element of the matrix
     * @param row index of the row
     * @param column index of the column
     * @param value value to set
     */
    public void setElement(int row, int column, double value){
        this.values[row][column] = value;
    }
    
    /**
     * Returns an element of the matrix
     * @param row index of the row
     * @param column index of the column
     * @return
     */
    public double getElement(int row, int column){
        return this.values[row][column];
    }
    
    /**
     * Returns the number of rows in the matrix
     * @return
     */
    public int rows(){
        return this.values.length;
    }
    
    /**
     * Returns the number of columns in the matrix
     * @return
     */
    public int columns(){
        return this.values[0].length;
    }
    
    /**
     * Returns the order of the matrix if this is squared
     * Otherwise throws an exception
     * @return
     */
    public int order(){
        if (this.rows() != this.columns()){
            throw new IllegalArgumentException("Only squared matrix return order");
        }
        return this.rows();
    }
    
    /**
     * Returns the total number of elements that the matrix has
     * @return
     */
    public int size(){
        return this.rows()*this.columns();
    }
    
    /**
     * Returns an int array with two element, the rows and the columns of
     * the matrix
     * @return
     */
    public int[] shape(){
        return new int[]{this.rows(),this.columns()};
    }
    
    /**
     * Returns a Vector with al the elements of a row
     * @param index index of thw row to return
     * @return
     */
    public Vector getRow(int index){
        return new Vector(this.values[index]);
    }
    
    /**
     * Sets a matrix row with the elements of a vector;
     * @param index
     * @param row
     */
    public void setRow(int index, Vector row){
        this.values[index] = row.getValues().clone();
    }
    
    /**
     * Sets a matrix column with the element of a vector
     * @param index
     * @param column
     */
    public void setColumn(int index, Vector column){
        for (int i = 0; i<this.values.length; i++){
            this.values[i][index] = column.getElement(i);
        }
    }
    
    /**
     * Returns a vector containing the elements of a column
     * @param index
     * @return
     */
    public Vector getColumn(int index){
        Vector out = new Vector(this.values.length);
        for (int i = 0; i<this.values.length; i++){
             out.setElement(i,this.getElement(i,index));
        }
        return out;
    }
    
    /**
     * Returns the dot product of the matrix with a vector
     * @param v
     * @return
     */
    public Vector dot(Vector v){
        if (this.columns() != v.length()){
            throw new IllegalArgumentException("Vector length must match row length.");
        }
        Vector output = new Vector(this.rows());
        for (int i = 0; i<this.rows(); i++){
            output.setElement(i, this.getRow(i).dotProduct(v));
        }
        return output;
    }
    
    /**
     * Returns a matrix with the result of adding this to another matrix b
     * @param b
     * @return
     */
    public Matrix add(Matrix b){
        if (this.rows() != b.rows() || this.columns() != b.columns()){
            throw new IllegalArgumentException("Matrix must have equal dimensions.");
        }
        Matrix output = new Matrix(this.rows(),this.columns());
        
        for (int i = 0; i<this.rows(); i++){
            for (int j = 0; j<this.columns(); j++){
                output.values[i][j] = this.values[i][j] + b.values[i][j];
            }
        }
        return output;
    }
    
    /**
     * Returns a matrix with the result of substrating a matrix
     * b from this matrix
     * @param b
     * @return
     */
    public Matrix subs(Matrix b){
        b = b.scalarProduct(-1);
        return this.add(b);
    }
    
    /**
     * Returns a matrix with the result of multiplying this matrix
     * with a scalar
     * @param scalar
     * @return
     */
    public Matrix scalarProduct(double scalar){
        Matrix output = new Matrix(this.rows(),this.columns());
        
        for (int i = 0; i<this.rows(); i++){
            for (int j = 0; j<this.columns(); j++){
                output.values[i][j] = scalar * this.values[i][j];
            }
        }
        return output;
    }
    
    /**
     * Return a matrix with the result of the elementwise product
     * of this matrix and another matrix b
     * @param b
     * @return
     */
    public Matrix elementwiseProduct(Matrix b){
        if (this.rows() != b.rows() || this.columns() != b.columns()){
            throw new IllegalArgumentException("Matrix must have equal dimensions.");
        }
        Matrix output = new Matrix(this.rows(),this.columns());
        
        for (int i = 0; i<this.rows(); i++){
            for (int j = 0; j<this.columns(); j++){
                output.values[i][j] = this.values[i][j] * b.values[i][j];
            }
        }
        return output;
    }
    
    /**
     * Return a matrix with the result of the elementwise division
     * of this matrix and another matrix b
     * @param b
     * @return
     */
    public Matrix elementwiseDivision(Matrix b){
        if (this.rows() != b.rows() || this.columns() != b.columns()){
            throw new IllegalArgumentException("Matrix must have equal dimensions.");
        }
        Matrix output = new Matrix(this.rows(),this.columns());
        
        for (int i = 0; i<this.rows(); i++){
            for (int j = 0; j<this.columns(); j++){
                output.values[i][j] = this.values[i][j] / b.values[i][j];
            }
        }
        return output;
    }
    
    /**
     * Returns the axb Matrix resulted from the matricial product
     * of a axn matrix (this) and a nxb matrix (b)
     * @param b
     * @return
     */
    public Matrix product(Matrix b){
        if (this.columns()!= b.rows()){
            throw new IllegalArgumentException("Matrix A columns must match Matrix B rows.");
        }
        Matrix out = new Matrix(this.rows(),b.columns());
        for (int i = 0; i<this.rows(); i++){
            for (int j = 0; j<b.columns();j++){
                out.values[i][j] = this.getRow(i).dotProduct(b.getColumn(j));
            }
        }
        return out;
    }
    
    /**
     * Returns the transposed matrix of this
     * @return
     */
    public Matrix transpose(){
        Matrix output = new Matrix(this.columns(), this.rows());
        for (int i = 0; i < this.rows(); i++){
            for (int j = 0; j < this.columns(); j++){
                output.values[j][i] = this.values[i][j];
            }
        }
        return output;
    }
    
    /**
     * Returns the determinant of a submatrix excluding the
     * specified row and column and set it's sign to positive 
     * or negative depending the excluded row and column.
     * @param row
     * @param column
     * @return
     */
    public double cofactor(int row, int column){
        
        int n = this.rows()-1;
        Matrix sub = new Matrix(n, n);
        int x = 0;
        int y = 0;
        for (int i=0; i<this.rows(); i++){
            for (int j=0; j<this.columns(); j++){
                if (i != row && j != column){
                    sub.values[x][y] = this.values[i][j];
                    y++;
                    if (y >= n){
                        x++;
                        y = 0;
                    }
                }
            }
        }
        return Math.pow(-1.0, row + column)* sub.det();
    }
    
    /**
     *  Returns the determinant of a matrix
     * @return
     */
    public double det(){
        if (this.rows() != this.columns()){
            throw new IllegalArgumentException("Matrix must be squared");
        }
        
        double det = 0.0;
        
        if (this.rows() == 1){
            det = this.getElement(0, 0);
        } else {
            for (int j = 0; j < this.columns(); j++){
                det += this.values[0][j] * this.cofactor(0,j);
            }
        }
        
        return det;
    }
    
    /**
     * Returns the characteristic polynomial of this matrix
     * @return
     */
    public Polynomial characteristicPolynomial(){
        double[] coefficients = new double[this.order() + 1];
        coefficients[this.order()] = Math.pow(-1, this.order());
        for (int i = this.order()-1; i > -1; i--) {
            Vector[] auxiliar = Vector.range(this.order()).getCombinations(this.order()-i);
            for (Vector auxiliar2: auxiliar){
                coefficients[i] += this.submatrix(auxiliar2, auxiliar2).det();
            }
            coefficients[i] *= Math.pow(-1, i);
        }
        return new Polynomial(coefficients);
    }
    
    /**
     * Returns the eigen value closest to the first aproximation
     * @param firstAprox
     * @return
     */
    public double eig(double firstAprox){
        if (this.rows() != this.columns()){
            throw new IllegalArgumentException("Matrix must be squared");
        }
        final Matrix aux = this;
        Function function = new Function(){
            @Override
            public double apply(double input) {
                Matrix lambdas = Matrix.eye(aux.order()).scalarProduct(input);
                return (aux.subs(lambdas)).det();
            }

            @Override
            public Function getDerivative() {
                return Solver.numericalDerivative(this);
            }

            @Override
            public Function getIntegral() {
                return null; 
            }
            
        };
        return Solver.newtonraphson(function, 1e-15, firstAprox);
    }
    
    /**
     * Returns the greates value of the matrix
     * @return
     */
    public double max(){
        double max = this.values[0][0];
        for(int i=0; i<this.rows(); i++){
            for (int j=0; j<this.columns(); j++){
                if(this.values[i][j] > max){
                    max = this.values[i][j];
                }
            }
        }
        return max;
    }
    
    /**
     * Returns the smallest value of the matrix
     * @return
     */
    public double min(){
        double min = this.values[0][0];
        for(int i=0; i<this.rows(); i++){
            for (int j=0; j<this.columns(); j++){
                if(this.values[i][j] < min){
                    min = this.values[i][j];
                }
            }
        }
        return min;
    }
    
    /**
     * Returns a vector V from a
     * MxV = B 
     * @param b
     * @return
     */
    public Vector solve(Vector b){
////        Matrix auxiliarMatrix = new Matrix(this.order());
//        for(int i = 0; i < this.rows(); i++){
//            for(int j = 0; j < this.columns(); j++){
//                auxiliarMatrix.values[i][j] = this.values[i][j];
//            }
//        }
        Matrix auxiliarMatrix = new Matrix(this.values);
        Vector V = new Vector(b.length());
        for (int i = 0; i < b.length(); i++){
            V.setElement(i, b.getElement(i));
        }
        for (int i = 0; i < this.order(); i++){
            double aux = auxiliarMatrix.values[i][i];
            for (int j = 0; j < this.order(); j++){
                if (j != i){
                    double coef = auxiliarMatrix.values[j][i]/aux;
                    auxiliarMatrix.setRow(j, auxiliarMatrix.getRow(j).subs(auxiliarMatrix.getRow(i).scalarProduct(coef)));
                    V.setElement(j, V.getElement(j)-V.getElement(i)*coef);
                }
            }
        }
        for (int i = 0; i < this.order(); i++){
            double aux = auxiliarMatrix.values[i][i];
            V.setElement(i, V.getElement(i)/aux);
        }
        
        return V;
    }
    
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i<this.values.length; i++){
            for (int j = 0; j<this.values[0].length; j++){
                s += this.values[i][j] + " ";
            }
            s += "\n";
        }
        return s;
    }
    
    /**
     * Returns true if this Matrix and a Matrix b are equals
     * @param b
     * @return
     */
    public boolean equals(Matrix b){
        if (this == b){
            return true;
        }
        if (this.shape() == b.shape()){
            for (int i=0; i<this.rows(); i++){
                for (int j=0; j<this.columns(); j++) {
                    if (this.values[i][j] != b.values[i][j]){
                        return false;
                    }    
                }
            }
            return true;
        }
        return false;
    }
}
