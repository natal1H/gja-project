package fit.gja.songtrainer.entity;


import javax.persistence.*;

@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tempo")
    private String tempo;

    @Column(name = "rhythm")
    private String rhythm;

    @Column(name = "technique")
    private String technique;

    @Column(name = "number_of_mistakes")
    private Integer numberMistakes;

    @Column(name = "external_expression")
    private String externalExpression;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Rating() { }

    public Rating(String tempo, String rhythm, String technique,Integer numberMistakes,String externalExpression, User user) {
        this.tempo = tempo;
        this.rhythm = rhythm;
        this.technique = technique;
        this.numberMistakes = numberMistakes;
        this.externalExpression = externalExpression;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getRhythm() {
        return rhythm;
    }

    public void setRhythm(String rhythm) {
        this.rhythm = rhythm;
    }

    public String getTechnique() {
        return technique;
    }

    public void setTechnique(String technique) {
        this.technique = technique;
    }

    public Integer getNumberMistakes() {
        return numberMistakes;
    }

    public void setNumberMistakes(Integer numberMistakes) {
        this.numberMistakes = numberMistakes;
    }

    public String getExternalExpression() {
        return externalExpression;
    }

    public void setExternalExpression(String externalExpression) {
        this.externalExpression = externalExpression;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
