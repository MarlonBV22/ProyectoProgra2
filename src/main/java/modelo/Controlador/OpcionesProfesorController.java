
package modelo.Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.ClasesDAO.UsuarioDAO;
import modelo.vistas.VistaOpcionesProfesor;
import modelo.vistas.VistaCursos;
import modelo.vistas.VistaLogin;
import modelo.vistas.VistaInscripciones;
import modelo.vistas.VistaUsuarios;

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
        
        // Botón Cursos
        if (e.getSource() == vista.btnCursos) {
            modelo.vistas.VistaCursos ventanaCursos = new modelo.vistas.VistaCursos();
            modelo.ClasesDAO.CursoDAO daoCurso = new modelo.ClasesDAO.CursoDAO();
            modelo.ClasesDAO.UsuarioDAO daoUsuario = new modelo.ClasesDAO.UsuarioDAO(); // Lo inyectamos para las validaciones

            // Instanciamos el controlador
            new modelo.Controlador.CursosController(ventanaCursos, daoCurso, daoUsuario);

            ventanaCursos.setLocationRelativeTo(null);
            ventanaCursos.setVisible(true);
            vista.dispose();
        }

        
        // Botón Inscripciones
        if (e.getSource() == vista.btnInscripciones) {
            VistaInscripciones ventanaInscripciones = new VistaInscripciones();
            ventanaInscripciones.setLocationRelativeTo(null);
            ventanaInscripciones.setVisible(true);
            vista.dispose();
        }
        
        // Botón Administrador de Usuarios
        if (e.getSource() == vista.btnUsuarios) {
            // 1. Creamos la pantalla
            modelo.vistas.VistaUsuarios ventanaUsuarios = new modelo.vistas.VistaUsuarios(); 

            // 2. Instanciamos su DAO correspondiente
            modelo.ClasesDAO.UsuarioDAO daoUsuarios = new modelo.ClasesDAO.UsuarioDAO();

            // 3. Enlazamos la pantalla con su controlador especializado
            modelo.Controlador.UsuariosController controlUsuarios = new modelo.Controlador.UsuariosController(ventanaUsuarios, daoUsuarios);

            // 4. Centramos y abrimos la ventana
            ventanaUsuarios.setLocationRelativeTo(null);
            ventanaUsuarios.setVisible(true);

            // 5. Ocultamos el menú de navegación actual
            vista.dispose();
        }


        // Botón Regresar al Inicio       
        if (e.getSource() == vista.btnRegresar) {
            // 1. Crea la nueva pantalla de Login
            modelo.vistas.VistaLogin login = new modelo.vistas.VistaLogin(); 
    
            // 2. Instancia un nuevo UsuarioDAO para el proceso de datos
            modelo.ClasesDAO.UsuarioDAO daoUsuario = new modelo.ClasesDAO.UsuarioDAO();

            // 3. Crea un nuevo LoginController para amarrar la nueva ventana
            modelo.Controlador.LoginController controlLogin = new modelo.Controlador.LoginController(login, daoUsuario);

            // 4. Centra y muestra la ventana en pantalla
            login.setLocationRelativeTo(null);
            login.setVisible(true); 

            // 5. Destruye el menú actual del profesor
            vista.dispose(); 
        }
    }
}

