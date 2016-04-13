package br.com.guigasgame.gameobject.hero.playable;

import br.com.guigasgame.collision.IntegerMask;
import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.frag.FragEventParser;
import br.com.guigasgame.frag.FragEventWrapper;
import br.com.guigasgame.frag.HeroEventCentralMessenger;
import br.com.guigasgame.frag.HeroFragEventParser;
import br.com.guigasgame.gameobject.hero.attributes.playable.RoundHeroAttributes;
import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap;
import br.com.guigasgame.team.HeroTeam;

public class PlayableHeroDefinition
{
	private final GameHeroInputMap gameHeroInput;
	private final int playerId;
	
	private ColorBlender color;
	private HeroTeam heroTeam;
	private RoundHeroAttributes roundHeroAttributes;
	private int idInTeam;
	private final FragEventParser fragCounter;

	public PlayableHeroDefinition(GameHeroInputMap gameHeroInput, int id)
	{
		this.gameHeroInput = gameHeroInput;
		this.playerId = id;
		
		fragCounter = new HeroFragEventParser(id);
		HeroEventCentralMessenger.getInstance().subscribe(FragEventWrapper.class, fragCounter);
	}
	
	public FragEventParser getFragCounter()
	{
		return fragCounter;
	}
	
	public void setTeamConfigurations(HeroTeam team) 
	{
		this.heroTeam = team;
		this.color = heroTeam.getColorOfPlayer(this);
	}

	public GameHeroInputMap getGameHeroInput()
	{
		return gameHeroInput;
	}

	public int getPlayerId()
	{
		return playerId;
	}

	public ColorBlender getColor()
	{
		return color;
	}

	public IntegerMask getHitTeamMask()
	{
		return heroTeam.getHitTeamMask(playerId);
	}

	public IntegerMask getHitEnemiesMask()
	{
		return heroTeam.getHitEnemiesMask();
	}

	public RoundHeroAttributes getRoundHeroAttributes()
	{
		return roundHeroAttributes;
	}

	public void setHeroAttributes(RoundHeroAttributes roundHeroAttributes)
	{
		this.roundHeroAttributes = roundHeroAttributes;
	}

	public void setIdInTeam(int idInTeam)
	{
		this.idInTeam = idInTeam;
	}

	public int getIdInTeam()
	{
		return idInTeam;
	}

	public HeroTeam getHeroTeam()
	{
		return heroTeam;
	}
	
}
