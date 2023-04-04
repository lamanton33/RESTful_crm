package server.api;

import commons.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import server.api.Password.PasswordController;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    private Map<Object, Consumer<Object>> listeners = new HashMap<>();
    /** Endpoint so that clients can "connect".
     * It's more of a confirmation that an XLII server does indeed exist at the specified location. */
    @GetMapping("")
    public Result<Object> connect() {
        PasswordController.passwordGenerator();
        return Result.SUCCESS;

    }

    /**
     * method that register for messages for long polling
     * @return
     */
    @GetMapping({"/polling"})
    public DeferredResult<ResponseEntity<Object>> registerForMessages(){
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT);
        var res = new DeferredResult<ResponseEntity<Object>>(5000L,noContent);

        var key = new Object();
        listeners.put(key, object->{



            res.setResult(ResponseEntity.ok(object));
        });

        res.onCompletion(()->{
            listeners.remove(key);
        });

        return res;
    }
}
