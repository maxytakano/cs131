import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;

public class AssemblyGenerator {
    // 1
    private int indent_level = 0;
    private int fpOffset = 68;

    // a counter for float labels. used for local floats
    private int floatCounter = 0;
    private int stringCounter = 0;

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

        decreaseIndent();
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
        increaseIndent();

        //This section is for the ret, restore stuff
        writeAssembly(AssemblyMsg.FUNC_END, mangledName);
        String finiName = mangledName + ".fini";
        writeAssembly(AssemblyMsg.FUNC_CALL, finiName);
        writeAssembly(AssemblyMsg.NOP);
        writeAssembly(AssemblyMsg.RET);
        writeAssembly(AssemblyMsg.RESTORE);
        writeAssembly(AssemblyMsg.FUNC_SAVE, "SAVE." + mangledName, localOffset);

        decreaseIndent();

        //Here's the section with all the fini messages
        writeAssembly(AssemblyMsg.LABEL, finiName);

        increaseIndent();

        writeAssembly(AssemblyMsg.SAVE, "%sp", "-96", "%sp");
        writeAssembly(AssemblyMsg.NEWLINE);
        writeAssembly(AssemblyMsg.RET);
        writeAssembly(AssemblyMsg.RESTORE);

        decreaseIndent();
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
            System.out.println("doing jank");
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
                    String iString = "%f" + i;
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
        increaseIndent();

        //writes out the start of all assign expressions. It's common, so it's
        //separated into its own setion for repeated code use.
        initStart(name, val, offset);

        if(!type.getName().equals("float")){

            //set         6, %o0
            writeAssembly(AssemblyMsg.SET_OP);
            writeAssembly(AssemblyMsg.TWO_VALS, val, "%o0");

            //st          %o0, [%o1]
            writeAssembly(AssemblyMsg.ST_OP);
            writeAssembly(AssemblyMsg.TWO_VALS, "%o0", "[%o1]");
            writeAssembly(AssemblyMsg.NEWLINE);
        }
        else{
            writeAssembly(AssemblyMsg.NEWLINE);
            writeFloatROData(val);
            //st %f0, [%o1]
            writeAssembly(AssemblyMsg.ST_OP);
            writeAssembly(AssemblyMsg.TWO_VALS, "%f0", "[%o1]");
            writeAssembly(AssemblyMsg.NEWLINE);
        }

