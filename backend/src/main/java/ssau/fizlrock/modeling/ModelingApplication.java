package ssau.fizlrock.modeling;

import java.util.stream.LongStream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ssau.fizlrock.modeling.core.random.generator.Puasson2;
import ssau.fizlrock.modeling.core.random.generator.Puasson1;

@SpringBootApplication
public class ModelingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModelingApplication.class, args);
	}

}
