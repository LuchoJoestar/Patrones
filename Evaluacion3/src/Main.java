
// src/Main.java
import inventario.AlertaStock;
import inventario.GestorInventario;
import productos.Producto;
import productos.ProductoFactory;
import utilidades.IdGenerator;

import java.util.Scanner;

import decoradores.ConDescuento;
import decoradores.ConGarantia;

public class Main {
    private static final GestorInventario inventario = GestorInventario.getInstancia();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n==== FERRETERIA GULTRO ====");
            System.out.println("1. Agregar producto");
            System.out.println("2. Ver inventario");
            System.out.println("3. Reducir stock");
            System.out.println("4. Aplicar decorador");
            System.out.println("5. Salir");
            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> agregarProducto();
                case 2 -> mostrarInventario();
                case 3 -> reducirStock();
                case 4 -> aplicarDecorador();
                case 5 -> System.out.println("¡Hasta luego!");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 5);
    }

    private static void agregarProducto() {
        int id = IdGenerator.generarId(); // ← ID generado automáticamente

        System.out.print("Tipo (herramienta/perno): ");
        String tipo = scanner.nextLine();

        System.out.print("Nombre del producto: ");
        String nombre = scanner.nextLine();

        System.out.print("Precio: ");
        double precio = scanner.nextDouble();

        System.out.print("Stock inicial: ");
        int stock = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer

        try {
            Producto nuevo = ProductoFactory.crearProducto(tipo, id, nombre, precio, stock);
            inventario.agregarProducto(nuevo);
            System.out.println("Producto agregado correctamente. ID asignado: " + id);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void mostrarInventario() {
        if (inventario.estaVacio()) {
            System.out.println("Inventario vacío.");
            return;
        }

        System.out.println("\n📋 Inventario Actual:");
        for (Producto p : inventario.obtenerProductos()) {
            p.mostrarInfo();
        }
    }

    private static void reducirStock() {
        System.out.print("Nombre del producto: ");
        String nombreBuscado = scanner.nextLine();

        for (Producto p : inventario.obtenerProductos()) {
            if (p.getNombre().equalsIgnoreCase(nombreBuscado)) {
                System.out.print("Cantidad a reducir: ");
                int cantidad = scanner.nextInt();
                scanner.nextLine(); // limpiar buffer

                // Agrega un observador por defecto
                p.agregarObservador(new AlertaStock());

                p.reducirStock(cantidad);
                System.out.println("✅ Stock actualizado.");
                return;
            }
        }
        System.out.println("Producto no encontrado.");
    }

    private static void aplicarDecorador() {
        System.out.print("Nombre del producto a decorar: ");
        String nombre = scanner.nextLine();

        for (int i = 0; i < inventario.obtenerProductos().size(); i++) {
            Producto p = inventario.obtenerProductos().get(i);

            if (p.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println("¿Qué desea aplicar?");
                System.out.println("1. Garantía extendida");
                System.out.println("2. Descuento 10%");
                int eleccion = scanner.nextInt();
                scanner.nextLine(); // limpiar buffer

                Producto decorado = switch (eleccion) {
                    case 1 -> new ConGarantia(p);
                    case 2 -> new ConDescuento(p);
                    default -> {
                        System.out.println("Opción inválida.");
                        yield null;
                    }
                };

                if (decorado != null) {
                    inventario.obtenerProductos().set(i, decorado);
                    System.out.println("✅ Decorador aplicado correctamente.");
                }

                return;
            }
        }

        System.out.println("Producto no encontrado.");
    }

}
