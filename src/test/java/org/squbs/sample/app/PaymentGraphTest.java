package org.squbs.sample.app;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PaymentGraphTest {

    private static ActorSystem system;
    private static ActorRef streamActor;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
        streamActor = system.actorOf(Props.create(PaymentGraphActor.class), "PaymentGraph");
    }

    @AfterClass
    public static void teardown() {
        JavaTestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void pingStreamActor() {
        new JavaTestKit(system) {{
            streamActor.tell("ping", getRef());
            int sum = expectMsgClass(Integer.class);
            assertEquals(340900, sum);
        }};
    }
}