//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// Copyright (C) 2008-2015 Garo Bournoutian and Rick Ord
// University of California, San Diego
//---------------------------------------------------------------------

import java.math.BigDecimal;

class VarSTO extends STO
{
	private Boolean m_passByReference;

	private BigDecimal		m_value;

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public VarSTO(String strName)
	{
		super(strName);
		// You may want to change the isModifiable and isAddressable
		// fields as necessary
	}

	public VarSTO(String strName, Type typ)
	{
		//we want VarSTOs to always be modifiable L Values, so we
		//pass is true and true for addressable and modifiable
		super(strName, typ, true, true);
		setPassByReference(false);
		// You may want to change the isModifiable and isAddressable
		// fields as necessary
	}

	public VarSTO(String strName, Type typ, boolean optref)
	{
		// TODO: possibly use if logic on opt ref to determine modifiable and adressable
		super(strName, typ, true, true);
		setPassByReference(optref);
	}

	public VarSTO(String strName, Type typ, boolean modifiable, boolean addressable)
	{
		//we want VarSTOs to always be modifiable L Values, so we
		//pass is true and true for addressable and modifiable
		super(strName, typ, modifiable, addressable);
		setPassByReference(false);
		// You may want to change the isModifiable and isAddressable
		// fields as necessary
	}

	public VarSTO(String strName, Type typ, int val)
	{
		super(strName, typ);
		m_value = new BigDecimal(val);
		// You may want to change the isModifiable and isAddressable
		// fields as necessary
	}

	public VarSTO(String strName, Type typ, double val)
	{
		super(strName, typ);
		m_value = new BigDecimal(val);
		// You may want to change the isModifiable and isAddressable
		// fields as necessary
	}

	public VarSTO(String strName, Type typ, BigDecimal val)
	{
		super(strName, typ);
		m_value = val;
	}

	public void setPassByReference(boolean optref) {
		m_passByReference = optref;
	}

	public boolean getPassByReference() {
		return m_passByReference;
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public boolean isVar() 
	{
		return true;
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public BigDecimal getValue() 
	{
		return m_value;
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public int getIntValue() 
	{
		return m_value.intValue();
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public float getFloatValue() 
	{
		return m_value.floatValue();
	}
}
