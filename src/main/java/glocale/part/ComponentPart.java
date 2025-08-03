package glocale.part;

import java.util.Map;

import glocale.Glocale;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public final class ComponentPart implements Part {
    public final String name;

    @Override
    public String render(Glocale gl, Map<String, String> arguments) {
        throw new UnsupportedOperationException("Components can't be rendered as strings.");
    }

    @Override
    public Type type() {
        return Type.COMPONENT;
    }

}
