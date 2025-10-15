package Entidades;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * @author Fred
 */
@Entity
public class Preferencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPreferencia;

    private String generoPreferido;

    private Long idCarreraPreferida;

    private Integer edadMinima;

    private Integer edadMaxima;

    private Long idEstudiante;

    public Preferencia() {
    }

    public Preferencia(Long idPreferencia, String generoPreferido, Long idCarreraPreferida, Integer edadMinima, Integer edadMaxima, Long idEstudiante) {
        this.idPreferencia = idPreferencia;
        this.generoPreferido = generoPreferido;
        this.idCarreraPreferida = idCarreraPreferida;
        this.edadMinima = edadMinima;
        this.edadMaxima = edadMaxima;
        this.idEstudiante = idEstudiante;
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

    public Long getIdCarreraPreferida() {
        return idCarreraPreferida;
    }

    public void setIdCarreraPreferida(Long idCarreraPreferida) {
        this.idCarreraPreferida = idCarreraPreferida;
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

    public Long getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Long idEstudiante) {
        this.idEstudiante = idEstudiante;
    }
}
