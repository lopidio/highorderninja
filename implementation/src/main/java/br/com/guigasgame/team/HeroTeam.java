package br.com.guigasgame.team;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.graphics.Color;

import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.collision.IntegerMask;
import br.com.guigasgame.gameobject.hero.playable.PlayableHeroDefinition;


public class HeroTeam
{

	private final Color color;
	private final int teamId;

	private IntegerMask teamMask;
	private IntegerMask enemiesMask;
	private boolean friendlyFire;
	// private int totalScore;

	private List<PlayableHeroDefinition> herosList;

	public HeroTeam(int id)
	{
		teamId = id;
		teamMask = new IntegerMask();
		enemiesMask = CollidableCategory.getAllPlayersCategory();
		// totalScore = 0;
		herosList = new ArrayList<PlayableHeroDefinition>();
		color = getTeamColor();
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

	public Color getColorOfPlayer(PlayableHeroDefinition gameHeroProperties)
	{
		int index = herosList.indexOf(gameHeroProperties);

		int constant = 255 / (herosList.size() * 2);
		int factor = constant * ((2 * index) + 1);

		Color mult = new Color(factor, factor, factor);
		return Color.add(color, mult);
	}

	public List<PlayableHeroDefinition> getHerosList()
	{
		return herosList;
	}

	private Color getTeamColor()
	{
		switch (teamId)
		{
			case 0:
				return Color.RED;
			case 1:
				return Color.BLUE;
			case 2:
				return Color.GREEN;
			case 3:
				return Color.CYAN;
			case 4:
				return Color.YELLOW;
			case 5:
				return Color.MAGENTA;
			case 6:
				return new Color(128, 128, 128);
			case 7:
				return new Color(255, 128, 0);
		}
		return Color.WHITE;
	}

	public void setUp()
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

}
