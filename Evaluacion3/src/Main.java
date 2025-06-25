import inventario.AlertaStock;
import inventario.GestorInventario;
import productos.Producto;
import productos.ProductoFactory;
import utilidades.IdGenerator;

import java.util.InputMismatchException;
import java.util.Scanner;

import decoradores.ConDescuento;
import decoradores.ConGarantia;
import decoradores.ProductoDecorador;

public class Main {
    private static final GestorInventario inventario = GestorInventario.getInstancia();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = 0;
        do {
            System.out.println("\n==== FERRETERIA GULTRO ====");
            System.out.println("1. Agregar producto");
            System.out.println("2. Ver inventario");
            System.out.println("3. Reducir stock");
            System.out.println("4. Aplicar decorador");
            System.out.println("5. Eliminar producto");
            System.out.println("6. Remover decorador");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> agregarProducto();
                    case 2 -> mostrarInventario();
                    case 3 -> reducirStock();
                    case 4 -> aplicarDecorador();
                    case 5 -> eliminarProducto();
                    case 6 -> removerDecorador();
                    case 7 -> System.out.println("Hasta luego.");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor ingrese un número.");
                scanner.nextLine();
            }

        } while (opcion != 7);
    }

    private static void agregarProducto() {
        int id = IdGenerator.generarId();
        System.out.println("Tipo de producto:");
        System.out.println("1. Herramienta");
        System.out.println("2. Perno");
        System.out.print("Seleccione una opción: ");

        int tipoOpcion;
        try {
            tipoOpcion = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Operación cancelada.");
            scanner.nextLine();
            return;
        }

        String tipo;
        if (tipoOpcion == 1) {
            tipo = "herramienta";
        } else if (tipoOpcion == 2) {
            tipo = "perno";
        } else {
            System.out.println("Opción no válida.");
            return;
        }

        System.out.print("Nombre del producto: ");
        String nombre = scanner.nextLine();

        double precio;
        int stock;
        try {
            System.out.print("Precio: ");
            precio = scanner.nextDouble();
            System.out.print("Stock inicial: ");
            stock = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Operación cancelada.");
            scanner.nextLine();
            return;
        }

        try {
            Producto nuevo = ProductoFactory.crearProducto(tipo, id, nombre, precio, stock);
            inventario.agregarProducto(nuevo);
            System.out.println("Producto agregado correctamente. ID asignado: " + id);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void eliminarProducto() {
        try {
            System.out.print("ID del producto a eliminar: ");
            int idBuscado = scanner.nextInt();
            scanner.nextLine();
            var productos = inventario.obtenerProductos();
            for (int i = 0; i < productos.size(); i++) {
                if (productos.get(i).getId() == idBuscado) {
                    productos.remove(i);
                    System.out.println("Producto eliminado correctamente.");
                    return;
                }
            }
            System.out.println("Producto no encontrado.");
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida.");
            scanner.nextLine();
        }
    }

    private static void mostrarInventario() {
        if (inventario.estaVacio()) {
            System.out.println("Inventario vacío.");
            return;
        }

        System.out.println("\nInventario Actual:");
        System.out.printf("%-5s | %-12s | %-20s | %-10s | %-6s | %-9s | %-9s%n",
                "ID", "Tipo", "Nombre", "Precio", "Stock", "Garantía", "Descuento");
        System.out.println("-------------------------------------------------------------------------------");

        for (Producto p : inventario.obtenerProductos()) {
            String tipo = obtenerTipoReal(p);
            String garantia = (p instanceof ConGarantia) ? "Sí" : "No";
            String descuento = (p instanceof ConDescuento) ? "Sí" : "No";

            System.out.printf("%-5d | %-12s | %-20s | $%-9.2f | %-6d | %-9s | %-9s%n",
                    p.getId(), tipo, p.getNombre(), p.getPrecio(), p.getStock(), garantia, descuento);
        }
    }

    private static void reducirStock() {
        try {
            System.out.print("ID del producto: ");
            int idBuscado = scanner.nextInt();
            scanner.nextLine();

            for (Producto p : inventario.obtenerProductos()) {
                if (p.getId() == idBuscado) {
                    System.out.print("Cantidad a reducir: ");
                    int cantidad = scanner.nextInt();
                    scanner.nextLine();
                    p.agregarObservador(new AlertaStock());
                    p.reducirStock(cantidad);
                    System.out.println("Stock actualizado.");
                    return;
                }
            }
            System.out.println("Producto no encontrado.");
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida.");
            scanner.nextLine();
        }
    }

    private static void aplicarDecorador() {
        try {
            System.out.print("ID del producto a decorar: ");
            int idBuscado = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < inventario.obtenerProductos().size(); i++) {
                Producto p = inventario.obtenerProductos().get(i);
                if (p.getId() == idBuscado) {
                    System.out.println("¿Qué desea aplicar?");
                    System.out.println("1. Garantía extendida");
                    System.out.println("2. Descuento 10%");
                    int eleccion = scanner.nextInt();
                    scanner.nextLine();

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
                        System.out.println("Decorador aplicado correctamente.");
                    }
                    return;
                }
            }
            System.out.println("Producto no encontrado.");
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida.");
            scanner.nextLine();
        }
    }

    private static void removerDecorador() {
        try {
            System.out.print("ID del producto a limpiar: ");
            int idBuscado = scanner.nextInt();
            scanner.nextLine();
            var productos = inventario.obtenerProductos();
            for (int i = 0; i < productos.size(); i++) {
                Producto actual = productos.get(i);
                if (actual.getId() == idBuscado) {
                    while (actual instanceof ProductoDecorador decorador) {
                        actual = decorador.getProductoBase();
                    }
                    productos.set(i, actual);
                    System.out.println("Decoradores eliminados. Producto restaurado al original.");
                    return;
                }
            }
            System.out.println("Producto no encontrado.");
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida.");
            scanner.nextLine();
        }
    }

    private static String obtenerTipoReal(Producto p) {
        Producto actual = p;
        while (actual instanceof ProductoDecorador decorador) {
            actual = decorador.getProductoBase();
        }
        return actual.getClass().getSimpleName();
    }
}
