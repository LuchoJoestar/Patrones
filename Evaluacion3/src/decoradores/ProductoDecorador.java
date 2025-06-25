package decoradores;

import productos.Producto;

public abstract class ProductoDecorador extends Producto {
public Producto productoBase;

public ProductoDecorador(Producto productoBase) {
    super(productoBase.getId(), productoBase.getNombre(), productoBase.getPrecio(), productoBase.getStock());
    this.productoBase = productoBase;
}

public Producto getProductoBase() {
return productoBase;
}

@Override
public abstract void mostrarInfo();
}