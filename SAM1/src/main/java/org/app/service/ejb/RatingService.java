package org.app.service.ejb;

import java.util.Collection;

import javax.ejb.Remote;

import org.app.service.entities.Rating;

@Remote
public interface RatingService {
	
	Rating AddRating(Rating ratingToAdd);
	
	String removeRating(Rating ratingToDelete);
	
	Rating getRatingByratingID (Integer ratingId);
	
	Collection<Rating> getRating();
	
	Collection<Rating> getRatingBytitle(String titleRating);
	
	String sayRest();
}
