package DTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object para la entidad Match.
 * Evita referencias circulares y expone solo los datos necesarios.
 *
 * @author Bonding Team
 */
public class MatchDTO {

    private Long idMatch;
    private LocalDateTime fechaMatch;
    private List<Long> idEstudiantes;
    private Long idChat;
    private boolean tieneChat;

    public MatchDTO() {
    }

    public MatchDTO(Long idMatch, LocalDateTime fechaMatch) {
        this.idMatch = idMatch;
        this.fechaMatch = fechaMatch;
    }

    public MatchDTO(Long idMatch, LocalDateTime fechaMatch, List<Long> idEstudiantes, Long idChat) {
        this.idMatch = idMatch;
        this.fechaMatch = fechaMatch;
        this.idEstudiantes = idEstudiantes;
        this.idChat = idChat;
        this.tieneChat = idChat != null;
    }


    public Long getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(Long idMatch) {
        this.idMatch = idMatch;
    }

    public LocalDateTime getFechaMatch() {
        return fechaMatch;
    }

    public void setFechaMatch(LocalDateTime fechaMatch) {
        this.fechaMatch = fechaMatch;
    }

    public List<Long> getIdEstudiantes() {
        return idEstudiantes;
    }

    public void setIdEstudiantes(List<Long> idEstudiantes) {
        this.idEstudiantes = idEstudiantes;
    }

    public Long getIdChat() {
        return idChat;
    }

    public void setIdChat(Long idChat) {
        this.idChat = idChat;
        this.tieneChat = idChat != null;
    }

    public boolean isTieneChat() {
        return tieneChat;
    }

    public void setTieneChat(boolean tieneChat) {
        this.tieneChat = tieneChat;
    }

    @Override
    public String toString() {
        return "MatchDTO{" +
                "idMatch=" + idMatch +
                ", fechaMatch=" + fechaMatch +
                ", idEstudiantes=" + idEstudiantes +
                ", idChat=" + idChat +
                ", tieneChat=" + tieneChat +
                '}';
    }
}
