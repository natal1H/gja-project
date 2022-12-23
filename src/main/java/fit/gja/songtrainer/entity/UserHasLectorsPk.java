package fit.gja.songtrainer.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserHasLectorsPk implements Serializable {

    public UserHasLectorsPk(Long userId, Long lectorId) {
        this.userId = userId;
        this.lectorId = lectorId;
    }

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "lector_id")
    private Long lectorId;

    protected UserHasLectorsPk() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLectorId() {
        return lectorId;
    }

    public void setLectorId(Long lectorId) {
        this.lectorId = lectorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserHasLectorsPk that = (UserHasLectorsPk) o;
        return userId.equals(that.userId) && lectorId.equals(that.lectorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, lectorId);
    }
}
