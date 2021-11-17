//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramatica.y"
package AnalizadorSintactico;
import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.acciones_semanticas.AccionSemantica;
import Utils.ErrorLog;

//#line 27 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short WHILE=257;
public final static short DO=258;
public final static short IF=259;
public final static short THEN=260;
public final static short ELSE=261;
public final static short ENDIF=262;
public final static short PRINT=263;
public final static short FUNC=264;
public final static short RETURN=265;
public final static short BEGIN=266;
public final static short END=267;
public final static short BREAK=268;
public final static short POST=269;
public final static short MENOR_IGUAL=270;
public final static short MAYOR_IGUAL=271;
public final static short DISTINTO=272;
public final static short IGUAL=273;
public final static short ASIGN=274;
public final static short INT=275;
public final static short SINGLE=276;
public final static short STRING=277;
public final static short TYPEDEF=278;
public final static short AND=279;
public final static short OR=280;
public final static short ID=281;
public final static short CONST_SINGLE=282;
public final static short CONST_INT=283;
public final static short CONST_STR=284;
public final static short PROGRAM=285;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    1,    1,    2,    2,    4,    4,    4,
    4,    4,    5,    5,    5,    5,    9,    9,    6,    6,
    6,    7,    7,    3,    3,    3,    8,    8,    8,    8,
   13,   13,   15,   15,   15,   15,   15,   15,   15,   10,
   10,   14,   14,   14,   14,   16,   16,   17,   17,   18,
   18,   21,   21,   23,   23,   23,   23,   24,   24,   24,
   24,   24,   22,   22,   22,   19,   19,   19,   19,   25,
   25,   25,   26,   26,   26,   27,   27,   27,   28,   28,
   28,   28,   28,   28,   11,   11,   11,   12,   12,   12,
   12,   12,   12,   12,   12,   12,   20,   20,   20,   29,
   29,   29,   30,   30,   30,   30,   30,   30,
};
final static short yylen[] = {                            2,
    3,    2,    3,    3,    2,    1,    2,    3,    9,    5,
    4,    2,    1,    1,    1,    1,    5,    4,    1,    3,
    2,    2,    4,    4,    3,    2,    6,    5,    3,    4,
    1,    4,    1,    2,    4,    6,    6,    7,    5,    1,
    2,    2,    2,    2,    2,    3,    3,    4,    3,    1,
    1,    1,    1,    7,    6,    6,    6,    9,    8,    8,
    8,    8,    6,    5,    5,    4,    4,    4,    4,    3,
    1,    2,    3,    1,    2,    3,    1,    2,    1,    1,
    1,    1,    1,    1,    5,    2,    2,    8,    7,    7,
    7,    7,    7,    7,    7,    7,    3,    3,    1,    3,
    3,    1,    1,    1,    1,    2,    2,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,   13,   14,   15,    0,
    0,    0,    2,    6,    0,    4,    3,    0,    0,    0,
    0,    0,    0,    0,   40,    0,    0,    0,    0,   50,
   51,   52,   53,    0,    0,    1,    7,    0,   19,   12,
    0,    0,    0,    0,   25,    0,    0,    0,  104,  105,
    0,  108,    0,    0,    0,    0,    0,  102,    0,   41,
   42,   43,   44,   45,    0,    0,   16,    0,    0,    0,
    8,    0,    0,    0,    0,    0,    0,    0,   47,    0,
   49,    0,    0,  106,  107,    0,    0,    0,    0,    0,
   81,   82,   83,   84,   79,   80,    0,    0,    0,   24,
    0,    0,    0,   11,    0,   20,    0,    0,   67,   69,
   68,   66,   22,   48,    0,    0,    0,    0,    0,    0,
    0,    0,  100,  101,   10,    0,    0,    0,    0,    0,
    0,   33,   65,    0,    0,    0,   31,    0,   64,    0,
   18,    0,    0,   63,    0,    0,   34,    0,    0,    0,
   56,   23,    0,   55,   17,    0,    0,    0,    0,    0,
   54,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   35,    0,    0,   61,   32,   60,   59,    0,    9,
    0,    0,   39,    0,    0,    0,   58,    0,   86,    0,
    0,    0,   37,   36,    0,    0,    0,    0,    0,    0,
   30,   38,   28,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   27,   85,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   90,   92,   94,    0,   95,   93,   91,   89,
   88,
};
final static short yydgoto[] = {                          2,
    3,   12,   13,   14,   15,   41,   83,  168,   69,   24,
  182,  199,  136,   25,  133,   26,   27,   28,   29,   53,
   30,   31,   32,   33,   54,   55,   56,   97,   57,   58,
};
final static short yysindex[] = {                      -262,
 -238,    0, -145,  -28,   21,   34,    0,    0,    0, -246,
   28, -145,    0,    0,  -57,    0,    0,   55,   62,   80,
   64,   -1,   95,   39,    0,   70,   78,   79,   93,    0,
    0,    0,    0,   85, -204,    0,    0, -124,    0,    0,
  -23,   95,   95, -214,    0,  141,   83,  130,    0,    0,
 -219,    0,   60,   20, -108,  177,  -18,    0,  114,    0,
    0,    0,    0,    0, -204,  134,    0,  -87,  131,  151,
    0,  -89,   24,   44,  153,  154,  155,  167,    0,   60,
    0,  -71,   37,    0,    0,   95,   95,   95, -150,   95,
    0,    0,    0,    0,    0,    0,   95,   95,   95,    0,
  152, -193,  174,    0, -193,    0,  -30,   18,    0,    0,
    0,    0,    0,    0, -193,  -18,  -18, -108,   26,   46,
  177,   60,    0,    0,    0,  171, -193,   52,   26,   51,
  156,    0,    0,   46,   94, -186,    0,  -64,    0, -147,
    0,  184, -109,    0,  181,   -2,    0,    1,   54,   46,
    0,    0,   46,    0,    0,   -7,   59,  213,  221,   46,
    0,  -32,  231,   99,  104,   94,  -39,  274,   66,  315,
   71,    0,   82,  126,    0,    0,    0,    0,   66,    0,
  -40,  129,    0,  338,  339,   87,    0,  -46,    0,   95,
   60,  343,    0,    0,  345,  346,  -24,  366,  149,   12,
    0,    0,    0,   95,  -37,   95,  349,  358,   68,  -34,
   75,   76,    0,    0,  377,  382,  -26,  384,  390,  157,
  162,  -31,  169,  170,  173,  376,  378,  379,  -53,  396,
  397,  402,    0,    0,    0,  404,    0,    0,    0,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0, -127,    0,    0,    0,    0,    0,
  -55,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  436,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  100,    0,    0,
    0,    0,  172,    0,   -4,    7,  120,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -14,    0,    0,    0,    0,    0,    0,    0,  405,
    0,    0,    0,    0,    0,    0,    0,    8,    0,   57,
    0,    0,    0,    0,    0,    0,  321,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  140,  160,   15,    0,    0,
   63,  341,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  406,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -55,    0,    0,    0,
    0,    0,    0,   78,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  407,    0,    0,
  117,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  200,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,  325,  460,   -3,  -15,    0,  368,    0,  409,  -38,
  296,    0, -106,  370, -102,    0,  372,    0,    0,   16,
    0,    0,    0,    0,  -17,  388,  389,    0,   69,   27,
};
final static int YYTABLESIZE=621;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        190,
   47,   40,  210,   16,   51,  237,  216,   51,   37,   23,
   51,  198,  229,  140,  222,  204,  139,  223,  189,   68,
   72,   35,    1,   98,   73,   74,  144,  148,   99,   21,
   16,   82,   23,  205,   34,   71,   71,   23,   47,   71,
   23,  162,    4,  164,   21,    5,  165,   74,   72,   68,
   74,   72,  208,  174,   87,   70,   86,   23,   70,   66,
   89,   80,   84,   85,  107,   23,   75,   76,   77,   78,
    7,    8,    9,   23,  150,  151,   67,  114,   23,   17,
  115,    7,    8,    9,  108,   23,  126,   67,   35,   82,
   23,  146,  143,   23,   42,  115,  149,   75,   23,  138,
   75,   43,   87,   73,   86,   23,   73,  119,  215,  120,
   23,  142,  122,  153,  154,  218,  219,  169,  171,   44,
    6,   23,   45,   81,  123,  124,   23,  179,   61,    7,
    8,    9,   10,   23,  186,   11,   62,   63,    5,   51,
  103,  103,  103,  103,  103,   65,  103,    5,    5,    5,
    5,   64,   37,    5,  116,  117,   70,  103,  103,  103,
   99,  103,   99,   99,   99,    7,    8,    9,   10,   47,
   90,   11,  100,  102,   87,   87,  103,   99,   99,   99,
   98,   99,   98,   98,   98,   51,  209,  211,  212,  104,
  105,  106,  217,  109,  110,  111,  191,   98,   98,   98,
   97,   98,   97,   97,   97,  200,   38,  112,   16,  113,
  125,  141,   77,  127,  147,   77,  152,   97,   97,   97,
  196,   97,  197,   39,  155,   16,   18,  129,   19,  175,
  236,   77,   20,   77,   46,  130,   95,  131,   96,  157,
   48,   49,   50,   48,   49,   50,   48,   49,   50,   18,
   22,   19,  228,   88,   18,   20,   19,   18,  166,   19,
   20,  160,  161,   20,  158,  159,  135,    7,    8,    9,
   10,  172,   46,  167,   18,   71,   19,  134,   22,  173,
   20,   22,   18,  135,   19,   74,   74,   72,   20,  176,
   18,  130,   19,  131,   70,   18,   20,   19,   22,   88,
   21,   20,   18,   88,   19,   59,   22,   18,   20,   19,
   18,  135,   19,   20,   22,   18,   20,   19,  145,   22,
  163,   20,   18,   88,   19,  170,   22,   18,   20,   19,
  181,   22,  180,   20,   22,   75,   75,  184,   18,   22,
   19,   73,   73,   18,   20,   19,   22,   88,  185,   20,
   18,   22,   19,  195,   88,   88,   20,    7,    8,    9,
  177,   78,   22,   67,   78,  178,  103,   22,  103,  103,
  103,  103,  103,  183,   22,   48,   49,   50,  103,  103,
   78,   76,   78,   87,   76,   87,   99,  187,   99,   99,
   99,   99,   99,   60,   52,  192,  193,  194,   99,   99,
   76,  201,   76,  202,  203,  206,   98,  213,   98,   98,
   98,   98,   98,   52,   52,  207,  214,   52,   98,   98,
  220,   48,   49,   50,   79,  221,   97,  224,   97,   97,
   97,   97,   97,  225,  233,   26,  234,  235,   97,   97,
  226,   77,   77,   77,   77,  227,   91,   92,   93,   94,
   77,   77,  230,  231,  238,  239,  232,   52,   52,   52,
  240,   52,  241,   46,   57,   29,   96,  156,   52,   52,
   52,   36,  128,  101,  188,  118,  132,  137,  121,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  132,  137,
    0,    0,    0,    0,    0,    0,    0,    0,  132,    0,
    0,    0,    0,  137,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   60,    0,  137,   60,  137,
    0,    0,  137,    0,    0,    0,    0,    0,    0,  137,
    0,    0,    0,    0,    0,    0,    0,    0,   60,    0,
   60,    0,    0,    0,    0,    0,    0,    0,   60,    0,
    0,    0,   52,    0,    0,   60,    0,    0,    0,    0,
    0,   52,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   52,   52,   52,    0,    0,
    0,   52,    0,    0,    0,    0,    0,    0,    0,    0,
   78,   78,   78,   78,    0,    0,    0,    0,    0,   78,
   78,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   76,   76,   76,   76,    0,    0,    0,    0,    0,   76,
   76,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   40,   59,   40,   59,   45,   59,   41,   45,   12,   40,
   45,   58,   44,  120,   41,   40,  119,   44,   59,   35,
   44,   61,  285,   42,   42,   43,  129,  134,   47,   44,
   59,   47,   40,   58,  281,   59,   41,   40,   40,   44,
   40,  148,  281,  150,   59,  284,  153,   41,   41,   65,
   44,   44,   41,  160,   43,   41,   45,   40,   44,  264,
   41,   46,  282,  283,   41,   40,  281,  282,  283,  284,
  275,  276,  277,   40,  261,  262,  281,   41,   40,   59,
   44,  275,  276,  277,   41,   40,  102,  281,   61,  105,
   40,  130,   41,   40,   40,   44,  135,   41,   40,  115,
   44,   40,   43,   41,   45,   40,   44,  258,   41,  260,
   40,  127,   97,  261,  262,   41,   41,  156,  157,   40,
  266,   40,   59,   41,   98,   99,   40,  166,   59,  275,
  276,  277,  278,   40,  173,  281,   59,   59,  266,   45,
   41,   42,   43,   44,   45,   61,   47,  275,  276,  277,
  278,   59,  156,  281,   86,   87,  281,   58,   59,   60,
   41,   62,   43,   44,   45,  275,  276,  277,  278,   40,
  279,  281,   59,   40,   58,   59,  264,   58,   59,   60,
   41,   62,   43,   44,   45,   45,  204,  205,  206,   59,
   40,  281,  210,   41,   41,   41,  181,   58,   59,   60,
   41,   62,   43,   44,   45,  190,  264,   41,  264,  281,
   59,   41,   41,   40,   59,   44,  281,   58,   59,   60,
  267,   62,  269,  281,   41,  281,  257,  258,  259,  262,
  284,   60,  263,   62,  274,  266,   60,  268,   62,   59,
  281,  282,  283,  281,  282,  283,  281,  282,  283,  257,
  281,  259,  284,  280,  257,  263,  259,  257,  266,  259,
  263,  261,  262,  263,  267,  268,  266,  275,  276,  277,
  278,   59,  274,  281,  257,  280,  259,  260,  281,   59,
  263,  281,  257,  266,  259,  279,  280,  280,  263,   59,
  257,  266,  259,  268,  280,  257,  263,  259,  281,  280,
  267,  263,  257,  280,  259,  267,  281,  257,  263,  259,
  257,  266,  259,  263,  281,  257,  263,  259,  268,  281,
  267,  263,  257,  280,  259,  267,  281,  257,  263,  259,
  265,  281,   59,  263,  281,  279,  280,  267,  257,  281,
  259,  279,  280,  257,  263,  259,  281,  280,  267,  263,
  257,  281,  259,  267,  280,  280,  263,  275,  276,  277,
  262,   41,  281,  281,   44,  262,  267,  281,  269,  270,
  271,  272,  273,   59,  281,  281,  282,  283,  279,  280,
   60,   41,   62,  267,   44,  269,  267,  262,  269,  270,
  271,  272,  273,   24,   23,  267,   59,   59,  279,  280,
   60,   59,   62,   59,   59,   40,  267,   59,  269,  270,
  271,  272,  273,   42,   43,  267,   59,   46,  279,  280,
   44,  281,  282,  283,  284,   44,  267,   44,  269,  270,
  271,  272,  273,   44,   59,    0,   59,   59,  279,  280,
  284,  270,  271,  272,  273,  284,  270,  271,  272,  273,
  279,  280,  284,  284,   59,   59,  284,   86,   87,   88,
   59,   90,   59,   59,   59,   59,  267,  143,   97,   98,
   99,   12,  105,   65,  179,   88,  107,  108,   90,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  119,  120,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  129,   -1,
   -1,   -1,   -1,  134,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  146,   -1,  148,  149,  150,
   -1,   -1,  153,   -1,   -1,   -1,   -1,   -1,   -1,  160,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  169,   -1,
  171,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  179,   -1,
   -1,   -1,  181,   -1,   -1,  186,   -1,   -1,   -1,   -1,
   -1,  190,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  204,  205,  206,   -1,   -1,
   -1,  210,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  270,  271,  272,  273,   -1,   -1,   -1,   -1,   -1,  279,
  280,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  270,  271,  272,  273,   -1,   -1,   -1,   -1,   -1,  279,
  280,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=285;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,"':'","';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,"WHILE","DO","IF","THEN","ELSE","ENDIF","PRINT",
