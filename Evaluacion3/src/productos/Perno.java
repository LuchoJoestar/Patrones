package productos;

public class Perno extends Producto {
    public Perno(int id,String nombre, double precio, int stock) {
        super(id, nombre, precio, stock);
    }

    @Override
    public void mostrarInfo() {
        System.out.println("🔩 Perno: " + nombre + " | Precio: $" + precio + " | Stock: " + stock);
    }
}
