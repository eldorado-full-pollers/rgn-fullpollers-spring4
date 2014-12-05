package rgn.fullpollers.hello;

import com.jayway.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;

/**
 * Test for {@link PersonRepository}
 *
 * @author ragnarokkrr
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Value("${local.server.port}")
    int port;


    Person johnConnor;
    Person sarahConnor;
    Person kyleReese;

    @Before
    public void setUp() {
        johnConnor = new Person("John", "Connor");
        sarahConnor = new Person("Sarah", "Connor");
        kyleReese = new Person("Kyle", "Reese");

        personRepository.deleteAll();
        personRepository.save(Arrays.asList(johnConnor, sarahConnor, kyleReese));


        RestAssured.port = port;
    }

    @Test
    public void canFetchJohnConnor() {
        Long johnConnorId = johnConnor.getId();

        when().
                get("/people/{id}", johnConnorId).
                then().
                statusCode(HttpStatus.SC_OK).
                body("firstName", is("John")).
                body("lastName", is("Connor"));
                //TODO Resolver  NPE
                //body("id", is(johnConnorId))
        ;

    }

}