"FUNC","RETURN","BEGIN","END","BREAK","POST","MENOR_IGUAL","MAYOR_IGUAL",
"DISTINTO","IGUAL","ASIGN","INT","SINGLE","STRING","TYPEDEF","AND","OR","ID",
"CONST_SINGLE","CONST_INT","CONST_STR","PROGRAM",
};
final static String yyrule[] = {
"$accept : programa",
"programa : nombre_programa bloque_declarativas bloque_ejecutables_programa",
"programa : nombre_programa bloque_ejecutables_programa",
"nombre_programa : PROGRAM CONST_STR ';'",
"nombre_programa : PROGRAM ID ';'",
"nombre_programa : PROGRAM CONST_STR",
"bloque_declarativas : sentencia_declarativa",
"bloque_declarativas : bloque_declarativas sentencia_declarativa",
"sentencia_declarativa : tipo lista_variables ';'",
"sentencia_declarativa : tipo FUNC ID '(' parametro ')' bloque_declarativas bloque_ejecutables_funcion ';'",
"sentencia_declarativa : TYPEDEF ID '=' encabezado_funcion ';'",
"sentencia_declarativa : ID '=' encabezado_funcion ';'",
"sentencia_declarativa : tipo ';'",
"tipo : INT",
"tipo : SINGLE",
"tipo : STRING",
"tipo : ID",
"encabezado_funcion : tipo FUNC '(' tipo ')'",
"encabezado_funcion : FUNC '(' tipo ')'",
"lista_variables : ID",
"lista_variables : lista_variables ',' ID",
"lista_variables : lista_variables ','",
"parametro : tipo ID",
"parametro : parametro ',' tipo ID",
"bloque_ejecutables_programa : BEGIN sentencias_ejecutables END ';'",
"bloque_ejecutables_programa : BEGIN END ';'",
"bloque_ejecutables_programa : BEGIN END",
"bloque_ejecutables_funcion : BEGIN sentencias_ejecutables retorno post_condicion END ';'",
"bloque_ejecutables_funcion : BEGIN sentencias_ejecutables retorno END ';'",
"bloque_ejecutables_funcion : BEGIN sentencias_ejecutables retorno",
"bloque_ejecutables_funcion : sentencias_ejecutables retorno END ';'",
"bloque_ejecutables_if : sentencia_ejecutable",
"bloque_ejecutables_if : BEGIN sentencias_ejecutables END ';'",
"bloque_ejecutables_while : sentencia_ejecutable",
"bloque_ejecutables_while : BREAK ';'",
"bloque_ejecutables_while : BEGIN sentencias_ejecutables END ';'",
"bloque_ejecutables_while : BEGIN sentencias_ejecutables BREAK ';' END ';'",
"bloque_ejecutables_while : BEGIN BREAK ';' sentencias_ejecutables END ';'",
"bloque_ejecutables_while : BEGIN sentencias_ejecutables BREAK ';' sentencias_ejecutables END ';'",
"bloque_ejecutables_while : BEGIN BREAK ';' END ';'",
"sentencias_ejecutables : sentencia_ejecutable",
"sentencias_ejecutables : sentencias_ejecutables sentencia_ejecutable",
"sentencia_ejecutable : asignacion ';'",
"sentencia_ejecutable : invocacion_funcion ';'",
"sentencia_ejecutable : estructura_control ';'",
"sentencia_ejecutable : impresion_pantalla ';'",
"asignacion : ID ASIGN expresion_aritmetica",
"asignacion : ID ASIGN CONST_STR",
"invocacion_funcion : ID '(' parametro ')'",
"invocacion_funcion : ID '(' ')'",
"estructura_control : sentencia_if",
"estructura_control : sentencia_while",
"sentencia_if : if_simple",
"sentencia_if : if_completo",
"if_simple : IF '(' condicion ')' THEN bloque_ejecutables_if ENDIF",
"if_simple : '(' condicion ')' THEN bloque_ejecutables_if ENDIF",
"if_simple : IF '(' condicion ')' bloque_ejecutables_if ENDIF",
"if_simple : IF '(' condicion ')' THEN bloque_ejecutables_if",
"if_completo : IF '(' condicion ')' THEN bloque_ejecutables_if ELSE bloque_ejecutables_if ENDIF",
"if_completo : '(' condicion ')' THEN bloque_ejecutables_if ELSE bloque_ejecutables_if ENDIF",
"if_completo : IF '(' condicion ')' bloque_ejecutables_if ELSE bloque_ejecutables_if ENDIF",
"if_completo : IF '(' condicion ')' THEN bloque_ejecutables_if bloque_ejecutables_if ENDIF",
"if_completo : IF '(' condicion ')' THEN bloque_ejecutables_if ELSE bloque_ejecutables_if",
"sentencia_while : WHILE '(' condicion ')' DO bloque_ejecutables_while",
"sentencia_while : '(' condicion ')' DO bloque_ejecutables_while",
"sentencia_while : WHILE '(' condicion ')' bloque_ejecutables_while",
"impresion_pantalla : PRINT '(' CONST_STR ')'",
"impresion_pantalla : PRINT '(' ID ')'",
"impresion_pantalla : PRINT '(' CONST_INT ')'",
"impresion_pantalla : PRINT '(' CONST_SINGLE ')'",
"condicion : condicion OR expresion_booleana",
"condicion : expresion_booleana",
"condicion : condicion OR",
"expresion_booleana : expresion_booleana AND comparacion",
"expresion_booleana : comparacion",
"expresion_booleana : expresion_booleana AND",
"comparacion : comparacion comparador expresion_aritmetica",
"comparacion : expresion_aritmetica",
"comparacion : comparacion comparador",
"comparador : '<'",
"comparador : '>'",
"comparador : MENOR_IGUAL",
"comparador : MAYOR_IGUAL",
"comparador : DISTINTO",
"comparador : IGUAL",
"retorno : RETURN '(' expresion_aritmetica ')' ';'",
"retorno : RETURN ';'",
"retorno : RETURN expresion_aritmetica",
"post_condicion : POST ':' '(' condicion ')' ',' CONST_STR ';'",
"post_condicion : ':' '(' condicion ')' ',' CONST_STR ';'",
"post_condicion : POST '(' condicion ')' ',' CONST_STR ';'",
"post_condicion : POST ':' condicion ')' ',' CONST_STR ';'",
"post_condicion : POST ':' '(' ')' ',' CONST_STR ';'",
"post_condicion : POST ':' '(' condicion ',' CONST_STR ';'",
"post_condicion : POST ':' '(' condicion ')' CONST_STR ';'",
"post_condicion : POST ':' '(' condicion ')' ',' ';'",
"post_condicion : POST ':' '(' condicion ')' ',' CONST_STR",
"expresion_aritmetica : expresion_aritmetica '+' termino",
"expresion_aritmetica : expresion_aritmetica '-' termino",
"expresion_aritmetica : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : CONST_SINGLE",
"factor : CONST_INT",
"factor : '-' CONST_SINGLE",
"factor : '-' CONST_INT",
"factor : invocacion_funcion",
};

