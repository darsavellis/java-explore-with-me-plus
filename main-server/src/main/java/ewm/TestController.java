package ewm;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping
    public String hell() {
        return "HELLO HOW ARE YOU";
    }
}
