
package modelo;


public abstract class Usuario {
 private int idUsuario;
 private String nombre;  
 private String email;
 private String password;

 
//constructor con parametros
    public Usuario(int idUsuario, String nombre, String email, String password, int fechaRegistro) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
       
    }
// contructor sin paramatros 
    public Usuario() {
        
    }
    //lleva un metodo rol??
    
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
}

