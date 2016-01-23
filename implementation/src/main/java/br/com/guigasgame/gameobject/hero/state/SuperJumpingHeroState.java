package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;


class SuperJumpingHeroState extends HeroState
{

	private boolean doubleJumpAllowed;
	protected SuperJumpingHeroState(GameHero gameHero)
	{
		super(gameHero, HeroAnimationsIndex.HERO_ASCENDING);
		doubleJumpAllowed = true;
	}

	@Override
	public void onEnter()
	{
		//Jump three times higher
		jump();
		jump();
	}
	
	@Override
	public void inputPressed(HeroInputKey key)
	{
		if (doubleJumpAllowed && key == HeroInputKey.JUMP)
		{
			if (heroStatesProperties.canJump && key == HeroInputKey.JUMP)
			{
				System.out.println("Super Double jump!");
				jump();
				doubleJumpAllowed = false;
			}
		}
	}	
	
	@Override
	public void updateState(float deltaTime)
	{
		gameHero.applyForce(new Vec2(0, -5));		

		if (gameHero.isFallingDown())
		{
			setState(new FallingHeroState(gameHero));
		}
	}
	
}
