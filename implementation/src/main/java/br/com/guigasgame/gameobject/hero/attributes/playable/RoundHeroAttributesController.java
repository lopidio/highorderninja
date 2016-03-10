package br.com.guigasgame.gameobject.hero.attributes.playable;

import java.util.ArrayList;
import java.util.List;

import br.com.guigasgame.updatable.UpdatableFromTime;

public class RoundHeroAttributesController implements UpdatableFromTime
{
	private final RoundHeroAttributes fullValueAttributes;
	private final RoundHeroAttributes currentValueAttributes;
	private final List<HeroRoundAttributesListener> listeners;
	
	public RoundHeroAttributesController(RoundHeroAttributes attributes) 
	{
		this.fullValueAttributes = attributes.clone();
		currentValueAttributes = attributes.clone();
		listeners = new ArrayList<HeroRoundAttributesListener>();
	}
	
	public void addLife(int lifeToAdd)
	{
		int newLife = currentValueAttributes.getLife() + lifeToAdd;
		currentValueAttributes.setLife(Math.min(newLife, fullValueAttributes.getLife()));
		notifyListenersLifeHasChanged();
	}
	
	public void removeLife(int lifeToRemove)
	{
		int newLife = currentValueAttributes.getLife() - lifeToRemove;
		currentValueAttributes.setLife(Math.max(newLife, 0));
		notifyListenersLifeHasChanged();
	}
	
	public void refillShuriken()
	{
		currentValueAttributes.setNumShurikens(fullValueAttributes.getNumShurikens());
		notifyListenersShurikenNumberHasChanged();
	}
	
	public void shootShuriken()
	{
		int newShurikenNumber = currentValueAttributes.getNumShurikens() - 1;
		currentValueAttributes.setNumShurikens(Math.max(newShurikenNumber, 0));
		notifyListenersShurikenNumberHasChanged();
		currentValueAttributes.shurikenTimeInterval = 0;
	}
	
	public void refillSmokeBomb()
	{
		currentValueAttributes.setNumSmokeBomb(fullValueAttributes.getNumSmokeBomb());
		notifyListenersSmokeBombNumberHasChanged();
	}
	

	public void shootSmokeBomb()
	{
		int newSmokeBombNumber = currentValueAttributes.getLife() - 0;
		currentValueAttributes.setNumSmokeBomb(Math.max(newSmokeBombNumber, 0));
		notifyListenersSmokeBombNumberHasChanged();
		currentValueAttributes.smokeBombTimeInterval = 0;
	}

	private void notifyListenersSmokeBombNumberHasChanged() 
	{
		for (HeroRoundAttributesListener heroRoundAttributesListener : listeners) 
		{
			heroRoundAttributesListener.smokeBombChanged(currentValueAttributes.getNumShurikens(), fullValueAttributes.getNumShurikens());
		}
	}
	
	private void notifyListenersShurikenNumberHasChanged()
	{
		for (HeroRoundAttributesListener heroRoundAttributesListener : listeners) 
		{
			heroRoundAttributesListener.shurikenNumChanged(currentValueAttributes.getNumShurikens(), fullValueAttributes.getNumShurikens());
		}
	}
	
	private void notifyListenersLifeHasChanged()
	{
		for (HeroRoundAttributesListener heroRoundAttributesListener : listeners) 
		{
			heroRoundAttributesListener.lifeChanged(currentValueAttributes.getLife(), fullValueAttributes.getLife());
		}
	}

	@Override
	public void update(float deltaTime) 
	{
		if (currentValueAttributes.shurikenTimeInterval <= fullValueAttributes.shurikenTimeInterval)
			currentValueAttributes.shurikenTimeInterval += deltaTime;
		if (currentValueAttributes.smokeBombTimeInterval <= fullValueAttributes.smokeBombTimeInterval)
			currentValueAttributes.smokeBombTimeInterval += deltaTime;
	}
	
	public boolean canShootShuriken() 
	{
		return currentValueAttributes.shurikenTimeInterval >= fullValueAttributes.shurikenTimeInterval && currentValueAttributes.numShurikens > 0;
	}

	public boolean canShootSmokeBomb() 
	{
		return currentValueAttributes.smokeBombTimeInterval >= fullValueAttributes.smokeBombTimeInterval && currentValueAttributes.numSmokeBomb > 0;
	}
	
	public void addListener(HeroRoundAttributesListener attributesListener)
	{
		listeners.add(attributesListener);
	}
	
	public void removeListener(HeroRoundAttributesListener attributesListener)
	{
		listeners.remove(attributesListener);
	}
	
	
}
