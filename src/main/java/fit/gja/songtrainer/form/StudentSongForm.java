package fit.gja.songtrainer.form;

import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;

/**
 * Form for adding song to student
 */
public class StudentSongForm {
    private User student;
    private Song song;

    public StudentSongForm(User student, Song song) {
        this.student = student;
        this.song = song;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}