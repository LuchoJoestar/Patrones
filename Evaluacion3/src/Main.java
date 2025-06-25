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
            mostrarMenuPrincipal();
            
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> agregarProducto();
                    case 2 -> mostrarInventario();
                    case 3 -> reducirStock();
                    case 4 -> aumentarStock();
                    case 5 -> cambiarPrecio();
                    case 6 -> aplicarDecorador();
                    case 7 -> eliminarProducto();
                    case 8 -> removerDecorador();
                    case 9 -> System.out.println("Saliendo del sistema...");
                    default -> System.out.println("Opción no válida. Intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número entero.");
                scanner.nextLine();
            }

        } while (opcion != 9);
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║      FERRETERÍA GULTRO - MENÚ        ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ 1. Agregar producto                  ║");
        System.out.println("║ 2. Ver inventario                    ║");
        System.out.println("║ 3. Reducir stock                     ║");
        System.out.println("║ 4. Aumentar stock                    ║");
        System.out.println("║ 5. Cambiar precio                    ║");
        System.out.println("║ 6. Aplicar decorador                 ║");
        System.out.println("║ 7. Eliminar producto                 ║");
        System.out.println("║ 8. Remover decorador                 ║");
        System.out.println("║ 9. Salir                             ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("Seleccione una opción: ");
    }

    private static void agregarProducto() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║        AGREGAR NUEVO PRODUCTO        ║");
        System.out.println("╚══════════════════════════════════════╝");
        
        int id = IdGenerator.generarId();
        System.out.println("\nTipo de producto:");
        System.out.println("1. Herramienta");
        System.out.println("2. Perno");
        System.out.print("Seleccione una opción: ");

        int tipoOpcion;
        try {
            tipoOpcion = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Error: Entrada inválida. Operación cancelada.");
            scanner.nextLine();
            return;
        }

        String tipo = switch (tipoOpcion) {
            case 1 -> "herramienta";
            case 2 -> "perno";
            default -> {
                System.out.println("Error: Opción no válida.");
                yield null;
            }
        };
        
        if (tipo == null) return;

        System.out.print("\nNombre del producto: ");
        String nombre = scanner.nextLine();

        double precio;
        int stock;
        try {
            System.out.print("Precio: $");
            precio = scanner.nextDouble();
            System.out.print("Stock inicial: ");
            stock = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Error: Entrada inválida. Operación cancelada.");
            scanner.nextLine();
            return;
        }

        try {
            Producto nuevo = ProductoFactory.crearProducto(tipo, id, nombre, precio, stock);
            inventario.agregarProducto(nuevo);
            System.out.println("\n✔ Producto agregado correctamente. ID asignado: " + id);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void eliminarProducto() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         ELIMINAR PRODUCTO            ║");
        System.out.println("╚══════════════════════════════════════╝");
        
        try {
            System.out.print("\nIngrese ID del producto a eliminar: ");
            int idBuscado = scanner.nextInt();
            scanner.nextLine();
            
            boolean eliminado = inventario.obtenerProductos().removeIf(p -> p.getId() == idBuscado);
            
            if (eliminado) {
                System.out.println("\n✔ Producto eliminado correctamente.");
            } else {
                System.out.println("\n✖ Producto no encontrado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Debe ingresar un número entero.");
            scanner.nextLine();
        }
    }

    private static void mostrarInventario() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         INVENTARIO ACTUAL           ║");
        System.out.println("╚══════════════════════════════════════╝");
        
        if (inventario.estaVacio()) {
            System.out.println("\nEl inventario está vacío.");
            return;
        }

        System.out.printf("\n%-5s │ %-12s │ %-20s │ %-10s │ %-6s │ %-9s │ %-9s\n",
                "ID", "Tipo", "Nombre", "Precio", "Stock", "Garantía", "Descuento");
        System.out.println("──────┼────────────┼──────────────────────┼───────────┼────────┼───────────┼───────────");

        for (Producto p : inventario.obtenerProductos()) {
            String tipo = obtenerTipoReal(p);
            String garantia = (p instanceof ConGarantia) ? "Sí" : "No";
            String descuento = (p instanceof ConDescuento) ? "Sí" : "No";

            System.out.printf("%-5d │ %-12s │ %-20s │ $%-9.2f │ %-6d │ %-9s │ %-9s\n",
                    p.getId(), tipo, p.getNombre(), p.getPrecio(), p.getStock(), garantia, descuento);
        }
    }

    private static void reducirStock() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║          REDUCIR STOCK              ║");
        System.out.println("╚══════════════════════════════════════╝");
        
        try {
            System.out.print("\nIngrese ID del producto: ");
            int idBuscado = scanner.nextInt();
            scanner.nextLine();

            boolean encontrado = false;
            for (Producto p : inventario.obtenerProductos()) {
                if (p.getId() == idBuscado) {
                    encontrado = true;
                    System.out.print("Cantidad a reducir: ");
                    int cantidad = scanner.nextInt();
                    scanner.nextLine();
                    
                    p.agregarObservador(new AlertaStock());
                    p.reducirStock(cantidad);
                    System.out.println("\n✔ Stock actualizado. Nuevo stock: " + p.getStock());
                    break;
                }
            }
            
            if (!encontrado) {
                System.out.println("\n✖ Producto no encontrado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Debe ingresar un número entero.");
            scanner.nextLine();
        }
    }

    private static void aumentarStock() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║          AUMENTAR STOCK             ║");
        System.out.println("╚══════════════════════════════════════╝");
        
        try {
            System.out.print("\nIngrese ID del producto: ");
            int idBuscado = scanner.nextInt();
            scanner.nextLine();

            boolean encontrado = false;
            for (Producto p : inventario.obtenerProductos()) {
                if (p.getId() == idBuscado) {
                    encontrado = true;
                    System.out.print("Cantidad a agregar: ");
                    int cantidad = scanner.nextInt();
                    scanner.nextLine();
                    
                    if (cantidad <= 0) {
                        System.out.println("\n✖ La cantidad debe ser mayor a cero.");
                        return;
                    }
                    
                    p.aumentarStock(cantidad);
                    System.out.println("\n✔ Stock actualizado. Nuevo stock: " + p.getStock());
                    break;
                }
            }
            
            if (!encontrado) {
                System.out.println("\n✖ Producto no encontrado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Debe ingresar un número entero.");
            scanner.nextLine();
        }
    }

    private static void cambiarPrecio() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║          CAMBIAR PRECIO              ║");
        System.out.println("╚══════════════════════════════════════╝");
        
        try {
            System.out.print("\nIngrese ID del producto: ");
            int idBuscado = scanner.nextInt();
            scanner.nextLine();

            boolean encontrado = false;
            for (Producto p : inventario.obtenerProductos()) {
                if (p.getId() == idBuscado) {
                    encontrado = true;
                    System.out.print("Nuevo precio: $");
                    double nuevoPrecio = scanner.nextDouble();
                    scanner.nextLine();
                    
                    if (nuevoPrecio <= 0) {
                        System.out.println("\n✖ El precio debe ser mayor a cero.");
                        return;
                    }
                    
                    p.setPrecio(nuevoPrecio);
                    System.out.println("\n✔ Precio actualizado. Nuevo precio: $" + p.getPrecio());
                    break;
                }
            }
            
            if (!encontrado) {
                System.out.println("\n✖ Producto no encontrado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Debe ingresar un número válido.");
            scanner.nextLine();
        }
    }

    private static void aplicarDecorador() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║        APLICAR DECORADOR            ║");
        System.out.println("╚══════════════════════════════════════╝");
        
        try {
            System.out.print("\nIngrese ID del producto a decorar: ");
            int idBuscado = scanner.nextInt();
            scanner.nextLine();

            var productos = inventario.obtenerProductos();
            for (int i = 0; i < productos.size(); i++) {
                Producto p = productos.get(i);
                if (p.getId() == idBuscado) {
                    System.out.println("\nSeleccione decorador a aplicar:");
                    System.out.println("1. Garantía extendida (+10% al precio)");
                    System.out.println("2. Descuento 10%");
                    System.out.print("Opción: ");
                    
                    int eleccion = scanner.nextInt();
                    scanner.nextLine();

                    Producto decorado = switch (eleccion) {
                        case 1 -> new ConGarantia(p);
                        case 2 -> new ConDescuento(p);
                        default -> {
                            System.out.println("\n✖ Opción inválida.");
                            yield null;
                        }
                    };

                    if (decorado != null) {
                        productos.set(i, decorado);
                        System.out.println("\n✔ Decorador aplicado correctamente.");
                        System.out.println("Nuevo precio: $" + decorado.getPrecio());
                    }
                    return;
                }
            }
            System.out.println("\n✖ Producto no encontrado.");
        } catch (InputMismatchException e) {
            System.out.println("Error: Debe ingresar un número entero.");
            scanner.nextLine();
        }
    }

    private static void removerDecorador() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║        REMOVER DECORADOR            ║");
        System.out.println("╚══════════════════════════════════════╝");
        
        try {
            System.out.print("\nIngrese ID del producto: ");
            int idBuscado = scanner.nextInt();
            scanner.nextLine();
            
            var productos = inventario.obtenerProductos();
            for (int i = 0; i < productos.size(); i++) {
                Producto actual = productos.get(i);
                if (actual.getId() == idBuscado) {
                    Producto base = obtenerProductoBase(actual);
                    productos.set(i, base);
                    System.out.println("\n✔ Decoradores eliminados. Producto restaurado al original.");
                    System.out.println("Precio base: $" + base.getPrecio());
                    return;
                }
            }
            System.out.println("\n✖ Producto no encontrado.");
        } catch (InputMismatchException e) {
            System.out.println("Error: Debe ingresar un número entero.");
            scanner.nextLine();
        }
    }

    private static String obtenerTipoReal(Producto p) {
        Producto base = obtenerProductoBase(p);
        return base.getClass().getSimpleName();
    }

    private static Producto obtenerProductoBase(Producto p) {
        Producto actual = p;
        while (actual instanceof ProductoDecorador decorador) {
            actual = decorador.getProductoBase();
        }
        return actual;
    }
}