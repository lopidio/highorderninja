package br.com.guigasgame.gameobject.projectile;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
class ProjectileProperties
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
	public final float damage;

	public ProjectileProperties()
	{
		numBounces = 0;
		initialSpeed = 0;
		mass = 0;
		radius = 0;
		restitution = 0;
		damage = 0;
	}

	public ProjectileProperties(short numBounces, float initialSpeed,
			float mass, float radius, float restitution, float damage)
	{
		this.numBounces = numBounces;
		this.initialSpeed = initialSpeed;
		this.mass = mass;
		this.radius = radius;
		this.restitution = restitution;
		this.damage = damage;
	}
	
}
