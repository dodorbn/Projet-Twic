package eseo.fr.robineau.backend.api;

import eseo.fr.robineau.backend.service.ExternalApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/external")
public class ExternalApiController {
    private final ExternalApiService externalApiService;

    public ExternalApiController(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    @GetMapping
    public String getExternalData() {
        return externalApiService.getExternalData();
    }
}