package com.filipiakp.warehousespring.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "warehouse_employee_task")
public class EmployeeTask {
    @Id
    @Column(name = "employeeTaskId")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    private Date addedDate;
    private Date removedDate;
}
