package Domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * @author Fred
 */

@Entity
public class Mensaje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMensaje;

    private String contenido;

    private LocalDateTime fechaEnvio;

    @ManyToOne
    @JoinColumn(name = "id_estudiante_emisor")
    private Estudiante estudianteEmisor;

    @ManyToOne
    @JoinColumn(name = "idChat")
    private Chat chat;

    public Mensaje() {
    }

    public Mensaje(Long idMensaje, String contenido, LocalDateTime fechaEnvio, Estudiante estudianteEmisor, Chat chat) {
        this.idMensaje = idMensaje;
        this.contenido = contenido;
        this.fechaEnvio = fechaEnvio;
        this.estudianteEmisor = estudianteEmisor;
        this.chat = chat;
    }

    public Long getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(Long idMensaje) {
        this.idMensaje = idMensaje;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Estudiante getEstudianteEmisor() {
        return estudianteEmisor;
    }

    public void setEstudianteEmisor(Estudiante estudianteEmisor) {
        this.estudianteEmisor = estudianteEmisor;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}