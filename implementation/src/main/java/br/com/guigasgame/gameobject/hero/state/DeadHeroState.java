package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;

class DeadHeroState extends HeroState
{

	protected DeadHeroState(PlayableGameHero gameHero)
	{
		super(gameHero, HeroStateIndex.HERO_DEAD);
	}
	
	@Override
	protected void stateOnEnter()
	{
		// TODO Auto-generated method stub
		super.stateOnEnter();
	}

}
