package br.com.guigasgame.round.hud;

import org.jsfml.system.Vector2i;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.round.hud.dynamic.heroattributes.HeroMovingHudController;
import br.com.guigasgame.round.hud.fix.HeroFragStatisticHud;
import br.com.guigasgame.round.hud.fix.TeamFragStatisticHud;
import br.com.guigasgame.round.hud.fix.TimerStaticHud;
import br.com.guigasgame.team.HeroTeam;

///Abstract factory
public interface RoundHudSkin
{
	Vector2i getIdealView();
	TimerStaticHud createTimerStaticHud(int totalTime);
	HeroMovingHudController createHeroAttributesHud(PlayableGameHero gameHero);
	HeroFragStatisticHud createHeroFragHud(PlayableGameHero gameHero);
	TeamFragStatisticHud createTeamFragHud(HeroTeam team);
}
