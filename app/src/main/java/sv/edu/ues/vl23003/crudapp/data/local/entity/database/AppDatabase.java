package sv.edu.ues.vl23003.crudapp.data.local.entity.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import sv.edu.ues.vl23003.crudapp.data.local.entity.ClienteEntity;
import sv.edu.ues.vl23003.crudapp.data.local.entity.ServicioEntity;
import sv.edu.ues.vl23003.crudapp.data.local.entity.dao.ClienteDao;
import sv.edu.ues.vl23003.crudapp.data.local.entity.dao.ServicioDao;


@Database(entities = {ClienteEntity.class, ServicioEntity.class}, version = 2, exportSchema = false)
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
                            .addCallback(SEED_CALLBACK)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback SEED_CALLBACK = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            db.execSQL("INSERT INTO clientes (nombre, direccion, telefono) VALUES ('Juan Perez', 'San Salvador', '7777-1234')");
        }
    };
}