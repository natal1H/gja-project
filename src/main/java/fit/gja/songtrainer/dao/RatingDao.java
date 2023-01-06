package fit.gja.songtrainer.dao;

import fit.gja.songtrainer.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;



public interface RatingDao  extends JpaRepository<Rating, Long> {

    Rating getRatingById(Long id);
}
