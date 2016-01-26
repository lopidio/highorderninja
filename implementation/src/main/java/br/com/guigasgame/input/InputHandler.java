package br.com.guigasgame.input;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Eu sei que deveria ser interface. Mas o JAXB não lida bem com interfaces 
 *
 */
@XmlRootElement
public abstract class InputHandler
{
	abstract boolean handleInput();
}
