package adopt.data.models;

import adopt.data.models.enums.Color;
import adopt.data.models.enums.Species;

import javax.persistence.*;

@Entity()
@Table(name = "animals")
public class Animal extends BaseEntity {

    /**
     *     private Species species;
     *     private String name;
     *     private Integer approximatelyAge;
     *     private Color color;
     *     private String description;
     *     private String imageUrl;
     *     private User founder;
     *     private boolean isAdopted
     */

    @Column(name = "species", nullable = false)
    @Enumerated(EnumType.STRING)
    private Species species;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "approximately_age", nullable = true)
    private Integer approximatelyAge;

    @Column(name = "color", nullable = false)
    @Enumerated(EnumType.STRING)
    private Color color;

    @Column(name = "description", columnDefinition = "TEXT", nullable = true)
    private String description;

    @Column(name = "imageUrl", nullable = true)
    private String imageUrl;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "founder_id", referencedColumnName = "id")
    private User founder;

    @Column(name = "is_adopted", columnDefinition = "tinyint(1) default 0")
    private boolean isAdopted;

    public Animal() {
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public User getFounder() {
        return founder;
    }

    public void setFounder(User founder) {
        this.founder = founder;
    }

    public boolean isAdopted() {
        return isAdopted;
    }

    public void setAdopted(boolean adopted) {
        isAdopted = adopted;
    }
}
