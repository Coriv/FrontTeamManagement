package com.tmvaddin.workplace;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.stereotype.Component;

@Route("workplaces" )
@Component
public class WorkplaceView extends VerticalLayout {

    private final WorkplaceService workplaceService = WorkplaceService.getInstance();
    private Grid<WorkplaceDto> grid = new Grid<>(WorkplaceDto.class);
    private EditWorkplaceForm editWorkplaceForm = new EditWorkplaceForm(this);
    private HorizontalLayout horizontalButtons = new HorizontalLayout();
    private Button addWorkplaceButton = new Button("ADD Workplace" );

    public WorkplaceView() {
        horizontalButtons.add(addWorkplaceButton);
        addWorkplaceButton.addClickListener(event -> setAddWorkplaceButtonAction());
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setColumns("name", "diffLvl", "teamNumber", "speedLine" );
        grid.asSingleSelect().addValueChangeListener(event -> showOrHideEditForm());
        grid.setSizeFull();
        editWorkplaceForm.getBackButton().addClickListener(e -> {
            editWorkplaceForm.setVisible(false);
            horizontalButtons.setVisible(true);
        });
        add(grid, horizontalButtons, editWorkplaceForm);
        editWorkplaceForm.setVisible(false);
        grid.setVisible(true);
        setSizeFull();
        refresh(46);
    }

    private void showOrHideEditForm() {
        if (grid.asSingleSelect().isEmpty()) {
            editWorkplaceForm.setVisible(false);
            horizontalButtons.setVisible(true);
        } else {
            horizontalButtons.setVisible(false);
            editWorkplaceForm.setVisible(true);
            editWorkplaceForm.setWorkplaceForm(grid.asSingleSelect().getValue());
        }
    }

    private void setAddWorkplaceButtonAction() {
        editWorkplaceForm.clearForm();
        horizontalButtons.setVisible(false);
        editWorkplaceForm.setVisible(true);
    }

    public void setVisibleEditWorkplaceForm(boolean b) {
        editWorkplaceForm.setVisible(b);
    }

    public void setVisibleButtons(boolean b) {
        horizontalButtons.setVisible(b);
    }

    public void refresh(int teamNumber) {
        grid.setItems(workplaceService.getWorkplaces(teamNumber));
    }
}
