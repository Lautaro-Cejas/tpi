package utn.tfi.grupo7.main;

import utn.tfi.grupo7.entities.Empleado;
import utn.tfi.grupo7.entities.Legajo;
import utn.tfi.grupo7.service.EmpleadoService;
import utn.tfi.grupo7.service.LegajoService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * Gestiona la interacción con el usuario (Vista de Consola).
 * Llama a la capa de Servicio para ejecutar las acciones.
 */
public class AppMenu {

    private EmpleadoService empleadoService;
    private LegajoService legajoService;
    private Scanner scanner;

    public AppMenu() {
        this.empleadoService = new EmpleadoService();
        this.legajoService = new LegajoService();
        this.scanner = new Scanner(System.in);
    }

    /*
     * Bucle principal del menú.
    */
    public void iniciar() {
        int opcion = -1;

        while (opcion != 0) {
            mostrarMenuPrincipal();
            try {
                System.out.print("Ingrese una opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcion) {
                    case 1:
                        menuGestionEmpleados(); 
                        break;
                    case 2:
                        menuGestionLegajos(); 
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Error: Debe ingresar un número.");
                scanner.nextLine(); 
                opcion = -1; 
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
            System.out.println("4. Buscar empleado por ID");
            System.out.println("5. Actualizar empleado");
            System.out.println("6. Eliminar empleado (Baja Lógica)");
            System.out.println("0. Volver al menú principal");
            System.out.print("Ingrese una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    listarEmpleados();
                    break;
                case 2:
                    crearEmpleado();
                    break;
                case 3:
                    buscarEmpleadoPorDni(); 
                    break;
                case 4:
                    buscarEmpleadoPorId(); 
                    break;
                case 5:
                    actualizarEmpleado(); 
                    break;
                case 6:
                    eliminarEmpleado(); 
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private void listarEmpleados() throws Exception {
        System.out.println("\n--- Listado de Empleados ---");
        List<Empleado> empleados = empleadoService.getAll();
        
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados para mostrar.");
            return;
        }
        for (Empleado emp : empleados) {
            System.out.println(emp); 
        }
    }

    private void crearEmpleado() throws Exception {
        System.out.println("\n--- Crear Nuevo Empleado ---");
        System.out.println("\n--- (Paso 1: Legajo) ---");
        System.out.println("\n--- Crear Nuevo Legajo ---");
        Legajo nuevoLegajo = crearInstanciaLegajo();

        System.out.println("\n--- (Paso 2: Empleado) ---");
        Empleado nuevoEmpleado = new Empleado();
        cargarDatosEmpleado(nuevoEmpleado);
        
        nuevoEmpleado.setEliminado(false);
        nuevoEmpleado.setLegajo(nuevoLegajo);
        
        empleadoService.crear(nuevoEmpleado); 
        
        System.out.println("\n¡Empleado creado con éxito! (ID: " + nuevoEmpleado.getId() + ")");
    }

    private void buscarEmpleadoPorDni() throws Exception {
        System.out.println("\n--- Buscar Empleado por DNI ---");
        
        String dni = pedirDni();
        Empleado emp = empleadoService.getEmpleadoByDni(dni);
        if (emp == null) { 
            System.out.println("Empleado no encontrado");
            return;
        }
        System.out.println("Empleado encontrado: ");
        System.out.println(emp);
    }

    private void buscarEmpleadoPorId() throws Exception {
        System.out.println("\n--- Buscar Empleado por ID ---");
        Long idEmpleado = pedirId();

        Empleado empleado = empleadoService.getById(idEmpleado); 

        if (empleado == null) {
            System.out.println("No se encontró el empleado.");
            return;
        } 
        System.out.println("Empleado encontrado: ");
        System.out.println(empleado);
    }

    private void actualizarEmpleado() throws Exception {
        System.out.println("\n--- Actualizar Empleado ---");
        Long idEmpleado = pedirId();
        Empleado empleado = empleadoService.getById(idEmpleado); 

        if (empleado == null) {
            System.out.println("No se encontró el empleado.");
            return;
        }
        System.out.println("Datos del empleado");
        System.out.println(empleado);
        
        // Pedir nuevos datos
        System.out.println("Ingrese los nuevos datos.");
        cargarDatosEmpleado(empleado);
        empleadoService.actualizar(empleado);
        
        System.out.println("El empleado fue actualizado!");
    }

    private void eliminarEmpleado() throws Exception {
        System.out.println("\n--- Eliminar Empleado (Baja Lógica) ---");
        Long id = pedirId();

        Empleado empleado = empleadoService.getById(id); 

        if (empleado == null) {
            System.out.println("No se encontró el empleado.");
            return;
        }
        System.out.println("Datos del empleado");
        System.out.println(empleado);

        // Confirmación
        while (true) {
            System.out.print("\n¿Está seguro que desea eliminar el empleado? (S/N): ");
            String opcion = scanner.nextLine().trim().toUpperCase();

            if (opcion.equals("S")) {
                empleadoService.eliminar(id);
                System.out.println("Empleado eliminado correctamente.");
                return;
            } else if (opcion.equals("N")) {
                System.out.println("Operación cancelada.");
                return;
            } else {
                System.out.println("Opción inválida. Ingrese S o N.\n");
            }
        }
    }


    //------------------------------------------------------------------
    // SUBMENÚ DE LEGAJOS 
    //------------------------------------------------------------------
    private void menuGestionLegajos() throws Exception {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- GESTIÓN DE LEGAJOS ---");
            System.out.println("1. Listar todos los legajos");
            System.out.println("2. Crear nuevo legajo");
            System.out.println("3. Buscar legajo por ID");
            System.out.println("4. Actualizar legajo");
            System.out.println("5. Eliminar legajo (Baja Lógica)");
            System.out.println("0. Volver al menú principal");
            System.out.print("Ingrese una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    listarLegajos();
                    break;
                case 2:
                    crearLegajo();
                    break;
                case 3:
                    buscarLegajoPorId(); 
                    break;
                case 4:
                    actualizarLegajo(); 
                    break;
                case 5:
                    eliminarLegajo(); 
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private void listarLegajos() throws Exception {
        System.out.println("\n--- Listado de Legajos ---");
        List<Legajo> legajos = legajoService.getAll();
        
        if (legajos.isEmpty()) {
            System.out.println("No hay legajos para mostrar.");
            return;
        }
        for (Legajo leg : legajos) {
            System.out.println(leg); // Usa el .toString() de Legajo
        }
    }

    private void crearLegajo() throws Exception {
        System.out.println("\n--- Crear Nuevo Legajo ---");
        Legajo nuevoLegajo = crearInstanciaLegajo();

        //Llamamos metodo transaccional
        legajoService.crear(nuevoLegajo);

        System.out.println("\n¡Legajo creado con éxito! (ID: " + nuevoLegajo.getId() + ")");
    }

    private void buscarLegajoPorId() throws Exception {
        System.out.println("\n--- Buscar Legajo por ID ---");
        Long idLegajo = pedirId();

        Legajo legajo = legajoService.getById(idLegajo); 

        if (legajo == null) {
            System.out.println("No se encontró el legajo.");
            return;
        }
        System.out.println("Datos del legajo");
        System.out.println(legajo);
        
    }

    private void actualizarLegajo() throws Exception {
        System.out.println("\n--- Actualizar Legajo ---");
        Long idLegajo = pedirId();
        Legajo legajo = legajoService.getById(idLegajo); 

        if (legajo == null) {
            System.out.println("No se encontró el legajo.");
            return;
        }
        System.out.println("Datos del legajo");
        System.out.println(legajo);
        
        // Pedir nuevos datos
        System.out.println("Ingrese los nuevos valores"); 
        cargarDatosLegajo(legajo);
        
        legajoService.actualizar(legajo);
        
        System.out.println("El legajo fue actualizado!");
    }

    private void eliminarLegajo() throws Exception {
        System.out.println("\n--- Eliminar Legajo (Baja Lógica) ---");
        System.out.print("Ingrese ID del legajo a eliminar: ");
        
        Long idLegajo = pedirId();
        Legajo legajo = legajoService.getById(idLegajo); 

        if (legajo == null) {
            System.out.println("No se encontró el legajo.");
            return;
        }
        
        System.out.println("Datos del legajo");
        System.out.println(legajo);
        
        // Confirmación
        while (true) {
            System.out.print("\n¿Está seguro que desea eliminar el legajo? (S/N): ");
            String opcion = scanner.nextLine().trim().toUpperCase();

            if (opcion.equals("S")) {
                legajoService.eliminar(idLegajo);
                System.out.println("Legajo eliminado correctamente.");
                return;
            } else if (opcion.equals("N")) {
                System.out.println("Operación cancelada.");
                return;
            } else {
                System.out.println("Opción inválida. Ingrese S o N.\n");
            }
        }
    }


    //------------------------------------------------------------------
    // METODOS AUXILIARES
    //------------------------------------------------------------------
    
    private void mostrarMenuPrincipal() {
        System.out.println("\n--- SISTEMA DE GESTIÓN DE RRHH ---");
        System.out.println("1. Gestionar Empleados");
        System.out.println("2. Gestionar Legajos");
        System.out.println("0. Salir");
    }
    
    private Legajo crearInstanciaLegajo() throws Exception {

        Legajo nuevoLegajo = new Legajo();
        cargarDatosLegajo(nuevoLegajo);
        nuevoLegajo.setEliminado(false);
        
        return nuevoLegajo;
    }

    private String pedirNombre(String tipo) {
        while (true) {
            System.out.print("Ingrese " + tipo + ": ");
            String input = scanner.nextLine().trim();

            // Validación: no vacío, sin números, sin caracteres raros
            if (input.matches("[a-zA-ZÁÉÍÓÚáéíóúñÑ ]+")) {
                String[] partes = input.toLowerCase().split("\\s+");
                StringBuilder sb = new StringBuilder();
                for (String parte : partes) {
                    sb.append(
                        parte.substring(0, 1).toUpperCase()
                    + parte.substring(1)
                    ).append(" ");
                }
                return sb.toString().trim();
            }

            System.out.println("Entrada inválida. Intente nuevamente.\n");
        }
    }

    private String pedirEstado() {
        while (true) {
            System.out.print("Ingrese el Estado (ACTIVO / INACTIVO): ");
            String inputEstado = scanner.nextLine().toUpperCase();

            if (inputEstado.equals("ACTIVO") || inputEstado.equals("INACTIVO")) {
                return inputEstado;
            }

            System.out.println("Estado inválido. Ingrese ACTIVO o INACTIVO.\n");
        }
    }

    private String pedirDni() {
        while (true) {
            System.out.print("Ingrese DNI: ");
            String input = scanner.nextLine().trim();

            // Validar solo dígitos
            if (input.matches("\\d+")) {
                return input; // retorna como String
            }

            System.out.println("DNI inválido. Ingrese solo números.\n");
        }
    }

    private String pedirEmail() {
        while (true) {
            System.out.print("Ingrese Email: ");
            String email = scanner.nextLine().trim();

            // Validación simple y efectiva
            if (email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                return email;
            }

            System.out.println("Email inválido. Intente nuevamente.\n");
        }
    }

    private LocalDate pedirFecha(String tipoFecha) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            System.out.print("Ingrese fecha de " + tipoFecha + " (dd/MM/yyyy): ");
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input, formatter);
            } catch (Exception e) {
                System.out.println("Fecha inválida. Formato correcto: dd/MM/yyyy\n");
            }
        }
    }
    
    private Long pedirId() {
        while (true) {
            System.out.print("Ingrese ID: ");
            String input = scanner.nextLine().trim();

            if (input.matches("\\d+")) {
                return Long.parseLong(input);
            }

            System.out.println("ID inválido. Ingrese solo números.\n");
        }
    }

    private String pedirCampo(String campo) {
        while (true) {
            System.out.print("Ingrese " + campo + ": ");
            String input = scanner.nextLine();

            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Entrada inválida, vuelva a intentarlo.\n");
        }
    }
    
    private void cargarDatosEmpleado(Empleado empleado) throws Exception {
        String nombre = pedirNombre("Nombre");
        String apellido = pedirNombre("Apellido");
        String dni = pedirDni();
        String email = pedirEmail();
        LocalDate fechaIngreso = pedirFecha("ingreso");
        String area = pedirCampo("Área");

        empleado.setNombre(nombre);
        empleado.setApellido(apellido);
        empleado.setDni(dni);
        empleado.setEmail(email);
        empleado.setFechaIngreso(fechaIngreso);
        empleado.setArea(area);
    }

    private void cargarDatosLegajo(Legajo legajo) throws Exception {
        String nroLegajo = pedirCampo("Número de Legajo").toUpperCase();
        String categoriaLegajo = pedirCampo("Categoria");
        String obsLegajo = pedirCampo("Observaciones");
        String estadoLegajo = pedirEstado();
        LocalDate fechaAlta = pedirFecha("Alta");
        
        legajo.setNroLegajo(nroLegajo);
        legajo.setCategoria(categoriaLegajo);
        legajo.setObservaciones(obsLegajo);
        legajo.setEstado(estadoLegajo);
        legajo.setFechaAlta(fechaAlta);   
    }
}