package Main;

import Config.JpaUtil;
import Domain.*;
import Service.*;
import InterfaceService.*;
import java.util.List;

/**
 *
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */


public class BondingPruebas {

    public static void main(String[] args) {
        // 1. Instanciar los servicios necesarios
        ICarreraService carreraService = new CarreraService();
        IEstudianteService estudianteService = new EstudianteService();
        ILikeService likeService = new LikeService();
        IMatchService matchService = new MatchService(); // Necesitamos este para verificar

        System.out.println("--- Iniciando Prueba de Likes y Match Mutuo ---");

        Carrera carreraPrueba = null;
        Estudiante estudianteA = null;
        Estudiante estudianteB = null;

        try {
            // --- Preparacion: Asegurar que existe una Carrera ---
            String nombreCarrera = "Carrera Match Test"; // Usa un nombre especifico
            carreraPrueba = carreraService.buscarPorNombre(nombreCarrera);
            if (carreraPrueba == null) {
                Carrera tempCarrera = new Carrera();
                tempCarrera.setNombreCarrera(nombreCarrera);
                carreraPrueba = carreraService.crearCarrera(tempCarrera);
                System.out.println("Carrera de prueba creada (ID: " + carreraPrueba.getIdCarrera() + ")");
            } else {
                 System.out.println("Usando carrera existente (ID: " + carreraPrueba.getIdCarrera() + ")");
            }
             System.out.println("-------------------------");

            // --- Preparacion: Crear/Obtener Estudiante A ---
            String correoA = "estudiante.a.match@itson.edu.mx"; // Correo especifico
            String passA = "passA";
            estudianteA = estudianteService.autenticar(correoA, passA);
            if (estudianteA == null) {
                Estudiante tempA = new Estudiante();
                tempA.setNombreEstudiante("EstudianteA");
                tempA.setApellidoPaterno("MatchTest");
                tempA.setCorreoInstitucional(correoA);
                tempA.setContrasena(passA);
                tempA.setCarrera(carreraPrueba);
                estudianteA = estudianteService.crearEstudiante(tempA);
                 System.out.println("Estudiante A creado (ID: " + estudianteA.getIdEstudiante() + ")");
            } else {
                System.out.println("Usando Estudiante A existente (ID: " + estudianteA.getIdEstudiante() + ")");
            }

            // --- Preparacion: Crear/Obtener Estudiante B ---
             String correoB = "estudiante.b.match@itson.edu.mx"; // Correo especifico
             String passB = "passB";
            estudianteB = estudianteService.autenticar(correoB, passB);
            if (estudianteB == null) {
                Estudiante tempB = new Estudiante();
                tempB.setNombreEstudiante("EstudianteB");
                tempB.setApellidoPaterno("MatchTest");
                tempB.setCorreoInstitucional(correoB);
                tempB.setContrasena(passB);
                tempB.setCarrera(carreraPrueba);
                estudianteB = estudianteService.crearEstudiante(tempB);
                 System.out.println("Estudiante B creado (ID: " + estudianteB.getIdEstudiante() + ")");
            } else {
                 System.out.println("Usando Estudiante B existente (ID: " + estudianteB.getIdEstudiante() + ")");
            }
             System.out.println("-------------------------");


            // --- Prueba 1: Crear Like A -> B (si no existe) ---
            System.out.println("Intentando crear Like: A(" + estudianteA.getIdEstudiante() + ") -> B(" + estudianteB.getIdEstudiante() + ")");
            Like likeAB = likeService.verificarLikeExistente(estudianteA.getIdEstudiante(), estudianteB.getIdEstudiante());
            if (likeAB == null) {
                Like nuevoLikeAB = new Like();
                // Importante: Asignar los objetos completos, no solo IDs
                nuevoLikeAB.setEstudianteEmisor(estudianteA);
                nuevoLikeAB.setEstudianteReceptor(estudianteB);
                likeAB = likeService.crearLike(nuevoLikeAB);
                System.out.println(" Like A -> B creado (ID: " + likeAB.getIdLike() + ")");
            } else {
                System.out.println(" Like A -> B ya existe (ID: " + likeAB.getIdLike() + ")");
            }
             System.out.println("-------------------------");

            // --- Prueba 2: Crear Like B -> A (si no existe, esto deberia disparar la creacion del Match) ---
             System.out.println("Intentando crear Like: B(" + estudianteB.getIdEstudiante() + ") -> A(" + estudianteA.getIdEstudiante() + ")");
             Like likeBA = likeService.verificarLikeExistente(estudianteB.getIdEstudiante(), estudianteA.getIdEstudiante());
             if (likeBA == null) {
                 Like nuevoLikeBA = new Like();
                 // Importante: Asignar los objetos completos
                 nuevoLikeBA.setEstudianteEmisor(estudianteB);
                 nuevoLikeBA.setEstudianteReceptor(estudianteA);
                 likeBA = likeService.crearLike(nuevoLikeBA); // Llamada clave que dispara la logica del match
                 System.out.println(" Like B -> A creado (ID: " + likeBA.getIdLike() + ")");
                 System.out.println(" --> Se esperaba que esto creara un Match si no existia.");
             } else {
                 System.out.println(" Like B -> A ya existe (ID: " + likeBA.getIdLike() + ")");
                 System.out.println(" --> Si ambos likes ya existian, el Match tambien deberia existir.");
             }
              System.out.println("-------------------------");

            // --- Prueba 3: Verificar si el Match se creo ---
             System.out.println("Verificando si existe Match entre A(" + estudianteA.getIdEstudiante() + ") y B(" + estudianteB.getIdEstudiante() + ")");
             Match matchEncontrado = matchService.verificarMatchExistente(estudianteA.getIdEstudiante(), estudianteB.getIdEstudiante());

             if (matchEncontrado != null) {
                 System.out.println("¡EXITO! Match encontrado:");
                 System.out.println(" Match ID: " + matchEncontrado.getIdMatch());
                 System.out.println(" Fecha: " + matchEncontrado.getFechaMatch());
                 // --- Prueba 4: Obtener/Crear Chat para el Match ---
             Chat chatDelMatch = null;
             if (matchEncontrado != null) {
                 System.out.println("Intentando obtener/crear chat para el Match ID: " + matchEncontrado.getIdMatch());
                 // Instanciar servicios de Chat y Mensaje aqui si no lo hiciste al inicio
                 IChatService chatService = new ChatService();
                 IMensajeService mensajeService = new MensajeService();

                 chatDelMatch = matchService.obtenerOCrearChatDelMatch(matchEncontrado.getIdMatch());

                 if (chatDelMatch != null) {
                     System.out.println("¡EXITO! Chat obtenido/creado:");
                     System.out.println(" Chat ID: " + chatDelMatch.getIdChat());
                     System.out.println(" Fecha Creacion: " + chatDelMatch.getFechaCreacion());
                     System.out.println(" Asociado a Match ID: " + chatDelMatch.getMatch().getIdMatch());
                 } else {
                     System.err.println("!!! FALLO: No se pudo obtener/crear el Chat para el Match.");
                 }
                 System.out.println("-------------------------");

                 // --- Prueba 5: Enviar Mensajes en el Chat ---
                 if (chatDelMatch != null) {
                     System.out.println("Intentando enviar mensajes entre A y B en el Chat ID: " + chatDelMatch.getIdChat());

                     // Mensaje de A para B
                     Mensaje msgA = new Mensaje();
                     msgA.setChat(chatDelMatch); // Asocia al chat correcto
                     msgA.setEstudianteEmisor(estudianteA); // Asigna el objeto Estudiante
                     msgA.setContenido("Hola B! Soy A.");
                     Mensaje msgAEnviado = mensajeService.crearMensaje(msgA);
                     System.out.println(" Mensaje enviado por A (ID: " + msgAEnviado.getIdMensaje() + "): '" + msgAEnviado.getContenido() + "'");

                     // Mensaje de B para A
                     Mensaje msgB = new Mensaje();
                     msgB.setChat(chatDelMatch);
                     msgB.setEstudianteEmisor(estudianteB);
                     msgB.setContenido("Que tal A? Soy B.");
                     Mensaje msgBEnviado = mensajeService.crearMensaje(msgB);
                     System.out.println(" Mensaje enviado por B (ID: " + msgBEnviado.getIdMensaje() + "): '" + msgBEnviado.getContenido() + "'");
                     System.out.println("-------------------------");

                     // --- Prueba 6: Obtener Mensajes del Chat ---
                     System.out.println("Intentando obtener los mensajes del Chat ID: " + chatDelMatch.getIdChat());
                     // Usamos chatService para obtener mensajes
                     List<Mensaje> mensajes = chatService.obtenerMensajesDelChat(chatDelMatch.getIdChat(), 10); // Pide hasta 10 mensajes

                     if (mensajes != null && !mensajes.isEmpty()) {
                         System.out.println("¡EXITO! Mensajes encontrados (" + mensajes.size() + "):");
                         for (Mensaje msg : mensajes) {
                             System.out.println("  - ID: " + msg.getIdMensaje() +
                                                ", EmisorID: " + msg.getEstudianteEmisor().getIdEstudiante() +
                                                ", Fecha: " + msg.getFechaEnvio() +
                                                ", Contenido: '" + msg.getContenido() + "'");
                         }
                         // Verificacion basica
                         if (mensajes.size() >= 2) { // Deberia haber al menos los 2 que enviamos
                             System.out.println(" Verificacion: Se recuperaron al menos 2 mensajes.");
                         } else {
                             System.err.println("!!! Advertencia: Se esperaban al menos 2 mensajes.");
                         }
                     } else {
                         System.err.println("!!! FALLO: No se encontraron mensajes en el chat.");
                     }
                 } else {
                      System.out.println("Saltando pruebas de mensajes porque no se obtuvo/creo el chat.");
                 }

             } else {
                 System.out.println("Saltando pruebas de chat y mensajes porque no se encontro el Match.");
             }
             // El ultimo System.out.println("-------------------------"); ya estaba despues de la verificacion del match
                 // Opcional: Verificar que los estudiantes en el match son A y B
                 // List<Estudiante> participantes = matchService.obtenerEstudiantesDelMatch(matchEncontrado.getIdMatch());
                 // boolean aEncontrado = participantes.stream().anyMatch(e -> e.getIdEstudiante().equals(estudianteA.getIdEstudiante()));
                 // boolean bEncontrado = participantes.stream().anyMatch(e -> e.getIdEstudiante().equals(estudianteB.getIdEstudiante()));
                 // if (aEncontrado && bEncontrado) System.out.println(" Estudiantes correctos en el match."); else System.err.println("!!! Error: Estudiantes incorrectos en el match.");

             } else {
                 System.err.println("!!! FALLO: No se encontro el Match esperado entre A y B.");
             }
             System.out.println("-------------------------");
             
             

        } catch (Exception e) {
            System.err.println("!!! Ocurrio un error durante la prueba:");
            e.printStackTrace();
        } finally {
            // Cerrar la fabrica de EntityManager al final
            JpaUtil.getInstance().close();
            System.out.println("--- Prueba Finalizada ---");
        }
    }
}

