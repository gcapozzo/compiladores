package AnalizadorLexico;

import AnalizadorLexico.acciones_semanticas.AccionSemantica;
import AnalizadorLexico.acciones_semanticas.*;
import Utils.*;

public class States {
    /*
     * COL0= LETRA-{S} COL7= * COL14= = COL21= \n COL1= DIGITO COL8= / COL15= >
     * COL22= % COL2= _ COL9= ( COL16= < COL23= : COL3= OTRO COL10= ) COL17= &
     * COL23= : COL4= S COL11= , COL18= | COL24= $ COL5= + COL12= . COL19= tab COL6=
     * - COL13= ; COL20= blanc -1 = estado final
     */
    private int actualState = 0;
    private final int FINAL_STATE = -1;
    private AnalizadorLexico aLexico;
    private SymbolTable tSymbol;
    private SourceCode cFuente;

    //Creacion de las AS
    private AccionSemantica asGeneral = new AccionSemantica(this, cFuente, tSymbol);
    private AccionSemantica.InicBuffer AS1 = asGeneral.new InicBuffer();
    private AccionSemantica.AddChar AS2 = asGeneral.new AddChar();
    private AccionSemantica.GeneratorIdentifier AS3 = asGeneral.new GeneratorIdentifier();
    private AccionSemantica.GeneratorFloat AS4 = asGeneral.new GeneratorFloat();
    private AccionSemantica.GeneratorInteger AS6 = asGeneral.new GeneratorInteger();
    private AccionSemantica.AddCharBis AS2bis = asGeneral.new AddCharBis();
    private AccionSemantica.ResetBuffer AS13 = asGeneral.new ResetBuffer();
    private AccionSemantica.NewLine AS15 = asGeneral.new NewLine();
    private AccionSemantica.Reparadora AS20 = asGeneral.new Reparadora();
    private AccionSemantica.MultilineCharacter AS21 = asGeneral.new MultilineCharacter();
    private AccionSemantica.AddAndReturn AS22 =  asGeneral.new AddAndReturn();
    private AccionSemantica.TranslateToASCII AS33 = asGeneral.new TranslateToASCII();
    private AccionSemantica.GoToFinalState AS99 =  asGeneral.new GoToFinalState();

    //CREACION DE CELDAS PARA LA MATRIZ
    private Celda c1, c2, c3;
    private Celda c4;
    private Celda c5;
    private Celda c6;
    private Celda c7;
    private Celda c8;
    private Celda c9;
    private Celda c10;
    private Celda c11;
    private Celda c12;
    private Celda c13;
    private Celda c14;
    private Celda c15;
    private Celda c16;
    private Celda c40;
    private Celda c17;
    private Celda c18;
    private Celda c19;
    private Celda c20;
    private Celda c21;
    private Celda c23;
    private Celda c24;
    private Celda c26;
    private Celda c27;
    private Celda c28;
    private Celda c29;
    private Celda c30;

    private Celda c33;
    private Celda c34;

    private Celda cFinal;
    private final int MAX_FILAS = 17;
    private final int MAX_COL = 24;
    private Celda[][] matrix ;

    public States(AnalizadorLexico aLexico, SymbolTable tSymbol, SourceCode cFuente){
        this.aLexico = aLexico;
        this.tSymbol = tSymbol;
        this.cFuente = cFuente;
        this.createCeldas();
        matrix = this.loadMatrix();
    }

