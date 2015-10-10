//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// Operator tree class created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

//---------------------------------------------------------------------
//
//---------------------------------------------------------------------
class EqualOp extends ComparisionOp
{
    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    public EqualOp(String strName)
    {
        super(strName);
    }

    // override relation ops
    public STO checkOperands(STO a, STO b) {
        // double check this
        if (a.isError()) {
            return a;
        }
        if (b.isError()) {
            return b;
        }

        Type aType = a.getType();
        Type bType = b.getType();

        if ( aType.isNumeric() && bType.isNumeric() ) {
            StringBuilder expr_builder = new StringBuilder();
            expr_builder.append(a.getName()).append(getName()).append(b.getName());

            ExprSTO expr = new ExprSTO(expr_builder.toString(), new BooleanType());
            return expr;
        } else if ( aType.isBoolean() && bType.isBoolean() ) {
            StringBuilder expr_builder = new StringBuilder();
            expr_builder.append(a.getName()).append(getName()).append(b.getName());

            ExprSTO expr = new ExprSTO(expr_builder.toString(), new BooleanType());
            return expr;
        } else {
            return new ErrorSTO( Formatter.toString(ErrorMsg.error1b_Expr, aType.getName(), getName(), bType.getName()) );
        }
    }

}