//public class BondingPruebas {
//
//    public static void main(String[] args) {
//        // 1. Instanciar los servicios necesarios
//        ICarreraService carreraService = new CarreraService();
//        IEstudianteService estudianteService = new EstudianteService();
//        ILikeService likeService = new LikeService();
//        IMatchService matchService = new MatchService(); // Necesitamos este para verificar
//
//        System.out.println("--- Iniciando Prueba de Likes y Match Mutuo ---");
//
//        Carrera carreraPrueba = null;
//        Estudiante estudianteA = null;
//        Estudiante estudianteB = null;
//
//        try {
//            // --- Preparacion: Asegurar que existe una Carrera ---
//            String nombreCarrera = "Carrera Match Test";
//            carreraPrueba = carreraService.buscarPorNombre(nombreCarrera);
//            if (carreraPrueba == null) {
//                Carrera tempCarrera = new Carrera();
//                tempCarrera.setNombreCarrera(nombreCarrera);
//                carreraPrueba = carreraService.crearCarrera(tempCarrera);
//                System.out.println("Carrera de prueba creada (ID: " + carreraPrueba.getIdCarrera() + ")");
//            } else {
//                 System.out.println("Usando carrera existente (ID: " + carreraPrueba.getIdCarrera() + ")");
//            }
//             System.out.println("-------------------------");
//
//            // --- Preparacion: Crear/Obtener Estudiante A ---
//            String correoA = "estudiante.a.match@itson.edu.mx";
//            estudianteA = estudianteService.autenticar(correoA, "passA");
//            if (estudianteA == null) {
//                Estudiante tempA = new Estudiante();
//                tempA.setNombreEstudiante("EstudianteA");
//                tempA.setApellidoPaterno("Match");
//                tempA.setCorreoInstitucional(correoA);
//                tempA.setContrasena("passA");
//                tempA.setCarrera(carreraPrueba);
//                estudianteA = estudianteService.crearEstudiante(tempA);
//                 System.out.println("Estudiante A creado (ID: " + estudianteA.getIdEstudiante() + ")");
//            } else {
//                System.out.println("Usando Estudiante A existente (ID: " + estudianteA.getIdEstudiante() + ")");
//            }
//
//            // --- Preparacion: Crear/Obtener Estudiante B ---
//             String correoB = "estudiante.b.match@itson.edu.mx";
//            estudianteB = estudianteService.autenticar(correoB, "passB");
//            if (estudianteB == null) {
//                Estudiante tempB = new Estudiante();
//                tempB.setNombreEstudiante("EstudianteB");
//                tempB.setApellidoPaterno("Prueba");
//                tempB.setCorreoInstitucional(correoB);
//                tempB.setContrasena("passB");
//                tempB.setCarrera(carreraPrueba);
//                estudianteB = estudianteService.crearEstudiante(tempB);
//                 System.out.println("Estudiante B creado (ID: " + estudianteB.getIdEstudiante() + ")");
//            } else {
//                 System.out.println("Usando Estudiante B existente (ID: " + estudianteB.getIdEstudiante() + ")");
//            }
//             System.out.println("-------------------------");
//
//
//            // --- Prueba 1: Crear Like A -> B ---
//            System.out.println("Intentando crear Like: A(" + estudianteA.getIdEstudiante() + ") -> B(" + estudianteB.getIdEstudiante() + ")");
//            Like likeAB = likeService.verificarLikeExistente(estudianteA.getIdEstudiante(), estudianteB.getIdEstudiante());
//            if (likeAB == null) {
//                Like nuevoLikeAB = new Like();
//                nuevoLikeAB.setEstudianteEmisor(estudianteA);
//                nuevoLikeAB.setEstudianteReceptor(estudianteB);
//                likeAB = likeService.crearLike(nuevoLikeAB);
//                System.out.println(" Like A -> B creado (ID: " + likeAB.getIdLike() + ")");
//            } else {
//                System.out.println(" Like A -> B ya existe (ID: " + likeAB.getIdLike() + ")");
//            }
//             System.out.println("-------------------------");
//
//            // --- Prueba 2: Crear Like B -> A (Esto deberia disparar la creacion del Match) ---
//             System.out.println("Intentando crear Like: B(" + estudianteB.getIdEstudiante() + ") -> A(" + estudianteA.getIdEstudiante() + ")");
//             Like likeBA = likeService.verificarLikeExistente(estudianteB.getIdEstudiante(), estudianteA.getIdEstudiante());
//             if (likeBA == null) {
//                 Like nuevoLikeBA = new Like();
//                 nuevoLikeBA.setEstudianteEmisor(estudianteB);
//                 nuevoLikeBA.setEstudianteReceptor(estudianteA);
//                 likeBA = likeService.crearLike(nuevoLikeBA); // Llamada clave
//                 System.out.println(" Like B -> A creado (ID: " + likeBA.getIdLike() + ")");
//                 System.out.println(" --> Se esperaba que esto creara un Match.");
//             } else {
//                 System.out.println(" Like B -> A ya existe (ID: " + likeBA.getIdLike() + ")");
//             }
//              System.out.println("-------------------------");
//
//            // --- Prueba 3: Verificar si el Match se creo ---
//             System.out.println("Verificando si existe Match entre A(" + estudianteA.getIdEstudiante() + ") y B(" + estudianteB.getIdEstudiante() + ")");
//             Match matchEncontrado = matchService.verificarMatchExistente(estudianteA.getIdEstudiante(), estudianteB.getIdEstudiante());
//
//             if (matchEncontrado != null) {
//                 System.out.println("¡EXITO! Match encontrado:");
//                 System.out.println(" Match ID: " + matchEncontrado.getIdMatch());
//                 System.out.println(" Fecha: " + matchEncontrado.getFechaMatch());
//                 // Podrias anadir logica para verificar los IDs de los estudiantes en el match si quieres ser mas exhaustivo
//             } else {
//                 System.err.println("!!! FALLO: No se encontro el Match esperado entre A y B.");
//             }
//             System.out.println("-------------------------");
//
//        } catch (Exception e) {
//            System.err.println("!!! Ocurrio un error durante la prueba:");
//            e.printStackTrace();
//        } finally {
//            // Cerrar la fabrica de EntityManager al final
//            JpaUtil.getInstance().close();
//            System.out.println("--- Prueba Finalizada ---");
//        }
//    }
//}

