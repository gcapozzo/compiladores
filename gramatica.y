%{
package AnalizadorSintactico;
import java.lang.Math;
import java.io.*;
import java.util.StringTokenizer;
import AnalizadorLexico.AnalizadorLexico;
import Utils.ErrorLog;
%}

/* YACC Declarations */
%token WHILE DO IF THEN ELSE ENDIF PRINT FUNC RETURN BEGIN END BREAK POST MENOR_IGUAL MAYOR_IGUAL DISTINTO IGUAL ASIGN INT SINGLE STRING TYPEDEF AND OR ID CONST_SINGLE CONST_INT CONST_STR PROGRAM

%start programa

%left '-'
%left '+'
%left '*'
%left '/'

/* Grammar follows */
%%

programa : nombre_programa bloque_declarativas bloque_ejecutables_programa {System.err.println("Se reconocio el programa");}
;

nombre_programa : PROGRAM CONST_STR ';' { this.generated("nombre de programa"); }
                | PROGRAM ID ';' { this.yyerror("El nombre del progrma debe ser un STRING no un ID"); }
;

bloque_declarativas : sentencia_declarativa { this.generated("bloque declarativo"); }
                    | bloque_declarativas sentencia_declarativa { this.generated("bloque declarativo"); }

sentencia_declarativa : tipo lista_variables ';' { this.generated("sentencia declarativa"); }
                    | tipo FUNC ID '(' parametro ')' bloque_declarativas bloque_ejecutables_funcion ';' { this.generated("sentencia declarativa"); }
                    | TYPEDEF ID '=' encabezado_funcion ';' { this.generated("sentencia declarativa"); }
;

tipo : INT | SINGLE | STRING | ID { this.generated("tipo de variable"); }
;

encabezado_funcion : tipo FUNC '(' tipo ')' { this.generated("encabezado de funcion"); }
;

lista_variables : ID { this.generated("lista de variables"); }
		| lista_variables',' ID { this.generated("lista de variables"); }
;

parametro : tipo ID { this.generated("parametro"); }
;

bloque_ejecutables_programa : BEGIN sentencias_ejecutables END ';' { this.generated("bloque ejecutable"); } /* creo que el ; no van en los bloques de ejecutables*/
;

bloque_ejecutables_funcion : BEGIN sentencias_ejecutables retorno post_condicion END ';' /* creo que el ; no van en los bloques de ejecutables*/
                            | BEGIN sentencias_ejecutables retorno END ';' /* creo que el ; no van en los bloques de ejecutables*/
;

bloque_ejecutables_if: sentencia_ejecutable { this.generated("IF de una linea"); }
                    | BEGIN sentencias_ejecutables END ';' { this.generated("IF"); }/* creo que el ; no van en los bloques de ejecutables*/
;

bloque_ejecutables_while: sentencia_ejecutable { this.generated("WHILE de una linea"); }
			| BREAK ';'
			| BEGIN sentencias_ejecutables END ';' { this.generated("WHILE"); }/* creo que el ; no van en los bloques de ejecutables*/
			| BEGIN sentencias_ejecutables BREAK ';' END ';' /* creo que el ; no van en los bloques de ejecutables*/
			| BEGIN BREAK ';' sentencias_ejecutables END ';' { this.yyerror("Faltan las sentencias ejecutables"); }/* creo que el ; no van en los bloques de ejecutables*/
			| BEGIN sentencias_ejecutables BREAK ';' sentencias_ejecutables END ';' { this.yyerror("No se esperaban sentencias ejecutables despues del break"); } /* creo que el ; no van en los bloques de ejecutables*/
			| BEGIN BREAK ';' END ';' { this.yyerror("Faltan las sentencias ejecutables"); } /* creo que el ; no van en los bloques de ejecutables*/
;

sentencias_ejecutables : sentencia_ejecutable { this.generated("sentencias ejecutable"); }
                        | sentencias_ejecutables sentencia_ejecutable { this.generated("sentencias ejecutable"); }
