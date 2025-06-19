package eseo.fr.robineau.backend.controller;

import eseo.fr.robineau.backend.service.pokemon.PokeApiService;
import eseo.fr.robineau.backend.service.pokemon.Pokemon;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PokeApiControllerTest {

    @Mock
    private PokeApiService pokeApiService;

    @InjectMocks
    private PokeApiController pokeApiController;

    @Test
    void getPokemon_shouldReturnPokemonData() {
        Pokemon expectedPokemon = new Pokemon();
        expectedPokemon.setName("pikachu");

        when(pokeApiService.getPokemonData("pikachu")).thenReturn(expectedPokemon);

        Pokemon result = pokeApiController.getPokemon("pikachu");

        assertNotNull(result);
        assertEquals("pikachu", result.getName());
        verify(pokeApiService).getPokemonData("pikachu");
    }
}