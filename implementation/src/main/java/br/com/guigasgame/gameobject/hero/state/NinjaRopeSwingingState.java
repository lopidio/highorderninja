package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;
import org.jsfml.graphics.Color;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.action.SideOrientationHeroSetter;
import br.com.guigasgame.gameobject.hero.action.SwingFasterAction;
import br.com.guigasgame.gameobject.hero.action.SwingSlowerAction;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.gameobject.projectile.rope.NinjaRopeProjectile;
import br.com.guigasgame.side.Side;

public class NinjaRopeSwingingState extends HeroState
{
	private NinjaRopeProjectile ninjaRope;
	
	public NinjaRopeSwingingState(GameHero gameHero, NinjaRopeProjectile ninjaRopeProjectile)
	{
		super(gameHero, HeroStateIndex.HERO_ROPE);

		animation.setColor(Color.CYAN);
		this.ninjaRope = ninjaRopeProjectile; 
	}
	
	@Override
	protected void rope()
	{
		//do nothing
	}

	@Override
	protected void releaseRope()
	{
		super.releaseRope();
		if (gameHero.getCollidableHero().isAscending())
			setState(new JumpingHeroState(gameHero));
		else if (gameHero.getCollidableHero().isFallingDown())
			setState(new FallingHeroState(gameHero));
			
//		if (gameHero.getCollidableHero().isTouchingGround() && !gameHero.getCollidableHero().isFallingDown())
		{
			setState(new StandingHeroState(gameHero));
		}
//		else
//		{
//		}
		ninjaRope.markToDestroy();
	}
	
	
	@Override
	public void stateUpdate(float deltaTime)
	{
		Side currentSide = gameHero.getForwardSide();
		Side newSide = Side.LEFT;
		if (gameHero.getCollidableHero().getBodyLinearVelocity().x > 0)
		{
			newSide = Side.RIGHT;
		}
		if (gameHero.isTouchingGround())
			releaseRope();

		if (newSide != currentSide)
		{
//			gameHero.addAction(new SideOrientationHeroSetter(newSide, heroStatesProperties));
		}

	}
	
	@Override
	protected void move(Side side)
	{
		if (gameHero.getCollidable().getPosition().y > ninjaRope.getCollidable().getPosition().y)
		{
			Vec2 tangent = gameHero.getCollidable().getBody().getLinearVelocity().clone();
			
			if ((side == Side.RIGHT && tangent.x >= 0) || (side == Side.LEFT && tangent.x <= 0))
			{
				gameHero.addAction(new SwingFasterAction(heroStatesProperties, side));
			}
			else
			{
				gameHero.addAction(new SwingSlowerAction(heroStatesProperties));
			}
			
		}
		if (gameHero.getForwardSide() != side)
			gameHero.addAction(new SideOrientationHeroSetter(side, heroStatesProperties));
	}
	
	@Override
	protected void stateInputIsPressing(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.UP)
		{
			ninjaRope.shorten();
		}
		else if (inputValue == HeroInputKey.DOWN)
		{
			ninjaRope.increase();
		}
	}

}
