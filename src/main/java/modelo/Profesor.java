
package modelo;


public class Profesor extends Usuario {
    
       //construcor con paramatros
     public Profesor(int idUsuario, 
            String nombre, 
            String email,
            String password, 
            int fechaRegistro) {
        
        super(idUsuario, nombre, email, password, fechaRegistro);
    }
     
        //constructor sin parametros
     public Profesor(){
       }
    }
