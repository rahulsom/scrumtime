package scrumtime

import org.quartz.JobExecutionContext

class PlayJob {

    MusicService musicService

    final void execute(JobExecutionContext context) {
        musicService.playMusicForSchedule(context.trigger.key.getName())
    }
}
