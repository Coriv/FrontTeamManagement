package com.tmvaddin.planning;

import com.tmvaddin.workplace.WorkplaceDto;
import com.tmvaddin.workplace.WorkplaceService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("plan" )
public class PlanView extends VerticalLayout {
    private PlanMapper mapper = new PlanMapper();
    private PlanService planService = PlanService.getInstance();
    private WorkplaceService workplaceService = WorkplaceService.getInstance();
    private Grid<DailyPlanDto> gridDailyPlan = new Grid<>(DailyPlanDto.class);
    private Grid<PlanDto> gridPlan = new Grid<>(PlanDto.class);
    private HorizontalLayout buttonsDailyPlan = new HorizontalLayout();
    private Button generatePlanButton = new Button("Generate Plan");
    private HorizontalLayout buttonsDayPlan = new HorizontalLayout();
    private List<DailyPlanDto> plans;

    public PlanView() {
        plans = planService.preparePlans();
        buttonsDailyPlan.add(generatePlanButton);
        gridDailyPlan.setItems(plans);
        gridDailyPlan.setSelectionMode(Grid.SelectionMode.MULTI);
        gridDailyPlan.removeAllColumns();


        gridDailyPlan.addColumn("dayPlanId" ).setHeader("ID" );
        gridDailyPlan.addComponentColumn(item -> createWorkplaceSelectColumn(item, 0)).setHeader("Post 1" );
        gridDailyPlan.addComponentColumn(item -> createWorkplaceSelectColumn(item, 1)).setHeader("Post 2" );
        gridDailyPlan.addComponentColumn(item -> createWorkplaceSelectColumn(item, 2)).setHeader("Post 3" );
        gridDailyPlan.addComponentColumn(item -> createWorkplaceSelectColumn(item, 3)).setHeader("Post 4" );

        generatePlanButton.addClickListener(event -> {
            gridDailyPlan.setVisible(false);
            buttonsDailyPlan.setVisible(false);
            System.out.println("PLAN: \n" + plans);
            var plans = generatePlan(this.plans);
            var mapped = mapper.mapToPlanDtoList(plans);
            gridPlan.setItems(mapped);
            gridPlan.setVisible(true);
        });

        gridDailyPlan.setVisible(true);
        gridPlan.setVisible(false);
        add(gridDailyPlan, buttonsDailyPlan, gridPlan);
        setSizeFull();
    }

    private List<DailyPlanDto> generatePlan(List<DailyPlanDto> dailyPlans) {
        return planService.callForPlan(dailyPlans);
    }

    private Component createWorkplaceSelectColumn(DailyPlanDto dailyPlanDto, int postNumber) {
        Select<WorkplaceDto> select = new Select<>();
        var collection = workplaceService.getWorkplaces(46);
        select.setItems(collection);
        select.addValueChangeListener(event -> {
            var plan = plans.stream()
                    .filter(p -> p.getEmployeeDto().equals(dailyPlanDto.getEmployeeDto()))
                    .findFirst()
                    .get();
            plan.getWorkplaces().set(postNumber, event.getValue());
            System.out.println(event.getValue());
        });
        return select;
    }
}

