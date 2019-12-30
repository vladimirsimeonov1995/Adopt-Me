package adopt.wep.models;

import java.util.List;

public class UserProfileViewModel {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private List<AnimalUserProfileViewModel> rescuedAnimals;

    public UserProfileViewModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<AnimalUserProfileViewModel> getRescuedAnimals() {
        return rescuedAnimals;
    }

    public void setRescuedAnimals(List<AnimalUserProfileViewModel> rescuedAnimals) {
        this.rescuedAnimals = rescuedAnimals;
    }
}
