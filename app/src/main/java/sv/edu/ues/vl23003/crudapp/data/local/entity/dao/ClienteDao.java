package sv.edu.ues.vl23003.crudapp.data.local.entity.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import sv.edu.ues.vl23003.crudapp.data.local.entity.ClienteEntity;

@Dao
public interface ClienteDao {
    @Insert
    void insert(ClienteEntity cliente);

    @Query("SELECT * FROM clientes")
    List<ClienteEntity> getAll();

    @Query("SELECT * FROM clientes WHERE id = :id")
    ClienteEntity getById(int id);
}
