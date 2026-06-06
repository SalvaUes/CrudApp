package sv.edu.ues.vl23003.crudapp.data.local.entity.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import sv.edu.ues.vl23003.crudapp.data.local.entity.ServicioEntity;

@Dao
public interface ServicioDao {
    @Insert
    void insert(ServicioEntity servicio);

    @Query("SELECT * FROM servicios")
    List<ServicioEntity> getAll();

    @Query("SELECT * FROM servicios WHERE id = :id")
    ServicioEntity getById(int id);

    @Delete
    void delete(ServicioEntity servicio);

    @Query("DELETE FROM servicios WHERE id = :id")
    void deleteById(int id);
}
