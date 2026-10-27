package ch.zli.m223.dto;

import java.time.LocalDateTime;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

public class EntryDto {

    @Schema(readOnly = true)
    private Long id;

    @NotNull(message = "Check-In darf nicht leer sein")
    private LocalDateTime checkIn;

    @NotNull(message = "Check-Out darf nicht leer sein")
    private LocalDateTime checkOut;

    @JsonIgnore
    @AssertTrue(message = "Check-In muss zeitlich vor dem Check-Out liegen")
    public boolean isCheckInBeforeCheckOut() {
        if (checkIn == null || checkOut == null) {
            return true; // Wird bereits durch @NotNull abgefangen
        }
        return checkIn.isBefore(checkOut);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }
}
