package eseo.fr.robineau.backend.service.pokemon;

import java.util.Map;

public class Pokemon {
    private String name;
    private Map<String, Object> sprites;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Map<String, Object> getSprites() { return sprites; }
    public void setSprites(Map<String, Object> sprites) { this.sprites = sprites; }

    public String getImageUrl() {
        return (String) sprites.get("front_default");
    }
}