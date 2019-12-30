package adopt.service.services;

import adopt.service.models.AnimalServiceModel;

import java.io.IOException;

public interface AnimalService {

    AnimalServiceModel rescue(AnimalServiceModel model, String username) throws IOException;

    AnimalServiceModel findAnimalById(String id);

}
