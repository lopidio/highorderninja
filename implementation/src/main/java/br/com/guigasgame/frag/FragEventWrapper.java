package br.com.guigasgame.frag;

import br.com.guigasgame.frag.FragEventMessenger.FragEventIndex;

public class FragEventWrapper
{
	private final int myId;
	private final int myTeamId;
	private final FragEventIndex fragEventIndex;
	private int otherId;
	private int otherTeamId;

	public FragEventWrapper(int myId, int myTeamId, int otherId, int otherTeamId, FragEventIndex fragEventIndex)
	{
		this.myId = myId;
		this.myTeamId = myTeamId;
		this.otherId = otherId;
		this.otherTeamId = otherTeamId;
		this.fragEventIndex = fragEventIndex;
	}
	
	public int getMyId()
	{
		return myId;
	}

	public int getMyTeamId()
	{
		return myTeamId;
	}

	public int getOtherId()
	{
		return otherId;
	}
	
	public int getOtherTeamId()
	{
		return otherTeamId;
	}

	public FragEventIndex getFragEventIndex()
	{
		return fragEventIndex;
	}
	
	FragEventWrapper setOtherId(int otherId)
	{
		this.otherId = otherId;
		return this;
	}
	
	FragEventWrapper setOtherTeamId(int otherTeamId)
	{
		this.otherTeamId = otherTeamId;
		return this;
	}
}
