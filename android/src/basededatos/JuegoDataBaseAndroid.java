package basededatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Timestamp;

public class JuegoDataBaseAndroid extends juegoDataBase {
    private Context contexto;

    public JuegoDataBaseAndroid(Context context) {
        this.contexto = context;
    }

    @Override
    public void empezarPartida(int puntuacion) {
        MiOpenHelper moh = new MiOpenHelper(contexto);
        SQLiteDatabase db = moh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(juegoDataBase.getScoreFieldName(), puntuacion);
        db.insert(juegoDataBase.getScoreTablename(), null, values);
    }

    @Override
    public void terminarPartida(int puntuacion, Timestamp tiempoFinal) {
        MiOpenHelper moh = new MiOpenHelper(contexto);
        SQLiteDatabase db = moh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(juegoDataBase.getScoreFieldName(), puntuacion);
        values.put(juegoDataBase.getEnddateFieldName(), 0);
        db.update(juegoDataBase.getScoreTablename(), values,  juegoDataBase.getStartdateFieldName() + " = (SELECT MAX("+juegoDataBase.getStartdateFieldName()+") FROM " + juegoDataBase.getScoreTablename() + ");", null);
    }

    @Override
    public void mejorPartida() {
        MiOpenHelper moh = new MiOpenHelper(contexto);
        SQLiteDatabase db = moh.getWritableDatabase();
        Cursor resultadoConsulta = db.rawQuery("select * from " + juegoDataBase.getScoreTablename()
                + " Order by " + juegoDataBase.getScoreFieldName() + " desc;", null);
        resultadoConsulta.moveToFirst();
        String hola = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(juegoDataBase.getScoreFieldName()));
        String tiempo = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(juegoDataBase.getStartdateFieldName()));
        System.out.println(hola);

    }

    /**
     *
     *

     @Override
     public Puntuacion getCurrentGame() {
     return new Puntuacion(10, new Timestamp(System.currentTimeMillis()), null);
     }

      *
     *
     * @Override
     *     public void saveCurrentGame(int score) {
     *         MiOpenHelper moh = new MiOpenHelper(contexto);
     *         SQLiteDatabase db = moh.getWritableDatabase();
     *         ContentValues cv=new ContentValues();
     *         cv.put(juegoDataBase.getScoreFieldName(),score);
     *         db.insert(juegoDataBase.getScoreTablename(),null,cv);
     *     }
     *
     *     @Override
     *     public void endCurrentGame(int score) {
     *         MiOpenHelper moh = new MiOpenHelper(contexto);
     *         SQLiteDatabase db = moh.getWritableDatabase();
     *         Cursor resultadoConsulta = db.query(juegoDataBase.getScoreTablename(), null, null, null, null, null, null, null);
     *         resultadoConsulta.moveToFirst();
     *         String hola = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(juegoDataBase.getScoreFieldName()));
     *         System.out.println(hola);
     *     }
     *
     *     @Override
     *     public juegoDataBase[] getTop3Games() {
     *         return new juegoDataBase[0];
     *     }
     */
}

