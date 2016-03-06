package br.com.guigasgame.gameobject.hero.action;

import java.util.List;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;


public class AnimationListSetterAction extends GameHeroAction
{

	private List<Animation> animationList;

	public AnimationListSetterAction(List<Animation> animationList)
	{
		this.animationList = animationList;
	}

	@Override
	protected void childExecute(PlayableGameHero hero)
	{
		hero.setAnimationList(animationList);
		hero.setForwardSide(hero.getForwardSide());
	}

}
