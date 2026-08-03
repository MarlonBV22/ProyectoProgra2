
package modelo;

import modelo.ClasesDAO.UsuarioDAO;
import modelo.Controlador.LoginController;
import modelo.vistas.VistaLogin;


public class ProyectoProgra2 {

    public static void main(String[] args) {
        // 1. Se instancia la vista
        VistaLogin pantallaLogin = new VistaLogin();
        
        // 2. Se instancia el Modelo DAO
        UsuarioDAO daoUsuario = new UsuarioDAO();
        
        // 3. Se instancia el Controlador amarrando la vista y el DAO
        // El constructor del controlador se encargará de activar los botones automáticamente
        LoginController controlador = new LoginController(pantallaLogin, daoUsuario);
        
        // 4. Centra la pantalla en el monitor del usuario
        pantallaLogin.setLocationRelativeTo(null);
        
        // 5. Hace visible la interfaz gráfica
        pantallaLogin.setVisible(true);
    }
} 