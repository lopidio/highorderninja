package br.com.guigasgame.frag;

public class EventWrapper
{
	protected final Object sender;

	public EventWrapper(Object sender)
	{
		this.sender = sender;
	}
	
	public Object getSender()
	{
		return sender;
	}
	
}
