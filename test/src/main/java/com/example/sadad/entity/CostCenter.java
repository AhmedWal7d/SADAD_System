package com.example.sadad.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "cost_centers")
public class CostCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sadad_record_id", nullable = false)
    @JsonIgnore   // <-- ده يمنع الloop
    private SadadRecord record;

    private String costCenterCode;
    private String costCenterDesc;

    @Column(precision = 5, scale = 2) // يدعم أرقام من 0.01 إلى 100.00
    private BigDecimal percentage;

    // -------- Getters & Setters --------
    public Long getId() { return id; }

    public SadadRecord getRecord() { return record; }
    public void setRecord(SadadRecord record) { this.record = record; }

    public String getCostCenterCode() { return costCenterCode; }
    public void setCostCenterCode(String costCenterCode) { this.costCenterCode = costCenterCode; }

    public String getCostCenterDesc() { return costCenterDesc; }
    public void setCostCenterDesc(String costCenterDesc) { this.costCenterDesc = costCenterDesc; }

    public BigDecimal getPercentage() { return percentage; }
    public void setPercentage(BigDecimal percentage) { this.percentage = percentage; }
}
