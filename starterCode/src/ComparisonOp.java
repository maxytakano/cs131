//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// Operator tree class created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

import java.math.BigDecimal;

//---------------------------------------------------------------------
//
//---------------------------------------------------------------------
abstract class ComparisonOp extends BinaryOp
{
    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    public ComparisonOp(String strName)
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

            String opName = getName();
            StringBuilder expr_builder = new StringBuilder();
            expr_builder.append(a.getName()).append(getName()).append(b.getName());

            STO expr;
            if (a.isConst() && b.isConst()) {
                // both are const, return a const expr.
                ConstSTO constExpr;
                BigDecimal result = null;
                BigDecimal aVal, bVal;
                aVal = ((ConstSTO) a).getValue();
                bVal = ((ConstSTO) b).getValue();
                boolean comparisonResult = false;

                switch (opName) {
                    case "<":
                        comparisonResult = aVal.floatValue() < bVal.floatValue();
                        break;
                    case ">":
                        comparisonResult = aVal.floatValue() > bVal.floatValue();
                        break;
                    case ">=":
                        comparisonResult = aVal.floatValue() >= bVal.floatValue();
                        break;
                    case "<=":
                        comparisonResult = aVal.floatValue() <= bVal.floatValue();
                        break;
                    default:
                        System.out.println("BooleanOp: shouln't be here");
                }

                //need to pass in an int to bigdecimal, so use a ternary operator to 1 = true 0 = false
                int booleanInt = (comparisonResult) ? 1 : 0;

                //VIVEK PRINTED HERE
                // System.out.println("------------------------------------------------------------------");
                // System.out.println("(in ComparisonOp) Operator: " + opName);
                // System.out.println("result of const folding ComparisonOp: " + comparisonResult);
                // System.out.println("int result of const folding ComparisonOp: " + booleanInt);
                // System.out.println();

                result = new BigDecimal(booleanInt);
                expr = new ConstSTO(expr_builder.toString(), new BooleanType(), result);
            } else {
                // if any are var return a expr.
                expr = new ExprSTO(expr_builder.toString(), new BooleanType());
            }
            
            return expr;
        }
    }

}
