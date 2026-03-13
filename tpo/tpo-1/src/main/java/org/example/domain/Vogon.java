package org.example.domain;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class Vogon {
    private final MuscleController muscleController;

    private final Set<Mood> currentMoods = new HashSet<>();

    public Vogon(MuscleController controller) {
        this.muscleController = controller;
        this.currentMoods.add(Mood.NEUTRAL);
    }

    public Smile smile() {
        return muscleController.executeMovement();
    }

    public void shoutAt(List<Prisoner> prisoners) {
        if (prisoners == null || prisoners.isEmpty()) return;

        for (Prisoner p : prisoners) {
            p.receiveShouting();
        }

        currentMoods.remove(Mood.NEUTRAL);
        currentMoods.remove(Mood.TIRED);
        currentMoods.add(Mood.RESTED);
        currentMoods.add(Mood.READY_FOR_NASTINESS);
    }

    public boolean hasMood(Mood mood) {
        return currentMoods.contains(mood);
    }
}