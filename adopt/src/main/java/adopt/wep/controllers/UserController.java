package adopt.wep.controllers;

import adopt.service.models.UserServiceModel;
import adopt.service.services.UserService;
import adopt.utils.validation.user.UserEditValidator;
import adopt.utils.validation.user.UserRegisterValidator;
import adopt.wep.controllers.base.BaseController;
import adopt.wep.models.UserEditModel;
import adopt.wep.models.UserProfileViewModel;
import adopt.wep.models.UserRegisterModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    private final UserEditValidator userEditValidator;
    private final UserRegisterValidator userRegisterValidator;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, UserEditValidator userEditValidator, UserRegisterValidator userRegisterValidator) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userEditValidator = userEditValidator;
        this.userRegisterValidator = userRegisterValidator;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(ModelAndView modelAndView) {
        modelAndView.addObject("model", new UserRegisterModel());

        return super.view("users/register", modelAndView);
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(ModelAndView modelAndView, @ModelAttribute UserRegisterModel model, BindingResult bindingResult) {

        this.userRegisterValidator.validate(model, bindingResult);

        if(bindingResult.hasErrors()){
            modelAndView.addObject("model", model);

            return super.view("/users/register", modelAndView);
        }

        this.userService.register(this.modelMapper.map(model, UserServiceModel.class));

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

    @GetMapping("/edit-profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfile(Principal principal, ModelAndView modelAndView, @ModelAttribute(name = "model") UserEditModel model){
        String username = principal.getName();
        UserServiceModel userServiceModel = this.userService.findUserByUsername(username);

        model = this.modelMapper.map(userServiceModel, UserEditModel.class);
        model.setPassword(null);

        modelAndView.addObject("model", model);

        return super.view("/users/edit-profile", modelAndView);
    }

    @PostMapping("/edit-profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView edinProfileConfirm(ModelAndView modelAndView, @ModelAttribute(name = "model") UserEditModel model,
                                           BindingResult bindingResult) {

        this.userEditValidator.validate(model, bindingResult);

        if(bindingResult.hasErrors()) {
            model.setPassword(null);
            model.setConfirmPassword(null);
            model.setOldPassword(null);
            modelAndView.addObject("model", model);

            return super.view("/users/edit-profile", modelAndView);
        }

        UserServiceModel userServiceModel = this.modelMapper.map(model, UserServiceModel.class);
        this.userService.editUserProfile(userServiceModel, model.getOldPassword());

        return super.redirect("/users/profile");
    }

}
