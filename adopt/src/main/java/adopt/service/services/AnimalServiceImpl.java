package adopt.service.services;

import adopt.data.models.Animal;
import adopt.data.repositories.AnimalRepository;
import adopt.service.models.AnimalServiceModel;
import adopt.utils.mapper.ModelMapperCustomImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final UserService userService;

    private final AnimalRepository animalRepository;

    private final ModelMapperCustomImpl modelMapper;


    @Autowired
    public AnimalServiceImpl(UserService userService, AnimalRepository animalRepository, ModelMapperCustomImpl modelMapper) {
        this.userService = userService;
        this.animalRepository = animalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AnimalServiceModel rescue(AnimalServiceModel model, String username) throws IOException {

        Animal entityToBeSaved = this.modelMapper.mapAnimalServiceModelToAnimal(model, this.userService.findUserByUsername(username));

        AnimalServiceModel savedModel = this.modelMapper.map(this.animalRepository.saveAndFlush(entityToBeSaved), AnimalServiceModel.class);

        return savedModel;

    }

    @Override
    public AnimalServiceModel findAnimalById(String id) {

        Animal entity = this.animalRepository.findById(id).orElse(null);

        if (entity == null) {
            throw new IllegalArgumentException("No animal found with that ID");
        }

        AnimalServiceModel animalServiceModel = this.modelMapper.mapAnimalToAnimalServiceModel(entity);

        return animalServiceModel;

    }

    @Override
    public List<AnimalServiceModel> findAllAnimalsForAdoption() {

        List<AnimalServiceModel> animalsForAdoption = this.animalRepository
                .findAllByIsAdopted(false)
                .stream()
                .map(e -> this.modelMapper.map(e, AnimalServiceModel.class))
                .collect(Collectors.toList());

        return animalsForAdoption;
    }

    @Override
    public void makeAnimalInAdoptedRequest(AnimalServiceModel animalServiceModel) {
        Animal animal = this.animalRepository.findById(animalServiceModel.getId()).orElseThrow(
                () -> new IllegalArgumentException("No animal with this id!")
        );

        animal.setAdopted(true);

        this.animalRepository.saveAndFlush(animal);
    }
}
