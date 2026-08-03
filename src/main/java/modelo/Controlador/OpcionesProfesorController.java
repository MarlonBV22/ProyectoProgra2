/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.vistas.visiorOpcionesProfesor;
import modelo.vistas.visorCursos;
import modelo.vistas.visorLoggin;
import modelo.vistas.visorTablaIncripciones;

/**
 *
 * @author David Cruz
 */
        // escucha que hace el boton que se selecciona
public class OpcionesProfesorController implements ActionListener {
    //guarda la ventana del profe
     private visiorOpcionesProfesor vista;

      public OpcionesProfesorController(
          visiorOpcionesProfesor vista
    ) {
          
        this.vista = vista;
        //conecta los botones
        vista.getbtnCursos().addActionListener(this);
        vista.getbtnInscripciones().addActionListener(this);
        vista.getbtnAdministradorUsuarios().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
           //pregunta si fue el boton curso
        if (e.getSource() == vista.getbtnCursos()) {
           //crear el curso
            visorCursos ventanaCursos =
                    new visorCursos();

            ventanaCursos.setVisible(true);
            vista.dispose();
        }
        //pregunta 
        if (e.getSource() == vista.getbtnInscripciones()) {
          // crea
            visorTablaIncripciones ventanaInscripciones =
                    new visorTablaIncripciones();

            ventanaInscripciones.setVisible(true);
            vista.dispose();
        }
           //pregunta
        if (e.getSource() == vista.getbtnAdministradorUsuarios()) {
            // crea
             visiorOpcionesProfesor vista = new visiorOpcionesProfesor();
               OpcionesProfesorController controlador =
                   new OpcionesProfesorController(vista);

                           vista.setVisible(true);
            /*visorLoggin login =
                   new visorLoggin();

            login.setVisible(true);
            vista.dispose(); */
       }
    }
}
