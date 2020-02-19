package basededatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class JuegoDataBaseAndroid extends JuegoDataBase {
    private Context contexto;

    public JuegoDataBaseAndroid(Context context) {
        this.contexto = context;
    }

    /**
     * Al iniciar una partida se inserta en la base de datos un nuevo registro con la fecha actual y los puntos a 0.
     */
    @Override
    public void empezarPartida() {
        MiOpenHelper moh = new MiOpenHelper(contexto);
        SQLiteDatabase db = moh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(JuegoDataBase.getScoreFieldName(), 0);
        db.insert(JuegoDataBase.getScoreTablename(), null, values);
    }

    /**
     * Se hace un update de la ultima partida utilizada para añadir la nueva puntuacion
     * @param puntos variable de tipo entero que se añaden con el update de puntos
     */
    @Override
    public void terminarPartida(int puntos) {
        MiOpenHelper moh = new MiOpenHelper(contexto);
        SQLiteDatabase db = moh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(JuegoDataBase.getScoreFieldName(), puntos);
        db.update(JuegoDataBase.getScoreTablename(), values,  JuegoDataBase.getStartdateFieldName() + " = (SELECT MAX("+ JuegoDataBase.getStartdateFieldName()+") FROM " + JuegoDataBase.getScoreTablename() + ");", null);
    }

    /**
     * Busca en la base de datos las tres mejores puntuaciones
     * @return ArrayList con las tres primeras posiciones por puntos
     */
    @Override
    public ArrayList<String> mejorPartida() {
        ArrayList<String> puntuaciones = new ArrayList<String>();
        MiOpenHelper moh = new MiOpenHelper(contexto);
        SQLiteDatabase db = moh.getWritableDatabase();
        Cursor resultadoConsulta = db.rawQuery("select * from " + JuegoDataBase.getScoreTablename()
                + " Order by " + JuegoDataBase.getScoreFieldName() + " desc limit 3;", null);
        if(resultadoConsulta.getCount() > 0){
            resultadoConsulta.moveToFirst();
            do {
                puntuaciones.add(resultadoConsulta.getString(resultadoConsulta.getColumnIndex(JuegoDataBase.getScoreFieldName())));
            } while (resultadoConsulta.moveToNext());
        }
        return puntuaciones;
    }
}

