package productos;

public class Perno extends Producto {
    public Perno(String nombre, double precio, int stock) {
        super(nombre, precio, stock);
    }

    @Override
    public void mostrarInfo() {
        System.out.println("🔩 Perno: " + nombre + " | Precio: $" + precio + " | Stock: " + stock);
    }
}
