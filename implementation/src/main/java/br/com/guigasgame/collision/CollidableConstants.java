package br.com.guigasgame.collision;

import br.com.guigasgame.collision.CollidableFilter.Category;

public class CollidableConstants 
{
	///What I am
	private final static Category playersCategory		= new Category(0x0F);
	private final static Category sceneryCategory 		= new Category(0x0020);
	private final static Category projectileCategory	= new Category(0x0040);
	
	///What I collide with
	public final static CollidableFilter heroCollidableFilter			= playersCategory.collidesWith(sceneryCategory).and(projectileCategory);
	public final static CollidableFilter sceneryCollidableFilter 		= sceneryCategory.collidesWithEverything();
	public final static CollidableFilter projectileCollidableFilter 	= projectileCategory.collidesWith(sceneryCategory).and(playersCategory);
	
	private final static int maxPlayers = 4;
	
	public static Category getPlayerCategory(int playerID)
	{
		int playersMaskValue = 0;
		for (int i = 0; i < maxPlayers; ++i)
		{
			playersMaskValue = (playersMaskValue << 1) + 1;
		}
		return playersCategory.matching(playerID);
	}
	
}
