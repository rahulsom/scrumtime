package scrumtime

import grails.transaction.Transactional
import javazoom.jl.player.Player

@Transactional
class MusicService {

    private static final File DATA_DIR = new File('data')

    void playMusicForSchedule(String name) {
        def schedule = Schedule.findByName(name)
        def music = schedule.music
        println music

        new File(DATA_DIR, "${music.filename}").withInputStream { stream ->
            new Player(stream).play()
        }

        println "Done"
    }

    File fileForName(String name) {
        if (DATA_DIR.exists()) {
            if (DATA_DIR.isDirectory())
                return new File(DATA_DIR, name)
            else
                throw new InternalError("data is not a dir")
        } else {
            DATA_DIR.mkdirs()
            return new File(DATA_DIR, name)
        }

    }

}
