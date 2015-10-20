//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// Operator tree class created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

//---------------------------------------------------------------------
//
//---------------------------------------------------------------------
abstract class BooleanOp extends BinaryOp
{
    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    public BooleanOp(String strName)
    {
        super(strName);
    }

    //----------------------------------------------------------------
    // Abstract method for checking operands
    //----------------------------------------------------------------
    public STO checkOperands(STO a, STO b) {
        // operand types must be numeric, and the resulting type is int
        // when both ops are int, or float otherwise.

        // double check this
        if (a.isError()) {
            return a;
        }
        if (b.isError()) {
            return b;
        }

        Type aType = a.getType();
        Type bType = b.getType();

        if ( !(aType.isBoolean()) ) {
            return new ErrorSTO( Formatter.toString(ErrorMsg.error1w_Expr, aType.getName(), getName(), BooleanType.TYPE_NAME) );
        } else if ( !(bType.isBoolean()) ) {
            return new ErrorSTO( Formatter.toString(ErrorMsg.error1w_Expr, bType.getName(), getName(), BooleanType.TYPE_NAME) );
        } else {

            StringBuilder expr_builder = new StringBuilder();
            expr_builder.append(a.getName()).append(getName()).append(b.getName());

            STO expr;
            if (a.isConst() && b.isConst()) {
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