//#line 192 "gramatica.y"

	private AnalizadorLexico lexico;
	private ErrorLog errors;

	public Parser (AnalizadorLexico aLexico, ErrorLog error) {
	    this.lexico = aLexico;
	    this.errors = error;
	}

    private int yylex() {
        int tokenGenerated = lexico.generateToken();
        yylval = new ParserVal(lexico.getLexemaObtenido());

        return tokenGenerated;
    }

    private void yyerror(String msg){
        errors.addError(msg);
    }


    private void checkNegative(double number){
        if( !(number >= AccionSemantica.GeneratorFloat.MIN_NEGATIVE && number <= AccionSemantica.GeneratorFloat.MAX_NEGATIVE) )
            yyerror("Single fuera de rango negativo");
    }
    private void checkPositiv(double number){
        if( !( (number >= AccionSemantica.GeneratorFloat.MIN_POSITIV && number <= AccionSemantica.GeneratorFloat.MAX_POSITIV) || number == AccionSemantica.GeneratorFloat.CERO) )
            yyerror("Single fuera de rango positivo");
    }
    private void isInRange(String number, boolean negative){
    	if(negative)
    	   number = "-" + number;
        if(number.contains(".")){
            double numberd = Double.parseDouble(number);
            if(!negative)
                checkPositiv(numberd);
            else
                checkNegative(numberd);
	    }
        else{
           int numberi = Integer.parseInt(number);
           if( !(numberi >= AccionSemantica.GeneratorInteger.MIN && numberi <= AccionSemantica.GeneratorInteger.MAX) )
            yyerror("Entero fuera de rango");
            }
    }

    private void print(String text) {
        System.out.println(text);
    }
    
    private void generated(String gen) {
        this.lexico.generated(gen);
    }
