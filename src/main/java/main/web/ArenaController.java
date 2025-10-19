package main.web;

import jakarta.servlet.http.HttpSession;
import main.model.House;
import main.model.Wizard;
import main.service.WizardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/arena")
public class ArenaController {

    private final WizardService wizardService;

    public ArenaController(WizardService wizardService) {
        this.wizardService = wizardService;
    }

    @GetMapping
    public ModelAndView getArenaPage(HttpSession session) {
        UUID userId = (UUID) session.getAttribute("user_id");
        Wizard wizard = wizardService.findById(userId);

        List<Wizard> gryffindorMembers = wizardService.getAllWizardsByHouse(House.GRYFFINDOR);
        List<Wizard> hufflepuffMembers = wizardService.getAllWizardsByHouse(House.HUFFLEPUFF);
        List<Wizard> ravenclawMembers = wizardService.getAllWizardsByHouse(House.RAVENCLAW);
        List<Wizard> slytherinMembers = wizardService.getAllWizardsByHouse(House.SLYTHERIN);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("arena");
        modelAndView.addObject("wizard", wizard);
        modelAndView.addObject("gryffindorMembers", gryffindorMembers);
        modelAndView.addObject("hufflepuffMembers", hufflepuffMembers);
        modelAndView.addObject("ravenclawMembers", ravenclawMembers);
        modelAndView.addObject("slytherinMembers", slytherinMembers);


        return modelAndView;
    }
}
