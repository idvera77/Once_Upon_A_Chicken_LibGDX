package escuchadores;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import actores.Personaje;
import actores.Popollo;

public class EscuchadorJugador extends InputListener {
    Popollo heroe;

    public EscuchadorJugador(Popollo heroe) {
        this.heroe = heroe;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                heroe.startMoving(0);
                break;
            case Input.Keys.D:
                heroe.startMoving(1);
                break;
            case Input.Keys.SPACE:
                heroe.salto();
                break;
        }
        return super.keyDown(event, keycode);
    }

    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                heroe.stopMoving(0);
                break;
            case Input.Keys.D:
                heroe.stopMoving(1);
                break;
        }
        return super.keyUp(event, keycode);
    }
}
