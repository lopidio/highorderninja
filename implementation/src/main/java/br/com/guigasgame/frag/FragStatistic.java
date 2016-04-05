package br.com.guigasgame.frag;

public class FragStatistic
{
	private int shoots;
	private int shootsOnTarget;
	private int hitAsTarget;
	private int deaths;
	private int kills;
	private int suicide;

	public FragStatistic()
	{
		 shoots = 0;
		 shootsOnTarget = 0;
		 hitAsTarget = 0;
		 deaths = 0;
		 kills = 0;
		 suicide = 0;
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
	
	public void incrementSuicides()
	{
		++suicide;
	}
	
	public void incrementHitAsTarget()
	{
		++hitAsTarget;
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
	
	public int getHitAsTarget()
	{
		return hitAsTarget;
	}

	public FragStatistic incorporate(FragStatistic other)
	{
		 this.shoots += other.shoots;
		 this.shootsOnTarget += other.shootsOnTarget;
		 this.hitAsTarget += other.hitAsTarget;
		 this.deaths += other.deaths;
		 this.kills += other.kills;
		 this.suicide += other.suicide;
		 return this;
	}

}