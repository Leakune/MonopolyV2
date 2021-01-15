package src.game;

public enum Effect {
	BEGIN("Begin", "It's the beginning, everyone start with 5 coins!", 5),
	EMPTY("Empty", "Nothing happened.", 0),
	BONUS("Bonus", "You gain 6 coins!", 6),
	LOOSE("Loose", "You loose 2 coins...", 2),
	PEN4A("Pen4a", "What the *family friendly game*, everyone loose 1 coin!", 1);

	private final String name;
	private final String message;
	private final int value;
	
	Effect(String name, String message, int value) {
		this.name = name;
		this.message = message;
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public String getMessage() {
		return this.message;
	}

	public int getValue() {
		return this.value;
	}
	
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("|----Name: ");
        sb.append(getName());
        sb.append("----|\n|----Message: ");
        sb.append(getMessage());
        sb.append("----|\n|----");
        sb.append("Price of buying/loosing/getting: ");
        sb.append(getValue());
        sb.append(" coins----|");
        return sb.toString();
    }

}
