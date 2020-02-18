package basededatos;

import java.sql.Timestamp;
import java.util.ArrayList;

public class juegoDataBaseDesktop extends juegoDataBase {


    @Override
    public void empezarPartida() {

    }

    @Override
    public void terminarPartida(int puntos) {

    }

    @Override
    public ArrayList<String> mejorPartida() {
        ArrayList<String> hola = new ArrayList<String>();
        hola.add("Hola, soy un mensaje oculto");
        return hola;
    }
}

