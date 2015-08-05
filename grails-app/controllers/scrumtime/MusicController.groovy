package scrumtime

import grails.rest.RestfulController
import grails.transaction.Transactional
import grails.web.http.HttpHeaders
import org.springframework.web.multipart.MultipartFile

import java.security.MessageDigest

import static org.springframework.http.HttpStatus.CREATED

class MusicController extends RestfulController<Music>{

    MusicService musicService

    MusicController() {
        super(Music)
    }

    @Override
    @Transactional
    Object save() {
        if(handleReadOnly()) {
            return
        }
        def instance = new Music()
        bindData instance, getObjectToBind()

        MultipartFile file = params.file

        MessageDigest digest = MessageDigest.getInstance("MD5")
        digest.update(file.bytes)
        def md5 = new BigInteger(1, digest.digest()).toString(16).padLeft(32, '0')

        instance.filename = "${md5}.mp3"
        file.transferTo(musicService.fileForName(instance.filename))

        instance.validate()
        if (instance.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond instance.errors, view:'create' // STATUS CODE 422
            return
        }

        instance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: "${resourceName}.label".toString(), default: resourceClassName), instance.id])
                redirect instance
            }
            '*' {
                response.addHeader(HttpHeaders.LOCATION,
                        grailsLinkGenerator.link( resource: this.controllerName, action: 'show',id: instance.id, absolute: true,
                                namespace: hasProperty('namespace') ? this.namespace : null ))
                respond instance, [status: CREATED]
            }
        }
    }
}
