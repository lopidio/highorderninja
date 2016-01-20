package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.input.InputHeroListener;

public abstract class HeroState  implements InputHeroListener{
	protected HeroState previousState;
	protected final Vec2 maxSpeed;
	protected final boolean canShoot;
	protected final boolean canJump;
	protected final boolean canUseRope;
	protected final Animation animation;
	protected final GameHero gameHero;

	protected HeroState(HeroState previousState, Vec2 maxSpeed, boolean canShoot, boolean canJump, boolean canUseRope,
			Animation animation, GameHero gameHero) {
		super();
		this.previousState = previousState;
		this.maxSpeed = maxSpeed;
		this.canShoot = canShoot;
		this.canJump = canJump;
		this.canUseRope = canUseRope;
		this.animation = animation;
		this.gameHero = gameHero;
	}

	public void setPreviousState(HeroState state) {
		previousState = state;
	}

	public abstract void entry();

	public abstract void updateState(float deltaTime);

	public final Vec2 getMaxSpeed() {
		return maxSpeed;
	}

	public final Animation getAnimation() {
		return animation;
	}

}
