package glocale.part;

import java.util.Map;

import glocale.Glocale;

public interface Part {

    public String render(Glocale gl, Map<String, String> arguments);

    public Type type();

    public static enum Type {
        ARGUMENT,
        COMPONENT,
        LOOKUP,
        RAW
    }

}
