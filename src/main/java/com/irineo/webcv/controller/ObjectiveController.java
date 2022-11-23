package com.irineo.webcv.controller;

import com.irineo.webcv.model.Objective;
import com.irineo.webcv.model.User;
import com.irineo.webcv.repository.ObjectiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ObjectiveController {
    @Autowired
    private ObjectiveRepository objectiveRepository;

    @GetMapping("/objective")
    public String index(Model model){
        model.addAttribute("objective", new Objective());
        model.addAttribute("objectives", objectiveRepository.findAll());
        return "objectives-view";
    }

    @GetMapping("/objective/all")
    List<Objective> getAll() {
        return objectiveRepository.findAll();
    }

    @GetMapping("/objective/{id}")
    Objective getById(@PathVariable long id) {
        Objective objective = objectiveRepository.findById(id)
                .orElseThrow(() -> new Error("Objective not found"));

        return objective;
    }

    @PostMapping("/objective")
    String create(Objective newObjective) {
        objectiveRepository.save(newObjective);
        return "redirect:/objective";
    }

    @GetMapping("/objective/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Objective objective = objectiveRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid objective Id:" + id));

        model.addAttribute("objective", objective);
        return "update-objective-view";
    }

    @PostMapping("/objective/update/{id}")
    String update(@PathVariable long id, Objective newObjective) {
        Optional<Objective> objectiveData = objectiveRepository.findById(id);

        objectiveData.map(objective -> {
            objective.setName(newObjective.getName());
            objective.setDescription(newObjective.getDescription());
            objective.setReason(newObjective.getName());
            objective.setTargetDate(newObjective.getTargetDate());
            objective.setDateCompleted(newObjective.getDateCompleted());

            return objectiveRepository.save(objective);
        }).orElseThrow(() -> new Error("No Objective data"));

        return "redirect:/objective";
    }

    @GetMapping("/objective/delete/{id}")
    String delete(@PathVariable long id) {
        if(objectiveRepository.existsById(id)) {
            objectiveRepository.deleteById(id);
        }

        return "redirect:/objective";
    }
}
