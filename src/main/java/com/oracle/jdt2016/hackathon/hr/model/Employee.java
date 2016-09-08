/**
 * Copyright (c) 2016 Oracle and/or its affiliates
 */
package com.oracle.jdt2016.hackathon.hr.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the EMPLOYEES database table.
 *
 */
@Entity
@Table(name="HR.EMPLOYEES")
@NamedQuery(name="Employee.findAll", query="SELECT e FROM Employee e")
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="EMPLOYEE_ID")
    private long employeeId;

    @Column(name="COMMISSION_PCT")
    private float commissionPct;

    @Column(name="EMAIL")
    private String email;

    @Column(name="FIRST_NAME")
    private String firstName;

    @Temporal(TemporalType.DATE)
    @Column(name="HIRE_DATE")
    private Date hireDate;

    @Column(name="LAST_NAME")
    private String lastName;

    @Column(name="PHONE_NUMBER")
    private String phoneNumber;

    @Column(name="SALARY")
    private float salary;

    @Column(name="DEPARTMENT_ID")
    private long departmentId;

    @Column(name="JOB_ID")
    private String jobId;

    @Column(name="MANAGER_ID")
    private long managerId;

    public Employee() {
    }

    public long getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public float getCommissionPct() {
        return this.commissionPct;
    }

    public void setCommissionPct(float commissionPct) {
        this.commissionPct = commissionPct;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getHireDate() {
        return this.hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public float getSalary() {
        return this.salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public long getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public String getJobId() {
        return this.jobId;
    }

    public void setJob(String jobId) {
        this.jobId = jobId;
    }

    public long getManagerId() {
        return this.managerId;
    }

    public void setEmployee(long managerId) {
        this.managerId = managerId;
    }

}