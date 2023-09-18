package com.tmvaddin.employee;

import com.tmvaddin.planning.PlanView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.router.Route;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Route("employees" )
@Component
public class EmployeeView extends VerticalLayout {
    private EmployeeService employeeService = EmployeeService.getInstance();
    private Grid<EmployeeDto> grid = new Grid<>(EmployeeDto.class);
    private HorizontalLayout buttons = new HorizontalLayout();
    private Button newEmployeeButton = new Button("New employee" );
    private ModifyEmployeeForm modifyEmployeeForm = new ModifyEmployeeForm(this);
    private NativeButton generatePlanButton = new NativeButton("Generate plan");

    public EmployeeView() {

        buttons.add(newEmployeeButton, generatePlanButton);

        generatePlanButton.addClickListener(e -> {
            var cos = grid.getDataProvider().fetch(new Query<>()).collect(Collectors.toList())
                    .stream()
                    .filter(d -> d.isTemporaryWorker())
                    .collect(Collectors.toList());
            generatePlanButton.getUI().ifPresent(ui -> ui.navigate(PlanView.class));
        });
        grid.removeAllColumns();
        grid.addColumn(EmployeeDto::getName).setHeader("Name");
        grid.addColumn(e -> e.getWorkplaces().size()).setHeader("Trained workplaces");
        grid.addColumn(e -> (int) e.getTeamNumber()).setHeader("Team number");

        grid.addComponentColumn(item -> {
                    Checkbox checkbox = new Checkbox();
                    checkbox.setValue(item.isTemporaryWorker());
                    checkbox.addValueChangeListener(event -> {
                        item.setTemporaryWorker(event.getValue());
                    });
                    return checkbox;
                })
                .setHeader("Temporary Worker" );
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.asSingleSelect().addValueChangeListener(event -> showOrHideEditForm());
        modifyEmployeeForm.getBackButton().addClickListener(e -> {
            modifyEmployeeForm.setVisible(false);
            buttons.setVisible(true);
        });
        newEmployeeButton.addClickListener(event -> {
            modifyEmployeeForm.clearForm();
            buttons.setVisible(false);
            modifyEmployeeForm.setVisible(true);
        });
        grid.setSizeFull();
        add(grid, buttons, modifyEmployeeForm);
        grid.setVisible(true);
        modifyEmployeeForm.setVisible(false);
        setSizeFull();
        refresh();
    }

    public void refresh() {
        grid.setItems(employeeService.getEmployee());
    }

    public void setVisibleEditEmployeeForm(boolean b) {
        modifyEmployeeForm.setVisible(b);
    }


    public void setVisibleButtons(boolean b) {
        buttons.setVisible(b);
    }

    private void showOrHideEditForm() {
        if (grid.asSingleSelect().isEmpty()) {
            modifyEmployeeForm.setVisible(false);
            buttons.setVisible(true);
        } else {
            buttons.setVisible(false);
            modifyEmployeeForm.setVisible(true);
            modifyEmployeeForm.setEmployeeForm(grid.asSingleSelect().getValue());
        }
    }
}
