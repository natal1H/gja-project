package fit.gja.songtrainer.dao;

import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.util.Instrument.InstrumentEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SongDao extends JpaRepository<Song, Long> {

    @Query("from Song where title=:title and artist=:artist and user=:user")
    Song findByTitleArtistUser(@Param("title") String title, @Param("artist") String artist, @Param("user") User user);

    Song getSongById(Long songId);

    List<Song> getSongsByUser(User user);

    @Query("from Song where user = :user and instrument=:instrument")
    List<Song> getSongsByUserInstrument(@Param("user") User user, @Param("instrument") InstrumentEnum instrument);

    void deleteSongById(Long songId);
}