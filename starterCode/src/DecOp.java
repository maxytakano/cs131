//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// Operator tree class created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

//---------------------------------------------------------------------
//
//---------------------------------------------------------------------
class DecOp extends UnaryOp
{
    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    public DecOp(String strName)
    {
        super(strName);
    }

    //----------------------------------------------------------------
    // Abstract method for checking operands
    //----------------------------------------------------------------
    public STO checkOperands(STO a, STO b){
        //Oh crud, this may not have been the best idea
        return new ErrorSTO( "How the Heck did you even get here? Just why?" );
    }

}
