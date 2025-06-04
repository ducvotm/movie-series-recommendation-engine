# Movie Project Architecture Reference

## Overview

This document outlines the recommended architecture transformation for the Movie Project using recruitment-service patterns in pseudocode format.

## Current vs Target Architecture

```
Current Architecture:
Controller -> Service -> External API -> Raw Response

Target Architecture:
Controller -> AbstractController -> Service -> External API -> Standardized Response
```

## Recommended Structure

```
movie/
├── common/
│   ├── response/ApiResponse.java
│   ├── errorcode/ErrorCode.java
│   ├── controller/AbstractResponseController.java
│   └── exception/GlobalExceptionHandler.java
├── controller/MovieController.java
├── service/TMDbService.java
└── dto/TMDbMovieResponse.java
```

## Pseudocode Implementation

### 1. Standardized API Response

```
CLASS ApiResponse<T>:
    FIELDS:
        errorCode: integer
        statusCode: integer
        message: string
        object: T
    
    METHOD success(data):
        RETURN new ApiResponse(
            errorCode = 0,
            statusCode = 200,
            message = "Successful!",
            object = data
        )
    
    METHOD error(errorCode, status, message):
        RETURN new ApiResponse(
            errorCode = errorCode,
            statusCode = status,
            message = message,
            object = null
        )
```

### 2. Error Code System

```
CLASS ErrorCode:
    SUCCESS = 0
    BAD_REQUEST = 400
    NOT_FOUND = 404
    INTERNAL_ERROR = 500
    TMDB_API_ERROR = 1001
    MOVIE_NOT_FOUND = 1002
```

### 3. Abstract Controller Pattern

```
ABSTRACT CLASS AbstractResponseController:
    
    METHOD responseEntity(callback):
        TRY:
            result = callback.execute()
            RETURN ResponseEntity.ok(ApiResponse.success(result))
        CATCH exception:
            HANDLE error appropriately
            THROW exception
```

### 4. Enhanced Movie Controller

```
CLASS MovieController EXTENDS AbstractResponseController:
    
    FIELD tmdbService: TMDbService
    
    METHOD searchMovies(title):
        VALIDATE title is not empty
        RETURN responseEntity(() -> {
            RETURN tmdbService.searchMovies(title.trim())
        })
    
    METHOD getMovieRating(tmdbId):
        VALIDATE tmdbId is positive
        RETURN responseEntity(() -> {
            RETURN tmdbService.getMovieRating(tmdbId)
        })
    
    METHOD getPopularMovies():
        RETURN responseEntity(() -> {
            RETURN tmdbService.getPopularMovies()
        })
```

### 5. Enhanced Service Layer

```
CLASS TMDbService:
    
    FIELD apiKey: string
    FIELD restTemplate: RestTemplate
    
    METHOD searchMovies(title):
        url = build_tmdb_url("/search/movie", title)
        TRY:
            response = restTemplate.get(url)
            filtered_movies = filter_and_sort_movies(response.results)
            recent_movies = take_first_4(filtered_movies)
            RETURN response_with_limited_results(recent_movies)
        CATCH exception:
            LOG error
            THROW RestClientException
    
    HELPER METHOD filter_and_sort_movies(movies):
        FILTER movies WHERE release_date is not null and not empty
        SORT movies BY release_date descending
        RETURN sorted_movies
    
    METHOD getMovieRating(tmdbId):
        url = build_tmdb_url("/movie/" + tmdbId)
        TRY:
            RETURN restTemplate.get(url)
        CATCH exception:
            LOG error
            THROW RestClientException
    
    METHOD getPopularMovies():
        url = build_tmdb_url("/movie/popular")
        TRY:
            response = restTemplate.get(url)
            limited_movies = take_first_4(response.results)
            RETURN response_with_limited_results(limited_movies)
        CATCH exception:
            LOG error
            THROW RestClientException
```

### 6. Global Exception Handler

```
CLASS GlobalExceptionHandler:
    
    METHOD handleValidationException(validationException):
        message = extract_first_validation_error(validationException)
        RETURN ApiResponse.error(BAD_REQUEST, 400, message)
    
    METHOD handleTMDbException(restClientException):
        RETURN ApiResponse.error(TMDB_API_ERROR, 503, "TMDb API unavailable")
    
    METHOD handleGenericException(exception):
        LOG exception
        RETURN ApiResponse.error(INTERNAL_ERROR, 500, "Unexpected error")
```

## Implementation Flow

```
PHASE 1 - Core Infrastructure:
1. CREATE ApiResponse class
2. CREATE ErrorCode constants
3. CREATE AbstractResponseController
4. UPDATE MovieController to extend abstract controller

PHASE 2 - Enhanced Features:
5. ADD GlobalExceptionHandler
6. ENHANCE TMDbService with new endpoints
7. ADD validation annotations
8. ADD comprehensive documentation

PHASE 3 - Advanced Features:
9. ADD caching layer
10. ADD metrics and monitoring
11. ADD database integration
12. ADD user features
```

## API Response Format

```
SUCCESS Response:
{
  "errorCode": 0,
  "statusCode": 200,
  "message": "Successful!",
  "object": { actual_data_here }
}

ERROR Response:
{
  "errorCode": 1001,
  "statusCode": 503,
  "message": "TMDb API unavailable",
  "object": null
}
```

## Benefits

```
CONSISTENCY: All APIs return same response format
ERROR_HANDLING: Centralized, predictable error responses  
DOCUMENTATION: Auto-generated API docs
VALIDATION: Automatic input validation
MAINTAINABILITY: Clean, organized code structure
EXTENSIBILITY: Easy to add new endpoints
PROFESSIONAL: Enterprise-grade architecture
```
