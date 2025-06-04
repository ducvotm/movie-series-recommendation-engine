package duke.movie.service;

import duke.movie.dto.TMDbMovieResponse;
import duke.movie.dto.TMDbMovieDetailsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.util.stream.Collectors;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TMDbService {

   @Value("${tmdb.api-key}")
   private String apiKey;

   private final RestTemplate restTemplate;

   public TMDbMovieResponse searchMovies(String title) {
      try {
         String url = "https://api.themoviedb.org/3/search/movie?api_key=" + apiKey + "&query=" + title;
         log.debug("Searching movies with title: {}", title);

         TMDbMovieResponse response = restTemplate.getForObject(url, TMDbMovieResponse.class);

         // ✅ FILTER AND SORT HERE - Only return 4 most recent
         if (response != null && response.getResults() != null) {
            List<TMDbMovieResponse.TMDbMovie> recentMovies = response.getResults().stream()
                  .filter(movie -> movie.getReleaseDate() != null && !movie.getReleaseDate().isEmpty())
                  .sorted((a, b) -> b.getReleaseDate().compareTo(a.getReleaseDate())) // Newest first
                  .limit(4) // Only take 4
                  .collect(Collectors.toList());

            response.setResults(recentMovies);
         }

         return response;
      } catch (RestClientException e) {
         log.error("Failed to search movies for title: {}", title, e);
         return new TMDbMovieResponse();
      }
   }

   public TMDbMovieDetailsResponse getMovieRating(Integer tmdbId) {
      try {
         String url = "https://api.themoviedb.org/3/movie/" + tmdbId + "?api_key=" + apiKey;
         log.debug("Getting movie rating for TMDb ID: {}", tmdbId);
         return restTemplate.getForObject(url, TMDbMovieDetailsResponse.class);
      } catch (RestClientException e) {
         log.error("Failed to get movie rating for TMDb ID: {}", tmdbId, e);
         // Return empty response on error
         return new TMDbMovieDetailsResponse();
      }
   }
}