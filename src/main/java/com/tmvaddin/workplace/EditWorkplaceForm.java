package com.tmvaddin.workplace;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;
import org.springframework.stereotype.Component;

@Component
public class EditWorkplaceForm extends FormLayout {

    private WorkplaceView workplaceView;
    private WorkplaceService workplaceService = WorkplaceService.getInstance();
    @PropertyId("workplaceId")
    private TextField idField = new TextField("ID");
    @PropertyId("name")
    private TextField nameField = new TextField("Name" );
    @PropertyId("diffLvl")
    private Select<DifficultyLevel> diffLvlSelect = new Select<>();
    @PropertyId("teamNumber")
    private NumberField teamNumberField = new NumberField("Team number" );
    @PropertyId("speedLine")
    private NumberField speedLineField = new NumberField("Speed line" );
    private Binder<WorkplaceDto> binder = new Binder<>(WorkplaceDto.class);
    private Button saveChangesButton = new Button("Save changes" );
    private Button backButton = new Button("Back" );

    public EditWorkplaceForm(WorkplaceView workplaceView) {
        this.workplaceView = workplaceView;
        saveChangesButton.getStyle().set("background-color", "green" );
        saveChangesButton.getStyle().set("color", "white" );
        backButton.getStyle().set("background-color", "green" );
        backButton.getStyle().set("color", "white" );
        diffLvlSelect.setLabel("Select type" );
        diffLvlSelect.setItems(DifficultyLevel.values());
        diffLvlSelect.setItemLabelGenerator(DifficultyLevel::name);
        teamNumberField.setStep(1);
        speedLineField.setStep(1);

        binder.bindInstanceFields(this);
        binder.setBean(new WorkplaceDto());

        saveChangesButton.addClickListener(e -> {
            var workplaceDto = binder.getBean();
            workplaceService.saveWorkplace(workplaceDto);
            workplaceView.setVisibleEditWorkplaceForm(false);
            workplaceView.setVisibleButtons(true);
            workplaceView.refresh(46);
        });

        add(nameField, diffLvlSelect, teamNumberField, speedLineField, saveChangesButton, backButton);
    }

    public void setWorkplaceForm(WorkplaceDto workplaceDto) {
        this.idField.setValue(String.valueOf(workplaceDto.getWorkplaceId()));
        this.nameField.setValue(workplaceDto.getName());
        this.diffLvlSelect.setValue(workplaceDto.getDiffLvl());
        this.teamNumberField.setValue(Double.valueOf(workplaceDto.getTeamNumber()));
        this.speedLineField.setValue(Double.valueOf(workplaceDto.getSpeedLine()));
    }

    public void clearForm() {
        this.idField.setValue("-1");
        this.nameField.setValue("");
        this.diffLvlSelect.setValue(null);
        this.speedLineField.setValue(0.0);
    }

    public Button getBackButton() {
        return backButton;
    }
}
