//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// Copyright (C) 2008-2015 Garo Bournoutian and Rick Ord
// University of California, San Diego
//---------------------------------------------------------------------

import java_cup.runtime.*;
import java.util.Vector;
import java.util.Iterator;
import java.math.BigDecimal;

class MyParser extends parser
{
    private Lexer m_lexer;
    private ErrorPrinter m_errors;
    private boolean m_debugMode;
    private int m_nNumErrors;
    private String m_strLastLexeme;
    private boolean m_bSyntaxError = true;
    private int m_nSavedLineNum;

    private int m_foreachwhileflag;

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
        m_foreachwhileflag = -1;
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
    void DoVarDecl(String id, Type type, STO optInit)
    {

        if (m_symtab.accessLocal(id) != null)
        {
            m_nNumErrors++;
            m_errors.print(Formatter.toString(ErrorMsg.redeclared_id, id));
        }

        if (optInit != null) {
            if (optInit.isError()) {
                m_symtab.insert(new VarSTO(id, type));
                return;
            }
            Type initType = optInit.getType();
            // user decided to initialize the variable, type check it

            if (!initType.isAssignableTo(type) && !initType.isNullPointer()) {
                m_nNumErrors++;
                m_errors.print(Formatter.toString(ErrorMsg.error8_Assign, initType.getName(), type.getName()));
                m_symtab.insert(new VarSTO(id, type));
                return;
            }
        }

        VarSTO sto = new VarSTO(id, type);
        m_symtab.insert(sto);
    }

