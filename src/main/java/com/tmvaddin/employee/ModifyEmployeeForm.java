package com.tmvaddin.employee;

import com.tmvaddin.workplace.WorkplaceDto;
import com.tmvaddin.workplace.WorkplaceService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.data.converter.StringToLongConverter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;

@Component
public class ModifyEmployeeForm extends FormLayout {
    private EmployeeService employeeService = EmployeeService.getInstance();
    private WorkplaceService workplaceService = WorkplaceService.getInstance();
    private EmployeeView employeeView;
    @PropertyId("employeeId" )
    private TextField employeeId = new TextField("ID");
    @PropertyId("name" )
    private TextField nameFiled = new TextField("Name" );
    @PropertyId("workplaces" )
    private CheckboxGroup<WorkplaceDto> workplacesCheckboxGroup = new CheckboxGroup<>("Workplaces" );
    @PropertyId("teamNumber" )
    private NumberField teamNumberField = new NumberField("Team number" );
    @PropertyId("temporaryWorker" )
    private Checkbox temporaryCheckbox = new Checkbox("Temporary" );
    private Button saveChangesButton = new Button("Save changes" );
    private Button backButton = new Button("Back" );
    private Binder<EmployeeDto> binder = new Binder<>(EmployeeDto.class);

    public ModifyEmployeeForm(EmployeeView employeeView) {
        this.employeeView = employeeView;

        saveChangesButton.getStyle().set("background-color", "green" );
        saveChangesButton.getStyle().set("color", "white" );
        backButton.getStyle().set("background-color", "green" );
        backButton.getStyle().set("color", "white" );

        workplacesCheckboxGroup.setItems(workplaceService.getWorkplaces(46));
        employeeId.setReadOnly(true);
        binder.forField(employeeId)
                .withConverter(new StringToLongConverter(""))
                .bind(EmployeeDto::getEmployeeId, EmployeeDto::setEmployeeId);
        binder.bindInstanceFields(this);
        binder.setBean(new EmployeeDto());
        saveChangesButton.addClickListener(e -> {
            var employeeDto = binder.getBean();
            employeeService.saveEmployee(employeeDto);
            employeeView.setVisibleEditEmployeeForm(false);
            employeeView.setVisibleButtons(true);
            employeeView.refresh();
        });
        add(nameFiled, workplacesCheckboxGroup, teamNumberField, temporaryCheckbox, saveChangesButton, backButton);
    }

    public void setEmployeeForm(EmployeeDto employeeDto) {
        employeeId.setValue(String.valueOf(employeeDto.getEmployeeId()));
        nameFiled.setValue(employeeDto.getName());
        teamNumberField.setValue(Double.valueOf(employeeDto.getTeamNumber()));
        temporaryCheckbox.setValue(employeeDto.isTemporaryWorker());
        workplacesCheckboxGroup.setValue(new HashSet<>(employeeDto.getWorkplaces()));
    }

    public Button getBackButton() {
        return backButton;
    }

    public void clearForm() {
        employeeId.setValue("-1");
        nameFiled.setValue("");
        temporaryCheckbox.setValue(false);
        workplacesCheckboxGroup.setValue(Collections.EMPTY_SET);
    }
}
