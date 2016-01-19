package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.Animation;

public abstract class HeroState {
	private HeroState previousState;
	private Vec2 maxSpeed;
	private boolean canShoot;
	private boolean canJump;
	private boolean canUseRope;
	private Animation animation;
	private GameHero gameHero;

	public void setPreviousState(HeroState state) {
		previousState = state;
	}

	public abstract void entry();

	public abstract void updateState(float deltaTime);

	private void checkSpeedLimits() {

		Vec2 speed = gameHero.getBody().getLinearVelocity();
		Vec2 speedToSubtract = new Vec2();
		float xDiff = Math.abs(speed.x) - Math.abs(maxSpeed.x); 
		float yDiff = Math.abs(speed.y) - Math.abs(maxSpeed.y); 
		if (xDiff > 0) {
			speedToSubtract.x = xDiff;
		}
		if (yDiff > 0) {
			speedToSubtract.y = xDiff;
		}
		
		//Se houve alteração
		if (speed.length() > 0)
		{
			gameHero.getBody().setLinearVelocity(speed.sub(speedToSubtract));
		}
	}
}
