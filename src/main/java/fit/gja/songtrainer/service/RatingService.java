package fit.gja.songtrainer.service;


import fit.gja.songtrainer.dao.RatingDao;

import fit.gja.songtrainer.entity.Rating;

import fit.gja.songtrainer.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private final RatingDao ratingDao;

    public RatingService(RatingDao ratingDao) {
        this.ratingDao = ratingDao;
    }


    public List<Rating> getRatingsByUser(User user) {
        return ratingDao.getRatingsByUser(user);
    }

    public Rating getRatingById(Long id){
       return ratingDao.getRatingById(id);
    }

}
