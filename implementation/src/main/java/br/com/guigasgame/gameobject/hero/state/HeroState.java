package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.input.InputHeroListener;

public abstract class HeroState  implements InputHeroListener{
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

	public Vec2 getMaxSpeed() {
		// TODO Auto-generated method stub
		return maxSpeed;
	}

}
