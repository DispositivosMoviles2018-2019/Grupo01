package ec.edu.uce.modelo;

public class Usuario {

    private String usuario;
    private String calve;

    public Usuario() {
    }

    public Usuario(String usuario, String calve) {
        this.usuario = usuario;
        this.calve = calve;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCalve() {
        return calve;
    }

    public void setCalve(String calve) {
        this.calve = calve;
    }

}
