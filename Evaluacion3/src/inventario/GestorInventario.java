package inventario;

import productos.Producto;

import java.util.ArrayList;
import java.util.List;

public class GestorInventario {
    private static GestorInventario instancia;
    private List<Producto> productos;

    private GestorInventario() {
        productos = new ArrayList<>();
    }

    public static GestorInventario getInstancia() {
        if (instancia == null) {
            instancia = new GestorInventario();
        }
        return instancia;
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public List<Producto> obtenerProductos() {
        return productos;
    }

    public boolean estaVacio() {
        return productos.isEmpty();
    }
}
