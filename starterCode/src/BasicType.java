//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// New Method for Basic Typecheck created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

abstract class BasicType extends Type
{
	//----------------------------------------------------------------
	// Constructor for the Basic type. All Basics are 32 bits long
	//----------------------------------------------------------------
	public BasicType()
	{
		super("Basic", 4);
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public BasicType(String strName, int size)
	{
		super(strName, size);
	}

	//----------------------------------------------------------------
	// Check to see if the type is a Basic Type
	//----------------------------------------------------------------
	public boolean isBasic()	{return true;}

	//----------------------------------------------------------------
	// Check to see if the type is assignable to an type
	//----------------------------------------------------------------
	public abstract boolean isAssignableTo(Type t);

	//----------------------------------------------------------------
	// Check to see if the type is a Type
	//----------------------------------------------------------------
	public abstract boolean isEquivalentTo(Type t);

}