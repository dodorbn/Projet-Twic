package eseo.fr.robineau.backend.service.pokemon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PokeApiServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    private PokeApiService pokeApiService;

    @BeforeEach
    void setUp() {
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
        pokeApiService = new PokeApiService(restTemplateBuilder);
    }

    @Test
    void getPokemonData() {
        String pokemonName = "pikachu";
        Pokemon expectedPokemon = new Pokemon();
        expectedPokemon.setName(pokemonName);

        when(restTemplate.getForObject(
                eq("https://pokeapi.co/api/v2/pokemon/" + pokemonName),
                eq(Pokemon.class)
        )).thenReturn(expectedPokemon);

        Pokemon result = pokeApiService.getPokemonData(pokemonName);

        assertEquals(expectedPokemon.getName(), result.getName());
    }
}