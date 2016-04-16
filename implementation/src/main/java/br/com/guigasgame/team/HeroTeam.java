package br.com.guigasgame.team;

import java.util.ArrayList;
import java.util.List;

import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.collision.IntegerMask;
import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.frag.TeamFragEventParser;
import br.com.guigasgame.gameobject.hero.playable.PlayableHeroDefinition;
import br.com.guigasgame.round.event.FragEventParser;


public class HeroTeam
{
	
	private final ColorBlender color;

	private IntegerMask teamMask;
	private IntegerMask enemiesMask;
	private boolean friendlyFire;

	private List<PlayableHeroDefinition> herosList;
	private TeamIndex teamIndex;

	private final FragEventParser fragCounter;

	public HeroTeam(TeamIndex name) throws Exception
	{
		this.teamIndex = name;
		teamMask = new IntegerMask();
		enemiesMask = CollidableCategory.getAllPlayersCategory();
		// totalScore = 0;
		herosList = new ArrayList<PlayableHeroDefinition>();
		color = getTeamColor();
		
		fragCounter = new TeamFragEventParser(teamIndex.getId());
	}

	public void setFriendlyFire(boolean friendlyFire)
	{
		this.friendlyFire = friendlyFire;
	}

	public void addGameHero(PlayableHeroDefinition gameHeroProperties)
	{
		if (!herosList.contains(gameHeroProperties))
		{
			final IntegerMask mask = CollidableCategory.getPlayerCategory(gameHeroProperties.getPlayerId());
			teamMask = teamMask.set(mask.value);
			enemiesMask = enemiesMask.clear(mask.value);
			gameHeroProperties.setIdInTeam(herosList.size());
			herosList.add(gameHeroProperties);
		}
	}

	public void removeGameHero(PlayableHeroDefinition gameHeroProperties)
	{
		final IntegerMask mask = CollidableCategory.getPlayerCategory(gameHeroProperties.getPlayerId());
		teamMask = teamMask.clear(mask.value);
		enemiesMask = enemiesMask.set(mask.value);
		herosList.remove(gameHeroProperties);
	}

	public ColorBlender getColorOfPlayer(PlayableHeroDefinition gameHeroProperties)
	{
		int index = herosList.indexOf(gameHeroProperties);

		int constant = 255 / (herosList.size() * 2);
		int factor = constant * ((2 * index) + 1);

		ColorBlender mult = new ColorBlender(factor, factor, factor);
		return color.add(mult);
	}

	public List<PlayableHeroDefinition> getHerosList()
	{
		return herosList;
	}

	public ColorBlender getTeamColor()
	{
		return teamIndex.getColor();
	}

	public void setHeroesUp()
	{
		for( PlayableHeroDefinition gameHeroProperties : herosList )
		{
			gameHeroProperties.setTeamConfigurations(this);
		}
	}

	public IntegerMask getHitTeamMask(int playerId)
	{
		if (!friendlyFire)
			return teamMask;
		else
			return CollidableCategory.getPlayerCategory(playerId);
	}

	public IntegerMask getHitEnemiesMask()
	{
		return enemiesMask;
	}

	public int getTeamId()
	{
		return teamIndex.getId();
	}

	public String getName()
	{
		return teamIndex.getName();
	}

	public FragEventParser getFragCounter()
	{
		return fragCounter;
	}
	

}
