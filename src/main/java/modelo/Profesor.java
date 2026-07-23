
package modelo;
import java.time.LocalDateTime; // Importación necesaria para el TIMESTAMP

public class Profesor extends Usuario {
    
       //construcor con paramatros
     public Profesor(int idUsuario, 
            String nombre, 
            String email,
            String password, 
            LocalDateTime fechaRegistro) {
        
        super(idUsuario, nombre, email, password, "PROFESOR" ,fechaRegistro);
    }
     
        //constructor sin parametros
     public Profesor(){
       }
    }
