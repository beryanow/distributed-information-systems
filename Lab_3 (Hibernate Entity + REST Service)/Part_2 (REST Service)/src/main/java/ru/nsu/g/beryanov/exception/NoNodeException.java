package ru.nsu.g.beryanov.exception;

public class NoNodeException extends NoComponentException {
    private static final String NO_NODE_MESSAGE = "Узел с id = %d не найден";

    public NoNodeException(Long nodeId) {
        super(String.format(NO_NODE_MESSAGE, nodeId));
    }
}
