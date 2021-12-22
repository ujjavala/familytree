import com.family.tree.enums.Gender;
import com.family.tree.model.Member;
import com.family.tree.service.impl.InLawServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.Suite;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static com.family.tree.enums.Gender.FEMALE;
import static com.family.tree.enums.Gender.MALE;

@Suite
@ExtendWith(MockitoExtension.class)
public class InLawServiceTest {

    Member root_KingArthur = new Member("King Arthur", MALE, Optional.of(Map.of("Queen Margret", FEMALE)));
    Member James = new Member("James Arthur", MALE, Optional.of(Map.of("Jenny", FEMALE)));
    Member Jin = new Member("Jin", FEMALE, Optional.of(Map.of("Manny", MALE)));
    LinkedHashMap<Map<String, Gender>, Member> lookup = new LinkedHashMap<>();

    @BeforeEach
    public void setup() {
        lookup.put((Map.of("root_KingArthur", MALE)), root_KingArthur);
    }

    @Test
    public void shouldGetInLawRelationships()
    {
        root_KingArthur.addChild(Jin);
        root_KingArthur.addChild(James);
        InLawServiceImpl service = new InLawServiceImpl();
        var output = service.getInLawRelationShips(Jin,FEMALE,true,lookup);
        Assertions.assertNotNull(output);
        Assertions.assertFalse(output.size() > 0);
    }
}
