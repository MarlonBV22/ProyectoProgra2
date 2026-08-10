
package modelo.Controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color; // Para cambiar el color del texto del mensaje (Si se desea)
import modelo.ClasesDAO.CursoDAO;
import modelo.ClasesDAO.InscripcionDAO;
import modelo.Estudiante;
import modelo.Profesor;
import modelo.Usuario;
import modelo.ClasesDAO.UsuarioDAO;
import modelo.vistas.VistaLogin;

public class LoginController implements ActionListener {
    
    private VistaLogin vista;
    private UsuarioDAO usuarioDao;
    
    // Constructor: Vincula la interfaz con la base de datos
    public LoginController(VistaLogin vista, UsuarioDAO usuarioDao) {
        this.vista = vista;
        this.usuarioDao = usuarioDao;

        // Indicamos a los botones que este controlador escuchará sus acciones
        this.vista.btnIngresar.addActionListener(this);
        this.vista.btnCerrar.addActionListener(this);
        
        // Limpiamos el label de mensajes al arrancar
        this.vista.lblMensaje.setText("");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        // Si se presiona el botón "Cerrar programa"
        if (e.getSource() == vista.btnCerrar) {
            System.exit(0);
        }
        

        // Si se presiona el botón "Ingresar"
        if (e.getSource() == vista.btnIngresar) {
            
            // 1. Capturamos los datos ingresados en la interfaz gráfica
            String correo = vista.txtCorreo.getText().trim();
            String password = new String(vista.txtPassword.getPassword()).trim();

            // 2. Validación básica de campos vacíos
            if (correo.isEmpty() || password.isEmpty()) {
                vista.lblMensaje.setForeground(Color.RED);
                vista.lblMensaje.setText("Por favor, rellene todos los campos.");
                return;
            }

            // 3. Consultamos al UsuarioDAO para verificar credenciales en MySQL
            Usuario usuarioLogueado = usuarioDao.validarLogin(correo, password);

            // 4. Procesamos la respuesta usando Polimorfismo
            if (usuarioLogueado == null) {
                vista.lblMensaje.setForeground(Color.RED);
                vista.lblMensaje.setText("Correo o contraseña incorrectos.");
            } else {
                vista.lblMensaje.setText(""); 
                vista.dispose(); // Cierra y destruye la ventana de Login

                // Evaluamos con 'instanceof' qué tipo de objeto real llegó
                if (usuarioLogueado instanceof Profesor) {
                    // 1. Instanciamos la vista del menú del profesor
                    modelo.vistas.VistaOpcionesProfesor menuProfesor = new modelo.vistas.VistaOpcionesProfesor();
    
                    // 2. Instanciamos su respectivo controlador pasándole la vista
                    modelo.Controlador.OpcionesProfesorController controlMenu = new modelo.Controlador.OpcionesProfesorController(menuProfesor);
                    
                    // 3. Centramos y mostramos la pantalla
                    menuProfesor.setLocationRelativeTo(null);
                    menuProfesor.setVisible(true);
                } else if (usuarioLogueado instanceof Estudiante) {
                    // 1. Instanciamos la vista del alumno
                    modelo.vistas.VistaInformacionEstudiante panelAlumno = new modelo.vistas.VistaInformacionEstudiante();

                    // 2. Instanciamos todos los DAOs que ocupará su controlador para mapear los nombres
                    InscripcionDAO daoIns = new InscripcionDAO();
                    UsuarioDAO daoUsr = new UsuarioDAO();
                    CursoDAO daoCur = new CursoDAO();

                    // 3. Conectamos el controlador del estudiante
                    int idAlumno = usuarioLogueado.getidUsuario();
                    new modelo.Controlador.EstudianteController(panelAlumno, daoIns, daoUsr, daoCur, idAlumno);

                    // 4. Mostramos centrado el panel del alumno
                    panelAlumno.setLocationRelativeTo(null);
                    panelAlumno.setVisible(true);
                }
            }
        }
    }
    
}
