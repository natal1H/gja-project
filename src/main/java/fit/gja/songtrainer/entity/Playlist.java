package fit.gja.songtrainer.entity;

import fit.gja.songtrainer.util.InstrumentEnum;

import javax.persistence.*;
import java.util.List;

/**
 * Class representing the "playlist" table from database
 */
@Entity
@Table(name = "playlist")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "instrument")
    @Enumerated(EnumType.STRING)
    private InstrumentEnum instrument;

    @Column(name = "public")
    private boolean isPublic;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JoinTable(name = "songs_playlists",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id"))
    private List<Song> songs;

    public Playlist() { }

    public Playlist(String name, InstrumentEnum instrument, User user) {
        this.name = name;
        this.instrument = instrument;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
