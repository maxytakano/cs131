//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// New Method for Boolean Type Check created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

class BooleanType extends BasicType
{
	public static final String TYPE_NAME = "bool";

	//----------------------------------------------------------------
	// Constructor for the Boolean type. All Booleans are 32 bits long
	//----------------------------------------------------------------
	public BooleanType()
	{
		super(TYPE_NAME, 4);
	}

	//----------------------------------------------------------------
	// Check to see if the type is a Boolean Type
	//----------------------------------------------------------------
	public boolean isBoolean()	{return true;}

}