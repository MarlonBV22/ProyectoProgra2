
package modelo;

import modelo.ClasesDAO.UsuarioDAO;
import modelo.Controlador.LoginController;
import modelo.vistas.visorLoggin;


public class ProyectoProgra2 {

    public static void main(String[] args) {
        // 1. Instanciamos la Vista (el JFrame diseñado por tu compañero)
        visorLoggin pantallaLogin = new visorLoggin();
        
        // 2. Instanciamos el Modelo DAO (el acceso a datos que tú programaste)
        UsuarioDAO daoUsuario = new UsuarioDAO();
        
        // 3. Instanciamos el Controlador amarrando la vista y el DAO
        // El constructor del controlador se encargará de activar los botones automáticamente
        LoginController controlador = new LoginController(pantallaLogin, daoUsuario);
        
        // 4. Centramos la pantalla en el monitor del usuario (buena práctica visual)
        pantallaLogin.setLocationRelativeTo(null);
        
        // 5. Hacemos visible la interfaz gráfica
        pantallaLogin.setVisible(true);
    }
} 