package linalg;

/*** A class that represents a two dimensional real-valued (double) matrix
 *   and supports various matrix computations required in linear algebra.
 *   
 *   Class and method comments are in JavaDoc: https://en.wikipedia.org/wiki/Javadoc
 * 
 * @author ssanner@mie.utoronto.ca, <YOUR_EMAIL>
 *
 */
public class Matrix {

	private int _nRows; // Number of rows in this matrix; nomenclature: _ for data member, n for integer
	private int _nCols;// Number of columns in this matrix; nomenclature: _ for data member, n for integer
        private double[][] _nMa; // double array for matrix
	
	
	/** Allocates a new matrix of the given row and column dimensions
	 * 
	 * @param rows
	 * @param cols
	 * @throws LinAlgException if either rows or cols is <= 0
	 */
	public Matrix(int rows, int cols) throws LinAlgException {
		if ((rows <= 0)||(cols <= 0))
                    throw new LinAlgException("Both dimensions must be greater than 0");  //row or col can not be smaller than 0
                _nRows = rows;
                _nCols = cols;
                _nMa = new double [rows][cols]; //new matrix is created 
	}
	
	/** Copy constructor: makes a new copy of an existing Matrix m
	 *                    (note: this explicitly allocates new memory and copies over content)
	 * 
	 * @param m
	 */
	public Matrix(Matrix m) {
	      _nRows = m._nRows;
              _nCols = m._nCols;  //make the row and col of matrix m equal to the row and col of the exsiting matrix
              _nMa = new double[_nRows][_nCols];
              for (int x = 0; x < _nRows; x++){
                   for(int y = 0; y < _nCols; y++){
                       _nMa[x][y] = m._nMa[x][y]; //new matrix is created with values from the exsiting matrix
                       
                 }
              }
	}

	/** Constructs a String representation of this Matrix
	 * 
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
                
                for (int x = 0; x < _nRows; x++){
                    sb.append("["); //assign [ to the start of each row in the matrix
                        for (int y = 0; y < _nCols; y++){
                            sb.append(String.format(" %6.3f ", _nMa[x][y])); //give each number in the matrix the same format
                    }
                    sb.append(" ] \n");  //ending with ], and jumps to the next line
                }
		return sb.toString();
	}

	/** Tests whether another Object o (most often a matrix) is a equal to *this*
	 *  (i.e., are the dimensions the same and all elements equal each other?)
	 * 
	 * @param o the object to compare to
	 */
	public boolean equals(Object o) {
		if ( o instanceof Matrix){
                    Matrix m = (Matrix)o;
                  if ((_nRows != m._nRows)|| (_nCols != m._nCols))
				return false; // Two matries cannot be equal if they don't have the same dimension
                  
			for (int x= 0; x < _nRows; x++){
                            for(int y = 0; y < _nCols; y++){
                                if (_nMa[x][y] != m._nMa[x][y])
					return false; // If two Vectors mismatch at any index, they are not equal
                            }
                        }	
			return true; // Everything matched... objects are equal!
		} else 
                    
		return false; // This should not always return false!
	}
	
	/** Return the number of rows in this matrix
	 *   
	 * @return 
	 */
	public int getNumRows() {
		return _nRows;
	}

	/** Return the number of columns in this matrix
	 *   
	 * @return 
	 */
	public int getNumCols() {
		return _nCols;
	}

	/** Return the scalar value at the given row and column of the matrix
	 * 
	 * @param row
	 * @param col
	 * @return
	 * @throws LinAlgException if row or col indices are out of bounds
	 */
	public double get(int row, int col) throws LinAlgException {
		if ((row < 0)|| (col < 0)|| (row >= _nRows)|| (col >= _nCols))
                    throw new LinAlgException ("One or both indices  are out of bounds "); //indices can not be out of bound
		else
                    return _nMa[row][col];
	}
	
	/** Return the Vector of numbers corresponding to the provided row index
	 * 
	 * @param row
	 * @return
	 * @throws LinAlgException if row is out of bounds
	 */
	public Vector getRow(int row) throws LinAlgException {
               Vector ret = new Vector(_nCols); //new vector with the size of the col of the matrix is created 
		if ((row < 0)|| (row > _nRows))
                    throw new LinAlgException ("Row index is out of bounds.");
                else
                    for (int x = 0; x < _nCols; x++)
                       ret.set(x,_nMa[row][x]);  //assign each element in that row of the matrix to the new vector
		return ret;
	}

