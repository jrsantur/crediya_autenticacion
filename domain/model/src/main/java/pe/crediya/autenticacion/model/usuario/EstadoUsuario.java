package pe.crediya.autenticacion.model.usuario;

public enum EstadoUsuario {
    ACTIVO("Activo"),
    INACTIVO("Inactivo"),
    BLOQUEADO("Bloqueado"),
    PENDIENTE_ACTIVACION("Pendiente de Activación");

    private final String descripcion;

    EstadoUsuario(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
