//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// New Method for Float Type Check created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

class FloatType extends NumericType
{
	//----------------------------------------------------------------
	// Constructor for the Float type. All Floats are 32 bits long
	//----------------------------------------------------------------
	public FloatType()
	{
		super("Float", 32);
	}

	//----------------------------------------------------------------
	// Check to see if the type is a Float Type
	//----------------------------------------------------------------
	public boolean isFloat()	{return true;}

}