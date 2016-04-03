package br.com.guigasgame.round.hud;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import br.com.guigasgame.frag.FragEventMessenger;
import br.com.guigasgame.frag.FragStatistic;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.round.hud.dynamic.heroattributes.HeroAttributesMovingHudController;
import br.com.guigasgame.round.hud.dynamic.heroattributes.barbellow.HeroAttributesCircleAndBarsBellowHudController;
import br.com.guigasgame.round.hud.fix.HeroFragStatisticHud;
import br.com.guigasgame.round.hud.fix.TeamFragStatisticHud;
import br.com.guigasgame.round.hud.fix.TextTimerHud;
import br.com.guigasgame.round.hud.fix.TimerStaticHud;
import br.com.guigasgame.team.HeroTeam;

public class RoundHudTopSkin implements RoundHudSkin
{
	
	private final Vector2i viewSize;
	private final int numTeams;
	
	
	public RoundHudTopSkin(Vector2i viewSize, int numTeams)
	{
		this.viewSize = new Vector2i(1600,  900);
		this.numTeams = numTeams;
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
		FragStatistic fragCounter = new FragStatistic(team.getTeamId());
		FragEventMessenger.getInstance().subscribeOnTeamEvents(team, fragCounter);
		TeamFragStatisticHud fragCounterHud = new TeamFragStatisticHud(position, team.getName(), team.getTeamColor());
		fragCounter.addListener(fragCounterHud);
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
		final FragStatistic fragCounter = new FragStatistic(gameHero.getHeroProperties().getPlayerId());
		HeroFragStatisticHud fragStatisticHud = new HeroFragStatisticHud(gameHero.getHeroProperties().getColor());
		FragEventMessenger.getInstance().subscribeOnHeroEvents(gameHero, fragCounter);
		fragCounter.addListener(fragStatisticHud);
		return fragStatisticHud;
	}

	
	@Override
	public HeroAttributesMovingHudController createHeroAttributesHud(PlayableGameHero gameHero)
	{
		return new HeroAttributesCircleAndBarsBellowHudController(gameHero);
	}
	
}
