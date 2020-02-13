package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ivan.popollo_adventures.Juego;

import java.util.Random;

import actores.Cuervo;
import actores.Sierra;
import objetos.GemaAzul;
import objetos.GemaRoja;
import objetos.Llave;
import objetos.Pincho;
import objetos.Puerta;


public class Tienda extends BaseScreen {
    private int seleccion;
    private Texture texturaObjeto;
    private TextureRegion texturaRegion;
    private TextureRegion[] framesTextura;
    private Animation animacionObjeto;
    private float duracion = 0f;
    private static final int ANCHO = Gdx.graphics.getWidth() / 5;
    private static final int ALTO = Gdx.graphics.getHeight() / 9;

    public Tienda(Juego game, int seleccion) {
        super(game);
        this.seleccion = seleccion;
        this.fondo = new Texture("fondospantalla/tienda.png");
        this.sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/cementerio.mp3"));
        sound.play(0.2f);

        //Añadimos los enemigos.
        enemigoTerrestre = new Cuervo(0,0);
        sierra = new Sierra(0, 0);
        pincho = new Pincho(0, 0);

        //Añadimos la llave
        llave = new Llave(Gdx.graphics.getWidth() / 31 * 7, Gdx.graphics.getHeight() / 23 * 5);
        pantalla.addActor(llave);

        //Añadimos las gemas
        gemaAzul = new GemaAzul(0,0);
        gemaRoja = new GemaRoja(0,0);

        //Añadimos la salida del nivel y la enviamos atras para que los personajes sean visibles al pasar por ella.
        puerta = new Puerta(Gdx.graphics.getWidth() / 31 * 27, Gdx.graphics.getHeight() / 23 * 5);
        pantalla.addActor(puerta);
    }

    @Override
    public void show() {
        texturaObjeto = new Texture("objetos/animacionpuerta.png");
        texturaRegion = new TextureRegion(texturaObjeto, ANCHO, ALTO);
        TextureRegion[][] temp = texturaRegion.split(ANCHO / 3, ALTO);
        framesTextura = new TextureRegion[temp.length * temp[0].length];

        int indice = 0;
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                framesTextura[indice++] = temp[i][j];
            }
        }
        animacionObjeto = new Animation(0.2f, framesTextura);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (popollo.checkCollision(puerta)) {
            if (popollo.getObjetos().size() >= 1) {
                puerta.getSound().play(1f);
                sound.stop();
                if(seleccion == 3){
                    game.setPantallaActual(new Pantalla1(this.game));
                }else if(seleccion == 1){
                    game.setPantallaActual(new Pantalla2(this.game));
                }else if(seleccion == 2){
                    game.setPantallaActual(new Pantalla3(this.game));
                }
            }
        }
        duracion += delta;
        TextureRegion frame = (TextureRegion) animacionObjeto.getKeyFrame(duracion, true);
        pantalla.getBatch().begin();
        pantalla.getBatch().draw(frame, Gdx.graphics.getWidth() / 50 * 43, Gdx.graphics.getHeight() / 23 * 7);//posicion de la animación
        pantalla.getBatch().end();
    }

    public Sound getSound() {
        return sound;
    }

    @Override
    public void dispose() {
        sound.dispose();
    }
}
