package data;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import helper.Utils;

public class MyWebSocketActor extends AbstractActor {
    private final ActorRef out;

    public MyWebSocketActor(ActorRef out) {
        this.out = out;
    }

    public static Props props(ActorRef out) {
        return Props.create(MyWebSocketActor.class, out);
    }

    @Override
    public Receive createReceive() {
        
        Utils.debug("--Da nhan message--");
        return receiveBuilder().match(String.class, message ->
                out.tell("Da nhan message: " + message, self())
        ).build();

    }

    @Override
    public void postStop() throws Exception {
        Utils.debug("Client disconnect");
        super.postStop();
//        self().tell(PoisonPill.getInstance(), self());
    }
}
