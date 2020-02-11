package actores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Controles extends Actor {

    public Controles(){
        BitmapFont fuente = new BitmapFont();
        TextButton.TextButtonStyle estiloBoton = new TextButton.TextButtonStyle();
        estiloBoton.font = fuente;
        estiloBoton.over=new TextureRegionDrawable(new Texture("personajes/polloderecha.png"));
        estiloBoton.up=new TextureRegionDrawable(new Texture("personajes/polloderecha.png"));
        TextButton boton1 = new TextButton("This is a button!!!", estiloBoton);
        boton1.setSize(500,500);
        boton1.setPosition(0, 60);
    }


}
