package br.com.guigasgame.moveable;

import org.jsfml.system.Vector2f;

public interface Moveable
{
	public void setPosition(Vector2f graphicPosition);
	public void move(Vector2f graphicPosition);
}
