/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author David Cruz
 */
public class Usuario {
 private int IDusuario;
 private String Nombre;  
 private String email;
 private String password;
 int FechaRegistro;
 
 //constructor con parametros
    public Usuario(int IDusuario, String Nombre, String email, String password,  int FechaRegistro) {
        this.IDusuario = IDusuario;
        this.Nombre = Nombre;
        this.email = email;
        this.password = password;
        this.FechaRegistro = FechaRegistro;
    }
// contructor sin paramatros 
    public Usuario() {
        
    }
// CREACION DE GET Y SET
    public int getIDusuario() {
        return IDusuario;
    }

    public void setIDusuario(int IDusuario) {
        this.IDusuario = IDusuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
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

    public int getFechaRegistro() {
        return FechaRegistro;
    }

    public void setFechaRegistro(int FechaRegistro) {
        this.FechaRegistro = FechaRegistro;
    }
    
    
    
}
