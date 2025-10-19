package main.service;

import main.exception.DomainException;
import main.model.Spell;
import main.model.Wizard;
import main.repository.SpellRepository;
import org.springframework.stereotype.Service;
import main.property.SpellsProperties;
import main.property.SpellsProperties.SpellDetails;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SpellService {


    private final SpellsProperties spellsProperties;
    private final SpellRepository spellRepository;


    public SpellService(SpellsProperties spellsProperties, SpellRepository spellRepository) {
        this.spellsProperties = spellsProperties;
        this.spellRepository = spellRepository;
    }

    public List<SpellDetails> getAvailableSpells(int learnedSpells, Wizard wizard) {
        List<Spell> ownedSpells = wizard.getSpells();
        List<SpellDetails> availableSpells = spellsProperties.getSpells().stream().filter(spellDetails -> spellDetails.getMinLearned() <= learnedSpells).toList();

        if (!availableSpells.isEmpty()) {
            return availableSpells.stream().filter(spellDetails -> ownedSpells.stream().noneMatch(o -> o.getCode().equals(spellDetails.getCode()))).toList();
        } else {
            return availableSpells;
        }
    }

    public List<SpellDetails> getUnavailableSpells(int learnedSpells) {
        return spellsProperties.getSpells().stream().filter(spellDetails -> spellDetails.getMinLearned() > learnedSpells).toList();
    }

    public SpellDetails getSpellByCode(String spellCode) {
        return spellsProperties.getSpells().stream().filter(spellDetails -> spellDetails.getCode().equals(spellCode)).findFirst().orElseThrow(() -> new DomainException("Spell with code " + spellCode + " not found"));
    }

    public Spell createSpell(SpellDetails spellInput, Wizard wizard) {
        Spell spell = generateSpell(spellInput, wizard);

        spellRepository.save(spell);
        return spell;
    }

    public Spell generateSpell(SpellDetails spellInput, Wizard wizard) {
        return Spell.builder()
                .code(spellInput.getCode())
                .name(spellInput.getName())
                .description(spellInput.getDescription())
                .wizard(wizard)
                .power(spellInput.getPower())
                .image(spellInput.getImage())
                .category(spellInput.getCategory())
                .alignment(spellInput.getAlignment())
                .createdOn(LocalDateTime.now())
                .build();
    }

    public void updateSpell(Spell spell) {
        spellRepository.save(spell);
    }
}