	/** Set the row and col of this matrix to the provided val
	 * 
	 * @param row
	 * @param col
	 * @param val
	 * @throws LinAlgException if row or col indices are out of bounds
	 */
	public void set(int row, int col, double val) throws LinAlgException {
		if ((row < 0)||(col < 0)||(row >= _nRows)||(col >= _nCols))
                        throw new LinAlgException("One or both indices are out of bounds.");  //indices can not be out of bounds
                else
                    _nMa[row][col] = val;
                        
	}
	
	/** Return a new Matrix that is the transpose of *this*, i.e., if "transpose"
	 *  is the transpose of Matrix m then for all row, col: transpose[row,col] = m[col,row]
	 *  (should not modify *this*)
	 * 
	 * @return
	 * @throws LinAlgException
	 */
	public Matrix transpose() throws LinAlgException {
		Matrix transpose = new Matrix(_nCols, _nRows);
		for (int row = 0; row < _nRows; row++) {
			for (int col = 0; col < _nCols; col++) {
				transpose.set(col, row, get(row,col)); //make numbers in the col into row and vice versa
			}
		}
		return transpose;
	}

	/** Return a new Matrix that is the square identity matrix (1's on diagonal, 0's elsewhere) 
	 *  with the number of rows, cols given by size.  E.g., if size = 3 then the returned matrix
	 *  would be the following:
	 *  
	 *  [ 1 0 0 ]
	 *  [ 0 1 0 ]
	 *  [ 0 0 1 ]
	 * 
	 * @param size
	 * @return
	 * @throws LinAlgException if the size is <= 0
	 */
	public static Matrix GetIdentity(int size) throws LinAlgException {
		Matrix id = new Matrix(size, size);//identity matrix have same col and row
                if (size <= 0)
                    throw new LinAlgException ("Size must be bigger than 0");
                else
                    for (int x = 0; x < size; x++){
                        for (int y = 0; y < size; y++){
                            if (x == y)
                               id.set(x, y, 1.000);  //only the numbershave the same row and col index equal to 1.000
                         
                        }
                    }
                                      
		return id; //return the new tity matrix
	}
	
	/** Returns the Matrix result of multiplying Matrix m1 and m2
	 *  (look up the definition of matrix multiply if you don't remember it)
	 * 
	 * @param m1
	 * @param m2
	 * @return
	 * @throws LinAlgException if m1 columns do not match the size of m2 rows
	 */
	public static Matrix Multiply(Matrix m1, Matrix m2) throws LinAlgException{
		int newRow = m1.getNumRows(), newCol = m2.getNumCols(),loopCol = m1.getNumCols();
                Matrix mult = new Matrix(newRow,newCol);
		
                if (m1.getNumCols()!= m2.getNumRows())
                    throw new LinAlgException ("Cannot multiply two matries");  //cannot mult two matrix with the col of the first!= row of the second
                else
                    for (int x = 0;x < newRow; x++ ){ //finally loop the row of the first matrix
                        for (int y = 0; y < newCol; y++){  // then loop the col of the second one
                            for (int z = 0; z < loopCol; z++){  //first loop the col of the first one/row of the second 
                                mult._nMa[x][y] += m1._nMa[x][z] * m2._nMa[z][y];
                            }
                        }
                            
                    }
		return mult;//return the result matrix
	}
		
	/** Returns the Vector result of multiplying Matrix m by Vector v (assuming v is a column vector)
	 * 
	 * @param m
	 * @param v
	 * @return
	 * @throws LinAlgException if m columns do match the size of v
	 */
	public static Vector Multiply(Matrix m, Vector v) throws LinAlgException {
		int newRow = m.getNumRows(),loopCol = m.getNumCols(); 
                double result = 0;//set the result to 0
                Vector mult = new Vector(newRow); 
		
                if (m.getNumCols()!= v.getDim())
                    throw new LinAlgException ("Cannot multiply matrix with the given vector.");//cannot mult if the col of matrix != dim of vector
                else
                    for (int x = 0;x < newRow; x++ ){ //then loop the row of matrix
                            for (int z = 0; z < loopCol; z++){ //first loop the col of matrix/dim of vector
                                result += m._nMa[x][z]*v.get(z);
                            }
                          mult.set(x, result);  //assign the accumulated result to the vector
                          result = 0; //set result back 0 for next loop
                            
                    }
		return mult; //return result vector
	}

}
