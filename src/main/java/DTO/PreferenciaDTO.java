package DTO;

/**
 * Data Transfer Object para la entidad Preferencia.
 * Evita referencias circulares y expone solo los datos necesarios.
 *
 * @author Bonding Team
 */
public class PreferenciaDTO {

    private Long idPreferencia;
    private String generoPreferido;
    private Integer edadMinima;
    private Integer edadMaxima;
    private Long idEstudiante;
    private String nombreEstudiante;
    private Long idCarreraPreferida;
    private String nombreCarreraPreferida;

    public PreferenciaDTO() {
    }

    public PreferenciaDTO(Long idPreferencia, String generoPreferido, Integer edadMinima, Integer edadMaxima) {
        this.idPreferencia = idPreferencia;
        this.generoPreferido = generoPreferido;
        this.edadMinima = edadMinima;
        this.edadMaxima = edadMaxima;
    }

    public PreferenciaDTO(Long idPreferencia, String generoPreferido, Integer edadMinima,
                          Integer edadMaxima, Long idEstudiante, Long idCarreraPreferida) {
        this.idPreferencia = idPreferencia;
        this.generoPreferido = generoPreferido;
        this.edadMinima = edadMinima;
        this.edadMaxima = edadMaxima;
        this.idEstudiante = idEstudiante;
        this.idCarreraPreferida = idCarreraPreferida;
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

    public Long getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Long idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public Long getIdCarreraPreferida() {
        return idCarreraPreferida;
    }

    public void setIdCarreraPreferida(Long idCarreraPreferida) {
        this.idCarreraPreferida = idCarreraPreferida;
    }

    public String getNombreCarreraPreferida() {
        return nombreCarreraPreferida;
    }

    public void setNombreCarreraPreferida(String nombreCarreraPreferida) {
        this.nombreCarreraPreferida = nombreCarreraPreferida;
    }

    @Override
    public String toString() {
        return "PreferenciaDTO{" +
                "idPreferencia=" + idPreferencia +
                ", generoPreferido='" + generoPreferido + '\'' +
                ", edadMinima=" + edadMinima +
                ", edadMaxima=" + edadMaxima +
                ", idEstudiante=" + idEstudiante +
                ", nombreEstudiante='" + nombreEstudiante + '\'' +
                ", idCarreraPreferida=" + idCarreraPreferida +
                ", nombreCarreraPreferida='" + nombreCarreraPreferida + '\'' +
                '}';
    }
}
