package modelo.Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import modelo.Inscripcion;
import modelo.Usuario;
import modelo.Curso;
import modelo.ClasesDAO.InscripcionDAO;
import modelo.ClasesDAO.UsuarioDAO;
import modelo.ClasesDAO.CursoDAO;
import modelo.Curso;
import modelo.Usuario;
import modelo.vistas.VistaInformacionEstudiante;
import modelo.vistas.VistaLogin;

public class EstudianteControlador implements ActionListener {

    private VistaInformacionEstudiante vista;
    private InscripcionDAO inscripcionDao;
    private UsuarioDAO usuarioDao;
    private CursoDAO cursoDao;
    private int idEstudianteLogueado;

    public EstudianteControlador(VistaInformacionEstudiante vista, InscripcionDAO inscripcionDao, UsuarioDAO usuarioDao, CursoDAO cursoDao, int idEstudiante) {
        this.vista = vista;
        this.inscripcionDao = inscripcionDao;
        this.usuarioDao = usuarioDao;
        this.cursoDao = cursoDao;
        this.idEstudianteLogueado = idEstudiante;

        this.vista.btnCerrarSesion.addActionListener(this);

        cargarTablaEstudiante();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnCerrarSesion) {
            VistaLogin login = new VistaLogin();
            UsuarioDAO daoUsuario = new UsuarioDAO();
            new LoginControlador(login, daoUsuario);
            login.setLocationRelativeTo(null);
            login.setVisible(true);
            vista.dispose();
        }
    }

    private void cargarTablaEstudiante() {
        javax.swing.table.DefaultTableModel modeloTabla = (javax.swing.table.DefaultTableModel) vista.tblMisCursos.getModel();
        modeloTabla.setRowCount(0);
        
        ArrayList<Inscripcion> lista = inscripcionDao.listarInscripcionesPorEstudiante(idEstudianteLogueado);

        for (Inscripcion ins : lista) {
            Usuario alumno = usuarioDao.buscarUsuarioPorId(ins.getIdEstudiante());
            Curso curs = cursoDao.buscarCursoPorId(ins.getIdCurso());

            String nombreAlumno = (alumno != null) ? alumno.getNombre() : "No encontrado";
            String nombreMateria = (curs != null) ? curs.getNombreCurso() : "No encontrado";

            Object[] fila = new Object[6];
            fila[0] = ins.getIdInscripcion();
            fila[1] = ins.getIdEstudiante();
            fila[2] = nombreAlumno;
            fila[3] = ins.getIdCurso();
            fila[4] = nombreMateria;
            fila[5] = ins.getFechaInscripcion();

            modeloTabla.addRow(fila);
        }
    }
}
