package org.correomqtt.business.scripting;

public class ScriptExecutionCancelledEvent extends BaseExecutionEvent {

    public ScriptExecutionCancelledEvent(ExecutionDTO executionDTO) {
        super(executionDTO);
    }
}
