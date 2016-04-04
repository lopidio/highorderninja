package br.com.guigasgame.gameobject.hero.playable;


public interface HeroDeathsListener
{
	void playerHasDied(PlayableGameHero gameHero);
	void playerHasRespawn(PlayableGameHero gameHero);
}
