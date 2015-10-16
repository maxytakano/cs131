//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// Operator tree class created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

import java.math.BigDecimal;

//---------------------------------------------------------------------
//
//---------------------------------------------------------------------
class ArithmeticOp extends BinaryOp
{
    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    public ArithmeticOp(String strName)
    {
        super(strName);
    }

    //----------------------------------------------------------------
    // Method for checking operands
    //----------------------------------------------------------------
    public STO checkOperands(STO a, STO b) {
        // operand types must be numeric, and the resulting type is int
        // when both ops are int, or float otherwise.

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
            String opName = getName();
            StringBuilder expr_builder = new StringBuilder();
            expr_builder.append(a.getName()).append(opName).append(b.getName());

            if (a.isConst() && b.isConst()) {
                // result should be a constExpr.
                ConstSTO constExpr;
                BigDecimal result = null;
                BigDecimal aVal, bVal;
                aVal = ((ConstSTO) a).getValue();
                bVal = ((ConstSTO) b).getValue();

                switch (opName) {
                    case "+":
                        result = aVal.add(bVal);
                        break;
                    case "-":
                        result = aVal.subtract(bVal);
                        break;
                    case "*":
                        result = aVal.multiply(bVal);
                        break;
                    case "/":
                        if (bVal.equals(BigDecimal.ZERO)) {
                            System.out.println(bVal);
                            // divide by 0 error
                            return new ErrorSTO(ErrorMsg.error8_Arithmetic);
                        }

                        result = aVal.divide(bVal);
                        break;
                    default:
                        System.out.println("arithop: shouln't be here");
                }

                System.out.println("result is: " + result + " /intval " + result.intValue() + " /floatval " + result.floatValue());

                if (aType.isInt() && bType.isInt()) {
                    constExpr = new ConstSTO(expr_builder.toString(), new IntType(), result.intValue());
                } else {
                    constExpr = new ConstSTO(expr_builder.toString(), new FloatType(), result.floatValue());
                }

                return constExpr;
            } else {
                // result is an expr.
                ExprSTO expr;

                if (aType.isInt() && bType.isInt()) {
                    expr = new ExprSTO(expr_builder.toString(), new IntType());
                } else {
                    expr = new ExprSTO(expr_builder.toString(), new FloatType());
                }

                return expr;
            }
        }
    }

}
