package com.acme.contracts.support;

import com.acme.contracts.model.WorkflowState;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.springframework.stereotype.Component;

@Component
public class StateCodec {

    public byte[] encode(WorkflowState state) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(buffer)) {
            out.writeObject(state);
        }
        return buffer.toByteArray();
    }

    public WorkflowState restore(byte[] blob) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(blob))) {
            return (WorkflowState) in.readObject();
        }
    }

    public WorkflowState restoreDraft(byte[] blob) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(blob))) {
            in.setObjectInputFilter(WorkflowStateFilter.ALLOWED);
            return (WorkflowState) in.readObject();
        }
    }
}
