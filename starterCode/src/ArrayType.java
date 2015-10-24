//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// New Method for Array Typecheck created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

import java.util.Vector;
class ArrayType extends CompositeType
{

	public static final String TYPE_NAME = "Array";

	//----------------------------------------------------------------
	// Constructor for the Array type. All Arrays are 0 bits long
	//----------------------------------------------------------------
	//nextLevel points to the next level in the array, e.g. another array 
	//or a base type like int or float
	private VarSTO nextLevel = null;
	//The base type of this array, bool, float, int, etc. 
	private Type baseType = null;

	//!! not using
	//the dimensions of the current array
	// private Vector<Integer> dims;
	//!! not using

	//current dimension of this array
	private int currentDim = 0;

	public ArrayType()
	{
		super("Array", 0);
	}

	public ArrayType(int dim)
	{
		super("Array", dim);
		setCurrentDim(dim);
	}

	public ArrayType(Type t, int dim)
	{
		super("Array", dim);
		setBaseType(t);
		setCurrentDim(dim);
	}

	//----------------------------------------------------------------
	// Check to see if the type is a Array Type
	//----------------------------------------------------------------
	public boolean isArray()	{return true;}

	//----------------------------------------------------------------
	// Check to see if the type is assignable to an type
	//----------------------------------------------------------------
	public boolean isAssignableTo(Type t){
		if(baseType != null){
			if (nextLevel == null && baseType.isAssignableTo(t)){
				return true;
			}
		} else {
			System.out.println("ArrayType: Shouldn't get here, baseType shouldn't be null");
		}
		return false;
	}

	//----------------------------------------------------------------
	// Get name of this array
	//----------------------------------------------------------------
	public String getName(){
		String name = "" + baseType.getName() + "[" + getCurrentDim() + "]";
		VarSTO arry = getNextLevel();
		while(arry != null && arry.getType().isArray()){
			ArrayType myType = (ArrayType)arry.getType();
			name += "[" + myType.getCurrentDim() + "]";
			arry = myType.getNextLevel();
		}
		return name;
	}


	//----------------------------------------------------------------
	// Check to see if the type is a Type
	//----------------------------------------------------------------
	public boolean isEquivalentTo(Type t)
	{
		// old
		// if(!(t.isArray())){
		// 	return false;
		// }
		// ArrayType tempT = (ArrayType)t;

		// if(!(tempT.getBaseType().isEquivalentTo(getBaseType()))){
		// 	return false;
		// }

		// //clone it so that we have a copy and aren't messing with the original
		// Vector<Integer> otherDims = (Vector<Integer>)tempT.getDims().clone();
		// if(otherDims.size() != dims.size()){
		// 	return false;
		// }

		// for (int i = 0; i < dims.size(); i++){
		// 	if(otherDims.get(i) != dims.get(i)){
		// 		return false;
		// 	}
		// }

		// //base types are the same, and dimensions are the same, so equivalent
		// return true;

		if (t.isArray()) {
			if (t.getName().equals(getName())) {
				return true;
			}
		}
		return false;
	}

	//----------------------------------------------------------------
	// accessors are here
	//----------------------------------------------------------------
	public Type getBaseType(){
		return baseType;
	}

	// public Vector<Integer> getDims(){
	// 	return dims;
	// }

	public VarSTO getNextLevel(){
		return nextLevel;
	}

	public int getCurrentDim(){
		return currentDim;
	}

	public int getSize() {
		int i = getCurrentDim();
		Type t = getNextLevel().getType();
		while (t.isArray()) {
			i *= ((ArrayType) t).getCurrentDim();
			t = ((ArrayType) t).getNextLevel().getType();
		}

		if (baseType.isPointer()) {
			return i * ((PointerType) baseType).getBaseType().getSize();
		} else {
			return i * baseType.getSize();
		}

	}

	//----------------------------------------------------------------
	// mutators are here
	//----------------------------------------------------------------
	public void setBaseType(Type t){
		baseType = t;
	}

	public void setNextLevel(VarSTO t){
		nextLevel = t;
	}

	public void setCurrentDim(int dim){
		currentDim = dim;
	}

}