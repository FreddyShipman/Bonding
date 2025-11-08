package Excepciones;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Excepción base para todas las excepciones personalizadas del sistema Bonding.
 * Incluye información de timestamp para facilitar el debugging.
 *
 * @author Bonding Team
 */
public class BondingException extends Exception {

    private final LocalDateTime timestamp;
    private final String codigoError;

    public BondingException(String mensaje) {
        super(mensaje);
        this.timestamp = LocalDateTime.now();
        this.codigoError = "BONDING_ERROR";
    }

    public BondingException(String mensaje, String codigoError) {
        super(mensaje);
        this.timestamp = LocalDateTime.now();
        this.codigoError = codigoError;
    }

    public BondingException(String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.timestamp = LocalDateTime.now();
        this.codigoError = "BONDING_ERROR";
    }

    public BondingException(String mensaje, String codigoError, Throwable causa) {
        super(mensaje, causa);
        this.timestamp = LocalDateTime.now();
        this.codigoError = codigoError;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getCodigoError() {
        return codigoError;
    }

    public String getMensajeCompleto() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] [%s] %s",
            timestamp.format(formatter),
            codigoError,
            getMessage());
    }

    @Override
    public String toString() {
        return getMensajeCompleto();
    }
}
