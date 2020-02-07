package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.ivan.popollo_adventures.Juego;

import actores.Popollo;


public class Pantalla1 extends BaseScreen {

    public Pantalla1(Juego game) {
        super(game);
        this.fondo=new Texture("fondospantalla/bosque.png");

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Popollo popollo = (Popollo) pantalla.getActors().get(0);
        if(popollo.getY()<0){
            popollo.moverAPixel(popollo.getX(),0);
        }else if(popollo.getY()>= Gdx.graphics.getHeight()){
            popollo.moverAPixel(popollo.getX(),Gdx.graphics.getHeight());
        }else if(popollo.getX()>=Gdx.graphics.getWidth()){
            game.setPantallaActual(new Pantalla2(this.game));
        }else if(popollo.getX()<0){
            game.setPantallaActual(new Pantalla3(this.game));
        }
    }
}
