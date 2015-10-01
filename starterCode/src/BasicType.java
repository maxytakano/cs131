//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// New Method for Basic Typecheck created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

class BasicType extends Type
{
	//----------------------------------------------------------------
	// Constructor for the Basic type. All Basics are 32 bits long
	//----------------------------------------------------------------
	public BasicType()
	{
		super("Basic", 32);
	}

	//----------------------------------------------------------------
	// Check to see if the type is a Basic Type
	//----------------------------------------------------------------
	public boolean isBasic()	{return true;}

}