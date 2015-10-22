//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// New Method for Struct Typecheck created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

//TODO WE NEED TO MAKE THIS NOT ABSTRACT AND GIVE IMPLEMMENTATION FOR
//ASSIGNABLETO AND EQUIVALENTTO
abstract class StructType extends CompositeType
{
	private Scope m_structScope;

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

	//----------------------------------------------------------------
	// Check to see if the type is assignable to an type
	//----------------------------------------------------------------
	public abstract boolean isAssignableTo(Type t);

	//----------------------------------------------------------------
	// Check to see if the type is a Type
	//----------------------------------------------------------------
	public abstract boolean isEquivalentTo(Type t);

	//----------------------------------------------------------------
	// Check 13
	//----------------------------------------------------------------
	public void addScope(Scope scope) {
		m_structScope = scope;
	}


}