    private void createCeldas(){

        c1 = new Celda(1, AS1);
        c2 = new Celda(2, AS1);
        c3 = new Celda(0, AS15);
        c4 = new Celda(-1,AS22);
        c5 = new Celda(6, AS1);
        c6 = new Celda(4, AS1);
        c7 = new Celda(13, AS1);
        c8 = new Celda(11, AS1);
        c9 = new Celda(12, AS1);
        c10 = new Celda(14, AS1);
        c11 = new Celda(15, AS1);
        c12 = new Celda(0, AS15);
        c13 = new Celda(8, AS1);
        c14 = new Celda(16, AS1);
        c15 = new Celda(1, AS2);
        c16 = new Celda(-1, AS3);
        c40 = new Celda(-1, AS6);
        c17 = new Celda(3, AS2);
        c18 = new Celda(2, AS2);
        c19 = new Celda(5, AS2);
        c20 = new Celda(5, AS2bis);
        c21 = new Celda(-1, AS4);
        c23 = new Celda(17, AS2);
        c24 = new Celda(10,AS15);
        c26 = new Celda(0, AS13);
        c27 = new Celda(7, AS2);
        c28 = new Celda(8, AS2);
        c29 = new Celda(9, AS2);
        c30 = new Celda(-1, AS21);

        c33 = new Celda(10, AS15);
        c34 = new Celda(8, AS20);

        cFinal = new Celda(-1, AS99);

    }
    private Celda[][] loadMatrix() {
        Celda[][] aux= {
                /*0 */       {c1, c2, c1, c3, c1, c4, c4, c5, c4, c4, c4, c4, c6, c4, c7, c8, c9, c10, c11, c12, c12, c12, c13, c14, cFinal},
                /*1 */       {c15, c15, c15, c16, c15, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16},
                /*2 */       {c40, c18, c40, c40, c40, c40, c40, c40, c40, c40, c40, c40, c17, c40, c40, c40, c40, c40, c40, c40, c40, c40, c40, c40, c40},
                /*3 */       {c40, c17, c21, c21, c20, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21},
                /*4 */       {c21, c17, c21, c21, c20, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21},
                /*5 */       {c21, c19, c21, c21, c21, c23, c23, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21},
                /*6 */       {c16, c16, c16, c16, c16, c16, c16, c27, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16},
                /*7 */       {c27, c27, c27, c27, c27, c27, c27, c27, c27, c27, c27, c27, c27, c27, c27, c27, c27, c27, c27, c27, c27, c26, c27, c27, c27},
                /*8 */       {c28, c28, c28, c28, c28, c29, c28, c28, c28, c28, c28, c28, c28, c28, c28, c28, c28, c28, c28, c28, c28, c30, c30, c28, c28},
                /*9 */       {c28, c28, c28, c28, c28, c29, c28, c28, c28, c28, c28, c28, c28, c28, c28, c28, c28, c28, c28, c28, c28, c33, c30, c28, c28},
                /*10*/       {c34, c34, c34, c34, c34, c28, c34, c34, c34, c34, c34, c34, c34, c34, c34, c34, c34, c34, c34, c24, c24, c34, c34, c34, c34},
                /*11*/       {c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c4, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16},
                /*12*/       {c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c4, c4, c16, c16, c16, c16, c16, c16, c16, c16, c16},
                /*13*/       {c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c4, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16},
                /*14*/       {c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c4, c16, c16, c16, c16, c16, c16, c16},
                /*15*/       {c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c4, c16, c16, c16, c16, c16, c16},
                /*16*/       {c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c4, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16},
                /*17*/       {c21, c23, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21}
        };
        return aux;

    }

    public boolean isInFinalState(){return actualState == FINAL_STATE;}


    public void goToLastState(){
        actualState = FINAL_STATE;
        setTokenToLexic(0,"$");
    }

    public void reset(){
        actualState = 0;
        //AS13.execute(' ');
    }

    public void setTokenToLexic(int token, String lexema){
        aLexico.setTokenGenerado(token,lexema);
    }

    public int goToNextState(char c) {
        int column;
        if (Character.isLetter(c) && c != 'S') {
            column = 0;
        } else {
            switch (c) {
                case '0': case '1': case '2': case '3': case '4':
                case '5': case '6': case '7': case '8':
                case '9': //incluye 0,1,2,3,4,5,6,7,8
                    column = 1;
                    break;

                case '_':
                    column = 2;
                    break;

                case 'S':
                    column = 4;
                    break;

                case '+':
                    column = 5;
                    break;

                case '-':
                    column = 6;
                    break;

                case '*':
                    column = 7;
                    break;

                case '/':
                    column = 8;
                    break;

                case '(':
                    column = 9;
                    break;

                case ')':
                    column = 10;
                    break;

                case ',':
                    column = 11;
                    break;

                case '.':
                    column = 12;
                    break;

                case ';':
                    column = 13;
                    break;

                case '=':
                    column = 14;
                    break;

                case '>':
                    column = 15;
                    break;

                case '<':
                    column = 16;
                    break;

                case '&':
                    column = 17;
                    break;

                case '|':
                    column = 18;
                    break;

                case '\t': // tabulation
                    column = 19;
                    break;

                case ' ': // blank space
                    column = 20;
                    break;

                case '\n':
                    column = 21;
                    break;

                case '%':
                    column = 22;
                    break;

                case ':':
                    column = 23;
                    break;

                case '$':
                    column = 24;
                    break;

                default:
                    column = 3;
                    break;
            }
        }

        if(actualState != FINAL_STATE){
            matrix[actualState][column].executeAS(c);
            actualState = matrix[actualState][column].SiguienteEstado();
        }
        return actualState;

    }


}
