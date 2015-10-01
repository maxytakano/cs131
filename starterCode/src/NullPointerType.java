//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// New Method for NullPointer Typecheck created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

class NullPointerType extends PointerType
{
	//----------------------------------------------------------------
	// Constructor for the NullPointer type. All NullPointer are 0 bits long
	//----------------------------------------------------------------
	public NullPointerType()
	{
		super("NullPointer", 0);
	}

	//----------------------------------------------------------------
	// Check to see if the type is a Array Type
	//----------------------------------------------------------------
	public boolean isNullPointer()	{return true;}

}