# Movie Recommendation Engine - TMDb MVP

## Project Overview

A simple movie recommendation system powered by **TMDb (The Movie Database) API**. Get real movie data, ratings, and recommendations from the world's largest movie database.

## Quick Start

### Prerequisites

- Java 17
- Maven
- TMDb API Key (free from [TMDb](https://www.themoviedb.org/settings/api))

### Setup

1. **Get TMDb API Key:**
   - Sign up at [TMDb](https://www.themoviedb.org/)
   - Go to Settings → API → Create API Key

2. **Set API Key:**

   ```bash
   export TMDB_API_KEY=your_actual_api_key_here
   ```

   Or update `application.yml`

3. **Run Application:**

   ```bash
   mvn spring-boot:run
   ```

### Access Points

- **API Documentation**: <http://localhost:8080/swagger-ui.html>
- **Database Console**: <http://localhost:8080/h2-console>
- **Health Check**: <http://localhost:8080/actuator/health>

## API Endpoints

### Movies

- `GET /api/movies` - Get local movies (empty initially)
- `GET /api/movies/search?title=matrix` - Search TMDb movies
- `GET /api/movies/rating/603` - Get TMDb rating for specific movie

## Example Usage

### Search Movies

```bash
curl "http://localhost:8080/api/movies/search?title=matrix"
```

### Get Movie Rating

```bash
curl http://localhost:8080/api/movies/rating/603
```

## Database

- **H2 in-memory** database for development
- **Genres only** - Movie data comes from TMDb API
- **No hardcoded movies** - Pure API-driven approach

## Architecture

```
movie/
├── entity/          # Movie, Genre models
├── repository/      # Database access
├── service/         # TMDbService (API calls)
├── controller/      # REST endpoints
└── resources/       # Configuration
```

## Features

- TMDb API integration
- Live movie search with ratings
- Real-time movie data
- RESTful API with Swagger docs
- H2 database with genre categorization
- Clean MVP architecture

## Example API Response

```json
{
  "results": [
    {
      "id": 603,
      "title": "The Matrix",
      "vote_average": 8.2,
      "vote_count": 24500,
      "overview": "A computer hacker learns...",
      "poster_path": "/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg"
    }
  ]
}
```

## Contributing

This is a learning project focused on TMDb API integration and clean architecture.

---

*Built with love using Spring Boot + TMDb API*
