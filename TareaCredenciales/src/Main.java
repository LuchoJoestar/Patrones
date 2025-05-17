import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static GestorCredenciales gestor = GestorCredenciales.getInstancia();

    public static void main(String[] args) {
        mostrarMenu();
    }

    private static void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n=== GENERADOR DE CREDENCIALES ===");
            System.out.println("1. Crear nueva credencial");
            System.out.println("2. Listar credenciales");
            System.out.println("3. Modificar plantilla base");
            System.out.println("4. Reiniciar sistema");
            System.out.println("5. Salir");
            System.out.print("Seleccione opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1: crearCredencial(); break;
                case 2: listarCredenciales(); break;
                case 3: modificarPlantilla(); break;
                case 4: reiniciarSistema(); break;
                case 5: System.out.println("Saliendo..."); break;
                default: System.out.println("Opción no válida");
            }
        } while (opcion != 5);
    }

    private static void crearCredencial() {
        Credencial nueva = gestor.crearCredencialBase().clone();
        
        System.out.println("\n--- NUEVA CREDENCIAL ---");
        System.out.print("Nombre: ");
        nueva.setNombre(scanner.nextLine());
        
        System.out.print("Cargo: ");
        nueva.setCargo(scanner.nextLine());
        
        System.out.print("RUT: ");
        nueva.setRut(scanner.nextLine());
        
        System.out.print("¿Agregar campos adicionales? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            agregarCamposAdicionales(nueva);
        }
        
        gestor.agregarCredencial(nueva);
        System.out.println("\n✅ Credencial creada exitosamente!");
    }

    private static void agregarCamposAdicionales(Credencial credencial) {
        String continuar;
        do {
            System.out.print("Nombre del campo: ");
            String campo = scanner.nextLine();
            
            System.out.print("Valor: ");
            String valor = scanner.nextLine();
            
            credencial.agregarCampo(campo, valor);
            
            System.out.print("¿Agregar otro campo? (s/n): ");
            continuar = scanner.nextLine();
        } while (continuar.equalsIgnoreCase("s"));
    }

    private static void listarCredenciales() {
        List<Credencial> lista = gestor.getCredenciales();
        
        if (lista.isEmpty()) {
            System.out.println("\nNo hay credenciales registradas");
            return;
        }
        
        System.out.println("\n=== LISTADO DE CREDENCIALES ===");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println("\nCredencial #" + (i + 1));
            System.out.println(lista.get(i));
        }
    }

    private static void modificarPlantilla() {
        PlantillaCredencial plantilla = gestor.getPlantillaBase();
        
        System.out.println("\n--- CONFIGURACIÓN DE PLANTILLA ---");
        System.out.println("1. Color de fondo (" + plantilla.getColorFondo() + ")");
        System.out.println("2. Color de texto (" + plantilla.getColorTexto() + ")");
        System.out.println("3. Logo (" + plantilla.getLogo() + ")");
        System.out.println("4. Diseño (" + plantilla.getDiseño() + ")");
        System.out.print("Seleccione qué modificar: ");
        
        int opcion = scanner.nextInt();
        scanner.nextLine();
        
        switch (opcion) {
            case 1:
                System.out.print("Nuevo color de fondo: ");
                plantilla.setColorFondo(scanner.nextLine());
                break;
            case 2:
                System.out.print("Nuevo color de texto: ");
                plantilla.setColorTexto(scanner.nextLine());
                break;
            case 3:
                System.out.print("Nuevo logo: ");
                plantilla.setLogo(scanner.nextLine());
                break;
            case 4:
                System.out.print("Nuevo diseño: ");
                plantilla.setDiseño(scanner.nextLine());
                break;
            default:
                System.out.println("Opción no válida");
                return;
        }
        
        System.out.println("✅ Plantilla actualizada");
    }

    private static void reiniciarSistema() {
        System.out.print("¿Está seguro de reiniciar el sistema? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            gestor.reiniciarSistema();
            System.out.println("✅ Sistema reiniciado");
        }
    }
}