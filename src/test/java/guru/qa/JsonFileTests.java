package guru.qa;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.qa.model.CharacterProfile;
import guru.qa.model.Cyberware;
import guru.qa.model.Skill;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonFileTests {

    private ClassLoader cl = JsonFileTests.class.getClassLoader();

    @Test
    void readJsonTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try (Reader reader = new InputStreamReader(cl.getResourceAsStream("sample.json"), StandardCharsets.UTF_8)) {
            CharacterProfile profile = mapper.readValue(reader, CharacterProfile.class);

            assertEquals("Рэйвен Кс", profile.getName());
            assertEquals("Глитч", profile.getAlias());
            assertEquals(29, profile.getAge());
            assertTrue(profile.isWanted());
            assertEquals(150000, profile.getBounty());

            List<Cyberware> cyberwareList = profile.getCyberware();
            assertEquals(3, cyberwareList.size());
            assertEquals("Нейроимплант GhostNet", cyberwareList.get(0).getName());

            List<Skill> skills = profile.getSkills();
            assertEquals(3, skills.size());
            assertEquals("Взлом банковских систем", skills.get(0).getName());
            assertEquals(5, skills.get(0).getLevel());
        }
    }
}