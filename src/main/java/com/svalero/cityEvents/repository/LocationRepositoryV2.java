package com.svalero.cityEvents.repository;

import com.svalero.cityEvents.domain.Location;
import com.svalero.cityEvents.domain.LocationV2;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepositoryV2 extends CrudRepository<LocationV2, Long> {

    List<LocationV2> findAll(); //con esto declaramos que nos haga un select * from Location
    List<LocationV2> findByCity(String city);
    List<LocationV2> findByDisabledAccessTrue();
    List<LocationV2> findByPostalCode(int postalCode);
    @Transactional //para borrado personalizado
    void deleteByCity(String city);
}
