// src/productos/Herramienta.java
package productos;

public class Herramienta extends Producto {
    public Herramienta(int id, String nombre, double precio, int stock) {
        super(id, nombre, precio, stock);
    }

    @Override
    public void mostrarInfo() {
        System.out.println("🔧 [ID " + id + "] Herramienta: " + nombre + " | Precio: $" + precio + " | Stock: " + stock);
    }
}
