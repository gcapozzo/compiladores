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
import java.lang.Math;
import java.io.*;
import java.util.StringTokenizer;
import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.acciones_semanticas.GeneratorFloat;
import AnalizadorLexico.acciones_semanticas.GeneratorInteger;
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
    4,    5,    5,    5,    5,    9,    9,    6,    6,    6,
    7,    7,    3,    3,    3,    8,    8,    8,    8,   13,
   13,   15,   15,   15,   15,   15,   15,   15,   10,   10,
   14,   14,   14,   14,   16,   16,   17,   17,   18,   18,
   21,   21,   23,   23,   23,   23,   24,   24,   24,   24,
   24,   22,   22,   22,   19,   19,   19,   19,   25,   25,
   25,   26,   26,   26,   27,   27,   27,   28,   28,   28,
   28,   28,   28,   11,   11,   11,   12,   12,   12,   12,
   12,   12,   12,   12,   12,   20,   20,   20,   29,   29,
   29,   30,   30,   30,   30,   30,   30,
};
final static short yylen[] = {                            2,
    3,    2,    3,    3,    2,    1,    2,    3,    9,    5,
    4,    1,    1,    1,    1,    5,    4,    1,    3,    2,
    2,    4,    4,    3,    2,    6,    5,    3,    4,    1,
    4,    1,    2,    4,    6,    6,    7,    5,    1,    2,
    2,    2,    2,    2,    3,    3,    4,    3,    1,    1,
    1,    1,    7,    6,    6,    6,    9,    8,    8,    8,
    8,    6,    5,    5,    4,    4,    4,    4,    3,    1,
    2,    3,    1,    2,    3,    1,    2,    1,    1,    1,
    1,    1,    1,    5,    2,    2,    8,    7,    7,    7,
    7,    7,    7,    7,    7,    3,    3,    1,    3,    3,
    1,    1,    1,    1,    2,    2,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,   12,   13,   14,    0,
    0,    0,    2,    6,    0,    4,    3,    0,    0,    0,
    0,    0,    0,    0,   39,    0,    0,    0,    0,   49,
   50,   51,   52,    0,    0,    1,    7,    0,   18,    0,
    0,    0,    0,   24,    0,    0,    0,  103,  104,    0,
  107,    0,    0,    0,    0,    0,  101,    0,   40,   41,
   42,   43,   44,    0,    0,   15,    0,    0,    0,    8,
    0,    0,    0,    0,    0,    0,    0,   46,    0,   48,
    0,    0,  105,  106,    0,    0,    0,    0,    0,   80,
   81,   82,   83,   78,   79,    0,    0,    0,   23,    0,
    0,    0,   11,    0,   19,    0,    0,   66,   68,   67,
   65,   21,   47,    0,    0,    0,    0,    0,    0,    0,
    0,   99,  100,   10,    0,    0,    0,    0,    0,    0,
   32,   64,    0,    0,    0,   30,    0,   63,    0,   17,
    0,    0,   62,    0,    0,   33,    0,    0,    0,   55,
   22,    0,   54,   16,    0,    0,    0,    0,    0,   53,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   34,    0,    0,   60,   31,   59,   58,    0,    9,    0,
    0,   38,    0,    0,    0,   57,    0,   85,    0,    0,
    0,   36,   35,    0,    0,    0,    0,    0,    0,   29,
   37,   27,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   26,   84,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   89,   91,   93,    0,   94,   92,   90,   88,   87,
};
final static short yydgoto[] = {                          2,
    3,   12,   13,   14,   15,   40,   82,  167,   68,   24,
  181,  198,  135,   25,  132,   26,   27,   28,   29,   52,
   30,   31,   32,   33,   53,   54,   55,   96,   56,   57,
};
final static short yysindex[] = {                      -281,
 -261,    0, -146,  -35,  -28,   34,    0,    0,    0, -245,
  -18, -146,    0,    0, -235,    0,    0,   17,   56,   62,
   64,   -1,   95,   39,    0,   67,   69,   74,   77,    0,
    0,    0,    0,   92, -204,    0,    0, -166,    0,   11,
   95,   95,  -87,    0,  141,   83,   81,    0,    0, -214,
    0,   60,   20, -154,  164,  -12,    0,   79,    0,    0,
    0,    0,    0, -204,  115,    0, -108,   98,  130,    0,
 -110,   24,   44,  132,  133,  136,  149,    0,   60,    0,
  -90,   23,    0,    0,   95,   95,   95, -150,   95,    0,
    0,    0,    0,    0,    0,   95,   95,   95,    0,  147,
 -193,  152,    0, -193,    0,  -30,   18,    0,    0,    0,
    0,    0,    0, -193,  -12,  -12, -154,   26,   46,  164,
   60,    0,    0,    0,  166, -193,   48,   26,   51,  150,
    0,    0,   46,   94, -186,    0,  -73,    0, -181,    0,
  169, -109,    0,  153,   -2,    0,    1,   54,   46,    0,
    0,   46,    0,    0,   -7,   59,  155,  156,   46,    0,
  -51,  158,  -32,  -25,   94,  -39,  180,   66,  181,   71,
    0,   82,   10,    0,    0,    0,    0,   66,    0,  -40,
  -42,    0,  221,  231,   87,    0,  -46,    0,   95,   60,
  274,    0,    0,  302,  307,  -24,  334,  121,   52,    0,
    0,    0,   95,  -37,   95,  336,  338,   68,  -34,   75,
   76,    0,    0,  354,  358,  -26,  360,  361,  122,  124,
  -31,  144,  154,  157,  357,  387,  390,  -53,  391,  394,
  395,    0,    0,    0,  396,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0, -127,    0,    0,    0,    0,    0,
 -219,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  417,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  100,    0,    0,    0,
    0,  172,    0,   -4,    7,  120,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   19,    0,    0,    0,    0,    0,    0,    0,  397,    0,
    0,    0,    0,    0,    0,    0,    8,    0,   57,    0,
    0,    0,    0,    0,    0,  321,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  140,  160,   15,    0,    0,   63,
  341,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  402,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -219,    0,    0,    0,    0,
    0,    0,   69,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  404,    0,    0,  117,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  198,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  324,  455,   -3,  -14,    0,  364,    0,  408,  292,
  295,    0, -105,  370, -101,    0,  373,    0,    0,  -43,
    0,    0,    0,    0,  -16,  388,  385,    0,   28,   21,
};
final static int YYTABLESIZE=621;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        189,
   46,   79,  209,    1,   50,  236,  215,   50,   37,   23,
   50,  197,  228,  139,  221,  203,  138,  222,  188,    4,
   67,   35,    5,   16,   72,   73,  143,  147,   38,   97,
   17,   81,   23,  204,   98,   34,   70,   23,   46,   70,
   23,  161,   35,  163,   15,   39,  164,   73,   71,   67,
   73,   71,  121,  173,   71,   69,   41,   23,   69,   65,
   88,   15,   20,  113,  106,   23,  114,   83,   84,   70,
    7,    8,    9,   23,  149,  150,   66,   20,   23,  152,
  153,    7,    8,    9,  107,   23,  125,   66,  142,   81,
   23,  114,  207,   23,   86,   42,   85,   74,   23,  137,
   74,   43,   86,   72,   85,   23,   72,  118,  214,  119,
   23,  141,  115,  116,   69,  217,  218,  122,  123,    6,
   46,   23,   44,   80,   89,   60,   23,   61,    7,    8,
    9,   10,   62,   23,   11,   63,  190,   99,    5,   50,
  102,  102,  102,  102,  102,  199,  102,    5,    5,    5,
    5,   37,   64,    5,  101,  102,  103,  102,  102,  102,
   98,  102,   98,   98,   98,    7,    8,    9,   10,  104,
  105,   11,  108,  109,   86,   86,  110,   98,   98,   98,
   97,   98,   97,   97,   97,   50,  208,  210,  211,  111,
  112,  126,  216,   74,   75,   76,   77,   97,   97,   97,
   96,   97,   96,   96,   96,  124,  140,  151,  146,  154,
  174,  156,   76,  171,  172,   76,  175,   96,   96,   96,
  195,   96,  196,   94,  191,   95,   18,  128,   19,  176,
  235,   76,   20,   76,   45,  129,  177,  130,  179,  182,
   47,   48,   49,   47,   48,   49,   47,   48,   49,   18,
   22,   19,  227,   87,   18,   20,   19,   18,  165,   19,
   20,  159,  160,   20,  157,  158,  134,    7,    8,    9,
   10,  186,   45,  166,   18,   70,   19,  133,   22,  192,
   20,   22,   18,  134,   19,   73,   73,   71,   20,  193,
   18,  129,   19,  130,   69,   18,   20,   19,   22,   87,
   21,   20,   18,   87,   19,   58,   22,   18,   20,   19,
   18,  134,   19,   20,   22,   18,   20,   19,  144,   22,
  162,   20,   18,   87,   19,  169,   22,   18,   20,   19,
  180,   22,  200,   20,   22,   74,   74,  183,   18,   22,
   19,   72,   72,   18,   20,   19,   22,   87,  184,   20,
   18,   22,   19,  194,   87,   87,   20,    7,    8,    9,
  201,   77,   22,   66,   77,  202,  102,   22,  102,  102,
  102,  102,  102,  205,   22,   47,   48,   49,  102,  102,
   77,   75,   77,   86,   75,   86,   98,  206,   98,   98,
   98,   98,   98,   59,  212,   51,  213,  219,   98,   98,
   75,  220,   75,  223,  224,  225,   97,  226,   97,   97,
   97,   97,   97,   51,   51,  232,   25,   51,   97,   97,
  145,   47,   48,   49,   78,  148,   96,  229,   96,   96,
   96,   96,   96,   90,   91,   92,   93,  230,   96,   96,
  231,   76,   76,   76,   76,  233,  168,  170,  234,  237,
   76,   76,  238,  239,  240,   45,  178,   51,   51,   51,
   56,   51,   28,  185,   95,  155,   36,  127,   51,   51,
   51,  100,  187,  120,  117,  131,  136,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  131,  136,    0,
    0,    0,    0,    0,    0,    0,    0,  131,    0,    0,
    0,    0,  136,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   59,    0,  136,   59,  136,    0,
    0,  136,    0,    0,    0,    0,    0,    0,  136,    0,
    0,    0,    0,    0,    0,    0,    0,   59,    0,   59,
    0,    0,    0,    0,    0,    0,    0,   59,    0,    0,
    0,    0,   51,    0,   59,    0,    0,    0,    0,    0,
    0,   51,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   51,   51,   51,    0,    0,
    0,   51,    0,    0,    0,    0,    0,    0,    0,    0,
   77,   77,   77,   77,    0,    0,    0,    0,    0,   77,
   77,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   75,   75,   75,   75,    0,    0,    0,    0,    0,   75,
   75,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   40,   45,   40,  285,   45,   59,   41,   45,   12,   40,
   45,   58,   44,  119,   41,   40,  118,   44,   59,  281,
   35,   61,  284,   59,   41,   42,  128,  133,  264,   42,
   59,   46,   40,   58,   47,  281,   41,   40,   40,   44,
   40,  147,   61,  149,  264,  281,  152,   41,   41,   64,
   44,   44,   96,  159,   44,   41,   40,   40,   44,  264,
   41,  281,   44,   41,   41,   40,   44,  282,  283,   59,
  275,  276,  277,   40,  261,  262,  281,   59,   40,  261,
  262,  275,  276,  277,   41,   40,  101,  281,   41,  104,
   40,   44,   41,   40,   43,   40,   45,   41,   40,  114,
   44,   40,   43,   41,   45,   40,   44,  258,   41,  260,
   40,  126,   85,   86,  281,   41,   41,   97,   98,  266,
   40,   40,   59,   41,  279,   59,   40,   59,  275,  276,
  277,  278,   59,   40,  281,   59,  180,   59,  266,   45,
   41,   42,   43,   44,   45,  189,   47,  275,  276,  277,
  278,  155,   61,  281,   40,  264,   59,   58,   59,   60,
   41,   62,   43,   44,   45,  275,  276,  277,  278,   40,
  281,  281,   41,   41,   58,   59,   41,   58,   59,   60,
   41,   62,   43,   44,   45,   45,  203,  204,  205,   41,
  281,   40,  209,  281,  282,  283,  284,   58,   59,   60,
   41,   62,   43,   44,   45,   59,   41,  281,   59,   41,
  262,   59,   41,   59,   59,   44,   59,   58,   59,   60,
  267,   62,  269,   60,  267,   62,  257,  258,  259,  262,
  284,   60,  263,   62,  274,  266,  262,  268,   59,   59,
  281,  282,  283,  281,  282,  283,  281,  282,  283,  257,
  281,  259,  284,  280,  257,  263,  259,  257,  266,  259,
  263,  261,  262,  263,  267,  268,  266,  275,  276,  277,
  278,  262,  274,  281,  257,  280,  259,  260,  281,   59,
  263,  281,  257,  266,  259,  279,  280,  280,  263,   59,
  257,  266,  259,  268,  280,  257,  263,  259,  281,  280,
  267,  263,  257,  280,  259,  267,  281,  257,  263,  259,
  257,  266,  259,  263,  281,  257,  263,  259,  268,  281,
  267,  263,  257,  280,  259,  267,  281,  257,  263,  259,
  265,  281,   59,  263,  281,  279,  280,  267,  257,  281,
  259,  279,  280,  257,  263,  259,  281,  280,  267,  263,
  257,  281,  259,  267,  280,  280,  263,  275,  276,  277,
   59,   41,  281,  281,   44,   59,  267,  281,  269,  270,
  271,  272,  273,   40,  281,  281,  282,  283,  279,  280,
   60,   41,   62,  267,   44,  269,  267,  267,  269,  270,
  271,  272,  273,   24,   59,   23,   59,   44,  279,  280,
   60,   44,   62,   44,   44,  284,  267,  284,  269,  270,
  271,  272,  273,   41,   42,   59,    0,   45,  279,  280,
  129,  281,  282,  283,  284,  134,  267,  284,  269,  270,
  271,  272,  273,  270,  271,  272,  273,  284,  279,  280,
  284,  270,  271,  272,  273,   59,  155,  156,   59,   59,
  279,  280,   59,   59,   59,   59,  165,   85,   86,   87,
   59,   89,   59,  172,  267,  142,   12,  104,   96,   97,
   98,   64,  178,   89,   87,  106,  107,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  118,  119,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  128,   -1,   -1,
   -1,   -1,  133,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  145,   -1,  147,  148,  149,   -1,
   -1,  152,   -1,   -1,   -1,   -1,   -1,   -1,  159,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  168,   -1,  170,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  178,   -1,   -1,
   -1,   -1,  180,   -1,  185,   -1,   -1,   -1,   -1,   -1,
   -1,  189,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  203,  204,  205,   -1,   -1,
   -1,  209,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
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

