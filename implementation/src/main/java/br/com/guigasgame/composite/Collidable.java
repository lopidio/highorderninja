package br.com.guigasgame.composite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

public abstract class Collidable implements Composible<Collidable>
{

	BodyDef bodyDef;
	Body body;
	List<Collidable> collidableChild;
	
	public Collidable(Vec2 position, List<Collidable> collidableChild)
	{
		bodyDef = new BodyDef();
		bodyDef.position = position;
		this.collidableChild = collidableChild;
	}	
	
	public Collidable(Vec2 position)
	{
		bodyDef = new BodyDef();
		bodyDef.position = position;
		collidableChild = new ArrayList<Collidable>();
	}
	
	@Override
	public final void addChild(Collidable child)
	{
		collidableChild.add(child);
	}

	@Override
	public final boolean hasChildrenToAdd()
	{
		return collidableChild.size() > 0;
	}

	@Override
	public final Collection<Collidable> getChildrenList()
	{
		return collidableChild;
	}

	@Override
	public final void clearChildren()
	{
		collidableChild.clear();
	}
	
	protected abstract void editBodyDef(BodyDef bodyDef);

	public abstract void endContact(Collidable collidable, Contact contact);

	public abstract void beginContact(Collidable collidable, Contact contact);

	protected abstract void editBody(Body body);

	public final BodyDef getBodyDef()
	{
		return bodyDef;
	}
	
	public final void attachBody(World world)
	{
		editBodyDef(bodyDef);
		body = world.createBody(bodyDef);
		body.setUserData(this);
		editBody(body);
	}

	public final Body getBody()
	{
		return body;
	}

}
