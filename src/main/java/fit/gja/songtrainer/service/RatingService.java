package fit.gja.songtrainer.service;


import fit.gja.songtrainer.dao.RatingDao;
import fit.gja.songtrainer.entity.Rating;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for ratings
 */
@Service
public class RatingService {

    private final RatingDao ratingDao;

    public RatingService(RatingDao ratingDao) {
        this.ratingDao = ratingDao;
    }

    @Transactional
    public Rating getRatingById(Long id) {
        return ratingDao.getRatingById(id);
    }

    @Transactional
    public void save(Rating rating) {
        ratingDao.save(rating);
    }



}
