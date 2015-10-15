//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// Copyright (C) 2008-2015 Garo Bournoutian and Rick Ord
// University of California, San Diego
//---------------------------------------------------------------------

import java_cup.runtime.*;
import java.util.Vector;
import java.util.Iterator;

class MyParser extends parser
{
    private Lexer m_lexer;
    private ErrorPrinter m_errors;
    private boolean m_debugMode;
    private int m_nNumErrors;
    private String m_strLastLexeme;
    private boolean m_bSyntaxError = true;
    private int m_nSavedLineNum;

    private SymbolTable m_symtab;

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    public MyParser(Lexer lexer, ErrorPrinter errors, boolean debugMode)
    {
        m_lexer = lexer;
        m_symtab = new SymbolTable();
        m_errors = errors;
        m_debugMode = debugMode;
        m_nNumErrors = 0;
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    public boolean Ok()
    {
        return m_nNumErrors == 0;
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    public Symbol scan()
    {
        Token t = m_lexer.GetToken();

        //  We'll save the last token read for error messages.
        //  Sometimes, the token is lost reading for the next
        //  token which can be null.
        m_strLastLexeme = t.GetLexeme();

        switch (t.GetCode())
        {
            case sym.T_ID:
            case sym.T_ID_U:
            case sym.T_STR_LITERAL:
            case sym.T_FLOAT_LITERAL:
            case sym.T_INT_LITERAL:
                return new Symbol(t.GetCode(), t.GetLexeme());
            default:
                return new Symbol(t.GetCode());
        }
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    public void syntax_error(Symbol s)
    {
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    public void report_fatal_error(Symbol s)
    {
        m_nNumErrors++;
        if (m_bSyntaxError)
        {
            m_nNumErrors++;

            //  It is possible that the error was detected
            //  at the end of a line - in which case, s will
            //  be null.  Instead, we saved the last token
            //  read in to give a more meaningful error 
            //  message.
            m_errors.print(Formatter.toString(ErrorMsg.syntax_error, m_strLastLexeme));
        }
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    public void unrecovered_syntax_error(Symbol s)
    {
        report_fatal_error(s);
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    public void DisableSyntaxError()
    {
        m_bSyntaxError = false;
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    public void EnableSyntaxError()
    {
        m_bSyntaxError = true;
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    public String GetFile()
    {
        return m_lexer.getEPFilename();
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    public int GetLineNum()
    {
        return m_lexer.getLineNumber();
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    public void SaveLineNum()
    {
        m_nSavedLineNum = m_lexer.getLineNumber();
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    public int GetSavedLineNum()
    {
        return m_nSavedLineNum;
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    void DoProgramStart()
    {
        // Opens the global scope.
        m_symtab.openScope();
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    void DoProgramEnd()
    {
        m_symtab.closeScope();
    }

    // ** Phase 1 check 5 ** //

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    void DoVarDecl(String id, Type type)
    {
        if (m_symtab.accessLocal(id) != null)
        {
            m_nNumErrors++;
            m_errors.print(Formatter.toString(ErrorMsg.redeclared_id, id));
        }

        //VV: Passed in IntType arbitrarily as well so that the appropriate constructor is called and
        // we can make VarSTO a modifiable L Value
        //VarSTO sto = new VarSTO(id,new IntType());
        VarSTO sto = new VarSTO(id, type);
        m_symtab.insert(sto);
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    void DoExternDecl(String id)
    {
        if (m_symtab.accessLocal(id) != null)
        {
            m_nNumErrors++;
            m_errors.print(Formatter.toString(ErrorMsg.redeclared_id, id));
        }

        VarSTO sto = new VarSTO(id);
        m_symtab.insert(sto);
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    void DoConstDecl(String id, Type type)
    {
        if (m_symtab.accessLocal(id) != null)
        {
            m_nNumErrors++;
            m_errors.print(Formatter.toString(ErrorMsg.redeclared_id, id));
        }

        ConstSTO sto = new ConstSTO(id, type, 0);   // fix me
        m_symtab.insert(sto);
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    void DoStructdefDecl(String id)
    {
        if (m_symtab.accessLocal(id) != null)
        {
            m_nNumErrors++;
            m_errors.print(Formatter.toString(ErrorMsg.redeclared_id, id));
        }

        StructdefSTO sto = new StructdefSTO(id);
        m_symtab.insert(sto);
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    void DoFuncDecl_1(String id, Type returnType, Boolean returnByReference)
    {
        if (m_symtab.accessLocal(id) != null)
        {
            m_nNumErrors++;
            m_errors.print(Formatter.toString(ErrorMsg.redeclared_id, id));
        }

        FuncSTO sto = new FuncSTO(id, returnType, returnByReference);
        m_symtab.insert(sto);

        m_symtab.openScope();
        m_symtab.setFunc(sto);
    }

    //----------------------------------------------------------------
    // Closes the scope since we are done checking function
    //----------------------------------------------------------------
    void DoFuncDecl_2()
    {
        FuncSTO curFunc = m_symtab.getFunc();
        // check 6c
        if (!curFunc.getHasTopReturn() && !(curFunc.getReturnType().isVoid())  ) {
            m_nNumErrors++;
            m_errors.print(ErrorMsg.error6c_Return_missing);
        }
        m_symtab.closeScope();
        m_symtab.setFunc(null);
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    void DoFormalParams(Vector<STO> params)
    {
        if (m_symtab.getFunc() == null)
        {
            m_nNumErrors++;
            m_errors.print ("internal: DoFormalParams says no proc!");
        }

        // insert parameters here
        m_symtab.getFunc().setParameters(params);
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    void DoBlockOpen()
    {
        // Open a scope.
        m_symtab.openScope();
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    void DoBlockClose()
    {
        m_symtab.closeScope();
    }

    // ** Phase 1 Check 3a Check 3b **/
    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    STO DoAssignExpr(STO stoDes, STO stoExpr)
    {
        if (!stoDes.isModLValue())
        {
            // Good place to do the assign checks
            m_nNumErrors++;
            m_errors.print(ErrorMsg.error3a_Assign);
            return new ErrorSTO(ErrorMsg.error3a_Assign);
        }
        Type lhs = stoDes.getType();
        Type rhs = stoExpr.getType();
        if(!(rhs.isAssignableTo(lhs))){
            String errormsg = Formatter.toString(ErrorMsg.error3b_Assign, rhs.getName(), lhs.getName());
            m_nNumErrors++;
            m_errors.print(errormsg);
            return new ErrorSTO( errormsg );
        }
        else{
            //we need to figure out how to do the types
            return new VarSTO(lhs.getName());
        }
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    STO DoFuncCall(STO sto, Vector<STO> args)
    {
        if (!sto.isFunc()) {
            m_nNumErrors++;
            m_errors.print(Formatter.toString(ErrorMsg.not_function, sto.getName()));
            return new ErrorSTO(sto.getName());
        }

        Vector<STO> params = ((FuncSTO)sto).getParameters();

        int numArgs = (args == null) ? 0 : args.size();
        int numParams = (params == null) ? 0: params.size();


        if (numArgs == 0 && numParams == 0) {
            // Nothing to check, just return STO
            return sto;
        }

        // 1. check if # of args differs from # expected params
        if (numArgs != numParams) {
            // # params error
            m_nNumErrors++;
            m_errors.print(Formatter.toString(ErrorMsg.error5n_Call, numArgs, numParams));
            return new ErrorSTO(sto.getName());
        }

        // 2. check for corresponding param/arg errors
        STO curArg, curParam;
        Type argType, paramType;
        Boolean errorFlag = false; // Error flag keeps track if there is any errors
        for (int i = 0; i < numParams; i++) {
            // Extract each corresponding arg and param
            curArg = args.get(i);
            curParam = params.get(i);
            argType = curArg.getType();
            paramType = curParam.getType();

            if (!((VarSTO)curParam).getPassByReference()) {
                // if param is declared pass-by-value, make sure the argument is assignable to it
                if ( !(argType.isAssignableTo(paramType)) ) {
                    m_nNumErrors++;
                    m_errors.print(Formatter.toString(ErrorMsg.error5a_Call, argType.getName(),
                        curParam.getName(), paramType.getName()));
                    errorFlag = true;
                }
            } else if ( ((VarSTO)curParam).getPassByReference() ) {
                // if param is declares pass-by-reference, check that the arg type is equiv to param type
                if ( !(argType.isEquivalentTo(paramType)) ) {
                    m_nNumErrors++;
                    m_errors.print(Formatter.toString(ErrorMsg.error5r_Call, argType.getName(),
                        curParam.getName(), paramType.getName()));
                    errorFlag = true;
                } else if ( !(curArg.isModLValue()) ) {
                    m_nNumErrors++;
                    m_errors.print(Formatter.toString(ErrorMsg.error5c_Call, curParam.getName(), paramType.getName()));
                    errorFlag = true;
                }
            }
        }

        if (errorFlag) {
            return new ErrorSTO("error in param check");
        } else {
            if ( ((FuncSTO)sto).isReturnByReference() ) {
                sto.setIsAddressable(true);
                sto.setIsModifiable(true);
            }
            return sto;
        }
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    STO DoDesignator2_Dot(STO sto, String strID)
    {
        // Good place to do the struct checks

        return sto;
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    STO DoDesignator2_Array(STO sto)
    {
        // Good place to do the array checks

        return sto;
    }

    // ** Phase 0 ** //
    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    STO DoDesignator3_ID(String strID)
    {
        STO sto;

        if ((sto = m_symtab.access(strID)) == null)
        {
            m_nNumErrors++;
            m_errors.print(Formatter.toString(ErrorMsg.undeclared_id, strID));
            sto = new ErrorSTO(strID);
        }

        return sto;
    }

    //----------------------------------------------------------------
    // function for checking global scope resolution
    //----------------------------------------------------------------
    STO DoDesignator3_GLOBAL_ID(String strID)
    {
        STO sto;

        if ( (sto = m_symtab.accessGlobal(strID)) == null ) {
            m_nNumErrors++;
            m_errors.print(Formatter.toString(ErrorMsg.error0g_Scope, strID));
            sto = new ErrorSTO(strID);
        }

        return sto;
    }

    // ** Phase 1 check 1 ** //

    //----------------------------------------------------------------
    // Checks if we have a valid binary operation.
    //----------------------------------------------------------------
    STO doBinaryExpr(STO a, BinaryOp o, STO b) {
        if (a.isError()) {
            return a;
        }
        if (b.isError()) {
            return b;
        }

        STO result = o.checkOperands(a, b);
        if (result.isError()) {
            m_nNumErrors++;
            m_errors.print(result.getName());
            return result;
        }
        return result;
    }

    // ** Phase 1 check 2 **/

    //----------------------------------------------------------------
    // Checks if we have a valid unary operation.
    //----------------------------------------------------------------
    STO doUnaryExpr(STO a, UnaryOp o) {
        if (a.isError()) {
            return a;
        }

        STO result = o.checkOperand(a);
        if(result.isError()){
            m_nNumErrors++;
            m_errors.print(result.getName());
            return result;
        }
        return result;
    }

    // ** Phase 1 check 4 **/
    void doConditionCheck(STO expr) {
        // do we need to do this check here, or can the expr not be an error here?
        // if (expr.isError()) {
        //     m_nNumErrors++;
        //     m_errors.print(expr.getName);
        // }

        Type exprType = expr.getType();

        if (!exprType.isBoolean()) {
            m_nNumErrors++;
            m_errors.print( Formatter.toString(ErrorMsg.error4_Test, exprType.getName()) );
        }
    }

    /* Phase 1 check 6a */
    void doReturnVoidCheck() {
        Type returnType = m_symtab.getFunc().getReturnType();
        if (returnType != null) {
            m_nNumErrors++;
            m_errors.print(ErrorMsg.error6a_Return_expr);
        }
    }

    /* Phase 1 check 6b */
    void doReturnTypeCheck(STO expr) {
        Type exprType = expr.getType();
        FuncSTO curFunc = m_symtab.getFunc();
        Type returnType = curFunc.getReturnType();

        if (!curFunc.isReturnByReference()) {
            if (!exprType.isAssignableTo(returnType)) {
                // return expr not assignable to return type
                m_nNumErrors++;
                m_errors.print( Formatter.toString(ErrorMsg.error6a_Return_type, exprType.getName(),
                    returnType.getName()) );
            }
        } else {
             if (!exprType.isEquivalentTo(returnType)) {
                // return expr not equiv to return type of func
                m_nNumErrors++;
                m_errors.print( Formatter.toString(ErrorMsg.error6b_Return_equiv, exprType.getName(),
                    returnType.getName()) );
            } else if (!expr.isModLValue()) {
                // return expr not a mod L-value
                m_nNumErrors++;
                m_errors.print(ErrorMsg.error6b_Return_modlval);
            }
        }

        /* Phase 1 check 6c */
        int curlevel = m_symtab.getLevel();
        if (curlevel == 2) {
            // global level is 1, just inside function is level 2
            m_symtab.getFunc().setHasTopReturn(true);
        }
    }

    // ** Phase 1 Check 7** /
    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    void doExitCheck(STO expr) {
        Type exprType = expr.getType();

        if(exprType.isAssignableTo(new IntType())) {

            m_nNumErrors++;
            m_errors.print( Formatter.toString(ErrorMsg.error7_Exit, exprType.getName()));
        }
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    Type DoStructType_ID(String strID)
    {
        STO sto;

        if ((sto = m_symtab.access(strID)) == null)
        {
            m_nNumErrors++;
            m_errors.print(Formatter.toString(ErrorMsg.undeclared_id, strID));
            return new ErrorType();
        }

        if (!sto.isStructdef())
        {
            m_nNumErrors++;
            m_errors.print(Formatter.toString(ErrorMsg.not_type, sto.getName()));
            return new ErrorType();
        }

        return sto.getType();
    }
}
