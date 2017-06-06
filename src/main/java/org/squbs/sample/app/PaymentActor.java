package org.squbs.sample.app;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.UnitPFBuilder;

public class PaymentActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(context().system(), this);

    private final long creatorId = 100;

    private long id = 1;

    public PaymentActor() {

        UnitPFBuilder<Object> rcvBuilder = new UnitPFBuilder<>();

        rcvBuilder.match(PaymentRequest.class, request -> {
                    log.info("Received payment request of ${} accounts {} => {}",
                            request.amount / 100D, request.payerAcct, request.payeeAcct);
                }
        );

        rcvBuilder.matchAny(o ->
                log.error("Message of type {} received is not understood", o.getClass().getName())
        );

        receive(rcvBuilder.build());
    }
}