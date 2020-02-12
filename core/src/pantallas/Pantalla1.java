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

        //Añadimos los enemigos.
        enemigoTerrestre = new Cuervo(Gdx.graphics.getWidth()+popollo.getX()*3, Gdx.graphics.getHeight()/23*4);
        pantalla.addActor(enemigoTerrestre);
        sierra = new Sierra(Gdx.graphics.getWidth()/31 * 7, Gdx.graphics.getWidth()/31 * 1 - popollo.getY()*5);
        pantalla.addActor(sierra);

        //Añadimos la llave
        llave = new Llave(Gdx.graphics.getWidth()/31 * 28, Gdx.graphics.getHeight()/ 23 * 6);
        pantalla.addActor(llave);

        //Añadimos las gemas
        gemaAzul = new GemaAzul(Gdx.graphics.getWidth()/31 * 4, Gdx.graphics.getHeight()/ 23 * 12);
        pantalla.addActor(gemaAzul);

        gemaRoja = new GemaRoja(Gdx.graphics.getWidth()/ 31 * 13, Gdx.graphics.getHeight()/ 23 * 12);
        pantalla.addActor(gemaRoja);

        //Añadimos la salida del nivel y la enviamos atras para que los personajes sean visibles al pasar por ella.
        puerta = new Puerta(Gdx.graphics.getWidth()/31 * 19, Gdx.graphics.getHeight()/23 * 11);
        pantalla.addActor(puerta);
        puerta.toBack();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        //Si el popollo tiene en su inventario un objeto (solo podemos guardar la llave) se completa
        // y nos movemos a la siguiente pantalla
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
