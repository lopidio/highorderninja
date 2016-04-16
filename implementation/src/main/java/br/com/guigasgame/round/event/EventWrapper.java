package br.com.guigasgame.round.event;

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
