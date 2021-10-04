package AnalizadorLexico.acciones_semanticas;
public class AddChar extends AccionSemantica{
    //Accion Semantica 2: Agrega un char al StringBuffer

    @Override
    public void execute(char c) {
        concatenateChar(c);

    }
}
