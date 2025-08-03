package glocale.part;

import java.util.Map;

import glocale.Glocale;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public final class LookupPart implements Part {
    public final String key;

    @Override
    public String render(Glocale gl, Map<String, String> arguments) {
        return gl.render(this.key, arguments);
    }

    @Override
    public Type type() {
        return Type.LOOKUP;
    }

}
