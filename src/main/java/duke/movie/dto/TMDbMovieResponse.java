package duke.movie.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TMDbMovieResponse {
   private Integer page;
   private List<TMDbMovie> results;
   private Integer totalPages;
   private Integer totalResults;

   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   public static class TMDbMovie {
      private Integer id;
      private String title;
      private String overview;

      @JsonProperty("release_date")
      private String releaseDate;

      @JsonProperty("vote_average")
      private Double voteAverage;

      @JsonProperty("vote_count")
      private Integer voteCount;

      @JsonProperty("poster_path")
      private String posterPath;

      @JsonProperty("backdrop_path")
      private String backdropPath;

      private Boolean adult;

      @JsonProperty("genre_ids")
      private List<Integer> genreIds;

      @JsonProperty("original_language")
      private String originalLanguage;

      @JsonProperty("original_title")
      private String originalTitle;

      private Double popularity;
      private Boolean video;
   }
}