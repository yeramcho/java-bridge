package bridge.controller;

import bridge.model.Bridge;
import bridge.model.BridgeGame;
import bridge.model.Player;
import bridge.view.OutputView;

public class BridgeGameController {
    private final BridgeGame bridgeGame;

    public BridgeGameController(BridgeGame bridgeGame) {
        this.bridgeGame = bridgeGame;
    }

    public void run() {
        OutputView.start();
        boolean created;
        do {
            created = create();
        } while (!created);
        play();
    }

    private boolean create() {
        try {
            OutputView.askLength();
            bridgeGame.create();
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private void play() {
        boolean isMoving = true;
        do {
            OutputView.askMove();
            isMoving = bridgeGame.move();
            OutputView.printMap(bridgeGame.getPlayer().getWay(), bridgeGame.getBridge().getBridge());
        } while (isMoving && bridgeGame.isOnGoing());
        determinePlay(bridgeGame.isOnGoing());
    }

    private void determinePlay(boolean onGoing) {
        if (bridgeGame.isOnGoing()) {
            OutputView.askRestart();
            if (bridgeGame.retry()) {
                play();
                return;
            }
        }
        end(bridgeGame);
    }

    private void end(BridgeGame bridgeGame) {
        OutputView.printResult(bridgeGame);
    }
}
