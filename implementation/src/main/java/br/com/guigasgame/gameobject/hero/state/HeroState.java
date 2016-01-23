package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationsRepositoryCentral;
import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.input.InputListener;
import br.com.guigasgame.side.Side;
import br.com.guigasgame.updatable.UpdatableFromTime;


public abstract class HeroState
		implements InputListener<HeroInputKey>, UpdatableFromTime
{

	protected HeroState previousState;
	protected boolean canJump;
	protected final Vec2 maxSpeed;
	protected final boolean canShoot;
	protected final boolean canUseRope;
	protected final Animation animation;
	protected final GameHero gameHero;
	protected final float horizontalAcceleration;
	protected final float jumpAcceleration;

	protected HeroState(HeroState previousState, Vec2 maxSpeed,
			boolean canShoot, boolean canJump, boolean canUseRope,
			HeroAnimationsIndex heroAnimationsIndex, GameHero gameHero,
			float horizontalAcceleration, float jumpAcceleration)
	{
		super();
		this.previousState = previousState;
		this.maxSpeed = maxSpeed;
		this.canShoot = canShoot;
		this.canJump = canJump;
		this.canUseRope = canUseRope;
		this.animation = Animation.createAnimation(AnimationsRepositoryCentral.getHeroAnimationRepository().getAnimationsProperties(heroAnimationsIndex));
		this.gameHero = gameHero;
		this.horizontalAcceleration = horizontalAcceleration;
		this.jumpAcceleration = jumpAcceleration;
	}

	public void setPreviousState(HeroState state)
	{
		previousState = state;
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

	public final void flipAnimation(Side side)
	{
		animation.flipAnimation(side);
	}

	protected final void setState(HeroState heroState)
	{
		heroState.flipAnimation(gameHero.getForwardSide());
		gameHero.setState(heroState);
	}

	public final Vec2 getMaxSpeed()
	{
		return maxSpeed;
	}

	public final Animation getAnimation()
	{
		return animation;
	}

	protected boolean jump()
	{
		if (canJump)
		{
			gameHero.applyImpulse(new Vec2(0, -jumpAcceleration));
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

	protected void moveRight()
	{
		flipAnimation(Side.RIGHT);
		gameHero.setForwardSide(Side.RIGHT);
		gameHero.applyForce(new Vec2(horizontalAcceleration, 0));
	}

	protected void moveLeft()
	{
		flipAnimation(Side.LEFT);
		gameHero.setForwardSide(Side.LEFT);
		gameHero.applyForce(new Vec2(-horizontalAcceleration, 0));
	}

}
