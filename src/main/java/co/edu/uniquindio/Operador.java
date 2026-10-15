package co.edu.uniquindio;

public class Operador {

    private String nombreCompleto;
    private String identificacion;
    private String certificacion;
    private int aniosExperiencia;


    public Operador( String nombreCompleto,String identificacion, String certificacion, int aniosExperiencia) {
        this.nombreCompleto = nombreCompleto;
        this.identificacion = identificacion;
        this.certificacion = certificacion;
        this.aniosExperiencia = aniosExperiencia;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getCertificacion() {
        return certificacion;
    }

    public void setCertificacion(String certificacion) {
        this.certificacion = certificacion;
    }

    public int getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(int aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }

    @Override
    public String toString() {
        return "Operador{" +
                "nombreCompleto='" + nombreCompleto + '\'' +
                ", identificacion='" + identificacion + '\'' +
                ", certificacion='" + certificacion + '\'' +
                ", aniosExperiencia=" + aniosExperiencia +
                '}';
    }
}
