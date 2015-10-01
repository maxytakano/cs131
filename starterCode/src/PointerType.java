//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// New Method for Pointer Typecheck created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

class PointerType extends CompositeType
{
	//----------------------------------------------------------------
	// Constructor for the Array type. All Pointer are 0 bits long
	//----------------------------------------------------------------
	public PointerType()
	{
		super("Pointer", 0);
	}

	//----------------------------------------------------------------
	// Check to see if the type is a Pointer Type
	//----------------------------------------------------------------
	public boolean isPointer()	{return true;}

}