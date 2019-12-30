package adopt.wep.controllers;

import adopt.service.models.AnimalServiceModel;
import adopt.service.services.AnimalService;
import adopt.service.services.CloudinaryService;
import adopt.wep.controllers.base.BaseController;
import adopt.wep.models.AnimalInfoViewModel;
import adopt.wep.models.AnimalRescueModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/animals")
public class AnimalController extends BaseController {

    private final AnimalService animalService;

    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    @Autowired
    public AnimalController(AnimalService animalService, CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.animalService = animalService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/rescue")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView rescue(){
        return super.view("animals/rescue");
    }

    @PostMapping("/rescue")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView rescueConfirm(@ModelAttribute AnimalRescueModel model, Principal principal) throws IOException {

        AnimalServiceModel animalServiceModel = this.modelMapper.map(model, AnimalServiceModel.class);
        animalServiceModel.setImageUrl(this.cloudinaryService.uploadImage(model.getImageUrl()));

        String username = principal.getName();
        AnimalServiceModel savedAnimal = this.animalService.rescue(animalServiceModel, username);

        return super.redirect("/animals/" + savedAnimal.getId());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView animalInformation(@PathVariable String id, ModelAndView modelAndView){

        AnimalServiceModel animalServiceModel = this.animalService.findAnimalById(id);

        AnimalInfoViewModel model = this.modelMapper.map(animalServiceModel, AnimalInfoViewModel.class);
        model.setFounderId(animalServiceModel.getFounder().getId());

        modelAndView.addObject("model", model);

        return super.view("animals/animal-information", modelAndView);
    }

}
