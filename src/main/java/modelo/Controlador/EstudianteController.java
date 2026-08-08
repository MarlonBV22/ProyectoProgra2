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
import modelo.vistas.VistaInformacionEstudiante;
import modelo.vistas.VistaLogin;

public class EstudianteController implements ActionListener {

    private VistaInformacionEstudiante vista;
    private InscripcionDAO inscripcionDao;
    private UsuarioDAO usuarioDao;
    private CursoDAO cursoDao;

    public EstudianteController(VistaInformacionEstudiante vista, InscripcionDAO inscripcionDao, UsuarioDAO usuarioDao, CursoDAO cursoDao) {
        this.vista = vista;
        this.inscripcionDao = inscripcionDao;
        this.usuarioDao = usuarioDao;
        this.cursoDao = cursoDao;

        // Da funcionalidad al botón de cerrar sesión
        this.vista.btnCerrarSesion.addActionListener(this);

        cargarTablaEstudiante(); // Llena la tabla automáticamente al iniciar
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Si el estudiante desea cerrar sesión y volver a la pantalla de Login
        if (e.getSource() == vista.btnCerrarSesion) {
            VistaLogin login = new VistaLogin();
            UsuarioDAO daoUsuario = new UsuarioDAO();
            new LoginController(login, daoUsuario); // Cierra el circuito regresando al Login activo
            
            login.setLocationRelativeTo(null);
            login.setVisible(true);
            vista.dispose(); // Destruye el panel del alumno
        }
    }

    private void cargarTablaEstudiante() {
        javax.swing.table.DefaultTableModel modeloTabla = (javax.swing.table.DefaultTableModel) vista.tblMisCursos.getModel();
        modeloTabla.setRowCount(0); // Limpiar filas previas

        // Consultamos la persistencia global de inscripciones
        ArrayList<Inscripcion> lista = (ArrayList<Inscripcion>) inscripcionDao.listarInscripciones();

        for (Inscripcion ins : lista) {
            // Buscamos los textos de los nombres con los DAO correspondientes
            Usuario alumno = usuarioDao.buscarUsuarioPorId(ins.getIdEstudiante());
            Curso curs = cursoDao.buscarCursoPorId(ins.getIdCurso());

            String nombreAlumno = (alumno != null) ? alumno.getNombre() : "No encontrado";
            String nombreMateria = (curs != null) ? curs.getNombreCurso() : "No encontrado";

            // Pintamos las 6 columnas en la tabla del estudiante
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

