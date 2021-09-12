#COMPILADOR VERSION ALPHA 0.0.0.0.0.0.1

Hay 3 archivos *.java: **AccionSemantica**, **Celda** y **State**.

- La **AccionSemantica** es una interfaz la cual tiene un método execute que recibe como parámetro un _caracter_ y un _buffer_. Cada AS decide si agregarle el char al buffer o no. En el archivo _States.java_ dejé una lista de las AS que usamos y a que celda se asocian.

- La **Celda** es una clase simple que tiene el número del siguiente estado y la AS asociada. Representa una celda de la matriz que tenemos en _Google Sheets_.

- El archivo **State** es el que por ahora hace todo, depnde del caracter que lea, es la acción que va a tomar y a que estado continuar (esto en el metodo _getStates_). En ese mismo metodo hago que se ejecute la AS asociada.

    - En el método _main_ de **State** tengo un string cortito que luego lo recorro con un while mientras la condicion sea verdadera (se vuelve falsa al leer el fin de archivo). 
    Siempre que se llegue al esatdo final y no sea el fin de archivo, vuelvo al estado 0 (la variable _state=0_) y no incremento la variable _i_, que funciona de iterador del string, así a la siguiente iteración vuelvo a leer el mismo caracter. 

    - Pueden testearlo sólo ejecutando el _main_ de **States.java**s