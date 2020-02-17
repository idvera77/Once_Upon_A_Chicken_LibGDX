package basededatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MiOpenHelper extends SQLiteOpenHelper {

    public MiOpenHelper(Context context) {
        super(context, JuegoDataBaseAndroid.getDatabaseName(), null, JuegoDataBaseAndroid.getDatabaseVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(JuegoDataBaseAndroid.getDatabaseCreationQuery());
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(JuegoDataBaseAndroid.getDatabaseUpdateQuery());
    }
}

