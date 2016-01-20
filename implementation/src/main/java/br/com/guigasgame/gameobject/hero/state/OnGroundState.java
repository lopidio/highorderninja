package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.gameobject.hero.GameHero;


public abstract class OnGroundState extends HeroState {

	protected final float horizontalAcceleration;
	protected final float jumpAcceleration;

	protected OnGroundState(HeroState previousState, Vec2 maxSpeed, boolean canShoot, boolean canJump, boolean canUseRope,
			Animation animation, GameHero gameHero, float horizontalAcceleration, float jumpAcceleration) 
	{
		super(previousState, maxSpeed, canShoot, canJump, canUseRope, animation, gameHero);
		this.horizontalAcceleration = horizontalAcceleration;
		this.jumpAcceleration = jumpAcceleration;
	}
}
