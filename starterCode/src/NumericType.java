//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// New Method for Numeric Type Check created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

abstract class NumericType extends BasicType
{
	//----------------------------------------------------------------
	// Constructor for the Numeric type. All Numerics are 32 bits long
	//----------------------------------------------------------------
	public NumericType()
	{
		super("Numeric", 4);
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public NumericType(String strName, int size)
	{
		super(strName, size);
	}

	//----------------------------------------------------------------
	// Check to see if the type is a Numeric Type
	//----------------------------------------------------------------
	public boolean isNumeric()	{return true;}

	//----------------------------------------------------------------
	// Check to see if the type is assignable to an type
	//----------------------------------------------------------------
	public abstract boolean isAssignableTo(Type t);

	//----------------------------------------------------------------
	// Check to see if the type is a Type
	//----------------------------------------------------------------
	public abstract boolean isEquivalentTo(Type t);

}