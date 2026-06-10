package sv.edu.ues.vl23003.crudapp.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "servicios")
public class ServicioEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int clienteId;
    private String tipoServicio;
    private String estado;
    private double precio;
    private String descripcion;
    private String fecha;

    public ServicioEntity(int clienteId, String tipoServicio, String estado, double precio, String descripcion, String fecha) {
        this.clienteId = clienteId;
        this.tipoServicio = tipoServicio;
        this.estado = estado;
        this.precio = precio;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
