package main.web;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import main.model.Wizard;
import main.service.WizardService;
import main.web.dto.EditProfileRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/profile")
public class ProfileController {


    private final WizardService wizardService;

    public ProfileController(WizardService wizardService) {
        this.wizardService = wizardService;
    }

    @GetMapping()
    public ModelAndView getProfilePage(HttpSession session) {
        UUID userId = (UUID) session.getAttribute("user_id");
        Wizard wizard = wizardService.findById(userId);
        EditProfileRequest editProfileRequest = EditProfileRequest.builder()
                .username(wizard.getUsername())
                .avatarUrl(wizard.getAvatarUrl())
                .build();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile");
        modelAndView.addObject("wizard", wizard);
        modelAndView.addObject("editProfileRequest", editProfileRequest);

        return modelAndView;
    }

    @PostMapping
    public ModelAndView editProfile(@Valid EditProfileRequest editProfileRequest, BindingResult bindingResult, HttpSession session) {
        UUID userId = (UUID) session.getAttribute("user_id");
        Wizard wizard = wizardService.findById(userId);

        if (bindingResult.hasErrors()) {
            return new ModelAndView("profile");
        }

        wizardService.editProfile(editProfileRequest, wizard);

        return new ModelAndView("redirect:/profile");
    }

    @PatchMapping
    public ModelAndView changeAlignment(HttpSession session) {
        UUID userId = (UUID) session.getAttribute("user_id");
        Wizard wizard = wizardService.findById(userId);

        wizardService.changeWizardAlignment(wizard);

        return new ModelAndView("redirect:/profile");
    }


}
