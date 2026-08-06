
package modelo.Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Estudiante;
import modelo.Profesor;
import modelo.Usuario;
import modelo.ClasesDAO.UsuarioDAO;
import modelo.vistas.VistaUsuarios;
import modelo.vistas.VistaOpcionesProfesor;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class UsuariosController implements ActionListener {

    private VistaUsuarios vista;
    private UsuarioDAO usuarioDao;

    public UsuariosController(VistaUsuarios vista, UsuarioDAO usuarioDao) {
        this.vista = vista;
        this.usuarioDao = usuarioDao;

        this.vista.btnCrear.addActionListener(this);
        this.vista.btnRegresarMenu.addActionListener(this);
        this.vista.btnVisualizar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
                
        // Botón Regresar al Menú
        if (e.getSource() == vista.btnRegresarMenu) {
            VistaOpcionesProfesor menu = new VistaOpcionesProfesor();
            OpcionesProfesorController controlMenu = new OpcionesProfesorController(menu);
            menu.setLocationRelativeTo(null);
            menu.setVisible(true);
            vista.dispose(); // Cierra el administrador de usuarios
        }

        // Botón Crear Usuario
        if (e.getSource() == vista.btnCrear) {
            // 1. Extraer los datos básicos de la vista
            String nombre = vista.txtNombre.getText().trim();
            String correo = vista.txtCorreo.getText().trim();
            String password = new String(vista.txtPassword.getPassword()).trim();

            // 2. Validar que los campos de texto no estén vacíos
            if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Por favor, llene todos los campos de texto.");
                return;
            }

            // 3. Validar cuál Radio Button está seleccionado usando .isSelected()
            Usuario nuevoUsuario = null;

            if (vista.rbEstudiante.isSelected()) {
                // Instanciamos un Estudiante (El ID va en 0 porque MySQL lo autoincrementa, y la fecha va en null porque la pone el TIMESTAMP)
                nuevoUsuario = new Estudiante(0, nombre, correo, password, null);
            } else if (vista.rbProfesor.isSelected()) {
                // Instanciamos un Profesor
                nuevoUsuario = new Profesor(0, nombre, correo, password, null);
            } else {
                // Si el profesor no marcó ninguno de los dos círculos
                JOptionPane.showMessageDialog(vista, "Por favor, seleccione un Rol (Estudiante o Profesor).");
                return;
            }

            // 4. Enviar el objeto polimórfico al UsuarioDAO para insertarlo en MySQL
            boolean exito = usuarioDao.insertarUsuario(nuevoUsuario);

            if (exito) {
                JOptionPane.showMessageDialog(vista, "¡Usuario registrado con éxito en MySQL!");               
            } else {
                JOptionPane.showMessageDialog(vista, "Error al registrar. Verifique si el correo ya existe.");
            }
        }
        
        // Botón para visualizar la tabla de información
        if (e.getSource() == vista.btnVisualizar) {
            cargarTablaUsuarios(); // Llama al método para refrescar los datos
        }
        
       // Botón Eliminar
       if (e.getSource() == vista.btnEliminar) {
           // Obtenemos la fila seleccionada por el profesor en la tabla gráfica
           int filaSeleccionada = vista.tblUsuarios.getSelectedRow();

           if (filaSeleccionada == -1) {
               javax.swing.JOptionPane.showMessageDialog(vista, "Por favor, seleccione un usuario de la tabla para eliminar.");
               return;
           }

           // Extraemos el ID numérico de la columna 0 de esa fila seleccionada
           int idUsuario = (int) vista.tblUsuarios.getValueAt(filaSeleccionada, 0);

           // Confirmación de seguridad
           int confirmar = javax.swing.JOptionPane.showConfirmDialog(vista, "¿Está seguro de eliminar este usuario?", "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);

           if (confirmar == javax.swing.JOptionPane.YES_OPTION) {
               // Llamamos al método DELETE de UsuarioDAO utilizando el ID extraído
               if (usuarioDao.eliminarUsuario(idUsuario)) {
                   javax.swing.JOptionPane.showMessageDialog(vista, "¡Usuario eliminado con éxito de la base de datos!");
                   cargarTablaUsuarios(); // Refrescamos la tabla automáticamente para ver el cambio
               } else {
                   javax.swing.JOptionPane.showMessageDialog(vista, "Error al intentar eliminar el usuario.");
               }
           }
       }
       
        // Botón Editar (Actualizar)
        if (e.getSource() == vista.btnEditar) {
            int filaSeleccionada = vista.tblUsuarios.getSelectedRow();

            if (filaSeleccionada == -1) {
                javax.swing.JOptionPane.showMessageDialog(vista, "Seleccione un usuario de la tabla para editar.");
                return;
            }

            int idUsuario = (int) vista.tblUsuarios.getValueAt(filaSeleccionada, 0);
            String nombre = vista.txtNombre.getText().trim();
            String correo = vista.txtCorreo.getText().trim();
            String password = new String(vista.txtPassword.getPassword()).trim();

            if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(vista, "Llene los campos con los nuevos datos.");
                return;
            }

            // Creamos un objeto temporal para mandar la actualización (el rol e instancias hijas no cambian el UPDATE de SQL)
            Usuario usuarioEditado = new Estudiante(idUsuario, nombre, correo, password, null);

            if (usuarioDao.actualizarUsuario(usuarioEditado)) {
                javax.swing.JOptionPane.showMessageDialog(vista, "¡Usuario actualizado con éxito!");
                cargarTablaUsuarios(); // Refrescamos la tabla
            } else {
                javax.swing.JOptionPane.showMessageDialog(vista, "Error al actualizar.");
            }
        }
    }
    
    // Método interno del controlador para refrescar la tabla gráficamente
    private void cargarTablaUsuarios() {
    // 1. Obtenemos el modelo actual de la tabla
    javax.swing.table.DefaultTableModel modeloTabla = (javax.swing.table.DefaultTableModel) vista.tblUsuarios.getModel();
    
    // 2. Limpiamos las filas viejas para que no se dupliquen los datos al dar clic varias veces
    modeloTabla.setRowCount(0);
    
    // 3. Traemos la lista actualizada directamente de la base de datos MySQL
    ArrayList<Usuario> listaUsuarios = usuarioDao.listarUsuarios();
    
        // 4. Recorremos cada usuario y preparamos su fila para la interfaz
        for (Usuario u : listaUsuarios) {
            Object[] fila = new Object[5];
            fila[0] = u.getidUsuario();
            fila[1] = u.getNombre();
            fila[2] = u.getEmail();
            fila[3] = u.getRol(); // Mostrará 'ESTUDIANTE' o 'PROFESOR'
            fila[4] = u.getFechaRegistro(); // Mostrará la fecha TIMESTAMP de MySQL

            // Agregamos la fila estructurada al componente visual
            modeloTabla.addRow(fila);
        }
    }
}

