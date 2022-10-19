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
public class PlaylistDaoImpl implements PlaylistDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public Playlist getPlaylistByUserByName(User user, String playlist_name) {
        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // now retrieve/read from database using name
        Query<Playlist> theQuery = currentSession.createQuery("from Playlist where name=:name and user_id=:userId", Playlist.class);
        theQuery.setParameter("name", playlist_name);
        theQuery.setParameter("userId", user.getId());

        return theQuery.getSingleResult();
    }

    @Override
    @Transactional
    public Playlist getPlaylistById(Long id) {
        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // now retrieve/read from database using name
        Query<Playlist> theQuery = currentSession.createQuery("from Playlist where id=:playlist_id", Playlist.class);
        theQuery.setParameter("playlist_id", id);

        return theQuery.getSingleResult();
    }

    @Override
    @Transactional
    public List<Playlist> getPlaylistsByUser(User user) {
        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // now retrieve/read from database using name
        Query<Playlist> theQuery = currentSession.createQuery("from Playlist where user_id=:userId", Playlist.class);
        theQuery.setParameter("userId", user.getId());

        return theQuery.getResultList();
    }

    @Override
    @Transactional
    public List<Playlist> getPlaylistsByUserInstrument(User user, Instrument instrument) {
        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        InstrumentConverter instConv = new InstrumentConverter();

        // now retrieve/read from database using name
        Query<Playlist> theQuery = currentSession.createQuery("from Playlist where user_id=:userId and instrument=:instrumentStr", Playlist.class);
        theQuery.setParameter("userId", user.getId());
        theQuery.setParameter("instrumentStr", instConv.convertToDatabaseColumn(instrument));

        return theQuery.getResultList();
    }

    @Override
    public void save(Playlist playlist) {
        // TODO
    }
}
