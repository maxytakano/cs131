//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// Operator tree class created by Max Takano and Vivek Venugopal
// Fall 2015
//---------------------------------------------------------------------

import java.math.BigDecimal;

//---------------------------------------------------------------------
//
//---------------------------------------------------------------------
class UnaryOp extends Operator
{
    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    public UnaryOp(String strName)
    {
        super(strName);
    }

    //----------------------------------------------------------------
    // Method for checking operands. Should not be accessible for Unary.    
    //----------------------------------------------------------------
    public STO checkOperands(STO a, STO b){
        //Oh crud, this may not have been the best idea
        return new ErrorSTO( "How the Heck did you even get here? Just why?" );
    }

    //----------------------------------------------------------------
    // method for checking operands
    //----------------------------------------------------------------
    public STO checkOperand(STO a){

        // double check this
        if (a.isError()) {
            return a;
        }

        //Check for if it's numeric first if not, return that error
        //check to see if sto is a funcsto, if so, we need to grab it's return type instead
        Type aType;
        if(a.isFunc()){
            aType = ((FuncSTO) a).getReturnType();

            if(!(a.isModLValue())){
                return new ErrorSTO( Formatter.toString(ErrorMsg.error2_Lval, getName()));
            }

            if ( !(aType.isNumeric()) && !(aType.isPointer()) ) {
                return new ErrorSTO( Formatter.toString(ErrorMsg.error2_Type, aType.getName(), getName()) );
            }
        }else{
            //This method is found in STO
            aType = a.getType();

            if ( !(aType.isNumeric()) && !(aType.isPointer()) ) {
                return new ErrorSTO( Formatter.toString(ErrorMsg.error2_Type, aType.getName(), getName()) );
            }

            if(!(a.isModLValue())){
                return new ErrorSTO( Formatter.toString(ErrorMsg.error2_Lval, getName()));
            }
        }

        // if(!(a.isModLValue())){
        //     return new ErrorSTO( Formatter.toString(ErrorMsg.error2_Lval, getName()));
        // }

        // if ( !(aType.isNumeric()) && !(aType.isPointer()) ) {
        //     return new ErrorSTO( Formatter.toString(ErrorMsg.error2_Type, aType.getName(), getName()) );
        // }

        StringBuilder expr_builder = new StringBuilder();
        expr_builder.append(a.getName()).append(getName());

        //we can never have a constant for the unary ops since ++ requires a modifyable L-Val

        //here it's numeric and not Const, so we have ExprSTO
        ExprSTO expr;

        if(aType.isInt()) {
            expr = new ExprSTO(expr_builder.toString(), new IntType());
        } else if(aType.isFloat()) {
            expr = new ExprSTO(expr_builder.toString(), new FloatType());
        } else {
            // its a pointer.
            expr = new ExprSTO(expr_builder.toString(), new PointerType());
        }

        return expr;
    }

}
