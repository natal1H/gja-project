package fit.gja.songtrainer.dao;

import fit.gja.songtrainer.entity.Instrument;
import fit.gja.songtrainer.entity.Playlist;
import fit.gja.songtrainer.entity.User;

import java.util.List;

public interface PlaylistDao {

    Playlist getPlaylistByUserByName(User user, String playlist_name);
    List<Playlist> getPlaylistsByUser(User user);

    List<Playlist> getPlaylistsByUserInstrument(User user, Instrument instrument);

    void save(Playlist playlist);
}
