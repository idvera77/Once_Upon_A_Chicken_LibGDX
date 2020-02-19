package basededatos;

import java.util.ArrayList;

public abstract class JuegoDataBase {
    private static  int databaseVersion;
    private static  String databaseName;
    private static  String SCORE_TABLENAME;
    private static  String SCORE_FIELD ;
    private static  String STARTDATE_FIELD;
    private static  String databaseCreationQuery;
    private static  String databaseUpdateQuery;

    public abstract void empezarPartida();
    public abstract void terminarPartida(int puntos);
    public abstract ArrayList<String> mejorPartida();

    public JuegoDataBase(){
        databaseVersion=1;
        databaseName="OnceUponAChicken";
        SCORE_TABLENAME="totalScore";
        SCORE_FIELD ="score";
        STARTDATE_FIELD="gameStartDate";
        databaseCreationQuery="CREATE TABLE "+SCORE_TABLENAME+" (" +
                STARTDATE_FIELD +" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                SCORE_FIELD + " INT NOT NULL);";
        databaseUpdateQuery="";
    }

    public static int getDatabaseVersion(){
        return databaseVersion;
    }
    public static String getScoreFieldName(){
        return SCORE_FIELD;
    }
    public static String getStartdateFieldName(){
        return STARTDATE_FIELD;
    }

    public static String getDatabaseName(){
        return databaseName;
    }
    public static String getDatabaseCreationQuery(){
        return databaseCreationQuery;
    }
    public static String getDatabaseUpdateQuery(){
        return databaseUpdateQuery;
    }
    public static String getScoreTablename(){
        return SCORE_TABLENAME;
    }
}


