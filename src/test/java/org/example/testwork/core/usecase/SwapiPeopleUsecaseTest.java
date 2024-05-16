package org.example.testwork.core.usecase;

import org.example.testwork.core.gateway.SwapiGateway;
import org.example.testwork.core.gateway.dto.SwapiPeopleDto;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class SwapiPeopleUsecaseTest {

    @InjectMocks
    private SwapiPeopleUsecase usecase;

    @Mock
    private SwapiGateway gateway;

    @Test
    void retornaPeople() {
        var mockEsperado = this.generate();
        var uuidGerado = UUID.randomUUID().toString();
        ResponseEntity<SwapiPeopleDto> myEntity = new ResponseEntity<>(mockEsperado, HttpStatus.OK);
        Mockito.when(this.gateway.people(Mockito.anyString())).thenReturn(myEntity);
//        Mockito.when(this.usecase.retornaPeople(Mockito.anyString())).thenReturn(mockEsperado);
        var resultado = this.usecase.retornaPeople(uuidGerado);

        // Primeiro teste é se meu resultado não é nulo
        Assertions.assertNotNull(resultado);

        // Segundo teste é se minha classe de gateway foi chamada pelo menos uma vez.
        Mockito.verify(this.gateway, Mockito.times(1)).people(uuidGerado);

        // Validar quantos campos eu achar necessário
        Assertions.assertEquals(resultado.getBirthYear(), mockEsperado.getBirthYear());

        // Validacao do tipo 'checksum'
        UUID uuidEsperado = UUID.nameUUIDFromBytes(mockEsperado.toString().getBytes());
        UUID uuidRecebido = UUID.nameUUIDFromBytes(resultado.toString().getBytes());
        Assertions.assertEquals(uuidRecebido, uuidEsperado);
    }

    private SwapiPeopleDto generate(){
        var data = Instancio.create(SwapiPeopleDto.class);
        return data;
    }
}