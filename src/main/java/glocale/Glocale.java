package glocale;

import java.util.Collections;
import java.util.Map;

import glocale.part.Part;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public final class Glocale {
    private Map<String, Part[]> map = Collections.emptyMap();

    /**
     * Configures Glocale to use the provided map.
     * 
     * @return this instance, for chaining
     */
    public Glocale use(@NonNull Map<String, Part[]> map) {
        this.map = map;
        return this;
    }

    /* ---------------- */
    /*      Graph       */
    /* ---------------- */

    /**
     * Looks up the provided key and returns the resulting parts.
     * 
     * @return the parts
     */
    public Part[] lookup(@NonNull String key) {
        Part[] result = this.map.get(key);
        if (result == null) {
            throw new IllegalArgumentException("Unknown key: " + key + " (no value present)");
        }
        return result;
    }

    /**
     * Renders the provided key with no arguments.
     * 
     * @return the rendered string
     */
    public String render(@NonNull String key) {
        return this.render(key, Collections.emptyMap());
    }

    /**
     * Renders the provided key with the provided arguments.
     * 
     * @return the rendered string
     */
    public String render(@NonNull String key, @NonNull Map<String, String> arguments) {
        Part[] values = this.lookup(key);
        StringBuilder buf = new StringBuilder();
        for (Part value : values) {
            String rendered = value.render(this, arguments);
            buf.append(rendered);
        }
        return buf.toString();
    }

}
