package ae.rta.dls.backend.web.rest.vm.trf;

public class ExamTrainingVM {

    private Integer minimumTheoryLessonsRequired;

    private Integer minimumDrivingLessonsRequired;

    public Integer getMinimumTheoryLessonsRequired() {
        return minimumTheoryLessonsRequired;
    }

    public void setMinimumTheoryLessonsRequired(Integer minimumTheoryLessonsRequired) {
        this.minimumTheoryLessonsRequired = minimumTheoryLessonsRequired;
    }

    public Integer getMinimumDrivingLessonsRequired() {
        return minimumDrivingLessonsRequired;
    }

    public void setMinimumDrivingLessonsRequired(Integer minimumDrivingLessonsRequired) {
        this.minimumDrivingLessonsRequired = minimumDrivingLessonsRequired;
    }
}
