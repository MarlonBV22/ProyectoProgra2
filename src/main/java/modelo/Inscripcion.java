/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.time.LocalDate;

/**
 *
 * @author David Cruz
 */
public class Inscripcion {
    
    private int idIncripcion;
    private int idEstudnainte;
    private int idCurso;
    private LocalDate fechaIncrpcion;
    //contructor sin parametros
    public Inscripcion(){
        
    }
     //constructor con parametros
    public Inscripcion(int idIncripcion, int idEstudnainte, int idCurso, LocalDate fechaIncrpcion) {
        this.idIncripcion = idIncripcion;
        this.idEstudnainte = idEstudnainte;
        this.idCurso = idCurso;
        this.fechaIncrpcion = fechaIncrpcion;
    }
    //get y set

    public int getIdIncripcion() {
        return idIncripcion;
    }

    public void setIdIncripcion(int idIncripcion) {
        this.idIncripcion = idIncripcion;
    }

    public int getIdEstudnainte() {
        return idEstudnainte;
    }

    public void setIdEstudnainte(int idEstudnainte) {
        this.idEstudnainte = idEstudnainte;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public LocalDate getFechaIncrpcion() {
        return fechaIncrpcion;
    }

    public void setFechaIncrpcion(LocalDate fechaIncrpcion) {
        this.fechaIncrpcion = fechaIncrpcion;
    }
      
}
