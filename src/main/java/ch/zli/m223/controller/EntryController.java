package ch.zli.m223.controller;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.dto.EntryDto;
import ch.zli.m223.model.Entry;
import ch.zli.m223.service.EntryService;

@Path("/entries")
@Tag(name = "Entries", description = "Handling of entries")
public class EntryController {

    @Inject
    EntryService entryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Index all Entries.", description = "Returns a list of all entries.")
    public List<EntryDto> index() {
        return entryService.findAll().stream().map(this::toDto).toList();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Creates a new entry.", description = "Creates a new entry and returns the newly added entry.")
    public EntryDto create(@Valid EntryDto entryDto) {
        Entry entry = toEntity(entryDto);
        return toDto(entryService.createEntry(entry));
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletes an entry.", description = "Deletes an entry by its id.")
    public void delete(@PathParam("id") Long id) {
        entryService.deleteEntry(id);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Updates an entry.", description = "Updates an existing entry by its id.")
    public EntryDto update(@PathParam("id") Long id, @Valid EntryDto entryDto) {
        Entry entry = toEntity(entryDto);
        return toDto(entryService.updateEntry(id, entry));
    }

    private Entry toEntity(EntryDto dto) {
        Entry entry = new Entry();
        entry.setId(dto.getId());
        entry.setCheckIn(dto.getCheckIn());
        entry.setCheckOut(dto.getCheckOut());
        return entry;
    }

    private EntryDto toDto(Entry entry) {
        EntryDto dto = new EntryDto();
        dto.setId(entry.getId());
        dto.setCheckIn(entry.getCheckIn());
        dto.setCheckOut(entry.getCheckOut());
        return dto;
    }

}
