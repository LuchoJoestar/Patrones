
package decoradores;

import productos.Producto;

public class ConGarantia extends ProductoDecorador {

    public ConGarantia(Producto productoBase) {
        super(productoBase);
    }

    @Override
    public void mostrarInfo() {
        productoBase.mostrarInfo();
        System.out.println("🛡️ Incluye garantía extendida de 12 meses.");
    }
}
