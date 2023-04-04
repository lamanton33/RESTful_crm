package server.api.Password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/password")
public class PasswordController {
    private static String password;

    private static final Logger LOG = Logger.getLogger(SpringApplication.class.getName());
    @Autowired
    public PasswordController() {
    }

    /**
     * Method to generate Admin password and print it in console
     */
    public static void passwordGenerator(){
        if(password == null){
            SecureRandom random = new SecureRandom();
            byte[] bytes = new byte[16];
            random.nextBytes(bytes);
            password = Base64.getEncoder().encodeToString(bytes);
            System.out.println("Admin password " + password);
        }


    }
}
