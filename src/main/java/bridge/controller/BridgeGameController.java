package bridge.controller;

import bridge.model.BridgeGame;
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
        boolean verified;
        do {
            try {
                verified = true;
                isMoving = askMove();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                verified = false;
            }
        } while (isMoving && bridgeGame.isOnGoing() || !verified);
        determinePlay(bridgeGame.isOnGoing());
    }

    private boolean askMove() {
        OutputView.askMove();
        boolean isMoving = bridgeGame.move();
        OutputView.printMap(bridgeGame.getPlayer().getWay(), bridgeGame.getBridge().getBridge());
        return isMoving;
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
