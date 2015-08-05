package scrumtime

import grails.plugins.quartz.GrailsJobClassConstants
import grails.plugins.quartz.JobDescriptor
import grails.transaction.Transactional
import org.quartz.CronScheduleBuilder
import org.quartz.CronTrigger
import org.quartz.TriggerBuilder

@Transactional
class SchedulerService {

    def jobManagerService

    void rebuild() {
        println "------"
        Schedule.findAll().each {
            PlayJob.unschedule(it.name)
            PlayJob.schedule(createTrigger(it.name, it.cronExpression))
            println it
        }
        println "------"
        printInfo()
        println "------"
    }

    void build() {
        println "------"
        Schedule.findAll().each {
            PlayJob.schedule(createTrigger(it.name, it.cronExpression))
            println it
        }
        println "------"
        printInfo()
        println "------"
    }

    private void printInfo() {
        jobManagerService.allJobs.each { k, v ->
            v.each { JobDescriptor jd ->
                println "${jd.name} - ${jd.group} - ${jd.jobDetail}"
                jd.triggerDescriptors.each { td ->
                    println "${td.name} - ${td.group} - ${td.trigger}"
                }

            }
        }
    }

    private CronTrigger createTrigger(String jobName, String cronExpression) {
        TriggerBuilder.newTrigger()
                .withIdentity(jobName, GrailsJobClassConstants.DEFAULT_TRIGGERS_GROUP)
                .withPriority(6)
                .forJob(jobName, GrailsJobClassConstants.DEFAULT_GROUP)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build()
    }
}
