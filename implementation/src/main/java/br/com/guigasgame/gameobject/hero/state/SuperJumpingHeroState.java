package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;


class SuperJumpingHeroState extends OnAirHeroState
{

	protected SuperJumpingHeroState(GameHero gameHero)
	{
		super(true, HeroAnimationsIndex.HERO_ASCENDING, gameHero, 20);
	}

	@Override
	public void onEnter()
	{
		//Jump three times higher
		jump();
		jump();
		jump();
	}
	
	public void updateState(float deltaTime) {
		super.updateState(deltaTime);
		gameHero.applyForce(new Vec2(0, -5));		
	};
	
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
	
}
