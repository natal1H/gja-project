package fit.gja.songtrainer.entity;

import javax.persistence.*;
import java.util.List;
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

    @Column(name = "instrument")
    @Convert(converter = InstrumentConverter.class)
    private Instrument instrument;

    @Column(name = "tuning")
    @Convert(converter = TuningConverter.class)
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
    private List<Role> playlists;

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

    public String getInstrumentStr() {
        InstrumentConverter instConverter = new InstrumentConverter();
        return instConverter.convertToDatabaseColumn(this.instrument);
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public Tuning getTuning() {
        return tuning;
    }

    public String getTuningStr() {
        TuningConverter tunConverter = new TuningConverter();
        return tunConverter.convertToDatabaseColumn(this.tuning);
    }
    public void setTuning(Tuning tuning) {
        this.tuning = tuning;
    }

    public int getLength() {
        return length;
    }

    public String getLengthStr() {
        int minutes = this.length / 60;
        int seconds = this.length - minutes * 60;

        return Integer.toString(minutes) + ":" + Integer.toString(seconds);
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

    public List<Role> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Role> playlists) {
        this.playlists = playlists;
    }
}
