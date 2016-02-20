package br.com.guigasgame.collision;

public class CollidableConstants 
{
	///What I am
	public final static CollidableCategory herosCategory		= CollidableConstants.getAllPlayerCategory();
	public final static CollidableCategory sceneryCategory 		= CollidableConstants.getNextCategory();
	public final static CollidableCategory projectileCategory	= CollidableConstants.getNextCategory();
	public final static CollidableCategory ropeBodyCategory			= CollidableConstants.getNextCategory();
	
	public static CollidableCategory getPlayerCategory(int playerID)
	{
		return herosCategory.matching(1 << (playerID - 1));
	}
	
	public static CollidableCategory getOtherPlayersCategory(int playerID)
	{
		return herosCategory.matching(~(1 << (playerID - 1)));
	}

	private static int categoriesUsed = 0; 
	private final static int NUM_MAX_PLAYERS = 4;
	
	public static CollidableCategory getAllPlayerCategory()
	{
		int playersMask = 1;
		for (int i = 0; i < NUM_MAX_PLAYERS - 1; ++i) 
		{
			playersMask = (playersMask << 1) + 1;
		}
		return new CollidableCategory(playersMask);
	}
	
	public static CollidableCategory getNextCategory()
	{
		return new CollidableCategory((++categoriesUsed) << NUM_MAX_PLAYERS );
	}	
	
	///What I collide with
	private final static CollidableFilter sceneryCollidableFilter 					= CollidableFilterManipulator.createFromCategory(CollidableConstants.sceneryCategory).collidesWithEveryThing();
	private final static CollidableFilter projectileCollidableFilter 				= CollidableFilterManipulator.createFromCategory(CollidableConstants.projectileCategory).collidesWith(CollidableConstants.herosCategory).and(CollidableConstants.sceneryCategory);
	private final static CollidableFilter ropeBodyCollidableFilter 					= CollidableFilterManipulator.createFromCategory(CollidableConstants.ropeBodyCategory).collidesWith(CollidableConstants.projectileCategory).and(CollidableConstants.sceneryCategory);

	public static CollidableFilter getPlayerFilter(int playerID)
	{
		return CollidableFilterManipulator.createFromCategory(CollidableConstants.getPlayerCategory(playerID)).collidesWith(CollidableConstants.projectileCategory).and(CollidableConstants.sceneryCategory).and(CollidableConstants.herosCategory);
	}

	public static CollidableFilter getRopeBodyCollidableFilter()
	{
		return ropeBodyCollidableFilter.clone();
	}

	public static CollidableFilter getProjectileCollidableFilter()
	{
		return projectileCollidableFilter.clone();
	}

	public static CollidableFilter getSceneryCollidablefilter()
	{
		return sceneryCollidableFilter.clone();
	}
	
}
