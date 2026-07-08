
package modelo;


public class Usuario {
 private int idUsuario;
 private String nombre;  
 private String email;
 private String password;
 private int fechaRegistro;
 
 //constructor con parametros
    public Usuario(int idUsuarioP, String nombreP, String emailP, String passwordP, int fechaRegistroP) {
        this.idUsuario = idUsuarioP;
        this.nombre = nombreP;
        this.email = emailP;
        this.password = passwordP;
        this.fechaRegistro = fechaRegistroP;
    }
// contructor sin paramatros 
    public Usuario() {
        
    }
// CREACION DE GET Y SET
    public int getidUsuario() {
        return idUsuario;
    }

    public void setIDusuario(int idUsuarioP) {
        this.idUsuario = idUsuarioP;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombreP) {
        this.nombre = nombreP;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String emailP) {
        this.email = emailP;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passwordP) {
        this.password = passwordP;
    }

    public int getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(int fechaRegistroP) {
        this.fechaRegistro = fechaRegistroP;
    }
   
}
