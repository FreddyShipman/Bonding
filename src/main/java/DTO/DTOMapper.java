package DTO;

import Domain.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase utilitaria para mapear entre entidades del dominio y DTOs.
 * Evita referencias circulares y convierte objetos complejos en estructuras simples.
 *
 * @author Bonding Team
 */
public class DTOMapper {

    /**
     * Convierte una entidad Match a MatchDTO.
     */
    public static MatchDTO toMatchDTO(Match match) {
        if (match == null) {
            return null;
        }

        MatchDTO dto = new MatchDTO();
        dto.setIdMatch(match.getIdMatch());
        dto.setFechaMatch(match.getFechaMatch());

        // Convertir estudiantes a lista de IDs
        if (match.getEstudiantes() != null) {
            List<Long> idEstudiantes = match.getEstudiantes().stream()
                    .map(Estudiante::getIdEstudiante)
                    .collect(Collectors.toList());
            dto.setIdEstudiantes(idEstudiantes);
        }

        // Agregar ID del chat si existe
        if (match.getChat() != null) {
            dto.setIdChat(match.getChat().getIdChat());
        }

        return dto;
    }

    /**
     * Convierte una lista de Match a lista de MatchDTO.
     */
    public static List<MatchDTO> toMatchDTOList(List<Match> matches) {
        if (matches == null) {
            return new ArrayList<>();
        }
        return matches.stream()
                .map(DTOMapper::toMatchDTO)
                .collect(Collectors.toList());
    }


    /**
     * Convierte una entidad Chat a ChatDTO.
     */
    public static ChatDTO toChatDTO(Chat chat) {
        if (chat == null) {
            return null;
        }

        ChatDTO dto = new ChatDTO();
        dto.setIdChat(chat.getIdChat());
        dto.setFechaCreacion(chat.getFechaCreacion());

        // Agregar ID del match si existe
        if (chat.getMatch() != null) {
            dto.setIdMatch(chat.getMatch().getIdMatch());
        }

        // Convertir mensajes si existen
        if (chat.getMensajes() != null) {
            dto.setCantidadMensajes(chat.getMensajes().size());
            // No incluimos todos los mensajes por defecto para evitar sobrecarga
            // Se pueden cargar bajo demanda
        }

        return dto;
    }

    /**
     * Convierte una entidad Chat a ChatDTO con mensajes incluidos.
     */
    public static ChatDTO toChatDTOWithMessages(Chat chat) {
        if (chat == null) {
            return null;
        }

        ChatDTO dto = toChatDTO(chat);

        // Incluir mensajes completos
        if (chat.getMensajes() != null) {
            List<MensajeDTO> mensajesDTO = chat.getMensajes().stream()
                    .map(DTOMapper::toMensajeDTO)
                    .collect(Collectors.toList());
            dto.setMensajes(mensajesDTO);
        }

        return dto;
    }

    /**
     * Convierte una lista de Chat a lista de ChatDTO.
     */
    public static List<ChatDTO> toChatDTOList(List<Chat> chats) {
        if (chats == null) {
            return new ArrayList<>();
        }
        return chats.stream()
                .map(DTOMapper::toChatDTO)
                .collect(Collectors.toList());
    }


    /**
     * Convierte una entidad Mensaje a MensajeDTO.
     */
    public static MensajeDTO toMensajeDTO(Mensaje mensaje) {
        if (mensaje == null) {
            return null;
        }

        MensajeDTO dto = new MensajeDTO();
        dto.setIdMensaje(mensaje.getIdMensaje());
        dto.setContenido(mensaje.getContenido());
        dto.setFechaEnvio(mensaje.getFechaEnvio());
        if (mensaje.getEstudianteEmisor() != null) {
            dto.setIdEstudianteEmisor(mensaje.getEstudianteEmisor().getIdEstudiante());
            dto.setNombreEmisor(mensaje.getEstudianteEmisor().getNombreEstudiante());
        } else {
            dto.setIdEstudianteEmisor(null);
            dto.setNombreEmisor("Desconocido");
        }

        if (mensaje.getChat() != null) {
            dto.setIdChat(mensaje.getChat().getIdChat());
        }

        return dto;
    }

