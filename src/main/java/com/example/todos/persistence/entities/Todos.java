package com.example.todos.persistence.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

@Entity
@Table(name = "todos")
public class Todos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    @NotBlank
    private String name;

    @Column(name="description")
    @NotBlank
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
    @Column(name="due_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date due_date;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
    @Column(name="creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation_date;


    public Todos() {};

    public Todos(Integer id, String name, String description, Date dueDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.due_date = dueDate;

        long millis = System.currentTimeMillis();
        this.creation_date =  new java.sql.Date(millis);
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }
}