//public class BondingPruebas {
//
//    public static void main(String[] args) {
//        // 1. Instanciar los servicios necesarios
//        ICarreraService carreraService = new CarreraService();
//        IEstudianteService estudianteService = new EstudianteService();
//
//        System.out.println("--- Iniciando Prueba ---");
//
//        Carrera carreraCreada = null;
//        Estudiante estudianteCreado = null;
//        Estudiante estudianteBuscado = null; // Variable para usarla en varias pruebas
//
//        try {
//            // --- Prueba 1: Crear una Carrera ---
//            System.out.println("Intentando crear una carrera...");
//            Carrera nuevaCarrera = new Carrera();
//            nuevaCarrera.setNombreCarrera("Ingenieria de Software Test"); // Nombre de prueba
//
//            carreraCreada = carreraService.buscarPorNombre(nuevaCarrera.getNombreCarrera());
//            if (carreraCreada == null) {
//                carreraCreada = carreraService.crearCarrera(nuevaCarrera);
//                System.out.println("Carrera Creada:");
//                System.out.println(" ID: " + carreraCreada.getIdCarrera());
//                System.out.println(" Nombre: " + carreraCreada.getNombreCarrera());
//            } else {
//                System.out.println("La carrera ya existe:");
//                 System.out.println(" ID: " + carreraCreada.getIdCarrera());
//                System.out.println(" Nombre: " + carreraCreada.getNombreCarrera());
//            }
//            System.out.println("-------------------------");
//
//            // --- Prueba 2: Crear un Estudiante asociado a la Carrera ---
//            System.out.println("Intentando crear un estudiante...");
//            Estudiante nuevoEstudiante = new Estudiante();
//            nuevoEstudiante.setNombreEstudiante("Juan");
//            nuevoEstudiante.setApellidoPaterno("Perez");
//            nuevoEstudiante.setApellidoMaterno("Lopez");
//            nuevoEstudiante.setCorreoInstitucional("juan.perez.test@itson.edu.mx");
//            nuevoEstudiante.setContrasena("secreto");
//            nuevoEstudiante.setCarrera(carreraCreada);
//
//            estudianteCreado = estudianteService.autenticar(nuevoEstudiante.getCorreoInstitucional(), nuevoEstudiante.getContrasena());
//             if (estudianteCreado == null) {
//                estudianteCreado = estudianteService.crearEstudiante(nuevoEstudiante);
//                System.out.println("Estudiante Creado:");
//                System.out.println(" ID: " + estudianteCreado.getIdEstudiante());
//                System.out.println(" Nombre: " + estudianteCreado.getNombreEstudiante());
//                System.out.println(" Correo: " + estudianteCreado.getCorreoInstitucional());
//                System.out.println(" Carrera ID: " + estudianteCreado.getCarrera().getIdCarrera());
//            } else {
//                 System.out.println("El estudiante ya existe:");
//                 System.out.println(" ID: " + estudianteCreado.getIdEstudiante());
//                 System.out.println(" Nombre: " + estudianteCreado.getNombreEstudiante());
//                 System.out.println(" Correo: " + estudianteCreado.getCorreoInstitucional());
//                 System.out.println(" Carrera ID: " + estudianteCreado.getCarrera().getIdCarrera());
//            }
//            System.out.println("-------------------------");
//
//             // --- Prueba 3: Buscar Estudiante por ID ---
//             // Solo proceder si se creo o encontro al estudiante
//             if (estudianteCreado != null) {
//                 System.out.println("Intentando buscar al estudiante por ID: " + estudianteCreado.getIdEstudiante());
//                 estudianteBuscado = estudianteService.buscarPorId(estudianteCreado.getIdEstudiante());
//                 if (estudianteBuscado != null) {
//                     System.out.println("Estudiante Encontrado:");
//                     System.out.println(" ID: " + estudianteBuscado.getIdEstudiante());
//                     System.out.println(" Nombre: " + estudianteBuscado.getNombreEstudiante());
//                     System.out.println(" Correo: " + estudianteBuscado.getCorreoInstitucional());
//                 } else {
//                     System.err.println("!!! Error: No se encontro el estudiante por ID despues de crearlo/encontrarlo.");
//                     // Si no se encuentra aqui, las siguientes pruebas fallaran, asi que lo marcamos como null
//                     estudianteBuscado = null;
//                 }
//             } else {
//                  System.out.println("Saltando pruebas de busqueda, actualizacion y eliminacion porque no se pudo crear/encontrar al estudiante inicial.");
//             }
//            System.out.println("-------------------------");
//
//            // --- Prueba 4: Actualizar Estudiante ---
//            if (estudianteBuscado != null) {
//                System.out.println("Intentando actualizar el apellido paterno del estudiante...");
//                String nuevoApellido = "PerezActualizado" + System.currentTimeMillis(); // Añadir timestamp para asegurar cambio
//                estudianteBuscado.setApellidoPaterno(nuevoApellido);
//                Estudiante estudianteActualizado = estudianteService.actualizarEstudiante(estudianteBuscado);
//                System.out.println("Estudiante Actualizado (respuesta del servicio):");
//                System.out.println(" ID: " + estudianteActualizado.getIdEstudiante());
//                System.out.println(" Nombre: " + estudianteActualizado.getNombreEstudiante());
//                System.out.println(" Apellido Paterno: " + estudianteActualizado.getApellidoPaterno());
//
//                // Volver a buscar para confirmar el cambio en la BD
//                Estudiante estudianteReBuscado = estudianteService.buscarPorId(estudianteCreado.getIdEstudiante());
//                 if (estudianteReBuscado != null && estudianteReBuscado.getApellidoPaterno().equals(nuevoApellido)) {
//                     System.out.println("Confirmacion: El cambio se persistio correctamente en la BD.");
//                 } else {
//                     System.err.println("!!! Error: El cambio no parece haberse guardado en la BD.");
//                 }
//            } else {
//                 System.out.println("Saltando prueba de actualizacion.");
//            }
//            System.out.println("-------------------------");
//
//            // --- Prueba 5: Eliminar Estudiante ---
//            // Usamos estudianteCreado.getIdEstudiante() por si estudianteBuscado fue modificado
//            if (estudianteCreado != null) {
//                System.out.println("Intentando eliminar al estudiante ID: " + estudianteCreado.getIdEstudiante());
//                boolean eliminado = estudianteService.eliminarEstudiante(estudianteCreado.getIdEstudiante());
//                if (eliminado) {
//                    System.out.println("Estudiante eliminado correctamente.");
//                    // Intentar buscarlo de nuevo para confirmar
//                    Estudiante estudiantePostEliminacion = estudianteService.buscarPorId(estudianteCreado.getIdEstudiante());
//                    if (estudiantePostEliminacion == null) {
//                        System.out.println("Confirmacion: El estudiante ya no se encuentra en la BD.");
//                    } else {
//                        System.err.println("!!! Error: El estudiante aun se encuentra despues de eliminar.");
//                    }
//                } else {
//                    System.err.println("!!! Error: El servicio reporto que no se pudo eliminar al estudiante (quiza ya fue eliminado?).");
//                }
//            } else {
//                 System.out.println("Saltando prueba de eliminacion.");
//            }
//            System.out.println("-------------------------");
//
//        } catch (Exception e) {
//            System.err.println("!!! Ocurrio un error durante la prueba:");
//            e.printStackTrace();
//        } finally {
//            // 3. Cerrar la fabrica de EntityManager al final
//            JpaUtil.getInstance().close();
//            System.out.println("--- Prueba Finalizada ---");
//        }
//    }
//}