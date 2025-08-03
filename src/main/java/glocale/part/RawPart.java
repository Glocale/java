package glocale.part;

import java.util.Map;

import glocale.Glocale;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public final class RawPart implements Part {
    public final String value;

    @Override
    public String render(Glocale gl, Map<String, String> arguments) {
        return this.value;
    }

    @Override
    public Type type() {
        return Type.RAW;
    }

}
