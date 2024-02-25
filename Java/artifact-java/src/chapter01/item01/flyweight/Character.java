package chapter01.item01.flyweight;

public class Character {

    private char value;

    private String color;

//    private String fontFamily;
//    private String fontSize;

    private Font font;

    public Character(char value, String color, Font font) {
        this.value = value;
        this.color = color;
        this.font = font;
    }
}
