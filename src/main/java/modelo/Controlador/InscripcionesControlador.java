package modelo.Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Inscripcion;
import modelo.ClasesDAO.InscripcionDAO;
import modelo.ClasesDAO.UsuarioDAO;
import modelo.ClasesDAO.CursoDAO;
import modelo.Usuario;
import modelo.Curso;
import modelo.InscripcionDuplicadaException;
import modelo.vistas.VistaInscripciones;
import modelo.vistas.VistaOpcionesProfesor;

public class InscripcionesControlador implements ActionListener {

    private VistaInscripciones vista;
    private InscripcionDAO inscripcionDao;
    private UsuarioDAO usuarioDao; // Para verificar que el estudiante exista
    private CursoDAO cursoDao;     // Para verificar que el curso exista

    public InscripcionesControlador(VistaInscripciones vista, InscripcionDAO inscripcionDao, UsuarioDAO usuarioDao, CursoDAO cursoDao) {
        this.vista = vista;
        this.inscripcionDao = inscripcionDao;
        this.usuarioDao = usuarioDao;
        this.cursoDao = cursoDao;

        // Funcionalidad de los botones
        this.vista.btnCrear.addActionListener(this);
        this.vista.btnVisualizar.addActionListener(this);
        this.vista.btnRegresarMenu.addActionListener(this);
        this.vista.btnBuscar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);

        cargarTablaInscripciones(); // Muestra las inscripciones existentes al abrir
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Regresar al menú de opciones
        if (e.getSource() == vista.btnRegresarMenu) {
            VistaOpcionesProfesor menu = new VistaOpcionesProfesor();
            new OpcionesProfesorControlador(menu);
            menu.setLocationRelativeTo(null);
            menu.setVisible(true);
            vista.dispose();
        }
        
