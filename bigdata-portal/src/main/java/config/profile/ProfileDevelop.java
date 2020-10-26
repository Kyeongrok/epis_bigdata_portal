package config.profile;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
//@Profile(value="${spring.profiles.active}")
//@PropertySource(value = {"classpath:egovframework/egovProps/${spring.profiles.active:dev}/globals.properties"})
@Profile(value="dev")
@PropertySource(value = {"classpath:egovframework/egovProps/dev/globals.properties"})
public class ProfileDevelop {

}
