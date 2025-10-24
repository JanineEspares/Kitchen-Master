package mypackage;

public class Story {
    private String characterName;
    private String text;
    private String imagePath;

    public Story(String characterName, String text, String imagePath) {
        this.characterName = characterName;
        this.text = text;
        this.imagePath = imagePath;
    }

    public String getCharacterName() { return characterName; }
    public String getText() { return text; }
    public String getImagePath() { return imagePath; }
}
