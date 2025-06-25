package productos;

public class ProductoFactory {
    public static Producto crearProducto(String tipo, int id, String nombre, double precio, int stock) {
        return switch (tipo.toLowerCase()) {
            case "herramienta" -> new Herramienta(id, nombre, precio, stock);
            case "perno" -> new Perno(id, nombre, precio, stock);
            default -> throw new IllegalArgumentException("Tipo de producto no válido: " + tipo);
        };
    }
}
