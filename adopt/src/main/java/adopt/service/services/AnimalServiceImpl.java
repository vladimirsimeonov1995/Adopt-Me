package adopt.service.services;

import adopt.data.models.Animal;
import adopt.data.models.User;
import adopt.data.repositories.AnimalRepository;
import adopt.service.models.AnimalServiceModel;
import adopt.service.models.UserServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final UserService userService;

    private final AnimalRepository animalRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public AnimalServiceImpl(UserService userService, AnimalRepository animalRepository, ModelMapper modelMapper) {
        this.userService = userService;
        this.animalRepository = animalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AnimalServiceModel rescue(AnimalServiceModel model, String username) throws IOException {
        Animal entityToBeSaved = this.modelMapper.map(model, Animal.class);
        entityToBeSaved.setFounder(this.modelMapper.map(this.userService.findUserByUsername(username), User.class));

        AnimalServiceModel savedModel = null;
        try {
            savedModel = this.modelMapper.map(this.animalRepository.saveAndFlush(entityToBeSaved), AnimalServiceModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return savedModel;

    }

    @Override
    public AnimalServiceModel findAnimalById(String id) {

        Animal entity = this.animalRepository.findById(id).orElse(null);

        if(entity == null) {
            throw new IllegalArgumentException("No animal found with that ID");
        }

        AnimalServiceModel animalServiceModel = this.modelMapper.map(entity, AnimalServiceModel.class);
        animalServiceModel.setFounder(this.modelMapper.map(entity.getFounder(), UserServiceModel.class));

        return animalServiceModel;

    }
}
