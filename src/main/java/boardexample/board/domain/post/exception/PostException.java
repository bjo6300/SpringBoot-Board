package boardexample.board.domain.post.exception;

import boardexample.board.global.exception.BaseException;
import boardexample.board.global.exception.BaseExceptionType;

public class PostException extends BaseException {

    private BaseExceptionType baseExceptionType;


    public PostException(BaseExceptionType baseExceptionType) {
        this.baseExceptionType = baseExceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return this.baseExceptionType;
    }
}

