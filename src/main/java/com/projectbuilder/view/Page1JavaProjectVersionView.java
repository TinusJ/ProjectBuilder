package com.projectbuilder.view;

import com.projectbuilder.model.DockerComposeConfig;
import com.projectbuilder.model.JavaVersion;
import com.projectbuilder.model.ProjectVersion;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Page 1: Java Version and Project Version Selection
 */
public class Page1JavaProjectVersionView implements WizardPage {
    
    private final DockerComposeConfig.Builder configBuilder;
    private final VerticalLayout layout;
    private ComboBox<JavaVersion> javaVersionCombo;
    private ComboBox<ProjectVersion> projectVersionCombo;
    
    public Page1JavaProjectVersionView(DockerComposeConfig.Builder configBuilder) {
        this.configBuilder = configBuilder;
        this.layout = new VerticalLayout();
        createContent();
    }
    
    private void createContent() {
        layout.setPadding(true);
        layout.setSpacing(true);
        
        H2 title = new H2("Step 1: Select Java and Project Version");
        Paragraph description = new Paragraph(
            "Choose the Java version and Spring Boot project version for your Docker Compose setup."
        );
        
        // Java version selection
        javaVersionCombo = new ComboBox<>("Java Version");
        javaVersionCombo.setItems(JavaVersion.getAvailableVersions());
        javaVersionCombo.setItemLabelGenerator(JavaVersion::displayName);
        javaVersionCombo.setWidthFull();
        javaVersionCombo.setPlaceholder("Select Java version...");
        
        // Project version selection
        projectVersionCombo = new ComboBox<>("Spring Boot Version");
        projectVersionCombo.setItems(ProjectVersion.getAvailableVersions());
        projectVersionCombo.setItemLabelGenerator(ProjectVersion::displayName);
        projectVersionCombo.setWidthFull();
        projectVersionCombo.setPlaceholder("Select Spring Boot version...");
        
        layout.add(title, description, javaVersionCombo, projectVersionCombo);
    }
    
    @Override
    public Component getContent() {
        return layout;
    }
    
    @Override
    public boolean validate() {
        if (javaVersionCombo.getValue() == null) {
            Notification.show("Please select a Java version", 3000, Notification.Position.MIDDLE);
            return false;
        }
        
        if (projectVersionCombo.getValue() == null) {
            Notification.show("Please select a Spring Boot version", 3000, Notification.Position.MIDDLE);
            return false;
        }
        
        return true;
    }
    
    @Override
    public void onNext() {
        configBuilder.javaVersion(javaVersionCombo.getValue().version());
        configBuilder.projectVersion(projectVersionCombo.getValue().version());
    }
}
