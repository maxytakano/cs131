//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// New Method for Struct Typecheck created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

class StructType extends CompositeType
{
	//----------------------------------------------------------------
	// Constructor for the Struct type. All Struct are 0 bits long
	//----------------------------------------------------------------
	public StructType()
	{
		super("Struct", 0);
	}

	//----------------------------------------------------------------
	// Check to see if the type is a Struct Type
	//----------------------------------------------------------------
	public boolean isStruct()	{return true;}

}