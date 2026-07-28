
package modelo.ClasesDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import modelo.Config.Conexion;
import modelo.Estudiante;
import modelo.Profesor;
import modelo.Usuario;


public class UsuarioDAO {
    public Usuario validarLogin(String email, String password) {
    String sql = "SELECT * FROM usuarios WHERE email = ? AND password = ?";
    
    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {// envia datos a la base de SQL HACE LA PREGUNTA
        
        ps.setString(1, email);//1 resive el primer ? 
        ps.setString(2, password);
        
        try (ResultSet rs = ps.executeQuery()) { //se trae y guarda el resultado
            if (rs.next()) {         
                int id = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre");
                String rol = rs.getString("rol");
                // Convertimos el TIMESTAMP de MySQL a LocalDateTime de Java
                java.time.LocalDateTime fechaReg = rs.getObject("fecha_registro", java.time.LocalDateTime.class);
                
                // Aplicamos polimorfismo según el rol guardado en MySQL
                if ("ESTUDIANTE".equalsIgnoreCase(rol)) {
                    // Retornamos un Estudiante, pero viaja "disfrazado" de Usuario (Polimorfismo)
                    return new Estudiante(id, nombre, email, password, fechaReg);
                } else if ("PROFESOR".equalsIgnoreCase(rol)) {
                    // Retornamos un Profesor
                    return new Profesor(id, nombre, email, password, fechaReg);
                }
            }
        }
    }   catch (SQLException e) {
            System.out.println("Error en validarLogin: " + e.getMessage());
        }
        return null; // Credenciales incorrectas o usuario no encontrado
    }

    public boolean insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, email, password, rol) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexion.getConexion();
            PreparedStatement ps = conn.prepareStatement(sql)) {
        
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getPassword());
            ps.setString(4, usuario.getRol()); // Guarda 'ESTUDIANTE' o 'PROFESOR' automáticamente
        
            // Si las filas afectadas son mayores a 0, devuelve true. Si no, devuelve false.
            return ps.executeUpdate() > 0;
        
        } catch (SQLException e) {
            System.out.println("Error al insertar usuario: " + e.getMessage());
            return false; // Si salta al catch por un error, devuelve false
        }
    }
    
    public ArrayList<Usuario> listarUsuarios() {
        ArrayList<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = Conexion.getConexion();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                String pass = rs.getString("password");
                String rol = rs.getString("rol");
                java.time.LocalDateTime fechaReg = rs.getObject("fecha_registro", java.time.LocalDateTime.class);

                // Polimorfismo: guardamos Estudiantes o Profesores en la lista de Usuarios
                if ("ESTUDIANTE".equalsIgnoreCase(rol)) {
                    lista.add(new Estudiante(id, nombre, email, pass, fechaReg));
                } else if ("PROFESOR".equalsIgnoreCase(rol)) {
                    lista.add(new Profesor(id, nombre, email, pass, fechaReg));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }

        return lista;
    }
    
    public Usuario buscarUsuarioPorId(int idUsuario) {
    String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";

    try (Connection conn = Conexion.getConexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idUsuario);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int id = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                String pass = rs.getString("password");
                String rol = rs.getString("rol");
                java.time.LocalDateTime fechaReg = rs.getObject("fecha_registro", java.time.LocalDateTime.class);

                // Evaluamos el rol para retornar la instancia correcta
                if ("ESTUDIANTE".equalsIgnoreCase(rol)) {
                    return new Estudiante(id, nombre, email, pass, fechaReg);
                } else if ("PROFESOR".equalsIgnoreCase(rol)) {
                    return new Profesor(id, nombre, email, pass, fechaReg);
                }
            }
        }

    } catch (SQLException e) {
        System.out.println("Error al buscar usuario: " + e.getMessage());
    }

    return null; // Si el ID no existe en la base de datos
    }
    
    public boolean actualizarUsuario(Usuario usuario) {
    String sql = "UPDATE usuarios SET nombre = ?, email = ?, password = ? WHERE id_usuario = ?";

    try (Connection conn = Conexion.getConexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, usuario.getNombre());
        ps.setString(2, usuario.getEmail());
        ps.setString(3, usuario.getPassword());
        ps.setInt(4, usuario.getidUsuario());

        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        System.out.println("Error al actualizar usuario: " + e.getMessage());
    }

        return false;
    }
    
    public boolean eliminarUsuario(int idUsuario) {
    String sql = "DELETE FROM usuarios WHERE id_usuario = ?";

    try (Connection conn = Conexion.getConexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idUsuario);

        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        System.out.println("Error al eliminar usuario: " + e.getMessage());
    }

    return false;
    }


}
