package AnalizadorLexico.acciones_semanticas;

public class ResetBuffer extends AccionSemantica{
    //Accion Semantica 13: Eliminar el contenido del buffer

    @Override
    public void execute(char c) {
        inicString();
    }
}
