package entities;

import java.time.Duration;

import javax.persistence.Entity;

@Entity
public class Subscribe {
	
	User user;
	Article article;
	Duration frequency;
	
}
