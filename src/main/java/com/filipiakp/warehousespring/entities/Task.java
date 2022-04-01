package com.filipiakp.warehousespring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(name="warehouse_task")
public class Task {
    @Id
    private Long id;
    private Date creationDate;
    private Date finishedDate;
    private String name;
    private String description;
    private String phase;
    private int importance;
    @OneToMany
    @JoinColumn(name = "employee_id")
    private List<EmployeeTask> employees;
    @ManyToOne
    @JoinColumn(name = "contractor_nip")
    private Contractor contractor;

    public Task() {
        employees = new LinkedList<>();
        creationDate = new Date();
    }

}
