// src/productos/Producto.java
package productos;

import inventario.ObservadorStock;
import java.util.ArrayList;
import java.util.List;

public abstract class Producto {
    protected int id;
    protected String nombre;
    protected double precio;
    protected int stock;

    private final List<ObservadorStock> observadores = new ArrayList<>();

    public Producto(int id, String nombre, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public void agregarObservador(ObservadorStock o) {
        observadores.add(o);
    }

    protected void notificarObservadores(String mensaje) {
        for (ObservadorStock o : observadores) {
            o.notificar(mensaje);
        }
    }

    public void reducirStock(int cantidad) {
        if (cantidad <= stock) {
            stock -= cantidad;
            if (stock < 5) {
                notificarObservadores(nombre + " (ID: " + id + ") tiene solo " + stock + " unidades disponibles.");
            }
        }
    }

    public void aumentarStock(int cantidad) {
        stock += cantidad;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public abstract void mostrarInfo();
}
