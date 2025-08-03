package glocale.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.rakurai.json.validation.JsonValidationException;
import glocale.Glocale;
import glocale.parser.RsonParser;

public class RsonTest {

    @Test
    public void simple() throws JsonValidationException, JsonParseException {
        String json = "{\r\n"
            + "    \"foo\": \"bar\",\r\n"
            + "    \"foo.baz\": \"biff\"\r\n"
            + "}";
        this.parse(json);
    }

    @Test
    public void complex() throws JsonValidationException, JsonParseException {
        String json = "{\r\n"
            + "    \"foo\": {\r\n"
            + "        \".\": \"bar\",\r\n"
            + "        \"baz\": \"biff\"\r\n"
            + "    }\r\n"
            + "}";
        this.parse(json);
    }

    private void parse(String json) throws JsonValidationException, JsonParseException {
        JsonObject obj = Rson.DEFAULT.fromJson(json, JsonObject.class);

        Glocale gl = new Glocale()
            .use(RsonParser.parse(obj));

        assertEquals("bar", gl.render("foo"));
        assertEquals("biff", gl.render("foo.baz"));
    }

}
