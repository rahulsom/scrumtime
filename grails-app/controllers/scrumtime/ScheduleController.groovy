package scrumtime

import grails.rest.RestfulController
import net.redhogs.cronparser.CronExpressionDescriptor

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

    def preview(String id) {
        def retval = [expression: id, description:CronExpressionDescriptor.getDescription(id)]
        respond(retval)
    }
}
