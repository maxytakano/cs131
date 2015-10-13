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

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public FuncSTO(String strName)
	{
		super (strName);
		setReturnType(null);
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
}

