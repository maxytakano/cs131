
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

    private AssemblyGenerator assGen;

    private int struct_counter = 0;


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
        assGen = new AssemblyGenerator("vvRC.s");
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    void DoProgramEnd()
    {
        assGen.dispose();
        m_symtab.closeScope();
    }

    // ** Phase 1 check 5 ** //

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    void DoVarDecl(String id, Type type, STO optInit, boolean isStatic)
    {
        // System.out.println("Level: " + id);

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


            // if (!initType.isAssignableTo(type) && !initType.isNullPointer()) {
            if (!initType.isAssignableTo(type)) {
                m_nNumErrors++;
                m_errors.print(Formatter.toString(ErrorMsg.error8_Assign, initType.getName(), type.getName()));
                m_symtab.insert(new VarSTO(id, type));
                return;
            }
        }

        VarSTO sto = new VarSTO(id, type);
        if (sto.getType().isStruct()) {
            // set the struct number for declaration/end of structs
            sto.setStructNumber(++struct_counter);
        }
        m_symtab.insert(sto);

        //----------------------------------------------------------------
        // TO DEAL WITH STATIC, WE NEED TO PASS IT IN FIRST
        //----------------------------------------------------------------
        //PART 2 STUFF HERE!!!
        //GLOBAL: Check to see if it's global (has level of 1)

        constOrVarDeclAssembly(id, type, optInit, isStatic, sto);
    }

    //----------------------------------------------------------------
    // Helper method to extract the value from optInit
    //----------------------------------------------------------------
    String optInitExtractor(STO optInit){
        String returnedValue = "";

        if(optInit != null){
            BigDecimal dec = null;
            //check to see if there was possible const folding so that values can be initialized
            //in the assembly itself
            if(optInit.isVar()){
                dec = ((VarSTO)optInit).getValue();
            }
            else if(optInit.isConst()){
                dec = ((ConstSTO)optInit).getValue();
            }

            //we are passing in the int value from the bigdecimal
            if(optInit.getType().getName().equals("int") || optInit.getType().getName().equals("bool") )
            {
                if(dec != null){
                    returnedValue += dec.intValue();
                }
            }
            //we are passing in the float value from the bigdecimal
            else if(optInit.getType().getName().equals("float"))
            {
                if(dec != null){
                    returnedValue += dec.floatValue();
                }
            }
        }
        return returnedValue;
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
        //never gonna be static var
        DoVarDecl(lhs_id, lhs_type, myRHS_Array, false);
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    void DoVarDecl_4Params(String id, Type type, Vector<STO> arraySizes, STO optInit, boolean isStatic)
    {
        //rather than adding on more params to doVarDecl, I'm just gonna do this here
        //for more modularity
        //If arraySizes is null, we just want to call the default vardecl
        if(arraySizes == null){
            //optInit not null, so call the default doVarDecl.
            DoVarDecl(id, type, optInit, isStatic);
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

        //write in the assembly for the array

        //value can be either "", or an actual value. if "" it's handled differently in the
        //assgen
        String optInitVal = optInitExtractor(optInit);

        if(m_symtab.getLevel() == 1){
            //we have a global here
            assGen.writeGlobalOrStaticVar(id, type, optInitVal, isStatic);
        }
        if (m_symtab.getFunc() != null)
        {
            //in here we are part of a method or a struct
            //if static, we call the globalorstaticvar writing method
            if(isStatic){
                String localStaticID = m_symtab.getFunc().getMangledName() + "." + id;
                assGen.writeGlobalOrStaticVar(localStaticID, type, optInitVal, isStatic);
                //set the offset to the label of the variable since it's a static variable
                sto.setOffset(localStaticID);
            }
            else{
                //we have a local. Increment function's offset by local's size. the set sto's offset
                m_symtab.getFunc().incOffsetCount(sto.getType().getSize());
                sto.setOffset(("-" + m_symtab.getFunc().getOffsetCount() + ""));
            }
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
    void DoConstDecl(String id, Type type, STO constExpr, boolean isStatic)
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

        constOrVarDeclAssembly(id, type, constExpr, isStatic, sto);

        m_symtab.insert(sto);
    }

    //----------------------------------------------------------------
    // helper method to write assembly for a var or const declaration
    //----------------------------------------------------------------
    void constOrVarDeclAssembly(String id, Type type, STO optInit, Boolean isStatic, STO sto){
        //PART 2 STUFF HERE!!!
        //GLOBAL: Check to see if it's global (has level of 1)

        //value can be either "", or an actual value. if "" it's handled differently in the
        //assgen
        String optInitVal = optInitExtractor(optInit);
        if(m_symtab.getLevel() == 1){
            //we have a global here
            assGen.writeGlobalOrStaticVar(id, type, optInitVal, isStatic);
            // writeGlobalOrStaticVar(id, type, optInit, isStatic);
            //set the offset to the label of the global
            sto.setOffset(id);
        }
        if (m_symtab.getFunc() != null)
        {
            //in here we are part of a method or a struct
            //if static, we call the globalorstaticvar writing method
            if(isStatic){
                String localStaticID = m_symtab.getFunc().getMangledName() + "." + id;
                assGen.writeGlobalOrStaticVar(localStaticID, type, optInitVal, isStatic);
                // writeGlobalOrStaticVar(localStaticID, type, optInit, isStatic);
                //set the offset to the label of the variable since it's a static variable
                sto.setOffset(localStaticID);
            }
            else{
                if (type.isStruct()) {
                    // size already set in ctor call for structs

                } else {
                    //we have a local. Increment function's offset by local's size. the set sto's offset
                    m_symtab.getFunc().incOffsetCount(type.getSize());
                    sto.setOffset(("-" + m_symtab.getFunc().getOffsetCount() + ""));
                }

                //inefficient but I want to keep the part 1 and part 2 stuff completely separate
                if(optInit != null){
                    //we are initializing the variable some value
                    if (optInit.isError()) {
                        //possibly don't have to deal with this since we're only receiving correct code
                    }
                    else{
                        // assGen.writeLocalInit(id, sto.getOffset(), optInitVal, type);
                        if(!optInitVal.equals("")){
                            //we have an actual value to put into the var
                            assGen.writeLocalInit(id, sto.getOffset(), optInitVal, type);
                        }
                        else{
                            //we don't have an actual value, we have an expression
                            // assGen.writeLocalAssign(id, sto.getOffset(), optInit.getName(), optInit.getOffset());
                            assGen.writeLocalAssign(id, sto, optInit);
                        }
                    } 
                } else {

                    // m_symtab.getFunc().incOffsetCount(type.getSize());
                    // sto.setOffset( "-" + m_symtab.getFunc().getOffsetCount() );
                    assGen.writeStructInit("-" + m_symtab.getFunc().getOffsetCount(), ((VarSTO) sto).getStructNumber() + "");
                }
            }
        }
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

        m_symtab.getStructType().incOffsetCount(type.getSize());
        sto.setOffset(m_symtab.getStructType().getOffsetCount() + "");

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

    Boolean checkForDtor(String id) {
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
        FuncSTO sto = new FuncSTO(id + "." + id, new VoidType(), false);
        m_symtab.insert(sto);

        assGen.writeMethodStart(sto, false, true);
        assGen.writeMethodEnd(sto.getMangledName(), sto.getOffsetCount() + "", null);
    }

    //----------------------------------------------------------------
    // Creates a default ctor for the current struct
    //----------------------------------------------------------------
    void createDefaultDtor(String id) {
        FuncSTO sto = new FuncSTO(id + ".~" + id, new VoidType(), false);
        m_symtab.insert(sto);

        assGen.writeMethodStart(sto, false, true);
        assGen.writeMethodEnd(sto.getMangledName(), sto.getOffsetCount() + "", null);
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
                // P2TODO: might need to pass something other than null here
                DoFuncCall(structCtor, params, structType, null);
                return;
            }
        }

        STO structSTO = m_symtab.access(structType.getName());
        structCtor = ((StructType)structSTO.getType()).getCtor();
        // STO structCtor = m_symtab.getStructType().getCtor();
        DoFuncCall(structCtor, params, structType, structSTO);
    }

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
                FuncSTO sto = new FuncSTO(structType.getName() + "." + id, returnType, returnByReference);
                m_symtab.setFunc(sto);
                m_symtab.insert(sto);
                m_symtab.openScope();
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

        if (structType != null) {
            id = structType.getName() + "." + id;
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
        setParamOffsets(params);
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
                insertParams(candidateFunc.getParameters());
                m_symtab.getFunc().getOverloadMatch(params).setInnerLevel(m_symtab.getLevel());
            }
        } else {
            // 3. If there is no existing function, insert a new entry to symtab.
            m_symtab.insert(candidateFunc);
            // TODO: check if open scope here is correct.
            m_symtab.openScope();
            insertParams(candidateFunc.getParameters());
            m_symtab.getFunc().setInnerLevel(m_symtab.getLevel());
        }

        //4. added in the code for the assembly. Call the function declaration method here to 
        //   write the assembly for a function declaration
        // if(existingFunc == null){
        //     assGen.writeMethodStart(candidateFunc.getName(), candidateFunc.getMangledName(), 
        //                             candidateFunc.getParameters());
        // }
        // else{
        //     assGen.writeMethodStart("", candidateFunc.getMangledName(), candidateFunc.getParameters());
        // }

        boolean overload_flag = (existingFunc != null);
        boolean struct_flag = (m_symtab.getStructType() != null);
        assGen.writeMethodStart(candidateFunc, overload_flag, struct_flag);
    }

    void setParamOffsets(Vector<STO> params) {
        int offset = 68;

        if (params == null) {
            return;
        }

        STO cur_param;
        for (int i = 0; i < params.size(); i++) {
            cur_param = params.get(i);
            cur_param.setOffset( (offset + (i * 4)) + "");
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

        // need the Struct type, the struct's dtor, and the corresponding ctor/dtor#
        Scope curScope = m_symtab.getScope();
        Vector<STO> locals = curScope.getLocals();
        Vector<STO> structs_found = new Vector<STO>();
        STO cur_STO;
        for (int i = 0; i < locals.size(); i++) {
            cur_STO = locals.get(i);
            if (cur_STO.getType().isStruct()) {
                structs_found.addElement(cur_STO);
            }
        }

        assGen.writeMethodEnd(curFunc.getMangledName(), curFunc.getOffsetCount() + "", structs_found);
        m_symtab.closeScope();
        m_symtab.setFunc(null);
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
    // closes the scope
    //----------------------------------------------------------------
    void DoBlockClose()
    {

        m_symtab.closeScope();
        if(m_symtab.getLevel() <= m_foreachwhileflag){
            m_foreachwhileflag = -1;
        }

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

            //set the offset for the newVar STO. It is the same offset as the stoDes
            newVar.setOffset(stoDes.getOffset());

            //value can be either "", or an actual value. if "" it's handled differently in the
            //assgen
            String initVal = optInitExtractor(stoExpr);
            //we must have a local since assignment. 
            //write the assembly for the expr.
            if(!initVal.equals("")){
                //we have an actual value to put into the var
                assGen.writeLocalInit(stoDes.getName(), stoDes.getOffset(), initVal, stoDes.getType());
            }
            else{
                //we don't have an actual value, we have an expression
                // assGen.writeLocalAssign(stoDes.getName(), stoDes.getOffset(), stoExpr.getName(), stoExpr.getOffset());
                assGen.writeLocalAssign(stoDes.getName(), stoDes, stoExpr);
            }

            return newVar;
        }
    }

    //----------------------------------------------------------------
    //
    //----------------------------------------------------------------
    STO DoFuncCall(STO sto, Vector<STO> args, Type structType, STO struct_STO)
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

        // incrementing 
        m_symtab.getFunc().incOffsetCount(sto.getType().getSize());
        sto.setOffset(("-" + m_symtab.getFunc().getOffsetCount() + ""));

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

                returnSTO.setOffset(("-" + m_symtab.getFunc().getOffsetCount() + ""));

                // p2 writing
                if (struct_STO != null) {
                    m_symtab.getFunc().incOffsetCount(structType.getSize());
                    matchingFunc.setOffset( "-" + m_symtab.getFunc().getOffsetCount() );
                }
                assGen.writeFunctionCall(matchingFunc, args, matchingFunc.getParameters(), structType);

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

            // p2 generate assembly for regular function call
            if (struct_STO != null) {
                m_symtab.getFunc().incOffsetCount(structType.getSize());
                sto.setOffset( "-" + m_symtab.getFunc().getOffsetCount() );
            }
            assGen.writeFunctionCall(sto, args, params, structType);

            // Set up for using the return value
            returnSTO.setOffset(("-" + m_symtab.getFunc().getOffsetCount() + ""));
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

        if (m_symtab.getStructType() != null) {
            // if we are in a struct, only check local/global scope. 
            if ((sto = m_symtab.accessLocal(strID)) != null) {
                return sto;
            } else if ((sto = m_symtab.accessGlobal(strID)) != null) {
                return sto;
            } else {
                m_nNumErrors++;
                m_errors.print(Formatter.toString(ErrorMsg.undeclared_id, strID));
                sto = new ErrorSTO(strID);
                return sto;
            }
        }

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

        //THE FOLLOWING SECTION is for assembly writing.
        //if we have globals, we want their offsets, not vals so don't make a check
        //if the offset == their name.
        String lhsVal = "";
        String lhsValType = a.getType().getName();
        if(!a.getName().equals(a.getOffset())){
            lhsVal = optInitExtractor(a);
        }

        String rhsVal = "";
        String rhsValType = b.getType().getName();
        if(!b.getName().equals(b.getOffset())){
            rhsVal = optInitExtractor(b);
        }

        if(lhsVal.equals("") && rhsVal.equals("")){
            //Increment function's offset by local's size. the set sto's offset
            m_symtab.getFunc().incOffsetCount(result.getType().getSize());
            result.setOffset(("-" + m_symtab.getFunc().getOffsetCount() + ""));
            if(!lhsValType.equals(rhsValType)){
                //we need type promotion, so extra offset required.
                m_symtab.getFunc().incOffsetCount(result.getType().getSize());
            }
            //there was no constants, we're dealing wiht only local vars/exprs
            // System.out.println(o.getName());
            assGen.exprArith(a, b, result, o.getName(), "-" + m_symtab.getFunc().getOffsetCount());
        }
        else if(lhsVal.equals("") && !rhsVal.equals("")){
            //Increment function's offset by local's size. the set sto's offset
            m_symtab.getFunc().incOffsetCount(result.getType().getSize());
            result.setOffset(("-" + m_symtab.getFunc().getOffsetCount() + ""));
            if(!lhsValType.equals(rhsValType)){
                //we need type promotion, so extra offset required.
                m_symtab.getFunc().incOffsetCount(result.getType().getSize());
            }
            //rhs is a constant, we have to deal with it differently
            assGen.constArith(a, rhsVal, rhsValType, result, o.getName(), true, "-" + m_symtab.getFunc().getOffsetCount());
        }
        else if(!lhsVal.equals("") && rhsVal.equals("")){
            //Increment function's offset by local's size. the set sto's offset
            m_symtab.getFunc().incOffsetCount(result.getType().getSize());
            result.setOffset(("-" + m_symtab.getFunc().getOffsetCount() + ""));
            if(!lhsValType.equals(rhsValType)){
                //we need type promotion, so extra offset required.
                m_symtab.getFunc().incOffsetCount(result.getType().getSize());
            }
            //lhs is constant, rhs is not, deal with it same as else if
            assGen.constArith(b, lhsVal, lhsValType, result, o.getName(), false, "-" + m_symtab.getFunc().getOffsetCount());
        }
        else if(!lhsVal.equals("") && !rhsVal.equals("") && o.getName().equals(">")){
            String resultVal = optInitExtractor(result);
            assGen.constComparisonAssembly(lhsVal, rhsVal, resultVal, result, o);
        }
        //at this point, 
        //neither a nor b are exprs. they both have vals, but no offset
        //const folding takes place, so do nothing

        return result;
    }

    // ** Phase 1 check 2 **/

    //----------------------------------------------------------------
    // Checks if we have a valid unary operation.
    //----------------------------------------------------------------
    STO doUnaryExpr(STO a, UnaryOp o, boolean isPre) {
        if (a.isError()) {
            return a;
        }

        STO result = o.checkOperand(a);
        if(result.isError()){
            m_nNumErrors++;
            m_errors.print(result.getName());
            return result;
        }

        if(m_symtab.getFunc() != null){
            m_symtab.getFunc().incOffsetCount(result.getType().getSize());
            result.setOffset(("-" + m_symtab.getFunc().getOffsetCount() + ""));
            // System.out.println(o.getName());
            //true = postincrement
            //false = preincrement
            assGen.exprUnaryOp(a, result, o.getName(), isPre);
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
        STO result;
        if(a.isConst()){
            result = new ConstSTO(a.getName(), a.getType(), ((ConstSTO)a).getFloatValue());
        }
        else{
            result = new VarSTO(a.getName(), a.getType());
        }
        if(o.getName().equals("-")){
            if(a.isConst()){
                float signchange = ((ConstSTO)a).getFloatValue() * -1;
                result = new ConstSTO(a.getName(), a.getType(), signchange);
            }
        }

        //only do this if a isn't a const. Otherwise the case is already handled.
        if(m_symtab.getFunc() != null){
            String unaryVal = optInitExtractor(a);
            if(unaryVal.equals("")){
                m_symtab.getFunc().incOffsetCount(result.getType().getSize());
                result.setOffset(("-" + m_symtab.getFunc().getOffsetCount() + ""));
                //there was no constants, we're dealing wiht only local vars/exprs
                // System.out.println(o.getName());
                assGen.exprUnarySign(a, result, o.getName());
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

        Type returnType = m_symtab.getFunc().getReturnType();
        if (!returnType.isVoid()) {
            m_nNumErrors++;
            m_errors.print(ErrorMsg.error6a_Return_expr);
        }
    }

    /* Phase 1 check 6b */
    void doReturnTypeCheck(STO expr) {
        if (expr.isError()) {
            // m_symtab.setFunc(null);
            return;
        }

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

    // takes in an expr or a null expr for void returns
    void parseReturnStmt(STO cur_STO) {
        FuncSTO curFunc = m_symtab.getFunc();
        String value = optInitExtractor(cur_STO);
        String mangled_name = curFunc.getMangledName();

        if (cur_STO.getType().isNullPointer()) {
            // void return case
            assGen.writeVoidFuncReturn(mangled_name);
            return;
        }

        assGen.writeFuncReturn(cur_STO, mangled_name, value);
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

        String val = "";
        if(!expr.getName().equals(expr.getOffset())){
            val = optInitExtractor(expr);
        }
        assGen.writeExit(expr, val);
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

    public Boolean doStructCtorArrayCheck(Vector<STO> arrayList) {
        if (arrayList == null) {
            return true;
        }

        for (STO curSTO : arrayList){
            if(curSTO.getType().isError()){
                m_nNumErrors++;
                m_errors.print(curSTO.getName());
                return false;
            }
        }

        return true;
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
            DoFuncCall(structCtor, params, structType, structSTO);
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
            DoFuncCall(structCtor, params, structType, structSTO);
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
        if (expr.isError()) {
            return expr;
        }

        if(!expr.getIsAddressable()){
            m_nNumErrors++;
                m_errors.print(Formatter.toString(ErrorMsg.error18_AddressOf, expr.getType().getName()));
                return new ErrorSTO(expr.getName());
        }
        PointerType addressof;
        if(!expr.getType().isPointer()){
            addressof = new PointerType(expr.getType());
        }
        else{
            addressof = new PointerType( ((PointerType) expr.getType()).getBaseType() );
        }
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

    void parseWriteStmt(Vector<STO> writeList) {
        STO cur_STO;
        for (int i = 0; i < writeList.size(); i++) {
            cur_STO = writeList.get(i);
            if ( cur_STO.getType() == null ) {
                // If the type is null write an endl.
                assGen.writeEndl();
            } else if (cur_STO.getType().isString()) {
                // Special case for strings.
                assGen.writePrintString(cur_STO.getName());
            } else {
                // This case handles ints, bools, and floats.
                assGen.writeCoutCall(cur_STO, optInitExtractor(cur_STO));
            }
        }
    }

    void parseCINStmt(STO cur_STO) {
        assGen.writeCINCall(cur_STO);
    }

    //----------------------------------------------------------------
    // do assembly for the end of the scope of an if statement
    //----------------------------------------------------------------
    void doIfScopeEnd(){
        assGen.ifScopeEnd();
    }

    //----------------------------------------------------------------
    // do assembly for the end of an if statement
    //----------------------------------------------------------------
    void doIfElseEnd(){
        assGen.ifElseEnd();
    }

    //----------------------------------------------------------------
    // do assembly for the lhs short circuit
    //----------------------------------------------------------------
    void doLeftShortCircuit(STO expr, String op){
        // System.out.println("lhs short circuts");
        // assGen.doLHSShortCircuit(expr, op);
    }

    void ifPush(){
        assGen.ifLabelPush();
    }

    void whilePush(){
         assGen.whileLabelPush();
    }

    void whileEnd(){
        assGen.whileEnd();
    }

    void doBreakAss(){
        assGen.doBreakAss();
    }

    void doConAss(){
        assGen.doConAss();
    }

} /* end of file */


















