package basededatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Timestamp;
import java.util.ArrayList;

public class JuegoDataBaseAndroid extends juegoDataBase {
    private Context contexto;

    public JuegoDataBaseAndroid(Context context) {
        this.contexto = context;
    }


    @Override
    public void empezarPartida() {
        MiOpenHelper moh = new MiOpenHelper(contexto);
        SQLiteDatabase db = moh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(juegoDataBase.getScoreFieldName(), 0);
        db.insert(juegoDataBase.getScoreTablename(), null, values);
    }

    @Override
    public void terminarPartida(int puntos) {
        MiOpenHelper moh = new MiOpenHelper(contexto);
        SQLiteDatabase db = moh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(juegoDataBase.getScoreFieldName(), puntos);
        db.update(juegoDataBase.getScoreTablename(), values,  juegoDataBase.getStartdateFieldName() + " = (SELECT MAX("+juegoDataBase.getStartdateFieldName()+") FROM " + juegoDataBase.getScoreTablename() + ");", null);
    }

    @Override
    public ArrayList<String> mejorPartida() {
        ArrayList<String> puntuaciones = new ArrayList<String>();
        MiOpenHelper moh = new MiOpenHelper(contexto);
        SQLiteDatabase db = moh.getWritableDatabase();
        Cursor resultadoConsulta = db.rawQuery("select * from " + juegoDataBase.getScoreTablename()
                + " Order by " + juegoDataBase.getScoreFieldName() + " desc limit 3;", null);
        if(resultadoConsulta.getCount() > 0){
            resultadoConsulta.moveToFirst();
            do {
                puntuaciones.add(resultadoConsulta.getString(resultadoConsulta.getColumnIndex(juegoDataBase.getScoreFieldName())));
            } while (resultadoConsulta.moveToNext());
        }
        return puntuaciones;
    }
}

