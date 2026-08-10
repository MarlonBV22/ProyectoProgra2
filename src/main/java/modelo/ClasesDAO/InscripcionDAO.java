
package modelo.ClasesDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Config.Conexion;
import modelo.Inscripcion;

public class InscripcionDAO {

    public boolean insertarInscripcion(Inscripcion inscripcion) {

        String sql = "INSERT INTO inscripciones (id_estudiante, id_curso, fecha_inscripcion) VALUES (?, ?, ?)";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, inscripcion.getIdEstudiante());
            ps.setInt(2, inscripcion.getIdCurso());
            ps.setDate(3, Date.valueOf(inscripcion.getFechaInscripcion()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar inscripción: " + e.getMessage());
        }

        return false;
    }

    public ArrayList<Inscripcion> listarInscripciones() {

        ArrayList<Inscripcion> lista = new ArrayList<>();

        String sql = "SELECT * FROM inscripciones";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Inscripcion inscripcion = new Inscripcion(
                        rs.getInt("id_inscripcion"),
                        rs.getInt("id_estudiante"),
                        rs.getInt("id_curso"),
                        rs.getDate("fecha_inscripcion").toLocalDate()
                );

                lista.add(inscripcion);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar inscripciones: " + e.getMessage());
        }

        return lista;
    }
    
    // Este método nos va a permitir obtener o devolver filas de un alumno en específico
    public ArrayList<Inscripcion> listarInscripcionesPorEstudiante(int idEstudiante) {
        
        ArrayList<Inscripcion> lista = new ArrayList<>();
        
        String sql = "SELECT * FROM inscripciones WHERE id_estudiante = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEstudiante);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Inscripcion inscripcion = new Inscripcion(
                            rs.getInt("id_inscripcion"),
                            rs.getInt("id_estudiante"),
                            rs.getInt("id_curso"),
                            rs.getDate("fecha_inscripcion").toLocalDate()
                    );
                    lista.add(inscripcion);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al filtrar inscripciones del estudiante: " + e.getMessage());
        }
        return lista;
    }


    public Inscripcion buscarInscripcionPorId(int idInscripcion) {

        String sql = "SELECT * FROM inscripciones WHERE id_inscripcion = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idInscripcion);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new Inscripcion(
                            rs.getInt("id_inscripcion"),
                            rs.getInt("id_estudiante"),
                            rs.getInt("id_curso"),
                            rs.getDate("fecha_inscripcion").toLocalDate()
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar inscripción: " + e.getMessage());
        }

        return null;
    }

    public boolean actualizarInscripcion(Inscripcion inscripcion) {

        String sql = "UPDATE inscripciones SET id_estudiante = ?, id_curso = ?, fecha_inscripcion = ? WHERE id_inscripcion = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, inscripcion.getIdEstudiante());
            ps.setInt(2, inscripcion.getIdCurso());
            ps.setDate(3, Date.valueOf(inscripcion.getFechaInscripcion()));
            ps.setInt(4, inscripcion.getIdInscripcion());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar inscripción: " + e.getMessage());
        }

        return false;
    }

    public boolean eliminarInscripcion(int idInscripcion) {

        String sql = "DELETE FROM inscripciones WHERE id_inscripcion = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idInscripcion);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar inscripción: " + e.getMessage());
        }

        return false;
    }
}