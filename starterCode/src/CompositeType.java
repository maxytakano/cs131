//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// New Method for Composite Typecheck created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

abstract class CompositeType extends Type
{
	//----------------------------------------------------------------
	// Constructor for the Composite type. All Composite are 0 bits long
	//----------------------------------------------------------------
	public CompositeType()
	{
		super("Composite", 0);
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public CompositeType(String strName, int size)
	{
		super(strName, size);
	}

	//----------------------------------------------------------------
	// Check to see if the type is a Composite Type
	//----------------------------------------------------------------
	public boolean isComposite()	{return true;}

	//----------------------------------------------------------------
	// Check to see if the type is assignable to an type
	//----------------------------------------------------------------
	public abstract boolean isAssignableTo(Type t);

	//----------------------------------------------------------------
	// Check to see if the type is a Type
	//----------------------------------------------------------------
	public abstract boolean isEquivalentTo(Type t);

}