package dao.factory.exception;

public class ModelPriceOutOfBoundsException extends RuntimeException {
    public ModelPriceOutOfBoundsException(String errorMessage) {
        super(errorMessage);
    }
}
