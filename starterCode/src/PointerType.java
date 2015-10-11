//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// New Method for Pointer Typecheck created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

//TODO WE NEED TO MAKE THIS NOT ABSTRACT AND GIVE IMPLEMMENTATION FOR 
//ASSIGNABLETO AND EQUIVALENTTO
abstract class PointerType extends CompositeType
{
	//----------------------------------------------------------------
	// Constructor for the Array type. All Pointer are 0 bits long
	//----------------------------------------------------------------
	public PointerType()
	{
		super("Pointer", 0);
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public PointerType(String strName, int size)
	{
		super(strName, size);
	}

	//----------------------------------------------------------------
	// Check to see if the type is a Pointer Type
	//----------------------------------------------------------------
	public boolean isPointer()	{return true;}


	//----------------------------------------------------------------
	// Check to see if the type is assignable to an type
	//----------------------------------------------------------------
	public abstract boolean isAssignableTo(Type t);

	//----------------------------------------------------------------
	// Check to see if the type is a Type
	//----------------------------------------------------------------
	public abstract boolean isEquivalentTo(Type t);
}