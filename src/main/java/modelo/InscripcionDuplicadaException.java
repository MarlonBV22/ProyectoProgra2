package modelo;

// Heredamos de Exception para hacer una excepción personalizada
public class InscripcionDuplicadaException extends Exception {
    
    public InscripcionDuplicadaException(String mensaje) {
        super(mensaje);
    }
}

