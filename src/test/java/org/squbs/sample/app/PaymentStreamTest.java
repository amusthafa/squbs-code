package org.squbs.sample.app;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PaymentStreamTest {

    private static ActorSystem system;
    private static ActorRef streamActor;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
        streamActor = system.actorOf(Props.create(PaymentStreamActor.class), "PaymentStrem");
    }

    @AfterClass
    public static void teardown() {
        JavaTestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void pingPaymentActor() {
        new JavaTestKit(system) {{
            streamActor.tell("ping", getRef());
        }};

        // Just temporary, as we want to see the result.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void pingStreamActorSquare() {
        new JavaTestKit(system) {{
            streamActor.tell("ping", getRef());
            int sum = expectMsgClass(Integer.class);

            // Just temporary, as we want to see the result.
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assertEquals(220, sum);
        }};
    }
}