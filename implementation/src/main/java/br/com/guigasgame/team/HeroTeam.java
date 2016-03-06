package br.com.guigasgame.team;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.graphics.Color;

import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.collision.IntegerMask;
import br.com.guigasgame.frag.RoundFragCounter;
import br.com.guigasgame.gameobject.hero.playable.GameHeroProperties;


public class HeroTeam
{

	private final Color color;
	private final int teamId;

	private IntegerMask teamMask;
	private IntegerMask enemiesMask;
	// private int totalScore;

	private List<GameHeroProperties> herosList;

	public HeroTeam(int id)
	{
		teamId = id;
		teamMask = new IntegerMask();
		enemiesMask = CollidableCategory.getAllPlayersCategory();
		// totalScore = 0;
		herosList = new ArrayList<GameHeroProperties>();
		color = getTeamColor();
	}

	public void addGameHero(GameHeroProperties gameHeroProperties)
	{
		if (!herosList.contains(gameHeroProperties))
		{
			final IntegerMask mask = CollidableCategory.getPlayerCategory(gameHeroProperties.getPlayerId());
			teamMask = teamMask.set(mask.value);
			enemiesMask = enemiesMask.clear(mask.value);
			herosList.add(gameHeroProperties);

		}
	}

	public void removeGameHero(GameHeroProperties gameHeroProperties)
	{
		final IntegerMask mask = CollidableCategory.getPlayerCategory(gameHeroProperties.getPlayerId());
		teamMask = teamMask.clear(mask.value);
		enemiesMask = enemiesMask.set(mask.value);
		herosList.remove(gameHeroProperties);
	}

	public Color getColorOfPlayer(GameHeroProperties gameHeroProperties)
	{
		int index = herosList.indexOf(gameHeroProperties);

		int constant = 255 / (herosList.size() * 2);
		int factor = constant * ((2 * index) + 1);

		Color mult = new Color(factor, factor, factor);
		return Color.add(color, mult);
	}

	public List<GameHeroProperties> getHerosList()
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
				return Color.YELLOW;
			case 3:
				return Color.GREEN;
			case 4:
				return Color.CYAN;
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
		for( GameHeroProperties gameHeroProperties : herosList )
		{
			gameHeroProperties.setTeamConfigurations(this);
		}
	}

	public IntegerMask getTeamMask()
	{
		return teamMask;
	}

	public IntegerMask getEnemiesMask(int playerID)
	{
		return enemiesMask;
	}

	public void displayFrags()
	{
		for( GameHeroProperties gameHeroProperties : herosList )
		{
			RoundFragCounter roundFragCounter = gameHeroProperties.getFragCounter();
			System.out.println("Shoots: "
					+ roundFragCounter.getShootsOnTarget() + "/"
					+ roundFragCounter.getShoots());
		}
	}

}
