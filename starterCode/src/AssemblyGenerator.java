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
        writeAssembly(AssemblyMsg.NEWLINE);
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
            increaseIndent();
            writeAssembly(AssemblyMsg.NEWLINE);
            writeAssembly(AssemblyMsg.DOT_GLOBAL, funcName);
            decreaseIndent();
            writeAssembly(AssemblyMsg.LABEL, funcName);
        }
        writeAssembly(AssemblyMsg.LABEL, mangledName);
        increaseIndent();
        writeAssembly((AssemblyMsg.SET_OP + AssemblyMsg.SEPARATOR));
        writeAssembly(AssemblyMsg.TWO_VALS, "SAVE." + mangledName, "%g1");
        writeAssembly(AssemblyMsg.NEWLINE);
        writeAssembly(AssemblyMsg.SAVE, "%sp", "%g1", "%sp");
        writeAssembly(AssemblyMsg.NEWLINE);
        writeAssembly(AssemblyMsg.NEWLINE);

        writeParameters(params);

        //-------------------------------------------------------------------
        // everything below here is for testing purposes only
        //-------------------------------------------------------------------
        writeAssembly(AssemblyMsg.NEWLINE);
        writeAssembly(AssemblyMsg.NEWLINE);
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
        writeAssembly(AssemblyMsg.NEWLINE);

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
            for(int i = 0; i < params.size(); i++){
                STO sto = params.get(i);
                if(sto.getType().getName().equals("int") || sto.getType().getName().equals("bool")){
                    writeAssembly(AssemblyMsg.ST_OP);
                    String iString = "%i" + i;
                    String fpString = "[%fp+" + (fpOffset + (i*4)) + "]";
                    writeAssembly(AssemblyMsg.TWO_VALS, iString, fpString);
                    writeAssembly(AssemblyMsg.NEWLINE);
                }
                else if(sto.getType().getName().equals("float")){
                    writeAssembly(AssemblyMsg.ST_OP);
                    String iString = "%f" + i;
                    String fpString = "[%fp+" + (fpOffset + (i*4)) + "]";
                    writeAssembly(AssemblyMsg.TWO_VALS, iString, fpString);
                    writeAssembly(AssemblyMsg.NEWLINE);
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

        // ! varName = val
        writeAssembly(AssemblyMsg.LOCAL_INIT_MSG, name, val);
        writeAssembly(AssemblyMsg.NEWLINE);

        //set       -offset, %o1
        writeAssembly(AssemblyMsg.SET_OP);
        writeAssembly(AssemblyMsg.TWO_VALS, "-" + offset, "%o1");
        writeAssembly(AssemblyMsg.NEWLINE);

        //add       %fp, %o1, %o1
        writeAssembly(AssemblyMsg.ADD_OP);
        writeAssembly(AssemblyMsg.THREE_VALS, "%fp", "%o1", "%o1");
        writeAssembly(AssemblyMsg.NEWLINE);

        if(!type.getName().equals("float")){

            //set         6, %o0
            writeAssembly(AssemblyMsg.SET_OP);
            writeAssembly(AssemblyMsg.TWO_VALS, val, "%o0");
            writeAssembly(AssemblyMsg.NEWLINE);

            //st          %o0, [%o1]
            writeAssembly(AssemblyMsg.ST_OP);
            writeAssembly(AssemblyMsg.TWO_VALS, "%o0", "[%o1]");
            writeAssembly(AssemblyMsg.NEWLINE);
            writeAssembly(AssemblyMsg.NEWLINE);
        }
        else{
            //THIS SECTION FOR FLOAT INIT COULD BE A HELPER METHOD, BUT FOR NOW
            //I'M JUST LEAVING IT HERE IN CASE IT DOESN'T NEED TO BE ONE
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
            //.section ".text"
            writeAssembly(AssemblyMsg.TEXT);
            //.align 4
            writeAssembly(AssemblyMsg.ALIGN_4);
            //set         [floatLabel], %l7
            writeAssembly(AssemblyMsg.SET_OP);
            writeAssembly(AssemblyMsg.TWO_VALS, floatLabel, "%l7");
            writeAssembly(AssemblyMsg.NEWLINE);
            //ld [%l7], %f0
            writeAssembly(AssemblyMsg.LD_OP);
            writeAssembly(AssemblyMsg.TWO_VALS, "[%l7]", "%f0");
            writeAssembly(AssemblyMsg.NEWLINE);
            //st %f0, [%o1]
            writeAssembly(AssemblyMsg.ST_OP);
            writeAssembly(AssemblyMsg.TWO_VALS, "%f0", "[%o1]");
            writeAssembly(AssemblyMsg.NEWLINE);
            writeAssembly(AssemblyMsg.NEWLINE);
        }

        decreaseIndent();
        decreaseIndent();
    }

    
    // 9
    public void writeAssembly(String template, String ... params) {
        StringBuilder asStmt = new StringBuilder();
        
        // 10
        for (int i=0; i < indent_level; i++) {
            asStmt.append(AssemblyMsg.SEPARATOR);
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