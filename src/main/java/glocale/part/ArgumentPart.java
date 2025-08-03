package glocale.part;

import java.util.Map;

import glocale.Glocale;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public final class ArgumentPart implements Part {
    public final String key;

    @Override
    public String render(Glocale gl, Map<String, String> arguments) {
        return arguments.get(this.key);
    }

    @Override
    public Type type() {
        return Type.ARGUMENT;
    }

}
