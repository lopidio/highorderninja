package br.com.guigasgame.round.hud.fix;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import br.com.guigasgame.drawable.TextDrawable;
import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.resourcemanager.FontResourceManager;

public class TextTimerHud extends TimerStaticHud
{
	private final Text text;
	private final Vector2f positionRatio;

	public TextTimerHud(Vector2f positionRatio, int totalTime)
	{
		super(totalTime);
		this.positionRatio = positionRatio;
		this.text = new Text();
		Font font = FontResourceManager.getInstance().getResource(FilenameConstants.getTimerCounterFontFilename());
		text.setFont(font);
		addToDrawableList(new TextDrawable(text));
		updateText(totalTime);
	}
	
	@Override
	public void setViewSize(Vector2i size)
	{
		text.setCharacterSize(size.x/50);
		text.setPosition(size.x*positionRatio.x,
						size.y*positionRatio.y);
		text.setOrigin(text.getLocalBounds().width / 2, text.getLocalBounds().height/ 2);
	}

	@Override
	protected void halfTime()
	{
		text.setColor(Color.RED);
	}
	
	@Override
	protected void timeOut()
	{
		text.setColor(Color.BLACK);
	}
	
	@Override
	protected void onDecimalChange(int currentValue)
	{
		updateText(currentValue);
	}

	private void updateText(int currentValue)
	{
		String newString = String.format("%02d:%02d", Math.abs(currentValue)/60, Math.abs(currentValue)%60);
		if (currentValue <= 0)
		{
			newString = "-" + newString;
		}
		text.setString(newString);
	}


}
