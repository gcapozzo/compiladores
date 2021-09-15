package AnalizadorLexico;

import AnalizadorLexico.Utils.SymbolTable;
import AnalizadorLexico.acciones_semanticas.*;

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
    private boolean endFile = false;
    //CREACION DE CELDAS PARA LA MATRIZ
    //FILA DEL ESTADO 0
    private static Celda c1 = new Celda(1); /* ADD AS1*/ //SE REPITE EN LAS POS 0-0, 0-2, 0-4,
    private static Celda c2 = new Celda(2); /* ADD AS1*/ //SE REPITE EN LAS POS 0-1
    private static Celda c3 = new Celda(0); /* ADD AS0*/ //SE REPITE EN LAS POS 0-3
    private static Celda c4 = new Celda(-1); /* ADD AS0*/ //SE REPITE EN LAS POS 0-5, 0-6, 0-8, 0-9, 0-10, 0-11, 0-13, 0-24
    private static Celda c5 = new Celda(6); /* ADD AS1*/ //SE REPITE EN LAS POS 0-7
    private static Celda c6 = new Celda(4); /* ADD AS1*/ //SE REPITE EN LAS POS 0-12
    private static Celda c7 = new Celda(13); /* ADD AS1*/ //SE REPITE EN LAS POS 0-14
    private static Celda c8 = new Celda(11); /* ADD AS1*/ //SE REPITE EN LAS POS 0-15
    private static Celda c9 = new Celda(12); /* ADD AS1*/ //SE REPITE EN LAS POS 0-16
    private static Celda c10 = new Celda(14); /* ADD AS1*/ //SE REPITE EN LAS POS 0-17
    private static Celda c11 = new Celda(15); /* ADD AS1*/ //SE REPITE EN LAS POS 0-18
    private static Celda c12 = new Celda(0); /* ADD AS15*/ //SE REPITE EN LAS POS 0-19, 0-20, 0-21
    private static Celda c13 = new Celda(8); /* ADD AS1*/ //SE REPITE EN LAS POS 0-22
    private static Celda c14 = new Celda(16); /* ADD AS1*/ //SE REPITE EN LAS POS 0-23

    //FILA DEL ESTADO 1
    private static Celda c15 = new Celda(1); /* ADD AS2*/ //SE REPITE EN LAS POS 1-0, 1-1, 1-2, 1-4
    private static Celda c16 = new Celda(-1); /* ADD AS3*/ //SE REPITE EN LAS DEMAS POS

    //FILA DEL ESTADO 2 Y 3
    private static Celda c40 = new Celda(-1); /* ADD AS6*/ //SE REPITE EN TODAS LA POS MENOS 2-1, 3-2, 3-4
    private static Celda c17 = new Celda(3); /* ADD AS7*/ //SE REPITE EN LAS POS 2-12
    private static Celda c18 = new Celda(2); /* ADD AS2*/ //SE REPITE EN LAS POS 2-1
    private static Celda c19 = new Celda(3); /* ADD AS2*/ //SE REPITE EN LAS POS 3-1, 4-1
    private static Celda c20 = new Celda(5); /* ADD AS2*/ //SE REPITE EN LAS POS 3-4, 4-4

    //FILA DE LOS ESATDOS 4 Y 5
    private static Celda c21 = new Celda(-1); /* ADD AS4*/ //SE REPITE EN TODAS LA DE LA FILA 4 Y 5 MENOS EN 4-2, 4-4, 5-2, 5-5 Y 5-6
    private static Celda c22 = new Celda(5); /* ADD AS2*/ //SE REPITE EN LAS POS 5-2
    private static Celda c23 = new Celda(17); /* ADD AS2*/ //SE REPITE EN LAS POS 5-5 Y 5-6

    //FILA DEL ESTADO 6
    private static Celda c24 = new Celda(7); /* ADD AS3*/ //SE REPITE EN TODA LA FILA 6 MENOS EN 6-7
    private static Celda c25 = new Celda(-1); /* ADD AS2*/ //SE REPITE EN LAS POS 6-7

    //FILA DEL ESATDO 7
    private static Celda c26 = new Celda(-1); /* ADD AS13*/ //SE REPITE EN LAS POS 7-21
    private static Celda c27 = new Celda(7); /* ADD AS2*/ //SE REPITE EN TODA LA FILA 7 MENOS EN 7-21

    //FILA DEL ESTADO 8
    private static Celda c28 = new Celda(8); /* ADD AS17*/ //SE REPITE EN TODA LA FILA 8 MENOS 8-5, 8-21, 8-22
    private static Celda c29 = new Celda(9); /* ADD AS17*/ //SE REPITE EN LAS POS 8-5
    private static Celda c30 = new Celda(-1); /* ADD AS21*/ //SE REPITE EN LAS POS 8-21, 8-22

    //FILA DEL ESATDO 9
    private static Celda c31 = new Celda(8); /* ADD AS17*/ //SE REPITE EN TODA LA FILA 9 MENOS 9-5, 9-21
    private static Celda c32 = new Celda(9); /* ADD AS17*/ //SE REPITE EN LAS POS 9-5
    private static Celda c33 = new Celda(10); /* ADD AS19*/ //SE REPITE EN LAS POS 9-21

    //FILA DEL ESTDO 10
    private static Celda c34 = new Celda(-1); /* ADD AS2*/ //SE REPITE EN TODA LA FILA 10 MENOS 9-5
    private static Celda c35 = new Celda(8); /* ADD AS17*/ //SE REPITE EN LAS POS 9-5

    //FILA DEL ESTADO 11 AL 16
    private static Celda c37 = new Celda(-1); /* ADD AS22*/ //SE REPITE EN LAS POS 11-14, 12-14, 12-15, 13-14, 14-17, 15-18, 16-14
    private static Celda c36 = new Celda(-1); /* ADD AS3*/ //SE REPITE EN CADA FILA EN EL RESTO

    //FILA DEL ESTADO 17
    private static Celda c38 = new Celda(17); /* ADD AS2*/ //SE REPITE EN LAS POS 17-2
    private static Celda c39 = new Celda(-1); /* ADD AS4*/ //SE REPITE EN TODA LA FILA 17 MENOS 17-2


    //FIN DE ARCHIVO
    private static Celda cFinal = new Celda(-2); /* ADD AS0??*/ //SOLO PARA CUANDO LEO EL $ ESTANDO EN EL ESATDO 0


    public static final Celda[][] matrix = {
            /*0 */       {  c1,  c2,  c1,  c3,  c1,  c4,  c4,  c5,  c4,  c4,  c4,  c4,  c6,  c4,  c7,  c8,  c9, c10, c11, c12, c12, c12, c13, c14,  cFinal },
            /*1 */       { c15, c15, c15, c16, c15, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16, c16 },
            /*2 */       { c40, c18, c40, c40, c40, c40, c40, c40, c40, c40, c40, c40, c17, c40, c40, c40, c40, c40, c40, c40, c40, c40, c40, c40, c40 },
            /*3 */       { c21, c18, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21 },
            /*4 */       { c21, c18, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21 },
            /*5 */       { c21, c22, c21, c21, c21, c23, c23, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21, c21 },
            /*6 */       { c24, c24, c24, c24, c24, c24, c24, c25, c24, c24, c24, c24, c24, c24, c24, c24, c24, c24, c24, c24, c24, c24, c24, c24, c24 },
            /*7 */       { c27, c27, c27, c27, c27, c27, c27, c27, c27, c27, c27, c27, c27, c27, c27, c27, c27, c27, c27, c27, c27, c26, c27, c27, c27 },
            /*8 */       { c28, c28, c28, c28, c28, c29, c28, c28, c28, c28, c28, c28, c28, c28, c28, c28, c28, c28, c28, c28, c28, c30, c30, c28, c28 },
            /*9 */       { c31, c31, c31, c31, c31, c32, c31, c31, c31, c31, c31, c31, c31, c31, c31, c31, c31, c31, c31, c31, c31, c33, c31, c31, c31 },
            /*10*/       { c34, c34, c34, c34, c34, c35, c34, c34, c34, c34, c34, c34, c34, c34, c34, c34, c34, c34, c34, c34, c34, c34, c34, c34, c34 },
            /*11*/       { c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c37, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36 },
            /*12*/       { c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c37, c37, c36, c36, c36, c36, c36, c36, c36, c36, c36 },
            /*13*/       { c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c37, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36 },
            /*14*/       { c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c37, c36, c36, c36, c36, c36, c36, c36 },
            /*15*/       { c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c37, c36, c36, c36, c36, c36, c36 },
            /*16*/       { c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36, c37, c36, c36, c36, c36, c36, c36, c36, c36, c36, c36 },
            /*17*/       { c39, c39, c38, c39, c39, c39, c39, c39, c39, c39, c39, c39, c39, c39, c39, c39, c39, c39, c39, c39, c39, c39, c39, c39, c39 } };

    public States(AnalizadorLexico aLexico, SymbolTable tSymbol){
        this.aLexico = aLexico;
        this.tSymbol = tSymbol;
    }
    public boolean isInFinalState(){return actualState == FINAL_STATE;}
    public void changeEndFile(){
        this.endFile = !this.endFile;
    }
    public void goToLastState(){actualState = FINAL_STATE;}

    public void reset(){actualState = 0;}

    public void setTokenToLexic(int token, String lexema){
        aLexico.setTokenGenerado(token, lexema);
    }

    public int goToNextState(char c) {
        int column;
        if (Character.isLetter(c) && c != 'S') {
            column = 0;
        } else {
            switch (c) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
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

        /*CAUNDO SE IMPLEMENTEN LAS AS, SE DESCOMENTA ESTO ASI SE EJECUTAN!!!

        LA IDEA ES QUE LAS AS VAYAN COMPLETANDO EL BUFFER Y QUE CUANDO SE LLEGUE A ALGUNA AS QUE VA AL ESTADO FINAL, ESTA DEVUELVA EL BUFFER Y EL TIPO DE ALGUINA MANERA PARA PODER ALMACENARLO EN LA TABLA DE SIMBOLOS.

        matrix[actualState][column].executeAS(buffer, c);
        */
        matrix[actualState][column].executeAS(c);
        return matrix[actualState][column].SiguienteEstado();

    }


}
