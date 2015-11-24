//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// New Method for String Type Check created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

class StringType extends BasicType
{
    public static final String TYPE_NAME = "string";

    //----------------------------------------------------------------
    // Constructor for the String type.
    //----------------------------------------------------------------
    public StringType()
    {
        super(TYPE_NAME, 0);
    }

    //----------------------------------------------------------------
    // Check to see if the type is a String Type
    //----------------------------------------------------------------
    public boolean isString()  {return true;}

    //----------------------------------------------------------------
    // Check to see if assignable
    //----------------------------------------------------------------
    //will 1 become true and 0 become false?
    public boolean isAssignableTo(Type t) {return t.isString();}

    //----------------------------------------------------------------
    // Check to see if type is equivalent to bool
    //----------------------------------------------------------------
    public boolean isEquivalentTo(Type t) {return t.isString();}

}