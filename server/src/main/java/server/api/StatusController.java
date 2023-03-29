package server.api;

import commons.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    /** Endpoint so that clients can "connect".
     * It's more of a confirmation that an XLII server does indeed exist at the specified location. */
    @GetMapping("")
    public Result<Object> connect() {
        return Result.SUCCESS;
    }
}
