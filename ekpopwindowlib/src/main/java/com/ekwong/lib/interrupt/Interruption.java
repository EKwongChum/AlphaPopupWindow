package com.ekwong.lib.interrupt;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ekwong
 */
public class Interruption {

    private Set<BackInterruption> backInterruptions = new HashSet<>();

    public boolean addBackInterruption(BackInterruption interruption) {
        return backInterruptions.add(interruption);
    }

    public boolean isEnable() {
        return !backInterruptions.isEmpty();
    }

    public void exec() {
        for (BackInterruption interruption : backInterruptions) {
            interruption.interrupt();
        }
        backInterruptions.clear();
    }
}
