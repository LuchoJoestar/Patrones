public class PlantillaCredencial {
    private String colorFondo;
    private String colorTexto;
    private String logo;
    private String diseño;

    public PlantillaCredencial() {
        // Valores por defecto
        this.colorFondo = "#FFFFFF";
        this.colorTexto = "#000000";
        this.logo = "logo_default.png";
        this.diseño = "Clásico";
    }

    public Credencial crearCredencial() {
        Credencial credencial = new Credencial();
        credencial.setDiseño(this.diseño);
        return credencial;
    }

    // Getters y Setters
    public String getColorFondo() { return colorFondo; }
    public void setColorFondo(String colorFondo) { this.colorFondo = colorFondo; }
    
    public String getColorTexto() { return colorTexto; }
    public void setColorTexto(String colorTexto) { this.colorTexto = colorTexto; }
    
    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }
    
    public String getDiseño() { return diseño; }
    public void setDiseño(String diseño) { this.diseño = diseño; }
}