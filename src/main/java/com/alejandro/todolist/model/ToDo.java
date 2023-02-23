package com.alejandro.todolist.model;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.annotation.Nonnull;
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

    @Nonnull
    private int priority;

    // Constructors
    public ToDo(@JsonProperty("text") String text, 
                @JsonProperty("priority") int priority,
                @JsonProperty("dueDate") LocalDate dueDate) {
        this.id = UUID.randomUUID();
        this.text = text;
        this.priority = priority;
        this.isDone = false;
        this.createdAt = LocalDate.now();
        this.dueDate = dueDate;
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

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public void setDoneDate(LocalDate doneDate) {
        this.doneDate = doneDate;
    }
}
