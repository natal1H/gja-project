package fit.gja.songtrainer.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "song")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "artist")
    private String artist;

    @Enumerated(EnumType.STRING)
    @Column(name = "instrument")
    private Instrument instrument;

    @Enumerated(EnumType.STRING)
    @Column(name = "tuning")
    private Tuning tuning;

    @Column(name = "length")
    private int length;

    @Column(name = "times_played")
    private int times_played;

    @Temporal(TemporalType.DATE)
    @Column(name = "last_played")
    private java.util.Date last_played;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "songs_playlists",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id"))
    private Collection<Role> playlists;

    public Song() { }

    public Song(String title, String artist, Instrument instrument, User user) {
        this.title = title;
        this.artist = artist;
        this.instrument = instrument;
        this.user = user;
    }

    public Song(String title, String artist, Instrument instrument, Tuning tuning, User user) {
        this.title = title;
        this.artist = artist;
        this.instrument = instrument;
        this.tuning = tuning;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public Tuning getTuning() {
        return tuning;
    }

    public void setTuning(Tuning tuning) {
        this.tuning = tuning;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getTimes_played() {
        return times_played;
    }

    public void setTimes_played(int times_played) {
        this.times_played = times_played;
    }

    public Date getLast_played() {
        return last_played;
    }

    public void setLast_played(Date last_played) {
        this.last_played = last_played;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collection<Role> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Collection<Role> playlists) {
        this.playlists = playlists;
    }
}
