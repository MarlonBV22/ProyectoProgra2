/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author jorge.villafuerte
 */
public class Estudiante extends Usuario {
      //cambiar los atributos 
    private String carrera;
    private String carnet;

    // Constructor vacío
    public Estudiante() {
    }

    // Constructor con parametros
    public Estudiante(String carrera, String carnet) {
        this.carrera = carrera;
        this.carnet = carnet;
    }

    // Getter de carrera
    public String getCarrera() {
        return carrera;
    }

    // Setter de carrera
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    // Getter del carnet
    public String getCarnet() {
        return carnet;
    }

    // Setter del carnet
    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

}

