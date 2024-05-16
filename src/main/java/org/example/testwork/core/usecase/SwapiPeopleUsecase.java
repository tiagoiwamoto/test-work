package org.example.testwork.core.usecase;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.testwork.config.Usecase;
import org.example.testwork.core.gateway.SwapiGateway;
import org.example.testwork.core.gateway.dto.ResultsItem;
import org.example.testwork.core.gateway.dto.SwapiPeopleDto;
import org.springframework.http.HttpStatus;

import java.util.List;

@Usecase
@Slf4j
@RequiredArgsConstructor
public class SwapiPeopleUsecase {

    private final SwapiGateway gateway;

    public List<ResultsItem> retornaApenasJedi(){
        var jedis = List.of("Yoda", "Count Dooku", "Mace Windu", "Qui-Gon Jinn",
                "Depa Billaba", "Younglings", "Obi-Wan Kenobi", "Kana Jarrus", "Anakin Skywalker",
                "Luke Skywalker", "Ezra Bridger", "Ahsoka Tano", "Leia Organa", "Sabine Wren",
                "Ben Solo", "Rey", "Din Grogu");

        var swapiResponse = this.gateway.peoples();
        var jedisLocalizados = swapiResponse.getBody().getResults().stream().filter(people -> jedis.contains(people.getName()));

        return jedisLocalizados.toList();
    }

    public SwapiPeopleDto retornaPeople(String id){

        try{
            var swapiResponse = this.gateway.people(id);
            return swapiResponse.getBody();
        }catch (FeignException e){
            if(e.status() == HttpStatus.NOT_FOUND.value()){
                throw new RuntimeException("Não localizamos um personagem com este identificador");
            }
            throw new RuntimeException("Erro interno, tente novamente mais tarde.");
        }

    }

}
