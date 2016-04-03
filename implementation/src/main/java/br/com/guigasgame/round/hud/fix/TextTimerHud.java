package br.com.guigasgame.round.hud.fix;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.drawable.TextDrawable;
import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.resourcemanager.FontResourceManager;

public class TextTimerHud extends TimerStaticHud
{
	private final Text text;

	public TextTimerHud(Vector2f position, int totalTime)
	{
		super(totalTime);
		this.text = new Text();
		Font font = FontResourceManager.getInstance().getResource(FilenameConstants.getTimerCounterFontFilename());
		text.setFont(font);
		text.setCharacterSize(30);

		text.setPosition(position);
		updateText(totalTime);
		text.setOrigin(text.getLocalBounds().width / 2, text.getLocalBounds().height/ 2);
		addToDrawableList(new TextDrawable(text));
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
