import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class AssemblyGenerator {
    // 1
    private int indent_level = 0;
    
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

    // public void writeGlobal(String id, String type, String val){
    //     switch(type){
    //         case "int":
    //             writeGlobalVar(id, type, val);
    //             break;
    //         case "float":
    //             writeGlobalVar(id, val, type);
    //             break;
    //         default:
    //             System.out.println("more needs to be added.");
    //     }
    // }

    public void writeGlobalVar(String id, String type, String val, boolean isStatic){
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
        writeAssembly(AssemblyMsg.GLOBAL_LABEL, id);
        increaseIndent();

        //if noVAl is true, there's no value to initialize
        if(noVal){
            writeAssembly(AssemblyMsg.SKIP_4);
        }
        else{
            switch(type){
                case "int":
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

    // public void writeInitGlobalFloats(String id, String val){
    //     writeAssembly(AssemblyMsg.NEWLINE);
    //     increaseIndent();
    //     writeAssembly(AssemblyMsg.DATA);
    //     writeAssembly(AssemblyMsg.ALIGN_4);
    //     writeAssembly(AssemblyMsg.DOT_GLOBAL, id); // REPLACE WITH VAR NAME
    //     decreaseIndent();
    //     writeAssembly(AssemblyMsg.GLOBAL_LABEL, id); //REPLACE WITH VAR NAME
    //     increaseIndent();
    //     writeAssembly(AssemblyMsg.DOT_SINGLE, val); // REPLACE WITH ACTUAL VALUE
    //     writeAssembly(AssemblyMsg.TEXT);
    //     writeAssembly(AssemblyMsg.ALIGN_4);
    //     decreaseIndent();
    // }
    

    
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