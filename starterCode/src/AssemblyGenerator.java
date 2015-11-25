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
        writeParameters(params);

        decreaseIndent();
    }

    //-------------------------------------------------------------------
    // Method that writes out the assembly for method ends
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
    //-------------------------------------------------------------------
    public void writeParameters(Vector<STO> params){
        increaseIndent();
        writeAssembly(AssemblyMsg.PARAM_MSG);
        writeAssembly(AssemblyMsg.NEWLINE);
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
    //-------------------------------------------------------------------
    public void writeFloatROData(String val) {
        writeAssembly(AssemblyMsg.NEWLINE);
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
    //-------------------------------------------------------------------
    public void exprAddition(STO a, STO b, STO result){
        increaseIndent();
        increaseIndent();
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

        //call the part of the addition op that is the same regardless
        //of constant/expr addition or expr/expr addition
        additionEnd(result);
        decreaseIndent();
        decreaseIndent();
    }

    public void constAddition(STO a, String b, STO result){
        increaseIndent();
        increaseIndent();
        // ! (7)+(a)
        writeAssembly(AssemblyMsg.ADD_MSG, a.getName(), b);
        // set         7, %o0
        writeAssembly(AssemblyMsg.SET_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, b, "%o0");
        // set         -4, %l7
        // add         %fp, %l7, %l7
        // ld          [%l7], %o1
        writeLoadExpr(a.getOffset(), 1);
        //call the part of the addition op that is the same regardless
        //of constant/expr addition or expr/expr addition
        additionEnd(result);
        decreaseIndent();
        decreaseIndent();
    }

    //-------------------------------------------------------------------
    // Method that writes out the common assembly found at end of
    // all additions
    //-------------------------------------------------------------------
    public void additionEnd(STO result){
        // add         %o0, %o1, %o0
        writeAssembly(AssemblyMsg.ADD_OP);
        writeAssembly(AssemblyMsg.THREE_VALS, "%o0", "%o1", "%o0");
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
    // Write assemby to print an int
    //
    // ! cout << 5
    // set         5, %o1
    // set         .$$.intFmt, %o0
    // call        printf
    // nop
    //-------------------------------------------------------------------
    public void writePrintInt(String int_value, String int_name) {
        increaseIndent();
        increaseIndent();

        writeAssembly(AssemblyMsg.COUT_COMMENT, int_name);
        writeAssembly(AssemblyMsg.SET_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, int_value, "%o1");

        writeAssembly(AssemblyMsg.SET_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, ".$$.intFmt", "%o0");

        writeAssembly(AssemblyMsg.FUNC_CALL, AssemblyMsg.PRINTF);
        writeAssembly(AssemblyMsg.NOP);
        writeAssembly(AssemblyMsg.NEWLINE);

        decreaseIndent();
        decreaseIndent();
    }

    //-------------------------------------------------------------------
    // Write assemby to print an bool
    //
    // ! cout << true
    // set         1, %o0
    // call        .$$.printBool
    // nop
    //-------------------------------------------------------------------
    public void writePrintBool(String bool_value, String bool_name) {
        increaseIndent();
        increaseIndent();

        writeAssembly(AssemblyMsg.COUT_COMMENT, bool_name);
        writeAssembly(AssemblyMsg.SET_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, bool_value, "%o0");
        writeAssembly(AssemblyMsg.FUNC_CALL, ".$$.printBool");
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
    // Write assemby to print a float
    //
    //     (float rodata)
    //
    //     call     printFloat
    //     nop
    //-------------------------------------------------------------------
    public void writePrintFloat(String float_value, String float_name) {
        increaseIndent();
        increaseIndent();

        writeAssembly(AssemblyMsg.COUT_COMMENT, float_name);
        writeAssembly(AssemblyMsg.NEWLINE);
        writeFloatROData(float_value);

        writeAssembly(AssemblyMsg.FUNC_CALL, "printFloat");
        writeAssembly(AssemblyMsg.NOP);
        writeAssembly(AssemblyMsg.NEWLINE);

        decreaseIndent();
        decreaseIndent();
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