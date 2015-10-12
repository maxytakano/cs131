//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// New Method for Void Typecheck created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

class VoidType extends Type
{
	public static final String TYPE_NAME = "void";

	//----------------------------------------------------------------
	// Constructor for the Void type. All Voids are 0 bits long
	//----------------------------------------------------------------
	public VoidType()
	{
		super(TYPE_NAME, 0);
	}

	//----------------------------------------------------------------
	// Check to see if the type is a Void Type
	//----------------------------------------------------------------
	public boolean isVoid()	{return true;}

	//----------------------------------------------------------------
    // Check to see if assignable
    //----------------------------------------------------------------
    //void can never be assigned to as far as I am aware.
    public boolean isAssignableTo(Type t) {return false;}

    //----------------------------------------------------------------
    // Check to see if type is equivalent to float
    //----------------------------------------------------------------
    public boolean isEquivalentTo(Type t) {return false;}

}