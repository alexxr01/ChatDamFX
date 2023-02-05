package chat;
public class Usuario {
    public String nombre, ip;

    public Usuario() {

    }

    // Constructor
    public Usuario (String nombre, String ip) {
        super();
        this.nombre = nombre;
        this.ip = ip;
    }

    /*
    GETTERS Y SETTERS
     */

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
