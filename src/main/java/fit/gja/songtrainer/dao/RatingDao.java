package fit.gja.songtrainer.dao;

import fit.gja.songtrainer.entity.Rating;
import fit.gja.songtrainer.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingDao  extends JpaRepository<Rating, Long> {

    Rating getRatingById(Long id);
    //List<Rating> getRatingsBySong(Song Song);

    //TODO:
}
