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
            System.out.println("a not numberic");
            return new ErrorSTO( Formatter.toString(ErrorMsg.error1n_Expr, aType.getName(), getName()) );
        } else if ( !(bType.isInt()) ) {
            System.out.println("b not numberic");
            return new ErrorSTO( Formatter.toString(ErrorMsg.error1n_Expr, bType.getName(), getName()) );
        } else {

            StringBuilder expr_builder = new StringBuilder();
            expr_builder.append(a.getName()).append(getName()).append(b.getName());

            System.out.println("returning int type " + expr_builder.toString());

            ExprSTO expr = new ExprSTO(expr_builder.toString(), new IntType());
            return expr;
        }
    }

}
