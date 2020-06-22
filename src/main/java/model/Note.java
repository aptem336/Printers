package model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
public class Note {
    @Id
    @GeneratedValue
    private String id;
    @ManyToOne(optional = false)
    private Printer printer;
    @NotNull
    @Column(nullable = false)
    private String noteUser;
    @NotNull
    @Column(nullable = false)
    private Date date;
    @NotNull
    @Column(nullable = false)
    private String content;
    @NotNull
    @Column(nullable = false)
    @Enumerated
    private NoteType noteType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Printer getPrinter() {
        return printer;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    public String getNoteUser() {
        return noteUser;
    }

    public void setNoteUser(String user) {
        this.noteUser = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public NoteType getNoteType() {
        return noteType;
    }

    public void setNoteType(NoteType noteType) {
        this.noteType = noteType;
    }

    public enum NoteType {
        COMMENT, HISTORY
    }
}
