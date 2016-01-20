package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.input.GameHeroInput.HeroInputKey;

public class StandingState extends OnGroundState {

	public StandingState(Animation animation, GameHero gameHero) {
		super(null, new Vec2(10, 10), true, true, true, animation, gameHero, 20, 10);
	}

	@Override
	public void inputPressed(HeroInputKey key) {
		if (key == HeroInputKey.JUMP)
		{
			gameHero.applyImpulse(new Vec2(0, -jumpAcceleration));
		}
		else if (key == HeroInputKey.ACTION)
		{
			System.out.println("Action");
		}
	}

	@Override
	public void isPressed(HeroInputKey key) {
		System.out.println("isPressed");
		if (key == HeroInputKey.LEFT)
		{
			gameHero.applyForce(new Vec2(-horizontalAcceleration, 0));
		}
		else if (key == HeroInputKey.RIGHT)
		{
			gameHero.applyForce(new Vec2(horizontalAcceleration, 0));
		}
	}
	
	@Override
	public void entry() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateState(float deltaTime) {
		animation.update(deltaTime);		
	}
}
