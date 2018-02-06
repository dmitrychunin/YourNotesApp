package ru.performanceLab.yourNote.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "note")
public class Note  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "note_id")
    private Long id;

    public Long getId() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
//    @JoinColumns({
//            @JoinColumn(name = "name", insertable = false, updatable = false),
//            @JoinColumn(name = "text", insertable = false, updatable = false)
//    })
    private User user;

    @Column(name = "name")
    public String name;

    @Column(name = "text")
    public String text;

    public Note(User user, String name, String text) {
        this.user = user;
        this.name = name;
        this.text = text;
    }

    public Note(){}

    public void setNoteText(String text) {
        this.text = text;
    }

    public void setNoteName(String name) {
        this.name = name;
    }

    public String getNoteName() {
        return name;
    }
}