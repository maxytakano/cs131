//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// New Method for Composite Typecheck created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

class CompositeType extends Type
{
	//----------------------------------------------------------------
	// Constructor for the Composite type. All Composite are 0 bits long
	//----------------------------------------------------------------
	public CompositeType()
	{
		super("Composite", 0);
	}

	//----------------------------------------------------------------
	// Check to see if the type is a Composite Type
	//----------------------------------------------------------------
	public boolean isComposite()	{return true;}

}