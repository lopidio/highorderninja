package br.com.guigasgame.moveable;

import org.jsfml.system.Vector2f;

public interface Moveable
{
	public void addPosition(Vector2f graphicPosition);
	public void setOrigin(Vector2f graphicPosition);
	public Vector2f getPosition();
}
