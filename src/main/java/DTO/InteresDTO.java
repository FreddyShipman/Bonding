package DTO;

import java.util.List;

/**
 * Data Transfer Object para la entidad Interes.
 * Evita referencias circulares y expone solo los datos necesarios.
 *
 * @author Bonding Team
 */
public class InteresDTO {

    private Long idInteres;
    private String categoria;
    private String nombreInteres;
    private Integer cantidadEstudiantes;
    private List<Long> idEstudiantes;

    public InteresDTO() {
    }

    public InteresDTO(Long idInteres, String categoria, String nombreInteres) {
        this.idInteres = idInteres;
        this.categoria = categoria;
        this.nombreInteres = nombreInteres;
    }

    public InteresDTO(Long idInteres, String categoria, String nombreInteres, Integer cantidadEstudiantes) {
        this.idInteres = idInteres;
        this.categoria = categoria;
        this.nombreInteres = nombreInteres;
        this.cantidadEstudiantes = cantidadEstudiantes;
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

    public Integer getCantidadEstudiantes() {
        return cantidadEstudiantes;
    }

    public void setCantidadEstudiantes(Integer cantidadEstudiantes) {
        this.cantidadEstudiantes = cantidadEstudiantes;
    }

    public List<Long> getIdEstudiantes() {
        return idEstudiantes;
    }

    public void setIdEstudiantes(List<Long> idEstudiantes) {
        this.idEstudiantes = idEstudiantes;
        if (idEstudiantes != null) {
            this.cantidadEstudiantes = idEstudiantes.size();
        }
    }

    @Override
    public String toString() {
        return "InteresDTO{" +
                "idInteres=" + idInteres +
                ", categoria='" + categoria + '\'' +
                ", nombreInteres='" + nombreInteres + '\'' +
                ", cantidadEstudiantes=" + cantidadEstudiantes +
                '}';
    }
}
