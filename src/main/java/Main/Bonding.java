package Main;

import Config.JpaUtil;
import Domain.*;
import Service.*;
import InterfaceService.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 *
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */
public class Bonding{


    public static void main(String[] args) {
        // Instantiate all necessary services
        ICarreraService carreraService = new CarreraService();
        IEstudianteService estudianteService = new EstudianteService();
        ILikeService likeService = new LikeService();
        IMatchService matchService = new MatchService(); // Needed indirectly by LikeService
        IChatService chatService = new ChatService();     // Optional, for chat/messages
        IMensajeService mensajeService = new MensajeService(); // Optional, for chat/messages

        Random random = new Random();

        try {
            System.out.println("Iniciando carga de datos...");

            // --- 1. Crear Carreras (Prerequisite) ---
            System.out.println("Creando carreras...");
            List<Carrera> carreras = new ArrayList<>();
            // Create a few sample careers if they don't exist
            String[] nombresCarrera = {"Software", "Civil", "Industrial", "Mecatronica", "Biomedica"};
            for (String nombre : nombresCarrera) {
                Carrera c = null;
                try {
                    c = carreraService.buscarPorNombre(nombre);
                    if (c == null) {
                        c = new Carrera();
                        c.setNombreCarrera(nombre);
                        c = carreraService.crearCarrera(c);
                        System.out.println(" Carrera creada: " + c.getNombreCarrera());
                    } else {
                         System.out.println(" Carrera ya existe: " + c.getNombreCarrera());
                    }
                    carreras.add(c);
                } catch (Exception e) {
                     System.err.println("Error creando/buscando carrera " + nombre + ": " + e.getMessage());
                     // If a career is essential and fails, maybe stop?
                }
            }
             if (carreras.isEmpty()) {
                 System.err.println("No se pudieron crear/cargar carreras. Abortando.");
                 return;
             }


            // --- 2. Crear 50 Estudiantes ---
            System.out.println("Creando 50 estudiantes...");
            List<Estudiante> estudiantes = new ArrayList<>();
            for (int i = 1; i <= 50; i++) {
                Estudiante est = new Estudiante();
                est.setNombreEstudiante("Estudiante " + i);
                est.setApellidoPaterno("ApellidoP" + i);
                est.setApellidoMaterno("ApellidoM" + i);
                est.setCorreoInstitucional("estudiante" + i + "@itson.edu.mx");
                est.setContrasena("pass" + i); // Use secure passwords in a real app!
                // Assign a random career from the list
                est.setCarrera(carreras.get(random.nextInt(carreras.size())));
                try {
                    Estudiante creado = estudianteService.crearEstudiante(est);
                    estudiantes.add(creado);
                    System.out.println(" Estudiante creado: " + creado.getCorreoInstitucional());
                } catch (Exception e) {
                    // Check if it failed because it already exists
                    if (e.getMessage().contains("ya esta registrado")) {
                         System.out.println(" Estudiante ya existe: estudiante" + i + "@itson.edu.mx");
                         // Try to fetch the existing one to add to the list for later use
                         try {
                              Estudiante existente = estudianteService.autenticar("estudiante" + i + "@itson.edu.mx", "pass" + i);
                              if(existente != null) estudiantes.add(existente);
                         } catch (Exception fetchErr) {
                              System.err.println(" No se pudo recuperar estudiante existente: estudiante" + i + "@itson.edu.mx");
                         }
                    } else {
                        System.err.println(" Error creando estudiante " + i + ": " + e.getMessage());
                    }
                }
            }
             if (estudiantes.size() < 2) { // Need at least 2 students for likes/matches
                 System.err.println("No hay suficientes estudiantes para continuar. Abortando.");
                 return;
             }
             System.out.println("Total estudiantes cargados/creados: " + estudiantes.size());


            // --- 3. Crear 100 Likes (Simulando) ---
            System.out.println("Creando 100 likes (esto podria generar matches)...");
            int likesCreados = 0;
            int intentosLike = 0;
            while (likesCreados < 100 && intentosLike < 500) { // Limit attempts to avoid infinite loop
                 intentosLike++;
                // Pick two different random students
                Estudiante emisor = estudiantes.get(random.nextInt(estudiantes.size()));
                Estudiante receptor = estudiantes.get(random.nextInt(estudiantes.size()));

                if (emisor.getIdEstudiante().equals(receptor.getIdEstudiante())) {
                    continue; // Skip liking oneself
                }

                Like like = new Like();
                like.setEstudianteEmisor(emisor);
                like.setEstudianteReceptor(receptor);

                try {
                    likeService.crearLike(like); // This service handles match creation logic
                    likesCreados++;
                    System.out.println(" Like creado: " + emisor.getIdEstudiante() + " -> " + receptor.getIdEstudiante());
                } catch (Exception e) {
                     // It's normal for some likes to fail if they already exist
                     if (!e.getMessage().contains("ya existe")) {
                         System.err.println(" Error creando like: " + e.getMessage());
                     }
                }
            }
             System.out.println("Total likes creados en esta ejecucion: " + likesCreados);


            // --- 4. Verificar Matches (Approx 20 expected, but depends on randomness) ---
            // We don't explicitly create matches here, LikeService does it.
            // Let's just count them.
            System.out.println("Verificando matches generados...");
            List<Match> todosLosMatches = new ArrayList<>();
             // We need a way to list all matches, let's assume MatchService has a method
             // If not, you'd need to add 'List<Match> listarMatches(int limit)' to IMatchService/MatchService
             // For now, let's just query one student's matches as an example
             if (!estudiantes.isEmpty()) {
                 try {
                     // Get matches for the first student
                      List<Match> matchesEstudiante1 = matchService.obtenerMatchesPorEstudiante(estudiantes.get(0).getIdEstudiante(), 100);
                      System.out.println(" Matches encontrados para estudiante " + estudiantes.get(0).getIdEstudiante() + ": " + matchesEstudiante1.size());
                      todosLosMatches.addAll(matchesEstudiante1); // Add them to a general list for potential chat creation
                 } catch (Exception e) {
                      System.err.println(" Error obteniendo matches: " + e.getMessage());
                 }
             }
             // To get exactly 20 matches is hard with random likes.
             // The requirement is likely just to have *around* 20.


            // --- 5. Crear Chat y Mensajes (Opcional) ---
            System.out.println("Creando chats y mensajes para matches existentes (opcional)...");
            if (!todosLosMatches.isEmpty()) {
                 // Create a chat and a few messages for the first match found
                 Match primerMatch = todosLosMatches.get(0);
                 try {
                      System.out.println(" Obteniendo/Creando chat para match ID: " + primerMatch.getIdMatch());
                      Chat chat = matchService.obtenerOCrearChatDelMatch(primerMatch.getIdMatch());
                      System.out.println(" Chat ID: " + chat.getIdChat());

                      // Get the students involved in the match to send messages
                      List<Estudiante> participantes = matchService.obtenerEstudiantesDelMatch(primerMatch.getIdMatch());
                      if (participantes.size() == 2) {
                           Estudiante est1 = participantes.get(0);
                           Estudiante est2 = participantes.get(1);

                           // Send a couple of messages
                           Mensaje msg1 = new Mensaje();
                           msg1.setChat(chat);
                           msg1.setEstudianteEmisor(est1); // Use the full object
                           msg1.setContenido("Hola!");
                           mensajeService.crearMensaje(msg1);
                           System.out.println("  Mensaje enviado por " + est1.getIdEstudiante());

                           Mensaje msg2 = new Mensaje();
                           msg2.setChat(chat);
                           msg2.setEstudianteEmisor(est2); // Use the full object
                           msg2.setContenido("Que tal?");
                           mensajeService.crearMensaje(msg2);
                            System.out.println("  Mensaje enviado por " + est2.getIdEstudiante());
                      }

                 } catch (Exception e) {
                      System.err.println(" Error creando chat/mensajes para match " + primerMatch.getIdMatch() + ": " + e.getMessage());
                 }
            } else {
                 System.out.println(" No se encontraron matches para crear chats/mensajes.");
            }


            System.out.println("Carga de datos finalizada.");

        } catch (Exception e) {
            System.err.println("Error general durante la carga de datos:");
            e.printStackTrace();
        } finally {
            // Shutdown EntityManagerFactory when done
            JpaUtil.getInstance().close(); // Use the close method from YOUR JpaUtil
            System.out.println("EntityManagerFactory cerrado.");
        }
    }
}
