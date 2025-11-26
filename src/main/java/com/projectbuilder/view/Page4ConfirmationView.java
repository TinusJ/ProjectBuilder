package com.projectbuilder.view;

import com.projectbuilder.model.DockerComposeConfig;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Page 4: Confirmation Page
 */
public class Page4ConfirmationView implements WizardPage {
    
    private final DockerComposeConfig.Builder configBuilder;
    private final VerticalLayout layout;
    private VerticalLayout summaryLayout;
    
    public Page4ConfirmationView(DockerComposeConfig.Builder configBuilder) {
        this.configBuilder = configBuilder;
        this.layout = new VerticalLayout();
        createContent();
    }
    
    private void createContent() {
        layout.setPadding(true);
        layout.setSpacing(true);
        
        H2 title = new H2("Step 4: Confirm Configuration");
        Paragraph description = new Paragraph(
            "Please review your configuration before generating the Docker Compose file."
        );
        
        summaryLayout = new VerticalLayout();
        summaryLayout.setPadding(true);
        summaryLayout.getStyle()
            .set("border", "1px solid #ddd")
            .set("border-radius", "4px")
            .set("background-color", "#f9f9f9");
        
        layout.add(title, description, summaryLayout);
    }
    
    @Override
    public Component getContent() {
        return layout;
    }
    
    @Override
    public void onShow() {
        // Build a temporary config to display
        DockerComposeConfig config = configBuilder.build();
        
        summaryLayout.removeAll();
        
        H3 configTitle = new H3("Configuration Summary");
        summaryLayout.add(configTitle);
        
        addSummaryItem("Java Version", config.getJavaVersion());
        addSummaryItem("Project Version", config.getProjectVersion());
        
        // Display multiple modules
        if (config.getProjectModules() != null && !config.getProjectModules().isEmpty()) {
            addSummaryItem("Project Modules", String.join(", ", config.getProjectModules()));
        } else {
            addSummaryItem("Project Modules", "None selected");
        }
        
        addSummaryItem("Database Type", config.getDatabaseType());
        addSummaryItem("Database Version", config.getDatabaseVersion());
        
        if (config.getBackupFileName() != null && !config.getBackupFileName().isEmpty()) {
            addSummaryItem("Backup File", config.getBackupFileName() + 
                " (" + (config.getBackupFile().length / 1024) + " KB)");
        } else {
            addSummaryItem("Backup File", "None");
        }
    }
    
    private void addSummaryItem(String label, String value) {
        Paragraph item = new Paragraph();
        item.getStyle().set("margin", "5px 0");
        item.add(new com.vaadin.flow.component.html.Span(label + ": "));
        
        com.vaadin.flow.component.html.Span valueSpan = new com.vaadin.flow.component.html.Span(value);
        valueSpan.getStyle().set("font-weight", "bold");
        item.add(valueSpan);
        
        summaryLayout.add(item);
    }
    
    @Override
    public boolean validate() {
        return true; // No validation needed on confirmation page
    }
}
