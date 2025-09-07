package pe.crediya.autenticacion.model.usuario.exception;

public class AccesoNoAutorizadoException extends DomainValidationException   {
    public AccesoNoAutorizadoException(String message) {
        super(message);
    }
    public AccesoNoAutorizadoException(String message, Throwable cause) {
        super(message, cause);
    }
}
