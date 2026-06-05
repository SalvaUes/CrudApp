package sv.edu.ues.vl23003.crudapp.data.local.entity.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import sv.edu.ues.vl23003.crudapp.data.local.entity.ClienteEntity;
import sv.edu.ues.vl23003.crudapp.data.local.entity.dao.ClienteDao;

@Database(entities = {ClienteEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ClienteDao clienteDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context){

        if(INSTANCE == null){

            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "clientes_db"
            ).allowMainThreadQueries().build();

        }

        return INSTANCE;
    }
}