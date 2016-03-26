package br.com.guigasgame.round.hud.controller;

import br.com.guigasgame.gameobject.hero.attributes.playable.RoundHeroAttributes;
import br.com.guigasgame.round.hud.HudObject;

public abstract class HeroAttributesHudController extends HudObject
{
	public abstract void addAsHudController(RoundHeroAttributes roundHeroAttributes);
}
