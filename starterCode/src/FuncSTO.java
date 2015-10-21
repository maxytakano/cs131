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
	private Vector<FuncSTO> overloadFuncs;

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
	// Compares one FuncSTO's params to another, returns true if they
	// are all identical.
	//----------------------------------------------------------------
	public Boolean compareParams(Vector<STO> params) {
		int numMyParams = (m_parameters == null) ? 0 : m_parameters.size();
        int numParams = (params == null) ? 0: params.size();

        if (numMyParams != numParams) {
        	return false;
        }

        STO curMyParam, curParam;
        Type myParamType, paramType;
		for (int i = 0; i < numMyParams; i++) {
			curMyParam = m_parameters.get(i);
			curParam = params.get(i);
			myParamType = curMyParam.getType();
			paramType = curParam.getType();

			if (!myParamType.isEquivalentTo(paramType)) {
				return false;
			}
		}

		// everything matched, return true
		return true;
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

