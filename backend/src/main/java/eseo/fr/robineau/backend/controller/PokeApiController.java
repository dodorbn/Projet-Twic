package eseo.fr.robineau.backend.controller;

import eseo.fr.robineau.backend.service.pokemon.PokeApiService;
import org.springframework.web.bind.annotation.*;
import eseo.fr.robineau.backend.service.pokemon.Pokemon;

@RestController
@RequestMapping("/external")
@CrossOrigin(origins = "http://localhost:8081")
public class PokeApiController {
    private final PokeApiService pokeApiService;

    public PokeApiController(PokeApiService pokeApiService) {
        this.pokeApiService = pokeApiService;
    }

    @GetMapping("/pokemon/{name}")
    public Pokemon getPokemon(@PathVariable String name) {
        return pokeApiService.getPokemonData(name);
    }
}