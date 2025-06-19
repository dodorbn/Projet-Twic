package eseo.fr.robineau.backend.service.pokemon;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PokeApiService {
    private final RestTemplate restTemplate;

    public PokeApiService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Pokemon getPokemonData(String name) {
        String apiUrl = "https://pokeapi.co/api/v2/pokemon/" + name;
        return restTemplate.getForObject(apiUrl, Pokemon.class);
    }
}
