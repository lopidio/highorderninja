package br.com.guigasgame.frag;

import java.util.ArrayList;
import java.util.List;

public class FragStatistic
{
	private List<Integer> shootsOnTarget;
	private List<Integer> hitAsTarget;
	private List<Integer> deaths;
	private List<Integer> kills;

	public FragStatistic()
	{
		 shootsOnTarget = new ArrayList<>();
		 hitAsTarget = new ArrayList<>();
		 deaths = new ArrayList<>();
		 kills = new ArrayList<>();
	}
	
	public void incrementShoots()
	{
		shootsOnTarget.add(new Integer(-1));
	}
	
	public void incrementShootsOnTarget(Integer targetId)
	{
		shootsOnTarget.add(targetId);
	}
	
	public void incrementDeaths(Integer killerId)
	{
		deaths.add(killerId);
	}

	public void incrementKills(Integer targetId)
	{
		kills.add(targetId);
	}
	
	public void incrementSuicides()
	{
		incrementDeaths(-1);
	}
	
	public void incrementHitAsTarget(Integer shooterId)
	{
		hitAsTarget.add(shooterId);
	}	
	
	public int getDeaths()
	{
		return deaths.size();
	}

	public int getKills()
	{
		return kills.size();
	}
	
//	public FragStatistic incorporate(FragStatistic other)
//	{
//		 this.shoots += other.shoots;
//		 this.shootsOnTarget += other.shootsOnTarget;
//		 this.hitAsTarget += other.hitAsTarget;
//		 this.deaths += other.deaths;
//		 this.kills += other.kills;
//		 this.suicide += other.suicide;
//		 return this;
//	}

}