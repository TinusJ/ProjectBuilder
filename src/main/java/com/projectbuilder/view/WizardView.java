package com.projectbuilder.view;

import com.projectbuilder.model.DockerComposeConfig;
import com.projectbuilder.service.DockerComposeGenerator;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

/**
 * Main wizard view that coordinates the 5-page wizard flow.
 * Follows the Template Method pattern for extensibility.
 */
@Route("")
public class WizardView extends VerticalLayout {
    
    private final DockerComposeGenerator composeGenerator;
    private final DockerComposeConfig.Builder configBuilder;
    
    private int currentStep = 0;
    private WizardPage[] pages;
    
    private VerticalLayout contentLayout;
    private HorizontalLayout buttonLayout;
    private Button previousButton;
    private Button nextButton;
    
    public WizardView(DockerComposeGenerator composeGenerator) {
        this.composeGenerator = composeGenerator;
        this.configBuilder = DockerComposeConfig.builder();
        
        setSizeFull();
        setPadding(true);
        
        initializePages();
        createLayout();
        showCurrentPage();
    }
    
    private void initializePages() {
        pages = new WizardPage[]{
            new Page1JavaProjectVersionView(configBuilder),
            new Page2ProjectModulesView(configBuilder),
            new Page3DatabaseConfigView(configBuilder),
            new Page4ConfirmationView(configBuilder),
            new Page5DockerComposeView(composeGenerator, configBuilder)
        };
    }
    
    private void createLayout() {
        // Content area
        contentLayout = new VerticalLayout();
        contentLayout.setSizeFull();
        contentLayout.setPadding(false);
        
        // Navigation buttons
        previousButton = new Button("Previous", e -> previousPage());
        previousButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        
        nextButton = new Button("Next", e -> nextPage());
        nextButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        
        buttonLayout = new HorizontalLayout(previousButton, nextButton);
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        
        add(contentLayout, buttonLayout);
        setFlexGrow(1, contentLayout);
    }
    
    private void showCurrentPage() {
        contentLayout.removeAll();
        
        WizardPage currentPage = pages[currentStep];
        contentLayout.add(currentPage.getContent());
        
        // Update buttons
        previousButton.setEnabled(currentStep > 0);
        
        if (currentStep == pages.length - 1) {
            nextButton.setText("Finish");
        } else {
            nextButton.setText("Next");
        }
        
        // Let the page know it's being shown
        currentPage.onShow();
    }
    
    private void nextPage() {
        if (!pages[currentStep].validate()) {
            return;
        }
        
        pages[currentStep].onNext();
        
        if (currentStep < pages.length - 1) {
            currentStep++;
            showCurrentPage();
        } else {
            // Wizard complete
            pages[currentStep].onComplete();
        }
    }
    
    private void previousPage() {
        if (currentStep > 0) {
            currentStep--;
            showCurrentPage();
        }
    }
}
