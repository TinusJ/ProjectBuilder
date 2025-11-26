package com.projectbuilder.view;

import com.projectbuilder.model.DockerComposeConfig;
import com.projectbuilder.model.ProjectModule;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Page 2: Project Modules Selection
 */
public class Page2ProjectModulesView implements WizardPage {
    
    private final DockerComposeConfig.Builder configBuilder;
    private final VerticalLayout layout;
    private CheckboxGroup<ProjectModule> moduleCheckboxGroup;
    private String selectedProjectVersion;
    
    public Page2ProjectModulesView(DockerComposeConfig.Builder configBuilder) {
        this.configBuilder = configBuilder;
        this.layout = new VerticalLayout();
        createContent();
    }
    
    private void createContent() {
        layout.setPadding(true);
        layout.setSpacing(true);
        
        H2 title = new H2("Step 2: Select Project Modules");
        Paragraph description = new Paragraph(
            "Choose one or more modules for your application using checkboxes."
        );
        
        // Module selection with checkboxes
        moduleCheckboxGroup = new CheckboxGroup<>();
        moduleCheckboxGroup.setLabel("Project Modules");
        moduleCheckboxGroup.setItemLabelGenerator(module -> 
            module.displayName() + " - " + module.description()
        );
        
        layout.add(title, description, moduleCheckboxGroup);
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
        moduleCheckboxGroup.setItems(ProjectModule.getModulesForVersion(selectedProjectVersion));
    }
    
    @Override
    public boolean validate() {
        Set<ProjectModule> selected = moduleCheckboxGroup.getSelectedItems();
        if (selected == null || selected.isEmpty()) {
            Notification.show("Please select at least one project module", 3000, Notification.Position.MIDDLE);
            return false;
        }
        return true;
    }
    
    @Override
    public void onNext() {
        Set<ProjectModule> selected = moduleCheckboxGroup.getSelectedItems();
        List<String> moduleIds = selected.stream()
            .map(ProjectModule::id)
            .toList();
        configBuilder.projectModules(moduleIds);
    }
}
