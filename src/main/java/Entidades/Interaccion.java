package Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 *
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */
@Entity
public class Interaccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInteraccion;

    private LocalDate fechaInteraccion;
    private String tipoInteraccion;

    @ManyToMany(mappedBy = "interacciones")
    private Set<Estudiante> estudiantes;

    public Interaccion() {
    }

    public Long getIdInteraccion() {
        return idInteraccion;
    }

    public void setIdInteraccion(Long idInteraccion) {
        this.idInteraccion = idInteraccion;
    }

    public LocalDate getFechaInteraccion() {
        return fechaInteraccion;
    }

    public void setFechaInteraccion(LocalDate fechaInteraccion) {
        this.fechaInteraccion = fechaInteraccion;
    }

    public String getTipoInteraccion() {
        return tipoInteraccion;
    }

    public void setTipoInteraccion(String tipoInteraccion) {
        this.tipoInteraccion = tipoInteraccion;
    }

    public Set<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(Set<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }
}