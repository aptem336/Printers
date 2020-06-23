package controller;

import model.Printer;
import model.PrinterModel;
import model.ReplaceableExpendable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Named
@ViewScoped
public class PrinterFilterController implements Serializable {
    @Inject
    private SecurityContext securityContext;
    @Inject
    private QueryController queryController;
    private List<Printer> printers;
    private Printer printer;
    private Mode mode;

    @PostConstruct
    private void postConstruct() {
        printer = new Printer();
        printer.setPrinterModel(new PrinterModel());
        printer.setReplaceableExpendable(queryController.getAllExpendables().stream().map(e ->
                new ReplaceableExpendable(e.getName(), false, null)).collect(Collectors.toList()));
        printers = queryController.getAllPrinters();
        if (securityContext.isCallerInRole("ENGINEER")) {
            mode = Mode.CREATE;
        } else {
            mode = Mode.VIEW;
        }
    }

    public Printer getPrinter() {
        return printer;
    }

    public List<Printer> getFilteredPrinters() {
        Stream<Printer> printerStream = printers.stream();
        if (printer.getInventoryNumber() != null) {
            printerStream = printerStream.filter(p -> p.getInventoryNumber().toLowerCase().contains(printer.getInventoryNumber().toLowerCase()));
        }
        printerStream = printerStream.filter(p -> p.getPrinterModel().getModel().toLowerCase().contains(printer.getPrinterModel().getModel().toLowerCase()));
        if (printer.getLocation() != null) {
            printerStream = printerStream.filter(p -> p.getLocation().toLowerCase().contains(printer.getLocation().toLowerCase()));
        }
        printerStream = printerStream.filter(p -> p.getReplaceableExpendables().containsAll(
                printer.getReplaceableExpendables().stream().filter(ReplaceableExpendable::getReplaceable).collect(Collectors.toList()))
        );
        return printerStream.collect(Collectors.toList());
    }

    public String logout() {
        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
        return "login?faces-redirect=true";
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
