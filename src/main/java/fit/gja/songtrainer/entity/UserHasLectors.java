package fit.gja.songtrainer.entity;

import javax.persistence.*;


@Entity
@Table(name = "user_has_lectors")
public class UserHasLectors {

    public UserHasLectors(User user, User lector, Playlist playlist) {
        this.user = user;
        this.lector = lector;
        this.playlist = playlist;
        id = new UserHasLectorsPk(user.getId(), lector.getId());
    }

    @EmbeddedId
    private UserHasLectorsPk id;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("lector_id")
    @JoinColumn(name = "lector_id")
    private User lector;

    @OneToOne(fetch = FetchType.LAZY)
    private Playlist playlist;

    protected UserHasLectors() {
    }

    public UserHasLectorsPk getId() {
        return id;
    }

    public void setId(UserHasLectorsPk id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getLector() {
        return lector;
    }

    public void setLector(User lector) {
        this.lector = lector;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }
}
