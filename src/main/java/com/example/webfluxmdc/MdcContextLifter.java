package com.example.webfluxmdc;

import brave.propagation.Propagation;
import org.reactivestreams.Subscription;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.CoreSubscriber;
import reactor.util.context.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper that copies the state of Reactor [Context] to MDC on the #onNext function.
 */
class MdcContextLifter<T> implements CoreSubscriber<T> {

    @Autowired
    private Propagation propagation;

    CoreSubscriber<T> coreSubscriber;

    public MdcContextLifter(CoreSubscriber<T> coreSubscriber) {
        this.coreSubscriber = coreSubscriber;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
//        ExtraFieldPropagation.set("principal", UUID.randomUUID().toString());
//        ExtraFieldPropagation.set("suraj", UUID.randomUUID().toString());
        coreSubscriber.onSubscribe(subscription);
    }

    @Override
    public void onNext(T obj) {
        copyToMdc(coreSubscriber.currentContext());
        coreSubscriber.onNext(obj);
    }

    @Override
    public void onError(Throwable t) {
        coreSubscriber.onError(t);
    }

    @Override
    public void onComplete() {
        coreSubscriber.onComplete();
    }

    @Override
    public Context currentContext() {
        return coreSubscriber.currentContext();
    }

    /**
     * Extension function for the Reactor [Context]. Copies the current context to the MDC, if context is empty clears the MDC.
     * State of the MDC after calling this method should be same as Reactor [Context] state.
     * One thread-local access only.
     */
    private void copyToMdc(Context context) {

        if (!context.isEmpty()) {
            Map<String, String> mdcMap = new HashMap<>();

            Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
            copyOfContextMap.forEach((p, k) -> mdcMap.put(p, k));

            if (context.getOrEmpty(LoggingFilterWebFilter.SURAJ_LOGGING_CONTEXT).isPresent()) {
                HashMap<String, Object> mdcMapObject
                        = (HashMap<String, Object>) context.get(LoggingFilterWebFilter.SURAJ_LOGGING_CONTEXT);
                if (mdcMapObject.containsKey(LoggingFilterWebFilter.SURAJ_SRE_LOGGING_CONTEXT)) {
                    HashMap<String, String> sreHashMap = (HashMap<String, String>) mdcMapObject.get(LoggingFilterWebFilter.SURAJ_SRE_LOGGING_CONTEXT);
                    sreHashMap.forEach((p, k) -> mdcMap.put(p, k));
                }

                if (mdcMapObject.containsKey(LoggingFilterWebFilter.SURAJ_CUSTOM_LOGGING_CONTEXT)) {
                    HashMap<Integer, String> customHashMap = (HashMap<Integer, String>) mdcMapObject.get(LoggingFilterWebFilter.SURAJ_CUSTOM_LOGGING_CONTEXT);
                    customHashMap.forEach((i, v) -> {
                        if (context.getOrEmpty(v).isPresent()) {
                            mdcMap.put(v, v.toString() + ": " + context.get(v));
                        }
                    });
                }

                MDC.setContextMap(mdcMap);
            }
        } else {
            MDC.clear();
        }
    }

}