package main.property;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import main.config.YamlPropertySourceFactory;
import main.model.SpellAlignment;
import main.model.SpellCategory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties
@PropertySource(value = "spells.yaml", factory = YamlPropertySourceFactory.class)
public class SpellsProperties {

    List<SpellDetails> spells;

    @Data
    public static class SpellDetails{
        private String code;
        private String name;
        private String image;
        private SpellCategory category;
        private SpellAlignment alignment;
        private int minLearned;
        private int power;
        private String description;
    }

//    @PostConstruct
//    public void test() {
//        System.out.println();
//    }
}
