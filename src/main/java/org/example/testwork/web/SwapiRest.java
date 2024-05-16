package org.example.testwork.web;

import lombok.RequiredArgsConstructor;
import org.example.testwork.core.gateway.dto.SwapiPeopleDto;
import org.example.testwork.core.usecase.SwapiPeopleUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/starwars")
@RequiredArgsConstructor
public class SwapiRest {

    private final SwapiPeopleUsecase usecase;

    @GetMapping(path = "/peoples/{id}")
    public ResponseEntity peoples(@PathVariable(name = "id") String id){

        try{
            return ResponseEntity.ok(this.usecase.retornaPeople(id));
        }catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
