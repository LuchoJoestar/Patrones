package productos;

public class ProductoFactory {
    public static Producto crearProducto(String tipo, String nombre, double precio, int stock) {
        switch (tipo.toLowerCase()) {
            case "herramienta":
                return new Herramienta(nombre, precio, stock);
            case "perno":
                return new Perno(nombre, precio, stock);
            default:
                throw new IllegalArgumentException("Tipo de producto no válido: " + tipo);
        }
    }
}
