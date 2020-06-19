package model;

import javax.persistence.*;

@Entity
public class Notes {
    @Id
    @GeneratedValue
    private String id;
    @ManyToOne
    private Printer printer;
    private String noteUser;
    private String content;
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

    public NoteType getNoteType() {
        return noteType;
    }

    public void setNoteType(NoteType noteType) {
        this.noteType = noteType;
    }

    private enum NoteType {
        //IMPL
    }
}
