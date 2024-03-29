package fit.gja.songtrainer.dao;

import fit.gja.songtrainer.entity.Playlist;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.util.InstrumentEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaylistDao extends JpaRepository<Playlist, Long> {

    @Query("from Playlist where name=:playlistName and user=:user")
    Playlist getPlaylistByUserByName(@Param("user") User user, @Param("playlistName") String playlist_name);
    Playlist getPlaylistById(Long id);
    List<Playlist> getPlaylistsByUser(User user);

    @Query("from Playlist where user=:user and instrument=:instrument")
    List<Playlist> getPlaylistsByUserInstrument(@Param("user") User user, @Param("instrument") InstrumentEnum instrument);

    List<Playlist> getPlaylistByUserAndIsPublicTrue(User user);

    void deletePlaylistById(Long playlistId);
}
