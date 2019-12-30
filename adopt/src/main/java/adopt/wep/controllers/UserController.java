package adopt.wep.controllers;

import adopt.service.models.UserServiceModel;
import adopt.service.services.UserService;
import adopt.wep.controllers.base.BaseController;
import adopt.wep.models.UserProfileViewModel;
import adopt.wep.models.UserRegisterModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register() {
        return super.view("users/register");
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(@ModelAttribute UserRegisterModel model) {
        if (!model.getPassword().equals(model.getConfirmPassword())){
            return super.redirect("users/register");
        }
        if (this.userService.register(this.modelMapper.map(model, UserServiceModel.class)) == null){
            throw new IllegalArgumentException("Register user failed");
        }

        return super.redirect("/login");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login(){
        return super.view("/users/login");
    }

    @GetMapping("/profile/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView profile(@PathVariable String id, ModelAndView modelAndView){

        UserServiceModel userServiceModel = this.userService.findUserById(id);
        UserProfileViewModel model = this.modelMapper.map(userServiceModel, UserProfileViewModel.class);
        modelAndView.addObject("model", model);

        return super.view("/users/profile", modelAndView);
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView myProfile(Principal principal){
        String username = principal.getName();
        UserServiceModel userServiceModel = this.userService.findUserByUsername(username);

        return super.redirect("/users/profile/" + userServiceModel.getId());
    }

}
