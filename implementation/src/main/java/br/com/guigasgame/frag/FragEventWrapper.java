package br.com.guigasgame.frag;

import br.com.guigasgame.frag.FragEventMessenger.FragEventIndex;

public class FragEventWrapper
{
	private final int myId;
	private final int otherId;
	private final FragEventIndex fragEventIndex;
	
	public FragEventWrapper(int myId, int otherId, FragEventIndex fragEventIndex)
	{
		this.myId = myId;
		this.otherId = otherId;
		this.fragEventIndex = fragEventIndex;
	}
	
	public int getMyId()
	{
		return myId;
	}
	
	public int getOtherId()
	{
		return otherId;
	}
	
	public FragEventIndex getFragEventIndex()
	{
		return fragEventIndex;
	}

}
