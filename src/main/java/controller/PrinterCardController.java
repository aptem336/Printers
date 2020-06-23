package controller;

import model.ExpendableMileage;
import model.ExpendableResource;
import model.Note;
import model.Printer;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.security.enterprise.SecurityContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.security.Principal;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class PrinterCardController implements Serializable {
    @Inject
    private SecurityContext securityContext;
    @Inject
    private EntityManager em;
    private Printer printer;
    private Note note;
    private Mode mode;

    @PostConstruct
    private void postConstruct() {
        note = new Note();
    }

    public Printer getPrinter() {
        if (printer == null) {
            if (securityContext.isCallerInRole("ENGINEER")) {
                printer = new Printer();
                Note note = new Note();
                note.setContent("Взят на обслуживание");
                note.setNoteType(Note.NoteType.HISTORY);
                note.setDate(new Date(System.currentTimeMillis()));
                note.setNoteUser(securityContext.getCallerPrincipal().getName());
                note.setPrinter(printer);
                printer.getNotes().add(note);
                mode = Mode.CREATE;
            } else {
                mode = Mode.VIEW;
            }
        }
        return printer;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
        if (securityContext.isCallerInRole("ENGINEER")) mode = Mode.EDIT;
        else mode = Mode.VIEW;
    }

    public String getUser() {
        Principal callerPrincipal = securityContext.getCallerPrincipal();
        return callerPrincipal == null ? null : callerPrincipal.getName();
    }

    @Transactional
    public String save() {
        if (mode == Mode.CREATE) {
            if (em.find(Printer.class, printer.getInventoryNumber()) != null) {
                printer.setInventoryNumber(null);
                FacesContext.getCurrentInstance().validationFailed();
                return null;
            } else {
                em.persist(printer);
            }
        } else {
            em.merge(printer);
        }
        return "printer_filter?faces-redirect=true";
    }

    @Transactional
    public String remove() {
        em.remove(em.merge(printer));
        return "printer_filter?faces-redirect=true";
    }

    public void addNote() {
        Note note = new Note();
        note.setNoteType(Note.NoteType.COMMENT);
        note.setDate(new Date(System.currentTimeMillis()));
        note.setNoteUser(securityContext.getCallerPrincipal().getName());
        note.setPrinter(printer);
        printer.getNotes().add(note);
    }

    public void removeNote(Note note) {
        note.setPrinter(null);
        printer.getNotes().remove(note);
    }

    public void replaceExpendable(ExpendableResource expendableResource) {
        ExpendableMileage expendableMileage = printer.getExpendableMileage(expendableResource);
        if (expendableMileage == null) {
            expendableMileage = new ExpendableMileage();
            expendableMileage.setExpendable(expendableResource.getExpendable());
            printer.getExpendableMileages().add(expendableMileage);
            expendableMileage.setPrinter(printer);
        }
        expendableMileage.setMileage(printer.getCounter() == null ? 0 : printer.getCounter());

        Note note = new Note();
        note.setContent(String.format("Заменён расходник: %s", expendableResource.getExpendable().getName()));
        note.setNoteType(Note.NoteType.HISTORY);
        note.setDate(new Date(System.currentTimeMillis()));
        note.setNoteUser(securityContext.getCallerPrincipal().getName());
        note.setPrinter(printer);
        printer.getNotes().add(note);
    }

    public Note getNote() {
        return note;
    }

    public void all() {
        note.setNoteType(null);
    }

    public void comment() {
        note.setNoteType(Note.NoteType.COMMENT);
    }

    public void history() {
        note.setNoteType(Note.NoteType.HISTORY);
    }

    public List<Note> getNotes() {
        return printer.getNotes().stream().filter(n -> note.getNoteType() == null
                || n.getNoteType() == note.getNoteType()).collect(Collectors.toList());
    }


    public Mode getMode() {
        return mode;
    }

    public Boolean getCreate() {
        return mode == Mode.CREATE;
    }

    public Boolean getEdit() {
        return mode == Mode.EDIT;
    }

    public Boolean getView() {
        return mode == Mode.VIEW;
    }

    public enum Mode {
        CREATE, EDIT, VIEW
    }
}
