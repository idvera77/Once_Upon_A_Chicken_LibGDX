package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.ivan.popollo_adventures.Juego;

import actores.Cuervo;
import actores.Sierra;
import objetos.GemaAzul;
import objetos.GemaRoja;
import objetos.Llave;
import objetos.Puerta;


public class Pantalla1 extends BaseScreen {

    public Pantalla1(Juego game) {
        super(game);
        this.fondo=new Texture("fondospantalla/bosque.png");
        this.sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/bosque.mp3"));
        sound.play(0.2f);
        //Añadimos los enemigos
        enemigoTerrestre = new Cuervo(Gdx.graphics.getWidth()+popollo.getX()*3, Gdx.graphics.getHeight()/23*4);
        pantalla.addActor(enemigoTerrestre);
        sierra = new Sierra(Gdx.graphics.getWidth()/31 * 7, Gdx.graphics.getWidth()/31 * 1 - popollo.getY()*5);
        pantalla.addActor(sierra);

        //Añadimos los objetos
        //Añado una llave
        llave = new Llave(Gdx.graphics.getWidth()/31 * 23, Gdx.graphics.getHeight()/ 23 * 11);
        pantalla.addActor(llave);

        //Añado las gemas
        gemaAzul = new GemaAzul(Gdx.graphics.getWidth()/31 * 26, Gdx.graphics.getHeight()/ 23 * 6);
        pantalla.addActor(gemaAzul);

        gemaRoja = new GemaRoja(Gdx.graphics.getWidth()/ 31 * 29, Gdx.graphics.getHeight()/ 23 * 6);
        pantalla.addActor(gemaRoja);

        //Añado la salida del nivel
        puerta = new Puerta(Gdx.graphics.getWidth()/31 * 19, Gdx.graphics.getHeight()/23 * 11);
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
                game.setPantallaActual(new Pantalla2(this.game));
            }
        }
    }

    @Override
    public void dispose() {
        sound.dispose();
    }

    public Sound getSound() {
        return sound;
    }
}
