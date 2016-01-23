package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationsRepositoryCentral;
import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;


public class JumpingHeroState extends OnAirHeroState
{

	protected JumpingHeroState(boolean canJumpAgain, GameHero gameHero)
	{
		super(canJumpAgain,
				Animation.createAnimation(AnimationsRepositoryCentral
						.getHeroAnimationRepository().getAnimationsProperties(
								HeroAnimationsIndex.HERO_ASCENDING)),
				gameHero, 20);
	}

	@Override
	public void onEnter()
	{
		jump();
	}

	@Override
	public void inputPressed(HeroInputKey key)
	{
		if (canJump && key == HeroInputKey.JUMP)
		{
			System.out.println("Double jump!");
			setState(new JumpingHeroState(false, gameHero));
		}
	}

}
