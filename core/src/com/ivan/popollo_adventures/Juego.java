package com.ivan.popollo_adventures;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import pantallas.BaseScreen;
import pantallas.Pantalla1;

public class Juego extends Game {
	private BaseScreen pantallaActual;

	@Override
	public void create () {
		this.setPantallaActual(new Pantalla1(this));
	}

	public void setPantallaActual(BaseScreen pa){
		pantallaActual=pa;
		setScreen(pantallaActual);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		pantallaActual.render(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {
		pantallaActual.dispose();
	}
}
