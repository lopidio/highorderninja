package br.com.guigasgame.color;

import java.util.ArrayList;
import java.util.List;

import br.com.guigasgame.interpolator.InterpolatorFromTime;
import br.com.guigasgame.interpolator.LinearInterpolatorFromTime;

public class ColorLinearInterpolator extends ColorInterpolator
{
	private List<InterpolatorFromTime<Float>> interpolators;
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
		for( InterpolatorFromTime<Float> interpolator : interpolators )
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
		interpolators.add(new LinearInterpolatorFromTime(sourceColor.getR(), duration).interpolateTo(color.getR()));
		interpolators.add(new LinearInterpolatorFromTime(sourceColor.getR(), duration).interpolateTo(color.getG()));
		interpolators.add(new LinearInterpolatorFromTime(sourceColor.getR(), duration).interpolateTo(color.getB()));
		interpolators.add(new LinearInterpolatorFromTime(sourceColor.getR(), duration).interpolateTo(color.getA()));
	}

	@Override
	public void interpolateFromColor(ColorBlender color, float duration)
	{
		if (!hasFinished())
		{
			forceFinalize();
		}
			
		this.duration = duration;
		interpolators.add(new LinearInterpolatorFromTime(color.getR(), duration).interpolateTo(sourceColor.getR()));
		interpolators.add(new LinearInterpolatorFromTime(color.getR(), duration).interpolateTo(sourceColor.getG()));
		interpolators.add(new LinearInterpolatorFromTime(color.getR(), duration).interpolateTo(sourceColor.getB()));
		interpolators.add(new LinearInterpolatorFromTime(color.getR(), duration).interpolateTo(sourceColor.getA()));
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
