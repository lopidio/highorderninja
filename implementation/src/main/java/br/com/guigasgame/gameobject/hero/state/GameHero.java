package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jsfml.graphics.Sprite;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.box2d.debug.WorldManager;
import br.com.guigasgame.gamehero.HeroSensorsController;
import br.com.guigasgame.gameobject.GameObject;

public class GameHero extends GameObject {

	ForwardSide forwardSide;
	HeroSensorsController sensorsController;
	Body body;
	HeroState state;
	Animation animation;

	public GameHero() {
		super();
		forwardSide = ForwardSide.LEFT;
		sensorsController = new HeroSensorsController(this);
		state = new StandingState();
	}

	@Override
	public void update(float deltaTime) {
		state.updateState(deltaTime);
		animation.update(deltaTime);
		adjustSpritePosition();
	}

	@Override
	public Sprite getSprite() {
		return animation.getSprite();
	}

	private void adjustSpritePosition() {
		animation.getSprite().setPosition(WorldManager.SCALE * body.getPosition().x,
				WorldManager.SCALE * body.getPosition().y);
		animation.getSprite().setRotation((float) WorldManager.radiansToDegree(body.getAngle()));
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
		state.entry();
	}

	public void flipSide() {
		if (forwardSide == ForwardSide.LEFT)
			forwardSide = ForwardSide.RIGHT;
		else
			// if (forwardSide == ForwardSide.LEFT)
			forwardSide = ForwardSide.LEFT;
	}

	public ForwardSide getForwardSide() {
		return forwardSide;
	}

	public final HeroSensorsController getSensorsController() {
		return sensorsController;
	}

	public void applyImpulse(Vec2 impulse) {
		body.applyLinearImpulse(impulse, body.getWorldCenter());
	}

	public void applyForce(Vec2 force) {
		body.applyForce(force, body.getWorldCenter());
	}

	public Body getBody() {
		return body;
	}

}
