package web.lab.islab12.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import web.lab.islab12.model.City;
import web.lab.islab12.model.Human;
import web.lab.islab12.repository.CityDataRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ApplicationScoped
public class CityService {

    @Inject
    CityDataRepository repo;

    public City find(int id) {
        return repo.findById(id);
    }

    public City saveNew(City c) {
        if (c.getCreationDate() == null) {
            c.prePersist();
        }
        return repo.save(c);
    }

    public City update(City c) {
        Human gov = c.getGovernor();
        if (gov != null && gov.getName() != null && gov.getName().isBlank()) {
            c.setGovernor(null);
        }
        return repo.save(c);
    }

    public void delete(int id) {
        repo.deleteById(id);
    }

    public List<City> list(int page, int size, String filterField, String filterValue, String sortField, boolean asc) {
        List<City> all = repo.findAll();
        List<City> filtered = filter(all, filterField, filterValue);
        Comparator<City> cmp = buildComparator(sortField);
        if (cmp != null) {
            if (!asc) cmp = cmp.reversed();
            filtered = filtered.stream().sorted(cmp).collect(Collectors.toList());
        }
        int from = Math.min(page * size, filtered.size());
        int to = Math.min(from + size, filtered.size());
        return filtered.subList(from, to);
    }

    public long count(String filterField, String filterValue) {
        return filter(repo.findAll(), filterField, filterValue).size();
    }

    private List<City> filter(List<City> src, String field, String value) {
        if (field == null || field.isBlank() || value == null || value.isBlank()) return src;
        String val = value.trim();
        Predicate<City> p = switch (field) {
            case "id" -> c -> c.getId() != null && c.getId().toString().equals(val);
            case "name" -> c -> c.getName() != null && c.getName().equals(val);
            case "creationDate" -> c -> matchDate(c.getCreationDate(), val);
            case "area" ->
                    parseFloat(val).map(v -> (Predicate<City>) c -> c.getArea() != null && c.getArea().equals(v)).orElse(c -> false);
            case "population" ->
                    parseLong(val).map(v -> (Predicate<City>) c -> c.getPopulation() == v).orElse(c -> false);
            case "establishmentDate" -> c -> matchLocalDateTime(c.getEstablishmentDate(), val);
            case "capital" ->
                    parseBoolean(val).map(v -> (Predicate<City>) c -> Objects.equals(c.getCapital(), v)).orElse(c -> false);
            case "metersAboveSeaLevel" ->
                    parseFloat(val).map(v -> (Predicate<City>) c -> Objects.equals(c.getMetersAboveSeaLevel(), v)).orElse(c -> false);
            case "telephoneCode" ->
                    parseLong(val).map(v -> (Predicate<City>) c -> c.getTelephoneCode() == v).orElse(c -> false);
            case "climate" -> c -> c.getClimate() != null && c.getClimate().name().equalsIgnoreCase(val);
            case "standardOfLiving" ->
                    c -> c.getStandardOfLiving() != null && c.getStandardOfLiving().name().equalsIgnoreCase(val);
            case "governorName" ->
                    c -> c.getGovernor() != null && c.getGovernor().getName() != null && c.getGovernor().getName().equals(val);
            default -> c -> true;
        };
        return src.stream().filter(p).collect(Collectors.toList());
    }

