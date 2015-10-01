//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// New Method for Boolean Type Check created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

class BooleanType extends BasicType
{
	//----------------------------------------------------------------
	// Constructor for the Boolean type. All Booleans are 32 bits long
	//----------------------------------------------------------------
	public BooleanType()
	{
		super("Boolean", 32);
	}

	//----------------------------------------------------------------
	// Check to see if the type is a Boolean Type
	//----------------------------------------------------------------
	public boolean isBoolean()	{return true;}

}