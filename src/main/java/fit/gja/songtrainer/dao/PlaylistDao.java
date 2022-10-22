package fit.gja.songtrainer.dao;

import fit.gja.songtrainer.util.Instrument.InstrumentEnum;
import fit.gja.songtrainer.entity.Playlist;
import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;

import java.util.List;

public interface PlaylistDao {

    Playlist getPlaylistByUserByName(User user, String playlist_name);
    Playlist getPlaylistById(Long id);
    List<Playlist> getPlaylistsByUser(User user);

    List<Playlist> getPlaylistsByUserInstrument(User user, InstrumentEnum instrument);

    void save(Playlist playlist);

    void deleteSongFromPlaylist(Playlist playlist, Song song);

    void delete(Long playlistId);
}
