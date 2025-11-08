package Excepciones;

/**
 * Excepción lanzada cuando no se encuentra una entidad en la base de datos.
 * Ejemplos: estudiante no encontrado, carrera no encontrada, match no encontrado.
 *
 * @author Bonding Team
 */
public class EntidadNoEncontradaException extends BondingException {

    private final String tipoEntidad;
    private final Object identificador;

    public EntidadNoEncontradaException(String mensaje) {
        super(mensaje, "ENTIDAD_NO_ENCONTRADA");
        this.tipoEntidad = null;
        this.identificador = null;
    }

    public EntidadNoEncontradaException(String tipoEntidad, Object identificador) {
        super(String.format("No se encontró %s con identificador: %s", tipoEntidad, identificador),
              "ENTIDAD_NO_ENCONTRADA");
        this.tipoEntidad = tipoEntidad;
        this.identificador = identificador;
    }

    public EntidadNoEncontradaException(String mensaje, String tipoEntidad, Object identificador) {
        super(mensaje, "ENTIDAD_NO_ENCONTRADA");
        this.tipoEntidad = tipoEntidad;
        this.identificador = identificador;
    }

    public String getTipoEntidad() {
        return tipoEntidad;
    }

    public Object getIdentificador() {
        return identificador;
    }

    @Override
    public String getMensajeCompleto() {
        if (tipoEntidad != null && identificador != null) {
            return super.getMensajeCompleto() +
                   String.format(" [Tipo: %s, ID: %s]", tipoEntidad, identificador);
        }
        return super.getMensajeCompleto();
    }
}
