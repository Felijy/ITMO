package web.lab.islab12.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.ConstraintViolationException;
import web.lab.islab12.model.*;
import web.lab.islab12.service.CityService;
import web.lab.islab12.websocket.CityUpdatesEndpoint;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Named
@SessionScoped
public class CityBean implements Serializable {
    @Inject
    CityService service;
    private City current = new City();
    private int currentPage = 0;
    private final int pageSize = 10;
    private String filterField;
    private String filterValue;
    private String sortField;
    private boolean asc = true;
    private City governorCity;

    public List<City> getCities() {
        return service.list(currentPage, pageSize, filterField, filterValue, sortField, asc);
    }

    public long getTotalPages() {
        long count = service.count(filterField, filterValue);
        return (count + pageSize - 1) / pageSize;
    }

    public void nextPage() {
        if (currentPage + 1 < getTotalPages()) currentPage++;
    }

    public void prevPage() {
        if (currentPage > 0) currentPage--;
    }

    public String createForm() {
        current = new City();
        current.setCoordinates(new Coordinates());
        return "create.xhtml";
    }

    public String edit(int id) {
        City c = service.find(id);
        if (c != null) {
            current = c;
        }
        return "edit.xhtml";
    }

    private void info(String msg) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.getExternalContext().getFlash().setKeepMessages(true);
        ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
    }

    private void error(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
    }

    public String saveNew() {
        try {
            service.saveNew(current);
            CityUpdatesEndpoint.broadcast();
            info("City created");
            return "index.xhtml?faces-redirect=true";
        } catch (ConstraintViolationException e) {
            error("Validation error while creating");
            return null;
        }
    }

    public String update() {
        try {
            service.update(current);
            CityUpdatesEndpoint.broadcast();
            info("City updated");
            return "index.xhtml?faces-redirect=true";
        } catch (ConstraintViolationException e) {
            error("Validation error while updating");
            return null;
        }
    }

    public void delete(int id) {
        service.delete(id);
        info("City deleted");
        CityUpdatesEndpoint.broadcast();
    }

    public City getCurrent() {
        if (current.getCoordinates() == null) {
            current.setCoordinates(new Coordinates());
        }
        return current;
    }

    public Climate[] getClimates() {
        return Climate.values();
    }

    public StandardOfLiving[] getStandards() {
        return StandardOfLiving.values();
    }

    public void setFilterField(String f) {
        this.filterField = f;
    }

    public String getFilterField() {
        return filterField;
    }

    public void setFilterValue(String v) {
        this.filterValue = v;
        currentPage = 0;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String s) {
        if (s != null && s.equals(sortField)) asc = !asc;
        else {
            sortField = s;
            asc = true;
        }
    }

    public boolean isAsc() {
        return asc;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setGovernorName(String name) {
        if (current.getGovernor() == null) current.setGovernor(new Human());
        current.getGovernor().setName(name);
    }

    public String getGovernorName() {
        return current.getGovernor() != null ? current.getGovernor().getName() : null;
    }

    public void setGovernorBirthday(LocalDateTime dt) {
        if (current.getGovernor() == null) current.setGovernor(new Human());
        current.getGovernor().setBirthday(dt);
    }

    public LocalDateTime getGovernorBirthday() {
        return current.getGovernor() != null ? current.getGovernor().getBirthday() : null;
    }

    public String viewGovernor(int id) {
        City c = service.find(id);
        if (c != null && c.getGovernor() != null) {
            governorCity = c;
            return "governor.xhtml?faces-redirect=false";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No governor for selected city", null));
        return null;
    }

    public String backFromGovernor() {
        governorCity = null;
        return "index.xhtml?faces-redirect=true";
    }

    public City getGovernorCity() {
        return governorCity;
    }

    public boolean hasGovernor() {
        return governorCity != null && governorCity.getGovernor() != null;
    }

    public boolean getHasGovernor() {
        return hasGovernor();
    }

    public void applyFilter() {
        currentPage = 0;
        long cnt = service.count(filterField, filterValue);
        if (cnt == 0) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "No results for current filter", null));
        }
    }

    public void clearGovernor() {
        governorCity = null;
    }
}
