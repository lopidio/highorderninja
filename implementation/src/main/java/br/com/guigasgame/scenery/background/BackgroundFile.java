package br.com.guigasgame.scenery.background;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class BackgroundFile
{
	@XmlElement
	private List<BackgroundItemProperties> backgroundItems;
	
	public BackgroundFile(List<BackgroundItemProperties> backgroundItems)
	{
		this.backgroundItems = backgroundItems;
	}

	public BackgroundFile()
	{
		backgroundItems = new ArrayList<>();
	}
	
	public void addBackgrounItem(BackgroundItemProperties backgroundItem)
	{
		backgroundItems.add(backgroundItem);
	}

	public List<BackgroundItemProperties> getBackgroundItems()
	{
		return backgroundItems;
	}

}
