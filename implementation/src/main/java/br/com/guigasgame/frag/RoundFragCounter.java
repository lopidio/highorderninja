package br.com.guigasgame.frag;


public class RoundFragCounter
{
	private int shoots;
	private int shootsOnTarget;
	private int hitAsTarget;
	private int deaths;
	private int kills;
	private int suicide;
	
	public RoundFragCounter()
	{
		 shoots = 0;
		 shootsOnTarget = 0;
		 deaths = 0;
		 kills = 0;
		 suicide = 0;
		 hitAsTarget = 0;
	}
	
	public void incrementShoots()
	{
		++shoots;
	}

	public void incrementShootsOnTarget()
	{
		++shootsOnTarget;
	}
	
	public void incrementDeaths()
	{
		++deaths;
	}
	
	public void incrementKills()
	{
		++kills;
	}
	
	public void incrementSuicide()
	{
		incrementDeaths();
		++suicide;
	}

	public int getShoots()
	{
		return shoots;
	}
	
	public int getShootsOnTarget()
	{
		return shootsOnTarget;
	}
	
	public int getDeaths()
	{
		return deaths;
	}

	public int getKills()
	{
		return kills;
	}
	
	public int getSuicide()
	{
		return suicide;
	}

	public void incrementHitAsTarget()
	{
		++hitAsTarget;
	}
	
}
