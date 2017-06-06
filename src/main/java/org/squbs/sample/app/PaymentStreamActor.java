package org.squbs.sample.app;

import akka.NotUsed;
import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Source;
import akka.Done;

import java.util.concurrent.CompletionStage;

import akka.stream.*;
import akka.stream.javadsl.*;


import static akka.pattern.PatternsCS.pipe;

import akka.stream.javadsl.RunnableGraph;

public class PaymentStreamActor extends AbstractActor {

    final Materializer mat = ActorMaterializer.create(context());

    final Source<Integer, NotUsed> src = Source.range(1, 10);

    final Sink<Integer, CompletionStage<Done>> sink = Sink.foreach(System.out::println);
    // Add this line ^^^^^^^^^^


    final Flow<Integer, Integer, NotUsed> filter = Flow.of(Integer.class).filter(i -> i % 2 == 0);

    final Flow<Integer, Integer, NotUsed> square = Flow.of(Integer.class).map(i -> i * i);
    // Add this line ^^^^^^^^^

  //  final Sink<Integer, CompletionStage<Done>> sink = Sink.foreach(System.out::println);

    final Source<Integer, NotUsed> filteredSource = src.via(filter);

    final Flow<Integer, Integer, NotUsed> filterAndSquare = filter.via(square);

    // Use materialized value of flow
    final Sink<Integer, NotUsed> squareAndPrint = square.to(sink);

    // Choose materialized value of sink
    final Sink<Integer, CompletionStage<Done>> squareAndPrintMat = square.toMat(sink, Keep.right());

    final Flow<Integer, Integer, NotUsed> print = Flow.of(Integer.class).map(i -> {
        System.out.println(i);
        return i;
    });

    final Sink<Integer, CompletionStage<Integer>> sum = Sink.reduce((a, b) -> a + b);

    public PaymentStreamActor() {
        receive(ReceiveBuilder
                .matchAny(o -> run())
                .build()
        );
    }

  /*  private void run() {
        src.runForeach(System.out::println, mat);
    }*/

  /*  private void run() {
        final RunnableGraph<NotUsed> stream = src.to(sink);
        stream.run(mat);
    }*/

    private void run() {
        final RunnableGraph<CompletionStage<Integer>> stream =
                src.via(filter).via(square).via(print).toMat(sum, Keep.right());
        final CompletionStage<Integer> resultFuture =
                stream.run(mat);
        pipe(resultFuture, context().dispatcher()).to(sender());
    }


}
