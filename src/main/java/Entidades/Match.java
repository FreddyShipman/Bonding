package Entidades;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Set;

/**
 * @author Fred
 */
@Entity
@Table(name = "Matches")
public class Match implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMatch;

    private LocalDateTime fechaMatch;

    @ManyToMany
    @JoinTable(
        name = "match_estudiante",
        joinColumns = @JoinColumn(name = "id_match"),
        inverseJoinColumns = @JoinColumn(name = "id_estudiante")
    )
    private Set<Estudiante> estudiantes;

    @OneToOne(mappedBy = "match", cascade = CascadeType.ALL)
    private Chat chat;

    public Match() {
    }
    
    public Match(Long idMatch, LocalDateTime fechaMatch, Set<Estudiante> estudiantes) {
        this.idMatch = idMatch;
        this.fechaMatch = fechaMatch;
        this.estudiantes = estudiantes;
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

    public Set<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(Set<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
