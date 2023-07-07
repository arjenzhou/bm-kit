package com.arjenzhou.kit.fsm;

import com.arjenzhou.kit.base.CollectionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Mealy machine
 *
 * @param <S> State Class
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/7
 */
public class MealyMachine<S> {
    /**
     * cannot be initialized by constructor
     */
    private MealyMachine() {
    }

    /**
     * how state transition performed
     */
    private Map<S, List<MealyTransition<S>>> transitionMap;

    /**
     * try to transfer state by input
     *
     * @param currentState current state
     * @param input        to trigger state transfer
     * @return state after triggered
     */
    S trigger(S currentState, MealyInput input) {
        List<MealyTransition<S>> transitions = transitionMap.get(currentState);
        if (CollectionUtil.isEmpty(transitions)) {
            throw new MealyException(currentState.getClass() + " has no transitions.");
        }
        // filter transition
        List<MealyTransition<S>> legalTransitions = transitions.stream()
                .filter(t -> t.canTransfer(input))
                .toList();
        if (CollectionUtil.nullOrHasMultipleItem(legalTransitions)) {
            throw new MealyException(currentState.getClass() + " can not transfer to any state by such input: " + input.toString());
        }
        // try transfer
        MealyTransition<S> transition = legalTransitions.get(0);
        return transition.action(input) ? transition.next() : currentState;
    }

    /**
     * FSM Builder
     *
     * @param <E> Event Class
     * @param <S> State Class
     */
    public static class Builder<E, S> {
        /**
         * follow FSM local param
         */
        private final Map<S, List<MealyTransition<S>>> transitionMap = new HashMap<>();

        /**
         * add one transition
         *
         * @param from        current state
         * @param on          the event to trigger
         * @param to          state after triggered
         * @param canTransfer function to check can transfer or not
         * @param action      function to perform transfer
         * @return state after triggered
         */
        public Builder<E, S> addTransition(S from, E on, S to, BiFunction<E, MealyInput, Boolean> canTransfer, Function<MealyInput, Boolean> action) {
            List<MealyTransition<S>> transitions = transitionMap.computeIfAbsent(from, s -> new ArrayList<>());
            transitions.add(new MealyTransition<>() {
                @Override
                public S next() {
                    return to;
                }

                @Override
                public boolean canTransfer(MealyInput input) {
                    return canTransfer.apply(on, input);
                }

                @Override
                public boolean action(MealyInput input) {
                    return action.apply(input);
                }
            });
            return this;
        }

        /**
         * @return FSM instance
         */
        public MealyMachine<S> build() {
            MealyMachine<S> machine = new MealyMachine<>();
            machine.transitionMap = transitionMap;
            return machine;
        }
    }
}
