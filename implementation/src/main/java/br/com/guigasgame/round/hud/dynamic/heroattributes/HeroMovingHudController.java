package br.com.guigasgame.round.hud.dynamic.heroattributes;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

import com.google.common.eventbus.Subscribe;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.camera.Followable;
import br.com.guigasgame.frag.DiedFragEventWrapper;
import br.com.guigasgame.frag.SpawnEventWrapper;
import br.com.guigasgame.round.event.EventCentralMessenger;
import br.com.guigasgame.round.hud.controller.HudObject;

public abstract class HeroMovingHudController extends HudObject
{
	protected Followable followable;
	protected List<HeroAttributeMoveableHud> heroAttributesList;
	private boolean enabled;
	
	public HeroMovingHudController(Followable gameHero)
	{
		this.followable = gameHero;
		heroAttributesList = new ArrayList<>();
		enabled = true;
		EventCentralMessenger.getInstance().subscribe(this);
	}
	
	@Subscribe public void onSpawnEvent(SpawnEventWrapper spawnEventWrapper) 
	{
		if ((Followable)(spawnEventWrapper.getSender()) == followable)
		{
			enabled = true;
			updatePosition();
		}
	}

	@Subscribe public void onDiedEvent(DiedFragEventWrapper spawnEventWrapper) 
	{
		if ((Followable)(spawnEventWrapper.getSender()) == followable)
			enabled = false;
	}

	private void updatePosition()
	{
		if (enabled)
		{
			final Vector2f position = WorldConstants.physicsToSfmlCoordinates(followable.getPosition());
			for( HeroAttributeMoveableHud moveableHud : heroAttributesList )
			{
				moveableHud.updatePosition(position);
			}
		}
	}
	
	
	@Override
	public void update(float deltaTime)
	{
		if (enabled)
		{
			updatePosition();
			for( HeroAttributeMoveableHud moveableHud : heroAttributesList )
			{
				moveableHud.update(deltaTime);
			}
		}
	}
	
	@Override
	public void destroy()
	{
		heroAttributesList.clear();
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		if (enabled)
		{
			for( HeroAttributeMoveableHud attributeBarBellowHud : heroAttributesList )
			{
				attributeBarBellowHud.draw(renderWindow);
			}
		}
	}


}
