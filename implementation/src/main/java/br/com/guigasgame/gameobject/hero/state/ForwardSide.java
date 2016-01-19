package br.com.guigasgame.gameobject.hero.state;

public enum ForwardSide {
	LEFT(-1), RIGHT(1);

	int value;
	ForwardSide(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
