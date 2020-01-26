package basededatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MiOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PopolloAdventure"; // Nombre
    private static final int DATABASE_VERSION = 1; // Version

    //Nombre de las tablas
    private static final String TABLE_JUGADOR = "jugador";

    //Nombre de las columnas en tablas
    private static final String NOMBRE = "nombre";
    private static final String PASSWORD = "password";
    private static final String VIDA = "vida";
    private static final String ATAQUE = "ataque";
    private static final String DEFENSA = "defensa";
    private static final String MANA = "mana";
    private static final String PUNTUACION = "puntuacion";
    private static final String KEY_ID = "id";

    // Tabla Jugador
    private static final String CREATE_TABLE_JUGADOR = "CREATE TABLE " + TABLE_JUGADOR + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOMBRE + " VARCHAR(30)," + PASSWORD + " VARCHAR(30)," +
            VIDA + " INTEGER," + MANA + " INTEGER," + ATAQUE + " INTEGER," + DEFENSA + " INTEGER," + PUNTUACION + " INTEGER);";

    public MiOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_JUGADOR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JUGADOR);
        onCreate(db);
    }

    public void guardarJugador(String nombre, String password) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO " + TABLE_JUGADOR + "("+ KEY_ID +", " + NOMBRE + ", " + PASSWORD +") VALUES ( null, '"+ nombre+"', '"+password+"')");
        db.close();
    }

    public Boolean nombreRepetido(String nombre){
        SQLiteDatabase db = getReadableDatabase();
        int encontrado = 0;
        Cursor cursor = db.rawQuery("SELECT " + NOMBRE + " FROM " + TABLE_JUGADOR + ";" , null);
        while (cursor.moveToNext()){
            if(cursor.getString(0).equals(nombre)){
                encontrado = 1;
            }
        }
        cursor.close();
        db.close();
        if (encontrado == 0){
            return false;
        }else{
            return true;
        }
    }
}