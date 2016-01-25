package br.com.guigasgame.gameobject.hero.executor;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStatesProperties;
import br.com.guigasgame.side.Side;


public class HeroJumperExecutor
{
	private final HeroStatesProperties heroStatesProperties;
	private final GameHero gameHero;

	public HeroJumperExecutor(HeroStatesProperties heroStatesProperties, GameHero gameHero)
	{
		this.heroStatesProperties = heroStatesProperties;
		this.gameHero = gameHero;
	}

	public boolean jump()
	{
		if (heroStatesProperties.canJump)
		{
			gameHero.applyImpulse(new Vec2(0, -heroStatesProperties.jumpImpulse));
			return true;
		}
		return false;
	}

	public boolean doubleJump()
	{
		if (heroStatesProperties.canJump)
		{
			gameHero.applyImpulse(new Vec2(0, -heroStatesProperties.jumpImpulse / 2));
			return true;
		}
		return false;
	}

	public boolean diagonalJump(Side side)
	{
		if (heroStatesProperties.canJump)
		{
			Vec2 jumpDirection = new Vec2(side.getHorizontalValue(), -1);
			jumpDirection.normalize();
			// Aponta para o local correto
			jumpDirection.mulLocal(heroStatesProperties.jumpImpulse);

			gameHero.applyImpulse(jumpDirection);
			return true;
		}
		return false;
	}

}
