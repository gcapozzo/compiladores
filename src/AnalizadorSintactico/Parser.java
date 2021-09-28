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
    0,    1,    1,    2,    2,    4,    4,    4,    5,    5,
    5,    5,    9,    6,    6,    7,    3,    8,    8,   13,
   13,   15,   15,   15,   15,   15,   15,   15,   10,   10,
   14,   14,   14,   14,   16,   16,   17,   18,   18,   21,
   21,   23,   24,   22,   19,   25,   25,   25,   26,   26,
   26,   27,   27,   27,   28,   28,   28,   28,   28,   28,
   11,   11,   11,   12,   12,   12,   12,   12,   12,   12,
   12,   12,   20,   20,   20,   29,   29,   29,   30,   30,
   30,   30,   30,
};
final static short yylen[] = {                            2,
    3,    3,    3,    1,    2,    3,    9,    5,    1,    1,
    1,    1,    5,    1,    3,    2,    4,    6,    5,    1,
    4,    1,    2,    4,    6,    6,    7,    5,    1,    2,
    2,    2,    2,    2,    3,    3,    4,    1,    1,    1,
    1,    7,    9,    6,    4,    3,    1,    2,    3,    1,
    2,    3,    1,    2,    1,    1,    1,    1,    1,    1,
    5,    2,    2,    8,    7,    7,    7,    7,    7,    7,
    7,    7,    3,    3,    1,    3,    3,    1,    1,    1,
    1,    2,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    9,   10,   11,    0,   12,
    0,    4,    0,    3,    2,    0,    0,    1,    5,    0,
   14,    0,    0,    0,    0,    0,    0,    0,   29,    0,
    0,    0,    0,   38,   39,   40,   41,    0,    6,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   30,   31,
   32,   33,   34,    0,   15,    0,    8,    0,   80,   81,
    0,   83,    0,    0,    0,    0,    0,   78,    0,    0,
   36,    0,    0,    0,   17,    0,    0,   82,    0,    0,
    0,    0,    0,   57,   58,   59,   60,   55,   56,    0,
    0,    0,    0,   45,   16,   37,    0,    0,    0,    0,
    0,    0,    0,    0,   76,   77,    0,    0,   13,    0,
    0,   22,   44,    0,    0,   20,    0,    0,    0,    0,
   23,    0,    0,   42,    0,    7,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   24,    0,   21,   43,   62,
    0,    0,    0,    0,    0,    0,   28,    0,    0,    0,
    0,   19,    0,    0,    0,    0,   26,   25,    0,    0,
    0,    0,    0,    0,   18,   27,   61,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   66,   68,   70,    0,   71,
   69,   67,   65,   64,
};
final static short yydgoto[] = {                          2,
    3,   11,   18,   12,   13,   22,   74,  118,   42,   28,
  133,  146,  115,   29,  113,   30,   62,   32,   33,   63,
   34,   35,   36,   37,   64,   65,   66,   90,   67,   68,
};
final static short yysindex[] = {                      -275,
 -209,    0,  130,  -45,   -3,    0,    0,    0, -229,    0,
  -67,    0, -246,    0,    0,   38, -202,    0,    0, -223,
    0,   75,    5,   56,   78,   85,  -38,   88,    0,   80,
   81,   87,   92,    0,    0,    0,    0,   97,    0, -137,
 -117,   94,   50,   50, -130,  -44,    5,  108,    0,    0,
    0,    0,    0,    5,    0,  119,    0,  131,    0,    0,
   50,    0,   -7,  -19, -111,  -47,   70,    0,   -2,  133,
    0,   -7, -112,  134,    0,  135,    5,    0,   50,   50,
   50,  -80,   50,    0,    0,    0,    0,    0,    0,   50,
   50,   50,  -79,    0,    0,    0,  130,  141,   70,   70,
 -111, -102,  -47,   -7,    0,    0, -165,  123,    0,   93,
  125,    0,    0, -202, -184,    0, -202,  132,  137,  -62,
    0,  100, -165,    0,  101,    0,  113,  143,  144,  145,
  -75,  -40,  -52,  148,  116,    0,  128,    0,    0,    0,
   50,   -7,  153,  -24,  149,  -74,    0,  157,  162,  129,
  115,    0,   50,  -37,   50,  163,    0,    0,  168,  169,
   -1,  -34,   18,   19,    0,    0,    0,  150,  186,  -20,
  187,  188,  -92,  -51,  -32,  -50,  -22,  -18,  176,  191,
  206,  -55,  208,  211,  213,    0,    0,    0,  226,    0,
    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -16,    0,    0,
    0,    0,   49,    0,   10,  -21,    4,    0,    0,    0,
    0,  228,    0,    0,    0,    0,    0,    0,    0,    0,
   29,    0,  -11,    0,    0,    0,    0,    0,    0,   64,
    0,    0,    0,    0,    0,    0,    0,    0,   24,   44,
   30,    0,    9,   69,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -49,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   25,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  203,    0,    6,   53,    0,  247,    0,    0,   35,
    0,    0,  179,   13,    0,    0,   63,    0,    0,   -9,
    0,    0,    0,    0,  -12,  224,  223,    0,   42,   36,
};
final static int YYTABLESIZE=411;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        141,
   61,   47,  162,  190,   61,  145,  169,   61,   63,    1,
   61,  182,   88,   14,   89,  153,   19,   20,  140,   50,
  175,   82,   50,  176,   79,   79,   79,   79,   79,   51,
   79,   69,   51,  154,   21,   80,   72,   79,   93,  168,
   49,   79,   79,   79,   75,   79,   75,   75,   75,   49,
   47,   16,   49,   47,   24,   15,   25,   38,  171,  172,
   26,   75,   75,   75,   74,   75,   74,   74,   74,   48,
   46,    4,   48,   46,    5,   41,  123,  124,   27,   31,
  104,   74,   74,   74,   73,   74,   73,   73,   73,   53,
   31,   24,   53,   25,   61,   43,   78,   26,   23,   73,
  114,   73,   73,   73,   54,   73,   73,   54,   53,   52,
   53,   91,   52,   19,  112,   27,   92,   44,   40,  116,
   99,  100,  142,   54,   45,   54,  105,  106,   52,   98,
   52,  151,   49,   39,   49,  116,   54,   49,   50,   51,
  161,  163,  164,   55,  120,   52,   56,   49,  122,  170,
   53,  125,   57,   70,   24,  160,   25,   80,   77,   79,
   26,  135,   49,  110,   31,  111,   75,   83,   95,   31,
   47,  150,   31,   94,   96,   97,   31,  102,   27,   31,
  107,  109,   31,  121,   31,   31,  139,   31,  155,   31,
  126,  179,  156,  173,   24,  127,   25,   31,   17,   31,
   26,  136,  137,  138,  128,  129,  147,    6,    7,    8,
    9,  152,   31,   10,  143,  157,  144,   63,   27,   63,
  158,  165,   84,   85,   86,   87,  166,  167,  189,  174,
  177,  178,  180,  183,  186,   46,   58,   59,   60,   71,
   58,   59,   60,   58,   59,   60,   58,   59,   60,  187,
   79,  181,   79,   79,   79,   79,   79,   50,   50,   81,
   81,  184,   79,   79,  188,  185,  191,   51,   51,  192,
   75,  193,   75,   75,   75,   75,   75,   81,   81,    6,
    7,    8,   75,   75,  194,   10,   35,   49,   49,   47,
   74,   72,   74,   74,   74,   74,   74,   81,   81,  108,
   76,  131,   74,   74,  101,  103,    0,    0,   48,   46,
   73,    0,   73,   73,   73,   73,   73,    0,   53,   53,
   53,   53,   73,   73,    0,    0,    0,   53,   53,    0,
   58,   59,   60,   54,   54,   54,   54,    0,   52,   52,
   52,   52,   54,   54,   24,    0,   25,   52,   52,   24,
   26,   25,    0,    0,   48,   26,   24,   24,   25,   25,
  119,    0,   26,   26,    0,  132,  130,    0,   27,   24,
    0,   25,   24,   27,   25,   26,    0,    0,   26,  134,
   27,   27,  148,    0,   24,   24,   25,   25,  117,    0,
   26,   26,    0,   27,  149,  159,   27,    6,    7,    8,
    9,    0,    0,   10,    6,    7,    8,    9,   27,   27,
   10,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   45,   40,   40,   59,   45,   58,   41,   45,   58,  285,
   45,   44,   60,   59,   62,   40,   11,  264,   59,   41,
   41,   41,   44,   44,   41,   42,   43,   44,   45,   41,
   47,   44,   44,   58,  281,   43,   46,   45,   41,   41,
   28,   58,   59,   60,   41,   62,   43,   44,   45,   41,
   41,  281,   44,   44,  257,   59,  259,  281,   41,   41,
  263,   58,   59,   60,   41,   62,   43,   44,   45,   41,
   41,  281,   44,   44,  284,   23,  261,  262,  281,   17,
   90,   58,   59,   60,   41,   62,   43,   44,   45,   41,
   28,  257,   44,  259,   45,   40,   61,  263,   61,   47,
  266,   58,   59,   60,   41,   62,   54,   44,   60,   41,
   62,   42,   44,  108,  102,  281,   47,   40,   44,  107,
   79,   80,  132,   60,   40,   62,   91,   92,   60,   77,
   62,  141,  120,   59,  122,  123,   40,  125,   59,   59,
  153,  154,  155,  281,  110,   59,  264,  135,  114,  162,
   59,  117,   59,  284,  257,   41,  259,   43,   40,   45,
  263,  127,  150,  266,  102,  268,   59,  279,  281,  107,
   40,  137,  110,   41,   41,   41,  114,  258,  281,  117,
  260,   41,  120,   59,  122,  123,  262,  125,   40,  127,
   59,  284,  267,   44,  257,   59,  259,  135,  266,  137,
  263,   59,   59,   59,  267,  268,   59,  275,  276,  277,
  278,   59,  150,  281,  267,   59,  269,  267,  281,  269,
   59,   59,  270,  271,  272,  273,   59,   59,  284,   44,
   44,   44,  284,  284,   59,  274,  281,  282,  283,  284,
  281,  282,  283,  281,  282,  283,  281,  282,  283,   59,
  267,  284,  269,  270,  271,  272,  273,  279,  280,  280,
  280,  284,  279,  280,   59,  284,   59,  279,  280,   59,
  267,   59,  269,  270,  271,  272,  273,  280,  280,  275,
  276,  277,  279,  280,   59,  281,   59,  279,  280,  280,
  267,  267,  269,  270,  271,  272,  273,  280,  280,   97,
   54,  123,  279,  280,   81,   83,   -1,   -1,  280,  280,
  267,   -1,  269,  270,  271,  272,  273,   -1,  270,  271,
  272,  273,  279,  280,   -1,   -1,   -1,  279,  280,   -1,
  281,  282,  283,  270,  271,  272,  273,   -1,  270,  271,
  272,  273,  279,  280,  257,   -1,  259,  279,  280,  257,
  263,  259,   -1,   -1,  267,  263,  257,  257,  259,  259,
  268,   -1,  263,  263,   -1,  265,  267,   -1,  281,  257,
   -1,  259,  257,  281,  259,  263,   -1,   -1,  263,  267,
  281,  281,  267,   -1,  257,  257,  259,  259,  266,   -1,
  263,  263,   -1,  281,  267,  267,  281,  275,  276,  277,
  278,   -1,   -1,  281,  275,  276,  277,  278,  281,  281,
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
"parametro : tipo ID",
"bloque_ejecutables_programa : BEGIN sentencias_ejecutables END ';'",
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
"factor : '-' factor",
"factor : invocacion_funcion",
};

