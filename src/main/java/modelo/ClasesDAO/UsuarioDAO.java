
package modelo.ClasesDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.Config.Conexion;
import modelo.Estudiante;
import modelo.Profesor;
import modelo.Usuario;


public class UsuarioDAO {
    public Usuario validarLogin(String email, String password) {
    String sql = "SELECT * FROM usuarios WHERE email = ? AND password = ?";
    
    try (Connection conn = Conexion.getConexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setString(1, email);
        ps.setString(2, password);
        
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {         
                int id = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre");
                String rol = rs.getString("rol");
                // Convertimos el TIMESTAMP de MySQL a LocalDateTime de Java
                java.time.LocalDateTime fechaReg = rs.getObject("fecha_registro", java.time.LocalDateTime.class);
                
                // 2. Aplicamos polimorfismo según el rol guardado en MySQL
                if ("ESTUDIANTE".equalsIgnoreCase(rol)) {
                    // Retornamos un Estudiante, pero viaja "disfrazado" de Usuario (Polimorfismo)
                    return new Estudiante(id, nombre, email, password, fechaReg);
                } else if ("PROFESOR".equalsIgnoreCase(rol)) {
                    // Retornamos un Profesor
                    return new Profesor(id, nombre, email, password, fechaReg);
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Error en validarLogin: " + e.getMessage());
    }
    return null; // Credenciales incorrectas o usuario no encontrado
}

}
