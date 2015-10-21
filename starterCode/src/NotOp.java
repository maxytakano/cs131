//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// Operator tree class created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

import java.math.BigDecimal;

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

                boolean aVal;
                aVal = ((ConstSTO) a).getBoolValue();
                int booleanInt = (!(aVal)) ? 1 : 0;
                //VIVEK PRINTED HERE
                // System.out.println("------------------------------------------------------------------");
                // System.out.println("(in NotOp) Operator: " + getName());
                // System.out.println("Origanl aVal passed in: " + aVal);
                // System.out.println("int result of const folding NotOp: " + booleanInt);
                // System.out.println();
                BigDecimal result = new BigDecimal(booleanInt);

                expr = new ConstSTO(expr_builder.toString(), new BooleanType(), result);
            } else {
                // if any are var return a expr.
                expr = new ExprSTO(expr_builder.toString(), new BooleanType());
            }

            return expr;
        }
    }


}
