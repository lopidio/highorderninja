package br.com.guigasgame.round.hud;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.round.hud.controller.HeroMovingHudController;
import br.com.guigasgame.round.hud.dynamic.heroattributes.HeroAttributesDefaultHudController;
import br.com.guigasgame.round.hud.fix.HeroFragStatisticHud;
import br.com.guigasgame.round.hud.fix.TeamFragStatisticHud;
import br.com.guigasgame.round.hud.fix.TextTimerHud;
import br.com.guigasgame.round.hud.fix.TimerStaticHud;
import br.com.guigasgame.team.HeroTeam;

public class RoundHudTopSkin implements RoundHudSkin
{
	
	private final Vector2i viewSize;
	private final int numTeams;
	
	
	public RoundHudTopSkin()
	{
		this.viewSize = new Vector2i(1600,  900);
		this.numTeams = 8;
	}
	
	@Override
	public Vector2i getIdealView()
	{
		return viewSize;
	}
	
	@Override
	public TimerStaticHud createTimerStaticHud(int totalTime)
	{
		return new TextTimerHud(new Vector2f(800, 20), totalTime);
	}

	@Override
	public TeamFragStatisticHud createTeamFragHud(HeroTeam team)
	{
		final Vector2f position = calculateTeamPosition(team);
		TeamFragStatisticHud fragCounterHud = new TeamFragStatisticHud(position, team.getName(), team.getTeamColor());
		team.getFragCounter().addListener(fragCounterHud);
		return fragCounterHud;
	}

	private Vector2f calculateTeamPosition(HeroTeam team)
	{
		if (team.getTeamId() < numTeams/2)
		{
			return new Vector2f(team.getTeamId()*160 + 100, 20);
		}
		else
		{
			int id = numTeams - team.getTeamId();
			return new Vector2f(viewSize.x - id*160 + 50, 20);
		}
	}
	
	@Override
	public HeroFragStatisticHud createHeroFragHud(PlayableGameHero gameHero)
	{
		HeroFragStatisticHud fragStatisticHud = new HeroFragStatisticHud(gameHero.getHeroProperties().getColor());
		gameHero.getHeroProperties().getFragCounter().addListener(fragStatisticHud);
		return fragStatisticHud;
	}

	
	@Override
	public HeroMovingHudController createHeroAttributesHud(PlayableGameHero gameHero)
	{
		return new HeroAttributesDefaultHudController(gameHero);
	}
	
}
