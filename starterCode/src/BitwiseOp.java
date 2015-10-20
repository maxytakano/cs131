//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// Operator tree class created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

//---------------------------------------------------------------------
//
//---------------------------------------------------------------------
abstract class BitwiseOp extends BinaryOp
{
    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    public BitwiseOp(String strName)
    {
        super(strName);
    }

    //----------------------------------------------------------------
    // Method for checking operands
    //----------------------------------------------------------------
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

        if ( !(aType.isInt()) ) {
            return new ErrorSTO( Formatter.toString(ErrorMsg.error1w_Expr, aType.getName(), getName(), IntType.TYPE_NAME) );
        } else if ( !(bType.isInt()) ) {
            return new ErrorSTO( Formatter.toString(ErrorMsg.error1w_Expr, bType.getName(), getName(), IntType.TYPE_NAME) );
        } else  {
            StringBuilder expr_builder = new StringBuilder();
            expr_builder.append(a.getName()).append(getName()).append(b.getName());

            STO expr;
            if (a.isConst() && b.isConst()) {
                int aVal, bVal;
                int result;
                String opName = getName();
                aVal = ((ConstSTO) a).getIntValue();
                bVal = ((ConstSTO) b).getIntValue();

                switch(opName) {
                    case "&":
                        result = aVal & aVal;
                        break;
                    case "^":
                        result = aVal ^ aVal;
                        break;
                    case "|":
                        result = aVal | aVal;
                        break;
                    default:
                        System.out.println("BitwiseOp: shouldn't be here");
                        result = 0;
                        break;
                }

                // both are const, return a const expr.
                expr = new ConstSTO(expr_builder.toString(), new IntType(), result);
            } else {
                // if any are var return a expr.
                expr = new ExprSTO(expr_builder.toString(), new IntType());
            }

            return expr;
        }
    }


}
