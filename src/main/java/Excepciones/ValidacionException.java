package Excepciones;

/**
 * Excepción lanzada cuando falla la validación de datos de entrada.
 * Ejemplos: campos requeridos vacíos, formato inválido, valores fuera de rango.
 *
 * @author Bonding Team
 */
public class ValidacionException extends BondingException {

    private final String campo;

    public ValidacionException(String mensaje) {
        super(mensaje, "VALIDACION_ERROR");
        this.campo = null;
    }

    public ValidacionException(String mensaje, String campo) {
        super(mensaje, "VALIDACION_ERROR");
        this.campo = campo;
    }

    public ValidacionException(String mensaje, Throwable causa) {
        super(mensaje, "VALIDACION_ERROR", causa);
        this.campo = null;
    }

    public String getCampo() {
        return campo;
    }

    @Override
    public String getMensajeCompleto() {
        if (campo != null && !campo.isEmpty()) {
            return super.getMensajeCompleto() + " - Campo: " + campo;
        }
        return super.getMensajeCompleto();
    }
}
