package sv.edu.ues.vl23003.crudapp.data.local.entity.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import sv.edu.ues.vl23003.crudapp.data.local.entity.ClienteEntity;

@Dao
public interface ClienteDao {

    @Insert
    void insertar(ClienteEntity cliente);

    @Update
    void actualizar(ClienteEntity cliente);

    @Delete
    void eliminar(ClienteEntity cliente);

    @Query("SELECT * FROM clientes WHERE id = :id")
    ClienteEntity obtenerPorId(int id);

    @Query("SELECT * FROM clientes ORDER BY nombre ASC")
    List<ClienteEntity> obtenerClientes();

    @Query("SELECT * FROM clientes WHERE " +
            "nombre LIKE '%' || :texto || '%' OR " +
            "telefono LIKE '%' || :texto || '%' OR " +
            "email LIKE '%' || :texto || '%' OR " +
            "direccion LIKE '%' || :texto || '%' OR " +
            "municipio LIKE '%' || :texto || '%' OR " +
            "notas LIKE '%' || :texto || '%' OR " +
            "CAST(id AS TEXT) LIKE '%' || :texto || '%' " +
            "ORDER BY nombre ASC")
    List<ClienteEntity> buscarCliente(String texto);
}