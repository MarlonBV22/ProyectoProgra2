
package modelo;
import java.time.LocalDateTime; // Importación necesaria para el TIMESTAMP

public class Estudiante extends Usuario {
    
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
}
