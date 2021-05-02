package com.example.matchmaker.api;

import com.example.matchmaker.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "The Matchmaker", description = "API")
@RequestMapping(path = "/users", consumes = APPLICATION_JSON_VALUE)
public interface MatchmakerApi {

    @Operation(summary = "The operation accepts uses to the matchmaker pool")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "The user was accepted to the matchmaker pool"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    void acceptUser(@Valid @RequestBody UserDto userDto);
}
