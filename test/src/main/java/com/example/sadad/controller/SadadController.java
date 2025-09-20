package com.example.sadad.controller;

import com.example.sadad.dto.CreateSadadRecordDto;
import com.example.sadad.dto.SadadRecordResponse;
import com.example.sadad.entity.SadadRecord;
import com.example.sadad.service.SadadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<?> create(@Valid @RequestBody CreateSadadRecordDto dto,
                                    Authentication auth) {
        String username = (auth != null) ? auth.getName() : "system";
        try {
            SadadRecord saved = service.create(dto, username);
            return ResponseEntity.ok(
                    new SadadRecordResponse(
                            saved.getId(),
                            saved.getStatus(),
                            Map.of("message", "SADAD record created successfully")
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "details", e.toString(), // هيرجع نوع الاستثناء + الرسالة
                            "message", "Failed to create SADAD record"
                    ));
        }

    }


    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ENTRY','ROLE_RELEASER','ROLE_APPROVER','ROLE_ADMIN')")
    public ResponseEntity<?> getAll(Authentication auth) {
        boolean isReleaser = auth.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_RELEASER"));

        boolean isEntry = auth.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ENTRY"));

        boolean isApprover = auth.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_APPROVER"));

        if (isReleaser) {
            return ResponseEntity.ok(
                    service.getAllByStatuses(List.of("APPROVED", "COMPLETED"))
            );
        } else if (isEntry) {
            return ResponseEntity.ok(
                    service.getAllByStatuses(List.of("SAVED", "FAILED"))
            );
        } else if (isApprover) {
            return ResponseEntity.ok(
                    service.getAllByStatuses(List.of("SAVED"))
            );
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


    @PutMapping("/{id}/release")
    @PreAuthorize("hasAnyAuthority('ROLE_RELEASER','ROLE_RELEASER')")

    public ResponseEntity<SadadRecordResponse> release(
            @PathVariable("id") Long id,
            @RequestParam(name = "success", defaultValue = "true") boolean success,
            Authentication auth) {

        String username = (auth != null) ? auth.getName() : "system";

        SadadRecord released = service.release(id, username, success);

        return ResponseEntity.ok(
                new SadadRecordResponse(
                        released.getId(),
                        released.getStatus(),
                        Map.of(
                                "message", released.getStatus().equals("COMPLETED")
                                        ? "SADAD record released and completed successfully"
                                        : "SADAD record release failed"
                        )
                )
        );
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
    public ResponseEntity<SadadRecordResponse> approve(
            @PathVariable("id") Long id,
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
