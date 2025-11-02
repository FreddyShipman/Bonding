package Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */
@Entity
public class Carrera implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarrera;
    
    private String nombreCarrera;

    @OneToMany(mappedBy = "carrera")
    private Set<Estudiante> estudiantes;

    public Carrera() {
    }

    public Long getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(Long idCarrera) {
        this.idCarrera = idCarrera;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public Set<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(Set<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }
    
    /**
     * Este método es usado por el JComboBox para mostrar el nombre
     * en lugar de 'Domain.Carrera@...'
     */
    @Override
    public String toString() {
        return this.nombreCarrera;
    }
}