package com.example.sadad.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "LOCATIONS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LocationRef {
    @Id
    @Column(name = "LOCATION_CODE")
    private String locationCode;

    @Column(name = "LOCATION_NAME")
    private String locationName;
}
