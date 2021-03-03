import com.family.tree.enums.Gender;
import com.family.tree.enums.Message;
import com.family.tree.model.Member;
import com.family.tree.service.MemberService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MemberServiceTest {
    @Mock
    Member member;
    LinkedHashMap<Map<String,Gender>, Member> lookup = new LinkedHashMap<>();

    @Before
    public void setup()
    {
        lookup.put((Map.of("Marcheline",Gender.Female)),member);
        lookup.put((Map.of("James",Gender.Male)),member);
    }

    @Test
    public void shouldAddChildElement(){
        MemberService service = new MemberService(lookup);
        var output = service.addChild("Marcheline","Anjelina","Female");
        Assert.assertEquals(Message.CHILD_ADDED,output);
    }

    @Test
    public void shouldReturnMessageWhenMemberNotFound(){
        MemberService service = new MemberService(lookup);
        var output = service.addChild("Hira","Anjelina","Female");
        Assert.assertEquals(Message.PERSON_NOT_FOUND,output);
    }

    @Test
    public void shouldReturnMessageWhenMotherNotFound(){
        MemberService service = new MemberService(lookup);
        var output = service.addChild("James","Anjelina","Female");
        Assert.assertEquals(Message.CHILD_ADDITION_FAILED,output);
    }

    @Test
    public void shouldReturnMessageForRelationshipWhenPersonNotFound(){
        MemberService service = new MemberService(lookup);
        var output = service.getRelationship("Herus","Siblings");
        Assert.assertEquals(Message.PERSON_NOT_FOUND,output);
    }
    @Ignore
    public void shouldReturnMessageWhenRelationshipNotFound(){
        when(member.getParent()).thenReturn(new Member("Hi",Gender.Female, Optional.empty()));
        Member parent = member.getParent();
        when(parent.getChildren()).thenReturn(List.of(new Member("Bye",Gender.Female, Optional.empty())));
        MemberService service = new MemberService(lookup);
        var output = service.getRelationship("James","Siblings");
        Assert.assertEquals(Message.CHILD_ADDITION_FAILED,output);
    }
    @Ignore
    public void shouldReturnArrayWhenRelationshipFound(){
        MemberService service = new MemberService(lookup);
        var output = service.getRelationship("James","Siblings");
        Assert.assertEquals(Message.CHILD_ADDITION_FAILED,output);
    }
}
