package decoradores;

import productos.Producto;

public class ConDescuento extends ProductoDecorador {

    public ConDescuento(Producto productoBase) {
        super(productoBase);
    }

    @Override
    public void mostrarInfo() {
        double precioOriginal = productoBase.getPrecio();
        double precioConDescuento = precioOriginal * 0.9;

        System.out.printf("💲 Producto con DESCUENTO: %s | Precio original: $%.2f | Precio con 10%% descuento: $%.2f\n",
                productoBase.getNombre(), precioOriginal, precioConDescuento);
    }
}
