package main.web;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import main.model.Spell;
import main.model.Wizard;
import main.service.SpellService;
import main.service.WizardService;
import main.web.dto.LoginRequest;
import main.web.dto.RegisterRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import main.property.SpellsProperties;
import main.property.SpellsProperties.SpellDetails;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Controller
public class IndexController {


    private final WizardService wizardService;
    private final SpellService spellService;


    public IndexController(WizardService wizardService, SpellService spellService) {
        this.wizardService = wizardService;
        this.spellService = spellService;
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public ModelAndView getRegisterPage() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("register");
        modelAndView.addObject("registerRequest", new RegisterRequest());

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid RegisterRequest registerRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("register");
        }

        wizardService.registerWizard(registerRequest);

        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        modelAndView.addObject("loginRequest", new LoginRequest());

        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login(LoginRequest loginRequest, HttpSession session) {

        Wizard wizard = wizardService.login(loginRequest);
        session.setAttribute("user_id", wizard.getId());

        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/home")
    public ModelAndView getHomePage(HttpSession session) {
        UUID userId = (UUID) session.getAttribute("user_id");
        Wizard wizard = wizardService.findById(userId);
        List<Spell> learnedSpells = wizardService.getLearnedSpells(wizard);
        List<SpellDetails> spellsCanLearn = spellService.getAvailableSpells(wizard.getSpells().size(), wizard);
        List<SpellDetails> spellsCanNotLearn = spellService.getUnavailableSpells(wizard.getSpells().size());


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        modelAndView.addObject("wizard", wizard);
        modelAndView.addObject("learnedSpells", learnedSpells);
        modelAndView.addObject("availableSpells", spellsCanLearn);
        modelAndView.addObject("lockedSpells", spellsCanNotLearn);

        return modelAndView;

    }
}
