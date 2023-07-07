package com.arjenzhou.kit.fsm;

/**
 * Exception for FSM
 *
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/7
 */
public class MealyException extends RuntimeException {
    /**
     * @param message what cause exception occurred
     */
    public MealyException(String message) {
        super(message);
    }
}
