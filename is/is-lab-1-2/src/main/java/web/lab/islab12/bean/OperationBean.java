package web.lab.islab12.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import web.lab.islab12.model.City;
import web.lab.islab12.service.CityService;

import java.io.Serializable;
import java.util.List;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

@Named
@SessionScoped
public class OperationBean implements Serializable {
    @Inject
    CityService service;
    private String prefix;
    private Float masl;
    private Integer cityA;
    private Integer cityB;
    private long sumTelephone;
    private List<City> prefixResult;
    private List<City> maslResult;
    private Double distanceAB;
    private Double distanceEarliestLatest;
    private boolean sumCalculated;
    private boolean prefixQueried;
    private boolean maslQueried;
    private boolean distanceQueried;
    private boolean earliestLatestQueried;

    public void calcSum() {
        sumTelephone = service.sumTelephoneCodes();
        sumCalculated = true;
        facesInfo("Sum telephone codes: " + sumTelephone);
    }

    public void findByPrefix() {
        if (prefix == null || prefix.isBlank()) {
            prefixResult = List.of();
            facesInfo("Enter a prefix to search");
            return;
        }
        prefixResult = service.nameStartsWith(prefix.trim());
        prefixQueried = true;
        facesInfo(prefixResult.isEmpty() ? "No names with given prefix" : "Found " + prefixResult.size() + " cities");
    }

    public void findByMasl() {
        if (masl != null) {
            maslResult = service.metersAboveSeaLevelGreater(masl);
            maslQueried = true;
            facesInfo(maslResult.isEmpty() ? "No cities with MASL > " + masl : "Found " + maslResult.size() + " cities");
        } else {
            facesInfo("Enter MASL value");
        }
    }

    public void calcDistance() {
        if (cityA != null && cityB != null) {
            distanceAB = service.distance(cityA, cityB);
            distanceQueried = true;
            facesInfo("Distance: " + distanceAB);
        } else {
            facesInfo("Enter both city IDs");
        }
    }

    public void calcEarliestLatest() {
        distanceEarliestLatest = service.distanceEarliestLatest();
        earliestLatestQueried = true;
        facesInfo("Distance earliest-latest: " + distanceEarliestLatest);
    }

    public void refresh() {
        if (sumCalculated) sumTelephone = service.sumTelephoneCodes();
        if (prefixQueried) {
            if (prefix != null && !prefix.isBlank()) prefixResult = service.nameStartsWith(prefix.trim());
            else prefixResult = List.of();
        }
        if (maslQueried && masl != null) maslResult = service.metersAboveSeaLevelGreater(masl);
        if (distanceQueried && cityA != null && cityB != null) distanceAB = service.distance(cityA, cityB);
        if (earliestLatestQueried) distanceEarliestLatest = service.distanceEarliestLatest();
    }

    private void facesInfo(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
    }

    public String getPrefix() { return prefix; }
    public void setPrefix(String p) { this.prefix = p; }
    public Float getMasl() { return masl; }
    public void setMasl(Float m) { this.masl = m; }
    public Integer getCityA() { return cityA; }
    public void setCityA(Integer c) { this.cityA = c; }
    public Integer getCityB() { return cityB; }
    public void setCityB(Integer c) { this.cityB = c; }
    public long getSumTelephone() { return sumTelephone; }
    public List<City> getPrefixResult() { return prefixResult; }
    public List<City> getMaslResult() { return maslResult; }
    public Double getDistanceAB() { return distanceAB; }
    public Double getDistanceEarliestLatest() { return distanceEarliestLatest; }
}
