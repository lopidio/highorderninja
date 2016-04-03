package br.com.guigasgame.team;

import br.com.guigasgame.color.ColorBlender;

public enum TeamsIndex
{
	//	private static final int NUM_MAX_TEAMS = 4; 

	ALPHA(getNextId(),		"Buscuinders"),
	BRAVO(getNextId(),		"MedalMedal"),
	CHARLIE(getNextId(),	"Pajjaguaios"),
	DELTA(getNextId(),		"Perceptors"),
	ECHO(getNextId(),		"TitithoChasers"),
	FOXTROT(getNextId(),	"OdoPalmares"),
	GOLF(getNextId(),		"LuizDawson"),
	HOTEL(getNextId(),		"Lawenznilson");

	private static int idsUsed = 0;
	private int id;
	private String name;

	private static int getNextId()
	{
		return idsUsed++;
	}

	private TeamsIndex(int id, String name)
	{
		this.id = id;
		this.name = name;
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
			case ECHO:
				return ColorBlender.CYAN;
			case FOXTROT:
				return ColorBlender.MAGENTA;
			case GOLF:
				return ColorBlender.BLACK;
			case HOTEL:
				return ColorBlender.WHITE;
			default:
				break;
		}
		return ColorBlender.WHITE;
	}

	public String getName()
	{
		return name;
	}
	
	

}
