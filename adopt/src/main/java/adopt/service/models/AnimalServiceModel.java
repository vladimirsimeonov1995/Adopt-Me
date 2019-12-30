package adopt.service.models;

import org.springframework.web.multipart.MultipartFile;

public class AnimalServiceModel {

    private String id;
    private String species;
    private String name;
    private Integer approximatelyAge;
    private String color;
    private String description;
    private String imageUrl;
    private UserServiceModel founder;

    public AnimalServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getApproximatelyAge() {
        return approximatelyAge;
    }

    public void setApproximatelyAge(Integer approximatelyAge) {
        this.approximatelyAge = approximatelyAge;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String  getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String  imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UserServiceModel getFounder() {
        return founder;
    }

    public void setFounder(UserServiceModel founder) {
        this.founder = founder;
    }
}
