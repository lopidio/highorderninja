package br.com.guigasgame.collision;

public interface FixtureContactListener {
	public void endContact(Collidable collidable);
	public void beginContact(Collidable collidable);
}
