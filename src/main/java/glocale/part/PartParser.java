package glocale.part;

import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.ToString;

public class PartParser {
    private static final char ESCAPE = '^';

    public static Part[] parse(String str) {
        return parse(str.toCharArray());
    }

    public static Part[] parse(char[] str) {
        List<LexIntermediate> intermediates = new LinkedList<>();

        boolean escaped = false;
        Part.Type inProgressType = Part.Type.RAW;
        StringBuilder inProgress = new StringBuilder();

        for (int idx = 0; idx < str.length; idx++) {
            char c = str[idx];

            if (escaped) {
                // Do not attempt to lex if the char was escaped
                escaped = false;
                inProgress.append(c);
                continue;
            }

            if (c == ESCAPE) {
                // Next char is escaped
                escaped = true;
                continue;
            }

            switch (c) {
                case '{': { // Argument start
                    if (inProgressType != Part.Type.RAW) {
                        throw new IllegalStateException("Tried to begin a part with a non-raw part in progress: " + inProgressType);
                    }

                    // We need to flush the inProgress if it's not empty
                    if (inProgress.length() > 0) {
                        intermediates.add(new LexIntermediate(Part.Type.RAW, inProgress.toString()));
                        inProgress.setLength(0);
                    }

                    inProgressType = Part.Type.ARGUMENT;
                    continue;
                }

                case '}': { // Argument end
                    if (inProgressType != Part.Type.ARGUMENT) {
                        break;
                    }

                    intermediates.add(new LexIntermediate(Part.Type.ARGUMENT, inProgress.toString()));
                    inProgress.setLength(0);
                    inProgressType = Part.Type.RAW;
                    continue;
                }

                // ----------------

                case '[': { // Lookup start
                    if (inProgressType != Part.Type.RAW) {
                        throw new IllegalStateException("Tried to begin a part with a non-raw part in progress: " + inProgressType);
                    }

                    // We need to flush the inProgress if it's not empty
                    if (inProgress.length() > 0) {
                        intermediates.add(new LexIntermediate(Part.Type.RAW, inProgress.toString()));
                        inProgress.setLength(0);
                    }

                    inProgressType = Part.Type.LOOKUP;
                    continue;
                }

                case ']': { // Lookup end
                    if (inProgressType != Part.Type.LOOKUP) {
                        break;
                    }

                    intermediates.add(new LexIntermediate(Part.Type.LOOKUP, inProgress.toString()));
                    inProgress.setLength(0);
                    inProgressType = Part.Type.RAW;
                    continue;
                }

                // ----------------

                case '<': { // Component start
                    if (inProgressType != Part.Type.RAW) {
                        throw new IllegalStateException("Tried to begin a part with a non-raw part in progress: " + inProgressType);
                    }

                    // We need to flush the inProgress if it's not empty
                    if (inProgress.length() > 0) {
                        intermediates.add(new LexIntermediate(Part.Type.RAW, inProgress.toString()));
                        inProgress.setLength(0);
                    }

                    inProgressType = Part.Type.COMPONENT;
                    continue;
                }

                case '>': { // Component end
                    if (inProgressType != Part.Type.COMPONENT) {
                        break;
                    }

                    intermediates.add(new LexIntermediate(Part.Type.COMPONENT, inProgress.toString()));
                    inProgress.setLength(0);
                    inProgressType = Part.Type.RAW;
                    continue;
                }

                // ----------------

                default:
                    break;
            }

            // It's not a special char, append it and move on.
            inProgress.append(c);
            continue;
        }

        if (inProgress.length() > 0) {
            if (inProgressType != Part.Type.RAW) {
                throw new IllegalStateException("Reached end of string with a non-raw part in progress: " + inProgressType);
            }
            intermediates.add(new LexIntermediate(Part.Type.RAW, inProgress.toString()));
        }

        Part[] parts = new Part[intermediates.size()];
        for (int idx = 0; idx < parts.length; idx++) {
            LexIntermediate intermediate = intermediates.get(idx);

            switch (intermediate.type) {
                case ARGUMENT:
                    parts[idx] = new ArgumentPart(intermediate.content);
                    break;
                case COMPONENT:
                    parts[idx] = new ComponentPart(intermediate.content);
                    break;
                case LOOKUP:
                    parts[idx] = new LookupPart(intermediate.content);
                    break;
                case RAW:
                    parts[idx] = new RawPart(intermediate.content);
                    break;
            }
        }

        return parts;
    }

    @ToString
    @AllArgsConstructor
    private static class LexIntermediate {
        Part.Type type;
        String content;
    }

}
