package com.github.redigermany.sechsnimmt.controller;

import com.github.redigermany.sechsnimmt.view.PlayCard;

/**
 * Simple observer.
 * @author Max Kruggel
 */
public interface PlayCardObserver {
//    protected PlayCard subject;

    void doPick(PlayCard tab);
}
