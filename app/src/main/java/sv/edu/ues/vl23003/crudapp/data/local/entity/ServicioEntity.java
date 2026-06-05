package sv.edu.ues.vl23003.crudapp.data.local.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "servicios",
        foreignKeys = @ForeignKey(
                entity = ClienteEntity.class,
                parentColumns = "id",
                childColumns = "clienteId",
                onDelete = ForeignKey.RESTRICT
        )
)

public class ServicioEntity {

	@PrimaryKey(autoGenerate = true)
	public int id;

	// FK hacia clientes.id
	public int clienteId;

	public String tipo; // tipo de servicio

	public String estado; // estado (En proceso, Finalizado, etc.)

	public String descripcion;

	public double precio;

	public String fecha; // formato simple YYYY-MM-DD u otro

}
