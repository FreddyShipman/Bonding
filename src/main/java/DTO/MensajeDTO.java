package DTO;

import java.time.LocalDateTime;

/**
 * Data Transfer Object para la entidad Mensaje.
 * Evita referencias circulares y expone solo los datos necesarios.
 *
 * @author Bonding Team
 */
public class MensajeDTO {

    private Long idMensaje;
    private String contenido;
    private LocalDateTime fechaEnvio;
    private Long idEstudianteEmisor;
    private String nombreEmisor;
    private Long idChat;

    public MensajeDTO() {
    }

    public MensajeDTO(Long idMensaje, String contenido, LocalDateTime fechaEnvio, Long idEstudianteEmisor) {
        this.idMensaje = idMensaje;
        this.contenido = contenido;
        this.fechaEnvio = fechaEnvio;
        this.idEstudianteEmisor = idEstudianteEmisor;
    }

    public MensajeDTO(Long idMensaje, String contenido, LocalDateTime fechaEnvio,
                      Long idEstudianteEmisor, String nombreEmisor, Long idChat) {
        this.idMensaje = idMensaje;
        this.contenido = contenido;
        this.fechaEnvio = fechaEnvio;
        this.idEstudianteEmisor = idEstudianteEmisor;
        this.nombreEmisor = nombreEmisor;
        this.idChat = idChat;
    }


    public Long getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(Long idMensaje) {
        this.idMensaje = idMensaje;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Long getIdEstudianteEmisor() {
        return idEstudianteEmisor;
    }

    public void setIdEstudianteEmisor(Long idEstudianteEmisor) {
        this.idEstudianteEmisor = idEstudianteEmisor;
    }

    public String getNombreEmisor() {
        return nombreEmisor;
    }

    public void setNombreEmisor(String nombreEmisor) {
        this.nombreEmisor = nombreEmisor;
    }

    public Long getIdChat() {
        return idChat;
    }

    public void setIdChat(Long idChat) {
        this.idChat = idChat;
    }

    @Override
    public String toString() {
        return "MensajeDTO{" +
                "idMensaje=" + idMensaje +
                ", contenido='" + contenido + '\'' +
                ", fechaEnvio=" + fechaEnvio +
                ", idEstudianteEmisor=" + idEstudianteEmisor +
                ", nombreEmisor='" + nombreEmisor + '\'' +
                ", idChat=" + idChat +
                '}';
    }
}
