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
//#line 24 "Parser.java"




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
    0,    0,    1,    1,    2,    2,    4,    4,    4,    5,
    5,    5,    5,    9,    6,    6,    6,    7,    3,    3,
    8,    8,   13,   13,   15,   15,   15,   15,   15,   15,
   15,   10,   10,   14,   14,   14,   14,   16,   16,   17,
   18,   18,   21,   21,   23,   24,   22,   19,   25,   25,
   25,   26,   26,   26,   27,   27,   27,   28,   28,   28,
   28,   28,   28,   11,   11,   11,   12,   12,   12,   12,
   12,   12,   12,   12,   12,   20,   20,   20,   29,   29,
   29,   30,   30,   30,   30,   30,   30,
};
final static short yylen[] = {                            2,
    3,    2,    3,    3,    1,    2,    3,    9,    5,    1,
    1,    1,    1,    5,    1,    3,    2,    2,    4,    3,
    6,    5,    1,    4,    1,    2,    4,    6,    6,    7,
    5,    1,    2,    2,    2,    2,    2,    3,    3,    4,
    1,    1,    1,    1,    7,    9,    6,    4,    3,    1,
    2,    3,    1,    2,    3,    1,    2,    1,    1,    1,
    1,    1,    1,    5,    2,    2,    8,    7,    7,    7,
    7,    7,    7,    7,    7,    3,    3,    1,    3,    3,
    1,    1,    1,    1,    2,    2,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,   10,   11,   12,    0,
   13,    0,    2,    5,    0,    4,    3,    0,    0,    0,
    0,    0,    0,   32,    0,    0,    0,    0,   41,   42,
   43,   44,    0,    1,    6,    0,   15,    0,    0,    0,
    0,   20,    0,    0,    0,   33,   34,   35,   36,   37,
    0,    0,    7,    0,    0,   83,   84,    0,   87,    0,
    0,    0,    0,    0,   81,    0,    0,   39,    0,    0,
    0,   19,    0,    0,    0,   16,   85,   86,    0,    0,
    0,    0,    0,   60,   61,   62,   63,   58,   59,    0,
    0,    0,    0,   48,   18,   40,    0,    9,    0,    0,
    0,    0,    0,    0,    0,   79,   80,    0,    0,    0,
    0,    0,   25,   47,    0,    0,   23,    0,    0,    0,
    0,   26,    0,    0,   45,   14,    0,    0,    0,    0,
    0,    0,    0,    0,    8,    0,    0,   27,    0,   24,
   46,    0,    0,   31,    0,    0,    0,   65,    0,    0,
    0,    0,    0,    0,   29,   28,    0,    0,   22,    0,
    0,    0,    0,   30,    0,    0,    0,    0,    0,   21,
   64,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   69,
   71,   73,    0,   74,   72,   70,   68,   67,
};
final static short yydgoto[] = {                          2,
    3,   12,   13,   14,   15,   38,   71,  128,   74,   23,
  143,  154,  116,   24,  114,   25,   59,   27,   28,   60,
   29,   30,   31,   32,   61,   62,   63,   90,   64,   65,
};
final static short yysindex[] = {                      -275,
 -203,    0,  123,  -43,  -23,  -74,    0,    0,    0, -243,
    0,  123,    0,    0, -223,    0,    0,   16,   40,   57,
   13,  -38,  -73,    0,   60,   66,   75,   84,    0,    0,
    0,    0,   86,    0,    0, -136,    0,  -27,   50,   50,
 -134,    0,  -44,    5,  106,    0,    0,    0,    0,    0,
    5,  128,    0, -118,  133,    0,    0, -191,    0,   56,
  -19, -103,  -47,   90,    0,   -2,  138,    0,   56, -101,
  146,    0,  -72,  143,    5,    0,    0,    0,   50,   50,
   50,  -53,   50,    0,    0,    0,    0,    0,    0,   50,
   50,   50,  -46,    0,    0,    0,  179,    0,  186,   90,
   90, -103,  -99,  -47,   56,    0,    0,  -60,    5,  -65,
  -59,  169,    0,    0, -202, -144,    0,  189,  130,  172,
 -115,    0,   88,  -60,    0,    0, -202,  173,   93,  174,
  175,  176,  -12,  100,    0,  203,  105,    0,  116,    0,
    0,  -40,  -52,    0,  206,  207,  121,    0,   50,   56,
  208,   36,  230,   20,    0,    0,  213,   55,    0,   50,
  -37,   50,  226,    0,  233,   -1,  -34,   18,   19,    0,
    0,  256,  257,  -20,  258,  261,   22,   23,  -32,   28,
   34,   41,  249,  267,  268,  -55,  271,  279,  287,    0,
    0,    0,  294,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   -7,  -16,    0,    0,    0,    0,   49,
    0,   10,  -21,    4,    0,    0,    0,    0,  295,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   29,    0,  -11,    0,    0,    0,    0,    0,    0,   64,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   24,
   44,   30,    0,    9,   69,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -49,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   94,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  248,  354,    2,   63,    0,  292,    0,    0,    1,
    0,    0,  246,   54,    0,    0,   12,    0,    0,   32,
    0,    0,    0,    0,   -6,  290,  293,    0,   91,  104,
};
final static int YYTABLESIZE=411;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        149,
   58,   44,  167,  194,   58,  153,  173,   58,   66,    1,
   58,  186,   88,   35,   89,   16,   54,   26,  148,   53,
  179,   82,   53,  180,   82,   82,   82,   82,   82,   54,
   82,   53,   54,   66,   26,   17,   17,   33,   93,  172,
   36,   82,   82,   82,   78,   82,   78,   78,   78,   52,
   50,   17,   52,   50,   18,   39,   19,   37,  175,  176,
   20,   78,   78,   78,   77,   78,   77,   77,   77,   51,
   49,   42,   51,   49,   69,  160,   46,    4,   22,   40,
    5,   77,   77,   77,   76,   77,   76,   76,   76,   56,
   77,   78,   56,  161,   58,  165,   41,   80,   80,   79,
   79,   76,   76,   76,   57,   76,   70,   57,   56,   55,
   56,  121,   55,   73,   26,  123,  124,  125,   47,   26,
   35,  105,   26,   57,   48,   57,   26,  134,   55,  137,
   55,   91,   26,   49,   26,   26,   92,   70,   26,  147,
   26,   18,   50,   19,   52,   26,   51,   20,   26,   67,
   26,  130,  131,  166,  168,  169,  113,   18,   26,   19,
  174,  117,   76,   20,   72,   22,  111,   75,  112,  100,
  101,  118,   44,  150,   46,   83,   46,  117,   94,   95,
  158,   22,   18,   18,   19,   19,   96,   46,   20,   20,
   46,   97,   21,   45,  106,  107,   18,   18,   19,   19,
   46,   98,   20,   20,  103,  115,   22,   22,  120,    7,
    8,    9,   10,  108,  151,   11,  152,   66,  109,   66,
   22,   22,   84,   85,   86,   87,  110,  122,  193,  126,
  129,  135,  138,  139,  140,   43,   55,   56,   57,   68,
   55,   56,   57,   55,   56,   57,   55,   56,   57,  141,
   82,  185,   82,   82,   82,   82,   82,   53,   53,   81,
   81,  144,   82,   82,  155,  156,  159,   54,   54,  162,
   78,  164,   78,   78,   78,   78,   78,   81,   81,    7,
    8,    9,   78,   78,  170,   11,  163,   52,   52,   50,
   77,  171,   77,   77,   77,   77,   77,   81,   81,  177,
  178,  181,   77,   77,  182,  183,  184,  190,   51,   49,
   76,  187,   76,   76,   76,   76,   76,  188,   56,   56,
   56,   56,   76,   76,  189,  191,  192,   56,   56,  195,
   55,   56,   57,   57,   57,   57,   57,  196,   55,   55,
   55,   55,   57,   57,   18,  197,   19,   55,   55,   18,
   20,   19,  198,   38,  132,   20,   18,  119,   19,  136,
   75,   18,   20,   19,  142,   34,   99,   20,   22,  133,
  102,  145,   18,   22,   19,  104,    0,   18,   20,   19,
   22,    0,  146,   20,    0,   22,    0,  157,    6,    0,
    0,    0,    0,    0,    0,  127,   22,    7,    8,    9,
   10,   22,    0,   11,    7,    8,    9,   10,    0,    0,
   11,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   45,   40,   40,   59,   45,   58,   41,   45,   58,  285,
   45,   44,   60,   12,   62,   59,   44,    6,   59,   41,
   41,   41,   44,   44,   41,   42,   43,   44,   45,   41,
   47,   59,   44,   40,   23,   59,   44,  281,   41,   41,
  264,   58,   59,   60,   41,   62,   43,   44,   45,   41,
   41,   59,   44,   44,  257,   40,  259,  281,   41,   41,
  263,   58,   59,   60,   41,   62,   43,   44,   45,   41,
   41,   59,   44,   44,   43,   40,   23,  281,  281,   40,
  284,   58,   59,   60,   41,   62,   43,   44,   45,   41,
  282,  283,   44,   58,   45,   41,   40,   43,   43,   45,
   45,   58,   59,   60,   41,   62,   44,   44,   60,   41,
   62,  111,   44,   51,  103,  115,  261,  262,   59,  108,
  119,   90,  111,   60,   59,   62,  115,  127,   60,  129,
   62,   42,  121,   59,  123,  124,   47,   75,  127,  139,
  129,  257,   59,  259,  281,  134,   61,  263,  137,  284,
  139,  267,  268,  160,  161,  162,  103,  257,  147,  259,
  167,  108,  281,  263,   59,  281,  266,   40,  268,   79,
   80,  109,   40,  142,  121,  279,  123,  124,   41,  281,
  149,  281,  257,  257,  259,  259,   41,  134,  263,  263,
  137,  264,  267,  267,   91,   92,  257,  257,  259,  259,
  147,   59,  263,  263,  258,  266,  281,  281,  268,  275,
  276,  277,  278,  260,  267,  281,  269,  267,   40,  269,
  281,  281,  270,  271,  272,  273,   41,   59,  284,   41,
   59,   59,   59,   59,   59,  274,  281,  282,  283,  284,
  281,  282,  283,  281,  282,  283,  281,  282,  283,  262,
  267,  284,  269,  270,  271,  272,  273,  279,  280,  280,
  280,   59,  279,  280,   59,   59,   59,  279,  280,   40,
  267,   59,  269,  270,  271,  272,  273,  280,  280,  275,
  276,  277,  279,  280,   59,  281,  267,  279,  280,  280,
  267,   59,  269,  270,  271,  272,  273,  280,  280,   44,
   44,   44,  279,  280,   44,  284,  284,   59,  280,  280,
  267,  284,  269,  270,  271,  272,  273,  284,  270,  271,
  272,  273,  279,  280,  284,   59,   59,  279,  280,   59,
  281,  282,  283,  270,  271,  272,  273,   59,  270,  271,
  272,  273,  279,  280,  257,   59,  259,  279,  280,  257,
  263,  259,   59,   59,  267,  263,  257,  110,  259,  267,
  267,  257,  263,  259,  265,   12,   75,  263,  281,  124,
   81,  267,  257,  281,  259,   83,   -1,  257,  263,  259,
  281,   -1,  267,  263,   -1,  281,   -1,  267,  266,   -1,
   -1,   -1,   -1,   -1,   -1,  266,  281,  275,  276,  277,
  278,  281,   -1,  281,  275,  276,  277,  278,   -1,   -1,
  281,
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
"bloque_declarativas : sentencia_declarativa",
"bloque_declarativas : bloque_declarativas sentencia_declarativa",
"sentencia_declarativa : tipo lista_variables ';'",
"sentencia_declarativa : tipo FUNC ID '(' parametro ')' bloque_declarativas bloque_ejecutables_funcion ';'",
"sentencia_declarativa : TYPEDEF ID '=' encabezado_funcion ';'",
"tipo : INT",
"tipo : SINGLE",
"tipo : STRING",
"tipo : ID",
"encabezado_funcion : tipo FUNC '(' tipo ')'",
"lista_variables : ID",
"lista_variables : lista_variables ',' ID",
"lista_variables : lista_variables ','",
"parametro : tipo ID",
"bloque_ejecutables_programa : BEGIN sentencias_ejecutables END ';'",
"bloque_ejecutables_programa : BEGIN END ';'",
"bloque_ejecutables_funcion : BEGIN sentencias_ejecutables retorno post_condicion END ';'",
"bloque_ejecutables_funcion : BEGIN sentencias_ejecutables retorno END ';'",
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
"estructura_control : sentencia_if",
"estructura_control : sentencia_while",
"sentencia_if : if_simple",
"sentencia_if : if_completo",
"if_simple : IF '(' condicion ')' THEN bloque_ejecutables_if ENDIF",
"if_completo : IF '(' condicion ')' THEN bloque_ejecutables_if ELSE bloque_ejecutables_if ENDIF",
"sentencia_while : WHILE '(' condicion ')' DO bloque_ejecutables_while",
"impresion_pantalla : PRINT '(' CONST_STR ')'",
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

