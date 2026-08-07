
package modelo.Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Curso;
import modelo.Profesor;
import modelo.Usuario;
import modelo.ClasesDAO.CursoDAO;
import modelo.ClasesDAO.UsuarioDAO;
import modelo.vistas.VistaCursos;
import modelo.vistas.VistaOpcionesProfesor;

public class CursosController implements ActionListener {

    private VistaCursos vista;
    private CursoDAO cursoDao;
    private UsuarioDAO usuarioDao; // Para validar que el profesor exista en la base de datos

    public CursosController(VistaCursos vista, CursoDAO cursoDao, UsuarioDAO usuarioDao) {
        this.vista = vista;
        this.cursoDao = cursoDao;
        this.usuarioDao = usuarioDao;

        // Para hacer funcionar todos los botones de la interfaz
        this.vista.btnBuscar.addActionListener(this);
        this.vista.btnCrear.addActionListener(this);
        this.vista.btnVisualizar.addActionListener(this);
        this.vista.btnRegresarMenu.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        
        cargarTablaCursos(); // Carga los cursos existentes al abrir la pantalla
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        // Regresar al menú de opciones
        if (e.getSource() == vista.btnRegresarMenu) {
            VistaOpcionesProfesor menu = new VistaOpcionesProfesor();
            new OpcionesProfesorController(menu);
            menu.setLocationRelativeTo(null);
            menu.setVisible(true);
            vista.dispose();
        }
        
        // Botón Buscar Curso por ID
        if (e.getSource() == vista.btnBuscar) {
            // 1. Le pedimos el ID del curso al profesor con una cajita emergente limpia
            String inputId = javax.swing.JOptionPane.showInputDialog(vista, "Ingrese el ID del curso a buscar:", "Buscar Curso", javax.swing.JOptionPane.QUESTION_MESSAGE);

            // Si el profesor presiona cancelar o cierra la ventana flotante, salimos sin hacer nada
            if (inputId == null || inputId.trim().isEmpty()) {
                return;
            }

            try {
                // 2. Convertimos el texto ingresado a un número entero
                int idCurso = Integer.parseInt(inputId.trim());

                // 3. Consultamos a tu CursoDAO utilizando el método que ya tienes listo
                Curso cursoEncontrado = cursoDao.buscarCursoPorId(idCurso);

                // 4. Procesamos el resultado
                if (cursoEncontrado != null) {
                    // Si lo encuentra, rellenamos las cajas de texto y el JTextArea de la izquierda
                    vista.txtNombreCurso.setText(cursoEncontrado.getNombreCurso());
                    // Extraemos el ID del profesor
                    vista.txtIdProfesor.setText(String.valueOf(cursoEncontrado.getIdProfesor()));
                    vista.txtDescripcion.setText(cursoEncontrado.getDescripcion());

                    javax.swing.JOptionPane.showMessageDialog(vista, "¡Curso encontrado y cargado en el formulario!");
                } else {
                    javax.swing.JOptionPane.showMessageDialog(vista, "No se encontró ningún curso con el ID: " + idCurso, "Sin resultados", javax.swing.JOptionPane.WARNING_MESSAGE);
                }

            } catch (NumberFormatException nfe) {
                // Si el profesor escribe letras en lugar de un número entero
                javax.swing.JOptionPane.showMessageDialog(vista, "Por favor, ingrese un ID numérico válido.", "Error de formato", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }

        // Crear un Curso
        if (e.getSource() == vista.btnCrear) {
            String nombre = vista.txtNombreCurso.getText().trim();
            String idProfInput = vista.txtIdProfesor.getText().trim();
            String descripcion = vista.txtDescripcion.getText().trim();

            if (nombre.isEmpty() || idProfInput.isEmpty() || descripcion.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Por favor, llene todos los espacios del formulario.");
                return;
            }

            try {
                int idProfesor = Integer.parseInt(idProfInput);

                // Importante. Validamos si el ID le pertenece a un profesor real
                Usuario usuarioValido = usuarioDao.buscarUsuarioPorId(idProfesor);

                if (usuarioValido == null || !"PROFESOR".equalsIgnoreCase(usuarioValido.getRol())) {
                    JOptionPane.showMessageDialog(vista, "Error: El ID ingresado no corresponde a ningún PROFESOR registrado.", "Profesor Inválido", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Si la validación pasa, creamos el objeto Curso asociándole ese Profesor
                Profesor prof = (Profesor) usuarioValido;
                // Le pasamos el ID numérico
                Curso nuevoCurso = new Curso(0, nombre, descripcion, prof.getidUsuario());


                if (cursoDao.insertarCurso(nuevoCurso)) {
                    JOptionPane.showMessageDialog(vista, "¡Curso '" + nombre + "' creado con éxito en la base de datos!");
                    limpiarCampos();
                    cargarTablaCursos(); // Refresca la tabla automáticamente
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al intentar registrar el curso.");
                }

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(vista, "El ID del profesor debe ser un valor numérico.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Visualizar / Refrescar la tabla manualmente
        if (e.getSource() == vista.btnVisualizar) {
            cargarTablaCursos();
        }
        
        // Botón Eliminar Curso
        if (e.getSource() == vista.btnEliminar) {
            // 1. Obtenemos la fila seleccionada por el usuario en la tabla
            int filaSeleccionada = vista.tblCursos.getSelectedRow();

            if (filaSeleccionada == -1) {
                javax.swing.JOptionPane.showMessageDialog(vista, "Por favor, seleccione un curso de la tabla para eliminar.");
                return;
            }

            // 2. Extraemos el ID numérico del curso de la columna 0
            int idCurso = (int) vista.tblCursos.getValueAt(filaSeleccionada, 0);

            // 3. Cuadro de confirmación por seguridad
            int confirmar = javax.swing.JOptionPane.showConfirmDialog(vista, 
                    "¿Está seguro de eliminar este curso? Esto borrará también sus inscripciones", 
                    "Confirmar Eliminación", javax.swing.JOptionPane.YES_NO_OPTION);

            if (confirmar == javax.swing.JOptionPane.YES_OPTION) {
                // 4. Llamamos al método DELETE
                if (cursoDao.eliminarCurso(idCurso)) {
                    javax.swing.JOptionPane.showMessageDialog(vista, "¡Curso eliminado con éxito de la base de datos!");
                    limpiarCampos();
                    cargarTablaCursos(); // Refrescamos la tabla automáticamente
                } else {
                    javax.swing.JOptionPane.showMessageDialog(vista, "Error al intentar eliminar el curso.");
                }
            }
        }
        
        // Botón Editar Curso
        if (e.getSource() == vista.btnEditar) {
            int filaSeleccionada = vista.tblCursos.getSelectedRow();

            if (filaSeleccionada == -1) {
                javax.swing.JOptionPane.showMessageDialog(vista, "Por favor, seleccione un curso de la tabla para editar.");
                return;
            }

            // Extraemos el ID original del curso
            int idCurso = (int) vista.tblCursos.getValueAt(filaSeleccionada, 0);
            String nombre = vista.txtNombreCurso.getText().trim();
            String idProfInput = vista.txtIdProfesor.getText().trim();
            String descripcion = vista.txtDescripcion.getText().trim();

            if (nombre.isEmpty() || idProfInput.isEmpty() || descripcion.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(vista, "Por favor, llene todos los espacios con los nuevos datos.");
                return;
            }

            try {
                int idProfesor = Integer.parseInt(idProfInput);

                // Importante. Validamos que el nuevo ID asignado pertenezca a un profesor real
                Usuario usuarioValido = usuarioDao.buscarUsuarioPorId(idProfesor);
                if (usuarioValido == null || !"PROFESOR".equalsIgnoreCase(usuarioValido.getRol())) {
                    javax.swing.JOptionPane.showMessageDialog(vista, "Error: El ID ingresado no corresponde a ningún PROFESOR registrado.", "Profesor Inválido", javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Creamos el objeto Curso actualizado con el ID plano del profesor
                Curso cursoEditado = new Curso(idCurso, nombre, descripcion, idProfesor);

                // Llamamos al método UPDATE
                if (cursoDao.actualizarCurso(cursoEditado)) {
                    javax.swing.JOptionPane.showMessageDialog(vista, "¡Curso actualizado con éxito en la base de datos!");
                    limpiarCampos();
                    cargarTablaCursos(); // Refrescamos los datos gráficos
                } else {
                    javax.swing.JOptionPane.showMessageDialog(vista, "Error al intentar actualizar el curso.");
                }

            } catch (NumberFormatException nfe) {
                javax.swing.JOptionPane.showMessageDialog(vista, "El ID del profesor debe ser un valor numérico.", "Error de Formato", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    // Método para llenar el JTable con los cursos guardados en la base de datos
    private void cargarTablaCursos() {
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tblCursos.getModel();
        modeloTabla.setRowCount(0); // Limpia filas viejas

        ArrayList<Curso> lista = (ArrayList<Curso>) cursoDao.listarCursos();

        for (Curso c : lista) {
            Object[] fila = new Object[4];
            fila[0] = c.getIdCurso();
            fila[1] = c.getNombreCurso();
            fila[2] = c.getIdProfesor();
            fila[3] = c.getDescripcion();
            
            modeloTabla.addRow(fila);
        }
    }

    private void limpiarCampos() {
        vista.txtNombreCurso.setText("");
        vista.txtIdProfesor.setText("");
        vista.txtDescripcion.setText("");
    }
}

