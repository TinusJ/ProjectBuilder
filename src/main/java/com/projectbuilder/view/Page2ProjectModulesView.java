package com.projectbuilder.view;

import com.projectbuilder.model.DockerComposeConfig;
import com.projectbuilder.model.ProjectModule;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Page 2: Project Modules Selection
 */
public class Page2ProjectModulesView implements WizardPage {
    
    private final DockerComposeConfig.Builder configBuilder;
    private final VerticalLayout layout;
    private ComboBox<ProjectModule> moduleCombo;
    private String selectedProjectVersion;
    
    public Page2ProjectModulesView(DockerComposeConfig.Builder configBuilder) {
        this.configBuilder = configBuilder;
        this.layout = new VerticalLayout();
        createContent();
    }
    
    private void createContent() {
        layout.setPadding(true);
        layout.setSpacing(true);
        
        H2 title = new H2("Step 2: Select Project Module");
        Paragraph description = new Paragraph(
            "Choose the main Spring Boot module for your application."
        );
        
        // Module selection
        moduleCombo = new ComboBox<>("Project Module");
        moduleCombo.setItemLabelGenerator(module -> 
            module.displayName() + " - " + module.description()
        );
        moduleCombo.setWidthFull();
        moduleCombo.setPlaceholder("Select a module...");
        
        layout.add(title, description, moduleCombo);
    }
    
    @Override
    public Component getContent() {
        return layout;
    }
    
    @Override
    public void onShow() {
        // Update modules based on the selected project version
        // This demonstrates extensibility - modules change based on version
        if (selectedProjectVersion == null) {
            selectedProjectVersion = "3.2.0"; // Default
        }
        moduleCombo.setItems(ProjectModule.getModulesForVersion(selectedProjectVersion));
    }
    
    @Override
    public boolean validate() {
        if (moduleCombo.getValue() == null) {
            Notification.show("Please select a project module", 3000, Notification.Position.MIDDLE);
            return false;
        }
        return true;
    }
    
    @Override
    public void onNext() {
        configBuilder.projectModule(moduleCombo.getValue().id());
    }
}