//#line 167 "gramatica.y"

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
//#line 487 "Parser.java"
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
//#line 23 "gramatica.y"
{System.err.println("Se reconocio el programa");}
break;
case 2:
//#line 24 "gramatica.y"
{System.err.println("Se reconocio el programa");}
break;
case 3:
//#line 27 "gramatica.y"
{ this.generated("nombre de programa"); }
break;
case 4:
//#line 28 "gramatica.y"
{ this.yyerror("El nombre del programa debe ser un STRING no un ID"); }
break;
case 5:
//#line 31 "gramatica.y"
{ this.generated("bloque declarativo"); }
break;
case 6:
//#line 32 "gramatica.y"
{ this.generated("bloque declarativo"); }
break;
case 7:
//#line 34 "gramatica.y"
{ this.generated("sentencia declarativa"); }
break;
case 8:
//#line 35 "gramatica.y"
{ this.generated("sentencia declarativa"); }
break;
case 9:
//#line 36 "gramatica.y"
{ this.generated("sentencia declarativa"); }
break;
case 13:
//#line 39 "gramatica.y"
{ this.generated("tipo de variable"); }
break;
case 14:
//#line 42 "gramatica.y"
{ this.generated("encabezado de funcion"); }
break;
case 15:
//#line 45 "gramatica.y"
{ this.generated("lista de variables"); }
break;
case 16:
//#line 46 "gramatica.y"
{ this.generated("lista de variables"); }
break;
case 17:
//#line 47 "gramatica.y"
{yyerror("Se esperaba un ID");}
break;
case 18:
//#line 50 "gramatica.y"
{ this.generated("parametro"); }
break;
case 19:
//#line 53 "gramatica.y"
{ this.generated("bloque ejecutable"); }
break;
case 23:
//#line 61 "gramatica.y"
{ this.generated("IF de una linea"); }
break;
case 24:
//#line 62 "gramatica.y"
{ this.generated("IF"); }
break;
case 25:
//#line 65 "gramatica.y"
{ this.generated("WHILE de una linea"); }
break;
case 27:
//#line 67 "gramatica.y"
{ this.generated("WHILE"); }
break;
case 29:
//#line 69 "gramatica.y"
{ this.yyerror("Faltan las sentencias ejecutables"); }
break;
case 30:
//#line 70 "gramatica.y"
{ this.yyerror("No se esperaban sentencias ejecutables despues del break"); }
break;
case 31:
//#line 71 "gramatica.y"
{ this.yyerror("Faltan las sentencias ejecutables"); }
break;
case 32:
//#line 74 "gramatica.y"
{ this.generated("sentencias ejecutable"); }
break;
case 33:
//#line 75 "gramatica.y"
{ this.generated("sentencias ejecutable"); }
break;
case 34:
//#line 78 "gramatica.y"
{ this.generated("sentencia ejecutable"); }
break;
case 35:
//#line 79 "gramatica.y"
{ this.generated("sentencia ejecutable"); }
break;
case 36:
//#line 80 "gramatica.y"
{ this.generated("sentencia ejecutable"); }
break;
case 37:
//#line 81 "gramatica.y"
{ this.generated("sentencia ejecutable"); }
break;
case 38:
//#line 84 "gramatica.y"
{ this.generated("asignacion"); }
break;
case 40:
//#line 88 "gramatica.y"
{ this.generated("invocacion a funcion"); }
break;
case 41:
//#line 91 "gramatica.y"
{ this.generated("estructura de control"); }
break;
case 42:
//#line 92 "gramatica.y"
{ this.generated("estructura de control"); }
break;
case 48:
//#line 108 "gramatica.y"
{ this.generated("impresion por pantalla"); print(val_peek(1).sval); }
break;
case 51:
//#line 113 "gramatica.y"
{ this.yyerror("Falta otra condicion despues del '||'"); }
break;
case 54:
//#line 118 "gramatica.y"
{ this.yyerror("Falta otra condicion despues del '&&'"); }
break;
case 57:
//#line 123 "gramatica.y"
{ this.yyerror("Falta expresion con la cual comparar"); }
break;
case 64:
//#line 130 "gramatica.y"
{ this.generated("RETURN"); }
break;
case 65:
//#line 131 "gramatica.y"
{ this.yyerror("Falta la expresion a retornar"); }
break;
case 66:
//#line 132 "gramatica.y"
{ this.yyerror("Falta un ';' al final de la sentencia"); }
break;
case 67:
//#line 135 "gramatica.y"
{ this.generated("POST condition"); }
break;
case 68:
//#line 136 "gramatica.y"
{ this.yyerror("Se esperaba POST al principio de la sentencia"); }
break;
case 69:
//#line 137 "gramatica.y"
{ this.yyerror("Falta ':' despues de POST"); }
break;
case 70:
//#line 138 "gramatica.y"
{ this.yyerror("Se esperaba '(' despues de ':'"); }
break;
case 71:
//#line 139 "gramatica.y"
{ this.yyerror("Falta la condicion del POST"); }
break;
case 72:
//#line 140 "gramatica.y"
{ this.yyerror("Se esperaba ')' despues de la condicion"); }
break;
case 73:
//#line 141 "gramatica.y"
{ this.yyerror("Se esperaba ',' despues de la condicion"); }
break;
case 74:
//#line 142 "gramatica.y"
{ this.yyerror("Falta el string despues de ','"); }
break;
case 75:
//#line 143 "gramatica.y"
{ this.yyerror("Se esperaba ';' al final de la linea"); }
break;
case 83:
//#line 158 "gramatica.y"
{ this.isInRange(val_peek(0).sval,false);}
break;
case 84:
//#line 159 "gramatica.y"
{ this.isInRange(val_peek(0).sval,false);}
break;
case 85:
//#line 160 "gramatica.y"
{this.isInRange(val_peek(0).sval,true);}
break;
case 86:
//#line 161 "gramatica.y"
{this.isInRange(val_peek(0).sval,true);}
break;
//#line 848 "Parser.java"
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
