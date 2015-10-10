//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// Operator tree class created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

//---------------------------------------------------------------------
//
//---------------------------------------------------------------------
abstract class ComparisionOp extends BinaryOp
{
    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    public ComparisionOp(String strName)
    {
        super(strName);
    }

    //----------------------------------------------------------------
    // Method for checking operands
    //----------------------------------------------------------------
    public STO checkOperands(STO a, STO b) {
        // double check this
        // if (a.isError()) {
        //     return a;
        // }
        // if (b.isError()) {
        //     return b;
        // }

        Type aType = a.getType();
        Type bType = b.getType();

        if ( !(aType.isNumeric()) ) {
            return new ErrorSTO( Formatter.toString(ErrorMsg.error1n_Expr, aType.getName(), getName()) );
        } else if ( !(bType.isNumeric()) ) {
            return new ErrorSTO( Formatter.toString(ErrorMsg.error1n_Expr, bType.getName(), getName()) );
        } else {
            StringBuilder expr_builder = new StringBuilder();
            expr_builder.append(a.getName()).append(getName()).append(b.getName());

            ExprSTO expr = new ExprSTO(expr_builder.toString(), new BooleanType());
            return expr;
        }
    }

}
