package chat;
import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 1919;
    private String user;
    private String ip;
    private String message;
    private int port;

    public Message(String user, String ip) {
        super();
        this.user = user;
        this.ip = ip;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    @Override
    public String toString() {
        return "Message [user=" + user + ", ip=" + ip + ", message=" + message + ", port=" + port + "]";
    }
}
