package antigo;

public class PlayerMoveEvent extends Event {
    public PlayerMoveEvent(String what, CancelInterface cancel) {
        super(what, cancel, "PlayerMoveEvent");
    }
}
