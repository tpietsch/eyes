package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by tpietsch on 1/21/17.
 */
@Configuration
@ComponentScan({"database", "rest.v1", "security", "filters"})
@Import({ JsonObjectMapperProvider.class,EyesBeans.class,EyesSecurityConfiguration.class, JPAConfiguration.class})
public class EyesApplicationConfigRoot {

}
