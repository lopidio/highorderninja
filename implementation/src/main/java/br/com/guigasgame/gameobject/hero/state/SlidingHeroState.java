package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;


class SlidingHeroState extends HeroState
{

	private float secondsRemaining;

	protected SlidingHeroState(GameHero gameHero)
	{
		super(null, new Vec2(40, 10), true, true, true,
				HeroAnimationsIndex.HERO_SLIDING, gameHero, 50, 10);
		secondsRemaining = 0.5f;
	}

	@Override
	public void updateState(float deltaTime)
	{
		secondsRemaining -= deltaTime;
		moveForward();
		if (secondsRemaining <= 0)
		{
			setState(new StandingHeroState(gameHero));
		}
	}
	
	@Override
	public void inputPressed(HeroInputKey key)
	{
		if (key == HeroInputKey.JUMP)
		{
			setState(new JumpingHeroState(true, gameHero));
		}
	}
	
}
