package com.projectbuilder.view;

import com.projectbuilder.model.DatabaseOption;
import com.projectbuilder.model.DatabaseVersion;
import com.projectbuilder.model.DockerComposeConfig;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;

/**
 * Page 3: Database Configuration
 */
public class Page3DatabaseConfigView implements WizardPage {
    
    private final DockerComposeConfig.Builder configBuilder;
    private final VerticalLayout layout;
    private ComboBox<DatabaseOption> databaseCombo;
    private ComboBox<DatabaseVersion> versionCombo;
    private Upload backupUpload;
    private MemoryBuffer buffer;
    private byte[] backupFileData;
    private String backupFileName;
    
    public Page3DatabaseConfigView(DockerComposeConfig.Builder configBuilder) {
        this.configBuilder = configBuilder;
        this.layout = new VerticalLayout();
        createContent();
    }
    
    private void createContent() {
        layout.setPadding(true);
        layout.setSpacing(true);
        
        H2 title = new H2("Step 3: Database Configuration");
        Paragraph description = new Paragraph(
            "Select the database type and version. Optionally upload a backup file to restore."
        );
        
        // Database selection
        databaseCombo = new ComboBox<>("Database Type");
        databaseCombo.setItems(DatabaseOption.getAvailableDatabases());
        databaseCombo.setItemLabelGenerator(DatabaseOption::displayName);
        databaseCombo.setWidthFull();
        databaseCombo.setPlaceholder("Select database type...");
        
        // Version selection - updates based on database selection
        versionCombo = new ComboBox<>("Database Version");
        versionCombo.setItemLabelGenerator(DatabaseVersion::displayName);
        versionCombo.setWidthFull();
        versionCombo.setPlaceholder("Select version...");
        versionCombo.setEnabled(false);
        
        // Listen for database selection to update versions
        databaseCombo.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                versionCombo.setItems(DatabaseVersion.getVersionsForDatabase(event.getValue().type()));
                versionCombo.setEnabled(true);
                versionCombo.clear();
            } else {
                versionCombo.setEnabled(false);
                versionCombo.clear();
            }
        });
        
        // Backup file upload
        buffer = new MemoryBuffer();
        backupUpload = new Upload(buffer);
        backupUpload.setAcceptedFileTypes(".sql", ".dump", ".gz");
        backupUpload.setMaxFiles(1);
        backupUpload.setMaxFileSize(50 * 1024 * 1024); // 50MB
        backupUpload.setWidthFull();
        
        backupUpload.addSucceededListener(event -> {
            try {
                backupFileData = buffer.getInputStream().readAllBytes();
                backupFileName = event.getFileName();
                Notification.show("File uploaded: " + backupFileName, 3000, Notification.Position.BOTTOM_START);
            } catch (Exception e) {
                Notification.show("Error uploading file: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        });
        
        Paragraph uploadNote = new Paragraph("Optional: Upload a database backup file (SQL, dump, or compressed)");
        uploadNote.getStyle().set("font-size", "0.9em");
        uploadNote.getStyle().set("color", "gray");
        
        layout.add(title, description, databaseCombo, versionCombo, uploadNote, backupUpload);
    }
    
    @Override
    public Component getContent() {
        return layout;
    }
    
    @Override
    public boolean validate() {
        if (databaseCombo.getValue() == null) {
            Notification.show("Please select a database type", 3000, Notification.Position.MIDDLE);
            return false;
        }
        
        if (versionCombo.getValue() == null) {
            Notification.show("Please select a database version", 3000, Notification.Position.MIDDLE);
            return false;
        }
        
        return true;
    }
    
    @Override
    public void onNext() {
        configBuilder.databaseType(databaseCombo.getValue().type());
        configBuilder.databaseVersion(versionCombo.getValue().version());
        
        if (backupFileData != null) {
            configBuilder.backupFile(backupFileData);
            configBuilder.backupFileName(backupFileName);
        }
    }
}
