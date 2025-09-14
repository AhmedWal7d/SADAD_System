package com.example.sadad.controller;

import com.example.sadad.dto.CreateSadadRecordDto;
import com.example.sadad.dto.SadadRecordResponse;
import com.example.sadad.model.SadadRecord;
import com.example.sadad.service.SadadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/sadad")
public class SadadController {

    private final SadadService service;

    public SadadController(SadadService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasRole('ENTRY') or hasRole('RELEASER') or hasRole('APPROVER')")
    public ResponseEntity<SadadRecordResponse> create(@Valid @RequestBody CreateSadadRecordDto dto,
                                                      Authentication auth) {
        String username = (auth != null) ? auth.getName() : "system";
        SadadRecord saved = service.create(dto, username);
        return ResponseEntity.ok(
                new SadadRecordResponse(
                        saved.getId(),
                        saved.getStatus(),
                        Map.of("message", "SADAD record created successfully")
                )
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ENTRY','ROLE_RELEASER','ROLE_APPROVER','ROLE_ADMIN')")
    public ResponseEntity<?> getAll(Authentication auth) {
        boolean isReleaser = auth.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_RELEASER"));

        if (isReleaser) {
            return ResponseEntity.ok(service.getAllByStatus("APPROVED"));
        } else {
            return ResponseEntity.ok(service.getAll());
        }
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ENTRY','ROLE_RELEASER','ROLE_APPROVER','ROLE_ADMIN')")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ENTRY') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<SadadRecordResponse> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody CreateSadadRecordDto dto,
            Authentication auth) {

        String username = (auth != null) ? auth.getName() : "system";
        SadadRecord updated = service.update(id, dto, username);
        return ResponseEntity.ok(
                new SadadRecordResponse(
                        updated.getId(),
                        updated.getStatus(),
                        Map.of("message", "SADAD record updated successfully")
                )
        );
    }


    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasAuthority('ROLE_APPROVER')")
    public ResponseEntity<SadadRecordResponse> cancel(@PathVariable Long id,
                                                      Authentication auth) {
        String username = (auth != null) ? auth.getName() : "system";
        SadadRecord canceled = service.cancel(id, username);
        return ResponseEntity.ok(
                new SadadRecordResponse(
                        canceled.getId(),
                        canceled.getStatus(),
                        Map.of("message", "SADAD record canceled successfully")
                )
        );
    }

    @PutMapping("/{id}/mark-pending")
    @PreAuthorize("hasAuthority('ROLE_ENTRY')")
    public ResponseEntity<?> markPending(@PathVariable Long id, Authentication auth) {
        try {
            SadadRecord record = service.markPending(id, auth.getName());
            return ResponseEntity.ok(
                    new SadadRecordResponse(
                            record.getId(),
                            record.getStatus(),
                            Map.of("message", "SADAD record marked as PENDING successfully")
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('ROLE_APPROVER')")
    public ResponseEntity<SadadRecordResponse> changeStatus(@PathVariable Long id,
                                                            @RequestParam String status,
                                                            Authentication auth) {
        String username = (auth != null) ? auth.getName() : "system";
        SadadRecord record = service.changeStatus(id, status, username);
        return ResponseEntity.ok(
                new SadadRecordResponse(
                        record.getId(),
                        record.getStatus(),
                        Map.of("message", "SADAD record status updated successfully")
                )
        );
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('ROLE_APPROVER')")
    public ResponseEntity<SadadRecordResponse> approve(@PathVariable Long id,
                                                       Authentication auth) {
        String username = (auth != null) ? auth.getName() : "system";
        SadadRecord approved = service.approve(id, username);
        return ResponseEntity.ok(
                new SadadRecordResponse(
                        approved.getId(),
                        approved.getStatus(),
                        Map.of("message", "SADAD record approved successfully")
                )
        );
    }
}
