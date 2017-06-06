package org.squbs.sample.app;

import akka.Done;
import akka.NotUsed;
import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import akka.stream.*;
import akka.stream.javadsl.*;

import java.util.concurrent.CompletionStage;

import static akka.pattern.PatternsCS.pipe;

public class PaymentGraphActor extends AbstractActor {

    final Materializer mat = ActorMaterializer.create(context());

    final Source<Integer, NotUsed> src = Source.range(1, 100);

    final Flow<Integer, Integer, NotUsed> filter = Flow.of(Integer.class).filter(i -> i % 2 == 0);

    final Flow<Integer, Integer, NotUsed> square = Flow.of(Integer.class).map(i -> i * i);

    final Sink<Integer, CompletionStage<Done>> sink = Sink.foreach(System.out::println);

    final Flow<Integer, Integer, NotUsed> print = Flow.of(Integer.class).map(i -> {
        System.out.println(i);
        return i;
    });

    final Sink<Integer, CompletionStage<Integer>> sum = Sink.reduce((a, b) -> a + b);


    public PaymentGraphActor() {
        receive(ReceiveBuilder
                .matchAny(o -> run())
                .build()
        );
    }

    private void run() {

        final RunnableGraph<CompletionStage<Integer>> graph =
                RunnableGraph.fromGraph(GraphDSL.create(sum, (builder, out) -> {
                    final UniformFanOutShape<Integer, Integer> bcast = builder.add(Broadcast.create(2));
                    final UniformFanInShape<Integer, Integer> merge = builder.add(Merge.create(2));
                    final FlowShape<Integer, Integer> filterS = builder.add(filter);
                    final FlowShape<Integer, Integer> squareS = builder.add(square);
                    final FlowShape<Integer, Integer> printS = builder.add(print);

                    final Outlet<Integer> source = builder.add(src).out();
                    builder.from(source).viaFanOut(bcast).via(filterS).viaFanIn(merge).via(printS).to(out);
                    builder.from(bcast).via(squareS).toFanIn(merge);

                    return ClosedShape.getInstance();
                }));
        final CompletionStage<Integer> resultFuture = graph.run(mat);
        pipe(resultFuture, context().dispatcher()).to(sender());
    }
}