package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.side.Side;


public class WallGrabHeroState extends HeroState
{

	private Side wallSide;

	protected WallGrabHeroState(GameHero gameHero)
	{
		super(null, new Vec2(0, 2), true, true, false,
				HeroAnimationsIndex.HERO_WALLGRABBING, gameHero, 400, 10);
		wallSide = gameHero.getForwardSide();
	}

	@Override
	public void updateState(float deltaTime)
	{
		if (gameHero.isTouchingGround() && !gameHero.isFallingDown())
		{
			setState(new StandingHeroState(gameHero));
		}
	}

	@Override
	public void inputPressed(HeroInputKey key)
	{
		if (key == HeroInputKey.JUMP)
		{
			moveBackward();

			setState(new JumpingHeroState(true, gameHero));
		}
		if (wallSide == Side.LEFT && key == HeroInputKey.RIGHT)
		{
			setState(new FallingHeroState(gameHero));
		}
		if (wallSide == Side.RIGHT && key == HeroInputKey.LEFT)
		{
			setState(new FallingHeroState(gameHero));
		}
	}

}