    /**
     * Convierte una lista de Mensaje a lista de MensajeDTO.
     */
    public static List<MensajeDTO> toMensajeDTOList(List<Mensaje> mensajes) {
        if (mensajes == null) {
            return new ArrayList<>();
        }
        return mensajes.stream()
                .map(DTOMapper::toMensajeDTO)
                .collect(Collectors.toList());
    }


    /**
     * Convierte una entidad Interes a InteresDTO.
     */
    public static InteresDTO toInteresDTO(Interes interes) {
        if (interes == null) {
            return null;
        }

        InteresDTO dto = new InteresDTO();
        dto.setIdInteres(interes.getIdInteres());
        dto.setCategoria(interes.getCategoria());
        dto.setNombreInteres(interes.getNombreInteres());

        if (interes.getEstudiantes() != null) {
            dto.setCantidadEstudiantes(interes.getEstudiantes().size());
        }

        return dto;
    }

    /**
     * Convierte una entidad Interes a InteresDTO con lista de IDs de estudiantes.
     */
    public static InteresDTO toInteresDTOWithStudents(Interes interes) {
        if (interes == null) {
            return null;
        }

        InteresDTO dto = toInteresDTO(interes);

        if (interes.getEstudiantes() != null) {
            List<Long> idEstudiantes = interes.getEstudiantes().stream()
                    .map(Estudiante::getIdEstudiante)
                    .collect(Collectors.toList());
            dto.setIdEstudiantes(idEstudiantes);
        }

        return dto;
    }

    /**
     * Convierte una lista de Interes a lista de InteresDTO.
     */
    public static List<InteresDTO> toInteresDTOList(List<Interes> intereses) {
        if (intereses == null) {
            return new ArrayList<>();
        }
        return intereses.stream()
                .map(DTOMapper::toInteresDTO)
                .collect(Collectors.toList());
    }


    /**
     * Convierte una entidad Preferencia a PreferenciaDTO.
     */
    public static PreferenciaDTO toPreferenciaDTO(Preferencia preferencia) {
        if (preferencia == null) {
            return null;
        }

        PreferenciaDTO dto = new PreferenciaDTO();
        dto.setIdPreferencia(preferencia.getIdPreferencia());
        dto.setGeneroPreferido(preferencia.getGeneroPreferido());
        dto.setEdadMinima(preferencia.getEdadMinima());
        dto.setEdadMaxima(preferencia.getEdadMaxima());

        if (preferencia.getEstudiante() != null) {
            dto.setIdEstudiante(preferencia.getEstudiante().getIdEstudiante());
            dto.setNombreEstudiante(preferencia.getEstudiante().getNombreEstudiante());
        }

        if (preferencia.getCarreraPreferida() != null) {
            dto.setIdCarreraPreferida(preferencia.getCarreraPreferida().getIdCarrera());
            dto.setNombreCarreraPreferida(preferencia.getCarreraPreferida().getNombreCarrera());
        }

        return dto;
    }

    /**
     * Convierte una lista de Preferencia a lista de PreferenciaDTO.
     */
    public static List<PreferenciaDTO> toPreferenciaDTOList(List<Preferencia> preferencias) {
        if (preferencias == null) {
            return new ArrayList<>();
        }
        return preferencias.stream()
                .map(DTOMapper::toPreferenciaDTO)
                .collect(Collectors.toList());
    }


    /**
     * Convierte un MensajeDTO a entidad Mensaje (sin relaciones).
     * Las relaciones deben ser configuradas manualmente.
     */
    public static Mensaje toMensajeEntity(MensajeDTO dto) {
        if (dto == null) {
            return null;
        }

        Mensaje mensaje = new Mensaje();
        mensaje.setIdMensaje(dto.getIdMensaje());
        mensaje.setContenido(dto.getContenido());
        mensaje.setFechaEnvio(dto.getFechaEnvio());
        return mensaje;
    }

    /**
     * Convierte un InteresDTO a entidad Interes (sin relaciones).
     */
    public static Interes toInteresEntity(InteresDTO dto) {
        if (dto == null) {
            return null;
        }

        Interes interes = new Interes();
        interes.setIdInteres(dto.getIdInteres());
        interes.setCategoria(dto.getCategoria());
        interes.setNombreInteres(dto.getNombreInteres());

        return interes;
    }
}