//#line 190 "gramatica.y"

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
        if( !(number >= GeneratorFloat.MIN_NEGATIVE && number <= GeneratorFloat.MAX_NEGATIVE) )
            yyerror("Single fuera de rango negativo");
    }
    private void checkPositiv(double number){
        if( !( (number >= GeneratorFloat.MIN_POSITIV && number <= GeneratorFloat.MAX_POSITIV) || number == GeneratorFloat.CERO) )
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
           if( !(numberi >= GeneratorInteger.MIN && numberi <= GeneratorInteger.MAX) )
            yyerror("Entero fuera de rango");
            }
    }

    private void print(String text) {
        System.out.println(text);
    }

    private void generated(String gen) {
        this.lexico.generated(gen);
    }
//#line 568 "Parser.java"
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
case 15:
//#line 44 "gramatica.y"
{ this.generated("tipo de variable"); }
break;
case 16:
//#line 47 "gramatica.y"
{ this.generated("encabezado de funcion"); }
break;
case 17:
//#line 48 "gramatica.y"
{yyerror("Falta el tipo de la funcion");}
break;
case 18:
//#line 51 "gramatica.y"
{ this.generated("lista de variables"); }
break;
case 19:
//#line 52 "gramatica.y"
{ this.generated("lista de variables"); }
break;
case 20:
//#line 53 "gramatica.y"
{yyerror("Se esperaba un ID");}
break;
case 21:
//#line 56 "gramatica.y"
{ this.generated("parametro"); }
break;
case 22:
//#line 57 "gramatica.y"
{ this.yyerror("Una funcion no puede tener mas de un parametro"); }
break;
case 23:
//#line 60 "gramatica.y"
{ this.generated("bloque ejecutable"); }
break;
case 25:
//#line 62 "gramatica.y"
{yyerror("Falta el ; al final del END");}
break;
case 28:
//#line 67 "gramatica.y"
{yyerror("Falta el END");}
break;
case 29:
//#line 68 "gramatica.y"
{yyerror("Falta el BEGIN");}
break;
case 30:
//#line 71 "gramatica.y"
{ this.generated("IF de una linea"); }
break;
case 31:
//#line 72 "gramatica.y"
{ this.generated("IF"); }
break;
case 32:
//#line 75 "gramatica.y"
{ this.generated("WHILE de una linea"); }
break;
case 34:
//#line 77 "gramatica.y"
{ this.generated("WHILE"); }
break;
case 36:
//#line 79 "gramatica.y"
{ this.yyerror("Faltan las sentencias ejecutables"); }
break;
case 37:
//#line 80 "gramatica.y"
{ this.yyerror("No se esperaban sentencias ejecutables despues del break"); }
break;
case 38:
//#line 81 "gramatica.y"
{ this.yyerror("Faltan las sentencias ejecutables"); }
break;
case 39:
//#line 84 "gramatica.y"
{ this.generated("sentencias ejecutable"); }
break;
case 40:
//#line 85 "gramatica.y"
{ this.generated("sentencias ejecutable"); }
break;
case 41:
//#line 88 "gramatica.y"
{ this.generated("sentencia ejecutable"); }
break;
case 42:
//#line 89 "gramatica.y"
{ this.generated("sentencia ejecutable"); }
break;
case 43:
//#line 90 "gramatica.y"
{ this.generated("sentencia ejecutable"); }
break;
case 44:
//#line 91 "gramatica.y"
{ this.generated("sentencia ejecutable"); }
break;
case 45:
//#line 94 "gramatica.y"
{ this.generated("asignacion"); }
break;
case 47:
//#line 98 "gramatica.y"
{ this.generated("invocacion a funcion"); }
break;
case 48:
//#line 99 "gramatica.y"
{yyerror("Falta el parametro de la funcion");}
break;
case 49:
//#line 102 "gramatica.y"
{ this.generated("estructura de control"); }
break;
case 50:
//#line 103 "gramatica.y"
{ this.generated("estructura de control"); }
break;
case 54:
//#line 111 "gramatica.y"
{yyerror("Falta la palabra reservada IF");}
break;
case 55:
//#line 112 "gramatica.y"
{yyerror("Falta la palabra reservada THEN");}
break;
case 56:
//#line 113 "gramatica.y"
{yyerror("Falta la palabra reservada ENDIF");}
break;
case 58:
//#line 117 "gramatica.y"
{yyerror("Falta la palabra reservada IF");}
break;
case 59:
//#line 118 "gramatica.y"
{yyerror("Falta la palabra reservada THEN");}
break;
case 60:
//#line 119 "gramatica.y"
{yyerror("Falta la palabra reservada ELSE");}
break;
case 61:
//#line 120 "gramatica.y"
{yyerror("Falta la palabra reservada ENDIF");}
break;
case 63:
//#line 124 "gramatica.y"
{yyerror("Falta la palabra reservada WHILE");}
break;
case 64:
//#line 125 "gramatica.y"
{yyerror("Falta la palabra reservada DO");}
break;
case 65:
//#line 128 "gramatica.y"
{ this.generated("impresion por pantalla"); print(val_peek(1).sval); }
break;
case 67:
//#line 130 "gramatica.y"
{print(String.valueOf(val_peek(1).ival));}
break;
case 68:
//#line 131 "gramatica.y"
{print(String.valueOf(val_peek(1).dval));}
break;
case 71:
//#line 136 "gramatica.y"
{ this.yyerror("Falta otra condicion despues del '||'"); }
break;
case 74:
//#line 141 "gramatica.y"
{ this.yyerror("Falta otra condicion despues del '&&'"); }
break;
case 77:
//#line 146 "gramatica.y"
{ this.yyerror("Falta expresion con la cual comparar"); }
break;
case 84:
//#line 153 "gramatica.y"
{ this.generated("RETURN"); }
break;
case 85:
//#line 154 "gramatica.y"
{ this.yyerror("Falta la expresion a retornar"); }
break;
case 86:
//#line 155 "gramatica.y"
{ this.yyerror("Falta un ';' al final de la sentencia"); }
break;
case 87:
//#line 158 "gramatica.y"
{ this.generated("POST condition"); }
break;
case 88:
//#line 159 "gramatica.y"
{ this.yyerror("Se esperaba POST al principio de la sentencia"); }
break;
case 89:
//#line 160 "gramatica.y"
{ this.yyerror("Falta ':' despues de POST"); }
break;
case 90:
//#line 161 "gramatica.y"
{ this.yyerror("Se esperaba '(' despues de ':'"); }
break;
case 91:
//#line 162 "gramatica.y"
{ this.yyerror("Falta la condicion del POST"); }
break;
case 92:
//#line 163 "gramatica.y"
{ this.yyerror("Se esperaba ')' despues de la condicion"); }
break;
case 93:
//#line 164 "gramatica.y"
{ this.yyerror("Se esperaba ',' despues de la condicion"); }
break;
case 94:
//#line 165 "gramatica.y"
{ this.yyerror("Falta el string despues de ','"); }
break;
case 95:
//#line 166 "gramatica.y"
{ this.yyerror("Se esperaba ';' al final de la linea"); }
break;
case 103:
//#line 181 "gramatica.y"
{ this.isInRange(val_peek(0).sval,false);}
break;
case 104:
//#line 182 "gramatica.y"
{ this.isInRange(val_peek(0).sval,false);}
break;
case 105:
//#line 183 "gramatica.y"
{this.isInRange(val_peek(0).sval,true);}
break;
case 106:
//#line 184 "gramatica.y"
{this.isInRange(val_peek(0).sval,true);}
break;
//#line 1005 "Parser.java"
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
