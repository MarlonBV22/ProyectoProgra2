package modelo.ClasesDAO;

/**
 *
 * @author Jorge Villafuerte
 */



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Config.Conexion;
import modelo.Curso;

public class CursoDAO {

    public boolean insertarCurso(Curso curso) {

        String sql = "INSERT INTO cursos (nombre_curso, descripcion, id_profesor) VALUES (?, ?, ?)";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, curso.getNombreCurso());
            ps.setString(2, curso.getDescripcion());
            ps.setInt(3, curso.getIdProfesor());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar curso: " + e.getMessage());
        }

        return false;
    }

    public ArrayList<Curso> listarCursos() {

        ArrayList<Curso> lista = new ArrayList<>();

        String sql = "SELECT * FROM cursos";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Curso curso = new Curso(
                        rs.getInt("id_curso"),
                        rs.getString("nombre_curso"),
                        rs.getString("descripcion"),
                        rs.getInt("id_profesor")
                );

                lista.add(curso);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar cursos: " + e.getMessage());
        }

        return lista;
    }

    public Curso buscarCursoPorId(int idCurso) {

        String sql = "SELECT * FROM cursos WHERE id_curso = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCurso);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new Curso(
                            rs.getInt("id_curso"),
                            rs.getString("nombre_curso"),
                            rs.getString("descripcion"),
                            rs.getInt("id_profesor")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar curso: " + e.getMessage());
        }

        return null;
    }

    public boolean actualizarCurso(Curso curso) {

        String sql = "UPDATE cursos SET nombre_curso = ?, descripcion = ?, id_profesor = ? WHERE id_curso = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, curso.getNombreCurso());
            ps.setString(2, curso.getDescripcion());
            ps.setInt(3, curso.getIdProfesor());
            ps.setInt(4, curso.getIdCurso());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar curso: " + e.getMessage());
        }

        return false;
    }

    public boolean eliminarCurso(int idCurso) {

        String sql = "DELETE FROM cursos WHERE id_curso = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCurso);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar curso: " + e.getMessage());
        }

        return false;
    }
}