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
        "\n" +
        "/*\n" +
        " * Generated %s\n" + 
        " */\n\n" +
        "\n";

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
    	".skip   " + SEPARATOR + "%s\n";

    //initialized global and static ints
    public static final String DOT_WORD = 
    	".word   " + SEPARATOR + "%s\n";

    //initialized global and static floats
	public static final String DOT_SINGLE = 
    	".single " + SEPARATOR + "0r%s\n"; 


    /////////////////////////////////////////////////////////////////
    // Starter vars are initialized here.
    /////////////////////////////////////////////////////////////////
    public static final String SAVE =
        "save    " + SEPARATOR + "%s, %s, %s\n";

    /////////////////////////////////////////////////////////////////
    // Function specific stuff here.
    /////////////////////////////////////////////////////////////////

    //Message for when we write a parameter
    public static final String PARAM_MSG = 
        "! Store params\n";

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
    public static final String ONE_VAL = "%s\n";
    public static final String TWO_VALS = "%s, %s\n";
    public static final String THREE_VALS = "%s, %s, %s\n";

    /////////////////////////////////////////////////////////////////
    // The comment for the local int initialization is here.
    /////////////////////////////////////////////////////////////////
    public static final String LOCAL_INIT_MSG = "! %s = %s\n";

    /////////////////////////////////////////////////////////////////
    // Operations strings here.
    /////////////////////////////////////////////////////////////////
    public static final String ADD_MSG       = "! (%s)+(%s)\n";
    public static final String SUB_MSG       = "! (%s)-(%s)\n";
    public static final String MUL_MSG       = "! (%s)*(%s)\n";
    public static final String DIV_MSG       = "! (%s)/(%s)\n";
    public static final String MOD_MSG       = "! (%s) mod (%s)\n";
    public static final String XOR_MSG       = "! (%s)^(%s)\n";
    public static final String OR_MSG        = "! (%s)|(%s)\n";
    public static final String AND_MSG       = "! (%s)&(%s)\n";
    public static final String UNARYNEG_MSG  = "! -(%s)\n";
    public static final String UNARYPOS_MSG  = "! +(%s)\n";

    public static final String PREINC_MSG    = "! ++(%s)\n";
    public static final String POSTINC_MSG   = "! (%s)++\n";
    public static final String PREDEC_MSG    = "! --(%s)\n";
    public static final String POSTDEC_MSG   = "! (%s)--\n";

    /////////////////////////////////////////////////////////////////
    // Ops Initialized Here
    /////////////////////////////////////////////////////////////////
    public static final String ST_OP        = "st  ";
    public static final String SET_OP       = "set  ";
    public static final String ADD_OP       = "add  ";
    public static final String SUB_OP       = "sub  ";
    public static final String MUL_OP       = ".mul";
    public static final String DIV_OP       = ".div";
    public static final String MOD_OP       = ".rem";
    public static final String XOR_OP       = "xor ";
    public static final String OR_OP        = "or  ";
    public static final String AND_OP       = "and ";
    public static final String UNARY_OP     = "neg ";
    public static final String LD_OP        = "ld  ";
    public static final String MOV_OP       = "mov ";
    public static final String CMP_OP       = "cmp ";
    public static final String BE_OP        = "be  ";
    public static final String BL_OP        = "bl  ";
    public static final String BGE_OP        = "bge ";
    public static final String BNE_OP        = "bne ";


    public static final String FADDS_OP     = "fadds";
    public static final String FSUBS_OP     = "fsubs";
    public static final String FMULS_OP     = "fmuls";
    public static final String FDIVS_OP     = "fdivs";

    /////////////////////////////////////////////////////////////////
    // Cout stuff here
    /////////////////////////////////////////////////////////////////

    // messages used for cout
    public static final String COUT_ENDL = "! cout << endl\n";
    public static final String COUT_COMMENT = "! cout << %s\n";
    // public static final String COUT_BOOL = "! cout << %s\n";

    // function consts
    public static final String PRINTF = "printf";
}















