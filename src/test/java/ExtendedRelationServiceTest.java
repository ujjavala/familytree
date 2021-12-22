import com.family.tree.enums.Gender;
import com.family.tree.model.Member;
import com.family.tree.service.impl.ExtendedRelationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.Suite;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static com.family.tree.enums.Gender.FEMALE;
import static com.family.tree.enums.Gender.MALE;
import static org.mockito.Mockito.when;

@Suite
@ExtendWith(MockitoExtension.class)
public class ExtendedRelationServiceTest {

    @Mock
    Member member;

    Member root_KingArthur = new Member("King Arthur", MALE, Optional.of(Map.of("Queen Margret", FEMALE)));
    Member James = new Member("James", MALE, Optional.of(Map.of("Jenny", FEMALE)));
    Member Jin = new Member("Jin", FEMALE, Optional.of(Map.of("Manny", MALE)));
    Member Jiny = new Member("Jiny", FEMALE, Optional.of(Map.of("Banny", MALE)));
    Member Jino = new Member("Jino", FEMALE, Optional.of(Map.of("Ganny", MALE)));


    LinkedHashMap<Map<String, Gender>, Member> lookup = new LinkedHashMap<>();
    ExtendedRelationServiceImpl extendedRelationService = new ExtendedRelationServiceImpl();

    @BeforeEach
    public void setup() {
        lookup.put((Map.of("King Arthur", MALE)), root_KingArthur);
        lookup.put((Map.of("James", Gender.MALE)), James);
        lookup.put((Map.of("Jin", FEMALE)), Jin);
        lookup.put((Map.of("Jiny", FEMALE)), Jiny);
        lookup.put((Map.of("Jino", FEMALE)), Jino);
        root_KingArthur.addChild(James);
        root_KingArthur.addChild(Jin);
        Jin.addChild(Jiny);
        Jin.addChild(Jino);
    }

    @Test
    public void shouldGetExtendedRelationships() {
        var output = extendedRelationService.getExtendedRelationShips(Jin, MALE, FEMALE, false, lookup);
        Assertions.assertNotNull(output);
        Assertions.assertFalse(output.size() > 0);
    }

    @Test
    public void shouldGetMaternalRelationships() {
        var output = extendedRelationService.getMaternalExtendedRelationship("Jin", "Maternal-Aunt", Jin, lookup);
        Assertions.assertNotNull(output);
        Assertions.assertFalse(output.size() > 0);

    }

    @Test @Disabled
    public void shouldGetPaternalRelationships() {
        List<Member> children = new ArrayList<>(List.of(Jino,Jiny));
        when(member.getParent()).thenReturn(root_KingArthur);
        when(member.getParent().getChildren()).thenReturn(children);
        when(member.getName()).thenReturn("Jin");
        when(member.getGender()).thenReturn(FEMALE);
        when(member.getChildren()).thenReturn(children);

        var output = extendedRelationService.getPaternalExtendedRelationship("Jin", "Paternal-Aunt", member, lookup);
        Assertions.assertNotNull(output);
        Assertions.assertFalse(output.size() > 0);
    }
}
