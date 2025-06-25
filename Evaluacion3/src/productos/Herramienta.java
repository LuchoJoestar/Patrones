package productos;

public class Herramienta extends Producto {
    public Herramienta(String nombre, double precio, int stock) {
        super(nombre, precio, stock);
    }

    @Override
    public void mostrarInfo() {
        System.out.println("🔧 Herramienta: " + nombre + " | Precio: $" + precio + " | Stock: " + stock);
    }
}
