package Main;

import Config.JpaUtil;
import Domain.Carrera;
import Domain.Estudiante;
import Domain.Hobby;
import Domain.Interes;
import Domain.Preferencia;
import Utils.ImagenUtils;
import jakarta.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Clase para inicializar la base de datos con datos de prueba
 * @author alfre
 */
public class InicializarBaseDatos {

    public static void inicializar() {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            em.getTransaction().begin();

            // Verificar si ya hay datos en la base de datos
            Long countCarreras = em.createQuery("SELECT COUNT(c) FROM Carrera c", Long.class).getSingleResult();
            if (countCarreras > 0) {
                System.out.println("✓ La base de datos ya contiene datos. Saltando inicialización.");
                em.getTransaction().commit();
                return;
            }

            // ========== CARRERAS ==========
            System.out.println("Creando carreras...");

            Carrera ingSoftware = new Carrera();
            ingSoftware.setNombreCarrera("Ingeniería en Software");
            em.persist(ingSoftware);

            Carrera ingSistemas = new Carrera();
            ingSistemas.setNombreCarrera("Ingeniería en Sistemas Computacionales");
            em.persist(ingSistemas);

            Carrera ingElectronica = new Carrera();
            ingElectronica.setNombreCarrera("Ingeniería Electrónica");
            em.persist(ingElectronica);

            Carrera ingIndustrial = new Carrera();
            ingIndustrial.setNombreCarrera("Ingeniería Industrial");
            em.persist(ingIndustrial);

            Carrera ingMecanica = new Carrera();
            ingMecanica.setNombreCarrera("Ingeniería Mecánica");
            em.persist(ingMecanica);

            Carrera licAdministracion = new Carrera();
            licAdministracion.setNombreCarrera("Licenciatura en Administración");
            em.persist(licAdministracion);

            Carrera licPsicologia = new Carrera();
            licPsicologia.setNombreCarrera("Licenciatura en Psicología");
            em.persist(licPsicologia);

            Carrera licDerecho = new Carrera();
            licDerecho.setNombreCarrera("Licenciatura en Derecho");
            em.persist(licDerecho);

            // ========== HOBBIES ==========
            System.out.println("Creando hobbies...");

            String[] hobbiesNombres = {
                "Gaming", "Programación", "Lectura", "Deportes", "Música",
                "Cine", "Fotografía", "Viajes", "Cocina", "Arte",
                "Fitness", "Yoga", "Senderismo", "Natación", "Ciclismo",
                "Bailar", "Tocar guitarra", "Dibujar", "Escribir", "Videojuegos",
                "Ajedrez", "Correr", "Voleibol", "Fútbol", "Basketball"
            };

            for (String nombre : hobbiesNombres) {
                Hobby hobby = new Hobby();
                hobby.setNombreHobby(nombre);
                em.persist(hobby);
            }

            // ========== INTERESES ==========
            System.out.println("Creando intereses...");

            // Tecnología
            crearInteres(em, "Inteligencia Artificial", "Tecnología");
            crearInteres(em, "Desarrollo Web", "Tecnología");
            crearInteres(em, "Ciberseguridad", "Tecnología");
            crearInteres(em, "Blockchain", "Tecnología");
            crearInteres(em, "IoT", "Tecnología");
            crearInteres(em, "Machine Learning", "Tecnología");
            crearInteres(em, "Realidad Virtual", "Tecnología");

            // Entretenimiento
            crearInteres(em, "Películas Sci-Fi", "Entretenimiento");
            crearInteres(em, "Series de TV", "Entretenimiento");
            crearInteres(em, "Anime", "Entretenimiento");
            crearInteres(em, "K-Pop", "Entretenimiento");
            crearInteres(em, "Rock", "Entretenimiento");
            crearInteres(em, "Música Indie", "Entretenimiento");
            crearInteres(em, "Reggaetón", "Entretenimiento");

            // Cultura
            crearInteres(em, "Literatura Clásica", "Cultura");
            crearInteres(em, "Historia", "Cultura");
            crearInteres(em, "Filosofía", "Cultura");
            crearInteres(em, "Arte Moderno", "Cultura");
            crearInteres(em, "Museos", "Cultura");

            // Deportes
            crearInteres(em, "Fútbol Americano", "Deportes");
            crearInteres(em, "Fútbol Soccer", "Deportes");
            crearInteres(em, "Béisbol", "Deportes");
            crearInteres(em, "Basketball", "Deportes");
            crearInteres(em, "Tenis", "Deportes");
            crearInteres(em, "eSports", "Deportes");

            // Estilo de vida
            crearInteres(em, "Vida saludable", "Estilo de vida");
            crearInteres(em, "Meditación", "Estilo de vida");
            crearInteres(em, "Veganismo", "Estilo de vida");
            crearInteres(em, "Minimalismo", "Estilo de vida");
            crearInteres(em, "Emprendimiento", "Estilo de vida");
            crearInteres(em, "Activismo", "Estilo de vida");

            // Ciencia
            crearInteres(em, "Astronomía", "Ciencia");
            crearInteres(em, "Física Cuántica", "Ciencia");
            crearInteres(em, "Biología", "Ciencia");
            crearInteres(em, "Química", "Ciencia");
            crearInteres(em, "Medio Ambiente", "Ciencia");

            em.getTransaction().commit();

            // Asegurar que los datos se persistan correctamente
            em.clear();

            System.out.println("✅ Base de datos inicializada correctamente!");
            System.out.println("   - Carreras: 8");
            System.out.println("   - Hobbies: " + hobbiesNombres.length);
            System.out.println("   - Intereses: 36");

            // Verificar que las carreras se guardaron
            em.getTransaction().begin();
            Long count = em.createQuery("SELECT COUNT(c) FROM Carrera c", Long.class).getSingleResult();
            em.getTransaction().commit();
            System.out.println("✅ Verificación: " + count + " carreras en la BD");

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("❌ Error al inicializar la base de datos:");
            e.printStackTrace();
            throw new RuntimeException("Error al inicializar la BD", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private static void crearInteres(EntityManager em, String nombre, String categoria) {
        Interes interes = new Interes();
        interes.setNombreInteres(nombre);
        interes.setCategoria(categoria);
        em.persist(interes);
    }

    public static void crearEstudiantesPrueba() {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            em.getTransaction().begin();

            // Verificar si ya hay estudiantes en la base de datos
            Long countEstudiantes = em.createQuery("SELECT COUNT(e) FROM Estudiante e", Long.class).getSingleResult();
            if (countEstudiantes > 0) {
                System.out.println("✓ Ya existen estudiantes en la base de datos. Saltando creación de estudiantes de prueba.");
                em.getTransaction().commit();
                return;
            }

            System.out.println("Creando estudiantes de prueba...");

            // Obtener carreras, hobbies e intereses existentes
            List<Carrera> carreras = em.createQuery("SELECT c FROM Carrera c", Carrera.class).getResultList();
            List<Hobby> hobbies = em.createQuery("SELECT h FROM Hobby h", Hobby.class).getResultList();
            List<Interes> intereses = em.createQuery("SELECT i FROM Interes i", Interes.class).getResultList();

            // ESTUDIANTE 1: María - Ing. Software
            Estudiante maria = new Estudiante();
            maria.setNombreEstudiante("María");
            maria.setApellidoPaterno("García");
            maria.setApellidoMaterno("López");
            maria.setCorreoInstitucional("maria.garcia@potros.itson.edu.mx");
            maria.setContrasena("password123");
            maria.setGenero("Mujer");
            maria.setEdad(20);
            maria.setCarrera(carreras.get(0)); // Ing. Software
            maria.setFotoPerfil(ImagenUtils.generarImagenConIniciales("María", "García", 200));
            Set<Hobby> hobbiesMaria = new HashSet<>();
            hobbiesMaria.add(hobbies.get(0)); // Gaming
            hobbiesMaria.add(hobbies.get(1)); // Programación
            hobbiesMaria.add(hobbies.get(4)); // Música
            maria.setHobbies(hobbiesMaria);
            Set<Interes> interesesMaria = new HashSet<>();
            interesesMaria.add(intereses.get(0)); // IA
            interesesMaria.add(intereses.get(7)); // Películas Sci-Fi
            interesesMaria.add(intereses.get(12)); // Música Indie
            maria.setIntereses(interesesMaria);
            em.persist(maria);

            // ESTUDIANTE 2: Carlos - Ing. Sistemas
            Estudiante carlos = new Estudiante();
            carlos.setNombreEstudiante("Carlos");
            carlos.setApellidoPaterno("Martínez");
            carlos.setApellidoMaterno("Rodríguez");
            carlos.setCorreoInstitucional("carlos.martinez@potros.itson.edu.mx");
            carlos.setContrasena("password123");
            carlos.setGenero("Hombre");
            carlos.setEdad(22);
            carlos.setCarrera(carreras.get(1)); // Ing. Sistemas
            carlos.setFotoPerfil(ImagenUtils.generarImagenConIniciales("Carlos", "López", 200));
            Set<Hobby> hobbiesCarlos = new HashSet<>();
            hobbiesCarlos.add(hobbies.get(3)); // Deportes
            hobbiesCarlos.add(hobbies.get(0)); // Gaming
            hobbiesCarlos.add(hobbies.get(10)); // Fitness
            carlos.setHobbies(hobbiesCarlos);
            Set<Interes> interesesCarlos = new HashSet<>();
            interesesCarlos.add(intereses.get(1)); // Desarrollo Web
            interesesCarlos.add(intereses.get(20)); // Fútbol Soccer
            interesesCarlos.add(intereses.get(25)); // eSports
            carlos.setIntereses(interesesCarlos);
            em.persist(carlos);

            // ESTUDIANTE 3: Ana - Lic. Psicología
            Estudiante ana = new Estudiante();
            ana.setNombreEstudiante("Ana");
            ana.setApellidoPaterno("Hernández");
            ana.setApellidoMaterno("Pérez");
            ana.setCorreoInstitucional("ana.hernandez@potros.itson.edu.mx");
            ana.setContrasena("password123");
            ana.setGenero("Mujer");
            ana.setEdad(21);
            ana.setCarrera(carreras.get(6)); // Lic. Psicología
            ana.setFotoPerfil(ImagenUtils.generarImagenConIniciales("Ana", "Martínez", 200));
            Set<Hobby> hobbiesAna = new HashSet<>();
            hobbiesAna.add(hobbies.get(2)); // Lectura
            hobbiesAna.add(hobbies.get(11)); // Yoga
            hobbiesAna.add(hobbies.get(9)); // Arte
            ana.setHobbies(hobbiesAna);
            Set<Interes> interesesAna = new HashSet<>();
            interesesAna.add(intereses.get(14)); // Literatura Clásica
            interesesAna.add(intereses.get(27)); // Meditación
            interesesAna.add(intereses.get(17)); // Arte Moderno
            ana.setIntereses(interesesAna);
            em.persist(ana);

            // ESTUDIANTE 4: Luis - Ing. Industrial
            Estudiante luis = new Estudiante();
            luis.setNombreEstudiante("Luis");
            luis.setApellidoPaterno("González");
            luis.setApellidoMaterno("Sánchez");
            luis.setCorreoInstitucional("luis.gonzalez@potros.itson.edu.mx");
            luis.setContrasena("password123");
            luis.setGenero("Hombre");
            luis.setEdad(23);
            luis.setCarrera(carreras.get(3)); // Ing. Industrial
            luis.setFotoPerfil(ImagenUtils.generarImagenConIniciales("Luis", "Rodríguez", 200));
            Set<Hobby> hobbiesLuis = new HashSet<>();
            hobbiesLuis.add(hobbies.get(7)); // Viajes
            hobbiesLuis.add(hobbies.get(6)); // Fotografía
            hobbiesLuis.add(hobbies.get(13)); // Natación
            luis.setHobbies(hobbiesLuis);
            Set<Interes> interesesLuis = new HashSet<>();
            interesesLuis.add(intereses.get(30)); // Emprendimiento
            interesesLuis.add(intereses.get(26)); // Vida saludable
            interesesLuis.add(intereses.get(15)); // Historia
            luis.setIntereses(interesesLuis);
            em.persist(luis);

            // ESTUDIANTE 5: Sofía - Ing. Software
            Estudiante sofia = new Estudiante();
            sofia.setNombreEstudiante("Sofía");
            sofia.setApellidoPaterno("Ramírez");
            sofia.setApellidoMaterno("Torres");
            sofia.setCorreoInstitucional("sofia.ramirez@potros.itson.edu.mx");
            sofia.setContrasena("password123");
            sofia.setGenero("Mujer");
            sofia.setEdad(19);
            sofia.setCarrera(carreras.get(0)); // Ing. Software
            sofia.setFotoPerfil(ImagenUtils.generarImagenConIniciales("Sofía", "Hernández", 200));
            Set<Hobby> hobbiesSofia = new HashSet<>();
            hobbiesSofia.add(hobbies.get(1)); // Programación
            hobbiesSofia.add(hobbies.get(9)); // Arte
            hobbiesSofia.add(hobbies.get(5)); // Cine
            sofia.setHobbies(hobbiesSofia);
            Set<Interes> interesesSofia = new HashSet<>();
            interesesSofia.add(intereses.get(5)); // Machine Learning
            interesesSofia.add(intereses.get(9)); // Anime
            interesesSofia.add(intereses.get(6)); // Realidad Virtual
            sofia.setIntereses(interesesSofia);
            em.persist(sofia);

            // ESTUDIANTE 6: Diego - Ing. Electrónica
            Estudiante diego = new Estudiante();
            diego.setNombreEstudiante("Diego");
            diego.setApellidoPaterno("Flores");
            diego.setApellidoMaterno("Morales");
            diego.setCorreoInstitucional("diego.flores@potros.itson.edu.mx");
            diego.setContrasena("password123");
            diego.setGenero("Hombre");
            diego.setEdad(24);
            diego.setCarrera(carreras.get(2)); // Ing. Electrónica
            diego.setFotoPerfil(ImagenUtils.generarImagenConIniciales("Diego", "Sánchez", 200));
            Set<Hobby> hobbiesDiego = new HashSet<>();
            hobbiesDiego.add(hobbies.get(4)); // Música
            hobbiesDiego.add(hobbies.get(16)); // Tocar guitarra
            hobbiesDiego.add(hobbies.get(14)); // Ciclismo
            diego.setHobbies(hobbiesDiego);
            Set<Interes> interesesDiego = new HashSet<>();
            interesesDiego.add(intereses.get(4)); // IoT
            interesesDiego.add(intereses.get(11)); // Rock
            interesesDiego.add(intereses.get(32)); // Astronomía
            diego.setIntereses(interesesDiego);
            em.persist(diego);

            // ESTUDIANTE 7: Valeria - Lic. Administración
            Estudiante valeria = new Estudiante();
            valeria.setNombreEstudiante("Valeria");
            valeria.setApellidoPaterno("Jiménez");
            valeria.setApellidoMaterno("Castro");
            valeria.setCorreoInstitucional("valeria.jimenez@potros.itson.edu.mx");
            valeria.setContrasena("password123");
            valeria.setGenero("Mujer");
            valeria.setEdad(22);
            valeria.setCarrera(carreras.get(5)); // Lic. Administración
            valeria.setFotoPerfil(ImagenUtils.generarImagenConIniciales("Valeria", "Torres", 200));
            Set<Hobby> hobbiesValeria = new HashSet<>();
            hobbiesValeria.add(hobbies.get(15)); // Bailar
            hobbiesValeria.add(hobbies.get(8)); // Cocina
            hobbiesValeria.add(hobbies.get(10)); // Fitness
            valeria.setHobbies(hobbiesValeria);
            Set<Interes> interesesValeria = new HashSet<>();
            interesesValeria.add(intereses.get(30)); // Emprendimiento
            interesesValeria.add(intereses.get(10)); // K-Pop
            interesesValeria.add(intereses.get(29)); // Minimalismo
            valeria.setIntereses(interesesValeria);
            em.persist(valeria);

            // ESTUDIANTE 8: Roberto - Ing. Mecánica
            Estudiante roberto = new Estudiante();
            roberto.setNombreEstudiante("Roberto");
            roberto.setApellidoPaterno("Ruiz");
            roberto.setApellidoMaterno("Mendoza");
            roberto.setCorreoInstitucional("roberto.ruiz@potros.itson.edu.mx");
            roberto.setContrasena("password123");
            roberto.setGenero("Hombre");
            roberto.setEdad(25);
            roberto.setCarrera(carreras.get(4)); // Ing. Mecánica
            roberto.setFotoPerfil(ImagenUtils.generarImagenConIniciales("Roberto", "Gómez", 200));
            Set<Hobby> hobbiesRoberto = new HashSet<>();
            hobbiesRoberto.add(hobbies.get(3)); // Deportes
            hobbiesRoberto.add(hobbies.get(22)); // Correr
            hobbiesRoberto.add(hobbies.get(8)); // Cocina
            roberto.setHobbies(hobbiesRoberto);
            Set<Interes> interesesRoberto = new HashSet<>();
            interesesRoberto.add(intereses.get(23)); // Basketball
            interesesRoberto.add(intereses.get(26)); // Vida saludable
            interesesRoberto.add(intereses.get(35)); // Medio Ambiente
            roberto.setIntereses(interesesRoberto);
            em.persist(roberto);

            em.getTransaction().commit();

            System.out.println("✅ Estudiantes de prueba creados: 8");
            System.out.println("   - Correo: [nombre].[apellido]@potros.itson.edu.mx");
            System.out.println("   - Contraseña: password123");

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("❌ Error al crear estudiantes de prueba:");
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
