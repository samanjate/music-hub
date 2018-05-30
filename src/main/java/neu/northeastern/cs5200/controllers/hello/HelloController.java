package neu.northeastern.cs5200.controllers.hello;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@Autowired
	HelloRepository helloRepository;

	@RequestMapping(value = "/api/hello/string", method = RequestMethod.GET)
	public String sayHello() {
		return "Hello Sree Keerthi Matta from Server";
	}

	@RequestMapping(value = "/api/hello/object", method = RequestMethod.GET)
	public HelloObject sayHelloObject() {
		return new HelloObject("Hello Sree Keerthi Matta from Server");
	}

	@RequestMapping("/api/hello/insert")
	public HelloObject insertHelloObject() {
		HelloObject obj = new HelloObject("Hello Jose Sree Keerthi Matta!");
		helloRepository.save(obj);
		return obj;
	}

	@RequestMapping("/api/hello/insert/{msg}")
	public HelloObject insertMessage(@PathVariable("msg") String message) {
	 HelloObject obj = new HelloObject(message);
	 helloRepository.save(obj);
	 return obj;
	}
	
	@RequestMapping("/api/hello/select/all")
	public List<HelloObject> selectAllHelloObjects() {
	 List<HelloObject> hellos =
	  (List<HelloObject>)helloRepository.findAll();
	 return hellos;
	}
}