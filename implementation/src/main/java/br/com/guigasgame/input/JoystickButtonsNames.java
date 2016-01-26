package br.com.guigasgame.input;


public enum JoystickButtonsNames
{
	SQUARE(0, 0),
	CROSS(1, 1),
	CIRCLE(2, 2),
	TRIANGLE(3, 3),

	LEFT_ONE(4, 4),
	LEFT_TWO(5, 5),
	LEFT_THREE(6, 6),
	
	RIGHT_ONE(7, 7),
	RIGHT_TWO(8, 9),
	RIGHT_THREE(9, 8),
	
	START(10, 11),
	SELECT(11, 10);
	
	private int ds3Code;
	private int xBoxCode;

	private JoystickButtonsNames(int ds3Code, int xBoxCode)
	{
		this.ds3Code = ds3Code;
		this.xBoxCode = xBoxCode;
	}

	public int getDs3Code()
	{
		return ds3Code;
	}

	public int getxBoxCode()
	{
		return xBoxCode;
	}
	

}
