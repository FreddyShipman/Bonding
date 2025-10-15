package Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author Fred
 */
@Entity
public class Hobby implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHobby;

    private String nombreHobby;

    @ManyToMany(mappedBy = "hobbies")
    private Set<Estudiante> estudiantes;

    public Hobby() {
    }

    public Long getIdHobby() {
        return idHobby;
    }

    public void setIdHobby(Long idHobby) {
        this.idHobby = idHobby;
    }

    public String getNombreHobby() {
        return nombreHobby;
    }

    public void setNombreHobby(String nombreHobby) {
        this.nombreHobby = nombreHobby;
    }

    public Set<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(Set<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }
}