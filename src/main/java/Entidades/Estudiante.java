package Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author Fred
 */
@Entity
public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstudiante;

     private String nombreEstudiante;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correoInstitucional;
    private String contrasena;
    private String fotoPerfil;

    // Relacion: Uno a Muchos con Carrera
    @ManyToOne
    @JoinColumn(name = "id_carrera")
    private Carrera carrera;

    // Relacion: Muchos a Muchos con Hobby
    @ManyToMany
    @JoinTable(
        name = "estudiante_hobby",
        joinColumns = @JoinColumn(name = "id_estudiante"),
        inverseJoinColumns = @JoinColumn(name = "id_hobby")
    )
    private Set<Hobby> hobbies;

    // Relacion: Muchos a Muchos con Interaccion
    @ManyToMany
    @JoinTable(
        name = "estudiante_interaccion",
        joinColumns = @JoinColumn(name = "id_estudiante"),
        inverseJoinColumns = @JoinColumn(name = "id_interaccion")
    )
    private Set<Interaccion> interacciones;
    
    // Relacion: Uno a Muchos para Likes DADOS
    @OneToMany(mappedBy = "estudianteEmisor")
    private Set<Like> likesDados;

    // Relacion: Uno a Muchos para Likes RECIBIDOS
    @OneToMany(mappedBy = "estudianteReceptor")
    private Set<Like> likesRecibidos;

    public Estudiante() {
    }

    public Estudiante(Long idEstudiante, String nombreEstudiante, String apellidoPaterno, String apellidoMaterno, String correoInstitucional, String contrasena, String fotoPerfil, Carrera carrera, Set<Hobby> hobbies, Set<Interaccion> interacciones, Set<Like> likesDados, Set<Like> likesRecibidos) {
        this.idEstudiante = idEstudiante;
        this.nombreEstudiante = nombreEstudiante;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correoInstitucional = correoInstitucional;
        this.contrasena = contrasena;
        this.fotoPerfil = fotoPerfil;
        this.carrera = carrera;
        this.hobbies = hobbies;
        this.interacciones = interacciones;
        this.likesDados = likesDados;
        this.likesRecibidos = likesRecibidos;
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

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public Set<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(Set<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    public Set<Interaccion> getInteracciones() {
        return interacciones;
    }

    public void setInteracciones(Set<Interaccion> interacciones) {
        this.interacciones = interacciones;
    }

    public Set<Like> getLikesDados() {
        return likesDados;
    }

    public void setLikesDados(Set<Like> likesDados) {
        this.likesDados = likesDados;
    }

    public Set<Like> getLikesRecibidos() {
        return likesRecibidos;
    }

    public void setLikesRecibidos(Set<Like> likesRecibidos) {
        this.likesRecibidos = likesRecibidos;
    }
    
    
}