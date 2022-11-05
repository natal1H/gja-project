package fit.gja.songtrainer.entity;

import com.fasterxml.jackson.annotation.JsonView;
import fit.gja.songtrainer.util.View;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.lang.annotation.Documented;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonView(View.Public.class)
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    @JsonView(View.Internal.class)
    private String password;

    @Column(name = "first_name")
    @JsonView(View.Public.class)
    private String firstName;

    @Column(name = "last_name")
    @JsonView(View.Public.class)
    private String lastName;

    @Column(name = "email")
    @JsonView(View.Internal.class)
    private String email;

    @Column(name = "profile_picture_path")
    @JsonView(View.Internal.class)
    private String profilePicturePath;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonView(View.Internal.class)
    private Collection<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_has_friend",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    @JsonView(View.Internal.class)
    private Collection<User> friends;

    @OneToMany(mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonView(View.Public.class)
    private List<Song> songs;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonView(View.Public.class)
    private List<Playlist> playlists;

    public User() { }

    public User(String userName, String password, String firstName, String lastName, String email) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(String userName, String password, String firstName, String lastName, String email,
                Collection<Role> roles) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public Collection<Role> getRoles() { return roles; }

    public void setRoles(Collection<Role> roles) { this.roles = roles; }

    public List<Song> getSongs() { return songs; }

    public void setSongs(List<Song> songs) { this.songs = songs; }

    public List<Playlist> getPlaylists() { return playlists; }

    public void setPlaylists(List<Playlist> playlists) { this.playlists = playlists; }

    public Collection<User> getFriends() {
        return friends;
    }

    public void setFriends(Collection<User> friends) {
        this.friends = friends;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }

//    @Override
//    public String toString() {
//        return "User{" + "id=" + id + ", userName='" + userName + '\'' + ", password='" + "*********" + '\''
//                + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='" + email + '\''
//                + ", roles=" + roles + '}';
//    }
}
