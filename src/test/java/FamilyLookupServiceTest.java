import com.family.tree.model.Member;
import com.family.tree.service.impl.FamilyLookupServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.Suite;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static com.family.tree.enums.Gender.FEMALE;
import static com.family.tree.enums.Gender.MALE;

@Suite
@ExtendWith(MockitoExtension.class)
public class FamilyLookupServiceTest {

    @Test
    public void shouldGenerateLookup()
    {
        Member root_KingArthur = new Member("King Arthur", MALE, Optional.of(Map.of("Queen Margret", FEMALE)));
        FamilyLookupServiceImpl familyLookupService = new FamilyLookupServiceImpl(root_KingArthur);
        var output = familyLookupService.getLookup();
        Assertions.assertNotNull(output);
        Assertions.assertTrue(output.size()>0);
        Assertions.assertEquals(2,output.size());
    }
}
