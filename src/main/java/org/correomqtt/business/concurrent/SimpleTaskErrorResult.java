package org.correomqtt.business.concurrent;

public class SimpleTaskErrorResult extends TaskErrorResultImpl<Void> {

    public SimpleTaskErrorResult(Throwable throwable) {
        super(null, throwable);
    }

    public Throwable getUnexpectedError() {
        return getUnexpectedErrorImpl();
    }
}
