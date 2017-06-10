package org.squbs.sample.app;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class PaymentActorTest {

    private static final long creator = 200L;
    private static long id = 1;

    private static ActorSystem system;
    private static ActorRef paymentActor;


    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
        paymentActor = system.actorOf(Props.create(PaymentActor.class), "PaymentActor");

    }

    @AfterClass
    public static void teardown() {
        JavaTestKit.shutdownActorSystem(system);
        system = null;
    }

  /*  @Test
    public void pingPaymentActor() {
        new JavaTestKit(system) {{
            paymentActor.tell(new PaymentRequest(new RecordId(creator, id++), 1001, 2001, 30000), getRef());
        }};

        // Temporarily have the sleep here so the test does not terminate prematurely.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/
    @Test
    public void lowRiskPayment() {
        new JavaTestKit(system) {{
            paymentActor.tell(new PaymentRequest(new RecordId(creator, id++), 1001, 2001, 1000), getRef());
            PaymentResponse response = expectMsgClass(PaymentResponse.class);
            assertEquals(PaymentStatus.APPROVED, response.status);
        }};
    }


}