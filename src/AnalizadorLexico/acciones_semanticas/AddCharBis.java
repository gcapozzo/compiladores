package AnalizadorLexico.acciones_semanticas;

import Utils.SourceCode;

public class AddCharBis extends AccionSemantica{
    //Accion Semantica 2bis: Agg al buffer una 'e' en vez de la 'S' que vendría
    //para hacer más facil el manejo del exponente float

    @Override
    public void execute(char c) {
        concatenateChar('e');

    }
}
