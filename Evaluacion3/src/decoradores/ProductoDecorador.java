package decoradores;

import productos.Producto;

public abstract class ProductoDecorador extends Producto {
    protected Producto productoBase;

    public ProductoDecorador(Producto productoBase) {
        super(productoBase.getId(), productoBase.getNombre(), productoBase.getPrecio(), productoBase.getStock());
        this.productoBase = productoBase;
    }

    @Override
    public abstract void mostrarInfo();
}

