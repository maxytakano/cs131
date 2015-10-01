//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// New Method for Int Type Check created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

class IntType extends NumericType
{
	//----------------------------------------------------------------
	// Constructor for the Int type. All Int are 32 bits long
	//----------------------------------------------------------------
	public IntType()
	{
		super("Int", 32);
	}

	//----------------------------------------------------------------
	// Check to see if the type is a Int Type
	//----------------------------------------------------------------
	public boolean isInt()	{return true;}

}