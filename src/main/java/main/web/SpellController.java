package main.web;

import jakarta.servlet.http.HttpSession;
import main.model.Wizard;
import main.property.SpellsProperties;
import main.property.SpellsProperties.SpellDetails;
import main.service.SpellService;
import main.service.WizardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/spells")
public class SpellController {

    private final SpellService spellService;
    private final WizardService wizardService;

    public SpellController(SpellService spellService, WizardService wizardService) {
        this.spellService = spellService;
        this.wizardService = wizardService;
    }

    @PostMapping()
    public ModelAndView learnSpell(@RequestParam("spell-code") String spellCode, HttpSession session) {
        SpellDetails spell = spellService.getSpellByCode(spellCode);
        UUID userId = (UUID) session.getAttribute("user_id");
        Wizard wizard = wizardService.findById(userId);

        wizardService.learnSpell(spell, wizard);

        return new ModelAndView("redirect:/home");
    }
}
