package fit.gja.songtrainer.entity;


import javax.persistence.*;
import java.util.List;

/**
 * Class representing the song ratings
 */
@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "feelings")
    private String feelings;

    @Column(name = "accuracy")
    private String accuracy;


    @Column(name = "number_of_mistakes")
    private Integer numberMistakes;

    @Column(name = "comment")
    private String comment;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JoinTable(name = "song_ratings",
            joinColumns = @JoinColumn(name = "rating_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id"))
    private List<Song> songs;

    public Rating() { }

    public Rating(String feelings, String accuracy, Integer numberMistakes,String comment) {
        this.feelings = feelings;
        this.accuracy = accuracy;
        this.numberMistakes = numberMistakes;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFeelings() {
        return feelings;
    }

    public void setFeelings(String feelings) {
        this.feelings = feelings;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }


    public Integer getNumberMistakes() {
        return numberMistakes;
    }

    public void setNumberMistakes(Integer numberMistakes) {
        this.numberMistakes = numberMistakes;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
