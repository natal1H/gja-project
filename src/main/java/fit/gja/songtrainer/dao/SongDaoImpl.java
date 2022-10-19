package fit.gja.songtrainer.dao;

import fit.gja.songtrainer.entity.Instrument;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class SongDaoImpl implements SongDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Song findByTitleArtistUser(String title, String artist, User user) {
        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // now retrieve/read from database using name
        Query<Song> theQuery = currentSession.createQuery("select a from Song where a.title=:title and a.artist=:artist and a.user_id=:userId", Song.class);
        theQuery.setParameter("title", title);
        theQuery.setParameter("artist", artist);
        theQuery.setParameter("userId", user.getId());

        Song theSong = null;

        try {
            theSong = theQuery.getSingleResult();
        } catch (Exception e) {
            theSong = null;
        }

        return theSong;
    }

    @Override
    @Transactional
    public List<Song> getAllSongs() {
        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // now retrieve/read from database using name
        Query<Song> theQuery = currentSession.createQuery("from Song", Song.class);
        List<Song> theSongs = theQuery.getResultList();

        return theSongs;
    }

    @Override
    @Transactional
    public List<Song> getSongsByUser(User user) {
        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // now retrieve/read from database using name
        Query<Song> theQuery = currentSession.createQuery("from Song where user_id=:userId", Song.class);
        theQuery.setParameter("userId", user.getId());

        List<Song> songs = theQuery.getResultList();;

        return songs;
    }

    @Override
    public List<Song> getSongsByUserInstrument(User user, Instrument instrument) {
        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // now retrieve/read from database using name
        Query<Song> theQuery = currentSession.createQuery("from Song where user_id=:userId and instrument", Song.class);
        theQuery.setParameter("userId", user.getId());

        List<Song> songs = theQuery.getResultList();;

        return songs;
    }


    @Override
    public void save(Song song) {
        // TODO
    }
}
