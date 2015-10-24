//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// New Method for Pointer Typecheck created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

class PointerType extends CompositeType
{
	//nextLevel points to the next level in the pointer, e.g. another pointer 
	//or a base type like int or float
	private Type nextLevel = null;
	//The base type of this pointer, bool, float, int, etc. 
	private Type baseType = null;

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

	public PointerType(Type t)
	{
		super("Pointer", 0);
		setBaseType(t);
	}

	//----------------------------------------------------------------
	// Check to see if the type is a Pointer Type
	//----------------------------------------------------------------
	public boolean isPointer()	{return true;}


	//----------------------------------------------------------------
	// Check to see if the type is assignable to an type
	//----------------------------------------------------------------
	public boolean isAssignableTo(Type t) {
// System.out.println("-------------------------------------------");
// System.out.println("type t in pointertype: " + t.getName());
// System.out.println("this type in PointerType: " + getName());
// System.out.println("tnext: " + ((PointerType)t).getNextLevel());
// System.out.println("thisnext: " + getNextLevel());
// System.out.println();
		if(t.isError()){
			return false;
		}
		//if t isn't a pointer, and we're in here, we have an error
		if(t.isNullPointer()){
			return true;
		}
		if(!t.isPointer()){
			return false;
		}
		if( ((PointerType) t).getNextLevel().isPointer() && !(getNextLevel().isPointer()) ){
			return false;
		}
		if( !(((PointerType) t).getNextLevel().isPointer()) && getNextLevel().isPointer() ){
			return false;
		}
		if( !(((PointerType) t).getNextLevel().isPointer()) && !(getNextLevel().isPointer()) ){
			return ((PointerType) t).getNextLevel().isAssignableTo(nextLevel);
		}
		else{
			return nextLevel.isAssignableTo(((PointerType) t).getNextLevel());
		}
	} //FIX ME

	//----------------------------------------------------------------
	// Check to see if the type is a Type
	//----------------------------------------------------------------
	public boolean isEquivalentTo(Type t) {
		if(t.isPointer()){
			if(t.getName().equals(getName())){
				return true;
			}
		}
		return false;
	} //FIX ME

	//----------------------------------------------------------------
	// Get name of this pointer
	//----------------------------------------------------------------
	public String getName(){
		String name = "" + ((baseType != null) ? (baseType.getName() + "*") : "nullptr");
		Type pointer = getNextLevel();
		while(pointer != null && pointer.isPointer()){
			PointerType myType = (PointerType)pointer;
			name += "*";
			pointer = myType.getNextLevel();
			// System.out.println("here: " + arry);
		}
		return name;
	}

	//----------------------------------------------------------------
	// accessors are here
	//----------------------------------------------------------------
	public Type getBaseType(){
		return baseType;
	}
	
	public Type getNextLevel(){
		return nextLevel;
	}

	//----------------------------------------------------------------
	// mutators are here
	//----------------------------------------------------------------
	public void setBaseType(Type t){
		baseType = t;
	}

	public void setNextLevel(Type t){
		nextLevel = t;
	}
}