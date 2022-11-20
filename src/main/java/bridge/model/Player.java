package bridge.model;

import bridge.view.InputView;

public class Player {
    public int inputBridgeSize() {
        return InputView.readBridgeSize();
    }

    public String inputMove() {
        return InputView.readMoving();
    }
}