    void doForEachCheck(String lhs_id, Type lhs_type, boolean ref, STO rhs_array){
        if(rhs_array.isError() || lhs_type.isError()){
            return;
        }

        //make sure rhs is actually an array
        if(!(rhs_array.getType().isArray())){
            m_nNumErrors++;
            m_errors.print(ErrorMsg.error12a_Foreach);
            return;
        }

        VarSTO myRHS_Array = ((ArrayType) rhs_array.getType()).getNextLevel();

        //if it's pass by value (ref == false) check if rhs is assignable to lhs
        if(!ref){
            if(!(myRHS_Array.getType().isAssignableTo(lhs_type))){
                m_nNumErrors++;
                m_errors.print(Formatter.toString(ErrorMsg.error12v_Foreach, 
                                                    myRHS_Array.getType().getName(), lhs_id, lhs_type.getName()));
                return;
            }
        }

        //if it's pass by ref (ref == true) check if rhs type is assignable to lhs
        if(ref){
            if(!(myRHS_Array.getType().isEquivalentTo(lhs_type))){
                m_nNumErrors++;
                m_errors.print(Formatter.toString(ErrorMsg.error12r_Foreach, 
                                                    myRHS_Array.getType().getName(), lhs_id, lhs_type.getName()));
                return;
            }
        }

        //if we're here, we can initialize our variable in the foreach
        //don't forget to set the foreachwhile flag
        DoVarDecl(lhs_id, lhs_type, myRHS_Array);
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    void DoVarDecl_4Params(String id, Type type, Vector<STO> arraySizes, STO optInit)
    {
        //rather than adding on more params to doVarDecl, I'm just gonna do this here
        //for more modularity
        //If arraySizes is null, we just want to call the default vardecl
        if(arraySizes == null){
            //optInit not null, so call the default doVarDecl.
            DoVarDecl(id, type, optInit);
            return;
        }

        //if we're here, we definitely have an an array.
        if (m_symtab.accessLocal(id) != null)
        {
            m_nNumErrors++;
            m_errors.print(Formatter.toString(ErrorMsg.redeclared_id, id));
            return;
        }

        //array actually made in this helper method
        STO sto = makeAnArray(id, type, arraySizes);
        if(sto.isError()){
            return;
        }

        m_symtab.insert(sto);
    }

    STO makeAnArray(String id, Type type, Vector<STO> arraySizes){

        if(type.isError()){
            return new ErrorSTO(id);
        }
        if(arraySizes.isEmpty()){
            return new VarSTO(id, type, true, true);
        }
        else{
            //if it's an error, it's been printed. Any instantiation errors are
            //constSTOs.
            if(arraySizes.firstElement().isError()){
                // m_nNumErrors++;
                // m_errors.print(arraySizes.firstElement().getName());
                return arraySizes.firstElement();
            }
            //a[expr]; expr's errors haven't been printed yet, but they will be here
            //any errors when making the array are printed here
            ConstSTO myconst = ((ConstSTO)arraySizes.firstElement()); 
            if(myconst.getType().isError()){
                m_nNumErrors++;
                m_errors.print(myconst.getName());
                return new ErrorSTO(myconst.getName());
            }
            //no errors, so we do the actual array making here.
            int val = myconst.getIntValue();
            ArrayType myArrayType = new ArrayType(type, val);
            arraySizes.remove(0);
            STO recursedSTO = makeAnArray(id, type, arraySizes);
            if(recursedSTO.isError()){
                return recursedSTO;
            }
            VarSTO myVarSTO = (VarSTO) recursedSTO;
            myArrayType.setNextLevel(myVarSTO);
            //the new varsto will be an non-mod lval, so addressable, but not modifiable
            return new VarSTO(id, myArrayType, false, true);
        }
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
    void DoConstDecl(String id, Type type, STO constExpr)
    {
        if (m_symtab.accessLocal(id) != null)
        {
            m_nNumErrors++;
            m_errors.print(Formatter.toString(ErrorMsg.redeclared_id, id));
        }

        if (constExpr == null) {
            // TODO: either set to 0 or throw an error.
            // value of constExpr not known, throw an error
            m_nNumErrors++;
            // m_errors.print(Formatter.toString(ErrorMsg.error8_CompileTime, id));
            return;
        } else if (constExpr.isError()) {
            // m_symtab.insert(new ConstSTO(id, type));
            return;
        } else if (!constExpr.isConst()) {
            // value of constExpr not known, throw an error
            m_nNumErrors++;
            m_errors.print(Formatter.toString(ErrorMsg.error8_CompileTime, id));
            // m_symtab.insert(new ConstSTO(id, type));
            return;
        } else {
            // check assignment
            Type initType = constExpr.getType();
            // user decided to initialize the variable, type check it
            if (!initType.isAssignableTo(type) && !initType.isNullPointer()) {
                m_nNumErrors++;
                m_errors.print(Formatter.toString(ErrorMsg.error8_Assign, initType.getName(), type.getName()));
                // m_symtab.insert(new ConstSTO(id, type));
                return;
            }
        }

        ConstSTO sto = new ConstSTO(id, type, ((ConstSTO)constExpr).getValue());
        m_symtab.insert(sto);
    }

    //----------------------------------------------------------------
    // Check 13
    //----------------------------------------------------------------

    //----------------------------------------------------------------
    // Sets the current structType in the symtab
    //----------------------------------------------------------------
    void setCurrentStructType(String id) {
        Scope curScope = m_symtab.getScope();
        StructType structType = new StructType(id, curScope);
        m_symtab.setStructType(structType);
    }

    //----------------------------------------------------------------
    // Clears the structType in the symtab
    //----------------------------------------------------------------
    void clearStructType() {
        m_symtab.setStructType(null);
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    void DoStructdefDecl(String id) {
        if (m_symtab.accessLocal(id) != null)
        {
            m_nNumErrors++;
            m_errors.print(Formatter.toString(ErrorMsg.redeclared_id, id));
        }

        // StructType structType = new StructType(id, scope);

        StructdefSTO sto = new StructdefSTO(id, m_symtab.getStructType());
        m_symtab.insert(sto);
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    void doStructVarDecl(Type type, String id, Vector<STO> arraySizes) {
        // Check if this is a redeclaration in the struct's scope.
        if (m_symtab.accessLocal(id) != null)
        {
            m_nNumErrors++;
            m_errors.print(Formatter.toString(ErrorMsg.error13a_Struct, id));
            return;
        }

        // Otherwise insert it into the struct's scope.
        VarSTO sto;
        if(arraySizes == null){
            sto = new VarSTO(id, type);
            // m_symtab.getStructType().addSize(sto.getType().getSize());
            m_symtab.getStructType().addSize( ((ConstSTO)getObjSize(sto)).getIntValue() );
        }
        else{
            sto = (VarSTO)makeAnArray(id, type, arraySizes);
            m_symtab.getStructType().addSize( ((ConstSTO)getObjSize(sto)).getIntValue() );
        }
        m_symtab.insert(sto);
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    Boolean checkForCtor(String id) {
        if (m_symtab.accessLocal(id) == null) {
            return false;
        } else {
            return true;
        }
    }

    //----------------------------------------------------------------
    // Creates a default ctor for the current struct
    //----------------------------------------------------------------
    void createDefaultCtor(String id) {
        FuncSTO sto = new FuncSTO(id, new VoidType(), false);
        m_symtab.insert(sto);
    }

    //----------------------------------------------------------------
    // Check 14
    //----------------------------------------------------------------
    void doCtorCall(Type structType, Vector<STO> params) {
        STO structCtor;

        // Check if we are in a struct, and the name is the same, if so
        // use the current structs ctor. 
        if (m_symtab.getStructType() != null) {
            if (m_symtab.getStructType().getName() == structType.getName()) {
                structCtor = m_symtab.getStructType().getCtor();
                DoFuncCall(structCtor, params);
                return;
            }
        } 

        STO structSTO = m_symtab.access(structType.getName());
        structCtor = ((StructType)structSTO.getType()).getCtor();
        // STO structCtor = m_symtab.getStructType().getCtor();
        DoFuncCall(structCtor, params);
    }

    //----------------------------------------------------------------
    // Check 14
    //----------------------------------------------------------------
    // void doNewCtorCall(STO sto, Vector<STO> params) {
    //     Type structType = ((PointerType)sto.getType()).getNextLevel();
    //     if (!structType.isStruct()) {
    //         return;
    //     }

    //     STO structSTO = m_symtab.access(structType.getName());
    //     STO structCtor = ((StructType)structSTO.getType()).getCtor();
    //     DoFuncCall(structCtor, params);
    // }

    STO getThis() {
        // Create the R - value with the current struct type
        ExprSTO thisSTO = new ExprSTO("this", m_symtab.getStructType(), false, false);
        return thisSTO;
    }

    // helper for param decl


    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    void DoFuncDecl_1(String id, Type returnType, Boolean returnByReference, Boolean isCtorDtor)
    {
        /*  check 13b CTOR/DTOR DECL in a struct checks */
        Type structType;
        // Check if we are in a struct's ctor or dtor
        if ((structType = m_symtab.getStructType()) != null && isCtorDtor) {
            String ctorDtorName = id;
            Boolean isDtor = (ctorDtorName.charAt(0) == '~');

            if (isDtor) {
                // in a dtor, do the redeclare check
                if (m_symtab.accessLocal(id) != null) {
                    m_nNumErrors++;
                    m_errors.print( Formatter.toString(ErrorMsg.error9_Decl, id) );
                    // return;
                }

                // Modify the dtor's name (removing '~') for the not same namecheck
                ctorDtorName = ctorDtorName.substring(1);
            }

            // Check if the ctor/dtor is the not the same name as the struct.
            if (!(structType.getName()).equals(ctorDtorName)) {
                m_nNumErrors++;
                if (isDtor) {
                    m_errors.print(Formatter.toString(ErrorMsg.error13b_Dtor, id, structType.getName()));
                } else {
                    m_errors.print(Formatter.toString(ErrorMsg.error13b_Ctor, id, structType.getName()));
                }
                // return;
            }

            if (isDtor) {
                // if we are a dtor and passed all checks, insert into symtab safely.
                FuncSTO sto = new FuncSTO(id, returnType, returnByReference);
                m_symtab.setFunc(sto);
                m_symtab.insert(sto);
                m_symtab.openScope();
                // System.out.println("idDtor if: " + m_symtab.getLevel());
                m_symtab.getFunc().setInnerLevel(m_symtab.getLevel());
                return;
            }
        }

        // 1. Get the function (could be a var also) associated with the passed id in the given scope
        STO symtabObject;
        if (m_symtab.getStructType() != null) {
            symtabObject = m_symtab.accessLocal(id);
        } else {
            symtabObject = m_symtab.access(id);
        }

        // 2. Check if the there was a non-function value related to id in the symtable
        if (symtabObject != null) {
            if (!symtabObject.isFunc()) {
                // Check if we are in a struct to know which error to throw
                if (m_symtab.getStructType() != null) {
                    // Throw 13a_struct error
                    m_nNumErrors++;
                    m_errors.print(Formatter.toString(ErrorMsg.error13a_Struct, id));
                    // m_symtab.setFunc(null);
                    // return;
                } else {
                    // Throw regular redeclared_id error.
                    m_nNumErrors++;
                    m_errors.print(Formatter.toString(ErrorMsg.redeclared_id, id));
                    // m_symtab.setFunc(null);
                    // return;
                }
            }
        }

        FuncSTO sto = new FuncSTO(id, returnType, returnByReference);
        m_symtab.setFunc(sto);
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    void DoFormalParams(Vector<STO> params, String id)
    {
        if (m_symtab.getFunc() == null)
        {
            return;
        }

        // 3. now we can assume we have a candidate and existing function.
        // old
        // FuncSTO existingFunc = (FuncSTO) m_symtab.access(id);

        // new
        FuncSTO existingFunc;
        if (m_symtab.getStructType() != null) {
            if (m_symtab.accessLocal(id) == null) {
                existingFunc = null;
            } else if (!m_symtab.accessLocal(id).isFunc()) {
                existingFunc = null;
            } else {
                existingFunc = (FuncSTO) m_symtab.accessLocal(id);
            }
        } else {
            if (m_symtab.accessLocal(id) == null) {
                existingFunc = null;
            } else if (!m_symtab.access(id).isFunc()) {
                existingFunc = null;
            } else {
                existingFunc = (FuncSTO) m_symtab.access(id);
            }
        }
        // end new

        FuncSTO candidateFunc = m_symtab.getFunc();
        candidateFunc.setParameters(params);

        // 1. Null check to see if there is a existing function or not.
        if (existingFunc != null) {
            // 2. Check if we can overload the function
            if ( existingFunc.hasParamMatch(candidateFunc.getParameters()) ) {
                // Matching parameters, can't overload.
                m_nNumErrors++;
                m_errors.print( Formatter.toString(ErrorMsg.error9_Decl, existingFunc.getName()) );
                m_symtab.openScope();
                insertParams(candidateFunc.getParameters());
                m_symtab.getFunc().setInnerLevel(m_symtab.getLevel());

                // trying
                // Set the current function to null to halt further checks.
                // m_symtab.setFunc(null);
            } else {
                // No matching parameters found, overload the function.
                existingFunc.addOverload(candidateFunc);
                // TODO: check if open scope here is correct.
                m_symtab.openScope();
                // System.out.println("overloadfunction : " + m_symtab.getLevel());
                insertParams(candidateFunc.getParameters());
                m_symtab.getFunc().getOverloadMatch(params).setInnerLevel(m_symtab.getLevel());
            }
        } else {
            // 3. If there is no existing function, insert a new entry to symtab.
            m_symtab.insert(candidateFunc);
            // TODO: check if open scope here is correct.
            m_symtab.openScope();
            // System.out.println("normal func insert: " + m_symtab.getLevel());
            insertParams(candidateFunc.getParameters());
            m_symtab.getFunc().setInnerLevel(m_symtab.getLevel());
        }
    }

    //----------------------------------------------------------------
    // Inserts a vector of params into the symtab
    //----------------------------------------------------------------
    void insertParams(Vector<STO> params) {
        if (params == null) {
            return;
        }

        for (int i = 0; i < params.size(); i++) {
            params.get(i).getName();
            m_symtab.insert(params.get(i));
        }
    }

    //----------------------------------------------------------------
    // Closes the scope since we are done checking function
    //----------------------------------------------------------------
    void DoFuncDecl_2()
    {
        if (m_symtab.getFunc() == null)
        {
            // func param comparison failed.
            return;
        }
        FuncSTO curFunc = m_symtab.getFunc();

        // check 6c
        if (!curFunc.getHasTopReturn() && !(curFunc.getReturnType().isVoid())  ) {
            m_nNumErrors++;
            m_errors.print(ErrorMsg.error6c_Return_missing);
        }
        m_symtab.closeScope();
        // System.out.println("do funcdecl 2 end: " + m_symtab.getLevel());
        m_symtab.setFunc(null);
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    void DoBlockOpen()
    {
        // Open a scope.
        m_symtab.openScope();
        // System.out.println("do block open: " + m_symtab.getLevel());
    }

    //----------------------------------------------------------------
    // closes the scope
    //----------------------------------------------------------------
    void DoBlockClose()
    {

        m_symtab.closeScope();
        if(m_symtab.getLevel() <= m_foreachwhileflag){
            m_foreachwhileflag = -1;
        }
        // System.out.println("do block close: " + m_symtab.getLevel());

    }

    //----------------------------------------------------------------
    // check to see if break and continue statements are within scope of
    // a foreach stmt (or while loop)
    //----------------------------------------------------------------
    void breakContinueCheck(int a)
    {
        //if it's -1, it's not inside a foreach or while loop
        if(m_foreachwhileflag == -1){
            m_nNumErrors++;
            if(a == 1){
                m_errors.print(ErrorMsg.error12_Break);
            }
            else if(a == 2){
                m_errors.print(ErrorMsg.error12_Continue);
            }
        }
    }

    //----------------------------------------------------------------
    // closes the scope, and returns it
    //----------------------------------------------------------------
    Scope DoScopeCapture()
    {
        return m_symtab.captureScope();
    }

    // ** Phase 1 Check 3a Check 3b **/
    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    STO DoAssignExpr(STO stoDes, STO stoExpr)
    {
        if (stoExpr.isError()) {
            return stoExpr;
        }
        if(stoDes.isError()){
            return stoDes;
        }

        if (!stoDes.isModLValue())
        {
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
            //keep same name as lhs (stoDes), keep same type as lhs (stoDes)
            VarSTO newVar = new VarSTO(stoDes.getName(), stoDes.getType());
            if(stoExpr.isConst()){
                //if the rhs is a const, we can place the value in to varsto already
                //get the bigdecimal
                BigDecimal constVal = ((ConstSTO) stoExpr).getValue();
                //put it into the varsto
                newVar = new VarSTO(stoDes.getName(), stoDes.getType(), constVal);
            }
            else{
                //we don't have a const at compile time to place into here
                newVar = new VarSTO(stoDes.getName(), stoDes.getType());
            }
            return newVar;
        }
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    STO DoFuncCall(STO sto, Vector<STO> args)
    {
        // Check if the function is an error
        if (sto.isError()) {
            return sto;
        }

        if (!sto.isFunc()) {
            m_nNumErrors++;
            m_errors.print(Formatter.toString(ErrorMsg.not_function, sto.getName()));
            return new ErrorSTO(sto.getName());
        }

        /* Check 9b */
        FuncSTO func = (FuncSTO) sto;
        Vector<FuncSTO> overloads = func.getOverloads();
        // 1. Check if this is an overloaded function
        if (overloads != null) {
            // 2. Function is overloaded, check if there is a function match (all equivalent params).
            if (func.hasParamMatch(args)) {
                // There is a function match, get the corresponding function to the passed args.
                FuncSTO matchingFunc = func.getOverloadMatch(args);

                VarSTO returnSTO = new VarSTO(matchingFunc.getName(), matchingFunc.getReturnType(), false, false);
                if ( matchingFunc.isReturnByReference() ) {
                    returnSTO.setIsAddressable(true);
                    returnSTO.setIsModifiable(true);
                }

                return returnSTO;
            } else {
                // No exact match, throw illegal overload call error
                m_nNumErrors++;
                m_errors.print( Formatter.toString(ErrorMsg.error9_Illegal, func.getName()) );
                // return since we only throw error9_illegal for overloads.
                return new ErrorSTO(func.getName());
            }
        }

        /* Check 5 */
        Vector<STO> params = func.getParameters();

        int numArgs = (args == null) ? 0 : args.size();
        int numParams = (params == null) ? 0: params.size();

        // 1. check if # of args differs from # expected params
        if (numArgs != numParams) {
            // # params error
            m_nNumErrors++;
            m_errors.print(Formatter.toString(ErrorMsg.error5n_Call, numArgs, numParams));
            return new ErrorSTO(func.getName());
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

            if (curArg.isError()) {
                errorFlag = true;
                continue;
            }

            if (!((VarSTO)curParam).getPassByReference()) {
                // if param is declared pass-by-value, make sure the argument is assignable to it
                if ( !(argType.isAssignableTo(paramType))) {
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
                } else if ( !(curArg.isModLValue()) && !(curArg.getType().isArray()) ) {
                    m_nNumErrors++;
                    m_errors.print(Formatter.toString(ErrorMsg.error5c_Call, curParam.getName(), paramType.getName()));
                    errorFlag = true;
                }
            }
        }

        if (errorFlag) {
            return new ErrorSTO("error in param check");
        } else {
            VarSTO returnSTO = new VarSTO(func.getName(), func.getReturnType(), false, false);
            if ( func.isReturnByReference() ) {
                returnSTO.setIsAddressable(true);
                returnSTO.setIsModifiable(true);
            }

            return returnSTO;
        }
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    STO DoDesignator2_Dot(STO sto, String strID)
    {
        // Good place to do the struct checks
        if(sto.isError()){
            return sto;
        }

        Type type = sto.getType();
        if (!type.isStruct()) {
            String errormsg = Formatter.toString(ErrorMsg.error14t_StructExp, type.getName());
            m_nNumErrors++;
            m_errors.print(errormsg);
            return new ErrorSTO(errormsg);
        }

        // Check for struct fields
        if ( !((StructType) type).hasField(strID) ) {
            if (m_symtab.getStructType() != null) {
                String errormsg = Formatter.toString(ErrorMsg.error14c_StructExpThis, strID);
                m_nNumErrors++;
                m_errors.print(errormsg);
                return new ErrorSTO(errormsg);  
            }
            String errormsg = Formatter.toString(ErrorMsg.error14f_StructExp, strID, type.getName());
            m_nNumErrors++;
            m_errors.print(errormsg);
            return new ErrorSTO(errormsg);
        }

        return ((StructType) type).getField(strID);
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    STO DoDesignator2_Array(STO sto, STO expr)
    {
        if(expr.isError()){
            return expr;
        }
        //possible that sto could be a conststo with type error. this occurs during instantiation of arrray
        if(sto.isError() || sto.getType().isError()){
            return sto;
        }
        // Good place to do the array checks
        Type stoType = sto.getType();
        //default to true since we don't have to check for pointers.
        if(!(stoType.isArray()) && !(stoType.isPointer())){
            m_nNumErrors++;
            m_errors.print(Formatter.toString(ErrorMsg.error11t_ArrExp, stoType.getName()));
            return new ErrorSTO(sto.getName());
        }

        //expression must be of type int
        if(!(expr.getType().isEquivalentTo(new IntType())))
        {
            m_nNumErrors++;
            m_errors.print(Formatter.toString(ErrorMsg.error11i_ArrExp, expr.getType().getName()));
            return new ErrorSTO(sto.getName());
        }

        STO isValid;
        //only do bounds check if stotype is of array. we don't need to do it for pointers
        if(stoType.isArray()){
            ArrayType myArry = (ArrayType) stoType;
            //only do bounds check if we have an array and expr is constant
            if(expr.isConst()){
                isValid = arrayValidityHelper(expr);
                if(!(isValid.isError())){
                    int intval = ((ConstSTO)expr).getIntValue(); 
                    if( intval >= myArry.getCurrentDim() || intval < 0){
                        m_nNumErrors++;
                        m_errors.print(Formatter.toString(ErrorMsg.error11b_ArrExp, 
                                                            ((ConstSTO)expr).getIntValue(), 
                                                            myArry.getCurrentDim()));
                        return new ErrorSTO(myArry.getName());
                    }
                }
            }
            VarSTO myNextArrayVal = ((ArrayType)sto.getType()).getNextLevel();

            return myNextArrayVal;
        }
        if(stoType.isNullPointer()){
            m_nNumErrors++;
            m_errors.print(ErrorMsg.error15_Nullptr);
            return new ErrorSTO(sto.getName());
        }
        return new VarSTO(sto.getName(), ((PointerType) stoType).getNextLevel());

        //at this point, we need to return the proper VarSTO. specifcally, if it's completely
        //dereferenced array, the varsto with basic type, else arraytype 
        // VarSTO myNextArrayVal = ((ArrayType)sto.getType()).getNextLevel();

        // return myNextArrayVal;
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

    //----------------------------------------------------------------
    // performs unary sign. This is phase 0, but never implemented till now
    //----------------------------------------------------------------
    STO doUnarySign(STO a, UnaryOp o) {
        //will not be performed on non-numerics
        if (a.isError()) {
            return a;
        }
        STO result = a;
        if(o.getName().equals("-")){
            if(a.isConst()){
                float signchange = ((ConstSTO)a).getFloatValue() * -1;
                result = new ConstSTO(a.getName(), a.getType(), signchange);
            }
        }
        return result;
    }

    //----------------------------------------------------------------
    // performs unary sign. This is phase 0, but never implemented till now
    //----------------------------------------------------------------
    void setForEachWhileFlag(){
        if(m_foreachwhileflag  == -1){
            m_foreachwhileflag = m_symtab.getLevel();
        }
    }


    // ** Phase 1 check 4 **/
    void doConditionCheck(STO expr) {
        // do we need to do this check here, or can the expr not be an error here?
        if (expr.isError()) {
            return;
        }

        Type exprType = expr.getType();

        if (!exprType.isBoolean()) {
            m_nNumErrors++;
            m_errors.print( Formatter.toString(ErrorMsg.error4_Test, exprType.getName()) );
        }
    }

    /* Phase 1 check 6a */
    void doReturnVoidCheck() {
        // Check if the function failed overload test
        // if (m_symtab.getFunc() == null) {
        //     return;
        // }

        Type returnType = m_symtab.getFunc().getReturnType();
        if (!returnType.isVoid()) {
            m_nNumErrors++;
            m_errors.print(ErrorMsg.error6a_Return_expr);
        }

        // ignore this
        /* Phase 1 check 6c */
        // int curlevel = m_symtab.getLevel();
        // if (curlevel == 2) {
        //     // global level is 1, just inside function is level 2
        //     m_symtab.getFunc().setHasTopReturn(true);
        // }
    }

    /* Phase 1 check 6b */
    void doReturnTypeCheck(STO expr) {
        if (expr.isError()) {
            // m_symtab.setFunc(null);
            return;
        }

        // Check if the function failed overload test
        // if (m_symtab.getFunc() == null) {
        //     System.out.println("bouncing");
        //     return;
        // }

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

        // ignore this
        /* Phase 1 check 6c */
        // int curlevel = m_symtab.getLevel();
        // if (curlevel == 2) {
        //     // global level is 1, just inside function is level 2
        //     m_symtab.getFunc().setHasTopReturn(true);
        // }
    }

    void setFunctionReturn() {
        if (m_symtab.getFunc() == null) {
            return;
        }

        /* Phase 1 check 6c */
        int curlevel = m_symtab.getLevel();
        // if (curlevel == 2) {
        // compare current level, to the current func's inner level (directly inside)
        if (curlevel == m_symtab.getFunc().getInnerLevel()) {   // extension to work at any nesting (maybe)
            
            m_symtab.getFunc().setHasTopReturn(true);
        }
    }

    // ** Phase 1 Check 7** /
    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    void doExitCheck(STO expr) {
        if (expr.isError()) {
            return;
        }
        Type exprType = expr.getType();

        if(!exprType.isAssignableTo(new IntType())) {

            m_nNumErrors++;
            m_errors.print( Formatter.toString(ErrorMsg.error7_Exit, exprType.getName()));
        }
    }

    // Boolean doRecursiveStructCheck(Type type) {

    // }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    Type DoStructType_ID(String strID)
    {
        if (m_symtab.getStructType() != null) {
            // If we are in a struct, check if we are doing a recursive declaration.
            if (m_symtab.getStructType().getName().equals(strID)) {

                // skip the rest of these checks and return.
                return m_symtab.getStructType();
            } 
            // probably need else here, check the global scope for other existing structs
        }

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

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    public STO doArrayDeclCheck(STO expr) {
        if(expr.isError()){
            return expr;
        }
        //do all checks for valid array[expr] in this helper method
        return arrayValidityHelper(expr);
    }

    //----------------------------------------------------------------
    // helper method used by both doArrayDeclCheck and DoDesignator2_Array
    // return -1 means error
    //----------------------------------------------------------------
    public STO arrayValidityHelper(STO expr){
        //return if error
        if (expr.isError()) {
            return expr;
        }

        //not error, so get type
        Type exprType = expr.getType();

        //NOTE: WE ARE RETURNING CONSTSTOs with a type of ERRORTYPE. This way we can tell whether we've
        //printed the error or not. If it's an errorsto, we have no way of knowing what's been printed
        //I think this is what Eduardo was talking about. We don't print the error here,

        //needs to be int, else error
        if (!exprType.isInt()) {
            // m_nNumErrors++;
            String errorToPrint =  Formatter.toString(ErrorMsg.error10i_Array, exprType.getName());
            // m_errors.print(errorToPrint);
            return new ConstSTO(errorToPrint, new ErrorType());
        }
        //needs to be constant else error
        if (!expr.isConst()){
            // m_nNumErrors++;
            String errorToPrint = ErrorMsg.error10c_Array ;
            // m_errors.print(errorToPrint);
            return new ConstSTO(errorToPrint, new ErrorType());
        }
        //known at compile time, so we can grab the value
        int intValue = ((ConstSTO)expr).getIntValue();
        //if value is less than or equal to 0, error
        if(intValue <= 0){
            // m_nNumErrors++;
            String errorToPrint = Formatter.toString(ErrorMsg.error10z_Array, intValue);
            // m_errors.print(errorToPrint);
            return new ConstSTO(errorToPrint, new ErrorType(), intValue);
        }

        return expr;
    }

    //----------------------------------------------------------------
    // Check 16 New/Delete Statement
    //----------------------------------------------------------------

    //----------------------------------------------------------------
    // New Statement checks
    //----------------------------------------------------------------
    public void doNewStatement(STO sto, Vector<STO> params) {
        if (sto.isError()) {
            return;
        }

        if (!sto.isModLValue()) {
            m_nNumErrors++;
            m_errors.print(ErrorMsg.error16_New_var);
            return;
        }

        Type type = sto.getType();
        if (!type.isPointer()) {
            m_nNumErrors++;
            m_errors.print( Formatter.toString(ErrorMsg.error16_New, type.getName()) );
            return;
        } else {
            Type structType = ((PointerType)sto.getType()).getNextLevel();
            if (!structType.isStruct()) {
                return;
            }

            STO structSTO = m_symtab.access(structType.getName());
            STO structCtor = ((StructType)structSTO.getType()).getCtor();
            DoFuncCall(structCtor, params);
        }
    }

    //----------------------------------------------------------------
    // New Ctor Statement checks
    //----------------------------------------------------------------
    public void doNewCtorStatement(STO sto, Vector<STO> params) {
        if (sto.isError()) {
            return;
        }

        Type type = sto.getType();
        if (!type.isPointer()) {
            m_nNumErrors++;
            m_errors.print( Formatter.toString(ErrorMsg.error16b_NonStructCtorCall, type.getName()) );
            return;
        }

        // If it's a pointer, check that it's pointing to a structType
        if ( !((PointerType) type).getNextLevel().isStruct() ) {
            m_nNumErrors++;
            m_errors.print( Formatter.toString(ErrorMsg.error16b_NonStructCtorCall, type.getName()) );
            return;
        } else {
            Type structType = ((PointerType) type).getNextLevel();

            STO structSTO = m_symtab.access(structType.getName());
            STO structCtor = ((StructType)structSTO.getType()).getCtor();
            DoFuncCall(structCtor, params);
        }
    }


    //----------------------------------------------------------------
    // Delete Statement checks
    //----------------------------------------------------------------
    public void doDeleteStatement(STO sto) {
        if (sto.isError()) {
            return;
        }

        if (!sto.isModLValue()) {
            m_nNumErrors++;
            m_errors.print(ErrorMsg.error16_Delete_var);
            return;
        }

        Type type = sto.getType();
        if (!type.isPointer()) {
            m_nNumErrors++;
            m_errors.print( Formatter.toString(ErrorMsg.error16_Delete, type.getName()) );
            return;
        }
    }


    //----------------------------------------------------------------
    // Check 19 Sizeof
    //----------------------------------------------------------------

    //----------------------------------------------------------------
    // Get the size of a variable/constant object.
    //----------------------------------------------------------------
    public STO getObjSize(STO sto) {
        if (sto.isError()) {
            return sto;
        }

        if (!sto.getIsAddressable()) {
            // object is not addressable, throw error
            m_nNumErrors++;
            m_errors.print(ErrorMsg.error19_Sizeof);
            return new ErrorSTO(ErrorMsg.error19_Sizeof);
        }


        // int finalSize;
        Type type = sto.getType();
        if (type.isPointer()) {
            return getTypeSize( ((PointerType) type).getBaseType(), null);
        } else {
            return new ConstSTO("var size", new IntType(), type.getSize(), false, false);
        }



        // else if (type.isArray()) {

        //     return new ConstSTO("var size", new IntType(), type.getSize(), false, false);

        // } else {
        //     return new ConstSTO("var size", new IntType(), type.getSize(), false, false);
        // }

        // Passed checks, calculate and return size.
        // return new ConstSTO("var size", new IntType(), finalSize, false, false);
    }

    //----------------------------------------------------------------
    // Get the size of a type/array type.
    //----------------------------------------------------------------
    public STO getTypeSize(Type type, Vector<STO> arrayList) {
        if (type.isError()) {
            return new ErrorSTO(type.getName());
        }

        if (type.isPointer()) {
            return getTypeSize( ((PointerType)type).getBaseType(), arrayList);
            // return new ConstSTO("type size", new IntType(), size);

            // return getTypeSize( ((PointerType)type).getBaseType(), null);
        }

        if (arrayList != null) {
            int i = 1;
            for (STO sto: arrayList){
                if(sto.getType().isError()){
                    return sto;
                }
                i *= ((ConstSTO)sto).getIntValue();
            }
            i *= type.getSize();
            return new ConstSTO("type size", new IntType(), i, false, false);
            // return i;
        }

        // Passed checks, calculate and return size.
        return new ConstSTO("type size", new IntType(), type.getSize(), false, false);
        // return type.getSize();
    }

    //                              BY: VIVEK
    // method to check proper pointer usage
    //----------------------------------------------------------------
    STO checkPointerValidity(STO sto){
        if(sto.isError()){
            return new ErrorSTO(sto.getName());
        }

        if(sto.getType().isNullPointer()){
            m_nNumErrors++;
            m_errors.print(ErrorMsg.error15_Nullptr);
            return new ErrorSTO(sto.getName());
        }
        else{
            if(!(sto.getType().isPointer())){
                m_nNumErrors++;
                m_errors.print(Formatter.toString(ErrorMsg.error15_Receiver, sto.getType().getName()));
                return new ErrorSTO(sto.getName());
            }

                return new ExprSTO(sto.getName(), 
                                    ((PointerType)sto.getType()).getNextLevel(),
                                    true, 
                                    true);

        }
    }

    //                              BY: VIVEK
    // method to make a pointer
    //----------------------------------------------------------------
    public Type makeAPointer(Type t, Vector<Type> pointers) {
        if(t.isError()){
            return new ErrorType();
        }

        if(pointers == null){
            return t;
        }
        if(pointers.isEmpty()){
            return t;
        }
        else{
            //if it's an error, it's been printed. 
            if(pointers.firstElement().isError()){
                return pointers.firstElement();
            }
            //no errors, so we do the actual POINTER making here.
            PointerType myPointerType = new PointerType(t);
            pointers.remove(0);
            Type recursedType = makeAPointer(t, pointers);
            if(recursedType.isError()){
                return recursedType;
            }
            myPointerType.setNextLevel(recursedType);
            //the new varsto will be an non-mod lval, so addressable, but not modifiable
            return myPointerType;
        }
    }

    //                              BY: VIVEK
    // do Arrow Check Validity
    //----------------------------------------------------------------
    STO checkArrowValidity(STO sto, String t_id){
        if(sto.isError()){
            m_nNumErrors++;
            // m_errors.print(Formatter.toString(ErrorMsg.error15_ReceiverArrow, sto.getType().getName()));
            return new ErrorSTO(sto.getName());
        }

        if(sto.getType().isNullPointer()){
            m_nNumErrors++;
            m_errors.print(ErrorMsg.error15_Nullptr);
            return new ErrorSTO(sto.getName());
        }
        else{
            //if it's an error, it's been printed. 
            Type stoType = sto.getType();
            if(!(stoType.isPointer())){
                m_nNumErrors++;
                m_errors.print(Formatter.toString(ErrorMsg.error15_ReceiverArrow, sto.getType().getName()));
                return new ErrorSTO(sto.getName());
            }
            //now we know it's a pointer, but is it a pointer to a struct
            PointerType stoPointer = (PointerType)stoType;
            if(!(stoPointer.getNextLevel().isStruct())){
                m_nNumErrors++;
                m_errors.print(Formatter.toString(ErrorMsg.error15_ReceiverArrow, sto.getType().getName()));
                return new ErrorSTO(sto.getName());
            }
            //at this point, we are dealing with a pointer to a struct. check if the rhs of the
            //arrow (t_id) is in the struct.
            StructType structType = (StructType)stoPointer.getNextLevel();
            if(!structType.hasField(t_id)){
                m_nNumErrors++;
                m_errors.print(Formatter.toString(ErrorMsg.error14f_StructExp, t_id, 
                                                    ((StructType)stoPointer.getNextLevel()).getName()));
                return new ErrorSTO(sto.getName());
            } else {
            // We have a field, return it for use.
                return structType.getField(t_id);
            }
        }
    }

    STO getAddressOf(STO expr) {
        if(!expr.getIsAddressable()){
            m_nNumErrors++;
                m_errors.print(Formatter.toString(ErrorMsg.error18_AddressOf, expr.getType().getName()));
                return new ErrorSTO(expr.getName());
            
        }
        PointerType addressof = new PointerType(expr.getType());
        addressof.setNextLevel(expr.getType());
        ExprSTO myExpr = new ExprSTO(expr.getName(), addressof);

        return myExpr;
    }

    STO typeCasting(STO sto, Type type){
        if(!(sto.getType().isBasic()) && !(sto.getType().isPointer())){
            m_nNumErrors++;
                m_errors.print(Formatter.toString(ErrorMsg.error20_Cast, sto.getType().getName(), type.getName()));
                return new ErrorSTO(sto.getName());
        }
        if(sto.getType().isNullPointer()){
            m_nNumErrors++;
                m_errors.print(Formatter.toString(ErrorMsg.error20_Cast, sto.getType().getName(), type.getName()));
                return new ErrorSTO(sto.getName());
        }
        if(sto.isConst() && !type.isPointer()){
            BigDecimal myVal = ((ConstSTO) sto).getValue();
            return new ConstSTO(sto.getName(), type, myVal, false, false);
        }
        else{
            return new ExprSTO(sto.getName(), type, false, false);
        }
    }
}




























