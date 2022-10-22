package fit.gja.songtrainer.dao;

import fit.gja.songtrainer.entity.*;
import fit.gja.songtrainer.util.Instrument.InstrumentEnum;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PlaylistDaoImpl implements PlaylistDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private SongDao songDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
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
    public Playlist getPlaylistById(Long id) {
        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Playlist.class, id);
    }

    @Override
    public List<Playlist> getPlaylistsByUser(User user) {
        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // now retrieve/read from database using name
        Query<Playlist> theQuery = currentSession.createQuery("from Playlist where user_id=:userId", Playlist.class);
        theQuery.setParameter("userId", user.getId());

        return theQuery.getResultList();
    }

    @Override
    public List<Playlist> getPlaylistsByUserInstrument(User user, InstrumentEnum instrument) {
        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // now retrieve/read from database using name
        Query<Playlist> theQuery = currentSession.createQuery("from Playlist where user_id=:userId and instrument=:instrumentStr", Playlist.class);
        theQuery.setParameter("userId", user.getId());
        theQuery.setParameter("instrumentStr", instrument.toString());

        return theQuery.getResultList();
    }

    @Override
    public void save(Playlist playlist) {
        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // create the user
        currentSession.save(playlist);
    }

    @Override
    public void deleteSongFromPlaylist(Playlist playlist, Song song) {
        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        playlist.getSongs().remove(song);

        currentSession.update(playlist);
    }

    @Override
    public void delete(Long playlistId) {
        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // get the song
        Playlist thePlaylistToDelete = this.getPlaylistById(playlistId);

        // delete in all playlists
        List<Song> songsInPlaylist = thePlaylistToDelete.getSongs();
        for (Song song : songsInPlaylist) {
            songDao.deletePlaylistFromSong(song, thePlaylistToDelete);
        }

        // delete from this user
        User user = thePlaylistToDelete.getUser();
        user.getPlaylists().remove(thePlaylistToDelete);
        currentSession.update(user); // update user

        // delete the song itself
        entityManager.createQuery("delete from Playlist where id = :id").setParameter("id", playlistId).executeUpdate();
    }
}
