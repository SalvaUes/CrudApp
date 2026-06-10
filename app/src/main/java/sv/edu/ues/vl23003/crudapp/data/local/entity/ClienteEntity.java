package sv.edu.ues.vl23003.crudapp.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "clientes")
public class ClienteEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String nombre;
    public String telefono;
    public String email;
    public String direccion;
    public String municipio;
    public String notas;

    public ClienteEntity() {}

    public ClienteEntity(String nombre, String direccion, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
