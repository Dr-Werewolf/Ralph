package com.ralph.staticclass;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.JDA;

@Named
@Getter
public class StaticClass {
	
	@Autowired
	private ConfigurableApplicationContext context;
	
	@Setter private JDA jda;
	
}
