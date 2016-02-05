package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;


class JumpingHeroState extends HeroState
{

	private boolean doubleJumpAllowed;

	protected JumpingHeroState(GameHero gameHero)
	{
		super(gameHero, HeroAnimationsIndex.HERO_ASCENDING);
		doubleJumpAllowed = true;
	}

	@Override
	protected void jump()
	{
		if (doubleJumpAllowed)
		{
			super.jump();
			doubleJumpAllowed = false;
		}
	}


	@Override
	public void stateUpdate(float deltaTime)
	{
		if (gameHero.getCollidableHero().isFallingDown())
		{
			setState(new FallingHeroState(gameHero));
		}
	}

}