//#line 572 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 26 "gramatica.y"
{System.err.println("Se reconocio el programa");}
break;
case 2:
//#line 27 "gramatica.y"
{System.err.println("Se reconocio el programa");}
break;
case 3:
//#line 30 "gramatica.y"
{ this.generated("nombre de programa"); }
break;
case 4:
//#line 31 "gramatica.y"
{ this.yyerror("El nombre del programa debe ser un STRING no un ID"); }
break;
case 5:
//#line 32 "gramatica.y"
{yyerror("Falta el ; al final del nombre del programa");}
break;
case 6:
//#line 35 "gramatica.y"
{ this.generated("bloque declarativo"); }
break;
case 7:
//#line 36 "gramatica.y"
{ this.generated("bloque declarativo"); }
break;
case 8:
//#line 38 "gramatica.y"
{ this.generated("sentencia declarativa"); }
break;
case 9:
//#line 39 "gramatica.y"
{ this.generated("sentencia declarativa"); }
break;
case 10:
//#line 40 "gramatica.y"
{ this.generated("sentencia declarativa"); }
break;
case 11:
//#line 41 "gramatica.y"
{yyerror("Falta la palabra reservada TYPEDEF");}
break;
case 12:
//#line 42 "gramatica.y"
{yyerror("Falta el ID");}
break;
case 16:
//#line 46 "gramatica.y"
{ this.generated("tipo de variable"); }
break;
case 17:
//#line 49 "gramatica.y"
{ this.generated("encabezado de funcion"); }
break;
case 18:
//#line 50 "gramatica.y"
{yyerror("Falta el tipo de la funcion");}
break;
case 19:
//#line 53 "gramatica.y"
{ this.generated("lista de variables"); }
break;
case 20:
//#line 54 "gramatica.y"
{ this.generated("lista de variables"); }
break;
case 21:
//#line 55 "gramatica.y"
{yyerror("Se esperaba un ID");}
break;
case 22:
//#line 58 "gramatica.y"
{ this.generated("parametro"); }
break;
case 23:
//#line 59 "gramatica.y"
{ this.yyerror("Una funcion no puede tener mas de un parametro"); }
break;
case 24:
//#line 62 "gramatica.y"
{ this.generated("bloque ejecutable"); }
break;
case 26:
//#line 64 "gramatica.y"
{yyerror("Falta el ; al final del END");}
break;
case 29:
//#line 69 "gramatica.y"
{yyerror("Falta el END");}
break;
case 30:
//#line 70 "gramatica.y"
{yyerror("Falta el BEGIN");}
break;
case 31:
//#line 73 "gramatica.y"
{ this.generated("IF de una linea"); }
break;
case 32:
//#line 74 "gramatica.y"
{ this.generated("IF"); }
break;
case 33:
//#line 77 "gramatica.y"
{ this.generated("WHILE de una linea"); }
break;
case 35:
//#line 79 "gramatica.y"
{ this.generated("WHILE"); }
break;
case 37:
//#line 81 "gramatica.y"
{ this.yyerror("Faltan las sentencias ejecutables"); }
break;
case 38:
//#line 82 "gramatica.y"
{ this.yyerror("No se esperaban sentencias ejecutables despues del break"); }
break;
case 39:
//#line 83 "gramatica.y"
{ this.yyerror("Faltan las sentencias ejecutables"); }
break;
case 40:
//#line 86 "gramatica.y"
{ this.generated("sentencias ejecutable"); }
break;
case 41:
//#line 87 "gramatica.y"
{ this.generated("sentencias ejecutable"); }
break;
case 42:
//#line 90 "gramatica.y"
{ this.generated("sentencia ejecutable"); }
break;
case 43:
//#line 91 "gramatica.y"
{ this.generated("sentencia ejecutable"); }
break;
case 44:
//#line 92 "gramatica.y"
{ this.generated("sentencia ejecutable"); }
break;
case 45:
//#line 93 "gramatica.y"
{ this.generated("sentencia ejecutable"); }
break;
case 46:
//#line 96 "gramatica.y"
{ this.generated("asignacion"); }
break;
case 48:
//#line 100 "gramatica.y"
{ this.generated("invocacion a funcion"); }
break;
case 49:
//#line 101 "gramatica.y"
{yyerror("Falta el parametro de la funcion");}
break;
case 50:
//#line 104 "gramatica.y"
{ this.generated("estructura de control"); }
break;
case 51:
//#line 105 "gramatica.y"
{ this.generated("estructura de control"); }
break;
case 55:
//#line 113 "gramatica.y"
{yyerror("Falta la palabra reservada IF");}
break;
case 56:
//#line 114 "gramatica.y"
{yyerror("Falta la palabra reservada THEN");}
break;
case 57:
//#line 115 "gramatica.y"
{yyerror("Falta la palabra reservada ENDIF");}
break;
case 59:
//#line 119 "gramatica.y"
{yyerror("Falta la palabra reservada IF");}
break;
case 60:
//#line 120 "gramatica.y"
{yyerror("Falta la palabra reservada THEN");}
break;
case 61:
//#line 121 "gramatica.y"
{yyerror("Falta la palabra reservada ELSE");}
break;
case 62:
//#line 122 "gramatica.y"
{yyerror("Falta la palabra reservada ENDIF");}
break;
case 64:
//#line 126 "gramatica.y"
{yyerror("Falta la palabra reservada WHILE");}
break;
case 65:
//#line 127 "gramatica.y"
{yyerror("Falta la palabra reservada DO");}
break;
case 66:
//#line 130 "gramatica.y"
{ this.generated("impresion por pantalla"); print(val_peek(1).sval); }
break;
case 68:
//#line 132 "gramatica.y"
{print(String.valueOf(val_peek(1).ival));}
break;
case 69:
//#line 133 "gramatica.y"
{print(String.valueOf(val_peek(1).dval));}
break;
case 72:
//#line 138 "gramatica.y"
{ this.yyerror("Falta otra condicion despues del '||'"); }
break;
case 75:
//#line 143 "gramatica.y"
{ this.yyerror("Falta otra condicion despues del '&&'"); }
break;
case 78:
//#line 148 "gramatica.y"
{ this.yyerror("Falta expresion con la cual comparar"); }
break;
case 85:
//#line 155 "gramatica.y"
{ this.generated("RETURN"); }
break;
case 86:
//#line 156 "gramatica.y"
{ this.yyerror("Falta la expresion a retornar"); }
break;
case 87:
//#line 157 "gramatica.y"
{ this.yyerror("Falta un ';' al final de la sentencia"); }
break;
case 88:
//#line 160 "gramatica.y"
{ this.generated("POST condition"); }
break;
case 89:
//#line 161 "gramatica.y"
{ this.yyerror("Se esperaba POST al principio de la sentencia"); }
break;
case 90:
//#line 162 "gramatica.y"
{ this.yyerror("Falta ':' despues de POST"); }
break;
case 91:
//#line 163 "gramatica.y"
{ this.yyerror("Se esperaba '(' despues de ':'"); }
break;
case 92:
//#line 164 "gramatica.y"
{ this.yyerror("Falta la condicion del POST"); }
break;
case 93:
//#line 165 "gramatica.y"
{ this.yyerror("Se esperaba ')' despues de la condicion"); }
break;
case 94:
//#line 166 "gramatica.y"
{ this.yyerror("Se esperaba ',' despues de la condicion"); }
break;
case 95:
//#line 167 "gramatica.y"
{ this.yyerror("Falta el string despues de ','"); }
break;
case 96:
//#line 168 "gramatica.y"
{ this.yyerror("Se esperaba ';' al final de la linea"); }
break;
case 104:
//#line 183 "gramatica.y"
{ this.isInRange(val_peek(0).sval,false);}
break;
case 105:
//#line 184 "gramatica.y"
{ this.isInRange(val_peek(0).sval,false);}
break;
case 106:
//#line 185 "gramatica.y"
{this.isInRange(val_peek(0).sval,true);}
break;
case 107:
//#line 186 "gramatica.y"
{this.isInRange(val_peek(0).sval,true);}
break;
//#line 1013 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
