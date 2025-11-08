package utn.tfi.grupo7.main;

/**
 * Clase principal que inicia la aplicación.
 * Su única responsabilidad es crear e iniciar el menú.
 */
public class Main {

    public static void main(String[] args) {
        // Instancia el menú
        AppMenu menu = new AppMenu();
        
        // Inicia el bucle principal del menú
        menu.iniciar();
    }
}