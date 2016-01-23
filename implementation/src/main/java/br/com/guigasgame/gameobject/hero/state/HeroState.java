package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.input.InputListener;
import br.com.guigasgame.side.Side;
import br.com.guigasgame.updatable.UpdatableFromTime;


public abstract class HeroState implements InputListener<HeroInputKey>,
		UpdatableFromTime
{

	protected HeroState previousState;
	protected final Vec2 maxSpeed;
	protected final boolean canShoot;
	protected final boolean canJump;
	protected final boolean canUseRope;
	protected final Animation animation;
	protected final GameHero gameHero;
	protected final float horizontalAcceleration;
	protected final float jumpAcceleration;

	protected HeroState(HeroState previousState, Vec2 maxSpeed,
			boolean canShoot, boolean canJump, boolean canUseRope,
			Animation animation, GameHero gameHero,
			float horizontalAcceleration, float jumpAcceleration)
	{
		super();
		this.previousState = previousState;
		this.maxSpeed = maxSpeed;
		this.canShoot = canShoot;
		this.canJump = canJump;
		this.canUseRope = canUseRope;
		this.animation = animation;
		this.gameHero = gameHero;
		this.horizontalAcceleration = horizontalAcceleration;
		this.jumpAcceleration = jumpAcceleration;
	}

	public void setPreviousState(HeroState state)
	{
		previousState = state;
	}

	public abstract void entry();

	public abstract void updateState(float deltaTime);

	@Override
	public final void update(float deltaTime)
	{
		animation.update(deltaTime);
		updateState(deltaTime);
	}
	
	protected final void setForwardSide(Side side)
	{
		animation.setSide(side);
		gameHero.setForwardSide(side);
	}
	
	protected final void setState(HeroState heroState)
	{
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

	protected void jump()
	{
		if (canJump) gameHero.applyImpulse(new Vec2(0, -jumpAcceleration));
	}

	protected void moveRight()
	{
		setForwardSide(Side.RIGHT);
		gameHero.applyForce(new Vec2(horizontalAcceleration, 0));
	}

	protected void moveLeft()
	{
		setForwardSide(Side.LEFT);
		gameHero.applyForce(new Vec2(-horizontalAcceleration, 0));
	}

}
