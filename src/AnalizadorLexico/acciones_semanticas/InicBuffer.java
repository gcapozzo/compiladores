package AnalizadorLexico.acciones_semanticas;

public class InicBuffer extends AccionSemantica{
    //Accion Semantica 1: Inicializar buffer y agg el caracter leido
    @Override
    public void execute(char c) {
        inicString();
        if(c != '%')
            concatenateChar(c);
    }
}
