package sv.edu.ues.vl23003.crudapp.data.local.entity.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import sv.edu.ues.vl23003.crudapp.data.local.entity.ClienteEntity;
import sv.edu.ues.vl23003.crudapp.data.local.entity.ServicioEntity;
import sv.edu.ues.vl23003.crudapp.data.local.entity.dao.ClienteDao;
import sv.edu.ues.vl23003.crudapp.data.local.entity.dao.ServicioDao;


@Database(entities = {ClienteEntity.class, ServicioEntity.class}, version = 2, exportSchema = false)// buena practica para evitar advertencias de Room si no esta exportando esquemas.
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract ClienteDao clienteDao();

    public abstract ServicioDao servicioDao();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "clientes_db"
                            )
                            .fallbackToDestructiveMigration(true)
                            .allowMainThreadQueries()

                            .build();
                }
            }
        }
        return INSTANCE;
    }
}