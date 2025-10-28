package DTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object para la entidad Chat.
 * Evita referencias circulares y expone solo los datos necesarios.
 *
 * @author Bonding Team
 */
public class ChatDTO {

    private Long idChat;
    private LocalDateTime fechaCreacion;
    private Long idMatch;
    private List<MensajeDTO> mensajes;
    private Integer cantidadMensajes;
    private MensajeDTO ultimoMensaje;

    public ChatDTO() {
    }

    public ChatDTO(Long idChat, LocalDateTime fechaCreacion, Long idMatch) {
        this.idChat = idChat;
        this.fechaCreacion = fechaCreacion;
        this.idMatch = idMatch;
    }

    public ChatDTO(Long idChat, LocalDateTime fechaCreacion, Long idMatch, Integer cantidadMensajes) {
        this.idChat = idChat;
        this.fechaCreacion = fechaCreacion;
        this.idMatch = idMatch;
        this.cantidadMensajes = cantidadMensajes;
    }


    public Long getIdChat() {
        return idChat;
    }

    public void setIdChat(Long idChat) {
        this.idChat = idChat;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(Long idMatch) {
        this.idMatch = idMatch;
    }

    public List<MensajeDTO> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<MensajeDTO> mensajes) {
        this.mensajes = mensajes;
        if (mensajes != null) {
            this.cantidadMensajes = mensajes.size();
        }
    }

    public Integer getCantidadMensajes() {
        return cantidadMensajes;
    }

    public void setCantidadMensajes(Integer cantidadMensajes) {
        this.cantidadMensajes = cantidadMensajes;
    }

    public MensajeDTO getUltimoMensaje() {
        return ultimoMensaje;
    }

    public void setUltimoMensaje(MensajeDTO ultimoMensaje) {
        this.ultimoMensaje = ultimoMensaje;
    }

    @Override
    public String toString() {
        return "ChatDTO{" +
                "idChat=" + idChat +
                ", fechaCreacion=" + fechaCreacion +
                ", idMatch=" + idMatch +
                ", cantidadMensajes=" + cantidadMensajes +
                '}';
    }
}
