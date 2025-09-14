package com.example.sadad.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cost_centers")
public class CostCenter {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "sadad_record_id", nullable = false)
    private SadadRecord record;

    private String costCenterCode;
    private String costCenterDesc;
    private Double percentage;

    // getters/setters
    public void setRecord(SadadRecord record) { this.record = record; }
    public String getCostCenterCode() { return costCenterCode; }
    public void setCostCenterCode(String costCenterCode) { this.costCenterCode = costCenterCode; }
    public String getCostCenterDesc() { return costCenterDesc; }
    public void setCostCenterDesc(String costCenterDesc) { this.costCenterDesc = costCenterDesc; }
    public Double getPercentage() { return percentage; }
    public void setPercentage(Double percentage) { this.percentage = percentage; }
}
