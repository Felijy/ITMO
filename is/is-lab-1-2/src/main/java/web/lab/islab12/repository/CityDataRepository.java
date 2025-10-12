package web.lab.islab12.repository;

import jakarta.data.repository.Repository; // annotation marker
import jakarta.data.repository.Query;
import web.lab.islab12.model.City;

import java.util.List;

@Repository
public interface CityDataRepository {
    City save(City c);
    City findById(Integer id);
    List<City> findAll();
    void deleteById(Integer id);

    List<City> findByNameStartingWithIgnoreCase(String prefix);
    List<City> findByMetersAboveSeaLevelGreaterThan(Float value);

    @Query("select sum(c.telephoneCode) from City c")
    Long sumTelephoneCodes();

    @Query("select c from City c where c.establishmentDate is not null")
    List<City> findWithEstablishmentDate();
}
