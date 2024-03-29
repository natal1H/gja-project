package fit.gja.songtrainer.dao;

import fit.gja.songtrainer.entity.Song;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.util.InstrumentEnum;
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

    List<Song> findByUserOrderByArtistAsc(User user);
    List<Song> findByUserOrderByArtistDesc(User user);
    List<Song> findByUserOrderByTitleAsc(User user);
    List<Song> findByUserOrderByTitleDesc(User user);
    List<Song> findByUserOrderByTuningAsc(User user);
    List<Song> findByUserOrderByLengthAsc(User user);
    List<Song> findByUserOrderByLengthDesc(User user);

    List<Song> findSongByUserAndAssignedBy(User student, User assignedBy);

    @Query("from Song where user=:user order by times_played asc")
    List<Song> findByUserOrderByTimes_playedAsc(@Param("user") User user);

    @Query("from Song where user=:user order by times_played desc")
    List<Song> findByUserOrderByTimes_playedDesc(@Param("user") User user);

    @Query("from Song where user=:user order by last_played asc")
    List<Song> findByUserOrderByLast_playedAsc(@Param("user") User user);

    @Query("from Song where user=:user order by last_played desc ")
    List<Song> findByUserOrderByLast_playedDesc(@Param("user") User user);
}