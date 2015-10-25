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
	// Level of scope directly inside this function.
	private int m_innerLevel;

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
		super (strName, returnType);
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
		super (strName, returnType);
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
		System.out.println("whoa");
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
	private Boolean compareParams(Vector<STO> params, Vector<STO> funcParams) {
		// param2 is the function params.
		int numParams = (params == null) ? 0 : params.size();
        int numfuncParams = (funcParams == null) ? 0: funcParams.size();

        if (numParams != numfuncParams) {
        	return false;
        }

        STO curParam, curFuncParam;
        Type paramType, funcParamType;
		for (int i = 0; i < numParams; i++) {
			curParam = params.get(i);
			curFuncParam = funcParams.get(i);
			paramType = curParam.getType();
			funcParamType = curFuncParam.getType();

			if (!paramType.isEquivalentTo(funcParamType)) {
				return false;
			}

			// Check for non-mod l-value case
			System.out.println("nelllyy");

			if (((VarSTO)curFuncParam).getPassByReference()) {
				System.out.println(curParam.getType());
				if (!curParam.isModLValue() && !(curParam.getType().isArray())) {
					return false;
				}
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

	public void setInnerLevel(int level) {
		m_innerLevel = level;
	}

	public int getInnerLevel() {
		return m_innerLevel;
	}
}