;

sentencia_ejecutable: asignacion ';' { this.generated("sentencia ejecutable"); }
                  | invocacion_funcion ';' { this.generated("sentencia ejecutable"); }
                  | estructura_control ';' { this.generated("sentencia ejecutable"); }
                  | impresion_pantalla ';' { this.generated("sentencia ejecutable"); }
;

asignacion : ID ASIGN expresion_aritmetica { this.generated("asignacion"); }
           | ID ASIGN CONST_STR
;

invocacion_funcion : ID '(' parametro ')' { this.generated("invocacion a funcion"); }
;

estructura_control : sentencia_if { this.generated("estructura de control"); }
                    | sentencia_while { this.generated("estructura de control"); }
;

sentencia_if : if_simple /* se podria sacar esto, cada uno solo tiene una definicion*/
		| if_completo
;

if_simple : IF '(' condicion ')' THEN bloque_ejecutables_if ENDIF
;

if_completo : IF '(' condicion ')' THEN bloque_ejecutables_if ELSE bloque_ejecutables_if ENDIF
;

sentencia_while : WHILE '(' condicion ')' DO bloque_ejecutables_while
;

impresion_pantalla : PRINT '(' CONST_STR ')' { this.generated("impresion por pantalla"); print($3.sval); }
;

condicion : condicion OR expresion_booleana
	| expresion_booleana
	| condicion OR { this.yyerror("Falta otra condicion despues del '||'"); }
;

expresion_booleana : expresion_booleana AND comparacion
		| comparacion
		| expresion_booleana AND { this.yyerror("Falta otra condicion despues del '&&'"); }
;

comparacion : comparacion comparador expresion_aritmetica
	| expresion_aritmetica
    	| comparacion comparador { this.yyerror("Falta expresion con la cual comparar"); }
;

comparador : '<' | '>' | MENOR_IGUAL | MAYOR_IGUAL | DISTINTO | IGUAL
;


retorno : RETURN '(' expresion_aritmetica ')' ';' { this.generated("RETURN"); }
        | RETURN ';' { this.yyerror("Falta la expresion a retornar"); }
        | RETURN expresion_aritmetica { this.yyerror("Falta un ';' al final de la sentencia"); }
;

post_condicion : POST ':' '(' condicion ')' ',' CONST_STR ';' { this.generated("POST condition"); }
       		| ':' '(' condicion ')' ',' CONST_STR ';' { this.yyerror("Se esperaba POST al principio de la sentencia"); }
		| POST '(' condicion ')' ',' CONST_STR ';' { this.yyerror("Falta ':' despues de POST"); }
		| POST ':' condicion ')' ',' CONST_STR ';' { this.yyerror("Se esperaba '(' despues de ':'"); }
		| POST ':' '(' ')' ',' CONST_STR ';' { this.yyerror("Falta la condicion del POST"); }
		| POST ':' '(' condicion ',' CONST_STR ';' { this.yyerror("Se esperaba ')' despues de la condicion"); }
		| POST ':' '(' condicion ')' CONST_STR ';'  { this.yyerror("Se esperaba ',' despues de la condicion"); }
		| POST ':' '(' condicion ')' ',' ';'  { this.yyerror("Falta el string despues de ','"); }
		| POST ':' '(' condicion ')' ',' CONST_STR { this.yyerror("Se esperaba ';' al final de la linea"); }

;

expresion_aritmetica : expresion_aritmetica '+' termino { $$.ival = $1.ival + $3.ival; }
                        | expresion_aritmetica '-' termino { $$.ival = $1.ival - $3.ival; }
                        | termino
;

termino : termino '*' factor { $$.ival = $1.ival * $3.ival; }
        | termino '/' factor { $$.ival = $1.ival / $3.ival; }
        | factor
;

factor : ID
        | CONST_SINGLE
        | CONST_INT
        | '-' factor { changeSigned($2.ival); }
        | invocacion_funcion
;


%%

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
