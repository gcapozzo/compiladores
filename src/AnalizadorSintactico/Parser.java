package AnalizadorSintactico;//### This file created by BYACC 1.8(/Java extension  1.15)
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
import java.lang.Math;
import java.io.*;
import java.util.StringTokenizer;
import AnalizadorLexico.AnalizadorLexico;
import Utils.ErrorLog;
//#line 23 "Parser.java"




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
    0,    1,    2,    2,    4,    4,    4,    5,    5,    5,
    5,    9,    6,    6,    7,   10,   10,    3,    8,    8,
   14,   14,   15,   15,   15,   15,   15,   15,   15,   11,
   11,   11,   11,   16,   16,   17,   18,   18,   21,   21,
   23,   24,   22,   19,   25,   25,   25,   26,   26,   26,
   27,   27,   27,   28,   28,   28,   28,   28,   28,   12,
   12,   12,   13,   13,   13,   13,   13,   13,   13,   13,
   13,   20,   20,   20,   29,   29,   29,   30,   30,   30,
   30,   30,
};
final static short yylen[] = {                            2,
    3,    3,    1,    2,    3,    9,    5,    1,    1,    1,
    1,    5,    1,    3,    2,    1,    2,    4,    6,    5,
    1,    4,    1,    2,    4,    6,    6,    7,    5,    2,
    2,    2,    2,    3,    3,    4,    1,    1,    1,    1,
    7,    9,    6,    4,    3,    1,    2,    3,    1,    2,
    3,    1,    2,    1,    1,    1,    1,    1,    1,    5,
    2,    2,    8,    7,    7,    7,    7,    7,    7,    7,
    7,    3,    3,    1,    3,    3,    1,    1,    1,    1,
    2,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    8,    9,   10,    0,   11,    0,
    3,    0,    2,    0,    0,    1,    4,    0,   13,    0,
    0,    0,    0,    0,    0,    0,   16,    0,    0,    0,
    0,   37,   38,   39,   40,    0,    5,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   17,   30,   31,   32,
   33,    0,   14,    0,    7,    0,   79,   80,    0,   82,
    0,    0,    0,    0,    0,   77,    0,    0,   35,    0,
    0,    0,   18,    0,    0,   81,    0,    0,    0,    0,
    0,   56,   57,   58,   59,   54,   55,    0,    0,    0,
    0,   44,   15,   36,    0,    0,    0,    0,    0,    0,
    0,    0,   75,   76,    0,    0,   12,    0,    0,   23,
   43,    0,   21,    0,    0,    0,    0,    0,   24,    0,
    0,   41,    0,    6,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   25,    0,   22,   42,   61,    0,    0,
    0,    0,    0,    0,   29,    0,    0,    0,    0,   20,
    0,    0,    0,    0,   27,   26,    0,    0,    0,    0,
    0,    0,   19,   28,   60,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   65,   67,   69,    0,   70,   68,   66,
   64,   63,
};
final static short yydgoto[] = {                          2,
    3,   10,   16,   11,   12,   20,   72,  116,   40,   26,
   27,  131,  144,  114,  111,   28,   60,   30,   31,   61,
   32,   33,   34,   35,   62,   63,   64,   88,   65,   66,
};
final static short yysindex[] = {                      -275,
 -270,    0,  138,  -42,    0,    0,    0, -226,    0,  118,
    0, -229,    0,   -4,  -53,    0,    0, -223,    0,   -3,
    5,   39,   59,   61,  -38,  -67,    0,   16,   48,   56,
   57,    0,    0,    0,    0,   79,    0, -161, -142,   75,
   50,   50, -148,  -44,    5,   78,    0,    0,    0,    0,
    0,    5,    0,   83,    0,   99,    0,    0,   50,    0,
   35,  -19, -127,  -47,   34,    0,   -2,  112,    0,   35,
 -126,  115,    0,  116,    5,    0,   50,   50,   50,  -99,
   50,    0,    0,    0,    0,    0,    0,   50,   50,   50,
  -95,    0,    0,    0,  138,  127,   34,   34, -127, -119,
  -47,   35,    0,    0,  -54,  131,    0,   88,  114,    0,
    0,  -53,    0, -225,  -53,  117,  126,  -79,    0,   95,
  -54,    0,   96,    0,  107,  132,  134,  136,  -87,  -40,
  -52,  139,  108,    0,  123,    0,    0,    0,   50,   35,
  140,  -24,  142,  -84,    0,  148,  149,  124,   55,    0,
   50,  -37,   50,  152,    0,    0,  154,  157,   -1,  -34,
   18,   19,    0,    0,    0,  175,  177,  -20,  178,  186,
  -98,  -83,  -32,  -51,  -50,  -22,  172,  173,  176,  -55,
  191,  206,  207,    0,    0,    0,  208,    0,    0,    0,
    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -16,    0,    0,    0,    0,
   49,    0,   10,  -21,    4,    0,    0,    0,    0,  211,
    0,    0,    0,    0,    0,    0,    0,    0,   29,    0,
  -11,    0,    0,    0,    0,    0,    0,   64,    0,    0,
    0,    0,    0,    0,    0,    0,   24,   44,   30,    0,
    9,   69,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -49,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   20,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
    0,  190,    0,    8,   73,    0,  220,    0,    0,   62,
   12,    0,    0,  171,    0,    0,   46,    0,    0,   33,
    0,    0,    0,    0,  -10,  221,  224,    0,   14,   38,
};
final static int YYTABLESIZE=419;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        139,
   59,   45,  160,  188,   59,  143,  167,   59,   62,    1,
   59,  180,   86,    4,   87,  151,   13,   17,  138,   49,
  173,   80,   49,  174,   78,   78,   78,   78,   78,   50,
   78,   67,   50,  152,   18,  121,  122,   47,   91,  166,
   38,   78,   78,   78,   74,   78,   74,   74,   74,   48,
   46,   19,   48,   46,   14,   37,   21,   36,  169,  170,
   29,   74,   74,   74,   73,   74,   73,   73,   73,   47,
   45,   29,   47,   45,   48,   89,   70,   78,   41,   77,
   90,   73,   73,   73,   72,   73,   72,   72,   72,   52,
   97,   98,   52,   39,   59,  158,   76,   78,   42,   77,
   43,   72,   72,   72,   53,   72,   49,   53,   52,   51,
   52,  110,   51,   17,   50,   51,  113,   71,   52,   53,
  102,   54,   75,   53,   71,   53,  103,  104,   51,   47,
   51,   47,  113,   55,   47,   68,   73,   22,   45,   23,
  159,  161,  162,   24,   47,   29,  108,   96,  109,  168,
   29,   81,   92,   29,   93,   94,   95,   29,  100,   47,
   29,   25,  140,   29,  105,   29,   29,  107,   29,  118,
   29,  149,  119,  120,  137,  124,  123,   22,   29,   23,
   29,  153,  154,   24,  125,  177,  133,  126,  127,   22,
  134,   23,  135,   29,  136,   24,  148,  145,  150,   46,
  178,   25,   22,   22,   23,   23,  155,  156,   24,   24,
  163,  112,  164,   25,  141,  165,  142,   62,  171,   62,
  172,  175,   82,   83,   84,   85,   25,   25,  187,  176,
  184,  185,  181,  182,  186,   44,   56,   57,   58,   69,
   56,   57,   58,   56,   57,   58,   56,   57,   58,  189,
   78,  179,   78,   78,   78,   78,   78,   49,   49,   79,
   79,  183,   78,   78,  190,  191,  192,   50,   50,   34,
   74,   74,   74,   74,   74,   74,   74,   79,   79,    5,
    6,    7,   74,   74,  106,    9,   71,   48,   48,   46,
   73,  129,   73,   73,   73,   73,   73,   79,   79,   99,
    0,    0,   73,   73,  101,    0,    0,    0,   47,   45,
   72,    0,   72,   72,   72,   72,   72,    0,   52,   52,
   52,   52,   72,   72,    0,    0,    0,   52,   52,    0,
   56,   57,   58,   53,   53,   53,   53,    0,   51,   51,
   51,   51,   53,   53,   22,    0,   23,   51,   51,    0,
   24,   22,   22,   23,   23,  117,    0,   24,   24,    0,
  130,  128,    0,   22,   22,   23,   23,    0,   25,   24,
   24,    0,    0,  132,  146,   25,   25,    0,    0,   22,
   22,   23,   23,   15,    0,   24,   24,   25,   25,  147,
  157,    0,    5,    6,    7,    8,  115,    0,    9,    0,
    0,    0,    0,   25,   25,    5,    6,    7,    8,    0,
    0,    9,    5,    6,    7,    8,    0,    0,    9,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   45,   40,   40,   59,   45,   58,   41,   45,   58,  285,
   45,   44,   60,  284,   62,   40,   59,   10,   59,   41,
   41,   41,   44,   44,   41,   42,   43,   44,   45,   41,
   47,   42,   44,   58,  264,  261,  262,   26,   41,   41,
   44,   58,   59,   60,   41,   62,   43,   44,   45,   41,
   41,  281,   44,   44,  281,   59,   61,  281,   41,   41,
   15,   58,   59,   60,   41,   62,   43,   44,   45,   41,
   41,   26,   44,   44,   59,   42,   44,   43,   40,   45,
   47,   58,   59,   60,   41,   62,   43,   44,   45,   41,
   77,   78,   44,   21,   45,   41,   59,   43,   40,   45,
   40,   58,   59,   60,   41,   62,   59,   44,   60,   41,
   62,  100,   44,  106,   59,   59,  105,   45,   40,  281,
   88,  264,   40,   60,   52,   62,   89,   90,   60,  118,
   62,  120,  121,   59,  123,  284,   59,  257,   40,  259,
  151,  152,  153,  263,  133,  100,  266,   75,  268,  160,
  105,  279,   41,  108,  281,   41,   41,  112,  258,  148,
  115,  281,  130,  118,  260,  120,  121,   41,  123,  108,
  125,  139,   59,  112,  262,   59,  115,  257,  133,  259,
  135,   40,  267,  263,   59,  284,  125,  267,  268,  257,
   59,  259,   59,  148,   59,  263,  135,   59,   59,  267,
  284,  281,  257,  257,  259,  259,   59,   59,  263,  263,
   59,  266,   59,  281,  267,   59,  269,  267,   44,  269,
   44,   44,  270,  271,  272,  273,  281,  281,  284,   44,
   59,   59,  284,  284,   59,  274,  281,  282,  283,  284,
  281,  282,  283,  281,  282,  283,  281,  282,  283,   59,
  267,  284,  269,  270,  271,  272,  273,  279,  280,  280,
  280,  284,  279,  280,   59,   59,   59,  279,  280,   59,
  267,   52,  269,  270,  271,  272,  273,  280,  280,  275,
  276,  277,  279,  280,   95,  281,  267,  279,  280,  280,
  267,  121,  269,  270,  271,  272,  273,  280,  280,   79,
   -1,   -1,  279,  280,   81,   -1,   -1,   -1,  280,  280,
  267,   -1,  269,  270,  271,  272,  273,   -1,  270,  271,
  272,  273,  279,  280,   -1,   -1,   -1,  279,  280,   -1,
  281,  282,  283,  270,  271,  272,  273,   -1,  270,  271,
  272,  273,  279,  280,  257,   -1,  259,  279,  280,   -1,
  263,  257,  257,  259,  259,  268,   -1,  263,  263,   -1,
  265,  267,   -1,  257,  257,  259,  259,   -1,  281,  263,
  263,   -1,   -1,  267,  267,  281,  281,   -1,   -1,  257,
  257,  259,  259,  266,   -1,  263,  263,  281,  281,  267,
  267,   -1,  275,  276,  277,  278,  266,   -1,  281,   -1,
   -1,   -1,   -1,  281,  281,  275,  276,  277,  278,   -1,
   -1,  281,  275,  276,  277,  278,   -1,   -1,  281,
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
"sentencias_ejecutables : sentencia_ejecutable",
"sentencias_ejecutables : sentencias_ejecutables sentencia_ejecutable",
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

