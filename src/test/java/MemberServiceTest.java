import com.family.tree.enums.Gender;
import com.family.tree.enums.Message;
import com.family.tree.model.Member;
import com.family.tree.service.impl.ExtendedRelationServiceImpl;
import com.family.tree.service.impl.InLawServiceImpl;
import com.family.tree.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.Suite;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@Suite
@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    @Mock
    Member member;

    @Mock
    InLawServiceImpl inLawService;

    @Mock
    ExtendedRelationServiceImpl extendedRelationService;

    LinkedHashMap<Map<String, Gender>, Member> lookup = new LinkedHashMap<>();

    @BeforeEach
    public void setup() {
        lookup.put((Map.of("Marcheline", Gender.FEMALE)), member);
        lookup.put((Map.of("James", Gender.MALE)), member);
        lookup.put((Map.of("Jin", Gender.MALE)), member);
    }

    @Test
    public void shouldAddChildElement() {
        MemberServiceImpl service = new MemberServiceImpl(lookup, inLawService, extendedRelationService);
        var output = service.addChild("Marcheline", "Anjelina", "FEMALE");
        Assertions.assertEquals(Message.CHILD_ADDED, output);
    }

    @Test
    public void shouldReturnMessageWhenMemberNotFound() {
        MemberServiceImpl service = new MemberServiceImpl(lookup, inLawService, extendedRelationService);
        var output = service.addChild("Hira", "Anjelina", "Female");
        Assertions.assertEquals(Message.PERSON_NOT_FOUND, output);
    }

    @Test
    public void shouldReturnMessageWhenMotherNotFound() {
        MemberServiceImpl service = new MemberServiceImpl(lookup, inLawService, extendedRelationService);
        var output = service.addChild("James", "Anjelina", "Female");
        Assertions.assertEquals(Message.CHILD_ADDITION_FAILED, output);
    }

    @Test
    public void shouldReturnMessageForRelationshipWhenPersonNotFound() {
        MemberServiceImpl service = new MemberServiceImpl(lookup, inLawService, extendedRelationService);
        var output = service.getRelationship("Herus", "Siblings");
        Assertions.assertEquals(Message.PERSON_NOT_FOUND, output);
    }

    @Test
    public void shouldReturnMessageWhenRelationshipNotFound() {
        MemberServiceImpl service = new MemberServiceImpl(lookup, inLawService, extendedRelationService);
        var output = service.getRelationship("James", "Father");
        Assertions.assertEquals(Message.NONE, output);
    }

    @Test
    public void shouldReturnArrayWhenRelationshipFound() {
        List emptyArray = new ArrayList();
        when(member.getName()).thenReturn("Luna");
        MemberServiceImpl service = new MemberServiceImpl(lookup, inLawService, extendedRelationService);
        var output = service.getRelationship("James", "Siblings");
        Assertions.assertEquals(output, emptyArray);
    }
}
