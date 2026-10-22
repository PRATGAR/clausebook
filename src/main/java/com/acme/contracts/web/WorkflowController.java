package com.acme.contracts.web;

import com.acme.contracts.service.WorkflowService;
import java.io.IOException;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workflows/{id}")
public class WorkflowController {

    private final WorkflowService workflows;

    public WorkflowController(WorkflowService workflows) {
        this.workflows = workflows;
    }

    @PostMapping(value = "/checkpoint", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Map<String, Integer> checkpoint(@PathVariable long id, @RequestBody byte[] state) {
        return Map.of("storedBytes", workflows.checkpoint(id, state));
    }

    @PostMapping("/resume")
    public Map<String, Object> resume(@PathVariable long id) throws IOException, ClassNotFoundException {
        return workflows.resume(id);
    }

    @PostMapping("/restore-draft")
    public Map<String, Object> restoreDraft(@PathVariable long id) throws IOException, ClassNotFoundException {
        return workflows.restoreDraft(id);
    }
}
