package adopt.wep.models;

import adopt.data.models.enums.Species;
import adopt.service.models.UserServiceModel;
import org.springframework.web.multipart.MultipartFile;

public class AnimalRescueModel {

    private String species;
    private String name;
    private Integer approximatelyAge;
    private String color;
    private String description;
    private MultipartFile imageUrl;

    public AnimalRescueModel() {
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

    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(MultipartFile imageUrl) {
        this.imageUrl = imageUrl;
    }
}
