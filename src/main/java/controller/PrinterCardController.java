package controller;

import model.Printer;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.Serializable;

@Named
@ViewScoped
public class PrinterCardController implements Serializable {
    @Inject
    private EntityManager em;
    private Printer printer;

    public Printer getPrinter() {
        return printer;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    @Transactional
    public String save() {
        em.merge(printer);
        return "printer_filter?faces-redirect=true";
    }

    @Transactional
    public String remove() {
        em.remove(em.merge(printer));
        return "printer_filter?faces-redirect=true";
    }
}
