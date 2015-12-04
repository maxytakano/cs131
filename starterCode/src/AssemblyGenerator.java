import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;
import java.util.Stack;

public class AssemblyGenerator {
    // 1
    private int indent_level = 0;
    private int fpOffset = 68;

    // a counter for float labels. used for local floats
    private int floatCounter = 0;
    private int stringCounter = 0;
    private int cmpCounter = 0;
    private int ifCounter = 0;
    private int elseCounter = 0;
    private Stack ifLabelStack = new Stack();
    private Stack elseLabelStack = new Stack();

    // 3
    private FileWriter fileWriter;

    public AssemblyGenerator(String fileToWrite) {
        try {
            fileWriter = new FileWriter(fileToWrite);

            // 7
            writeAssembly(AssemblyMsg.FILE_HEADER, (new Date()).toString());

            //add in the rodata area
            //increase indent after the file header
            increaseIndent();
            writeAssembly(AssemblyMsg.RODATA);
            writeAssembly(AssemblyMsg.ALIGN_4);
            //then decrease the indent after
            decreaseIndent();

            //write out all the local vars at the top
            if(AssemblyMsg.START_VAR_NAME_ARRAY.length != AssemblyMsg.START_VAR_ASCIZ_ARRAY.length){
                System.out.println("What the hell did you do? START_VAR_ASCIZ_ARRAY and START_VAR_NAME_ARRAY should be" +
                                    " the same size!");
                //close the file to prevent any unintended bugs
                dispose();
                System.exit(1);
            }
            else{
                for(int i = 0; i < AssemblyMsg.START_VAR_NAME_ARRAY.length; i++){
                    writeAssembly(AssemblyMsg.STARTER_VAR_NAMES, AssemblyMsg.START_VAR_NAME_ARRAY[i]);
                    increaseIndent();
                    writeAssembly(AssemblyMsg.ASCIZ, AssemblyMsg.START_VAR_ASCIZ_ARRAY[i]);
                    decreaseIndent();
                }
            }
            writeAssembly(AssemblyMsg.NEWLINE);
            increaseIndent();
            writeAssembly(AssemblyMsg.TEXT);
            writeAssembly(AssemblyMsg.ALIGN_4);
            decreaseIndent();
            writeAssembly(AssemblyMsg.STARTER_VAR_NAMES, "printBool");
            increaseIndent();
            writeAssembly(AssemblyMsg.SAVE, "%sp", "-96", "%sp");
            writeAssembly((AssemblyMsg.SET_OP + AssemblyMsg.SEPARATOR));
            writeAssembly(AssemblyMsg.TWO_VALS, ".$$.strTF", "%o0");
            writeAssembly((AssemblyMsg.CMP_OP + AssemblyMsg.SEPARATOR));
            writeAssembly(AssemblyMsg.TWO_VALS, "%g0", "%i0");
            writeAssembly((AssemblyMsg.BE_OP + AssemblyMsg.SEPARATOR));
            writeAssembly(AssemblyMsg.ONE_VAL, ".$$.printBool2");
            writeAssembly(AssemblyMsg.NOP);
            writeAssembly((AssemblyMsg.ADD_OP + AssemblyMsg.SEPARATOR));
            writeAssembly(AssemblyMsg.THREE_VALS, "%o0", "8", "%o0");
            decreaseIndent();
            writeAssembly(AssemblyMsg.STARTER_VAR_NAMES, "printBool2");
            increaseIndent();
            writeAssembly(AssemblyMsg.FUNC_CALL, "printf");
            writeAssembly(AssemblyMsg.NOP);
            writeAssembly(AssemblyMsg.RET);
            writeAssembly(AssemblyMsg.RESTORE);
            writeAssembly(AssemblyMsg.NEWLINE);
            decreaseIndent();
            writeAssembly(AssemblyMsg.STARTER_VAR_NAMES, "arrCheck");
            increaseIndent();
            writeAssembly(AssemblyMsg.SAVE, "%sp", "-96", "%sp");
            writeAssembly((AssemblyMsg.CMP_OP + AssemblyMsg.SEPARATOR));
            writeAssembly(AssemblyMsg.TWO_VALS, "%i0", "%g0");
            writeAssembly((AssemblyMsg.BL_OP + AssemblyMsg.SEPARATOR));
            writeAssembly(AssemblyMsg.ONE_VAL, ".$$.arrCheck2");
            writeAssembly(AssemblyMsg.NOP);
            writeAssembly((AssemblyMsg.CMP_OP + AssemblyMsg.SEPARATOR));
            writeAssembly(AssemblyMsg.TWO_VALS, "%i0", "%i1");
            writeAssembly((AssemblyMsg.BGE_OP + AssemblyMsg.SEPARATOR));
            writeAssembly(AssemblyMsg.ONE_VAL, ".$$.arrCheck2");
            writeAssembly(AssemblyMsg.NOP);
            writeAssembly(AssemblyMsg.RET);
            writeAssembly(AssemblyMsg.RESTORE);
            decreaseIndent();
            writeAssembly(AssemblyMsg.STARTER_VAR_NAMES, "arrCheck2");
            increaseIndent();
            writeAssembly((AssemblyMsg.SET_OP + AssemblyMsg.SEPARATOR));
            writeAssembly(AssemblyMsg.TWO_VALS, ".$$.strArrBound", "%o0");
            writeAssembly((AssemblyMsg.MOV_OP + AssemblyMsg.SEPARATOR));
            writeAssembly(AssemblyMsg.TWO_VALS, "%i0", "%o1");
            writeAssembly(AssemblyMsg.FUNC_CALL, "printf");
            writeAssembly((AssemblyMsg.MOV_OP + AssemblyMsg.SEPARATOR));
            writeAssembly(AssemblyMsg.TWO_VALS, "%i1", "%o2");
            writeAssembly(AssemblyMsg.FUNC_CALL, "exit");
            writeAssembly((AssemblyMsg.MOV_OP + AssemblyMsg.SEPARATOR));
            writeAssembly(AssemblyMsg.TWO_VALS, "1", "%o0");
            writeAssembly(AssemblyMsg.RET);
            writeAssembly(AssemblyMsg.RESTORE);
            writeAssembly(AssemblyMsg.NEWLINE);
            decreaseIndent();
            writeAssembly(AssemblyMsg.STARTER_VAR_NAMES, "ptrCheck");
            increaseIndent();
            writeAssembly(AssemblyMsg.SAVE, "%sp", "-96", "%sp");
            writeAssembly((AssemblyMsg.CMP_OP + AssemblyMsg.SEPARATOR));
            writeAssembly(AssemblyMsg.TWO_VALS, "%i0", "%g0");
            writeAssembly((AssemblyMsg.BNE_OP + AssemblyMsg.SEPARATOR));
            writeAssembly(AssemblyMsg.ONE_VAL, ".$$.ptrCheck2");
            writeAssembly(AssemblyMsg.NOP);
            writeAssembly((AssemblyMsg.SET_OP + AssemblyMsg.SEPARATOR));
            writeAssembly(AssemblyMsg.TWO_VALS, ".$$.strNullPtr", "%o0");
            writeAssembly(AssemblyMsg.FUNC_CALL, "printf");
            writeAssembly(AssemblyMsg.NOP);
            writeAssembly(AssemblyMsg.FUNC_CALL, "exit");
            writeAssembly((AssemblyMsg.MOV_OP + AssemblyMsg.SEPARATOR));
            writeAssembly(AssemblyMsg.TWO_VALS, "1", "%o0");
            decreaseIndent();
            writeAssembly(AssemblyMsg.STARTER_VAR_NAMES, "ptrCheck2");
            increaseIndent();
            writeAssembly(AssemblyMsg.RET);
            writeAssembly(AssemblyMsg.RESTORE);
            writeAssembly(AssemblyMsg.NEWLINE);
            writeAssembly(AssemblyMsg.NEWLINE);

            decreaseIndent();
        } catch (IOException e) {
            System.err.printf(AssemblyMsg.ERROR_IO_CONSTRUCT, fileToWrite);
            e.printStackTrace();
            System.exit(1);
        }
    }


