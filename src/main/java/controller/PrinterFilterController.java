package controller;

import model.Expendable;
import model.ExpendableEnumeration;
import model.Printer;
import model.PrinterExpendable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Named
@ViewScoped
public class PrinterFilterController implements Serializable {
    @Inject
    private EntityManager em;
    private Printer printer;
    private Set<Printer> printers;

    @PostConstruct
    private void postConstruct() {
        printer = new Printer();
        Stream.of(ExpendableEnumeration.values()).forEach(e ->
                printer.getExpendables().add(new PrinterExpendable(printer, em.find(Expendable.class, e))));

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Printer> cq = cb.createQuery(Printer.class);
        Root<Printer> rootEntry = cq.from(Printer.class);
        CriteriaQuery<Printer> all = cq.select(rootEntry);
        TypedQuery<Printer> allQuery = em.createQuery(all);
        printers = new LinkedHashSet<>(allQuery.getResultList());
    }

    public Printer getPrinter() {
        return printer;
    }

    public Set<Printer> getPrinters() {
        Stream<Printer> printerStream = printers.stream();
        if (printer.getInventoryNumber() != null) {
            printerStream = printerStream.filter(p -> p.getInventoryNumber().contains(printer.getInventoryNumber()));
        }
        if (printer.getModel() != null) {
            printerStream = printerStream.filter(p -> p.getModel().contains(printer.getModel()));
        }
        if (printer.getLocation() != null) {
            printerStream = printerStream.filter(p -> p.getLocation().contains(printer.getLocation()));
        }
        printerStream = printerStream.filter(p ->
                p.getReplaceableExpendables().stream().map(e ->
                        e.getExpendable().getEnumeration().getName()
                ).collect(Collectors.toSet()).containsAll(
                        printer.getReplaceableExpendables().stream().map(e ->
                                e.getExpendable().getEnumeration().getName()
                        ).collect(Collectors.toSet()))
        );
        return printerStream.collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public String logout() {
        return "printer_filter?faces-redirect=true";
    }

    public String addPrinter() {
        return null;
    }
}
