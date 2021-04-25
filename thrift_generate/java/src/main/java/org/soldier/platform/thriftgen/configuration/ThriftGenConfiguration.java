package org.soldier.platform.thriftgen.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.soldier.platform.thriftgen")
public class ThriftGenConfiguration {
	

}