
package modelo;
import java.time.LocalDate;


public class Inscripcion {
    
    private int idInscripcion;
    private int idEstudiante;
    private int idCurso;
    private LocalDate fechaInscripcion;
    //contructor sin parametros
    
    public Inscripcion(){
        
    }
     //constructor con parametros
    public Inscripcion(int idInscripcion, int idEstudiante, int idCurso, LocalDate fechaInscripcion) {
        this.idInscripcion = idInscripcion;
        this.idEstudiante = idEstudiante;
        this.idCurso = idCurso;
        this.fechaInscripcion = fechaInscripcion;
    }
    //get y set

    public int getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }
      
}
