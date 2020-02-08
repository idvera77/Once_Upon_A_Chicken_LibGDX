package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.ivan.popollo_adventures.Juego;


public class Pantalla2 extends BaseScreen {
    private Sound sound;

    public Pantalla2(Juego game) {
        super(game);
        this.fondo=new Texture("fondospantalla/montaña.png");
        sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/nieve.mp3"));
        sound.play(0.5f);
    }

    @Override
    public void dispose() {
        sound.dispose();
    }
}
