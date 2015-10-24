//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// New Method for Struct Typecheck created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

//TODO WE NEED TO MAKE THIS NOT ABSTRACT AND GIVE IMPLEMMENTATION FOR
//ASSIGNABLETO AND EQUIVALENTTO
class StructType extends CompositeType
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
	// Constructor for the Struct type. All Struct are 0 bits long
	//----------------------------------------------------------------
	public StructType(String name, Scope scope)
	{
		super(name, 0);
		addScope(scope);
	}

	//----------------------------------------------------------------
	// Check to see if the type is a Struct Type
	//----------------------------------------------------------------
	public boolean isStruct()	{return true;}

	//----------------------------------------------------------------
	// Check to see if the type is assignable to an type
	//----------------------------------------------------------------
	public boolean isAssignableTo(Type t) {
		if (isEquivalentTo(t)) {
			return true;
		} else {
			return false;
		}
	}

	//----------------------------------------------------------------
	// Check to see if the type is a Type
	//----------------------------------------------------------------
	public boolean isEquivalentTo(Type t) {
		if (t.isStruct()) {
			if ( t.getName().equals(getName()) ) {
				return true;
			}
		}
		return false;
	}

	//----------------------------------------------------------------
	// Check 13
	//----------------------------------------------------------------
	public void addScope(Scope scope) {
		m_structScope = scope;
	}

	//----------------------------------------------------------------
	// Check 14a
	//----------------------------------------------------------------
	public STO getCtor() {
		return m_structScope.access(getName());
	}

	//----------------------------------------------------------------
	// Check 14b
	//----------------------------------------------------------------
	public Boolean hasField(String id) {
		if (m_structScope.access(id) == null) {
			return false;
		} else {
			return true;
		}
	}

	public STO getField(String id) {
		return m_structScope.access(id);
	}

	public void addSize(int size) {
		int curSize = getSize();
		setSize(curSize + size);
	}

}