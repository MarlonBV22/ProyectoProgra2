
package modelo.Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.vistas.VistaOpcionesProfesor;
import modelo.vistas.VistaCursos;
import modelo.vistas.VistaLogin;
import modelo.vistas.VistaTablaInscripciones;
import modelo.vistas.VistaCreacionUsuario;

public class OpcionesProfesorController implements ActionListener {
    
    private VistaOpcionesProfesor vista;

    public OpcionesProfesorController(VistaOpcionesProfesor vista) {
        this.vista = vista;
        
        // Conecta los botones directamente
        this.vista.btnCursos.addActionListener(this);
        this.vista.btnInscripciones.addActionListener(this);
        this.vista.btnUsuarios.addActionListener(this);
        this.vista.btnRegresar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        // 1. Botón Cursos
        if (e.getSource() == vista.btnCursos) {
            VistaCursos ventanaCursos = new VistaCursos();
            ventanaCursos.setLocationRelativeTo(null); // Centra la ventana
            ventanaCursos.setVisible(true);
            vista.dispose();
        }
        
        // 2. Botón Inscripciones
        if (e.getSource() == vista.btnInscripciones) {
            VistaTablaInscripciones ventanaInscripciones = new VistaTablaInscripciones();
            ventanaInscripciones.setLocationRelativeTo(null);
            ventanaInscripciones.setVisible(true);
            vista.dispose();
        }
        
        // 3. Botón Administrador de Usuarios
        if (e.getSource() == vista.btnUsuarios) {
            VistaCreacionUsuario ventanaUsuarios = new VistaCreacionUsuario();
            ventanaUsuarios.setLocationRelativeTo(null);
            ventanaUsuarios.setVisible(true);
            vista.dispose();
        }

        // 4. Botón Regresar al Inicio
        if (e.getSource() == vista.btnRegresar) {
            VistaLogin login = new VistaLogin();
            login.setLocationRelativeTo(null);
            login.setVisible(true);
            vista.dispose(); // Cierra el menú del profesor
        }
    }
}