    private boolean matchDate(Date date, String val) {
        if (date == null) return false;
        try {
            if (val.length() == 10) {
                LocalDate d = LocalDate.parse(val);
                Date from = Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date to = Date.from(d.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
                return !date.before(from) && date.before(to);
            } else {
                LocalDateTime dt = LocalDateTime.parse(val);
                Date exact = Date.from(dt.atZone(ZoneId.systemDefault()).toInstant());
                return date.equals(exact);
            }
        } catch (Exception e) {
            return false;
        }
    }

    private boolean matchLocalDateTime(LocalDateTime dt, String val) {
        if (dt == null) return false;
        try {
            if (val.length() == 10) {
                LocalDate d = LocalDate.parse(val);
                return dt.toLocalDate().equals(d);
            } else {
                LocalDateTime parsed = LocalDateTime.parse(val);
                return dt.equals(parsed);
            }
        } catch (Exception e) {
            return false;
        }
    }

    private Optional<Float> parseFloat(String s) {
        try {
            return Optional.of(Float.parseFloat(s));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Optional<Long> parseLong(String s) {
        try {
            return Optional.of(Long.parseLong(s));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Optional<Boolean> parseBoolean(String s) {
        String low = s.toLowerCase();
        if (List.of("true", "yes", "1").contains(low)) return Optional.of(Boolean.TRUE);
        if (List.of("false", "no", "0").contains(low)) return Optional.of(Boolean.FALSE);
        return Optional.empty();
    }

    private Comparator<City> buildComparator(String field) {
        if (field == null) return null;
        return switch (field) {
            case "id" -> Comparator.comparing(c -> c.getId() == null ? Integer.MAX_VALUE : c.getId());
            case "name" -> Comparator.comparing(c -> nullSafe(c.getName()));
            case "area" -> Comparator.comparing(c -> c.getArea() == null ? Float.MAX_VALUE : c.getArea());
            case "population" -> Comparator.comparingLong(City::getPopulation);
            case "establishmentDate" ->
                    Comparator.comparing(c -> c.getEstablishmentDate() == null ? LocalDateTime.MAX : c.getEstablishmentDate());
            case "capital" -> Comparator.comparing(c -> c.getCapital() == null ? Boolean.FALSE : c.getCapital());
            case "metersAboveSeaLevel" ->
                    Comparator.comparing(c -> c.getMetersAboveSeaLevel() == null ? Float.MAX_VALUE : c.getMetersAboveSeaLevel());
            case "telephoneCode" -> Comparator.comparingLong(City::getTelephoneCode);
            case "climate" -> Comparator.comparing(c -> c.getClimate() == null ? "" : c.getClimate().name());
            case "standardOfLiving" ->
                    Comparator.comparing(c -> c.getStandardOfLiving() == null ? "" : c.getStandardOfLiving().name());
            case "creationDate" ->
                    Comparator.comparing(c -> c.getCreationDate() == null ? new Date(0) : c.getCreationDate());
            case "governorName" ->
                    Comparator.comparing(c -> c.getGovernor() == null ? "" : nullSafe(c.getGovernor().getName()));
            default -> null;
        };
    }

    private String nullSafe(String s) {
        return s == null ? "" : s;
    }

    public long sumTelephoneCodes() {
        Long s = repo.sumTelephoneCodes();
        return s == null ? 0L : s;
    }

    public List<City> nameStartsWith(String prefix) {
        if (prefix == null || prefix.isBlank()) return List.of();
        return repo.findByNameStartingWithIgnoreCase(prefix.trim());
    }

    public List<City> metersAboveSeaLevelGreater(float val) {
        return repo.findByMetersAboveSeaLevelGreaterThan(val);
    }

    public double distance(int id1, int id2) {
        City a = find(id1);
        City b = find(id2);
        if (a == null || b == null || a.getCoordinates() == null || b.getCoordinates() == null) return 0d;
        double dx = a.getCoordinates().getX() - b.getCoordinates().getX();
        double dy = a.getCoordinates().getY() - b.getCoordinates().getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double distanceEarliestLatest() {
        List<City> withDates = repo.findWithEstablishmentDate();
        City earliest = null;
        City latest = null;
        for (City c : withDates) {
            if (earliest == null || c.getEstablishmentDate().isBefore(earliest.getEstablishmentDate())) earliest = c;
            if (latest == null || c.getEstablishmentDate().isAfter(latest.getEstablishmentDate())) latest = c;
        }
        if (earliest == null || latest == null || earliest == latest) return 0d;
        if (earliest.getCoordinates() == null || latest.getCoordinates() == null) return 0d;
        double dx = earliest.getCoordinates().getX() - latest.getCoordinates().getX();
        double dy = earliest.getCoordinates().getY() - latest.getCoordinates().getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
