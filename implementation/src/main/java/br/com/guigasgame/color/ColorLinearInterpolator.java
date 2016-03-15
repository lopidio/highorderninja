package br.com.guigasgame.color;

import java.util.ArrayList;
import java.util.List;

import br.com.guigasgame.interpolator.Interpolator;
import br.com.guigasgame.interpolator.LinearInterpolator;

public class ColorLinearInterpolator extends ColorInterpolator
{
	private List<Interpolator> interpolators;
	private ColorBlender currentColor;
	
	public ColorLinearInterpolator(ColorBlender sourceColor)
	{
		super(sourceColor);
		currentColor = sourceColor.clone();
		interpolators = new ArrayList<>();
	}

	@Override
	public void update(float deltaTime)
	{
		if (interpolators.isEmpty())
			return;
		
		for (int i = 0; i < interpolators.size(); ++i)
		{
			interpolators.get(i).update(deltaTime);
		}
		
		updateColor();

		verifyFinish();
	}

	private void updateColor()
	{
		if (interpolators.size() == 4)
		{
			currentColor.setR(interpolators.get(0).getCurrent());
			currentColor.setG(interpolators.get(1).getCurrent());
			currentColor.setB(interpolators.get(2).getCurrent());
			currentColor.setA(interpolators.get(3).getCurrent());
		}
	}
	
	private void verifyFinish()
	{
		if (hasFinished())
		{
			interpolators.clear();
		}
	}

	@Override
	public ColorBlender getCurrentColor()
	{
		return currentColor;
	}

	@Override
	public boolean hasFinished()
	{
		for( Interpolator interpolator : interpolators )
		{
			if (!interpolator.hasFinished())
				return false;
		}
		return true;
	}
	
	@Override
	public void interpolateToColor(ColorBlender color, float duration)
	{
		this.duration = duration;
		interpolators.add(new LinearInterpolator(sourceColor.getR(), color.getR(), duration));
		interpolators.add(new LinearInterpolator(sourceColor.getG(), color.getG(), duration));
		interpolators.add(new LinearInterpolator(sourceColor.getB(), color.getB(), duration));
		interpolators.add(new LinearInterpolator(sourceColor.getA(), color.getA(), duration));
	}

	@Override
	public void interpolateFromColor(ColorBlender color, float duration)
	{
		if (!hasFinished())
		{
			forceFinalize();
		}
			
		this.duration = duration;
		interpolators.add(new LinearInterpolator(color.getR(), sourceColor.getR(), duration));
		interpolators.add(new LinearInterpolator(color.getG(), sourceColor.getG(), duration));
		interpolators.add(new LinearInterpolator(color.getB(), sourceColor.getB(), duration));
		interpolators.add(new LinearInterpolator(color.getA(), sourceColor.getA(), duration));
	}

	private void forceFinalize()
	{
		currentColor = new ColorBlender(interpolators.get(0).getDestiny(),
										interpolators.get(1).getDestiny(),
										interpolators.get(2).getDestiny(),
										interpolators.get(3).getDestiny());
		interpolators.clear();
	}

}
