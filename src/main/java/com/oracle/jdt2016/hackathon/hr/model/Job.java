/**
 * Copyright (c) 2016 Oracle and/or its affiliates
 */
package com.oracle.jdt2016.hackathon.hr.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the JOBS database table.
 *
 */
@Entity
@Table(name="HR.JOBS")
@NamedQuery(name="Job.findAll", query="SELECT j FROM Job j")
public class Job implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="JOB_ID")
    private String jobId;

    @Column(name="JOB_TITLE")
    private String jobTitle;

    @Column(name="MAX_SALARY")
    private BigDecimal maxSalary;

    @Column(name="MIN_SALARY")
    private BigDecimal minSalary;

    public Job() {
    }

    public String getJobId() {
        return this.jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return this.jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public BigDecimal getMaxSalary() {
        return this.maxSalary;
    }

    public void setMaxSalary(BigDecimal maxSalary) {
        this.maxSalary = maxSalary;
    }

    public BigDecimal getMinSalary() {
        return this.minSalary;
    }

    public void setMinSalary(BigDecimal minSalary) {
        this.minSalary = minSalary;
    }

}