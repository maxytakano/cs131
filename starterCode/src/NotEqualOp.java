//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// Operator tree class created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

import java.math.BigDecimal;

//---------------------------------------------------------------------
//
//---------------------------------------------------------------------
class NotEqualOp extends ComparisonOp
{
    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    public NotEqualOp(String strName)
    {
        super(strName);
    }

    // override relation ops
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

        if ( aType.isNumeric() && bType.isNumeric() ) {
            StringBuilder expr_builder = new StringBuilder();
            expr_builder.append(a.getName()).append(getName()).append(b.getName());

            STO expr;
            if (a.isConst() && b.isConst()) {
                // both are const, return a const expr.
                float aVal, bVal;
                aVal = ((ConstSTO) a).getFloatValue();
                bVal = ((ConstSTO) b).getFloatValue();

                int booleanInt = (aVal != bVal) ? 1 : 0;
                //VIVEK PRINTED HERE
                System.out.println("------------------------------------------------------------------");
                System.out.println("(in NotEqualOp) Operator: " + getName());
                System.out.println("int result of const folding NotEqualOp: " + booleanInt);
                System.out.println();
                BigDecimal result = new BigDecimal(booleanInt);

                expr = new ConstSTO(expr_builder.toString(), new BooleanType(), result);
            } else {
                // if any are var return a expr.
                expr = new ExprSTO(expr_builder.toString(), new BooleanType());
            }
            return expr;
        } else if ( aType.isBoolean() && bType.isBoolean() ) {
            StringBuilder expr_builder = new StringBuilder();
            expr_builder.append(a.getName()).append(getName()).append(b.getName());

            STO expr;
            if (a.isConst() && b.isConst()) {
                // both are const, return a const expr.
                boolean aVal, bVal;
                aVal = ((ConstSTO) a).getBoolValue();
                bVal = ((ConstSTO) b).getBoolValue();

                int booleanInt = (aVal != bVal) ? 1 : 0;

                //VIVEK PRINTED HERE
                System.out.println("------------------------------------------------------------------");
                System.out.println("(in NotEqualOp) Operator: " + getName());
                System.out.println("int result of const folding NotEqualOp: " + booleanInt);
                System.out.println();
                BigDecimal result = new BigDecimal(booleanInt);

                expr = new ConstSTO(expr_builder.toString(), new BooleanType(), result);
            } else {
                // if any are var return a expr.
                expr = new ExprSTO(expr_builder.toString(), new BooleanType());
            }

            return expr;
        } else {
            return new ErrorSTO( Formatter.toString(ErrorMsg.error1b_Expr, aType.getName(), getName(), bType.getName()) );
        }
    }

}
