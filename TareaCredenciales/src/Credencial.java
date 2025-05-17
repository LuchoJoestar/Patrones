import java.util.HashMap;
import java.util.Map;

public class Credencial implements Cloneable {
    private String nombre;
    private String cargo;
    private String rut;
    private Map<String, String> camposAdicionales;
    private String diseño;

    public Credencial() {
        this.camposAdicionales = new HashMap<>();
    }

    // Métodos de acceso
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    
    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }
    
    public String getDiseño() { return diseño; }
    public void setDiseño(String diseño) { this.diseño = diseño; }

    // Métodos para campos adicionales
    public void agregarCampo(String clave, String valor) {
        camposAdicionales.put(clave, valor);
    }
    
    public String obtenerCampo(String clave) {
        return camposAdicionales.get(clave);
    }

    // Implementación del Prototype
    @Override
    public Credencial clone() {
        try {
            Credencial clon = (Credencial) super.clone();
            clon.camposAdicionales = new HashMap<>(this.camposAdicionales);
            return clon;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== CREDENCIAL ===\n");
        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Cargo: ").append(cargo).append("\n");
        sb.append("RUT: ").append(rut).append("\n");
        sb.append("Diseño: ").append(diseño).append("\n");
        
        if (!camposAdicionales.isEmpty()) {
            sb.append("Campos adicionales:\n");
            camposAdicionales.forEach((k, v) -> 
                sb.append("- ").append(k).append(": ").append(v).append("\n"));
        }
        return sb.toString();
    }
}