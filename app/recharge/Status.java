package recharge;

public interface Status {
    int REQUIRE_PAYMENT = 1;
    int SUCCESS = 2;
    int FAILURE = 3;
    int CANCEL_PAYMENT = 4;
}
