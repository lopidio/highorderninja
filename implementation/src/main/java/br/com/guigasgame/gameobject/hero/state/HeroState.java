package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.input.InputHeroListener;
import br.com.guigasgame.updatable.UpdatableFromTime;

public abstract class HeroState  implements InputHeroListener, UpdatableFromTime{
	private HeroState previousState;
	private final Vec2 maxSpeed;
	private final boolean canShoot;
	private final boolean canJump;
	private final boolean canUseRope;
	private final Animation animation;
	private final GameHero gameHero;
	private final float horizontalAcceleration;
	private final float jumpAcceleration;

	protected HeroState(HeroState previousState, Vec2 maxSpeed, boolean canShoot, boolean canJump, boolean canUseRope,
			Animation animation, GameHero gameHero, float horizontalAcceleration, float jumpAcceleration) {
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

	public void setPreviousState(HeroState state) {
		previousState = state;
	}


	public abstract void entry();

	public abstract void updateState(float deltaTime);
	
	@Override
	public final void update(float deltaTime) {
		animation.update(deltaTime);
		updateState(deltaTime);
	}

	public final Vec2 getMaxSpeed() {
		return maxSpeed;
	}

	public final Animation getAnimation() {
		return animation;
	}

	protected void jump()
	{
		if (canJump)
			gameHero.applyImpulse(new Vec2(0, -jumpAcceleration));
	}

	protected void moveRight() {
		gameHero.applyForce(new Vec2(horizontalAcceleration, 0));
	}

	protected void moveLeft() {
		gameHero.applyForce(new Vec2(-horizontalAcceleration, 0));
	}
	
}
