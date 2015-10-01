//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// New Method for Array Typecheck created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

class ArrayType extends CompositeType
{
	//----------------------------------------------------------------
	// Constructor for the Array type. All Arrays are 0 bits long
	//----------------------------------------------------------------
	public ArrayType()
	{
		super("Array", 0);
	}

	//----------------------------------------------------------------
	// Check to see if the type is a Array Type
	//----------------------------------------------------------------
	public boolean isArray()	{return true;}

}