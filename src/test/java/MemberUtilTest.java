import com.family.tree.enums.Gender;
import com.family.tree.model.Member;
import com.family.tree.util.MemberUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.Suite;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static com.family.tree.enums.Gender.FEMALE;
import static com.family.tree.enums.Gender.MALE;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Suite
@ExtendWith(MockitoExtension.class)
public class MemberUtilTest {
    Member root_KingArthur = new Member("King Arthur", MALE, Optional.of(Map.of("Queen Margret", FEMALE)));
    Member James = new Member("James Arthur", MALE, Optional.of(Map.of("Jenny", FEMALE)));
    Member Jin = new Member("Jin Arthur", FEMALE, Optional.of(Map.of("Manny", MALE)));
    LinkedHashMap<Map<String, Gender>, Member> lookup = new LinkedHashMap<>();

    @BeforeEach
    public void setup() {
        lookup.put((Map.of("King Arthur", MALE)), root_KingArthur);
        root_KingArthur.addChild(Jin);
        root_KingArthur.addChild(James);
    }

    @Test
    public void shouldCheckIfMemberIsBloodLine() {
        var res = MemberUtil.isBloodline("King Arthur", lookup);
        assertTrue(res);
    }

    @Test
    public void shouldCheckIfRelativeIsMatch() {
        var res = MemberUtil.getRelativeMatch(Gender.FEMALE, lookup);
        assertNotNull(res);
    }
}
