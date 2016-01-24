package br.com.guigasgame.gameobject.hero.state;

import java.util.HashMap;
import java.util.Map;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationsRepositoryCentral;
import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.input.InputListener;
import br.com.guigasgame.math.Vector2;
import br.com.guigasgame.side.Side;
import br.com.guigasgame.updatable.UpdatableFromTime;


public abstract class HeroState
		implements InputListener<HeroInputKey>, UpdatableFromTime
{

	protected final GameHero gameHero;
	protected final HeroAnimationsIndex heroAnimationsIndex;
	protected final HeroStatesProperties heroStatesProperties;
	protected Map<HeroInputKey, Boolean> inputMap;
	private final Animation animation;

	protected HeroState(GameHero gameHero, HeroAnimationsIndex heroAnimationsIndex)
	{
		super();
		this.heroAnimationsIndex = heroAnimationsIndex;
		this.heroStatesProperties = HeroStatesPropertiesRepository.getStateProperties(heroAnimationsIndex);
		this.animation = Animation.createAnimation(AnimationsRepositoryCentral.getHeroAnimationRepository().getAnimationsProperties(heroAnimationsIndex));
		this.gameHero = gameHero;
		inputMap = new HashMap<>();
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

	@Override
	public final void update(float deltaTime)
	{
		animation.update(deltaTime);
		updateState(deltaTime);
	}

	protected final void setState(HeroState heroState)
	{
		gameHero.setState(heroState);
		heroState.animation.flipAnimation(gameHero.getForwardSide());
	}

	public final Vector2 getMaxSpeed()
	{
		return heroStatesProperties.maxSpeed;
	}

	public final Animation getAnimation()
	{
		return animation;
	}

	protected void shoot()
	{
		gameHero.shoot();
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

	protected void moveForward()
	{
		if (gameHero.getForwardSide() == Side.LEFT)
		{
			moveLeft();
		}
		else // if (gameHero.getForwardSide() == Side.RIGHT)
		{
			moveRight();
		}
	}

	protected void moveBackward()
	{
		if (gameHero.getForwardSide() == Side.LEFT)
		{
			moveRight();
		}
		else // if (gameHero.getForwardSide() == Side.RIGHT)
		{
			moveLeft();
		}
	}

	protected void setHeroForwardSide(Side side)
	{
		animation.flipAnimation(side);
		gameHero.setForwardSide(side);
	}

	protected void moveRight()
	{
		if (gameHero.getForwardSide() == Side.LEFT)
			setHeroForwardSide(Side.RIGHT);
		gameHero.applyForce(new Vec2(heroStatesProperties.horizontalAcceleration, 0));
	}

	protected void moveLeft()
	{
		if (gameHero.getForwardSide() == Side.RIGHT)
			setHeroForwardSide(Side.LEFT);
		gameHero.applyForce(new Vec2(-heroStatesProperties.horizontalAcceleration, 0));
	}

}
