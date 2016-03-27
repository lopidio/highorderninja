package br.com.guigasgame.team;

import br.com.guigasgame.color.ColorBlender;

public enum TeamName
{
	//	private static final int NUM_MAX_TEAMS = 4; 

	ALPHA(getNextId()),
	BRAVO(getNextId()),
	CHARLIE(getNextId()),
	DELTA(getNextId());

	private static int idsUsed = 0;
	private int id;

	private static int getNextId()
	{
		return idsUsed++;
	}

	private TeamName(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public ColorBlender getColor()
	{
		switch (this)
		{
			case ALPHA:
				return ColorBlender.RED;
			case BRAVO:
				return ColorBlender.BLUE;
			case CHARLIE:
				return ColorBlender.GREEN;
			case DELTA:
				return ColorBlender.YELLOW;
		}
		return ColorBlender.WHITE;
	}

}
