package cc.kertaskerja.kepegawaian;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@Import(TestContainersConfiguration.class)
@SpringBootTest()
@ActiveProfiles("test")
class KepegawaianApplicationTests {

	@Test
	void contextLoads() {
	}

}
