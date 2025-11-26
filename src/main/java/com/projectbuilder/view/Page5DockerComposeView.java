package com.projectbuilder.view;

import com.projectbuilder.model.DockerComposeConfig;
import com.projectbuilder.service.DockerComposeGenerator;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

/**
 * Page 5: Docker Compose File Display and Download
 */
public class Page5DockerComposeView implements WizardPage {
    
    private final DockerComposeGenerator composeGenerator;
    private final DockerComposeConfig.Builder configBuilder;
    private final VerticalLayout layout;
    private TextArea composeFileArea;
    private String generatedCompose;
    
    public Page5DockerComposeView(DockerComposeGenerator composeGenerator, 
                                  DockerComposeConfig.Builder configBuilder) {
        this.composeGenerator = composeGenerator;
        this.configBuilder = configBuilder;
        this.layout = new VerticalLayout();
        createContent();
    }
    
    private void createContent() {
        layout.setPadding(true);
        layout.setSpacing(true);
        
        H2 title = new H2("Step 5: Docker Compose File");
        Paragraph description = new Paragraph(
            "Your Docker Compose file has been generated. You can copy it or download it."
        );
        
        // Text area to display the docker-compose.yml content
        composeFileArea = new TextArea("docker-compose.yml");
        composeFileArea.setWidthFull();
        composeFileArea.setHeight("400px");
        composeFileArea.setReadOnly(false); // Allow editing if needed
        composeFileArea.getStyle()
            .set("font-family", "monospace")
            .set("font-size", "12px");
        
        layout.add(title, description, composeFileArea);
    }
    
    @Override
    public Component getContent() {
        return layout;
    }
    
    @Override
    public void onShow() {
        // Generate the Docker Compose file
        DockerComposeConfig config = configBuilder.build();
        generatedCompose = composeGenerator.generateDockerCompose(config);
        composeFileArea.setValue(generatedCompose);
        
        // Add download button if not already present
        if (layout.getComponentCount() == 4) { // Only add once
            addDownloadButton();
        }
    }
    
    private void addDownloadButton() {
        Button copyButton = new Button("Copy to Clipboard", event -> {
            // Note: Clipboard API requires user interaction
            composeFileArea.focus();
            Notification.show("Select all text (Ctrl+A) and copy (Ctrl+C)", 
                3000, Notification.Position.BOTTOM_START);
        });
        copyButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        
        // Create download link
        StreamResource resource = new StreamResource("docker-compose.yml", 
            () -> new ByteArrayInputStream(generatedCompose.getBytes(StandardCharsets.UTF_8)));
        
        Anchor downloadLink = new Anchor(resource, "");
        downloadLink.getElement().setAttribute("download", true);
        
        Button downloadButton = new Button("Download File", event -> {
            downloadLink.getElement().callJsFunction("click");
            Notification.show("Downloading docker-compose.yml", 
                3000, Notification.Position.BOTTOM_START);
        });
        downloadButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        
        layout.add(copyButton, downloadLink, downloadButton);
    }
    
    @Override
    public boolean validate() {
        return true; // No validation needed
    }
    
    @Override
    public void onComplete() {
        Notification.show("Docker Compose file generated successfully!", 
            5000, Notification.Position.MIDDLE);
    }
}
