package scrumtime

import grails.rest.RestfulController

class ScheduleController extends RestfulController<Schedule> {
    SchedulerService schedulerService

    ScheduleController() {
        super(Schedule)
    }

    @Override
    Object save() {
        def retval = super.save()
        schedulerService.rebuild()
        return retval
    }

    @Override
    Object update() {
        def retval = super.update()
        schedulerService.rebuild()
        return retval
    }
}
