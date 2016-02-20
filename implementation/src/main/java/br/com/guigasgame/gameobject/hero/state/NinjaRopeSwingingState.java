package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;
import org.jsfml.graphics.Color;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.action.SideOrientationHeroSetter;
import br.com.guigasgame.gameobject.hero.action.SwingFasterAction;
import br.com.guigasgame.gameobject.hero.action.SwingSlowerAction;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.gameobject.projectile.NinjaRope;
import br.com.guigasgame.side.Side;

public class NinjaRopeSwingingState extends HeroState
{
	private NinjaRope ninjaRope;
	
	public NinjaRopeSwingingState(GameHero gameHero, NinjaRope ninjaRope)
	{
		super(gameHero, HeroStateIndex.HERO_ROPE);

		animation.setColor(Color.CYAN);
		this.ninjaRope = ninjaRope; 
	}
	
	@Override
	protected void rope()
	{
		//do nothing
	}
	
	private void ropeRelease()
	{
		if (gameHero.getCollidableHero().isTouchingGround() && !gameHero.getCollidableHero().isFallingDown())
		{
			setState(new StandingHeroState(gameHero));
		}
		else
		{
			setState(new JumpingHeroState(gameHero));
		}
		ninjaRope.markToDestroy();
	}
	
	@Override
	protected void stateInputReleased(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.ROPE)
		{
			ropeRelease();
		}
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
		if (newSide != currentSide)
		{
			gameHero.addAction(new SideOrientationHeroSetter(newSide, heroStatesProperties));
		}
		
		if (!getInputMap().get(HeroInputKey.ROPE))
		{
			ropeRelease();
		}
	}
	
	@Override
	protected void move(Side side)
	{
		if (gameHero.getCollidable().getPosition().y > ninjaRope.getCollidable().getPosition().y)
		{
			Vec2 tangent = gameHero.getCollidable().getBody().getLinearVelocity().clone();
			
			if ((side == Side.RIGHT && tangent.x > 0) || (side == Side.LEFT && tangent.x < 0))
			{
				gameHero.addAction(new SwingFasterAction(heroStatesProperties));
			}
			else
			{
				gameHero.addAction(new SwingSlowerAction(heroStatesProperties));
			}
			
		}
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
