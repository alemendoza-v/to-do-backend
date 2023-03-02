package com.alejandro.todolist.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private LocalDateTime doneDate;

    private LocalDateTime createdAt;

    @Nonnull
    private int priority;

    // Constructors
    public ToDo(@JsonProperty("text") String text, 
                @JsonProperty("priority") int priority,
                @JsonProperty("dueDate") LocalDate dueDate) {
        this.id = UUID.randomUUID();
        this.text = text;
        this.dueDate = dueDate;
        this.isDone = false;
        this.doneDate = null;
        this.createdAt = LocalDateTime.now();
        this.priority = priority;
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

    public LocalDateTime getDoneDate() {
        return doneDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getPriority() {
        return priority;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public void setDoneDate(LocalDateTime doneDate) {
        this.doneDate = doneDate;
    }
}
