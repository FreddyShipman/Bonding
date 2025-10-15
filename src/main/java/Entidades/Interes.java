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
public class Interes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idInteres;

    private String categoria;

    private String nombreInteres;

    public Interes() {
    }

    public Interes(Long idInteres, String categoria, String nombreInteres) {
        this.idInteres = idInteres;
        this.categoria = categoria;
        this.nombreInteres = nombreInteres;
    }

    public Long getIdInteres() {
        return idInteres;
    }

    public void setIdInteres(Long idInteres) {
        this.idInteres = idInteres;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNombreInteres() {
        return nombreInteres;
    }

    public void setNombreInteres(String nombreInteres) {
        this.nombreInteres = nombreInteres;
    }
}
