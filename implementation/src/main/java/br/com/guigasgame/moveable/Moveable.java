package br.com.guigasgame.moveable;

import org.jsfml.system.Vector2f;

public interface Moveable
{
	public void move(Vector2f graphicPosition);
	public void setPosition(Vector2f graphicPosition);
	public Vector2f getPosition();
}
