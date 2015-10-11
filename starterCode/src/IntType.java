//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// New Method for Int Type Check created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

class IntType extends NumericType
{
	public static final String TYPE_NAME = "int";

	//----------------------------------------------------------------
	// Constructor for the Int type. All Int are 32 bits long
	//----------------------------------------------------------------
	public IntType()
	{
		super(TYPE_NAME, 4);
	}

	//----------------------------------------------------------------
	// Check to see if the type is a Int Type
	//----------------------------------------------------------------
	public boolean isInt()	{return true;}

	//----------------------------------------------------------------
	// Check to see if the type is assignable to an int
	//----------------------------------------------------------------
	//floats cannot become ints: pg 15, check 3B
	//can bools become ints? i.e. true = 1 and false = 0?
	public boolean isAssignableTo(Type t) {return t.isInt();}

	//----------------------------------------------------------------
	// Check to see if the type is a Int Type
	//----------------------------------------------------------------
	public boolean isEquivalentTo(Type t) {return t.isInt();}

}