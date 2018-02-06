package ru.performanceLab.yourNote.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "note")
public class Note implements Serializable {

    @Column(name = "name")
    public String name;
    @Column(name = "text")
    public String text;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "note_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Note(User user, String name, String text) {
        this.user = user;
        this.name = name;
        this.text = text;
    }

    public Note() {
    }

    public Long getId() {
        return id;
    }

    public void setNoteText(String text) {
        this.text = text;
    }

    public String getNoteName() {
        return name;
    }

    public void setNoteName(String name) {
        this.name = name;
    }
}