//#line 161 "gramatica.y"

	private AnalizadorLexico lexico;
	private ErrorLog errors;
    public Parser(AnalizadorLexico aLex, ErrorLog errors){
        this.lexico = aLex;
        this.errors = errors;
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

//#line 454 "Parser.java"
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
//#line 22 "gramatica.y"
{System.err.println("Se reconocio el programa");}
break;
case 44:
//#line 103 "gramatica.y"
{ print(val_peek(1).sval); }
break;
case 47:
//#line 108 "gramatica.y"
{ yyerror("Falta otra condicion despues del '||'"); }
break;
case 50:
//#line 113 "gramatica.y"
{ yyerror("Falta otra condicion despues del '&&'"); }
break;
case 53:
//#line 118 "gramatica.y"
{ yyerror("Falta expresion con la cual comparar"); }
break;
case 61:
//#line 126 "gramatica.y"
{ yyerror("Falta la expresion a retornar"); }
break;
case 62:
//#line 127 "gramatica.y"
{ yyerror("Falta un ';' al final de la sentencia"); }
break;
case 64:
//#line 131 "gramatica.y"
{ yyerror("Se esperaba POST al principio de la sentencia"); }
break;
case 65:
//#line 132 "gramatica.y"
{ yyerror("Falta ':' despues de POST"); }
break;
case 66:
//#line 133 "gramatica.y"
{ yyerror("Se esperaba '(' despues de ':'"); }
break;
case 67:
//#line 134 "gramatica.y"
{ yyerror("Falta la condicion del POST"); }
break;
case 68:
//#line 135 "gramatica.y"
{ yyerror("Se esperaba ')' despues de la condicion"); }
break;
case 69:
//#line 136 "gramatica.y"
{ yyerror("Se esperaba ',' despues de la condicion"); }
break;
case 70:
//#line 137 "gramatica.y"
{ yyerror("Falta el string despues de ','"); }
break;
case 71:
//#line 138 "gramatica.y"
{ yyerror("Se esperaba ';' al final de la linea"); }
break;
case 72:
//#line 142 "gramatica.y"
{ yyval.ival = val_peek(2).ival + val_peek(0).ival; }
break;
case 73:
//#line 143 "gramatica.y"
{ yyval.ival = val_peek(2).ival - val_peek(0).ival; }
break;
case 75:
//#line 147 "gramatica.y"
{ yyval.ival = val_peek(2).ival * val_peek(0).ival; }
break;
case 76:
//#line 148 "gramatica.y"
{ yyval.ival = val_peek(2).ival / val_peek(0).ival; }
break;
case 81:
//#line 155 "gramatica.y"
{ changeSigned(val_peek(0).ival); }
break;
//#line 683 "Parser.java"
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
