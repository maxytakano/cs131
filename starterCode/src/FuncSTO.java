//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// Copyright (C) 2008-2015 Garo Bournoutian and Rick Ord
// University of California, San Diego
//---------------------------------------------------------------------
import java.util.Vector;

class FuncSTO extends STO
{
	private Type m_returnType;
	private Vector<STO> m_parameters;
	private Boolean m_returnByReference;
	private Boolean m_hasTopReturn;
	// This is a list of overloaded functions associated with this func
	private Vector<FuncSTO> overloadedFuncs;

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public FuncSTO(String strName)
	{
		super (strName);
		setReturnType(null);
		setHasTopReturn(false);
		// You may want to change the isModifiable and isAddressable
		// fields as necessary
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public FuncSTO(String strName, Type returnType)
	{
		super (strName);
		setReturnType(returnType);
		setHasTopReturn(false);
		// You may want to change the isModifiable and isAddressable
		// fields as necessary
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public FuncSTO(String strName, Type returnType, Boolean returnByReference)
	{
		super (strName);
		setReturnType(returnType);
		setReturnByReference(returnByReference);
		setHasTopReturn(false);
		// You may want to change the isModifiable and isAddressable
		// fields as necessary
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public boolean isFunc() 
	{
		return true;
		// You may want to change the isModifiable and isAddressable
		// fields as necessary
	}

	//----------------------------------------------------------------
	// This is the return type of the function. This is different from
	// the function's type (for function pointers - which we are not
	// testing in this project).
	//----------------------------------------------------------------
	public void setReturnType(Type typ)
	{
		m_returnType = typ;
	}

	//----------------------------------------------------------------
	// Set the parameters for the function sto
	//----------------------------------------------------------------
	public void setParameters(Vector<STO> params)
	{
		if (params != null) {
			m_parameters = (Vector<STO>)params.clone();
		} else {
			m_parameters = null;
		}
	}

	public Vector<STO> getParameters() {
		return m_parameters;
	}

	//----------------------------------------------------------------
	// Check 9a/9b stuff
	//----------------------------------------------------------------

	//----------------------------------------------------------------
	// Checks if this function or it's overloads has a matching set of
	// params to the passed in params.
	//----------------------------------------------------------------
	public boolean hasParamMatch(Vector<STO> params) {
		// 1. Check if passed params match this function's params
		if (compareParams(params, m_parameters)) {
			return true;
		}

		// 2. Check if passed params match any of this function's overload's
		// params.
		int numOverloadedFuncs = (overloadedFuncs == null) ? 0 : overloadedFuncs.size();
		FuncSTO curFunc;
		for (int i = 0; i < numOverloadedFuncs; i++) {
			curFunc = overloadedFuncs.get(i);
			if (compareParams(params, curFunc.getParameters())) {
	            return true;
			}
		}

		// 3. No matches found, return false
		return false;
	}

	//----------------------------------------------------------------
	// Compares one FuncSTO's params to another, returns true if they
	// are all identical. (All equivalent)
	//----------------------------------------------------------------
	private Boolean compareParams(Vector<STO> params1, Vector<STO> params2) {
		int numParams1 = (params1 == null) ? 0 : params1.size();
        int numParams2 = (params2 == null) ? 0: params2.size();

        if (numParams1 != numParams2) {
        	return false;
        }

        STO curParam1, curParam2;
        Type paramType1, paramType2;
		for (int i = 0; i < numParams1; i++) {
			curParam1 = params1.get(i);
			curParam2 = params2.get(i);
			paramType1 = curParam1.getType();
			paramType2 = curParam2.getType();

			if (!paramType1.isEquivalentTo(paramType2)) {
				return false;
			}
		}

		// everything matched, return true
		return true;
	}

	//----------------------------------------------------------------
	// Adds an overload function to this function.
	//----------------------------------------------------------------
	public void addOverload(FuncSTO overload) {
		if (overloadedFuncs == null) {
			// initialize overloadedFuncs for first overload.
			overloadedFuncs = new Vector<FuncSTO>();
			overloadedFuncs.add(overload);
		} else {
			overloadedFuncs.add(overload);
		}
	}

	public Vector<FuncSTO> getOverloads() {
		return overloadedFuncs;
	}

	//----------------------------------------------------------------
	// Returns a overloaded function matching the passed in args
	//----------------------------------------------------------------
	public FuncSTO getOverloadMatch(Vector<STO> args) {
		if (compareParams(args, m_parameters)) {
			return this;
		}

		if (overloadedFuncs == null) {
			return null;
		}

		int numOverloadedFuncs = overloadedFuncs.size();
		FuncSTO curFunc;
		for (int i = 0; i < numOverloadedFuncs; i++) {
			curFunc = overloadedFuncs.get(i);
			if (compareParams(args, curFunc.getParameters())) {
	            return curFunc;
			}
		}

		// Should never reach this.
		return null;
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public Type getReturnType ()
	{
		return m_returnType;
	}

	public void setReturnByReference(Boolean returnByReference) {
		m_returnByReference = returnByReference;
	}

	public Boolean isReturnByReference() {
		return m_returnByReference;
	}

	public void setHasTopReturn(Boolean hasTopReturn) {
		m_hasTopReturn = hasTopReturn;
	}

	public Boolean getHasTopReturn() {
		return m_hasTopReturn;
	}
}

