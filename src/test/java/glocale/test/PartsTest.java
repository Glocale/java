package glocale.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;

import glocale.part.ArgumentPart;
import glocale.part.ComponentPart;
import glocale.part.LookupPart;
import glocale.part.Part;
import glocale.part.PartParser;
import glocale.part.RawPart;

public class PartsTest {

    @Test
    public void parse() {
        Part[] result = PartParser.parse("{arg}[look]<com> raw");

        assertInstanceOf(ArgumentPart.class, result[0], "ArgumentPart");
        assertInstanceOf(LookupPart.class, result[1], "LookupPart");
        assertInstanceOf(ComponentPart.class, result[2], "ComponentPart");
        assertInstanceOf(RawPart.class, result[3], "RawPart");
    }

    @Test
    public void escaped() {
        Part[] result = PartParser.parse("^{this is escaped!}");

        assertEquals(1, result.length);
        assertInstanceOf(RawPart.class, result[0], "RawPart");
    }

}