//#line 163 "gramatica.y"

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

    private void changeSigned(int factor){
        //cambiar signo de la TS del factor
    }

    private void print(String text) {
        System.out.println(text);
    }

    private void generated(String gen) {
        this.lexico.generated(gen);
    }
//#line 461 "Parser.java"
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
//#line 26 "gramatica.y"
{ this.generated("nombre de programa"); }
break;
case 3:
//#line 27 "gramatica.y"
{ this.yyerror("El nombre del progrma debe ser un STRING no un ID"); }
break;
case 4:
//#line 30 "gramatica.y"
{ this.generated("bloque declarativo"); }
break;
case 5:
//#line 31 "gramatica.y"
{ this.generated("bloque declarativo"); }
break;
case 6:
//#line 33 "gramatica.y"
{ this.generated("sentencia declarativa"); }
break;
case 7:
//#line 34 "gramatica.y"
{ this.generated("sentencia declarativa"); }
break;
case 8:
//#line 35 "gramatica.y"
{ this.generated("sentencia declarativa"); }
break;
case 12:
//#line 38 "gramatica.y"
{ this.generated("tipo de variable"); }
break;
case 13:
//#line 41 "gramatica.y"
{ this.generated("encabezado de funcion"); }
break;
case 14:
//#line 44 "gramatica.y"
{ this.generated("lista de variables"); }
break;
case 15:
//#line 45 "gramatica.y"
{ this.generated("lista de variables"); }
break;
case 16:
//#line 48 "gramatica.y"
{ this.generated("parametro"); }
break;
case 17:
//#line 51 "gramatica.y"
{ this.generated("bloque ejecutable"); }
break;
case 20:
//#line 58 "gramatica.y"
{ this.generated("IF de una linea"); }
break;
case 21:
//#line 59 "gramatica.y"
{ this.generated("IF"); }
break;
case 22:
//#line 62 "gramatica.y"
{ this.generated("WHILE de una linea"); }
break;
case 24:
//#line 64 "gramatica.y"
{ this.generated("WHILE"); }
break;
case 26:
//#line 66 "gramatica.y"
{ this.yyerror("Faltan las sentencias ejecutables"); }
break;
case 27:
//#line 67 "gramatica.y"
{ this.yyerror("No se esperaban sentencias ejecutables despues del break"); }
break;
case 28:
//#line 68 "gramatica.y"
{ this.yyerror("Faltan las sentencias ejecutables"); }
break;
case 29:
//#line 71 "gramatica.y"
{ this.generated("sentencias ejecutable"); }
break;
case 30:
//#line 72 "gramatica.y"
{ this.generated("sentencias ejecutable"); }
break;
case 31:
//#line 75 "gramatica.y"
{ this.generated("sentencia ejecutable"); }
break;
case 32:
//#line 76 "gramatica.y"
{ this.generated("sentencia ejecutable"); }
break;
case 33:
//#line 77 "gramatica.y"
{ this.generated("sentencia ejecutable"); }
break;
case 34:
//#line 78 "gramatica.y"
{ this.generated("sentencia ejecutable"); }
break;
case 35:
//#line 81 "gramatica.y"
{ this.generated("asignacion"); }
break;
case 37:
//#line 85 "gramatica.y"
{ this.generated("invocacion a funcion"); }
break;
case 38:
//#line 88 "gramatica.y"
{ this.generated("estructura de control"); }
break;
case 39:
//#line 89 "gramatica.y"
{ this.generated("estructura de control"); }
break;
case 45:
//#line 105 "gramatica.y"
{ this.generated("impresion por pantalla"); print(val_peek(1).sval); }
break;
case 48:
//#line 110 "gramatica.y"
{ this.yyerror("Falta otra condicion despues del '||'"); }
break;
case 51:
//#line 115 "gramatica.y"
{ this.yyerror("Falta otra condicion despues del '&&'"); }
break;
case 54:
//#line 120 "gramatica.y"
{ this.yyerror("Falta expresion con la cual comparar"); }
break;
case 61:
//#line 127 "gramatica.y"
{ this.generated("RETURN"); }
break;
case 62:
//#line 128 "gramatica.y"
{ this.yyerror("Falta la expresion a retornar"); }
break;
case 63:
//#line 129 "gramatica.y"
{ this.yyerror("Falta un ';' al final de la sentencia"); }
break;
case 64:
//#line 132 "gramatica.y"
{ this.generated("POST condition"); }
break;
case 65:
//#line 133 "gramatica.y"
{ this.yyerror("Se esperaba POST al principio de la sentencia"); }
break;
case 66:
//#line 134 "gramatica.y"
{ this.yyerror("Falta ':' despues de POST"); }
break;
case 67:
//#line 135 "gramatica.y"
{ this.yyerror("Se esperaba '(' despues de ':'"); }
break;
case 68:
//#line 136 "gramatica.y"
{ this.yyerror("Falta la condicion del POST"); }
break;
case 69:
//#line 137 "gramatica.y"
{ this.yyerror("Se esperaba ')' despues de la condicion"); }
break;
case 70:
//#line 138 "gramatica.y"
{ this.yyerror("Se esperaba ',' despues de la condicion"); }
break;
case 71:
//#line 139 "gramatica.y"
{ this.yyerror("Falta el string despues de ','"); }
break;
case 72:
//#line 140 "gramatica.y"
{ this.yyerror("Se esperaba ';' al final de la linea"); }
break;
case 73:
//#line 144 "gramatica.y"
{ yyval.ival = val_peek(2).ival + val_peek(0).ival; }
break;
case 74:
//#line 145 "gramatica.y"
{ yyval.ival = val_peek(2).ival - val_peek(0).ival; }
break;
case 76:
//#line 149 "gramatica.y"
{ yyval.ival = val_peek(2).ival * val_peek(0).ival; }
break;
case 77:
//#line 150 "gramatica.y"
{ yyval.ival = val_peek(2).ival / val_peek(0).ival; }
break;
case 82:
//#line 157 "gramatica.y"
{ changeSigned(val_peek(0).ival); }
break;
//#line 818 "Parser.java"
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
