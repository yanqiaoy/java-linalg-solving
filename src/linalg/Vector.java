package linalg;

/*** A class that represents a multidimensional real-valued (double) vector
 *   and supports various vector computations required in linear algebra.
 *   
 *   Class and method comments are in JavaDoc: https://en.wikipedia.org/wiki/Javadoc
 * 
 * @author ssanner@mie.utoronto.ca, <YOUR_EMAIL>
 *
 */
public class Vector {

	private int _nDim;       // Dimension of the Vector; nomenclature: _ for data member, n for integer
	private double[] _adVal; // Contents of the Vector; nomenclature: _ for data member, a for array, d for double

	/** Constructor: allocates space for a new vector of dimension dim
	 * 
	 * @param dim
	 * @throws LinAlgException if vector dimension is < 1
	 */
	public Vector(int dim) throws LinAlgException {
		if (dim <= 0)
			throw new LinAlgException("Vector dimension " + dim + " cannot be less than 1"); 
		_nDim = dim;
		_adVal = new double[dim]; // Entries will be automatically initialized to 0.0, new vector array in created
	}
	
	/** Copy constructor: makes a new copy of an existing Vector v
	 *                    (note: this explicitly allocates new memory and copies over content)
	 * 
	 * @param v
	 */
	public Vector(Vector v) {
		_nDim = v._nDim;  
		_adVal = new double[_nDim];  //new array is located with dimension as _nDim
		for (int index = 0; index < _nDim; index++)
			_adVal[index] = v._adVal[index];
	}  //assign value of the exsiting array to the new array

	/** Constructor: creates a new Vector with dimension and values given by init
	 * 
	 * @param init: a String formatted like "[ -1.2 2.0 3.1 5.8 ]" (must start with [ and end with ]), convert [xxxx] into vector type
	 * @throws LinAlgException if init is not properly formatted (missing [ or ], or improperly formatted number)
	 */
	public Vector(String init) throws LinAlgException {
		
		// The following says split init on whitespace (\\s) into an array of Strings
		String[] split = init.split("\\s");  
		// Uncomment the following to see what split produces
		// for (int i = 0; i < split.length; i++)
		// 		System.out.println(i + ". " + split[i]);

		if (!split[0].equals("[") || !split[split.length-1].equals("]")) //if the string is not started with [ or end with ]
			throw new LinAlgException("Malformed vector initialization: missing [ or ] in " + init);  //error

		// We don't count the [ and ] in the dimensionality
		_nDim = split.length - 2;
		_adVal = new double[_nDim];
		
		// Parse each number from init and add it to the Vector in order (note the +1 offset to account for [)
		for (int index = 0; index < _nDim; index++) {
			try {
				set(index, Double.parseDouble(split[index + 1]));
			} catch (NumberFormatException e) {
				throw new LinAlgException("Malformed vector initialization: could not parse " + split[index + 1] + " in " + init);
			}
		}
	}//done

