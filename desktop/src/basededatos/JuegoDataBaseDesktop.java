package basededatos;

import java.util.ArrayList;

public class JuegoDataBaseDesktop extends JuegoDataBase {


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

