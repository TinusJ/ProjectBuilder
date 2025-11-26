package com.projectbuilder.view;

import com.vaadin.flow.component.Component;

/**
 * Abstract base class for wizard pages.
 * Defines the contract that all wizard pages must follow.
 */
public interface WizardPage {
    
    /**
     * Gets the content component to display for this page.
     * 
     * @return the page content
     */
    Component getContent();
    
    /**
     * Validates the current page inputs.
     * 
     * @return true if validation passes, false otherwise
     */
    boolean validate();
    
    /**
     * Called when the page is shown.
     */
    default void onShow() {
        // Default implementation does nothing
    }
    
    /**
     * Called when moving to the next page.
     */
    default void onNext() {
        // Default implementation does nothing
    }
    
    /**
     * Called when the wizard is completed (only on last page).
     */
    default void onComplete() {
        // Default implementation does nothing
    }
}
