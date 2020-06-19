package converter;

import model.Printer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Named
public class PrinterConverter implements Converter<Printer> {
    @Inject
    private EntityManager em;

    @Override
    public Printer getAsObject(FacesContext facesContext, UIComponent uiComponent, String id) {
        if (id == null) {
            return null;
        }
        return em.find(Printer.class, id);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Printer printer) {
        return printer == null ? null : printer.getInventoryNumber();
    }
}
