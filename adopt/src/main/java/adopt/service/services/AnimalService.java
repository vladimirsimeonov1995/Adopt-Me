package adopt.service.services;

import adopt.service.models.AnimalServiceModel;

import java.io.IOException;
import java.util.List;

public interface AnimalService {

    AnimalServiceModel rescue(AnimalServiceModel model, String username) throws IOException;

    AnimalServiceModel findAnimalById(String id);

    List<AnimalServiceModel> findAllAnimalsForAdoption();

    void makeAnimalInAdoptedRequest(AnimalServiceModel animalServiceModel);
}
