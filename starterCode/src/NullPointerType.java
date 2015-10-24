//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// New Method for NullPointer Typecheck created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

//TODO WE NEED TO MAKE THIS NOT ABSTRACT AND GIVE IMPLEMMENTATION FOR 
//ASSIGNABLETO AND EQUIVALENTTO
class NullPointerType extends PointerType
{
	//----------------------------------------------------------------
	// Constructor for the NullPointer type. All NullPointer are 0 bits long
	//----------------------------------------------------------------
	public NullPointerType()
	{
		super("nullptr", 4);
	}

	//----------------------------------------------------------------
	// Check to see if the type is a Array Type
	//----------------------------------------------------------------
	public boolean isNullPointer()	{return true;}

	//----------------------------------------------------------------
	// Check to see if the type is assignable to an type
	//----------------------------------------------------------------
	//TODO check if the Reference Compiler allows for assigning to nullptrs
	public boolean isAssignableTo(Type t) { 
		if(t.isPointer()){
			return true;
		} 
		return false;
	}

	//----------------------------------------------------------------
	// Check to see if the type is a Type
	//----------------------------------------------------------------
	//TODO REPLACE WITH ACTUAL 
	public boolean isEquivalentTo(Type t) {
		if(t.isPointer()){
			return true;
		}
		return false;
	}

}