    // 8
    public void decreaseIndent() {
        indent_level--;
    }

    public void dispose() {
        try {
            fileWriter.close();
        } catch (IOException e) {
            System.err.println(AssemblyMsg.ERROR_IO_CLOSE);
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void increaseIndent() {
        indent_level++;
    }

    //-------------------------------------------------------------------
    // Method that writes out the assembly for any global or static vars
    //-------------------------------------------------------------------
    public void writeGlobalOrStaticVar(String id, Type type, String val, boolean isStatic){
        increaseIndent();
        boolean noVal = val.equals("");

        //if there's no value, go into bss, otherwise data
        if(noVal){
            writeAssembly(AssemblyMsg.BSS);
        }
        else{
            writeAssembly(AssemblyMsg.DATA);
        }
        writeAssembly(AssemblyMsg.ALIGN_4);

        //don't do the global key word if it's static
        if(!isStatic){
            writeAssembly(AssemblyMsg.DOT_GLOBAL, id);
        }
        decreaseIndent();
        writeAssembly(AssemblyMsg.LABEL, id);
        increaseIndent();

        //if noVAl is true, there's no value to initialize
        if(noVal){
            writeAssembly(AssemblyMsg.SKIP, type.getSize() + "");
        }
        else{
            switch(type.getName()){
                case "int":
                case "bool":
                    writeAssembly(AssemblyMsg.DOT_WORD, val);
                    break;
                case "float":
                    if(!val.contains(".")){
                        val += ".0";
                    }
                    writeAssembly(AssemblyMsg.DOT_SINGLE, val);
                    break;
                default:
                    System.out.println("more needs to be added.");
            }
        }
        writeAssembly(AssemblyMsg.TEXT);
        writeAssembly(AssemblyMsg.ALIGN_4);
        decreaseIndent();
    }


    //-------------------------------------------------------------------
    // Method that writes out the assembly for method starts
    //      .global     main
    //  main:
    //  main.int.float:
    //      set         SAVE.main.int.float, %g1
    //      save        %sp, %g1, %sp
    //
    //      (writeparameters)
    //
    //-------------------------------------------------------------------
    public void writeMethodStart(String funcName, String mangledName, Vector<STO> params){
        boolean declared = funcName.equals("");
        if(!declared){
            writeAssembly(AssemblyMsg.NEWLINE);
            increaseIndent();
            writeAssembly(AssemblyMsg.DOT_GLOBAL, funcName);
            decreaseIndent();
            writeAssembly(AssemblyMsg.LABEL, funcName);
        }
        writeAssembly(AssemblyMsg.LABEL, mangledName);
        increaseIndent();
        writeAssembly((AssemblyMsg.SET_OP + AssemblyMsg.SEPARATOR));
        writeAssembly(AssemblyMsg.TWO_VALS, "SAVE." + mangledName, "%g1");
        writeAssembly(AssemblyMsg.SAVE, "%sp", "%g1", "%sp");
        writeAssembly(AssemblyMsg.NEWLINE);
        writeParameters(params);
        writeAssembly(AssemblyMsg.NEWLINE);
    }

    //-------------------------------------------------------------------
    // Method that writes out the assembly for method ends
    //      ! End of function main.void
    //      call        main.void.fini
    //      nop     
    //      ret     
    //      restore 
    //      SAVE.main.void = -(92 + 28) & -8
    //      
    //  main.void.fini:
    //      save        %sp, -96, %sp
    //      ret     
    //      restore 
    //-------------------------------------------------------------------
    public void writeMethodEnd(String mangledName, String localOffset){

        //This section is for the ret, restore stuff
        writeAssembly(AssemblyMsg.FUNC_END, mangledName);
        functionReturn(mangledName);
        String finiName = mangledName + ".fini";
        writeAssembly(AssemblyMsg.FUNC_SAVE, "SAVE." + mangledName, localOffset);
        writeAssembly(AssemblyMsg.NEWLINE);

        decreaseIndent();
        //Here's the section with all the fini messages
        writeAssembly(AssemblyMsg.LABEL, finiName);
        increaseIndent();

        writeAssembly(AssemblyMsg.SAVE, "%sp", "-96", "%sp");
        writeAssembly(AssemblyMsg.RET);
        writeAssembly(AssemblyMsg.RESTORE);
        decreaseIndent();
    }


    //-------------------------------------------------------------------
    // Helper for return section of functions
    //-------------------------------------------------------------------
    public void functionReturn(String mangledName) {
        String finiName = mangledName + ".fini";
        writeAssembly(AssemblyMsg.FUNC_CALL, finiName);
        writeAssembly(AssemblyMsg.NOP);
        writeAssembly(AssemblyMsg.RET);
        writeAssembly(AssemblyMsg.RESTORE);
    }

    //-------------------------------------------------------------------
    // Void return writer
    //
    // void ret
    // ! return;
    // call        yo.void.fini
    // nop
    // ret
    // restore
    //-------------------------------------------------------------------
    public void writeVoidFuncReturn(String mangled_name) {
        increaseIndent();

        writeAssembly(AssemblyMsg.VOID_RETURN_COMMENT);
        functionReturn(mangled_name);
        writeAssembly(AssemblyMsg.NEWLINE);

        decreaseIndent();
    }

    public void writeFuncReturn(STO cur_STO, String mangled_name, String value_string) {
        increaseIndent();

        String register_string = "";

        // 1. Determine type specific strings based on STO type.
        if (cur_STO.getType().isInt() || cur_STO.getType().isBoolean()) {
            register_string = "%i0";
        } else if (cur_STO.getType().isFloat()) {
            register_string = "%f0";
        }

        writeAssembly(AssemblyMsg.RETURN_COMMENT, cur_STO.getName());
        if (cur_STO.isExpr()) {
            // if it's an expr always write the offset
            writeLoadBlock(cur_STO, register_string);
        } else {
            if (cur_STO.isConst()) {
                if (cur_STO.getType().isFloat()) {
                    // check for floats to see if we need to print rodata
                    writeAssembly(AssemblyMsg.NEWLINE);
                    writeFloatROData(value_string, register_string);
                } else {
                    writeAssembly(AssemblyMsg.SET_OP);
                    writeAssembly(AssemblyMsg.TWO_VALS, value_string, register_string);
                }
            } else {
                writeLoadBlock(cur_STO, register_string);
            }
        }

        // Ending of all return statements
        functionReturn(mangled_name);
        writeAssembly(AssemblyMsg.NEWLINE);

        decreaseIndent();
    }

    //-------------------------------------------------------------------
    // Function calls
    //
    // ! yo(...)
    // call        yo.void
    // nop
    // set         -8, %o1
    // add         %fp, %o1, %o1
    // st          %o0, [%o1]
    //-------------------------------------------------------------------
    public void writeFunctionCall(STO caller, Vector<STO> args, Vector<STO> params) {
        increaseIndent();

        String func_name = caller.getName();
        String offset = caller.getOffset();
        String mangled_name = ((FuncSTO) caller).getMangledName();
        String register_string;

        if (caller.getType().isFloat()) {
            register_string = "%f0";
        } else {
            register_string = "%o0";
        }

        writeAssembly(AssemblyMsg.FUNC_COMMENT, func_name);

        writeFuncCallArgs(args, params);

        writeAssembly(AssemblyMsg.FUNC_CALL, mangled_name);
        writeAssembly(AssemblyMsg.NOP);

        if (!caller.getType().isVoid()) {
            writeStore(offset, "%fp", register_string);
            writeAssembly(AssemblyMsg.NEWLINE);
        }

        decreaseIndent();
    }

    // Function to create args for function calls
    public void writeFuncCallArgs(Vector<STO> args, Vector<STO> params) {
        if (args == null) {
            return;
        }

        // set         -4, %o1
        // add         %fp, %o1, %o1

        for (int i = 0; i < args.size(); i++) {
            STO arg_STO = args.get(i);
            STO param_STO = params.get(i);

            String register_string = "";

            // 1. Determine type specific strings based on STO type.
            if (arg_STO.getType().isInt() || arg_STO.getType().isBoolean()) {
                register_string = "%o" + i;
            } else if (arg_STO.getType().isFloat()) {
                register_string = "%f" + i;
            }

            writeAssembly(AssemblyMsg.ARG_COMMENT, param_STO.getName(), arg_STO.getName());
            if (arg_STO.isExpr()) {
                // if it's an expr always write the offset
                writeLoadBlock(arg_STO, register_string);
            } else {
                if (arg_STO.isConst()) {
                    if (arg_STO.getType().isFloat()) {
                        // check for floats to see if we need to print rodata
                        writeAssembly(AssemblyMsg.NEWLINE);
                        writeFloatROData( ((ConstSTO)arg_STO).getFloatValue() + "", register_string );
                    } else {
                        writeAssembly(AssemblyMsg.SET_OP);
                        writeAssembly(AssemblyMsg.TWO_VALS, ((ConstSTO)arg_STO).getIntValue() + "", register_string);
                    }
                } else {

                    if ( ((VarSTO)param_STO).getPassByReference() ) {
                        // MAXTODO: refactor this same as writeloadstmt to determine g0 vs fp inside the method
                        if (arg_STO.getOffset().equals(arg_STO.getName())) {
                            writeReferenceArg(arg_STO.getName(), "%g0", "%o"+i);
                        } else {
                            writeReferenceArg(arg_STO.getOffset(), "%fp", "%o"+i);
                        }
                    } else {
                        writeLoadBlock(arg_STO, register_string);
                    }

                }
            }
        } /* end for loop */

    }

    // helper to write args passed by reference
    // set         |offset|, |register_string|
    // add         |add_string|, |register_string|, |register_string|
    public void writeReferenceArg(String offset, String add_string, String register_string) {
        writeAssembly(AssemblyMsg.SET_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, offset, register_string);
        writeAssembly(AssemblyMsg.ADD_OP);
        writeAssembly(AssemblyMsg.THREE_VALS, add_string, register_string, register_string);
    }

    //-------------------------------------------------------------------
    // Method that writes out the parameters for methods
    //      ! Store params
    //      st          %i0, [%fp+68]
    //      st          %f1, [%fp+72]
    //-------------------------------------------------------------------
    public void writeParameters(Vector<STO> params){
        increaseIndent();
        writeAssembly(AssemblyMsg.PARAM_MSG);
        if(params != null){
            for(int i = 0; i < params.size(); i++){
                STO sto = params.get(i);
                if(sto.getType().getName().equals("int") || sto.getType().getName().equals("bool")){
                    writeAssembly(AssemblyMsg.ST_OP);
                    String iString = "%i" + i;
                    String fpString = "[%fp+" + (fpOffset + (i*4)) + "]";
                    writeAssembly(AssemblyMsg.TWO_VALS, iString, fpString);
                }
                else if(sto.getType().getName().equals("float")){
                    writeAssembly(AssemblyMsg.ST_OP);
                    String iString;
                    // MAXTODO: double check with vivek if this cast is ok.
                    if ( ((VarSTO) sto).getPassByReference() ) {
                        iString = "%i" + i;
                    } else {
                        iString = "%f" + i;
                    }
                    String fpString = "[%fp+" + (fpOffset + (i*4)) + "]";
                    writeAssembly(AssemblyMsg.TWO_VALS, iString, fpString);
                }
            }
        }
        decreaseIndent();
    }

    //-------------------------------------------------------------------
    // Method that writes out the parameters for methods
    //
    //      (initStart)
    //      set         6, %o0
    //      //st          %o0, [%o1]
    //-------------------------------------------------------------------
    public void writeLocalInit(String name, String offset, String val, Type type){
        increaseIndent();

        writeAssembly(AssemblyMsg.LOCAL_INIT_MSG, name, val);
        System.out.println("initializing local: " + name +" with value: " + val);

        if(offset.equals(name)){
            writeLoadGlobalForAssign(offset);
        }else{
            initStart(name, val, offset);
        }

        if(!type.getName().equals("float")){
            writeAssembly(AssemblyMsg.SET_OP);
            writeAssembly(AssemblyMsg.TWO_VALS, val, "%o0");

            writeAssembly(AssemblyMsg.ST_OP);
            writeAssembly(AssemblyMsg.TWO_VALS, "%o0", "[%o1]");
            writeAssembly(AssemblyMsg.NEWLINE);
        }
        else{
            writeAssembly(AssemblyMsg.NEWLINE);
            writeFloatROData(val, "%f0");
            writeAssembly(AssemblyMsg.ST_OP);
            writeAssembly(AssemblyMsg.TWO_VALS, "%f0", "[%o1]");
            writeAssembly(AssemblyMsg.NEWLINE);
        }

        decreaseIndent();
    }

    //-------------------------------------------------------------------
    // helper method to write out float in rodata
    //
    //    .section    ".rodata"
    //    .align      4
    //.$$.float.1:
    //    .single     0r[val]
    //    
    //    .section    ".text"
    //    .align      4
    //    set         .$$.float.1, %l7
    //    ld          [%l7], [register]
    //
    //-------------------------------------------------------------------
    public void writeFloatROData(String val, String register) {
        writeAssembly(AssemblyMsg.RODATA);
        writeAssembly(AssemblyMsg.ALIGN_4);
        decreaseIndent();
        floatCounter++;
        String floatLabel = ".$$.float." + floatCounter;
        writeAssembly(AssemblyMsg.LABEL, floatLabel);
        increaseIndent();

        //hacking my way down town, parsing fast, test case pass, and I'm statically bound
        if(!val.contains(".")){
            val += ".0";
        }
        writeAssembly(AssemblyMsg.DOT_SINGLE, val);
        writeAssembly(AssemblyMsg.NEWLINE);
        writeAssembly(AssemblyMsg.TEXT);
        writeAssembly(AssemblyMsg.ALIGN_4);

        writeAssembly(AssemblyMsg.SET_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, floatLabel, "%l7");
        writeAssembly(AssemblyMsg.LD_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, "[%l7]", register);
    }

    //-------------------------------------------------------------------
    // helper method to write out the load block
    // offset = offset of the expression.
    // oVal = the type of o we're loading. e.g. %o0, %o1, %o2, etc.
    //      set         -4, %l7
    //      add         %fp, %l7, %l7
    //      ld          [%l7], %o0
    //-------------------------------------------------------------------
    public void writeLoadExpr(String offset, int oVal){
        writeAssembly(AssemblyMsg.SET_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, offset, "%l7");
        writeAssembly(AssemblyMsg.ADD_OP);
        writeAssembly(AssemblyMsg.THREE_VALS, "%fp", "%l7", "%l7");
        writeAssembly(AssemblyMsg.LD_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, "[%l7]", "%o" + oVal);
    }

    //-------------------------------------------------------------------
    // helper method to write out the load block
    // offset = offset of the expression.
    // oVal = the type of o we're loading. e.g. %o0, %o1, %o2, etc.
    //      set         -4, %l7
    //      add         %fp, %l7, %l7
    //      ld          [%l7], %o0
    //-------------------------------------------------------------------
    public void writeLoadGlobal(String offset, int oVal){
        writeAssembly(AssemblyMsg.SET_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, offset, "%l7");
        writeAssembly(AssemblyMsg.ADD_OP);
        writeAssembly(AssemblyMsg.THREE_VALS, "%g0", "%l7", "%l7");
        writeAssembly(AssemblyMsg.LD_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, "[%l7]", "%o" + oVal);
    }

    //-------------------------------------------------------------------
    // helper method to write out the load block
    // offset = offset of the expression.
    // oVal = the type of o we're loading. e.g. %o0, %o1, %o2, etc.
    //      set         i2, %o1
    //      add         %g0, %o1, %o1
    //-------------------------------------------------------------------
    public void writeLoadGlobalForAssign(String offset){
        writeAssembly(AssemblyMsg.SET_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, offset, "%o1");
        writeAssembly(AssemblyMsg.ADD_OP);
        writeAssembly(AssemblyMsg.THREE_VALS, "%g0", "%o1", "%o1");
    }

    //-------------------------------------------------------------------
    // helper method to write out float in rodata
    //
    //     .section    ".rodata"
    //     .align      4
    // .$$.str.1:
    //     .asciz      "hi"
    //-------------------------------------------------------------------
    public void writeStringROData(String string_name) {
        writeAssembly(AssemblyMsg.RODATA);
        writeAssembly(AssemblyMsg.ALIGN_4);

        decreaseIndent();
        stringCounter++;
        String stringLabel = ".$$.str." + stringCounter;
        writeAssembly(AssemblyMsg.LABEL, stringLabel);
        increaseIndent();

        writeAssembly(AssemblyMsg.ASCIZ, string_name);
    }

    //-------------------------------------------------------------------
    // Method that writes out assembly for initialization with vars
    //      (initStart)
    //      (loadExpr)
    //      st          %o0, [%o1]
    //
    //-------------------------------------------------------------------
    // public void writeLocalAssign(String desName, String desOffset, String exprName, String exprOffset){
    public void writeLocalAssign(String desName, STO des_STO, STO expr_STO) {
        increaseIndent();
        String desOffset = des_STO.getOffset();
        String exprName = expr_STO.getName();

        //writes out the start of the initialization
        writeAssembly(AssemblyMsg.LOCAL_INIT_MSG, desName, exprName);

        if(desOffset.equals(desName)){
            writeLoadGlobalForAssign(desOffset);
        }else{
            initStart(desName, exprName, desOffset);
        }

        writeLoadBlock(expr_STO, "%o0");
        
        writeAssembly(AssemblyMsg.ST_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, "%o0", "[%o1]");
        writeAssembly(AssemblyMsg.NEWLINE);

        decreaseIndent();
    }

    //-------------------------------------------------------------------
    // Method that writes out the the start of all initializations
    //
    //set       -desOffset, %o1
    //add       %fp, %o1, %o1
    //-------------------------------------------------------------------
    public void initStart(String desName, String exprName, String desOffset){
        writeAssembly(AssemblyMsg.SET_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, desOffset, "%o1");
        writeAssembly(AssemblyMsg.ADD_OP);
        writeAssembly(AssemblyMsg.THREE_VALS, "%fp", "%o1", "%o1");
    }

    //-------------------------------------------------------------------
    // Method that writes out the assembly for arithmetic
    //      (arithMsgCall)
    //      (writeLoadExp)
    //      (writeLoadExp)
    //      (arithOpCall)
    //      (arithEnd)   or  (comparisonEnd)
    //-------------------------------------------------------------------
    public void exprArith(STO a, STO b, STO result, String op){
        increaseIndent();
        //get the comment based on the op
        arithMsgCall(a.getName(), b.getName(), op);
        
        //is a a float?
        String aType = a.getType().getName();
        String resultType = result.getType().getName();
        if(!aType.equals("float")){
            writeLoadBlock(a, "%o0");

            if(resultType.equals("float")){
                // set         -8, %l7
                // add         %fp, %l7, %l7
                // st          %o0, [%l7]
                // ld          [%l7], %f0
                // fitos       %f0, %f0

                // set         |offset|, %o1
                // add         |add_name|, %o1, %o1
                // st          |reg_name|, [%o1]
                //-------------------------------------------------------------------
                // public void writeStore(String offset, String add_name, String reg_name) {
                writeStore(result.getOffset(), "%fp", "%o0");
                writeAssembly(AssemblyMsg.LD_OP);
                writeAssembly(AssemblyMsg.TWO_VALS, "[%l7]", "%f0");
                writeAssembly(AssemblyMsg.FITOS_OP);
                writeAssembly(AssemblyMsg.TWO_VALS, "%f0", "%f0");

            }
        }
        else if(aType.equals("float")){
            writeLoadBlock(a, "%f0");
        }
        //is b a float?
        String bType = b.getType().getName();
        if(!bType.equals("float")){
            writeLoadBlock(b, "%o1");
        }
        else if(bType.equals("float")){
            writeLoadBlock(b, "%f1");
        }

        arithOpCall(aType, bType, result, op);

        //call the part of the addition op that is the same regardless
        //of constant/expr addition or expr/expr addition
        switch(op){
            case "+":
            case "-":
            case "*":
            case "/":
            case "%":
            case "^":
            case "&":
            case "|":
                arithEnd(result);
                break;
            case ">":
                elseBranchAssembly(result);
                break;
            default:
                System.out.println("not handled constArith msg");
                break;
        }

        decreaseIndent();
    }

    //-------------------------------------------------------------------
    // Method that writes out the assembly for comparisons
    //      ! if ( ([lhs])>([rhs]) )
    //      set         [resultVal], %o0
    //      cmp         %o0, %g0
    //      be          .$$.else.1
    //      nop  
    //-------------------------------------------------------------------
    public void constComparisonAssembly(String lhs, String rhs, String resultVal, STO result, BinaryOp op){
        writeAssembly(AssemblyMsg.CONST_IF_MSG, lhs, rhs);
        writeAssembly(AssemblyMsg.SET_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, resultVal, "%o0");

        // //push on the ifEndLabel
        // cmpCounter++;
        // String beTarget = AssemblyMsg.CMP_LABEL + cmpCounter;
        //push the ifEndLabel onto the stack so that when the scope ends
        //we know which label to use.
        ifCounter++;
        String ifEndLabel = AssemblyMsg.ENDIF_LABEL + ifCounter;
        ifLabelStack.push(ifEndLabel);

        //push on the else label stuff
        elseCounter++;
        String beTarget = AssemblyMsg.ELSE_LABEL + elseCounter;
        //push onto stack so that we can keep track of else scopes
        elseLabelStack.push(beTarget);
        writeAssembly(AssemblyMsg.CMP_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, "%o0", "%g0");
        writeAssembly(AssemblyMsg.BE_OP);
        writeAssembly(AssemblyMsg.ONE_VAL, beTarget);
        writeAssembly(AssemblyMsg.NOP);
        writeAssembly(AssemblyMsg.NEWLINE);
    }

    //-------------------------------------------------------------------
    // Method that writes out the assembly for comparisons
    //      (comparisonEnd)
    //-------------------------------------------------------------------
    public void elseBranchAssembly(STO result){
        elseCounter++;
        String beTarget = AssemblyMsg.ELSE_LABEL + elseCounter;
        //push onto stack so that we can keep track of else scopes
        elseLabelStack.push(beTarget);
        comparisonEnd(result, beTarget);
    }

    //-------------------------------------------------------------------
    // Method that writes out the assembly for arithmetic
    //      (arithMsgCall)
    //      set 5, %o0
    //      (writeLoadExp)
    //      (arithCall)
    //      (arithEnd)   or  (comparisonEnd)
    //-------------------------------------------------------------------
    public void constArith(STO a, String b, String bType, STO result, String op, boolean constIsRight){
        increaseIndent();
        //call the part of the addition op that is the same regardless
        //of constant/expr addition or expr/expr addition
        //get the correct comment message based on operation
        String lhs;
        String rhs;
        if(constIsRight){
            lhs = a.getName();
            rhs = b;
        }
        else{
            lhs = b;
            rhs = a.getName();
        }

        //get the message for the arithmetic expression
        arithMsgCall(lhs, rhs, op);

        //based on which side is the constant, load them differently
        String aType = a.getType().getName();
        if(constIsRight){
            //is a a float?
            if(!aType.equals("float")){
                writeLoadBlock(a, "%o0");
            }
            else if(aType.equals("float")){
                writeLoadBlock(a, "%f0");
            }
            //CHECK FOR b'S TYPE AND DO STUFF BASED ON THAT.
            if(!bType.equals("float")){
                writeAssembly(AssemblyMsg.SET_OP);
                writeAssembly(AssemblyMsg.TWO_VALS, b, "%o1");
            }else if (bType.equals("float")){
                writeFloatROData(b, "%f1");
            }
        }
        else{
            if(!bType.equals("float")){
                writeAssembly(AssemblyMsg.SET_OP);
                writeAssembly(AssemblyMsg.TWO_VALS, b, "%o0");
            }else if (bType.equals("float")){
                writeFloatROData(b, "%f0");
            }
            //is a a float?
            if(!aType.equals("float")){
                writeLoadBlock(a, "%o1");
            }
            else if(aType.equals("float")){
                writeLoadBlock(a, "%f1");

            }
        }


        //do proper operation based on op
        arithOpCall(aType, bType, result, op);

        switch(op){
            case "+":
            case "-":
            case "*":
            case "/":
            case "%":
            case "^":
            case "&":
            case "|":
                arithEnd(result);
                break;
            case ">":
                elseBranchAssembly(result);
                break;
            default:
                System.out.println("not handled constArith msg");
                break;
        }

        decreaseIndent();
    }

    //-------------------------------------------------------------------
    // Method that writes out the assembly for arith messages
    //      ! (a)+(b)
    //-------------------------------------------------------------------
    public void arithMsgCall(String lhs, String rhs, String op){
        switch(op){
            case "+":
                writeAssembly(AssemblyMsg.ADD_MSG, lhs, rhs);
                break;
            case "-":
                writeAssembly(AssemblyMsg.SUB_MSG, lhs, rhs);  
                break;
            case "*":
                writeAssembly(AssemblyMsg.MUL_MSG, lhs, rhs);
                break;
            case "/":
                writeAssembly(AssemblyMsg.DIV_MSG, lhs, rhs);
                break;
            case "%":
                writeAssembly(AssemblyMsg.MOD_MSG, lhs, rhs);
                break;
            case "^":
                writeAssembly(AssemblyMsg.XOR_MSG, lhs, rhs);
                break;
            case "&":
                writeAssembly(AssemblyMsg.AND_MSG, lhs, rhs);
                break;
            case "|":
                writeAssembly(AssemblyMsg.OR_MSG,  lhs, rhs);
                break;
            case ">":
                writeAssembly(AssemblyMsg.GT_MSG,  lhs, rhs);
                break;
            default:
                System.out.println("not handled constArith msg");
                break;
        }
    }


    //-------------------------------------------------------------------
    // Method that writes out the assembly for arithmetic ops
    //      add or fadds       %o0, %o1, %o0, or %f0, %f1, %f0
    //-------------------------------------------------------------------
    public void arithOpCall(String aType, String bType, STO result, String op){
        String resultType = result.getType().getName();
        switch(op){
            case "+":
                if(!aType.equals("float") && !bType.equals("float")){
                    writeAssembly(AssemblyMsg.ADD_OP);
                    writeAssembly(AssemblyMsg.THREE_VALS, "%o0", "%o1", "%o0");
                }
                else if(aType.equals("float")){
                    writeAssembly(AssemblyMsg.FADDS_OP);
                    writeAssembly(AssemblyMsg.THREE_VALS, "%f0", "%f1", "%f0");
                }
                break;
            case "-":
                if(!aType.equals("float") && !bType.equals("float")){
                    writeAssembly(AssemblyMsg.SUB_OP);
                    writeAssembly(AssemblyMsg.THREE_VALS, "%o0", "%o1", "%o0");
                }
                else if(aType.equals("float")){
                    writeAssembly(AssemblyMsg.FSUBS_OP);
                    writeAssembly(AssemblyMsg.THREE_VALS, "%f0", "%f1", "%f0");
                }
                break;
            case "*":
                // arithFuncCall(AssemblyMsg.MUL_OP);
                if(!aType.equals("float") && !bType.equals("float")){
                    arithFuncCall(AssemblyMsg.MUL_OP);
                }
                else if(aType.equals("float")){
                    writeAssembly(AssemblyMsg.FMULS_OP);
                    writeAssembly(AssemblyMsg.THREE_VALS, "%f0", "%f1", "%f0");
                }
                break;
            case "/":
                // arithFuncCall(AssemblyMsg.DIV_OP);
                if(!aType.equals("float") && !bType.equals("float")){
                    arithFuncCall(AssemblyMsg.DIV_OP);
                }
                else if(aType.equals("float")){
                    writeAssembly(AssemblyMsg.FDIVS_OP);
                    writeAssembly(AssemblyMsg.THREE_VALS, "%f0", "%f1", "%f0");
                }
                break;
            case "%":
                arithFuncCall(AssemblyMsg.MOD_OP);
                break;
            case "^":
                writeAssembly(AssemblyMsg.XOR_OP);
                writeAssembly(AssemblyMsg.THREE_VALS, "%o0", "%o1", "%o0");
                break;
            case "&":
                writeAssembly(AssemblyMsg.AND_OP);
                writeAssembly(AssemblyMsg.THREE_VALS, "%o0", "%o1", "%o0");
                break;
            case "|":
                writeAssembly(AssemblyMsg.OR_OP);
                writeAssembly(AssemblyMsg.THREE_VALS, "%o0", "%o1", "%o0");
                break;
            case ">":
                cmpCounter++;
                String bleTarget = AssemblyMsg.CMP_LABEL + cmpCounter;
                //push the ifEndLabel onto the stack so that when the scope ends
                //we know which label to use.
                ifCounter++;
                String ifEndLabel = AssemblyMsg.ENDIF_LABEL + ifCounter;
                ifLabelStack.push(ifEndLabel);
                conditionalAssemblyStart(bleTarget, result.getOffset()); 
                break;
            default:
                System.out.println("not handled exprArith op");
                break;
        }
    }

    //-------------------------------------------------------------------
    // Method that writes out the assembly for a conditional expression call
    //     cmp         %o0, %o1
    //     ble         [bleTarget]
    //     mov         %g0, %o0
    //     inc         %o0
    // [bleTarget]:
    //     set         [resultOffset], %o1
    //     add         %fp, %o1, %o1
    //     st          %o0, [%o1]
    //-------------------------------------------------------------------
    public void conditionalAssemblyStart(String bleTarget, String resultOffset){
        writeAssembly(AssemblyMsg.CMP_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, "%o0", "%o1");
        writeAssembly(AssemblyMsg.BLE_OP);
        writeAssembly(AssemblyMsg.ONE_VAL, bleTarget);
        writeAssembly(AssemblyMsg.MOV_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, "%g0", "%o0");
        writeAssembly(AssemblyMsg.INC_OP);
        writeAssembly(AssemblyMsg.ONE_VAL, "%o0");

        decreaseIndent();
        writeAssembly(AssemblyMsg.LABEL, bleTarget);
        increaseIndent();
        writeStore(resultOffset, "%fp", "%o0");
        writeAssembly(AssemblyMsg.NEWLINE);
    }

    //-------------------------------------------------------------------
    // Method that writes out the assembly for a unary expression call
    // 
    //      ! -(a)
    //      set         -4, %l7
    //      add         %fp, %l7, %l7
    //      ld          [%l7], %o0
    //      neg         %o0, %o0
    //      (arithEnd)
    //-------------------------------------------------------------------
    public void exprUnarySign(STO a, STO result, String op){
        increaseIndent();

        switch(op){
            case "-":
                writeAssembly(AssemblyMsg.UNARYNEG_MSG, a.getName());
                writeLoadBlock(a, "%o0");
                writeAssembly(AssemblyMsg.UNARY_OP);
                writeAssembly(AssemblyMsg.TWO_VALS, "%o0", "%o0");
                break;
            case "+":
                writeAssembly(AssemblyMsg.UNARYPOS_MSG, a.getName());
                writeLoadBlock(a, "%o0");
                writeAssembly(AssemblyMsg.MOV_OP);
                writeAssembly(AssemblyMsg.TWO_VALS, "%o0", "%o0");
                break;
            default:
                System.out.println("Shouldn't be here in exprUnary");
                break;
        }

        arithEnd(result);
        decreaseIndent();
    }

    //-------------------------------------------------------------------
    // Method that writes out the assembly for a unary expression call
    // 
    //      ! ++(a)
    //      set         -4, %l7
    //      add         %fp, %l7, %l7
    //      ld          [%l7], %o0
    //      set         1, %o1
    //      add         %o0, %o1, %o2       or      sub
    //      set         -108, %o1
    //      add         %fp, %o1, %o1
    //      st          %o2, [%o1]          or      %o0, [%o1]
    //      set         -4, %o1
    //      add         %fp, %o1, %o1
    //      st          %o2, [%o1]
    //-------------------------------------------------------------------
    public void exprUnaryOp(STO a, STO result, String op, boolean isPre){

        //load the expression first
        increaseIndent();

        switch(op){
            case "++":
                if(isPre){
                    writeAssembly(AssemblyMsg.PREINC_MSG, a.getName());
                }else{
                    writeAssembly(AssemblyMsg.POSTINC_MSG, a.getName());
                }
                break;
            case "--":
                if(isPre){
                    writeAssembly(AssemblyMsg.PREDEC_MSG, a.getName());
                }else{
                    writeAssembly(AssemblyMsg.POSTDEC_MSG, a.getName());
                }
                break;
            default:
                break;
        }

        String aType = a.getType().getName();

        if(!aType.equals("float")){
            writeLoadBlock(a, "%o0");
            writeAssembly(AssemblyMsg.SET_OP);
            writeAssembly(AssemblyMsg.TWO_VALS,  "1", "%o1");
        }
        else if(aType.equals("float")){
            writeLoadBlock(a, "%f0");
            writeFloatROData("1.0", "%f1");
        }

        switch(op){
            case "++":
                if(!aType.equals("float")){
                    writeAssembly(AssemblyMsg.ADD_OP);
                    writeAssembly(AssemblyMsg.THREE_VALS, "%o0","%o1","%o2");
                    if(isPre){
                        writeStore(result.getOffset(), "%fp", "%o2");
                    }else{
                        writeStore(result.getOffset(), "%fp", "%o0");
                    }
                }
                else if(aType.equals("float")){
                    writeAssembly(AssemblyMsg.FADDS_OP);
                    writeAssembly(AssemblyMsg.THREE_VALS, "%f0","%f1","%f2");

                    if(isPre){
                        writeStore(result.getOffset(), "%fp", "%f2");
                    }else{
                        writeStore(result.getOffset(), "%fp", "%f0");
                    }
                }
                break;
            case "--":
                if(!aType.equals("float")){
                    writeAssembly(AssemblyMsg.SUB_OP);
                    writeAssembly(AssemblyMsg.THREE_VALS, "%o0","%o1","%o2");
                    if(isPre){
                        writeStore(result.getOffset(), "%fp", "%o2");
                    }else{
                        writeStore(result.getOffset(), "%fp", "%o0");
                    }
                }
                else if(aType.equals("float")){
                    writeAssembly(AssemblyMsg.FSUBS_OP);
                    writeAssembly(AssemblyMsg.THREE_VALS, "%f0","%f1","%f2");
                    if(isPre){
                        writeStore(result.getOffset(), "%fp", "%f2");
                    }else{
                        writeStore(result.getOffset(), "%fp", "%f0");
                    }
                }
                
                break;
            default:
                break;
        }

        if(!aType.equals("float")){
            writeStore(a.getOffset(), "%fp", "%o2");
        }
        else if(aType.equals("float")){
            writeStore(a.getOffset(), "%fp", "%f2");
        }

        decreaseIndent();
    }

    //-------------------------------------------------------------------
    // Method that writes out the common assembly found at end of
    // all additions
    //      set         [resultOffset], %o1
    //      add         %fp, %o1, %o1
    //      st          %o0, [%o1]
    //-------------------------------------------------------------------
    public void arithEnd(STO result){
        writeAssembly(AssemblyMsg.SET_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, result.getOffset(), "%o1");
        writeAssembly(AssemblyMsg.ADD_OP);
        writeAssembly(AssemblyMsg.THREE_VALS, "%fp", "%o1", "%o1");
        writeAssembly(AssemblyMsg.ST_OP);
        if(result.getType().getName().equals("float")){
            writeAssembly(AssemblyMsg.TWO_VALS, "%f0", "[%o1]");
        }else{
            writeAssembly(AssemblyMsg.TWO_VALS, "%o0", "[%o1]");
        }
        writeAssembly(AssemblyMsg.NEWLINE);
    }

    //-------------------------------------------------------------------
    // Method that writes out the common assembly found at end of
    // all comparisons
    //     (MSG)
    //     [writeLoadBlock]
    //     cmp         %o0, %g0
    //     be          [beTarget]
    //     nop 
    //
    //-------------------------------------------------------------------
    public void comparisonEnd(STO result, String beTarget){
        writeAssembly(AssemblyMsg.IF_MSG, result.getName());
        writeLoadBlock(result, "%o0");
        writeAssembly(AssemblyMsg.CMP_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, "%o0", "%g0");
        writeAssembly(AssemblyMsg.BE_OP);
        writeAssembly(AssemblyMsg.ONE_VAL, beTarget);
        writeAssembly(AssemblyMsg.NOP);
        writeAssembly(AssemblyMsg.NEWLINE);
        increaseIndent();
    }

    //-------------------------------------------------------------------
    // Method that writes out the assembly found at end of an if scope
    //          ba       .$$.endif.4
    //          nop    
    //  
    //          ! else
    //     .$$.else.4:
    //-------------------------------------------------------------------
    public void ifScopeEnd(){
        increaseIndent();
        writeAssembly(AssemblyMsg.BA_OP);
        //pop the label for the end of this scope
        String endifTarget = "";
        if(!ifLabelStack.isEmpty()){
            endifTarget = (String) ifLabelStack.pop();
        }
        //then push it back onto the stack since we need it when we do the 
        //exit label for the if statement
        ifLabelStack.push(endifTarget);
        writeAssembly(AssemblyMsg.ONE_VAL, endifTarget);
        writeAssembly(AssemblyMsg.NOP);
        writeAssembly(AssemblyMsg.NEWLINE);
        decreaseIndent();
        writeAssembly(AssemblyMsg.ELSE_MSG);
        decreaseIndent();
        //pop the label for the end of this scope
        String elseTarget = "";
        if(!elseLabelStack.isEmpty()){
            elseTarget = (String) elseLabelStack.pop();
        }
        writeAssembly(AssemblyMsg.LABEL, elseTarget);
        writeAssembly(AssemblyMsg.NEWLINE);
        increaseIndent();
    }

    //-------------------------------------------------------------------
    // Method that writes out the assembly found at end of an if statement
    //          ! endif
    //     .$$.endif.4:  
    //-------------------------------------------------------------------
    public void ifElseEnd(){
        writeAssembly(AssemblyMsg.ENDIF_MSG);
        decreaseIndent();
        //pop the label for the end of this scope
        String endifTarget = "";
        if(!ifLabelStack.isEmpty()){
            endifTarget = (String) ifLabelStack.pop();
        }
        writeAssembly(AssemblyMsg.LABEL, endifTarget);
        writeAssembly(AssemblyMsg.NEWLINE);
    }

    //-------------------------------------------------------------------
    // Method that writes out the common assembly for arithmetic that
    // calls on a function to do stuff
    //       call        .mul/.div/.rem
    //       nop
    //       mov         %o0, %o0
    //-------------------------------------------------------------------
    public void arithFuncCall(String op){
        writeAssembly(AssemblyMsg.FUNC_CALL, op);
        writeAssembly(AssemblyMsg.NOP);
        writeAssembly(AssemblyMsg.MOV_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, "%o0", "%o0");
    }

    //-------------------------------------------------------------------
    // Handles writing assembly for ints bools and floats
    //
    // ! cout << |STO name|
    // writeFloatROData(); (if it's a float)
    // writeLoadBlock();      or      set op for consts
    // print call
    // nop
    //-------------------------------------------------------------------
    public void writeCoutCall(STO cur_STO, String value_string) {
        String register_string = "";

        // 1. Determine type specific strings based on STO type.
        if (cur_STO.getType().isInt()) {
            register_string = "%o1";
        } else if (cur_STO.getType().isBoolean()) {
            register_string = "%o0";
        } else if (cur_STO.getType().isFloat()) {
            register_string = "%f0";
        }

        // 2. Write the common assembly with type specific strings.
        increaseIndent();

        writeAssembly(AssemblyMsg.COUT_COMMENT, cur_STO.getName());
        if (cur_STO.isExpr()) {
            // if it's an expr always write the offset
            writeLoadBlock(cur_STO, register_string);
        } else {
            if (cur_STO.isConst()) {
                if (cur_STO.getType().isFloat()) {
                    // check for floats to see if we need to print rodata
                    writeAssembly(AssemblyMsg.NEWLINE);
                    writeFloatROData(value_string, register_string);
                } else {
                    writeAssembly(AssemblyMsg.SET_OP);
                    writeAssembly(AssemblyMsg.TWO_VALS, value_string, register_string);
                }
            } else {
                writeLoadBlock(cur_STO, register_string);
            }
        }

        // 3. Determine which print function to write.
        if (cur_STO.getType().isInt()) {
            writeAssembly(AssemblyMsg.SET_OP);
            writeAssembly(AssemblyMsg.TWO_VALS, ".$$.intFmt", "%o0");
            writeAssembly(AssemblyMsg.FUNC_CALL, AssemblyMsg.PRINTF);
        } else if (cur_STO.getType().isBoolean()) {
            writeAssembly(AssemblyMsg.FUNC_CALL, ".$$.printBool");
        } else if (cur_STO.getType().isFloat()) {
            writeAssembly(AssemblyMsg.FUNC_CALL, "printFloat");
        }

        writeAssembly(AssemblyMsg.NOP);
        writeAssembly(AssemblyMsg.NEWLINE);
        decreaseIndent();
    }

    //-------------------------------------------------------------------
    // Write assembly for a CIN
    //-------------------------------------------------------------------
    public void writeCINCall(STO cur_STO) {
        increaseIndent();

        String call_string;
        String add_string;
        String register_string;

        // Check for float vs int
        if (cur_STO.getType().isFloat()) {
            call_string = "inputFloat";
            register_string = "%f0";
        } else if (cur_STO.getType().isInt()) {
            call_string = "inputInt";
            register_string = "%o0";
        } else {
            call_string = "Error: not int or float";
            register_string = "Error: not int or float";
        }

        // Check for global vs local
        if (cur_STO.getOffset() != null && cur_STO.getOffset().equals(cur_STO.getName())) {
            add_string = "%g0";
        } else {
            add_string = "%fp";
        }

        writeAssembly(AssemblyMsg.CIN_COMMENT, cur_STO.getName());
        writeAssembly(AssemblyMsg.FUNC_CALL, call_string);
        writeAssembly(AssemblyMsg.NOP);
        writeStore(cur_STO.getOffset(), add_string, register_string);
        writeAssembly(AssemblyMsg.NEWLINE);

        decreaseIndent();
    }

    //-------------------------------------------------------------------
    // Write assemby to print a string
    //
    //     (string rodata)
    //
    //     .section    ".text"
    //     .align      4
    //     ! cout << "hi"
    //     set         .$$.strFmt, %o0
    //     set         .$$.str.1, %o1
    //     call        printf
    //     nop
    //-------------------------------------------------------------------
    public void writePrintString(String string_name) {
        increaseIndent();

        writeStringROData(string_name);
        writeAssembly(AssemblyMsg.NEWLINE);

        writeAssembly(AssemblyMsg.TEXT);
        writeAssembly(AssemblyMsg.ALIGN_4);
        String comment_string = "\"" + string_name + "\"";
        writeAssembly(AssemblyMsg.COUT_COMMENT, comment_string);
        writeAssembly(AssemblyMsg.SET_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, ".$$.strFmt", "%o0");
        writeAssembly(AssemblyMsg.SET_OP);
        String string_number = ".$$.str." + stringCounter;
        writeAssembly(AssemblyMsg.TWO_VALS, string_number, "%o1");
        writeAssembly(AssemblyMsg.FUNC_CALL, AssemblyMsg.PRINTF);
        writeAssembly(AssemblyMsg.NOP);
        writeAssembly(AssemblyMsg.NEWLINE);

        decreaseIndent();
    }

    //-------------------------------------------------------------------
    // Helper: Write assemby to load a var into a register
    //
    // set         |offset|, %l7
    // add         |add_name|, %l7, %l7
    // ld          [%l7], |reg_name|
    //-------------------------------------------------------------------
    public void writeLoadBlock(STO cur_STO, String reg_name) {
        String offset;
        String add_name;
        offset = cur_STO.getOffset();
        
        if ( cur_STO.getOffset() != null && cur_STO.getOffset().equals(cur_STO.getName()) ) {
            add_name = "%g0";
        } else {
            add_name = "%fp";
        }

        writeAssembly(AssemblyMsg.SET_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, offset, "%l7");
        writeAssembly(AssemblyMsg.ADD_OP);
        writeAssembly(AssemblyMsg.THREE_VALS, add_name, "%l7", "%l7");
        if (cur_STO.isVar()) {
            // System.out.println("was a varSTO: " + cur_STO.getName() + " / " + cur_STO.getType());
            if ( ((VarSTO)cur_STO).getPassByReference() ) {
                // need to dereference once
                writeAssembly(AssemblyMsg.LD_OP);
                writeAssembly(AssemblyMsg.TWO_VALS, "[%l7]", "%l7");
            }
        } else {
            // System.out.println("not a varSTO: " + cur_STO.getName() + " / " + cur_STO.getType());
        }
        writeAssembly(AssemblyMsg.LD_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, "[%l7]", reg_name);
    }

    //-------------------------------------------------------------------
    // Helper: Write assemby to store a var into a register
    //
    // set         |offset|, %o1
    // add         |add_name|, %o1, %o1
    // st          |reg_name|, [%o1]
    //-------------------------------------------------------------------
    public void writeStore(String offset, String add_name, String reg_name) {
        writeAssembly(AssemblyMsg.SET_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, offset, "%o1");
        writeAssembly(AssemblyMsg.ADD_OP);
        writeAssembly(AssemblyMsg.THREE_VALS, add_name, "%o1", "%o1");
        writeAssembly(AssemblyMsg.ST_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, reg_name, "[%o1]");
    }


    //-------------------------------------------------------------------
    // Write assemby to print a endl
    // ! cout << endl
    // set         .$$.strEndl, %o0
    // call        printf
    // nop
    //-------------------------------------------------------------------
    public void writeEndl() {
        increaseIndent();

        writeAssembly(AssemblyMsg.COUT_ENDL);
        writeAssembly(AssemblyMsg.SET_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, ".$$.strEndl", "%o0");

        writeAssembly(AssemblyMsg.FUNC_CALL, AssemblyMsg.PRINTF);
        writeAssembly(AssemblyMsg.NOP);
        writeAssembly(AssemblyMsg.NEWLINE);

        decreaseIndent();
    }

    //-------------------------------------------------------------------
    // Write assemby to print an exit with a constant value
    // ! exit([val])
    // set         [val], %o0  or (writeLoadBlock)
    // call        exit
    // nop     
    //
    //-------------------------------------------------------------------
    public void writeExit(STO expr, String val) {
        increaseIndent();

        if(!val.equals("")){
            writeAssembly(AssemblyMsg.EXIT_MSG, val);

            writeAssembly(AssemblyMsg.SET_OP);
            writeAssembly(AssemblyMsg.TWO_VALS, val, "%o0");
        } else {
            writeAssembly(AssemblyMsg.EXIT_MSG, expr.getName());
            writeLoadBlock(expr, "%o0");
        }

        writeAssembly(AssemblyMsg.FUNC_CALL, "exit");
        writeAssembly(AssemblyMsg.NOP);
        writeAssembly(AssemblyMsg.NEWLINE);

        decreaseIndent();
    } 

    // 9
    public void writeAssembly(String template, String ... params) {
        StringBuilder asStmt = new StringBuilder();

        // 10
        if (template != AssemblyMsg.NEWLINE) {
            for (int i=0; i < indent_level; i++) {
                asStmt.append(AssemblyMsg.SEPARATOR);
            }
        }

        // 11
        asStmt.append(String.format(template, (Object[])params));

        try {
        	// System.out.println("writing assembly: " + asStmt.toString());
            fileWriter.write(asStmt.toString());
        } catch (IOException e) {
            System.err.println(AssemblyMsg.ERROR_IO_WRITE);
            e.printStackTrace();
        }
    }
}