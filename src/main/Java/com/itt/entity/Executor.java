package com.itt.entity;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "executors")
public class Executor {

    private String name;
    private String type;
    private String status = "active";
    private List<Task> tasks;

    @Id
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "executor", fetch = FetchType.LAZY)
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Executor executor = (Executor) o;

        if (name != null ? !name.equals(executor.name) : executor.name != null) return false;
        if (type != null ? !type.equals(executor.type) : executor.type != null) return false;
        if (status != null ? !status.equals(executor.status) : executor.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "'" + name + "," + type + "'";
    }
}
