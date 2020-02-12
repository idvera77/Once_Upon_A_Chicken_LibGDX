package escuchadores;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import actores.Personaje;

public class EscuchadorJugador extends InputListener {
    Personaje jugador;

    public EscuchadorJugador(Personaje j) {
        this.jugador = j;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                jugador.startMoving(0);
                break;
            case Input.Keys.D:
                jugador.startMoving(1);
                break;
            case Input.Keys.SPACE:
                jugador.salto();
                break;
        }
        return super.keyDown(event, keycode);
    }

    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                jugador.stopMoving(0);
                break;
            case Input.Keys.D:
                jugador.stopMoving(1);
                break;
        }
        return super.keyUp(event, keycode);
    }
}