        // Botón Buscar Inscripción por ID
        if (e.getSource() == vista.btnBuscar) {
            // 1. Solicitamos el ID de la inscripción mediante una cajita emergente limpia
            String inputId = javax.swing.JOptionPane.showInputDialog(vista, "Ingrese el ID de la inscripción a buscar:", "Buscar Inscripción", javax.swing.JOptionPane.QUESTION_MESSAGE);

            if (inputId == null || inputId.trim().isEmpty()) {
                return;
            }

            try {
                int idInscripcion = Integer.parseInt(inputId.trim());

                // 2. Consultamos al InscripcionDAO
                Inscripcion inscripcionEncontrada = inscripcionDao.buscarInscripcionPorId(idInscripcion);

                // 3. Procesamos el resultado
                if (inscripcionEncontrada != null) {
                    // Rellenamos automáticamente las casillas de texto con los IDs correspondientes
                    vista.txtIdEstudiante.setText(String.valueOf(inscripcionEncontrada.getIdEstudiante()));
                    vista.txtIdCurso.setText(String.valueOf(inscripcionEncontrada.getIdCurso()));

                    javax.swing.JOptionPane.showMessageDialog(vista, "¡Inscripción encontrada y cargada en el formulario!");
                } else {
                    javax.swing.JOptionPane.showMessageDialog(vista, "No se encontró ninguna inscripción con el ID: " + idInscripcion, "Sin resultados", javax.swing.JOptionPane.WARNING_MESSAGE);
                }

            } catch (NumberFormatException nfe) {
                javax.swing.JOptionPane.showMessageDialog(vista, "Por favor, ingrese un ID numérico válido.", "Error de formato", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }


        // Matricular / Inscribir Estudiante
        if (e.getSource() == vista.btnCrear) {
            String idEstInput = vista.txtIdEstudiante.getText().trim();
            String idCurInput = vista.txtIdCurso.getText().trim();

            if (idEstInput.isEmpty() || idCurInput.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Por favor, complete los espacios de ID Estudiante e ID Curso.");
                return;
            }

            try {
                int idEstudiante = Integer.parseInt(idEstInput);
                int idCurso = Integer.parseInt(idCurInput);

                // Validación 1: Verificar que el estudiante exista y sea ESTUDIANTE
                Usuario estudianteValido = usuarioDao.buscarUsuarioPorId(idEstudiante);
                if (estudianteValido == null || !"ESTUDIANTE".equalsIgnoreCase(estudianteValido.getRol())) {
                    JOptionPane.showMessageDialog(vista, "Error: El ID ingresado no corresponde a ningún ESTUDIANTE registrado.", "Estudiante Inválido", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validación 2: Verificar que el curso exista en la base de SQL
                Curso cursoValido = cursoDao.buscarCursoPorId(idCurso);
                if (cursoValido == null) {
                    JOptionPane.showMessageDialog(vista, "Error: El ID ingresado no corresponde a ningún CURSO existente.", "Curso Inválido", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Inscripcion nuevaInscripcion = new Inscripcion(0, idEstudiante, idCurso, LocalDate.now());

                // Intentamos insertar
                if (inscripcionDao.insertarInscripcion(nuevaInscripcion)) {
                    JOptionPane.showMessageDialog(vista, "¡Estudiante inscrito con éxito en el curso!");
                    limpiarCampos();
                    cargarTablaInscripciones(); 
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al intentar procesar la inscripción.");
                }

                } catch (InscripcionDuplicadaException ide) {
                    // Si salta la excepción de duplicado, capturamos su mensaje original
                    JOptionPane.showMessageDialog(vista, ide.getMessage(), "Matrícula Duplicada", JOptionPane.WARNING_MESSAGE);

                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(vista, "Los IDs deben ser valores numéricos enteros", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                }
        }

        // Visualizar / Refrescar la tabla manualmente
        if (e.getSource() == vista.btnVisualizar) {
            cargarTablaInscripciones();
        }

        // Eliminar Inscripción
        if (e.getSource() == vista.btnEliminar) {
            int filaSeleccionada = vista.tblInscripciones.getSelectedRow();

            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(vista, "Seleccione una inscripción de la tabla para eliminarla.");
                return;
            }

            int idInscripcion = (int) vista.tblInscripciones.getValueAt(filaSeleccionada, 0);
            int confirmar = JOptionPane.showConfirmDialog(vista, "¿Está seguro que quiere eliminar esta inscripción?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if (confirmar == JOptionPane.YES_OPTION) {
                if (inscripcionDao.eliminarInscripcion(idInscripcion)) {
                    JOptionPane.showMessageDialog(vista, "Inscripción eliminada con éxito.");
                    limpiarCampos();
                    cargarTablaInscripciones();
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al intentar eliminar la inscripción.");
                }
            }
        }
        
        // Botón Editar Inscripción
        if (e.getSource() == vista.btnEditar) {
            // Validamos que el profesor haya seleccionado la fila a editar en la tabla
            int filaSeleccionada = vista.tblInscripciones.getSelectedRow();

            if (filaSeleccionada == -1) {
                javax.swing.JOptionPane.showMessageDialog(vista, "Por favor, seleccione la fila de la inscripción en la tabla que desea actualizar.");
                return;
            }

            // Extraemos el ID original de la inscripción
            int idInscripcion = (int) vista.tblInscripciones.getValueAt(filaSeleccionada, 0);
            String idEstInput = vista.txtIdEstudiante.getText().trim();
            String idCurInput = vista.txtIdCurso.getText().trim();

            if (idEstInput.isEmpty() || idCurInput.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(vista, "Por favor, llene los espacios con los nuevos datos.");
                return;
            }

            try {
                int idEstudiante = Integer.parseInt(idEstInput);
                int idCurso = Integer.parseInt(idCurInput);

                // Verificar que el nuevo estudiante exista en la base de SQL
                Usuario estudianteValido = usuarioDao.buscarUsuarioPorId(idEstudiante);
                if (estudianteValido == null || !"ESTUDIANTE".equalsIgnoreCase(estudianteValido.getRol())) {
                    javax.swing.JOptionPane.showMessageDialog(vista, "Error: El ID ingresado no corresponde a ningún ESTUDIANTE registrado.", "Estudiante Inválido", javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Verificar que el nuevo curso exista en la base de SQL
                Curso cursoValido = cursoDao.buscarCursoPorId(idCurso);
                if (cursoValido == null) {
                    javax.swing.JOptionPane.showMessageDialog(vista, "Error: El ID ingresado no corresponde a ningún CURSO existente.", "Curso Inválido", javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Formamos el objeto Inscripcion actualizado
                Inscripcion inscripcionEditada = new Inscripcion(idInscripcion, idEstudiante, idCurso, LocalDate.now());

                // Ejecutamos la actualización en la base de datos
                if (inscripcionDao.actualizarInscripcion(inscripcionEditada)) {
                    javax.swing.JOptionPane.showMessageDialog(vista, "¡Inscripción actualizada con éxito en la base de datos!");
                    limpiarCampos();
                    cargarTablaInscripciones(); // Refrescamos la tabla visual automáticamente
                } else {
                    javax.swing.JOptionPane.showMessageDialog(vista, "Error al intentar actualizar la inscripción.");
                }

            } catch (NumberFormatException nfe) {
                javax.swing.JOptionPane.showMessageDialog(vista, "Los IDs deben ser valores numéricos enteros.", "Error de Formato", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }

    }
    
    
    private void cargarTablaInscripciones() {
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tblInscripciones.getModel();
        modeloTabla.setRowCount(0); // Limpiamos las filas viejas

        // Traemos la lista estándar de inscripciones
        ArrayList<Inscripcion> lista = (ArrayList<Inscripcion>) inscripcionDao.listarInscripciones();

        // Recorremos cada inscripción
        for (Inscripcion ins : lista) {

            // El controlador usa los DAO para buscar los nombres reales en la base de SQL usando los IDs
            Usuario estudiante = usuarioDao.buscarUsuarioPorId(ins.getIdEstudiante());
            Curso curso = cursoDao.buscarCursoPorId(ins.getIdCurso());

            // Cuidamos que no tire errores si algún dato fue borrado externamente
            String nombreEstudiante = (estudiante != null) ? estudiante.getNombre() : "No encontrado";
            String nombreCurso = (curso != null) ? curso.getNombreCurso() : "No encontrado";

            // Estructuramos la nueva fila con las 6 columnas completas para la interfaz gráfica
            Object[] fila = new Object[6];
            fila[0] = ins.getIdInscripcion();
            fila[1] = ins.getIdEstudiante();
            fila[2] = nombreEstudiante; // Nueva columna visual
            fila[3] = ins.getIdCurso();
            fila[4] = nombreCurso;     // Nueva columna visual
            fila[5] = ins.getFechaInscripcion();

            modeloTabla.addRow(fila);
        }
    }


    private void limpiarCampos() {
        vista.txtIdEstudiante.setText("");
        vista.txtIdCurso.setText("");
    }
}