	/** Overrides method toString() on Object: converts the vector class array to a human readable String
	 * 
	 *  Note 1: this is invoked *automatically* when the object is listed where a String is expected,
	 *          e.g., "System.out.println(v);" is actually equivalent to "System.out.println(v.toString());"       
	 *          
	 *  Note 2: for debugging purposes, you should always define a toString() method on a class you define
	 */
	@Override // optional annotation to tell Java we expect this overrides a parent method -- compiler will warn if not
	public String toString() {
		// We could just repeatedly append to an existing String, but that copies the String each
		// time, whereas a StringBuilder simply appends new characters to the end of the String
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < _nDim; i++)
			sb.append(String.format(" %6.3f ", _adVal[i])); // Append each vector value in order
		sb.append(" ]");
		return sb.toString();
	}

	/** Overrides address equality check on Object: allows semantic equality testing of vectors,
	 *  i.e., here we say two objects are equal iff they have the same dimensions and values
	 *        match at all indices
	 * 
	 * Note: you should almost always define equals() since the default equals() on Object simply
	 *       tests that two objects occupy the same space in memory (are actually the same instance), 
	 *       but does not test that two objects may be different instances but have the same content
	 *       
	 * @param o the object to compare to
	 */
	@Override // optional annotation to tell Java we expect this overrides a parent method -- compiler will warn if not
	public boolean equals(Object o) {
		if (o instanceof Vector) {
			Vector v = (Vector)o; // This is called a cast (or downcast)... we can do it since we
			                      // know from the if statement that o is actually of subtype Vector
			if (_nDim != v._nDim)
				return false; // Two Vectors cannot be equal if they don't have the same dimension
			for (int index = 0; index < _nDim; index++)
				if (_adVal[index] != v._adVal[index])
					return false; // If two Vectors mismatch at any index, they are not equal
			return true; // Everything matched... objects are equal!
		} else // if we get here "(o instanceof Vector)" was false
			return false; // Two objects cannot be equal if they don't have the same class type
	}
	
	/** Get the dimension of this vector
	 * 
	 * @return: the dimensionality of this Vector
	 */
	public int getDim() {
		return _nDim;
	}

	/** Returns the value of this vector at the given index (remember: array indices start at 0)
	 * 
	 * @param index
	 * @return
	 * @throws LinAlgException if array index is out of bounds (see throw examples above)
	 */
	public double get(int index) throws LinAlgException {
		if ((index >= 0)&& (index < _adVal.length)) //length is function that already exists
                    return _adVal[index];
                else
                    throw new LinAlgException("Index is out of bounds.");//index is out of bound so error
	}

	/** Set the value val of the vector at the given index (remember: array indices start at 0)
	 * 
	 * @param index
	 * @param val
	 * @throws LinAlgException if array index is out of bounds (see throw examples above)
	 */
	public void set(int index, double val) throws LinAlgException {
		if ((index >= 0)&& (index < _adVal.length))
                    _adVal[index] = val;
                else
                    throw new LinAlgException("Index is out of bounds.");//index is out of bounds so error
	}
	
	/** Change the dimension of this Vector by *reallocating array storage* and copying content over
	 *  ... if new dim is larger than current dim then the additional indices take value 0.0
	 *  ... if new dim is smaller than current dim then any indices in current vector beyond current
	 *      dim are simply lost
	 * 
	 * @param new_dim
	 * @throws LinAlgException if vector dimension is < 1
	 */
	public void changeDim(int new_dim) throws LinAlgException {
               if (new_dim < 1)
                   throw new LinAlgException("Vector dimension cannot be less than 1");
               
               double[] newArr = new double[new_dim]; //new array is created
               if(_nDim <= new_dim)
                   for (int index = 0; index < _nDim; index++)
                       newArr[index] = _adVal[index];  //remaining indeices in new array will automatically be zero
                             
               else if (_nDim > new_dim)
                   for (int index = 0; index < new_dim; index++)
                       newArr[index] = _adVal[index];
                     
                 
               
                _adVal = newArr; // copy the content over
                _nDim = new_dim;  //make the dimension of the exsiting vector equal to the new one
	}
	
	/** This adds a scalar d to all elements of *this* Vector
	 *  (should modify *this*)
	 * 
	 * @param d
	 */
	public void scalarAddInPlace(double d) {
		for (int index = 0; index < _nDim; index++)
			_adVal[index] += d;
	}
	
	/** This creates a new Vector, adds a scalar d to it, and returns it
	 *  (should not modify *this*)
	 * 
	 * @param d
	 * @return new Vector after scalar addition
	 */
	public Vector scalarAdd(double d) throws LinAlgException {  //need to check exception since vector constructor needs to be checked
                Vector addSca = new Vector(_nDim); 
                for (int index = 0; index < _nDim; index++)
			addSca._adVal[index] = _adVal[index]+d;
                    return addSca;  //return the new array	
        } 
	
	/** This multiplies a scalar d by all elements of *this* Vector
	 *  (should modify *this*)
	 * 
	 * @param d
	 */
	public void scalarMultInPlace(double d) {
		for (int index = 0; index < _nDim; index++)
                    _adVal[index] = _adVal[index]*d;  //exsiting array is changed
	}
	
	/** This creates a new Vector, multiplies it by a scalar d, and returns it
	 *  (should not modify *this*)
	 * 
	 * @param d
	 * @return new Vector after scalar addition
	 */
	public Vector scalarMult(double d) throws LinAlgException {
		Vector multSca = new Vector(_nDim); 
                for (int index = 0; index < _nDim; index++)
			multSca._adVal[index] = _adVal[index]*d;
                    return multSca;  //return the new array
	}

	/** Performs an elementwise addition of v to *this*, modifies *this*
	 * 
	 * @param v
	 * @throws LinAlgException if dimensions of the two operand vectors do not match
	 */
	public void elementwiseAddInPlace(Vector v) throws LinAlgException {
		if (v._nDim != _nDim)  
                    throw new LinAlgException("Cannot perform elementWiseAdd for vectors of different dimensions");
                else
                    for (int index = 0; index < _nDim; index++){
                        _adVal[index] +=  v._adVal[index];
                    }
                        
	}  //exsiting array is changed

	/** Performs an elementwise addition of *this* and v and returns a new Vector with result
	 * 
	 * @param v
	 * @return
	 * @throws LinAlgException if dimensions of the two operand vectors do not match
	 */
	public Vector elementwiseAdd(Vector v) throws LinAlgException {
                Vector addTwo = new Vector(this);  //this refers to the vector which called this method in the main class, make those two equal
		if (v._nDim != _nDim)
                    throw new LinAlgException("Cannot perform elementWiseAdd vectors of different dimensions ");
                else
                    addTwo.elementwiseAddInPlace(v);  //call the previous method to perform same type of addition
                return addTwo;
	}
	
	/** Performs an elementwise multiplication of v and *this*, modifies *this*
	 * 
	 * @param v
	 * @throws LinAlgException if dimensions of the two operand vectors do not match
	 */
	public void elementwiseMultInPlace(Vector v) throws LinAlgException {
		if (v._nDim != _nDim)
                    throw new LinAlgException("Cannot elementWiseMult vectors of different dimensions.");
                else
                    for (int index = 0; index < _nDim; index++){
                        _adVal[index] *=  v._adVal[index];
                    }   //exsiting array is changed
	}

	/** Performs an elementwise multiplication of *this* and v and returns a new Vector with result
	 * 
	 * @param v
	 * @return
	 * @throws LinAlgException if dimensions of the two operand vectors do not match
	 */
	public Vector elementwiseMult(Vector v) throws LinAlgException {
		 Vector multTwo = new Vector(this);
		if (v._nDim != _nDim)
                    throw new LinAlgException("Cannot elementWiseMult vectors of different dimensions.");
                else
                    multTwo.elementwiseMultInPlace(v);
                return multTwo;
	}

	/** Performs an inner product of Vectors v1 and v2 and returns the scalar result
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 * @throws LinAlgException
	 */
	public static double InnerProd(Vector v1, Vector v2) throws LinAlgException {
		double result = 0;
                if(v1._nDim != v2._nDim)
                    throw new LinAlgException("Cannot innerProd vectors of different dimensions");
                else
                     for (int index = 0; index < v1._nDim ; index++){
                          result += v1._adVal[index]*v2._adVal[index]; //for loop to keep adding
                }
                return result;
	}
}
