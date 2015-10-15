//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// Operator tree class created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

//---------------------------------------------------------------------
//
//---------------------------------------------------------------------
class ModOp extends ArithmeticOp
{
    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    public ModOp(String strName)
    {
        super(strName);
    }

    //----------------------------------------------------------------
    // Overloaded the Super's method for checking operands
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

        if ( !(aType.isInt()) ) {
            return new ErrorSTO( Formatter.toString(ErrorMsg.error1w_Expr, aType.getName(), getName(), IntType.TYPE_NAME) );
        } else if ( !(bType.isInt()) ) {
            return new ErrorSTO( Formatter.toString(ErrorMsg.error1w_Expr, bType.getName(), getName(), IntType.TYPE_NAME) );
        } else {
            StringBuilder expr_builder = new StringBuilder();
            expr_builder.append(a.getName()).append(getName()).append(b.getName());

            STO expr;
            if (a.isConst() && b.isConst()) {
                // both are const, return a const expr.
                expr = new ConstSTO(expr_builder.toString(), new IntType());
            } else {
                // if any are var return a expr.
                expr = new ExprSTO(expr_builder.toString(), new IntType());
            }
            
            return expr;
        }
    }

}
