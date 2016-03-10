package br.com.guigasgame.gameobject.hero.attributes.playable;

public interface HeroRoundAttributesListener 
{
	public void lifeChanged(int current, int max);
	public void shurikenNumChanged(int current, int max);
	public void smokeBombChanged(int current, int max);
}
