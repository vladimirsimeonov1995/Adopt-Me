package adopt.data.models.enums;

public enum Color {

    BLACK("Black"),
    GRAY("Gray"),
    BROWN("Brown"),
    WHITE("White"),
    ORANGE("Orange"),
    SILVER("Silver"),
    GOLD("Gold"),
    GREEN("Green"),
    YELLOW("Yellow"),
    MIXED("Mixed"),
    Other("Other");

    private final String fieldDescription;

    private Color(String value) {
        fieldDescription = value;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }

}
