package Main;

import Domain.Carrera;
import InterfaceService.ICarreraService;
import Service.CarreraService;
import java.util.Arrays;
import java.util.List;

/**
 * Clase de utilidad para poblar la base de datos con datos iniciales.
 * Se ejecuta UNA SOLA VEZ manualmente.
 */
public class DataSeeder {

    public static void main(String[] args) {
        ICarreraService carreraService = new CarreraService();
        
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
                    System.out.println("[CREADA] " + nombre);
                    carrerasCreadas++;
                } else {
                    System.out.println("[OMITIDA] " + nombre + " (ya existe)");
                    carrerasOmitidas++;
                }
                
            } catch (Exception e) {
                System.err.println("[ERROR] Procesando '" + nombre + "': " + e.getMessage());
            }
        }
        
        System.out.println("--- Carga finalizada ---");
        System.out.println("Carreras Creadas: " + carrerasCreadas);
        System.out.println("Carreras Omitidas (Duplicadas): " + carrerasOmitidas);
        System.exit(0);
    }
}