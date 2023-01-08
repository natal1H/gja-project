package fit.gja.songtrainer.entity;

import fit.gja.songtrainer.util.InstrumentEnum;
//import fit.gja.songtrainer.util.InstrumentConverter;
import fit.gja.songtrainer.util.Tuning.TuningEnum;
//import fit.gja.songtrainer.util.TuningConverter;

import javax.persistence.*;
import java.util.List;
import java.util.Date;

/**
 * Class representing the "song" table from database
 */
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
    @Enumerated(EnumType.STRING)
    private InstrumentEnum instrument;

    @Column(name = "tuning")
    @Enumerated(EnumType.STRING)
    private TuningEnum tuning;

    @Column(name = "length")
    private int length;

    @Column(name = "times_played")
    private int times_played;

    @Column(name = "file_name")
    private String backingTrackFilename;

    @Temporal(TemporalType.DATE)
    @Column(name = "last_played")
    private java.util.Date last_played;

    @Column(name = "is_visible")
    private Boolean visible;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "assigned_by")
    private User assignedBy;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JoinTable(name = "songs_playlists",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id"))
    private List<Playlist> playlists;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JoinTable(name = "song_ratings",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "rating_id"))
    private List<Rating> ratings;

    public Song() { }

    public Song(String title, String artist, InstrumentEnum instrument, User user) {
        this.title = title;
        this.artist = artist;
        this.instrument = instrument;
        this.user = user;
    }

    public Song(String title, String artist, InstrumentEnum instrument, TuningEnum tuning, User user) {
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

    public InstrumentEnum getInstrument() {
        return instrument;
    }

    public String getInstrumentStr() {
        return this.instrument.toString();
    }

    public void setInstrument(InstrumentEnum instrument) {
        this.instrument = instrument;
    }

    public TuningEnum getTuning() {
        return tuning;
    }

    public String getTuningStr() {
        return this.tuning.toString();
    }
    public void setTuning(TuningEnum tuning) {
        this.tuning = tuning;
    }

    public User getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(User assignedBy) {
        this.assignedBy = assignedBy;
    }

    public int getLength() {
        return length;
    }

    public String getLengthStr() {
        int minutes = this.length / 60;
        int seconds = this.length - minutes * 60;
        String secondsStr = seconds < 10 ? "0" + seconds : Integer.toString(seconds);

        return minutes + ":" + secondsStr;
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

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public String getBackingTrackFilename() {
        return backingTrackFilename;
    }

    public void setBackingTrackFilename(String backingTrackFilename) {
        this.backingTrackFilename = backingTrackFilename;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
