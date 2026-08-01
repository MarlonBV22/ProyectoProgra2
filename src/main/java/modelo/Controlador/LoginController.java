
package modelo.Controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color; // Para cambiar el color del texto del mensaje (Si se desea)
import modelo.Estudiante;
import modelo.Profesor;
import modelo.Usuario;
import modelo.ClasesDAO.UsuarioDAO;
import modelo.vistas.visorLoggin;

public class LoginController implements ActionListener {
    
    private visorLoggin vista;
    private UsuarioDAO usuarioDao;
    
    // Constructor: Vincula la interfaz con la base de datos
    public LoginController(visorLoggin vista, UsuarioDAO usuarioDao) {
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
        
        // ACCIÓN 1: Si se presiona el botón "Cerrar programa" (rojo)
        if (e.getSource() == vista.btnCerrar) {
            System.exit(0);
        }

        // ACCIÓN 2: Si se presiona el botón "Ingresar" (amarillo)
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
                    System.out.println("¡Profesor verificado! Abriendo Dashboard de Profesor...");
                    // Aquí irá la instancia del Dashboard del Profesor en el futuro
                } else if (usuarioLogueado instanceof Estudiante) {
                    System.out.println("¡Estudiante verificado! Abriendo Dashboard de Estudiante...");
                    // Aquí irá la instancia del Dashboard del Estudiante en el futuro
                }
            }
        }
    }
    
}
