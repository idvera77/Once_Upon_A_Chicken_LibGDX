package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.ivan.popollo_adventures.Juego;

import actores.Popollo;


public class Pantalla1 extends BaseScreen {
    private Sound sound;

    public Pantalla1(Juego game) {
        super(game);
        this.fondo=new Texture("fondospantalla/bosque.png");
        sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/bosque.mp3"));
        sound.play(0.2f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (popollo.checkCollision(puerta)) {
            if(popollo.getObjetos().size()>= 1){
                puerta.getSound().play(1f);
                sound.stop();
                game.setPantallaActual(new Pantalla2(this.game));
            }
        }
    }

    @Override
    public void dispose() {
        sound.dispose();
    }
}
