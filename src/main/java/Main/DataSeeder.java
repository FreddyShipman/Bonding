package Main;

import Domain.Carrera;
import InterfaceService.ICarreraService;
import Service.CarreraService;
import java.util.Arrays;
import java.util.List;
import InterfaceService. *;
import Domain. *;
import Service. *;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase de utilidad para poblar la base de datos con datos iniciales. Se
 * ejecuta UNA SOLA VEZ manualmente.
 */
public class DataSeeder {

    public static void main(String[] args) {
        
        // --- 1. CREADOR DE SERVICIOS ---
        // (Creamos todos los servicios que necesitamos para el seeder)
        ICarreraService carreraService = new CarreraService();
        IEstudianteService estudianteService = new EstudianteService();
        IHobbyService hobbyService = new HobbyService();
        IInteresService interesService = new InteresService();
        ILikeService likeService = new LikeService();
        IMatchService matchService = new MatchService();
        IChatService chatService = new ChatService();
        IMensajeService mensajeService = new MensajeService();
        IPreferenciaService preferenciaService = new PreferenciaService();


        // =================================================================
        // --- PARTE A: CARGA DE CARRERAS ---
        // =================================================================
        System.out.println("--- Iniciando carga de carreras ---");
        List<String> nombresCarreras = Arrays.asList(
                "Licenciatura en Administración",
                "Licenciatura en Administración de Empresas Turísticas",
                "Licenciatura en Administración Estratégica",
                "Licenciatura en Arquitectura",
                "Licenciatura en Ciencias de la Educación",
                "Licenciatura en Ciencias del Ejercicio Físico",
                "Licenciatura en Contaduría Pública",
                "Licenciatura en Contaduría Pública Modalidad Mixta",
                "Licenciatura en Dirección de la Cultura Física y el Deporte",
                "Licenciatura en Diseño Gráfico",
                "Licenciatura en Derecho",
                "Licenciatura en Economía y Finanzas",
                "Licenciatura en Educación Artística y Gestión Cultural",
                "Licenciatura en Educación Infantil",
                "Licenciatura en Educación Inicial y Gestión de Instituciones",
                "Licenciatura en Emprendimiento e Innovación",
                "Licenciatura en Enfermería",
                "Licenciatura en Gastronomía",
                "Licenciatura en Mercadotecnia",
                "Licenciatura en Psicología",
                "Licenciatura en Tecnología de Alimentos",
                "Ingeniería en Biosistemas",
                "Ingeniería en Biotecnología",
                "Ingeniería en Ciencias Ambientales",
                "Ingeniería Civil",
                "Ingeniería Electromecánica",
                "Ingeniería en Electrónica",
                "Ingeniería Industrial y de Sistemas",
                "Ingeniería en Logística",
                "Ingeniería en Manufactura",
                "Ingeniería en Mecatrónica",
                "Ingeniería Química",
                "Ingeniería en Software",
                "Medicina Veterinaria y Zootecnia"
        );

        int carrerasCreadas = 0;
        int carrerasOmitidas = 0;

        for (String nombre : nombresCarreras) {
            try {
                Carrera carreraExistente = carreraService.buscarPorNombre(nombre);
                if (carreraExistente == null) {
                    Carrera nuevaCarrera = new Carrera();
                    nuevaCarrera.setNombreCarrera(nombre);
                    carreraService.crearCarrera(nuevaCarrera);
                    carrerasCreadas++;
                } else {
                    carrerasOmitidas++;
                }
            } catch (Exception e) {
                System.err.println("[ERROR] Procesando '" + nombre + "': " + e.getMessage());
            }
        }
        System.out.println("--- Carga de carreras finalizada ---");
        System.out.println("Carreras Creadas: " + carrerasCreadas);
        System.out.println("Carreras Omitidas (Duplicadas): " + carrerasOmitidas);
        
        // --- CORRECCIÓN CRÍTICA ---
        // ¡Se eliminó el System.exit(0); que estaba aquí!


        // =================================================================
        // --- PARTE B: CARGA DE USUARIOS Y MATCHES DE PRUEBA ---
        // =================================================================
        System.out.println("--- INICIANDO SEEDER DE DATOS DE PRUEBA ---");

        try {
            // 2. Crear Hobbies e Intereses base
            Hobby hGaming = crearHobby(hobbyService, "Gaming");
            Hobby hSenderismo = crearHobby(hobbyService, "Senderismo");
            Hobby hMusica = crearHobby(hobbyService, "Tocar Guitarra");
            Hobby hFotografia = crearHobby(hobbyService, "Fotografía");

            Interes iProg = crearInteres(interesService, "Programación");
            Interes iMusicaIndie = crearInteres(interesService, "Música Indie");
            Interes iCine = crearInteres(interesService, "Cine de Arte");
            Interes iIA = crearInteres(interesService, "Inteligencia Artificial");

            // 3. Obtener Carreras (que acabamos de crear)
            Carrera cSoftware = carreraService.buscarPorNombre("Ingeniería en Software");
            Carrera cPsicologia = carreraService.buscarPorNombre("Licenciatura en Psicología");
            Carrera cDiseno = carreraService.buscarPorNombre("Licenciatura en Diseño Gráfico");

            if (cSoftware == null || cPsicologia == null || cDiseno == null) {
                System.err.println("ERROR: No se encontraron las carreras base.");
                return;
            }

            // 4. Crear Estudiantes
            System.out.println("Creando estudiantes...");

            Estudiante ana = new Estudiante();
            ana.setNombreEstudiante("Ana");
            ana.setApellidoPaterno("García");
            ana.setCorreoInstitucional("ana.garcia@potros.itson.edu.mx");
            ana.setContrasena("1234"); // Contraseña simple para pruebas
            ana.setFotoPerfil("user_uploads/fotos_perfil/ana.png"); // Requiere foto
            ana.setCarrera(cSoftware);
            ana.setHobbies(new HashSet<>(Set.of(hGaming, hSenderismo)));
            ana.setIntereses(new HashSet<>(Set.of(iProg, iMusicaIndie)));
            estudianteService.crearEstudiante(ana);

            Estudiante bruno = new Estudiante();
            bruno.setNombreEstudiante("Bruno");
            bruno.setApellidoPaterno("Martínez");
            bruno.setCorreoInstitucional("bruno.martinez@potros.itson.edu.mx");
            bruno.setContrasena("1234");
            bruno.setFotoPerfil("user_uploads/fotos_perfil/bruno.png"); // Requiere foto
            bruno.setCarrera(cPsicologia);
            bruno.setHobbies(new HashSet<>(Set.of(hMusica)));
            bruno.setIntereses(new HashSet<>(Set.of(iCine, iMusicaIndie)));
            estudianteService.crearEstudiante(bruno);
            
            Estudiante carla = new Estudiante();
            carla.setNombreEstudiante("Carla");
            carla.setApellidoPaterno("López");
            carla.setCorreoInstitucional("carla.lopez@potros.itson.edu.mx");
            carla.setContrasena("1234");
            carla.setFotoPerfil("user_uploads/fotos_perfil/carla.png"); // Requiere foto
            carla.setCarrera(cDiseno);
            carla.setHobbies(new HashSet<>(Set.of(hFotografia)));
            carla.setIntereses(new HashSet<>(Set.of(iCine)));
            estudianteService.crearEstudiante(carla);
            
            Estudiante david = new Estudiante();
            david.setNombreEstudiante("David");
            david.setApellidoPaterno("Sánchez");
            david.setCorreoInstitucional("david.sanchez@potros.itson.edu.mx");
            david.setContrasena("1234");
            david.setFotoPerfil("user_uploads/fotos_perfil/david.png"); // Requiere foto
            david.setCarrera(cSoftware);
            david.setHobbies(new HashSet<>(Set.of(hGaming)));
            david.setIntereses(new HashSet<>(Set.of(iProg, iIA)));
            estudianteService.crearEstudiante(david);
            
            Estudiante elena = new Estudiante();
            elena.setNombreEstudiante("Elena");
            elena.setApellidoPaterno("Ríos");
            elena.setCorreoInstitucional("elena.rios@potros.itson.edu.mx");
            elena.setContrasena("1234");
            elena.setFotoPerfil("user_uploads/fotos_perfil/elena.png"); // Requiere foto
            elena.setCarrera(cPsicologia);
            elena.setHobbies(new HashSet<>(Set.of(hSenderismo, hFotografia)));
            elena.setIntereses(new HashSet<>(Set.of(iCine)));
            estudianteService.crearEstudiante(elena);

            // 5. Crear Preferencias (para Ana)
            Preferencia pAna = new Preferencia();
            pAna.setEstudiante(ana);
            pAna.setGeneroPreferido("Hombre"); // (Recuerda añadir 'genero' a Estudiante)
            pAna.setEdadMinima(18);
            pAna.setEdadMaxima(25);
            pAna.setCarreraPreferida(cPsicologia); // Ana (Software) busca a gente de Psicología
            preferenciaService.guardarPreferencias(pAna);

            // 6. Crear el Ecosistema de Likes y Matches
            System.out.println("Creando interacciones...");

            // --- Escenario 1: Match mutuo (Ana <-> Bruno) ---
            likeService.crearLike(new Like(null, LocalDateTime.now(), ana, bruno));
            likeService.crearLike(new Like(null, LocalDateTime.now(), bruno, ana));
            System.out.println("MATCH CREADO: Ana <-> Bruno");
            
            // --- Escenario 2: Match mutuo (Carla <-> David) ---
            likeService.crearLike(new Like(null, LocalDateTime.now(), carla, david));
            likeService.crearLike(new Like(null, LocalDateTime.now(), david, carla));
            System.out.println("MATCH CREADO: Carla <-> David");

            // --- Escenario 3: Like unilateral (Elena -> Ana) ---
            likeService.crearLike(new Like(null, LocalDateTime.now(), elena, ana));
            System.out.println("LIKE PENDIENTE: Elena -> Ana");
            
            // --- Escenario 4: Like unilateral (David -> Ana) ---
            likeService.crearLike(new Like(null, LocalDateTime.now(), david, ana));
            System.out.println("LIKE PENDIENTE: David -> Ana");

            System.out.println("--- ¡Seeder completado con éxito! ---");

        } catch (Exception e) {
            System.err.println("!!! --- ERROR EN EL SEEDER DE USUARIOS --- !!!");
            System.err.println("Es probable que los datos ya existan (ej. correo duplicado).");
            e.printStackTrace();
        } finally {
            // Forzar la salida (JPA puede dejar hilos abiertos)
            System.exit(0);
        }
    }

    // --- CORRECCIÓN AQUÍ: Usamos setters para Hobby ---
    private static Hobby crearHobby(IHobbyService s, String nombre) {
        try {
            Hobby nuevoHobby = new Hobby();
            nuevoHobby.setNombreHobby(nombre); // Asumo que tu setter se llama así
            return s.crearHobby(nuevoHobby);
        } catch (Exception e) {
            try {
                return s.buscarPorNombre(nombre);
            } catch (Exception ex) {
                return null;
            }
        }
    }
    
    // --- CORRECCIÓN AQUÍ: Usamos setters para Interes ---
    private static Interes crearInteres(IInteresService s, String nombre) {
        try {
            Interes nuevoInteres = new Interes();
            nuevoInteres.setNombreInteres(nombre); // Asumo que tu setter se llama así
            nuevoInteres.setCategoria("General"); // Asumo que tienes este setter
            return s.crearInteres(nuevoInteres);
        } catch (Exception e) {
            try {
                return s.buscarPorNombre(nombre);
            } catch (Exception ex) {
                return null;
            }
        }
    }
}