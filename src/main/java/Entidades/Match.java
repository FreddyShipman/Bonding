package Entidades;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

/**
 * @author Fred
 */
@Entity
public class Match implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idMatch;

    private LocalDateTime fechaMatch;

    private Long idEstudiante1;

    private Long idEstudiante2;

    @OneToOne(mappedBy = "match", cascade = CascadeType.ALL)
    private Chat chat;

    public Match() {
    }

    public Match(Long idMatch, LocalDateTime fechaMatch, Long idEstudiante1, Long idEstudiante2) {
        this.idMatch = idMatch;
        this.fechaMatch = fechaMatch;
        this.idEstudiante1 = idEstudiante1;
        this.idEstudiante2 = idEstudiante2;
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

    public Long getIdEstudiante1() {
        return idEstudiante1;
    }

    public void setIdEstudiante1(Long idEstudiante1) {
        this.idEstudiante1 = idEstudiante1;
    }

    public Long getIdEstudiante2() {
        return idEstudiante2;
    }

    public void setIdEstudiante2(Long idEstudiante2) {
        this.idEstudiante2 = idEstudiante2;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
