//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// Created by Max Takano and Vivek Venugopal
//---------------------------------------------------------------------

/////////////////////////////////////////////////////////////////
// This static final class provides string literals for
// 131 Project 2.  These literals are meant to be used
// with the AssemblyGenerator class, provided separately, which uses
// C/C++ printf-like conventions.
/////////////////////////////////////////////////////////////////

class AssemblyMsg{

	/////////////////////////////////////////////////////////////////
    // ERROR MESSAGES ARE HERE
    /////////////////////////////////////////////////////////////////
	public static final String ERROR_IO_CLOSE = 
        "Unable to close fileWriter";
    public static final String ERROR_IO_CONSTRUCT = 
        "Unable to construct FileWriter for file %s";
    public static final String ERROR_IO_WRITE = 
        "Unable to write to fileWriter";

    /////////////////////////////////////////////////////////////////
    // Messages used in general everywhere in the rc.s file are here
    /////////////////////////////////////////////////////////////////
    public static final String SEPARATOR = 
        "\t";

    public static final String ASCIZ = 
    	".asciz  "+ SEPARATOR + "\"" + "%s" + "\"\n";

    public static final String ALIGN_4 = 
    	".align  "+ SEPARATOR + "4\n";

    //possibly unnecessary
    public static final String NEWLINE = 
    	"\n";

    //Label (used for method names, globals, etc.)
    public static final String LABEL =
        "%s" + ":\n";

    //Nops
    public static final String NOP = 
        "nop\n";

    //ret
    public static final String RET = 
        "ret\n";

    //restore
    public static final String RESTORE =
        "restore\n";

    /////////////////////////////////////////////////////////////////
    // FILE HEADER FOR THE DATE ALWAYS AT THE TOP of rc.s file
    /////////////////////////////////////////////////////////////////
	public static final String FILE_HEADER = 
        "/*\n" +
        " * Generated %s\n" + 
        " */\n\n";

    /////////////////////////////////////////////////////////////////
    // SECTIONS ARE INITIALIZED HERE
    /////////////////////////////////////////////////////////////////
    public static final String RODATA =
    	".section" + SEPARATOR + "\".rodata\"\n";

	public static final String TEXT =
	   ".section" + SEPARATOR + "\".text\"\n";

	public static final String DATA =
	   ".section" + SEPARATOR + "\".data\"\n";

	public static final String BSS =
	   ".section" + SEPARATOR + "\".bss\"\n";

	public static final String HEAP =
	   ".section" + SEPARATOR + "\".heap\"\n";

	public static final String STACK =
    	".section" + SEPARATOR + "\".stack\"\n";

    /////////////////////////////////////////////////////////////////
    // Starter vars are initialized here.
    /////////////////////////////////////////////////////////////////

    public static final String STARTER_VAR_NAMES = 
    	".$$." + "%s" + ":\n"; 

    //ARRAY containting all the variables we'll be initializing at the top of the page
    public static String[] START_VAR_NAME_ARRAY = {"intFmt", "strFmt", "strTF",
    								 "strEndl", "strArrBound", "strNullPtr"};

    //ARRAY containing corresponding asciz values for the startvarname
    public static String[] START_VAR_ASCIZ_ARRAY = {"%d", "%s", "false\\0\\0\\0true",
    								  "\\n",
    								  "Index value of %d is outside legal range [0,%d).\\n",
    								  "Attempt to dereference NULL pointer.\\n"};
        
    /////////////////////////////////////////////////////////////////
    // Global Variables Initialized Here
    /////////////////////////////////////////////////////////////////

    public static final String DOT_GLOBAL = 
    	".global " + SEPARATOR + "%s\n";


    public static final String SKIP =
    	".skip   " + SEPARATOR + "%s\n\n";

    //initialized global and static ints
    public static final String DOT_WORD = 
    	".word   " + SEPARATOR + "%s\n\n";   

    //initialized global and static floats
	public static final String DOT_SINGLE = 
    	".single " + SEPARATOR + "0r%s\n\n"; 


    /////////////////////////////////////////////////////////////////
    // Starter vars are initialized here.
    /////////////////////////////////////////////////////////////////
    public static final String SAVE =
        "save    " + SEPARATOR + "%s, %s, %s";

    /////////////////////////////////////////////////////////////////
    // Function specific stuff here.
    /////////////////////////////////////////////////////////////////

    //Message for when we write a parameter
    public static final String PARAM_MSG = 
        "! Store params";

    public static final String FUNC_END =
        "!  End of function %s\n";

    public static final String FUNC_CALL = 
        "call    " + SEPARATOR + "%s\n";

    public static final String FUNC_SAVE = 
        "%s = -(92 +  %s) & -8\n";

    /////////////////////////////////////////////////////////////////
    // Param input stuff initialized
    /////////////////////////////////////////////////////////////////
    public static final String TWO_PARAM = "%s" + SEPARATOR + "%s, %s\n";
    public static final String TWO_VALS = "%s, %s";
    public static final String THREE_VALS = "%s, %s, %s";

    /////////////////////////////////////////////////////////////////
    // The comment for the local int initialization is here.
    /////////////////////////////////////////////////////////////////
    public static final String LOCAL_INIT_MSG = "! %s = %s\n";    

    /////////////////////////////////////////////////////////////////
    // Operations strings here.
    /////////////////////////////////////////////////////////////////
    public static final String ADD_MSG = "! (%s)+(%s)\n";

    /////////////////////////////////////////////////////////////////
    // Ops Initialized Here
    /////////////////////////////////////////////////////////////////
    public static final String ST_OP  = "st  ";
    public static final String SET_OP = "set  ";
    public static final String ADD_OP = "add  ";
    public static final String LD_OP  = "ld  ";
}