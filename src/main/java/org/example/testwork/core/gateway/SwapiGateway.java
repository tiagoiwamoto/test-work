package org.example.testwork.core.gateway;

import org.example.testwork.core.gateway.dto.SwapiPeople;
import org.example.testwork.core.gateway.dto.SwapiPeopleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "swapi", url = "https://swapi.dev/api")
public interface SwapiGateway {

    @GetMapping(path = "/people")
    ResponseEntity<SwapiPeople> peoples();

    @GetMapping(path = "/people/{id}")
    ResponseEntity<SwapiPeopleDto> people(@PathVariable(name = "id") String id);

    @GetMapping(path = "/planets/{id}")
    ResponseEntity planets(@PathVariable(name = "id") Long id);

    @GetMapping(path = "/starships/{id}")
    ResponseEntity starships(@PathVariable(name = "id") Long id);
}
