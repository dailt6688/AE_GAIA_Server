package controllers.webSocket;

import akka.actor.ActorSystem;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import data.MyWebSocketActor;
import play.libs.F;
import play.libs.streams.ActorFlow;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

public class SocketController extends Controller {

    private final ActorSystem actorSystem;
    private final Materializer materializer;

    @Inject
    public SocketController(ActorSystem actorSystem, Materializer materializer) {
        this.actorSystem = actorSystem;
        this.materializer = materializer;
    }

    public Result test() {
        WebSocket ws = socketJson();
        if(ws!=null)
        {
//            Utils.debug("Da nhan lenh");
//            ws.apply(request());
            return ok("Create WebSocket Success -> "+ ws.toString());
        }
        return ok("Nhan request");
    }


    public WebSocket socket() {
        return WebSocket.Text.accept(request ->
                ActorFlow.actorRef(MyWebSocketActor::props,
                        actorSystem, materializer
                )
        );
    }

    public WebSocket socketCanReject() {
        return WebSocket.Text.acceptOrResult(request -> {
            if (session().get("user") != null) {
                return CompletableFuture.completedFuture(
                        F.Either.Right(ActorFlow.actorRef(MyWebSocketActor::props,
                                actorSystem, materializer)));
            } else {
                return CompletableFuture.completedFuture(F.Either.Left(forbidden()));
            }
        });
    }

    public WebSocket socketJson() {
        return WebSocket.Json.accept(request ->
                ActorFlow.actorRef(MyWebSocketActor::props,
                        actorSystem, materializer));
    }

    public WebSocket socketStream() {
        return WebSocket.Text.accept(request -> {
            // Log events to the console
            Sink<String, ?> in = Sink.foreach(System.out::println);

            // Send a single 'Hello!' message and then leave the socket open
            Source<String, ?> out = Source.single("Hello!").concat(Source.maybe());

            return Flow.fromSinkAndSource(in, out);
        });
    }

}
