package objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

public class Objeto extends Actor {
    protected Sprite sprite;

    public Rectangle getHitBox() {
        return sprite.getBoundingRectangle();
    }
    /**
     * Constructor con todos los parámetros de objeto
     * @param rutaTextura la textura que usamos
     * @param x posicion x inicial
     * @param y posicion y inicial
     * @param w anchura inicial
     * @param h altura inicial
     */
    public Objeto(String rutaTextura, float x, float y, float w, float h) {
        sprite = new Sprite(new Texture(rutaTextura));
        sprite.setBounds(sprite.getX(), sprite.getY(), w, h);
        this.setSize(w, h);
        this.setPosition(x, y);
        this.setOrigin(this.sprite.getWidth() / 2, this.sprite.getHeight() / 2);
        sprite.setOrigin(this.getOriginX(), this.getOriginY());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.setPosition(getX(), getY());
        sprite.setScale(this.getScaleX(), getScaleY());
        sprite.setRotation(this.getRotation());
        sprite.setColor(getColor().r, getColor().g, getColor().b, getColor().a);
        sprite.draw(batch);
    }

    public void moverA(float x, float y) {
        this.setPosition(x, y);
        sprite.setPosition(x, y);
    }

    public void reduce() {
        this.scaleBy(-0.5f);
        sprite.scale(-0.5f);
    }


    public void ataqueDisparo() {
        MoveByAction moveRightAction = new MoveByAction();
        moveRightAction.setAmount(1000, 0);
        moveRightAction.setDuration(1);
        addAction(moveRightAction);
    }


}
