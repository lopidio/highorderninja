package br.com.guigasgame.gameobject;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jsfml.graphics.Sprite;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.box2d.debug.PhysicsConstants;
import br.com.guigasgame.gameobject.hero.state.HeroState;

public class GameHero extends GameObject {
	enum ForwardSide {
		LEFT(-1), RIGHT(1);

		int value;

		ForwardSide(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	ForwardSide forwardSide;
	Body body;
	HeroState state;
	Animation animation;

	@Override
	public void update(float deltaTime) {
		animation.update(deltaTime);
		adjustSpritePosition();
	}

	@Override
	public Sprite getSprite() {
		return animation.getSprite();
	}

	private void adjustSpritePosition() {
		animation.getSprite().setPosition(PhysicsConstants.SCALE * body.getPosition().x,
				PhysicsConstants.SCALE * body.getPosition().y);
		animation.getSprite().setRotation((float) PhysicsConstants.radiansToDegree(body.getAngle()));
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public void addState(HeroState newState) {
		newState.setPreviousState(state);
		setState(newState);
	}

	public void setState(HeroState newState) {
		state = newState;
	}

	public void moveForward(float acceleration) {
		body.applyForceToCenter(new Vec2(acceleration * forwardSide.getValue(), 0));
	}

	public void jump(float jumpAcceleration) {
		body.applyLinearImpulse(new Vec2(0, jumpAcceleration), body.getWorldCenter());
	}

	public boolean isTouchingGround() {
		return true;
	}

	public boolean isTouchingWallAhead() {
		return false;
	}

	public void flipSide() {
		if (forwardSide == ForwardSide.LEFT)
			forwardSide = ForwardSide.RIGHT;
		else //if (forwardSide == ForwardSide.LEFT)
			forwardSide = ForwardSide.LEFT;
	}
}
