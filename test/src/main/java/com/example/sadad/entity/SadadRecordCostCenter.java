package com.example.sadad.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "sadad_record_cost_centers")
public class SadadRecordCostCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sadad_record_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private SadadRecord sadadRecord;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cost_center_code", referencedColumnName = "costCenterCode", nullable = false)
    private CostCenter costCenter;

    @Column(nullable = false)
    private BigDecimal percentage;

    public SadadRecordCostCenter() {
    }

    public SadadRecordCostCenter(SadadRecord sadadRecord,
                                 CostCenter costCenter,
                                 @NotNull @DecimalMin("0.0") @DecimalMax("100.0") BigDecimal percentage) {
        this.sadadRecord = sadadRecord;
        this.costCenter = costCenter;
        this.percentage = percentage;
    }

    public Long getId() { return id; }

    public SadadRecord getSadadRecord() { return sadadRecord; }
    public void setSadadRecord(SadadRecord sadadRecord) { this.sadadRecord = sadadRecord; }

    public CostCenter getCostCenter() { return costCenter; }
    public void setCostCenter(CostCenter costCenter) { this.costCenter = costCenter; }

    public BigDecimal getPercentage() { return percentage; }
    public void setPercentage(BigDecimal percentage) { this.percentage = percentage; }
}
