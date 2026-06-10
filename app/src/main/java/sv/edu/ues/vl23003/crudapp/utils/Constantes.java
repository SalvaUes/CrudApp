package sv.edu.ues.vl23003.crudapp.utils;

public final class Constantes {

    private Constantes() {
    }

    public static final String DB_NAME = "clientes_db";
    public static final String TABLA_CLIENTES = "clientes";
    public static final String TABLA_SERVICIOS = "servicios";
    public static final String EXTRA_ID_CLIENTE = "idCliente";
    public static final String EXTRA_ID_SERVICIO = "idServicio";

    public static final String[] ESTADOS = {"Pendiente", "En proceso", "Completado", "Cancelado"};
    public static final String[] TIPOS_SERVICIO = {"Mantenimiento", "Reparacion", "Instalacion", "Consultoria", "Otro"};
}