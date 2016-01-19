package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.gameobject.hero.input.GameHeroInput.HeroKey;


public class OnGroundState extends HeroState {

	private float acceleration;
	private float jumpAcceleration;
	@Override
	public void entry() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateState(float deltaTime) {
		
//		gameHero.getBody().applyForce(force, point);(acceleration);
//		gameHero.jump(jumpAcceleration);
	}
	@Override
	public void inputReleased(HeroKey key) {
		if (key == HeroKey.JUMP)
		{
			
		}
		// TODO Auto-generated method stub
		
	}
	@Override
	public void inputPressed(HeroKey key) {
		// TODO Auto-generated method stub
		
	}

}
