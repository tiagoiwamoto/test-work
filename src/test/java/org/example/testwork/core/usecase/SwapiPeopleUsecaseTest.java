package org.example.testwork.core.usecase;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.example.testwork.core.gateway.SwapiGateway;
import org.example.testwork.core.gateway.dto.SwapiPeopleDto;
import org.instancio.Instancio;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;


@ExtendWith(SpringExtension.class)
class SwapiPeopleUsecaseTest {

    @InjectMocks
    private SwapiPeopleUsecase usecase;

    @Mock
    private SwapiGateway gateway;

    private static GenericContainer<?> wiremockContainer;

    @BeforeAll
    public static void setUp() {
        // Configure WireMock to run inside a Testcontainer
        wiremockContainer = new GenericContainer<>(DockerImageName.parse("wiremock/wiremock:2.31.0"))
                .withExposedPorts(8080);
        wiremockContainer.start();

        // Set up WireMock configuration
        WireMock.configureFor(wiremockContainer.getHost(), wiremockContainer.getFirstMappedPort());

        // Create stub mappings
        stubFor(get(urlPathMatching("/people/(\\S+)"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "\t\"name\": \"Luke Skywalker\",\n" +
                                "\t\"height\": \"172\",\n" +
                                "\t\"mass\": \"77\",\n" +
                                "\t\"hair_color\": \"blond\",\n" +
                                "\t\"skin_color\": \"fair\",\n" +
                                "\t\"eye_color\": \"blue\",\n" +
                                "\t\"birth_year\": \"19BBY\",\n" +
                                "\t\"gender\": \"male\",\n" +
                                "\t\"homeworld\": \"https://swapi.dev/api/planets/1/\",\n" +
                                "\t\"films\": [\n" +
                                "\t\t\"https://swapi.dev/api/films/2/\",\n" +
                                "\t\t\"https://swapi.dev/api/films/6/\",\n" +
                                "\t\t\"https://swapi.dev/api/films/3/\",\n" +
                                "\t\t\"https://swapi.dev/api/films/1/\",\n" +
                                "\t\t\"https://swapi.dev/api/films/7/\"\n" +
                                "\t],\n" +
                                "\t\"species\": [\n" +
                                "\t\t\"https://swapi.dev/api/species/1/\"\n" +
                                "\t],\n" +
                                "\t\"vehicles\": [\n" +
                                "\t\t\"https://swapi.dev/api/vehicles/14/\",\n" +
                                "\t\t\"https://swapi.dev/api/vehicles/30/\"\n" +
                                "\t],\n" +
                                "\t\"starships\": [\n" +
                                "\t\t\"https://swapi.dev/api/starships/12/\",\n" +
                                "\t\t\"https://swapi.dev/api/starships/22/\"\n" +
                                "\t],\n" +
                                "\t\"created\": \"2014-12-09T13:50:51.644000Z\",\n" +
                                "\t\"edited\": \"2014-12-20T21:17:56.891000Z\",\n" +
                                "\t\"url\": \"https://swapi.dev/api/people/1/\"\n" +
                                "}")));
    }

    @AfterAll
    public static void tearDown() {
        wiremockContainer.stop();
    }

    @Test
    void retornaPeople() {
        var mockEsperado = this.generate();
        var uuidGerado = UUID.randomUUID().toString();
        ResponseEntity<SwapiPeopleDto> myEntity = new ResponseEntity<>(mockEsperado, HttpStatus.OK);
//        Mockito.when(this.gateway.people(Mockito.anyString())).thenReturn(myEntity);
//        Mockito.when(this.usecase.retornaPeople(Mockito.anyString())).thenReturn(mockEsperado);
        var resultado = this.usecase.retornaPeople(uuidGerado);

        // Primeiro teste é se meu resultado não é nulo
        Assertions.assertNotNull(resultado);

        // Segundo teste é se minha classe de gateway foi chamada pelo menos uma vez.
//        Mockito.verify(this.gateway, Mockito.times(1)).people(uuidGerado);

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