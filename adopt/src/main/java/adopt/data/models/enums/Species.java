package adopt.data.models.enums;

public enum Species {

    DOG("Dog"),
    CAT("Cat");

    private final String fieldDescription;

    private Species(String value) {
        fieldDescription = value;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }

}
