package controller;

import model.Expendable;
import model.Printer;
import model.PrinterModel;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class QueryController implements Serializable {
    public static final String getAllPrinters = "getAllPrinters";
    public static final String getAllPrinterModels = "getAllPrinterModels";
    public static final String getAllExpendables = "getAllExpendables";
    @Inject
    private EntityManager em;

    public List<PrinterModel> getAllPrinterModels() {
        return em.createNamedQuery(getAllPrinterModels, PrinterModel.class).getResultList();
    }

    public List<Printer> getAllPrinters() {
        return em.createNamedQuery(getAllPrinters, Printer.class).getResultList();
    }

    public List<Expendable> getAllExpendables() {
        return em.createNamedQuery(getAllExpendables, Expendable.class).getResultList();
    }
}
