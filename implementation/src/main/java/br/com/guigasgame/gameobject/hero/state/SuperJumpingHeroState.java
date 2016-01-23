package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;


class SuperJumpingHeroState extends HeroState
{

	protected SuperJumpingHeroState(GameHero gameHero)
	{
		super(null, new Vec2(20, 10), true, true, true, HeroAnimationsIndex.HERO_ASCENDING,
				gameHero, 2, 30);
	}

	@Override
	public void onEnter()
	{
		//Jump three times higher
		jump();
		jump();
		jump();
	}
	
	@Override
	public void inputPressed(HeroInputKey key)
	{
		if (key == HeroInputKey.JUMP)
		{
			if (canJump && key == HeroInputKey.JUMP)
			{
				System.out.println("Super Double jump!");
				jump();
				canJump = false;
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
