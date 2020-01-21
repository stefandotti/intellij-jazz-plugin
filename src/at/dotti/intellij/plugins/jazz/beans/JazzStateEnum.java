package at.dotti.intellij.plugins.jazz.beans;

public enum JazzStateEnum {

    ADD, DELETE, MOVE, CONTENT_CHANGE, PROPERTY_CHANGE;

    public static JazzStateEnum valueOf(JazzState state) {
        if (state.isAdd()) {
            return ADD;
        } else if (state.isDelete()) {
            return DELETE;
        } else if (state.isMove()) {
            return MOVE;
        } else if (state.isContentChange()) {
            return CONTENT_CHANGE;
        } else if (state.isPropertyChange()) {
            return PROPERTY_CHANGE;
        } else {
            throw new IllegalArgumentException("jazz state unknown");
        }
    }

}
