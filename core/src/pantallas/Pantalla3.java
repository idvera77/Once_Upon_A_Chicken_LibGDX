package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.ivan.popollo_adventures.Juego;

import java.util.Random;

import actores.Cuervo;
import actores.Sierra;
import objetos.GemaAzul;
import objetos.GemaRoja;
import objetos.Llave;
import objetos.Puerta;


public class Pantalla3 extends BaseScreen {
    private Sound sound;

    public Pantalla3(Juego game) {
        super(game);
        this.fondo=new Texture("fondospantalla/cementerio.png");
        sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/cementerio.mp3"));
        sound.play(0.2f);

        //Añadimos los enemigos
        enemigoTerrestre = new Cuervo(Gdx.graphics.getWidth()+popollo.getX()*3, Gdx.graphics.getHeight()/5);
        pantalla.addActor(enemigoTerrestre);
        sierra = new Sierra(Gdx.graphics.getWidth()/10 * 2, 0 - popollo.getY()*3);
        pantalla.addActor(sierra);

        //Añadimos los objetos
        //Añado una llave
        Random r = new Random();
        //float posXLlave = (float) r.nextInt(Gdx.graphics.getWidth() / 10 * 9);
        //float posYLlave = (float) r.nextInt(Gdx.graphics.getHeight() / 10 * 9);
        llave = new Llave(Gdx.graphics.getWidth()/10 * 1, Gdx.graphics.getHeight()/ 10 * 5);
        pantalla.addActor(llave);

        //Añado las gemas
        //float posXAzul = (float) r.nextInt(Gdx.graphics.getWidth() / 10 * 9);
        //float posYAzul = (float) r.nextInt(Gdx.graphics.getHeight() / 10 * 9);
        gemaAzul = new GemaAzul(Gdx.graphics.getWidth()/10 * 9, Gdx.graphics.getHeight()/ 10 * 7);
        pantalla.addActor(gemaAzul);

        //float posXRoja = (float) r.nextInt(Gdx.graphics.getWidth() / 10 * 9);
        //float posYRoja = (float) r.nextInt(Gdx.graphics.getHeight() / 10 * 9);
        gemaRoja = new GemaRoja(Gdx.graphics.getWidth()/ 10 * 7, Gdx.graphics.getHeight()/ 10 * 5);
        pantalla.addActor(gemaRoja);

        //Añado la salida del nivel
        puerta = new Puerta(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/5);
        pantalla.addActor(puerta);
        puerta.toBack();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (popollo.checkCollision(puerta)) {
            if(popollo.getObjetos().size()>= 1){
                puerta.getSound().play(1f);
                sound.stop();
                game.setPantallaActual(new Pantalla1(this.game));
            }
        }
    }

    public Sound getSound() {
        return sound;
    }

    @Override
    public void dispose() {
        sound.dispose();
    }
}
