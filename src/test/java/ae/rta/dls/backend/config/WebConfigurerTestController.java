package ae.rta.dls.backend.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebConfigurerTestController {

    @GetMapping("/api/test-cors")
    public void testCorsOnApiPath() {
        // Do nothing
    }

    @GetMapping("/test/test-cors")
    public void testCorsOnOtherPath() {
        // Do nothing
    }
}
