package br.com.guigasgame.gameobject.projectile;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProjectileProperties
{
	@XmlElement
	public final short numBounces;
	@XmlElement
	public final float initialSpeed;
	@XmlElement
	public final float mass;
	@XmlElement
	public final float radius;
	@XmlElement
	public final float restitution;
	@XmlElement
	public final float friction;
	@XmlElement
	public final float damage;
	@XmlElement
	public final float maxDistance;
	@XmlElement
	public final float rangeAngle;

	public ProjectileProperties()
	{
		numBounces = 0;
		initialSpeed = 0;
		mass = 0;
		radius = 0;
		restitution = 0;
		friction = 0;
		damage = 0;
		maxDistance = 0;
		rangeAngle = (float) Math.PI/10;
	}

	public ProjectileProperties(short numBounces, float initialSpeed,
			float mass, float radius, float restitution, float friction, float damage, float maxDistance,float rangeAngle)
	{
		this.numBounces = numBounces;
		this.initialSpeed = initialSpeed;
		this.mass = mass;
		this.radius = radius;
		this.restitution = restitution;
		this.friction = friction;
		this.damage = damage;
		this.maxDistance = maxDistance;
		this.rangeAngle = rangeAngle;
	}
	
}
