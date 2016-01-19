package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.gameobject.GameHero;

public class OnGroundState extends HeroState {

	private float acceleration;
	private float jumpAcceleration;

	@Override
	public void entry(GameHero gameHero) {
		gameHero.setAnimation(animation);
	}

	@Override
	public void updateState(GameHero gameHero, float deltaTime) {
		// TODO Auto-generated method stub
		gameHero.moveForward(acceleration);
		gameHero.jump(jumpAcceleration);
	}

}