        decreaseIndent();
        decreaseIndent();
    }

    //-------------------------------------------------------------------
    // helper method to write out float in rodata
    //
    //    .section    ".rodata"
    //    .align      4
    //.$$.float.1:
    //    .single     0r7.0
    //    
    //    .section    ".text"
    //    .align      4
    //    set         .$$.float.1, %l7
    //    ld          [%l7], %f0
    //
    //-------------------------------------------------------------------
    public void writeFloatROData(String val) {
        //.section ".rodata"
        writeAssembly(AssemblyMsg.RODATA);
        //.align 4
        writeAssembly(AssemblyMsg.ALIGN_4);
        //.$$.float.[floatCounter]:
        decreaseIndent();
        floatCounter++;
        String floatLabel = ".$$.float." + floatCounter;
        writeAssembly(AssemblyMsg.LABEL, floatLabel);
        increaseIndent();
        //.single     val
        writeAssembly(AssemblyMsg.DOT_SINGLE, val);
        //now we save the float into the location f0z
        writeAssembly(AssemblyMsg.NEWLINE);
        //.section ".text"
        writeAssembly(AssemblyMsg.TEXT);
        //.align 4
        writeAssembly(AssemblyMsg.ALIGN_4);
        //set         [floatLabel], %l7
        writeAssembly(AssemblyMsg.SET_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, floatLabel, "%l7");
        //ld [%l7], %f0
        writeAssembly(AssemblyMsg.LD_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, "[%l7]", "%f0");
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
        // set         -4, %l7
        writeAssembly(AssemblyMsg.SET_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, "-" + offset, "%l7");
        // add         %fp, %l7, %l7
        writeAssembly(AssemblyMsg.ADD_OP);
        writeAssembly(AssemblyMsg.THREE_VALS, "%fp", "%l7", "%l7");
        // ld          [%l7], %o0
        writeAssembly(AssemblyMsg.LD_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, "[%l7]", "%o" + oVal);
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
    public void writeLocalAssign(String desName, String desOffset, String exprName, String exprOffset){
        increaseIndent();
        increaseIndent();

        //writes out the start of the initialization
        // ! b = a
        // set         -8, %o1
        // add         %fp, %o1, %o1
        initStart(desName, exprName, desOffset);

        //call the load code
        // set         -4, %l7
        // add         %fp, %l7, %l7
        // ld          [%l7], %o0
        writeLoadExpr(exprOffset, 0);

        // st          %o0, [%o1]
        writeAssembly(AssemblyMsg.ST_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, "%o0", "[%o1]");
        writeAssembly(AssemblyMsg.NEWLINE);

        decreaseIndent();
        decreaseIndent();
    }

    //-------------------------------------------------------------------
    // Method that writes out the the start of all initializations
    //-------------------------------------------------------------------
    public void initStart(String desName, String exprName, String desOffset){
        // ! desName = exprName
        writeAssembly(AssemblyMsg.LOCAL_INIT_MSG, desName, exprName);

        //set       -desOffset, %o1
        writeAssembly(AssemblyMsg.SET_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, "-" + desOffset, "%o1");

        //add       %fp, %o1, %o1
        writeAssembly(AssemblyMsg.ADD_OP);
        writeAssembly(AssemblyMsg.THREE_VALS, "%fp", "%o1", "%o1");
    }

    //-------------------------------------------------------------------
    // Method that writes out the assembly for method starts
    //      ! (a)+(b)
    //      (writeLoadExpr)
    //      (writeLoadExpr)
    //      add/sub/(arithCall)
    //      (additionEndResult)
    //-------------------------------------------------------------------
    public void exprArith(STO a, STO b, STO result, String op){
        increaseIndent();
        increaseIndent();
        switch(op){
            case "+":
                // ! (a)+(b)
                writeAssembly(AssemblyMsg.ADD_MSG, a.getName(), b.getName());
                // set         -4, %l7
                // add         %fp, %l7, %l7
                // ld          [%l7], %o0
                writeLoadExpr(a.getOffset(), 0);

                // set         -8, %l7
                // add         %fp, %l7, %l7
                // ld          [%l7], %o1
                writeLoadExpr(b.getOffset(), 1);
                // add         %o0, %o1, %o0
                writeAssembly(AssemblyMsg.ADD_OP);
                writeAssembly(AssemblyMsg.THREE_VALS, "%o0", "%o1", "%o0");
                break;
            case "-":
                // ! (a)-(b)
                writeAssembly(AssemblyMsg.SUB_MSG, a.getName(), b.getName());
                // set         -4, %l7      
                // add         %fp, %l7, %l7
                // ld          [%l7], %o0
                writeLoadExpr(a.getOffset(), 0);

                // set         -8, %l7
                // add         %fp, %l7, %l7
                // ld          [%l7], %o1
                writeLoadExpr(b.getOffset(), 1);  

                // add         %o0, %o1, %o0
                writeAssembly(AssemblyMsg.SUB_OP);
                writeAssembly(AssemblyMsg.THREE_VALS, "%o0", "%o1", "%o0");  
                break;
            case "*":
                // ! (a)*(b)
                writeAssembly(AssemblyMsg.MUL_MSG, a.getName(), b.getName());

                // set         -4, %l7
                // add         %fp, %l7, %l7
                // ld          [%l7], %o0
                writeLoadExpr(a.getOffset(), 0);

                // set         -8, %l7
                // add         %fp, %l7, %l7
                // ld          [%l7], %o1
                writeLoadExpr(b.getOffset(), 1);

                arithFuncCall(AssemblyMsg.MUL_OP);
                break;
            case "/":
                // ! (a)/(b)
                writeAssembly(AssemblyMsg.DIV_MSG, a.getName(), b.getName());
                // set         -4, %l7
                // add         %fp, %l7, %l7
                // ld          [%l7], %o0
                writeLoadExpr(a.getOffset(), 0);

                // set         -8, %l7
                // add         %fp, %l7, %l7
                // ld          [%l7], %o1
                writeLoadExpr(b.getOffset(), 1);

                arithFuncCall(AssemblyMsg.DIV_OP);
                break;
            case "%":
                // ! (a)%(b)
                writeAssembly(AssemblyMsg.MOD_MSG, a.getName(), b.getName());
                // set         -4, %l7
                // add         %fp, %l7, %l7
                // ld          [%l7], %o0
                writeLoadExpr(a.getOffset(), 0);

                // set         -8, %l7
                // add         %fp, %l7, %l7
                // ld          [%l7], %o1
                writeLoadExpr(b.getOffset(), 1);

                arithFuncCall(AssemblyMsg.MOD_OP);
                break;
            case "^":
                // ! (a)^(b)
                writeAssembly(AssemblyMsg.XOR_MSG, a.getName(), b.getName());

                // set         -4, %l7
                // add         %fp, %l7, %l7
                // ld          [%l7], %o0
                writeLoadExpr(a.getOffset(), 0);

                // set         -8, %l7
                // add         %fp, %l7, %l7
                // ld          [%l7], %o1
                writeLoadExpr(b.getOffset(), 1);

                // add         %o0, %o1, %o0
                writeAssembly(AssemblyMsg.XOR_OP);
                writeAssembly(AssemblyMsg.THREE_VALS, "%o0", "%o1", "%o0");
                break;
            case "&":
                // ! (a)^(b)
                writeAssembly(AssemblyMsg.AND_MSG, a.getName(), b.getName());

                // set         -4, %l7
                // add         %fp, %l7, %l7
                // ld          [%l7], %o0
                writeLoadExpr(a.getOffset(), 0);

                // set         -8, %l7
                // add         %fp, %l7, %l7
                // ld          [%l7], %o1
                writeLoadExpr(b.getOffset(), 1);

                // add         %o0, %o1, %o0
                writeAssembly(AssemblyMsg.AND_OP);
                writeAssembly(AssemblyMsg.THREE_VALS, "%o0", "%o1", "%o0");
                break;
            case "|":
                // ! (a)^(b)
                writeAssembly(AssemblyMsg.OR_MSG, a.getName(), b.getName());

                // set         -4, %l7
                // add         %fp, %l7, %l7
                // ld          [%l7], %o0
                writeLoadExpr(a.getOffset(), 0);

                // set         -8, %l7
                // add         %fp, %l7, %l7
                // ld          [%l7], %o1
                writeLoadExpr(b.getOffset(), 1);

                // add         %o0, %o1, %o0
                writeAssembly(AssemblyMsg.OR_OP);
                writeAssembly(AssemblyMsg.THREE_VALS, "%o0", "%o1", "%o0");
                break;
            default:
                System.out.println("not handled");
                break;
        }
        

        //call the part of the addition op that is the same regardless
        //of constant/expr addition or expr/expr addition
        arithEnd(result);
        decreaseIndent();
        decreaseIndent();
    }

    //-------------------------------------------------------------------
    // Method that writes out the assembly for method starts
    //      ! (7)+(a)
    //      set         7, %o0
    //      (writeLoadExpr)
    //      (additionEndResult)
    //-------------------------------------------------------------------
    public void constAddition(STO a, String b, STO result, String op){
        increaseIndent();
        increaseIndent();
        //call the part of the addition op that is the same regardless
        //of constant/expr addition or expr/expr addition
        switch(op){
            case "+":
                // ! (a)+(b)
                writeAssembly(AssemblyMsg.ADD_MSG, a.getName(), b);
                // set         7, %o0
                writeAssembly(AssemblyMsg.SET_OP);
                writeAssembly(AssemblyMsg.TWO_VALS, b, "%o0");

                // set         -8, %l7
                // add         %fp, %l7, %l7
                // ld          [%l7], %o1
                writeLoadExpr(a.getOffset(), 1);
                // add         %o0, %o1, %o0
                writeAssembly(AssemblyMsg.ADD_OP);
                writeAssembly(AssemblyMsg.THREE_VALS, "%o0", "%o1", "%o0");
                break;
            case "-":
                // ! (a)-(b)
                writeAssembly(AssemblyMsg.SUB_MSG, a.getName(), b);
                // set         7, %o0
                writeAssembly(AssemblyMsg.SET_OP);
                writeAssembly(AssemblyMsg.TWO_VALS, b, "%o0");

                // set         -8, %l7
                // add         %fp, %l7, %l7
                // ld          [%l7], %o1
                writeLoadExpr(a.getOffset(), 1);  

                // add         %o0, %o1, %o0
                writeAssembly(AssemblyMsg.SUB_OP);
                writeAssembly(AssemblyMsg.THREE_VALS, "%o0", "%o1", "%o0");  
                break;
            case "*":
                // ! (a)*(b)
                writeAssembly(AssemblyMsg.MUL_MSG, a.getName(), b);

                // set         7, %o0
                writeAssembly(AssemblyMsg.SET_OP);
                writeAssembly(AssemblyMsg.TWO_VALS, b, "%o0");

                // set         -8, %l7
                // add         %fp, %l7, %l7
                // ld          [%l7], %o1
                writeLoadExpr(a.getOffset(), 1);

                arithFuncCall(AssemblyMsg.MUL_OP);
                break;
            case "/":
                // ! (a)/(b)
                writeAssembly(AssemblyMsg.DIV_MSG, a.getName(), b);
                // set         7, %o0
                writeAssembly(AssemblyMsg.SET_OP);
                writeAssembly(AssemblyMsg.TWO_VALS, b, "%o0");

                // set         -8, %l7
                // add         %fp, %l7, %l7
                // ld          [%l7], %o1
                writeLoadExpr(a.getOffset(), 1);

                arithFuncCall(AssemblyMsg.DIV_OP);
                break;
            case "%":
                // ! (a)%(b)
                writeAssembly(AssemblyMsg.MOD_MSG, a.getName(), b);
                // set         7, %o0
                writeAssembly(AssemblyMsg.SET_OP);
                writeAssembly(AssemblyMsg.TWO_VALS, b, "%o0");

                // set         -8, %l7
                // add         %fp, %l7, %l7
                // ld          [%l7], %o1
                writeLoadExpr(a.getOffset(), 1);

                arithFuncCall(AssemblyMsg.MOD_OP);
                break;
            case "^":
                // ! (a)^(b)
                writeAssembly(AssemblyMsg.XOR_MSG, a.getName(), b);

                // set         7, %o0
                writeAssembly(AssemblyMsg.SET_OP);
                writeAssembly(AssemblyMsg.TWO_VALS, b, "%o0");

                // set         -8, %l7
                // add         %fp, %l7, %l7
                // ld          [%l7], %o1
                writeLoadExpr(a.getOffset(), 1);

                // add         %o0, %o1, %o0
                writeAssembly(AssemblyMsg.XOR_OP);
                writeAssembly(AssemblyMsg.THREE_VALS, "%o0", "%o1", "%o0");
                break;
            case "&":
                // ! (a)^(b)
                writeAssembly(AssemblyMsg.AND_MSG, a.getName(), b);

                // set         7, %o0
                writeAssembly(AssemblyMsg.SET_OP);
                writeAssembly(AssemblyMsg.TWO_VALS, b, "%o0");

                // set         -8, %l7
                // add         %fp, %l7, %l7
                // ld          [%l7], %o1
                writeLoadExpr(a.getOffset(), 1);

                // add         %o0, %o1, %o0
                writeAssembly(AssemblyMsg.AND_OP);
                writeAssembly(AssemblyMsg.THREE_VALS, "%o0", "%o1", "%o0");
                break;
            case "|":
                // ! (a)^(b)
                writeAssembly(AssemblyMsg.OR_MSG, a.getName(), b);

                // set         7, %o0
                writeAssembly(AssemblyMsg.SET_OP);
                writeAssembly(AssemblyMsg.TWO_VALS, b, "%o0");

                // set         -8, %l7
                // add         %fp, %l7, %l7
                // ld          [%l7], %o1
                writeLoadExpr(a.getOffset(), 1);

                // add         %o0, %o1, %o0
                writeAssembly(AssemblyMsg.OR_OP);
                writeAssembly(AssemblyMsg.THREE_VALS, "%o0", "%o1", "%o0");
                break;
            default:
                System.out.println("not handled");
                break;
        }
        arithEnd(result);
        decreaseIndent();
        decreaseIndent();
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
        increaseIndent();

        switch(op){
            case "-":
                writeAssembly(AssemblyMsg.UNARYNEG_MSG, a.getName());
                writeLoadExpr(a.getOffset(), 0);
                writeAssembly(AssemblyMsg.UNARY_OP);
                writeAssembly(AssemblyMsg.TWO_VALS, "%o0", "%o0");
                break;
            case "+":
                writeAssembly(AssemblyMsg.UNARYPOS_MSG, a.getName());
                writeLoadExpr(a.getOffset(), 0);
                writeAssembly(AssemblyMsg.MOV_OP);
                writeAssembly(AssemblyMsg.TWO_VALS, "%o0", "%o0");
                break;
            default:
                System.out.println("Shouldn't be here in exprUnary");
                break;
        }

        arithEnd(result);
        decreaseIndent();
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

        writeLoadExpr(a.getOffset(), 0);
        writeAssembly(AssemblyMsg.SET_OP);
        writeAssembly(AssemblyMsg.TWO_VALS,  "1", "%o1");

        switch(op){
            case "++":
                writeAssembly(AssemblyMsg.ADD_OP);
                writeAssembly(AssemblyMsg.THREE_VALS, "%o0","%o1","%o2");

                writeAssembly(AssemblyMsg.SET_OP);
                writeAssembly(AssemblyMsg.TWO_VALS, "-" + result.getOffset(), "%o1");
                writeAssembly(AssemblyMsg.ADD_OP);
                writeAssembly(AssemblyMsg.THREE_VALS, "%fp", "%o1", "%o1");
                if(isPre){
                    writeAssembly(AssemblyMsg.ST_OP);
                    writeAssembly(AssemblyMsg.TWO_VALS, "%o2", "[%o1]");
                }else{
                    writeAssembly(AssemblyMsg.ST_OP);
                    writeAssembly(AssemblyMsg.TWO_VALS, "%o0", "[%o1]");
                }
                break;
            case "--":
                writeAssembly(AssemblyMsg.SUB_OP);
                writeAssembly(AssemblyMsg.THREE_VALS, "%o0","%o1","%o2");

                writeAssembly(AssemblyMsg.SET_OP);
                writeAssembly(AssemblyMsg.TWO_VALS, "-" + result.getOffset(), "%o1");
                writeAssembly(AssemblyMsg.ADD_OP);
                writeAssembly(AssemblyMsg.THREE_VALS, "%fp", "%o1", "%o1");
                if(isPre){
                    writeAssembly(AssemblyMsg.ST_OP);
                    writeAssembly(AssemblyMsg.TWO_VALS, "%o2", "[%o1]");
                }else{
                    writeAssembly(AssemblyMsg.ST_OP);
                    writeAssembly(AssemblyMsg.TWO_VALS, "%o0", "[%o1]");
                }
                break;
            default:
                break;
        }

        writeAssembly(AssemblyMsg.SET_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, "-" + a.getOffset(), "%o1");
        writeAssembly(AssemblyMsg.ADD_OP);
        writeAssembly(AssemblyMsg.THREE_VALS, "%fp", "%o1", "%o1");
        writeAssembly(AssemblyMsg.ST_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, "%o2", "[%o1]");


        decreaseIndent();
        decreaseIndent();
    }

    //-------------------------------------------------------------------
    // Method that writes out the common assembly found at end of
    // all additions
    //      set         -12, %o1
    //      add         %fp, %o1, %o1
    //      st          %o0, [%o1]
    //-------------------------------------------------------------------
    public void arithEnd(STO result){
        // set         -12, %o1
        writeAssembly(AssemblyMsg.SET_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, "-" + result.getOffset(), "%o1");
        // add         %fp, %o1, %o1
        writeAssembly(AssemblyMsg.ADD_OP);
        writeAssembly(AssemblyMsg.THREE_VALS, "%fp", "%o1", "%o1");
        // st          %o0, [%o1]
        writeAssembly(AssemblyMsg.ST_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, "%o0", "[%o1]");
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
    // writeLoadCout();      or      set op for consts
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
        increaseIndent();

        writeAssembly(AssemblyMsg.COUT_COMMENT, cur_STO.getName());
        if (cur_STO.isExpr()) {
            // if it's an expr always write the offset
            writeLoadCout(cur_STO.getOffset(), "%fp", register_string);
        } else {
            if (cur_STO.isConst()) {
                if (cur_STO.getType().isFloat()) {
                    // check for floats to see if we need to print rodata
                    writeAssembly(AssemblyMsg.NEWLINE);
                    writeFloatROData(value_string);
                } else {
                    writeAssembly(AssemblyMsg.SET_OP);
                    writeAssembly(AssemblyMsg.TWO_VALS, value_string, register_string);
                }
            } else {
                // Check if it's a global or local var.
                if (cur_STO.getOffset().equals(cur_STO.getName())) {
                    // if the name equals the offset it's a global int
                    writeLoadCout(cur_STO.getOffset(), "%g0", register_string);
                } else {
                    // else it's a local int
                    writeLoadCout(cur_STO.getOffset(), "%fp", register_string);
                }
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
        decreaseIndent();
    }

    //-------------------------------------------------------------------
    // Helper: Write assemby to load a var into a register
    //
    // set         |offset|, %l7
    // add         |add_name|, %l7, %l7
    // ld          [%l7], |reg_name|
    //-------------------------------------------------------------------
    public void writeLoadCout(String offset, String add_name, String reg_name) {
        writeAssembly(AssemblyMsg.SET_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, offset, "%l7");
        writeAssembly(AssemblyMsg.ADD_OP);
        writeAssembly(AssemblyMsg.THREE_VALS, add_name, "%l7", "%l7");
        writeAssembly(AssemblyMsg.LD_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, "[%l7]", reg_name);
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
        increaseIndent();

        writeAssembly(AssemblyMsg.COUT_ENDL);
        writeAssembly(AssemblyMsg.SET_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, ".$$.strEndl", "%o0");

        writeAssembly(AssemblyMsg.FUNC_CALL, AssemblyMsg.PRINTF);
        writeAssembly(AssemblyMsg.NOP);
        writeAssembly(AssemblyMsg.NEWLINE);

        decreaseIndent();
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