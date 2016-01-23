package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationsRepositoryCentral;
import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;


public class SuperJumpingHeroState extends OnAirHeroState
{

	protected SuperJumpingHeroState(GameHero gameHero)
	{
		super(true, Animation.createAnimation(AnimationsRepositoryCentral
				.getHeroAnimationRepository().getAnimationsProperties(HeroAnimationsIndex.HERO_ASCENDING)), gameHero, 40);
	}

	@Override
	public void onEnter()
	{
		jump();
	}
	
	public void updateState(float deltaTime) {
		super.updateState(deltaTime);
		gameHero.applyForce(new Vec2(0, -5));		
	};
	
	@Override
	public void inputPressed(HeroInputKey key)
	{
		if (key == HeroInputKey.JUMP)
		{
			if (jump())
			{
				System.out.println("Super Double jump!");
				setState(new JumpingHeroState(false, gameHero));
			}
		}
	}	
	
}
