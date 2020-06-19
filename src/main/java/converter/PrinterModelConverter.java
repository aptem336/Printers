package converter;

import model.PrinterModel;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Named
public class PrinterModelConverter implements Converter<PrinterModel> {
    @Inject
    private EntityManager em;

    @Override
    public PrinterModel getAsObject(FacesContext facesContext, UIComponent uiComponent, String model) {
        if (model == null) {
            return null;
        }
        return em.find(PrinterModel.class, model);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, PrinterModel printerModel) {
        return printerModel == null ? null : printerModel.getModel();
    }
}
