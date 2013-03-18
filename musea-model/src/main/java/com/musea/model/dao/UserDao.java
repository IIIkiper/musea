package com.musea.model.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.musea.model.domain.Genre;
import com.musea.model.domain.Song;
import com.musea.model.domain.SongGenre;
import com.musea.model.domain.User;

/**
 * Dao for {@link User} polymorphic queries.
 * @author Roman Zaripov
 */
@Repository
public class UserDao extends Dao<User> { 
		
	public List<Object[]> getUsersGenres(Collection<Long> ids) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
		Root<User> from = query.from(User.class);

		Join<Song, SongGenre> userSongGenre = from.join("audios").join("song").join("songsGenres");
		Join<SongGenre, Genre> songGenreGenre = userSongGenre.join("genre");
		
		Path<Long> userId = from.get("id");
		Path<Long> userSystemId = from.get("systemId");
		Path<String> genreName = songGenreGenre.get("displayName");
		Path<Integer> genreId = songGenreGenre.get("id");
		Expression<Double> sumWeight = cb.sumAsDouble(userSongGenre.<Float>get("weight"));
		
		return list(query.multiselect(userSystemId, genreName, genreId, sumWeight)
			.where(userId.in(ids))
			.orderBy(cb.asc(userSystemId), cb.desc(sumWeight))
			.groupBy(userSystemId, genreName, genreId)
		);	
	}
}