package br.com.guigasgame.gameobject.hero.state;

import java.util.HashMap;
import java.util.Map;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationsCentralPool;
import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.executor.HeroShooterExecutor;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.gameobject.projectile.Shuriken;
import br.com.guigasgame.input.InputListener;
import br.com.guigasgame.math.Vector2;
import br.com.guigasgame.updatable.UpdatableFromTime;


public abstract class HeroState implements InputListener<HeroInputKey>,
		UpdatableFromTime
{

	protected final GameHero gameHero;
	protected final HeroAnimationsIndex heroAnimationsIndex;
	protected final HeroStatesProperties heroStatesProperties;
	private Map<HeroInputKey, Boolean> inputMap;
	protected final HeroShooterExecutor shooter;

	protected HeroState(GameHero gameHero, HeroAnimationsIndex heroAnimationsIndex)
	{
		super();
		this.heroAnimationsIndex = heroAnimationsIndex;
		this.heroStatesProperties = HeroStatesPropertiesPool.getStateProperties(heroAnimationsIndex);
		gameHero.setAnimation(Animation.createAnimation(AnimationsCentralPool.getHeroAnimationRepository().getAnimationsProperties(heroAnimationsIndex)));
		this.gameHero = gameHero;
		inputMap = new HashMap<>();
		for( HeroInputKey key : HeroInputKey.values() )
		{
			inputMap.put(key, false);
		}
		shooter = new HeroShooterExecutor(heroStatesProperties, gameHero);
	}

	public void onEnter()
	{
		// hook method
	}

	public void onQuit()
	{
		// hook method
	}

	public void updateState(float deltaTime)
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

	@Override
	public final void inputPressed(HeroInputKey inputValue)
	{
		inputMap.put(inputValue, true);
		if (inputValue == HeroInputKey.SHOOT)
		{
			shoot();
		}
		if (inputValue == HeroInputKey.STOP)
		{
			stopMovement();
		}
		stateInputPressed(inputValue);
	}

	private void stopMovement()
	{
		System.out.println("STOP!");
	}

	@Override
	public final void inputReleased(HeroInputKey inputValue)
	{
		inputMap.put(inputValue, false);
		stateInputReleased(inputValue);
	}

	@Override
	public final void update(float deltaTime)
	{
		updateState(deltaTime);
	}

	protected final void setState(HeroState heroState)
	{
		gameHero.setState(heroState);
		heroState.inputMap = inputMap;
	}

	public final Vector2 getMaxSpeed()
	{
		return heroStatesProperties.maxSpeed;
	}

	protected void shoot()
	{
		shooter.shoot(new Shuriken(pointingDirection(), gameHero.getBody().getWorldCenter(), gameHero.getPlayerID()));
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
