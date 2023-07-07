package com.arjenzhou.kit.fsm;

/**
 * FSM Transition
 *
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/7
 */
public interface MealyTransition<S> {
    /**
     * @return next state
     */
    S next();

    /**
     * @param input to trigger state to transfer
     * @return can transfer by input or not
     */
    boolean canTransfer(MealyInput input);

    /**
     * @param input to trigger state to transfer
     * @return transition is success or not
     */
    boolean action(MealyInput input);
}
