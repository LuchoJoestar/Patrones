package inventario;

public class AlertaStock implements ObservadorStock {
    @Override
    public void notificar(String mensaje){
        System.out.println("¡Alerta de stock!: " + mensaje);
    }
}
