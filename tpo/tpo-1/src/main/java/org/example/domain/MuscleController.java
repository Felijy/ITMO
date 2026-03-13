package org.example.domain;

public class MuscleController {
    private final boolean memoryFunctioning;

    public MuscleController(boolean memoryFunctioning) {
        this.memoryFunctioning = memoryFunctioning;
    }

    public Smile executeMovement() {
        if (memoryFunctioning) {
            return Smile.FAST;
        } else {
            return Smile.SLOW;
        }
    }
}