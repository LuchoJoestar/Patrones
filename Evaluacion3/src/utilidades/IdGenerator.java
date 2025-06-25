// src/utilidades/IdGenerator.java
package utilidades;

public class IdGenerator {
    private static int contador = 1;

    public static int generarId() {
        return contador++;
    }
}
