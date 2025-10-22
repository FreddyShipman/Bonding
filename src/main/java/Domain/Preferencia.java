package Domain;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

/**
 * @author Fred
 */
@Entity
public class Preferencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPreferencia;

    private String generoPreferido;
    private Integer edadMinima;
    private Integer edadMaxima;

    // Relacion: Uno a Uno con Estudiante
    @OneToOne
    @JoinColumn(name = "id_estudiante")
    private Estudiante estudiante;

    // Relacion: Muchos a Uno con Carrera
    @ManyToOne
    @JoinColumn(name = "id_carrera_preferida")
    private Carrera carreraPreferida;
    
    public Preferencia() {
    }

    public Preferencia(Long idPreferencia, String generoPreferido, Integer edadMinima, Integer edadMaxima, Estudiante estudiante, Carrera carreraPreferida) {
        this.idPreferencia = idPreferencia;
        this.generoPreferido = generoPreferido;
        this.edadMinima = edadMinima;
        this.edadMaxima = edadMaxima;
        this.estudiante = estudiante;
        this.carreraPreferida = carreraPreferida;
    }

    public Long getIdPreferencia() {
        return idPreferencia;
    }

    public void setIdPreferencia(Long idPreferencia) {
        this.idPreferencia = idPreferencia;
    }

    public String getGeneroPreferido() {
        return generoPreferido;
    }

    public void setGeneroPreferido(String generoPreferido) {
        this.generoPreferido = generoPreferido;
    }

    public Integer getEdadMinima() {
        return edadMinima;
    }

    public void setEdadMinima(Integer edadMinima) {
        this.edadMinima = edadMinima;
    }

    public Integer getEdadMaxima() {
        return edadMaxima;
    }

    public void setEdadMaxima(Integer edadMaxima) {
        this.edadMaxima = edadMaxima;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Carrera getCarreraPreferida() {
        return carreraPreferida;
    }

    public void setCarreraPreferida(Carrera carreraPreferida) {
        this.carreraPreferida = carreraPreferida;
    }
}
