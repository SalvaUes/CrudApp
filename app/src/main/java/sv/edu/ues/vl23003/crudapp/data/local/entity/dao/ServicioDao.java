package sv.edu.ues.vl23003.crudapp.data.local.entity.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import sv.edu.ues.vl23003.crudapp.data.local.entity.ServicioEntity;

@Dao
public interface ServicioDao {

    @Insert
    void insertar(ServicioEntity servicio);

    @Update
    void actualizar(ServicioEntity servicio);

    @Delete
    void eliminar(ServicioEntity servicio);

    @Query("SELECT * FROM servicios WHERE id = :id")
    ServicioEntity obtenerPorId(int id);

    @Query("SELECT * FROM servicios ORDER BY fecha DESC")
    List<ServicioEntity> obtenerServicios();

    @Query("SELECT * FROM servicios WHERE clienteId = :clienteId ORDER BY fecha DESC")
    List<ServicioEntity> obtenerServiciosPorCliente(int clienteId);

    @Query("SELECT COUNT(*) FROM servicios WHERE clienteId = :clienteId")
    int contarPorCliente(int clienteId);
}
