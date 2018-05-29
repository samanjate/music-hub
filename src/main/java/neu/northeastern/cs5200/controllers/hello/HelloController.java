package neu.northeastern.cs5200.controllers.hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@RequestMapping(value = "/api/hello/string", method = RequestMethod.GET)
	public String sayHello() {
		return "Hello Sree Keerthi Matta from Server";
	}
	
	@RequestMapping(value = "/api/hello/object", method = RequestMethod.GET)
	public HelloObject sayHelloObject() {
		return new HelloObject("Hello Sree Keerthi Matta from Server");
	}

}