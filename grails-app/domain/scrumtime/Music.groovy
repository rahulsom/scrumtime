package scrumtime

class Music {

    String filename
    String artist
    String name

    static constraints = {
    }

    @Override
    String toString() {
        "$name - $artist"
    }
}
