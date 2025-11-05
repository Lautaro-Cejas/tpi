package utn.tfi.grupo7.entities;

import java.time.LocalDate;

/**
 * Representa la tabla Legajo (B)
 */
public class Legajo {

    private Long id;               // Clave primaria
    private Boolean eliminado;     // Baja lógica
    private String nroLegajo;      // Número único
    private String categoria;      
    private String estado;         // "ACTIVO" o "INACTIVO"
    private LocalDate fechaAlta;
    private String observaciones;

    // Constructor vacío
    public Legajo() {
        this.eliminado = false; // por defecto activo
    }

    // Constructor completo
    public Legajo(Long id, String nroLegajo, String categoria, String estado,
                  LocalDate fechaAlta, String observaciones, Boolean eliminado) {
        this.id = id;
        this.nroLegajo = nroLegajo;
        this.categoria = categoria;
        this.estado = estado;
        this.fechaAlta = fechaAlta;
        this.observaciones = observaciones;
        this.eliminado = eliminado != null ? eliminado : false;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Boolean getEliminado() { return eliminado; }
    public void setEliminado(Boolean eliminado) { this.eliminado = eliminado; }

    public String getNroLegajo() { return nroLegajo; }
    public void setNroLegajo(String nroLegajo) { this.nroLegajo = nroLegajo; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDate getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    // toString legible
    @Override
    public String toString() {
        return "Legajo{" +
                "id=" + id +
                ", nroLegajo='" + nroLegajo + '\'' +
                ", categoria='" + categoria + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaAlta=" + fechaAlta +
                ", observaciones='" + observaciones + '\'' +
                ", eliminado=" + eliminado +
                '}';
    }
}