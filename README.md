# glocale / java

The Java implementation of a "good" locale system for Java 8 and later. You can learn more about Glocale on the [wiki](https://github.com/Glocale/glocale/wiki).

Use [JitPack](https://jitpack.io/#glocale/java) to include this library in your project.

## Usage

You'll have to bring your own Json library, currently we support both Rson and Gson.

Example using Gson:

```java
String json = "{\r\n"
    + "    \"foo\": \"bar\",\r\n"
    + "    \"foo.baz\": \"biff\",\r\n"
    + "    \"hello\": \"Hello {name}!\"\r\n"
    + "}";

JsonObject obj = new Gson().fromJson(json, JsonObject.class);

Glocale gl = new Glocale()
    .use(GsonParser.parse(obj));

System.out.println(gl.render("foo"));     // "bar"
System.out.println(gl.render("foo.baz")); // "biff"

System.out.println(
    gl.render(
        "hello",
        Map.of("name", "world")
    )
); // "Hello world!"
```
