package glocale.test.parsers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import glocale.Glocale;
import glocale.parser.GsonParser;

public class GsonTest {

    @Test
    public void simple() {
        String json = "{\r\n"
            + "    \"foo\": \"bar\",\r\n"
            + "    \"foo.baz\": \"biff\"\r\n"
            + "}";
        this.parse(json);
    }

    @Test
    public void complex() {
        String json = "{\r\n"
            + "    \"foo\": {\r\n"
            + "        \".\": \"bar\",\r\n"
            + "        \"baz\": \"biff\"\r\n"
            + "    }\r\n"
            + "}";
        this.parse(json);
    }

    private void parse(String json) {
        JsonObject obj = new Gson().fromJson(json, JsonObject.class);

        Glocale gl = new Glocale()
            .use(GsonParser.parse(obj));

        assertEquals("bar", gl.render("foo"));
        assertEquals("biff", gl.render("foo.baz"));
    }

}
