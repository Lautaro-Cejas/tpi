package utn.tfi.grupo7.main;

import utn.tfi.grupo7.entities.Empleado;
import utn.tfi.grupo7.entities.Legajo;
import utn.tfi.grupo7.service.EmpleadoService;
import utn.tfi.grupo7.service.LegajoService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Gestiona la interacción con el usuario (Vista de Consola).
 * Llama a la capa de Servicio para ejecutar las acciones.
 */
public class AppMenu {

    // Los dos servicios (tu capa) que va a usar el menú
    private EmpleadoService empleadoService;
    private LegajoService legajoService;
    private Scanner scanner;

    public AppMenu() {
        // El menú instancia tus servicios
        this.empleadoService = new EmpleadoService();
        this.legajoService = new LegajoService();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Bucle principal del menú.
     */
    public void iniciar() {
        int opcion = -1;

        while (opcion != 0) {
            mostrarMenuPrincipal();
            try {
                System.out.print("Ingrese una opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer

                switch (opcion) {
                    case 1:
                        menuGestionEmpleados(); // Ir al submenú de Empleados
                        break;
                    case 2:
                        menuGestionLegajos(); // Ir al submenú de Legajos
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Error: Debe ingresar un número.");
                scanner.nextLine(); // Limpiar buffer en caso de error
                opcion = -1; // Resetear opción
            } catch (Exception e) {
                // Captura TODOS los errores de tu capa de Servicio (ej: "DNI duplicado", "Rollback ejecutado")
                System.err.println("\n!!!! HA OCURRIDO UN ERROR !!!!");
                System.err.println("MENSAJE: " + e.getMessage());
                System.out.println("---------------------------------");
            }
            
            if (opcion != 0) {
                 System.out.println("\nPresione Enter para continuar...");
                 scanner.nextLine();
            }
        }
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n--- SISTEMA DE GESTIÓN DE RRHH ---");
        System.out.println("1. Gestionar Empleados");
        System.out.println("2. Gestionar Legajos (CRUD Simple)");
        System.out.println("0. Salir");
    }

    //------------------------------------------------------------------
    // SUBMENÚ DE EMPLEADOS
    //------------------------------------------------------------------

    private void menuGestionEmpleados() throws Exception {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- GESTIÓN DE EMPLEADOS ---");
            System.out.println("1. Listar todos los empleados");
            System.out.println("2. Crear nuevo empleado (Con Legajo)");
            System.out.println("3. Buscar empleado por DNI");
            System.out.println("4. Actualizar empleado");
            System.out.println("5. Eliminar empleado (Baja Lógica)");
            System.out.println("0. Volver al menú principal");
            System.out.print("Ingrese una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    listarEmpleados();
                    break;
                case 2:
                    crearEmpleado();
                    break;
                case 3:
                    buscarEmpleadoPorDni(); // TODO: Agustín
                    break;
                case 4:
                    actualizarEmpleado(); // TODO: Agustín
                    break;
                case 5:
                    eliminarEmpleado(); // TODO: Agustín
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    /**
     * [HECHO] Llama a tu Service para listar empleados.
     */
    private void listarEmpleados() throws Exception {
        System.out.println("\n--- Listado de Empleados ---");
        List<Empleado> empleados = empleadoService.getAll();
        
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados para mostrar.");
            return;
        }
        for (Empleado emp : empleados) {
            System.out.println(emp); // Usa el .toString() de Empleado
        }
    }

    /**
     * [HECHO] Pide datos y llama a tu Service transaccional.
     */
    private void crearEmpleado() throws Exception {
        System.out.println("\n--- Crear Nuevo Empleado (Paso 1: Legajo) ---");
        System.out.print("Número de Legajo: ");
        String nroLegajo = scanner.nextLine();
        // ... (Pedir resto de datos de Legajo: categoria, estado, etc.)
        
        Legajo nuevoLegajo = new Legajo();
        nuevoLegajo.setNroLegajo(nroLegajo);
        // ... (Setear resto de atributos)

        System.out.println("\n--- (Paso 2: Empleado) ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("DNI: ");
        String dni = scanner.nextLine();
        // ... (Pedir resto de datos de Empleado: email, area, cargo, etc.)

        Empleado nuevoEmpleado = new Empleado();
        nuevoEmpleado.setNombre(nombre);
        nuevoEmpleado.setApellido(apellido);
        nuevoEmpleado.setDni(dni);
        nuevoEmpleado.setLegajo(nuevoLegajo); // Asocia el legajo

        // Llama a TU método transaccional
        empleadoService.crear(nuevoEmpleado);
        
        System.out.println("\n¡Empleado creado con éxito! (ID: " + nuevoEmpleado.getId() + ")");
    }

    /**
     * TODO: AGUSTÍN
     * Debe llamar a empleadoService.getEmpleadoByDni()
     */
    private void buscarEmpleadoPorDni() throws Exception {
        System.out.println("\n--- Buscar Empleado por DNI ---");
        System.out.print("Ingrese DNI a buscar: ");
        String dni = scanner.nextLine();
        
        // TODO: Agustín - Llamar al servicio
        // Empleado emp = empleadoService.getEmpleadoByDni(dni);
        // if (emp != null) { ... } else { ... }
        
        System.out.println("TODO: Implementar búsqueda...");
    }

    /**
     * TODO: AGUSTÍN
     * Debe llamar a empleadoService.actualizar()
     */
    private void actualizarEmpleado() throws Exception {
        System.out.println("\n--- Actualizar Empleado ---");
        // TODO: Agustín
        // 1. Pedir ID
        // 2. Llamar a empleadoService.getById(id)
        // 3. Mostrar datos actuales
        // 4. Pedir nuevos datos
        // 5. Llamar a empleadoService.actualizar(emp)
        
        System.out.println("TODO: Implementar actualización...");
    }

    /**
     * TODO: AGUSTÍN
     * Debe llamar a empleadoService.eliminar()
     */
    private void eliminarEmpleado() throws Exception {
        System.out.println("\n--- Eliminar Empleado (Baja Lógica) ---");
        System.out.print("Ingrese ID del empleado a eliminar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        
        // TODO: Agustín
        // 1. (Opcional) Mostrar datos del empleado
        // 2. Pedir confirmación
        // 3. Llamar a empleadoService.eliminar(id)
        
        System.out.println("TODO: Implementar eliminación...");
    }


    //------------------------------------------------------------------
    // SUBMENÚ DE LEGAJOS (CRUD Simple)
    //------------------------------------------------------------------
    
    /**
     * TODO: AGUSTÍN
     * Menú simple para el CRUD de la entidad Legajo.
     * Debe llamar a los métodos de legajoService.
     */
    private void menuGestionLegajos() throws Exception {
        System.out.println("\n--- GESTIÓN DE LEGAJOS ---");
        // TODO: Agustín - Armar este submenú
        // 1. Listar Legajos (llamar a legajoService.getAll())
        // 2. Crear Legajo (llamar a legajoService.crear())
        // 3. Actualizar Legajo (llamar a legajoService.actualizar())
        // 4. Eliminar Legajo (llamar a legajoService.eliminar())
        // 0. Volver
        
        System.out.println("TODO: Implementar CRUD de Legajos...");
    }
}