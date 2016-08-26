import grails.util.Environment
import org.springframework.web.context.support.WebApplicationContextUtils
import scrumtime.Music
import scrumtime.Schedule

class BootStrap {

    def init = { servletContext ->
        if (Environment.current == Environment.DEVELOPMENT) {
            def m1 = new Music(artist: "The Champs", name: "Tequila", filename: "Tequila.mp3").save()
            def m2 = new Music(artist: "AR Rahman", name: "Oruvan Oruvan", filename: "OruvanOruvan.mp3").save(flush: true)

            def s1 = new Schedule(name: 'Courier', cronExpression: '0 59 9 ? * * *', music: m1).save()
            def s2 = new Schedule(name: 'Allegro', cronExpression: '0 14 10 ? * * *', music: m2).save(flush: true)
        }

        def ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext)
        ctx['schedulerService'].rebuild()

    }
    def destroy = {
    }
}
