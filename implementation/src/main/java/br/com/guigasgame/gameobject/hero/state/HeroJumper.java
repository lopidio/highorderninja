package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.side.Side;


public class HeroJumper
{

	private final HeroStatesProperties heroStatesProperties;
	private final GameHero gameHero;

	public HeroJumper(HeroStatesProperties heroStatesProperties, GameHero gameHero)
	{
		this.heroStatesProperties = heroStatesProperties;
		this.gameHero = gameHero;
	}

	protected boolean jump()
	{
		if (heroStatesProperties.canJump)
		{
			gameHero.applyImpulse(new Vec2(0, -heroStatesProperties.jumpImpulse));
			return true;
		}
		return false;
	}

	protected boolean doubleJump()
	{
		if (heroStatesProperties.canJump)
		{
			gameHero.applyImpulse(new Vec2(0, -heroStatesProperties.jumpImpulse
					/ 2));
			return true;
		}
		return false;
	}

	protected boolean diagonalJump(Side side)
	{
		if (heroStatesProperties.canJump)
		{
			Vec2 jumpDirection = new Vec2(0, -1);
			if (side == Side.LEFT)
			{
				jumpDirection.x = -1;
			}
			else // if (gameHero.getForwardSide() == Side.RIGHT)
			{
				jumpDirection.x = 1;
			}

			//Aponta para o local correto
			jumpDirection.normalize();
			jumpDirection.mulLocal(heroStatesProperties.jumpImpulse);

			gameHero.applyImpulse(jumpDirection);
			return true;
		}
		return false;
	}

}
