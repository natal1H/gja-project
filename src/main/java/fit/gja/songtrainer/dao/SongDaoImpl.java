package fit.gja.songtrainer.dao;

import fit.gja.songtrainer.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class SongDaoImpl implements SongDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private PlaylistDao playlistDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
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
    public Song getSongById(Long songId) {
        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // now retrieve/read from database using name
        Query<Song> theQuery = currentSession.createQuery("from Song where id=:theId", Song.class);
        theQuery.setParameter("theId", songId);

        return theQuery.getSingleResult();
    }

    @Override
    public List<Song> getSongsByUser(User user) {
        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // now retrieve/read from database using name
        Query<Song> theQuery = currentSession.createQuery("from Song where user_id=:userId", Song.class);
        theQuery.setParameter("userId", user.getId());

        return theQuery.getResultList();
    }

    @Override
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
        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // create the user
        currentSession.save(song);
    }

    @Override
    public void delete(Long songId) {
        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // get the song
        Song theSongToDelete = this.getSongById(songId);

        // delete in all playlists
        List<Playlist> playlistsWithSong = theSongToDelete.getPlaylists();
        for (Playlist playlist : playlistsWithSong) {
            playlistDao.deleteSongFromPlaylist(playlist, theSongToDelete);
        }

        // delete from this user
        User user = theSongToDelete.getUser();
        user.getSongs().remove(theSongToDelete);
        currentSession.update(user); // update user

        // delete the song itself
        entityManager.createQuery("delete from Song where id = :id").setParameter("id", songId).executeUpdate();
    }
}
