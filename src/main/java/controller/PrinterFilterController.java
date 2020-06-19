package controller;

import model.ExpendableResource;
import model.ExpendableResourceMileage;
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
    private Printer printer;
    private List<Printer> printers;

    @PostConstruct
    private void postConstruct() {
        printer = new Printer();

        PrinterModel printerModel = new PrinterModel();
        printerModel.setModel("");
        printer.setPrinterModel(printerModel);

        printer.setExpendableResourceMileages(queryController.getAllExpendables().stream().map(e -> {
            ExpendableResource expendableResource = new ExpendableResource();
            expendableResource.setExpendable(e);
            ExpendableResourceMileage expendableResourceMileage = new ExpendableResourceMileage(expendableResource, null);
            expendableResourceMileage.setReplaceable(false);
            return expendableResourceMileage;
        }).collect(Collectors.toList()));

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
        printerStream = printerStream.filter(p ->
                p.getReplaceableExpendableResourceMileage().stream()
                        .map(erm -> erm.getExpendableResource().getExpendable()).collect(Collectors.toList()).containsAll(
                        printer.getReplaceableExpendableResourceMileage().stream()
                                .map(erm -> erm.getExpendableResource().getExpendable()).collect(Collectors.toList()))
        );
        return printerStream.collect(Collectors.toList());
    }

    public String logout() {
        return "printer_filter?faces-redirect=true";
    }
}
