package com.alejandro.todolist.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public class ToDo {
    private UUID id;

    @NotBlank
    private String text;

    @Nullable
    private LocalDate dueDate;

    private boolean isDone;

    @Nullable
    private LocalDate doneDate;

    private LocalDate createdAt;

    private int priority;

    // Constructors
    public ToDo(UUID id, String text, int priority) {
        this.id = id;
        this.text = text;
        this.priority = priority;
        this.isDone = false;
        this.createdAt = LocalDate.now();
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public LocalDate getDoneDate() {
        return doneDate;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public int getPriority() {
        return priority;
    }
}
