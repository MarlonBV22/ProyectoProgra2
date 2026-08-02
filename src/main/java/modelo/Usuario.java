
package modelo;
import java.time.LocalDateTime; // Importación necesaria para el TIMESTAMP

public abstract class Usuario {
 private int idUsuario;
 private String nombre;  
 private String email;
 private String password;
 private String rol;
 private LocalDateTime fechaRegistro;

 
//constructor con parametros
    public Usuario(int idUsuario, String nombre, String email, String password, String rol, LocalDateTime fechaRegistro) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.fechaRegistro = fechaRegistro;
    }
// contructor sin paramatros 
    public Usuario() {
        
    }
     //pruba de borra
// CREACION DE GET Y SET
    public int getidUsuario() {
        return idUsuario;
    }

    public void setIDusuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    
    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol){
        this.rol = rol;
    }
    
    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro() {
        this.fechaRegistro = fechaRegistro;
    }
}

