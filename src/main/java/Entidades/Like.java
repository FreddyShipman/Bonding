package Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */
@Entity
@Table(name = "MeGusta")
public class Like implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLike;

    private LocalDateTime fechaLike;

    @ManyToOne
    @JoinColumn(name = "id_estudiante_emisor")
    private Estudiante estudianteEmisor;

    @ManyToOne
    @JoinColumn(name = "id_estudiante_receptor")
    private Estudiante estudianteReceptor;

    public Like() {
    }

    public Like(Long idLike, LocalDateTime fechaLike, Estudiante estudianteEmisor, Estudiante estudianteReceptor) {
        this.idLike = idLike;
        this.fechaLike = fechaLike;
        this.estudianteEmisor = estudianteEmisor;
        this.estudianteReceptor = estudianteReceptor;
    }

    public Long getIdLike() {
        return idLike;
    }

    public void setIdLike(Long idLike) {
        this.idLike = idLike;
    }

    public LocalDateTime getFechaLike() {
        return fechaLike;
    }

    public void setFechaLike(LocalDateTime fechaLike) {
        this.fechaLike = fechaLike;
    }

    public Estudiante getEstudianteEmisor() {
        return estudianteEmisor;
    }

    public void setEstudianteEmisor(Estudiante estudianteEmisor) {
        this.estudianteEmisor = estudianteEmisor;
    }

    public Estudiante getEstudianteReceptor() {
        return estudianteReceptor;
    }

    public void setEstudianteReceptor(Estudiante estudianteReceptor) {
        this.estudianteReceptor = estudianteReceptor;
    }
}