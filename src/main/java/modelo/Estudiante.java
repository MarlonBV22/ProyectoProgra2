
package modelo;
import java.time.LocalDateTime; // Importación necesaria para el TIMESTAMP

public class Estudiante extends Usuario {
    private String nombreString;
    private String email;
    private String password;

    // Constructor con parametros
    public Estudiante(int idUsuario,
           String nombre, 
           String email,
           String password, 
           LocalDateTime fechaRegistro) {
        super(idUsuario, nombre, email, password, "ESTUDIANTE" ,fechaRegistro);
    }
    
    // Constructor sin parametros
    public Estudiante(){
    }
    
    //creacion de gets y sets

    public String getNombreString() {
        return nombreString;
    }

    public void setNombreString(String nombreString) {
        this.nombreString = nombreString;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
