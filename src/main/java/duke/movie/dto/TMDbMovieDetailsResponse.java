package duke.movie.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TMDbMovieDetailsResponse {
   private Integer id;
   private String title;
   private String overview;
   private String releaseDate;
   private Double voteAverage;
   private Integer voteCount;
   private String posterPath;
   private String backdropPath;
   private Integer runtime;
   private String status;
   private String tagline;
   private List<TMDbGenre> genres;
   private Long budget;
   private Long revenue;
   private String homepage;
   private String imdbId;
   private String originalLanguage;
   private String originalTitle;
   private Double popularity;

   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   public static class TMDbGenre {
      private Integer id;
      private String name;
   }
}