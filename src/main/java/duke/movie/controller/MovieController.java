package duke.movie.controller;

import duke.movie.dto.TMDbMovieResponse;
import duke.movie.dto.TMDbMovieDetailsResponse;
import duke.movie.service.TMDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

   private final TMDbService tmdbService;

   @GetMapping("/search")
   public ResponseEntity<TMDbMovieResponse> searchMovies(@RequestParam String title) {
      if (title == null || title.trim().isEmpty()) {
         return ResponseEntity.badRequest().build();
      }

      TMDbMovieResponse response = tmdbService.searchMovies(title.trim());
      return ResponseEntity.ok(response);
   }

   @GetMapping("/rating/{tmdbId}")
   public ResponseEntity<TMDbMovieDetailsResponse> getMovieRating(@PathVariable Integer tmdbId) {
      if (tmdbId == null || tmdbId <= 0) {
         return ResponseEntity.badRequest().build();
      }

      TMDbMovieDetailsResponse response = tmdbService.getMovieRating(tmdbId);
      return ResponseEntity.ok(response);
   }
}