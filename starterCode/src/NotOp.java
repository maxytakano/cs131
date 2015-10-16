//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// Operator tree class created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

//---------------------------------------------------------------------
//
//---------------------------------------------------------------------
class NotOp extends UnaryOp
{
    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    public NotOp(String strName)
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

    //----------------------------------------------------------------
    // overwritten method for checking operands
    //----------------------------------------------------------------
    public STO checkOperand(STO a){
        /// operand types must be numeric, and the resulting type is int
        // when both ops are int, or float otherwise.
        // double check this
        // if (a.isError()) {
        //     return a;
        // }

        Type aType = a.getType();

        if ( !(aType.isBoolean()) ) {
            //TODO: NEED TO REPLACE "bool" WITH ACTUAL TYPE THINGY WE DID ON THURSDAY.
            return new ErrorSTO( Formatter.toString(ErrorMsg.error1u_Expr, aType.getName(), getName(), BooleanType.TYPE_NAME) );
        }  else {
            StringBuilder expr_builder = new StringBuilder();
            expr_builder.append(a.getName()).append(getName());

            STO expr;
            if (a.isConst()) {
                // both are const, return a const expr.
                expr = new ConstSTO(expr_builder.toString(), new BooleanType());
            } else {
                // if any are var return a expr.
                expr = new ExprSTO(expr_builder.toString(), new BooleanType());
            }

            return expr;
        }
    }


}
