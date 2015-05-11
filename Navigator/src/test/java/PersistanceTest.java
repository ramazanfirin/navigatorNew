import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.navigator.service.PersistanceService;
import com.navigator.service.ServiceProvider;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:///D:/calismalar/navigator/Navigator/src/main/webapp/WEB-INF/applicationContext.xml"})

public class PersistanceTest {

	ApplicationContext appContext;
	
	@Autowired
	public PersistanceService persistanceService;
	
	@Before
	public void control(){
		//appContext = new ClassPathXmlApplicationContext("file:///D:/calismalar/navigator/Navigator/src/main/webapp/WEB-INF/applicationContext.xml");
		System.out.println("calisiyor");
	}
	
	@Test
	public void checkBrach() throws Exception{
	
		System.out.println("calisiyor");
		
	}
}
