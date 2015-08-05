package scrumtime

import org.quartz.JobExecutionContext

class PlayJob {

    MusicService playService
    final void execute(JobExecutionContext context) {
        playService.playMusicForSchedule(context.trigger.key.getName())
    }
}
