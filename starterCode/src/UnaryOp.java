//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// Operator tree class created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

//---------------------------------------------------------------------
//
//---------------------------------------------------------------------
abstract class UnaryOp extends Operator
{
    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    public UnaryOp(String strName)
    {
        super(strName);
    }

    //----------------------------------------------------------------
    // Method for checking operands. Should not be accessible for Unary.    
    //----------------------------------------------------------------
    public STO checkOperands(STO a, STO b){
        //Oh crud, this may not have been the best idea
        return new ErrorSTO( "How the Heck did you even get here? Just why?" );
    }

    //----------------------------------------------------------------
    // method for checking operands
    //----------------------------------------------------------------
    public STO checkOperand(STO a){
        /// operand types must be numeric, and the resulting type is int
        // when both ops are int, or float otherwise.

        // double check this
        // if (a.isError()) {
        //     return a;
        // }

        //Check for if it's modifyible first if not, return that error
        //This method is found in STO
        if(!(a.isModLValue())){
            return new ErrorSTO( Formatter.toString(ErrorMsg.error2_Lval, getName()));
        }

        Type aType = a.getType();

        if ( !(aType.isNumeric()) ) {
            return new ErrorSTO( Formatter.toString(ErrorMsg.error2_Type, aType.getName(), getName()) );
        }  else if (aType.isInt()) {

            StringBuilder expr_builder = new StringBuilder();
            expr_builder.append(a.getName()).append(getName());

            ExprSTO expr = new ExprSTO(expr_builder.toString(), new IntType());
            return expr;
        } else {

            StringBuilder expr_builder = new StringBuilder();
            expr_builder.append(a.getName()).append(getName());

            STO expr;
            if (a.isConst()) {
                // both are const, return a const expr.
                expr = new ConstSTO(expr_builder.toString(), new FloatType());
            } else {
                // if any are var return a expr.
                expr = new ExprSTO(expr_builder.toString(), new FloatType());
            }

            return expr;
        }
    }

}
