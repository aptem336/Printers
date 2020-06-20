package controller;

import model.ReplaceableExpendable;
import model.Printer;
import model.PrinterModel;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Named
@ViewScoped
public class PrinterFilterController implements Serializable {
    @Inject
    private QueryController queryController;
    private List<Printer> printers;
    private Printer printer;

    @PostConstruct
    private void postConstruct() {
        printer = new Printer();
        printer.setPrinterModel(new PrinterModel());
        printer.setReplaceableExpendable(queryController.getAllExpendables().stream().map(e ->
                new ReplaceableExpendable(e.getName(), false)).collect(Collectors.toList()));
        printers = queryController.getAllPrinters();
    }

    public Printer getPrinter() {
        return printer;
    }

    public List<Printer> getFilteredPrinters() {
        Stream<Printer> printerStream = printers.stream();
        if (printer.getInventoryNumber() != null) {
            printerStream = printerStream.filter(p -> p.getInventoryNumber().contains(printer.getInventoryNumber()));
        }
        printerStream = printerStream.filter(p -> p.getPrinterModel().getModel().contains(printer.getPrinterModel().getModel()));
        if (printer.getLocation() != null) {
            printerStream = printerStream.filter(p -> p.getLocation().contains(printer.getLocation()));
        }
        printerStream = printerStream.filter(p -> p.getReplaceableExpendables().containsAll(
                printer.getReplaceableExpendables())
        );
        return printerStream.collect(Collectors.toList());
    }

    public String logout() {
        return "printer_filter?faces-redirect=true";
    }
}
