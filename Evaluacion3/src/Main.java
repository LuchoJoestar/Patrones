// src/Main.java
import productos.Producto;
import productos.ProductoFactory;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Producto> inventario = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n==== FERREPLUS ====");
            System.out.println("1. Agregar producto");
            System.out.println("2. Ver inventario");
            System.out.println("3. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> agregarProducto();
                case 2 -> mostrarInventario();
                case 3 -> System.out.println("¡Hasta luego!");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 3);
    }

    private static void agregarProducto() {
        System.out.print("Tipo (Herramienta/Perno): ");
        String tipo = scanner.nextLine();

        System.out.print("Nombre del producto: ");
        String nombre = scanner.nextLine();

        System.out.print("Precio: ");
        double precio = scanner.nextDouble();

        System.out.print("Stock inicial: ");
        int stock = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer

        try {
            Producto nuevo = ProductoFactory.crearProducto(tipo, nombre, precio, stock);
            inventario.add(nuevo);
            System.out.println("✅ Producto agregado correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void mostrarInventario() {
        if (inventario.isEmpty()) {
            System.out.println("📦 Inventario vacío.");
            return;
        }

        System.out.println("\n📋 Inventario Actual:");
        for (Producto p : inventario) {
            p.mostrarInfo();
        }
    }
}
