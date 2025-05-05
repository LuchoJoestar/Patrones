import java.util.ArrayList;
import java.util.List;

public class GestorCredenciales {
    private static GestorCredenciales instancia;
    private List<Credencial> credenciales;
    private PlantillaCredencial plantillaBase;

    private GestorCredenciales() {
        this.credenciales = new ArrayList<>();
        this.plantillaBase = new PlantillaCredencial();
    }

    public static synchronized GestorCredenciales getInstancia() {
        if (instancia == null) {
            instancia = new GestorCredenciales();
        }
        return instancia;
    }

    public Credencial crearCredencialBase() {
        Credencial nueva = plantillaBase.crearCredencial();
        credenciales.add(nueva);
        return nueva;
    }

    public void agregarCredencial(Credencial credencial) {
        credenciales.add(credencial);
    }

    public List<Credencial> getCredenciales() {
        return new ArrayList<>(credenciales);
    }

    public PlantillaCredencial getPlantillaBase() {
        return plantillaBase;
    }

    public void reiniciarSistema() {
        this.credenciales.clear();
        this.plantillaBase = new PlantillaCredencial();
    }
}