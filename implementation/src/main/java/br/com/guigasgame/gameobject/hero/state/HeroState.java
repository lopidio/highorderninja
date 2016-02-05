package br.com.guigasgame.gameobject.hero.state;

import java.util.HashMap;
import java.util.Map;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationsCentralPool;
import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.action.HeroStateSetterAction;
import br.com.guigasgame.gameobject.hero.action.JumpAction;
import br.com.guigasgame.gameobject.hero.action.MoveHeroAction;
import br.com.guigasgame.gameobject.hero.action.ShootAction;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.gameobject.projectile.Shuriken;
import br.com.guigasgame.input.InputListener;
import br.com.guigasgame.math.Vector2;
import br.com.guigasgame.side.Side;
import br.com.guigasgame.updatable.UpdatableFromTime;


public abstract class HeroState implements InputListener<HeroInputKey>,
		UpdatableFromTime
{

	protected final GameHero gameHero;
	protected final HeroStateProperties heroStatesProperties;
	private Map<HeroInputKey, Boolean> inputMap;

	protected HeroState(GameHero gameHero, HeroAnimationsIndex heroAnimationsIndex)
	{
		super();
		this.heroStatesProperties = HeroStatesPropertiesPool.getStateProperties(heroAnimationsIndex);
		gameHero.setAnimation(Animation.createAnimation(AnimationsCentralPool.getHeroAnimationRepository().getAnimationsProperties(heroAnimationsIndex)));
		this.gameHero = gameHero;
		
		inputMap = new HashMap<>();
		for( HeroInputKey key : HeroInputKey.values() )
		{
			inputMap.put(key, false);
		}
	}

	public void onEnter()
	{
		// hook method
	}

	public void onQuit()
	{
		// hook method
	}

	public void stateUpdate(float deltaTime)
	{
		// hook method
	}

	protected void stateInputPressed(HeroInputKey inputValue)
	{
		// hook method
	}

	protected void stateInputReleased(HeroInputKey inputValue)
	{
		// hook method
	}
	
	protected void stateInputIsPressing(HeroInputKey inputValue)
	{
		// hook method
	}


	protected void rope()
	{
		//do nothing
	}
	
	protected void jump()
	{
		if (heroStatesProperties.jump != null)
		{
			gameHero.addAction(new JumpAction(gameHero, new Vec2(0, -heroStatesProperties.jump.impulse)));
			setState(new JumpingHeroState(gameHero));
		}
	}
	
	protected void shoot()
	{
		if (heroStatesProperties.shoot != null)
		{
			gameHero.addAction(new ShootAction(gameHero, new Shuriken(pointingDirection(), gameHero.getBody().getWorldCenter(), gameHero.getPlayerID())));
		}
		
	}

	@Override
	public final void inputPressed(HeroInputKey inputValue)
	{
		inputMap.put(inputValue, true);
		if (inputValue == HeroInputKey.JUMP)
		{
			jump();
		}
		if (inputValue == HeroInputKey.ROPE)
		{
			rope();
		}
		
		stateInputPressed(inputValue);
	}
	
	@Override
	public final void isPressing(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.SHOOT)
		{
			System.out.println("Aiming!");
		}			
		if (inputValue == HeroInputKey.LEFT)
		{
			move(Side.LEFT);
		}
		else if (inputValue == HeroInputKey.RIGHT)
		{
			move(Side.RIGHT);
		}
		
		stateInputIsPressing(inputValue);
	}

	protected void move(Side side)
	{
		if (heroStatesProperties.move != null)
		{
			gameHero.addAction(new MoveHeroAction(gameHero, side, heroStatesProperties));
		}

		gameHero.setForwardSide(side);

	}

	@Override
	public final void inputReleased(HeroInputKey inputValue)
	{
		inputMap.put(inputValue, false);
		if (inputValue == HeroInputKey.SHOOT)
		{
			shoot();
		}		
		stateInputReleased(inputValue);
	}

	@Override
	public final void update(float deltaTime)
	{
		stateUpdate(deltaTime);
	}

	protected final void setState(HeroState heroState)
	{
		heroState.inputMap = inputMap;
		gameHero.addAction(new HeroStateSetterAction(gameHero, heroState));
	}

	public final Vector2 getMaxSpeed()
	{
		return heroStatesProperties.maxSpeed;
	}

	protected final Vec2 pointingDirection()
	{
		Vec2 retorno = new Vec2();

		if (inputMap.get(HeroInputKey.UP))
		{
			retorno.y = -1;
		}
		else if (inputMap.get(HeroInputKey.DOWN))
		{
			retorno.y = 1;
		}

		if (inputMap.get(HeroInputKey.RIGHT))
		{
			retorno.x = 1;
		}
		else if (inputMap.get(HeroInputKey.LEFT))
		{
			retorno.x = -1;
		}

		if (retorno.length() == 0) // Default
		{
			retorno.x = gameHero.getForwardSide().getHorizontalValue();
		}
		return retorno;
	}

}
