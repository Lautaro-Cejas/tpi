package utn.tfi.grupo7.entities;

import java.time.LocalDate;

/**
 * Representa la tabla Empleado (A)
 * Contiene una referencia 1→1 a Legajo
 */
public class Empleado {

    private Long id;
    private Boolean eliminado;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private LocalDate fechaIngreso;
    private String area;
    private Legajo legajo; // referencia 1→1 a Legajo

    // Constructor vacío
    public Empleado() {
        this.eliminado = false; // por defecto activo
    }

    // Constructor completo
    public Empleado(Long id, String nombre, String apellido, String dni,
                    String email, LocalDate fechaIngreso, String area,
                    Legajo legajo, Boolean eliminado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.fechaIngreso = fechaIngreso;
        this.area = area;
        this.legajo = legajo;
        this.eliminado = eliminado != null ? eliminado : false;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Boolean getEliminado() { return eliminado; }
    public void setEliminado(Boolean eliminado) { this.eliminado = eliminado; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public Legajo getLegajo() { return legajo; }
    public void setLegajo(Legajo legajo) { this.legajo = legajo; }

    // toString legible
    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", email='" + email + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                ", area='" + area + '\'' +
                ", legajo=" + (legajo != null ? legajo.getNroLegajo() : "null") +
                ", eliminado=" + eliminado +
                '}';
    }
}