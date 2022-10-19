package fit.gja.songtrainer.dao;

import fit.gja.songtrainer.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class SongDaoImpl implements SongDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public Song findByTitleArtistUser(String title, String artist, User user) {
        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // now retrieve/read from database using name
        Query<Song> theQuery = currentSession.createQuery("from Song where title=:title and artist=:artist and user_id=:userId", Song.class);
        theQuery.setParameter("title", title);
        theQuery.setParameter("artist", artist);
        theQuery.setParameter("userId", user.getId());

        return theQuery.getSingleResult();
    }

    @Override
    @Transactional
    public List<Song> getSongsByUser(User user) {
        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // now retrieve/read from database using name
        Query<Song> theQuery = currentSession.createQuery("from Song where user_id=:userId", Song.class);
        theQuery.setParameter("userId", user.getId());

        return theQuery.getResultList();
    }

    @Override
    @Transactional
    public List<Song> getSongsByUserInstrument(User user, Instrument instrument) {
        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        InstrumentConverter instConv = new InstrumentConverter();

        // now retrieve/read from database using name
        Query<Song> theQuery = currentSession.createQuery("from Song where user_id=:userId and instrument=:instrumentStr", Song.class);
        theQuery.setParameter("userId", user.getId());
        theQuery.setParameter("instrumentStr", instConv.convertToDatabaseColumn(instrument));

        return theQuery.getResultList();
    }

    @Override
    public void save(Song song) {
        // TODO
    }
}
