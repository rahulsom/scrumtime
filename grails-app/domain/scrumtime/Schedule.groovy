package scrumtime

class Schedule {

    String cronExpression
    String name

    Music music

    static constraints = {
        music nullable: true
    }

    @Override
    String toString() {
        "$name ($cronExpression)"
    }
}
