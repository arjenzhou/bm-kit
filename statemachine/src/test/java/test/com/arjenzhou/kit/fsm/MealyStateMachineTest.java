package test.com.arjenzhou.kit.fsm;

import com.arjenzhou.kit.fsm.MealyInput;
import com.arjenzhou.kit.fsm.MealyMachine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/7
 */
public class MealyStateMachineTest {
    MealyMachine.Builder<AccountEvent, AccountState> builder;
    MealyInput mealyInput = new MealyInput() {
        @Override
        public String toString() {
            return super.toString();
        }
    };

    @BeforeEach
    public void init() {
        builder = new MealyMachine.Builder<AccountEvent, AccountState>()
                .addTransition(
                        AccountState.UN_REGISTERED,
                        AccountEvent.REGISTER,
                        AccountState.REGISTERED,
                        (event, input) -> AccountEvent.REGISTER.equals(event),
                        input -> true)
                .addTransition(
                        AccountState.REGISTERED,
                        AccountEvent.LOGIN,
                        AccountState.ONLINE,
                        (event, input) -> AccountEvent.LOGIN.equals(event),
                        input -> true)
                .addTransition(
                        AccountState.ONLINE,
                        AccountEvent.POST,
                        AccountState.ONLINE,
                        (event, input) -> AccountEvent.LOGOUT.equals(event),
                        input -> false
                )
                .addTransition(
                        AccountState.ONLINE,
                        AccountEvent.LOGOUT,
                        AccountState.OFFLINE,
                        (event, input) -> AccountEvent.LOGOUT.equals(event),
                        input -> true
                )
                .addTransition(
                        AccountState.OFFLINE,
                        AccountEvent.LOGIN,
                        AccountState.ONLINE,
                        (event, input) -> AccountEvent.LOGIN.equals(event),
                        input -> true
                );
    }

    @Test
    public void testStateMachine() {
        MealyMachine<AccountState> stateMachine = builder.build();
        assert AccountState.REGISTERED.equals(stateMachine.trigger(AccountState.UN_REGISTERED, mealyInput));
        assert AccountState.ONLINE.equals(stateMachine.trigger(AccountState.REGISTERED, mealyInput));
        assert AccountState.OFFLINE.equals(stateMachine.trigger(AccountState.ONLINE, mealyInput));
        assert AccountState.ONLINE.equals(stateMachine.trigger(AccountState.OFFLINE, mealyInput));
    }
}
