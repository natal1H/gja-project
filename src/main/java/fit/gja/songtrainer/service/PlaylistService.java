package fit.gja.songtrainer.service;

import fit.gja.songtrainer.entity.Instrument;
import fit.gja.songtrainer.entity.Playlist;
import fit.gja.songtrainer.entity.User;

import java.util.List;

public interface PlaylistService {

    Playlist getPlaylistByUserByName(User user, String playlist_name);

    Playlist getPlaylistById(Long id);

    List<Playlist> getPlaylistsByUser(User user);

    List<Playlist> getPlaylistsByUserInstrument(User user, Instrument instrument);

    void save(Playlist playlist);

    void delete(Long playlistId);
}
