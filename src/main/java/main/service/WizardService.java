package main.service;

import jakarta.transaction.Transactional;
import main.exception.DomainException;
import main.model.*;
import main.property.SpellsProperties;
import main.property.SpellsProperties.SpellDetails;
import main.repository.WizardRepository;
import main.web.dto.EditProfileRequest;
import main.web.dto.LoginRequest;
import main.web.dto.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WizardService {

    private final WizardRepository wizardRepository;
    private final PasswordEncoder passwordEncoder;
    private final SpellsProperties spellsProperties;
    private final SpellService spellService;

    public WizardService(WizardRepository wizardRepository, PasswordEncoder passwordEncoder, SpellsProperties spellsProperties, SpellService spellService) {
        this.wizardRepository = wizardRepository;
        this.passwordEncoder = passwordEncoder;
        this.spellsProperties = spellsProperties;
        this.spellService = spellService;
    }

    @Transactional
    public void registerWizard(RegisterRequest registerRequest) {
        Wizard wizard = createWizard(registerRequest);
        addRandomSpellWhenRegister(wizard);
    }

    private Wizard createWizard(RegisterRequest registerRequest) {
        Wizard wizard = Wizard.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .avatarUrl(registerRequest.getAvatarUrl())
                .alignment(registerRequest.getAlignment())
                .house(registerRequest.getHouse())
                .spells(new ArrayList<>())
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

        wizardRepository.save(wizard);

        return wizard;
    }

    private void addRandomSpellWhenRegister(Wizard wizard) {
        Random random = new Random();
        List<SpellDetails> spellsWithZeroMinLearn = spellsProperties.getSpells().stream().filter(spellDetails -> spellDetails.getMinLearned() == 0).toList();
        int randomSpellIndex = random.nextInt(0, spellsWithZeroMinLearn.size());

        Spell spell = spellService.createSpell(spellsWithZeroMinLearn.get(randomSpellIndex), wizard);
        wizard.getSpells().add(spell);

        updateWizard(wizard);
    }

    public Wizard login(LoginRequest loginRequest) {
        Wizard wizard = wizardRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new DomainException("Wizard with username " + loginRequest.getUsername() + " not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), wizard.getPassword())) {
            throw new RuntimeException("Username or password are incorrect");
        }

        return wizard;
    }

    public Wizard findById(UUID userId) {
        return wizardRepository.findById(userId).orElseThrow(() -> new DomainException("Wizard with id " + userId + " not found"));
    }

    public List<Spell> getLearnedSpells(Wizard wizard) {
        return wizard.getSpells()
                .stream().sorted(Comparator.comparing(Spell::getPower).reversed())
                .collect(Collectors.toList());
    }

    public void updateWizard(Wizard wizard) {
        wizardRepository.save(wizard);
    }

    public void editProfile(EditProfileRequest editProfileRequest, Wizard wizard) {
        wizard.setUsername(editProfileRequest.getUsername());
        wizard.setAvatarUrl(editProfileRequest.getAvatarUrl());
        wizard.setUpdatedOn(LocalDateTime.now());

        updateWizard(wizard);
    }

    public void changeWizardAlignment(Wizard wizard) {
        wizard.setAlignment(WizardAlignment.DARK);
        wizard.setUpdatedOn(LocalDateTime.now());

        updateWizard(wizard);
    }

    public List<Wizard> getAllWizardsByHouse(House house) {
        List<Wizard> wizards = wizardRepository.findAllByHouse(house);

        wizards.sort(Comparator.comparing(Wizard::getTotalPower)
                .reversed()
                .thenComparing(Wizard::getUsername));

        return wizards;
    }

    @Transactional
    public void learnSpell(SpellDetails spellInput, Wizard wizard) {
        Spell spell = spellService.generateSpell(spellInput, wizard);

        if (!wizard.getSpells().stream().anyMatch(s -> s.getCode().equals(spell.getCode()))) {
            wizard.getSpells().add(spell);

            if (wizard.getAlignment().equals(WizardAlignment.LIGHT) && spellInput.getAlignment().equals(SpellAlignment.DARK)) {
                wizard.setAlignment(WizardAlignment.DARK);
                wizard.setUpdatedOn(LocalDateTime.now());
            }

            updateWizard(wizard);
            spellService.updateSpell(spell);
        }

    }
}
