package br.com.guigasgame.color;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import org.jsfml.graphics.Color;

@XmlAccessorType(XmlAccessType.NONE)
public class ColorBlender
{
	public static final ColorBlender RED = new ColorBlender(255, 0, 0);
	public static final ColorBlender GREEN = new ColorBlender(0, 255, 0);
	public static final ColorBlender BLUE = new ColorBlender(0, 0, 255);
	public static final ColorBlender BLACK = new ColorBlender(0, 0, 0);
	public static final ColorBlender WHITE = new ColorBlender(255, 255, 255);
	public static final ColorBlender GRAY = new ColorBlender(128, 128, 128);
	public static final ColorBlender CYAN = new ColorBlender(0, 255, 255);
	public static final ColorBlender MAGENTA = new ColorBlender(255, 0, 255);
	public static final ColorBlender YELLOW = new ColorBlender(255, 255, 0);
	
	@XmlAttribute
	private float r;
	@XmlAttribute
	private float g;
	@XmlAttribute
	private float b;
	@XmlAttribute
	private float a;

	public ColorBlender()
	{
		this(0,0,0,0);
	}

	public ColorBlender(float r, float g, float b)
	{
		this(r, g, b, 255);
	}
	
	public ColorBlender(float r, float g, float b, float a)
	{
		setR(r);
		setG(g);
		setB(b);
		setA(a);
	}
	
	public float getR()
	{
		return r;
	}
	
	public ColorBlender setR(float f)
	{
		this.r = Math.min(255, f);
		return this;
	}

	public float getG()
	{
		return g;
	}
	
	public ColorBlender setG(float g)
	{
		this.g = Math.min(255, g);
		return this;
	}
	
	public float getB()
	{
		return b;
	}
	
	public ColorBlender setB(float b)
	{
		this.b = Math.min(255, b);
		return this;
	}
	
	public float getA()
	{
		return a;
	}

	public ColorBlender setA(float a)
	{
		this.a = Math.min(255, a);
		return this;
	}

	public Color getSfmlColor()
	{
		return new Color((int)r, (int)g, (int)b, (int)a);
	}
	
	public ColorBlender lighten(float factor)
	{
		return new ColorBlender(r*factor, g*factor, b*factor, a);
	}

	public ColorBlender darken(float factor)
	{
		return lighten(1/factor);
	}
	
	public ColorBlender add(ColorBlender other)
	{
		return new ColorBlender((r + other.r)/2, (g + other.g)/2, (b + other.b)/2);
	}

	public ColorBlender sub(ColorBlender other)
	{
		return new ColorBlender((r - other.r)/2, (g - other.g)/2, (b - other.b)/2);
	}
	
	public ColorBlender inverse()
	{
		return new ColorBlender(255 - r, 255 - g, 255 - b);
	}

	public ColorBlender grayScale()
	{
		final float average = (r + g + b)/3; 
		return new ColorBlender(average, average, average);
	}

	public boolean componentEqualsTo(ColorBlender destinyColor)
	{
		return 	r == destinyColor.r &&
				g == destinyColor.g &&
				b == destinyColor.b;
	}

	@Override
	protected ColorBlender clone()
	{
		return new ColorBlender(r, g, b, a);
	}

	public ColorBlender makeTranslucid(float factor)
	{
		if (factor <= 0)
		{
			factor = 0.001f;
		}
		return clone().setA(a/factor);
	}
	
}
