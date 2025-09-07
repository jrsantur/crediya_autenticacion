package pe.crediya.autenticacion.model.usuario;

public enum AccionSistema {
    // Gestión de usuarios
    CREAR_USUARIO,
    CONSULTAR_USUARIO,
    ACTUALIZAR_USUARIO,
    ELIMINAR_USUARIO,

    // Gestión de solicitudes
    CREAR_SOLICITUD,
    CONSULTAR_SOLICITUD,
    ACTUALIZAR_SOLICITUD,
    APROBAR_SOLICITUD,
    RECHAZAR_SOLICITUD,
    DESEMBOLSAR_SOLICITUD,

    // Reportes
    GENERAR_REPORTES,
    CONSULTAR_ESTADISTICAS
}
