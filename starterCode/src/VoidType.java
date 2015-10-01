//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// New Method for Void Typecheck created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

class VoidType extends Type
{
	//----------------------------------------------------------------
	// Constructor for the Void type. All Voids are 0 bits long
	//----------------------------------------------------------------
	public VoidType()
	{
		super("Void", 0);
	}

	//----------------------------------------------------------------
	// Check to see if the type is a Void Type
	//----------------------------------------------------------------
	public boolean isVoid()	{return true;}

}