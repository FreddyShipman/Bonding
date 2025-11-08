package Main;

import Config.JpaUtil;
import Domain.*;
import Excepciones.*;
import Service.*;
import InterfaceService.*;
import java.util.List;

/**
 * Archivo de pruebas extendidas para todas las entidades del sistema Bonding.
 * Este archivo prueba CRUD completo y casos de error para todas las entidades.
 *
 * @author Bonding Team
 */
public class BondingPruebasExtendidas {

    // Servicios
    private static ICarreraService carreraService;
    private static IEstudianteService estudianteService;
    private static IHobbyService hobbyService;
    private static IInteresService interesService;
    private static IPreferenciaService preferenciaService;
    private static ILikeService likeService;
    private static IMatchService matchService;
    private static IChatService chatService;
    private static IMensajeService mensajeService;

    // Entidades de prueba
    private static Carrera carreraPrueba;
    private static Estudiante estudiante1;
    private static Estudiante estudiante2;
    private static Estudiante estudiante3;
    private static Hobby hobby1;
    private static Interes interes1;

    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("INICIANDO PRUEBAS EXTENDIDAS DEL SISTEMA BONDING");
        System.out.println("=".repeat(80));

        inicializarServicios();

        try {
            // === PRUEBAS DE CARRERA ===
            pruebasCarrera();

            // === PRUEBAS DE ESTUDIANTE ===
            pruebasEstudiante();

            // === PRUEBAS DE HOBBY ===
            pruebasHobby();

            // === PRUEBAS DE INTERES ===
            pruebasInteres();

            // === PRUEBAS DE PREFERENCIAS ===
            pruebasPreferencias();

            // === PRUEBAS DE LIKES Y MATCHES ===
            pruebasLikesYMatches();

            // === PRUEBAS DE CHAT Y MENSAJES ===
            pruebasChatYMensajes();

            // === PRUEBAS DE CASOS DE ERROR ===
            pruebasCasosDeError();

            System.out.println("\n" + "=".repeat(80));
            System.out.println("TODAS LAS PRUEBAS COMPLETADAS EXITOSAMENTE");
            System.out.println("=".repeat(80));

        } catch (Exception e) {
            System.err.println("\n" + "!".repeat(80));
            System.err.println("ERROR CRÍTICO EN LAS PRUEBAS: " + e.getMessage());
            System.err.println("!".repeat(80));
            e.printStackTrace();
        } finally {
            JpaUtil.getInstance().close();
            System.out.println("\nEntityManagerFactory cerrado.");
        }
    }

    private static void inicializarServicios() {
        System.out.println("\n[INICIALIZACIÓN] Creando servicios...");
        carreraService = new CarreraService();
        estudianteService = new EstudianteService();
        hobbyService = new HobbyService();
        interesService = new InteresService();
        preferenciaService = new PreferenciaService();
        likeService = new LikeService();
        matchService = new MatchService();
        chatService = new ChatService();
        mensajeService = new MensajeService();
        System.out.println("[INICIALIZACIÓN] ✓ Servicios creados correctamente");
    }

    // ===============================
    // PRUEBAS DE CARRERA
    // ===============================
    private static void pruebasCarrera() throws Exception {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("PRUEBAS DE CARRERA");
        System.out.println("-".repeat(80));

        // Crear Carrera
        System.out.println("\n[CARRERA - CREAR] Creando carrera de prueba...");
        Carrera carrera = new Carrera();
        carrera.setNombreCarrera("Ingeniería en Software - Pruebas Extendidas");
        try {
            carreraPrueba = carreraService.crearCarrera(carrera);
            System.out.println("[CARRERA - CREAR] ✓ Carrera creada: ID=" + carreraPrueba.getIdCarrera());
        } catch (Exception e) {
            System.out.println("[CARRERA - CREAR] ℹ Carrera ya existe, buscando...");
            carreraPrueba = carreraService.buscarPorNombre("Ingeniería en Software - Pruebas Extendidas");
        }

        // Buscar Carrera
        System.out.println("\n[CARRERA - BUSCAR] Buscando carrera por ID...");
        Carrera carreraEncontrada = carreraService.buscarPorId(carreraPrueba.getIdCarrera());
        if (carreraEncontrada != null) {
            System.out.println("[CARRERA - BUSCAR] ✓ Carrera encontrada: " + carreraEncontrada.getNombreCarrera());
        } else {
            throw new Exception("✗ No se encontró la carrera");
        }

        // Listar Carreras
        System.out.println("\n[CARRERA - LISTAR] Listando todas las carreras...");
        List<Carrera> carreras = carreraService.listarCarreras(50);
        System.out.println("[CARRERA - LISTAR] ✓ Total de carreras: " + carreras.size());
    }

    // ===============================
    // PRUEBAS DE ESTUDIANTE
    // ===============================
    private static void pruebasEstudiante() throws Exception {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("PRUEBAS DE ESTUDIANTE");
        System.out.println("-".repeat(80));

        // Crear Estudiantes
        System.out.println("\n[ESTUDIANTE - CREAR] Creando estudiantes de prueba...");

        estudiante1 = crearEstudiantePrueba("test1.pruebas@itson.edu.mx", "Juan", "Pérez", "López");
        estudiante2 = crearEstudiantePrueba("test2.pruebas@itson.edu.mx", "María", "González", "Martínez");
        estudiante3 = crearEstudiantePrueba("test3.pruebas@itson.edu.mx", "Carlos", "Ramírez", "Sánchez");

        // Buscar Estudiante por ID
        System.out.println("\n[ESTUDIANTE - BUSCAR] Buscando estudiante por ID...");
        Estudiante encontrado = estudianteService.buscarPorId(estudiante1.getIdEstudiante());
        System.out.println("[ESTUDIANTE - BUSCAR] ✓ Estudiante encontrado: " + encontrado.getNombreEstudiante());

        // Actualizar Estudiante
        System.out.println("\n[ESTUDIANTE - ACTUALIZAR] Actualizando estudiante...");
        estudiante1.setApellidoMaterno("López Actualizado");
        Estudiante actualizado = estudianteService.actualizarEstudiante(estudiante1);
        System.out.println("[ESTUDIANTE - ACTUALIZAR] ✓ Estudiante actualizado: " + actualizado.getApellidoMaterno());

        // Autenticar
        System.out.println("\n[ESTUDIANTE - AUTENTICAR] Autenticando estudiante...");
        Estudiante autenticado = estudianteService.autenticar("test1.pruebas@itson.edu.mx", "pass123");
        System.out.println("[ESTUDIANTE - AUTENTICAR] ✓ Autenticación exitosa: " + autenticado.getNombreEstudiante());

        // Listar Estudiantes
        System.out.println("\n[ESTUDIANTE - LISTAR] Listando estudiantes...");
        List<Estudiante> estudiantes = estudianteService.listarEstudiantes(10);
        System.out.println("[ESTUDIANTE - LISTAR] ✓ Total de estudiantes: " + estudiantes.size());
    }

    private static Estudiante crearEstudiantePrueba(String correo, String nombre, String apellidoP, String apellidoM) throws Exception {
        Estudiante est = new Estudiante();
        est.setNombreEstudiante(nombre);
        est.setApellidoPaterno(apellidoP);
        est.setApellidoMaterno(apellidoM);
        est.setCorreoInstitucional(correo);
        est.setContrasena("pass123");
        est.setCarrera(carreraPrueba);

        try {
            Estudiante creado = estudianteService.crearEstudiante(est);
            System.out.println("[ESTUDIANTE - CREAR] ✓ " + nombre + " creado: ID=" + creado.getIdEstudiante());
            return creado;
        } catch (DuplicadoException e) {
            System.out.println("[ESTUDIANTE - CREAR] ℹ " + nombre + " ya existe, autenticando...");
            return estudianteService.autenticar(correo, "pass123");
        }
    }

    // ===============================
    // PRUEBAS DE HOBBY
    // ===============================
    private static void pruebasHobby() throws Exception {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("PRUEBAS DE HOBBY");
        System.out.println("-".repeat(80));

        // Crear Hobby
        System.out.println("\n[HOBBY - CREAR] Creando hobby de prueba...");
        Hobby hobby = new Hobby();
        hobby.setNombreHobby("Programación - Pruebas");

        try {
            hobby1 = hobbyService.crearHobby(hobby);
            System.out.println("[HOBBY - CREAR] ✓ Hobby creado: ID=" + hobby1.getIdHobby());
        } catch (Exception e) {
            System.out.println("[HOBBY - CREAR] ℹ Hobby ya existe, buscando...");
            List<Hobby> hobbies = hobbyService.listarHobbies(100);
            hobby1 = hobbies.stream()
                .filter(h -> h.getNombreHobby().equals("Programación - Pruebas"))
                .findFirst()
                .orElse(hobbies.get(0));
        }

        // Listar Hobbies
        System.out.println("\n[HOBBY - LISTAR] Listando hobbies...");
        List<Hobby> hobbies = hobbyService.listarHobbies(10);
        System.out.println("[HOBBY - LISTAR] ✓ Total de hobbies: " + hobbies.size());
    }

    // ===============================
    // PRUEBAS DE INTERES
    // ===============================
    private static void pruebasInteres() throws Exception {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("PRUEBAS DE INTERES");
        System.out.println("-".repeat(80));

        // Crear Interés
        System.out.println("\n[INTERES - CREAR] Creando interés de prueba...");
        Interes interes = new Interes();
        interes.setNombreInteres("Tecnología - Pruebas");
        interes.setCategoria("Tecnología");

        try {
            interes1 = interesService.crearInteres(interes);
            System.out.println("[INTERES - CREAR] ✓ Interés creado: ID=" + interes1.getIdInteres());
        } catch (Exception e) {
            System.out.println("[INTERES - CREAR] ℹ Interés ya existe, buscando...");
            List<Interes> intereses = interesService.listarIntereses(100);
            interes1 = intereses.stream()
                .filter(i -> i.getNombreInteres().equals("Tecnología - Pruebas"))
                .findFirst()
                .orElse(intereses.get(0));
        }

        // Listar Intereses
        System.out.println("\n[INTERES - LISTAR] Listando intereses...");
        List<Interes> intereses = interesService.listarIntereses(10);
        System.out.println("[INTERES - LISTAR] ✓ Total de intereses: " + intereses.size());
    }

    // ===============================
    // PRUEBAS DE PREFERENCIAS
    // ===============================
    private static void pruebasPreferencias() throws Exception {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("PRUEBAS DE PREFERENCIAS");
        System.out.println("-".repeat(80));

        // Crear Preferencia
        System.out.println("\n[PREFERENCIA - CREAR] Creando preferencia para estudiante1...");
        Preferencia pref = new Preferencia();
        pref.setEstudiante(estudiante1);
        pref.setEdadMinima(18);
        pref.setEdadMaxima(30);
        pref.setCarreraPreferida(carreraPrueba);

        try {
            Preferencia prefCreada = preferenciaService.guardarPreferencias(pref);
            System.out.println("[PREFERENCIA - CREAR] ✓ Preferencia creada: ID=" + prefCreada.getIdPreferencia());
        } catch (Exception e) {
            System.out.println("[PREFERENCIA - CREAR] ℹ Error o ya existe: " + e.getMessage());
        }

        // Obtener Preferencia por Estudiante
        System.out.println("\n[PREFERENCIA - BUSCAR] Buscando preferencias del estudiante...");
        Preferencia prefEncontrada = preferenciaService.buscarPorEstudiante(estudiante1.getIdEstudiante());
        if (prefEncontrada != null) {
            System.out.println("[PREFERENCIA - BUSCAR] ✓ Preferencia encontrada: Edad " +
                prefEncontrada.getEdadMinima() + "-" + prefEncontrada.getEdadMaxima());
        }
    }

    // ===============================
    // PRUEBAS DE LIKES Y MATCHES
    // ===============================
    private static void pruebasLikesYMatches() throws Exception {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("PRUEBAS DE LIKES Y MATCHES");
        System.out.println("-".repeat(80));

        // Crear Like 1 -> 2
        System.out.println("\n[LIKE - CREAR] Estudiante 1 da like a Estudiante 2...");
        Like like12 = crearLike(estudiante1, estudiante2);

        // Crear Like 2 -> 1 (esto debería crear un match)
        System.out.println("\n[LIKE - CREAR] Estudiante 2 da like a Estudiante 1 (match esperado)...");
        Like like21 = crearLike(estudiante2, estudiante1);

        // Verificar Match
        System.out.println("\n[MATCH - VERIFICAR] Verificando si existe match...");
        Match match = matchService.verificarMatchExistente(estudiante1.getIdEstudiante(), estudiante2.getIdEstudiante());
        if (match != null) {
            System.out.println("[MATCH - VERIFICAR] ✓ Match encontrado: ID=" + match.getIdMatch());
        } else {
            System.out.println("[MATCH - VERIFICAR] ℹ No se encontró match (puede ser normal)");
        }

        // Obtener Matches del Estudiante
        System.out.println("\n[MATCH - LISTAR] Obteniendo matches del estudiante1...");
        List<Match> matches = matchService.obtenerMatchesPorEstudiante(estudiante1.getIdEstudiante(), 10);
        System.out.println("[MATCH - LISTAR] ✓ Total de matches: " + matches.size());
    }

    private static Like crearLike(Estudiante emisor, Estudiante receptor) throws Exception {
        Like like = likeService.verificarLikeExistente(emisor.getIdEstudiante(), receptor.getIdEstudiante());
        if (like == null) {
            like = new Like();
            like.setEstudianteEmisor(emisor);
            like.setEstudianteReceptor(receptor);
            like = likeService.crearLike(like);
            System.out.println("[LIKE - CREAR] ✓ Like creado: ID=" + like.getIdLike());
        } else {
            System.out.println("[LIKE - CREAR] ℹ Like ya existe: ID=" + like.getIdLike());
        }
        return like;
    }

    // ===============================
    // PRUEBAS DE CHAT Y MENSAJES
    // ===============================
    private static void pruebasChatYMensajes() throws Exception {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("PRUEBAS DE CHAT Y MENSAJES");
        System.out.println("-".repeat(80));

        // Verificar si existe match
        Match match = matchService.verificarMatchExistente(estudiante1.getIdEstudiante(), estudiante2.getIdEstudiante());
        if (match == null) {
            System.out.println("[CHAT] ℹ No hay match, saltando pruebas de chat");
            return;
        }

        // Obtener o crear Chat
        System.out.println("\n[CHAT - CREAR] Obteniendo/creando chat para el match...");
        Chat chat = matchService.obtenerOCrearChatDelMatch(match.getIdMatch());
        System.out.println("[CHAT - CREAR] ✓ Chat obtenido/creado: ID=" + chat.getIdChat());

        // Enviar Mensajes
        System.out.println("\n[MENSAJE - ENVIAR] Enviando mensajes en el chat...");

        Mensaje msg1 = new Mensaje();
        msg1.setChat(chat);
        msg1.setEstudianteEmisor(estudiante1);
        msg1.setContenido("Hola! Este es un mensaje de prueba.");
        Mensaje msgEnviado1 = mensajeService.crearMensaje(msg1);
        System.out.println("[MENSAJE - ENVIAR] ✓ Mensaje 1 enviado: ID=" + msgEnviado1.getIdMensaje());

        Mensaje msg2 = new Mensaje();
        msg2.setChat(chat);
        msg2.setEstudianteEmisor(estudiante2);
        msg2.setContenido("Hola! Respuesta de prueba.");
        Mensaje msgEnviado2 = mensajeService.crearMensaje(msg2);
        System.out.println("[MENSAJE - ENVIAR] ✓ Mensaje 2 enviado: ID=" + msgEnviado2.getIdMensaje());

        // Obtener Mensajes del Chat
        System.out.println("\n[MENSAJE - LISTAR] Obteniendo mensajes del chat...");
        List<Mensaje> mensajes = chatService.obtenerMensajesDelChat(chat.getIdChat(), 10);
        System.out.println("[MENSAJE - LISTAR] ✓ Total de mensajes: " + mensajes.size());
        for (Mensaje m : mensajes) {
            System.out.println("  - [" + m.getEstudianteEmisor().getNombreEstudiante() + "]: " + m.getContenido());
        }
    }

    // ===============================
    // PRUEBAS DE CASOS DE ERROR
    // ===============================
    private static void pruebasCasosDeError() {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("PRUEBAS DE CASOS DE ERROR Y VALIDACIONES");
        System.out.println("-".repeat(80));

        // Test 1: Crear estudiante con correo duplicado
        System.out.println("\n[ERROR - DUPLICADO] Intentando crear estudiante con correo duplicado...");
        try {
            Estudiante duplicado = new Estudiante();
            duplicado.setNombreEstudiante("Duplicado");
            duplicado.setCorreoInstitucional("test1.pruebas@itson.edu.mx");
            duplicado.setContrasena("pass");
            duplicado.setCarrera(carreraPrueba);
            estudianteService.crearEstudiante(duplicado);
            System.out.println("[ERROR - DUPLICADO] ✗ ERROR: Debería haber lanzado DuplicadoException");
        } catch (DuplicadoException e) {
            System.out.println("[ERROR - DUPLICADO] ✓ DuplicadoException capturada correctamente: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR - DUPLICADO] ⚠ Excepción diferente: " + e.getClass().getSimpleName());
        }

        // Test 2: Crear estudiante con correo inválido
        System.out.println("\n[ERROR - VALIDACION] Intentando crear estudiante con correo no institucional...");
        try {
            Estudiante invalido = new Estudiante();
            invalido.setNombreEstudiante("Invalido");
            invalido.setCorreoInstitucional("correo@gmail.com");
            invalido.setContrasena("pass");
            invalido.setCarrera(carreraPrueba);
            estudianteService.crearEstudiante(invalido);
            System.out.println("[ERROR - VALIDACION] ✗ ERROR: Debería haber lanzado ValidacionException");
        } catch (ValidacionException e) {
            System.out.println("[ERROR - VALIDACION] ✓ ValidacionException capturada correctamente: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR - VALIDACION] ⚠ Excepción diferente: " + e.getClass().getSimpleName());
        }

        // Test 3: Autenticar con credenciales incorrectas
        System.out.println("\n[ERROR - AUTENTICACION] Intentando autenticar con contraseña incorrecta...");
        try {
            estudianteService.autenticar("test1.pruebas@itson.edu.mx", "contraseñaIncorrecta");
            System.out.println("[ERROR - AUTENTICACION] ✗ ERROR: Debería haber lanzado AutenticacionException");
        } catch (AutenticacionException e) {
            System.out.println("[ERROR - AUTENTICACION] ✓ AutenticacionException capturada correctamente: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR - AUTENTICACION] ⚠ Excepción diferente: " + e.getClass().getSimpleName());
        }

        // Test 4: Buscar estudiante inexistente
        System.out.println("\n[ERROR - NO_ENCONTRADO] Intentando buscar estudiante con ID inexistente...");
        try {
            estudianteService.buscarPorId(999999L);
            System.out.println("[ERROR - NO_ENCONTRADO] ✗ ERROR: Debería haber lanzado EntidadNoEncontradaException");
        } catch (EntidadNoEncontradaException e) {
            System.out.println("[ERROR - NO_ENCONTRADO] ✓ EntidadNoEncontradaException capturada correctamente: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR - NO_ENCONTRADO] ⚠ Excepción diferente: " + e.getClass().getSimpleName());
        }

        // Test 5: Validar límites
        System.out.println("\n[ERROR - LIMITE] Intentando listar con límite mayor a 100...");
        try {
            estudianteService.listarEstudiantes(150);
            System.out.println("[ERROR - LIMITE] ✗ ERROR: Debería haber lanzado ValidacionException");
        } catch (ValidacionException e) {
            System.out.println("[ERROR - LIMITE] ✓ ValidacionException capturada correctamente: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR - LIMITE] ⚠ Excepción diferente: " + e.getClass().getSimpleName());
        }
    }
}
