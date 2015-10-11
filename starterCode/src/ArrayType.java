//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// New Method for Array Typecheck created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------


//TODO WE NEED TO MAKE THIS NOT ABSTRACT AND GIVE IMPLEMMENTATION FOR 
//ASSIGNABLETO AND EQUIVALENTTO
abstract class ArrayType extends CompositeType
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

	//----------------------------------------------------------------
	// Check to see if the type is assignable to an type
	//----------------------------------------------------------------
	public abstract boolean isAssignableTo(Type t);

	//----------------------------------------------------------------
	// Check to see if the type is a Type
	//----------------------------------------------------------------
	public abstract boolean isEquivalentTo(Type t);

}