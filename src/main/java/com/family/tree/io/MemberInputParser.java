package com.family.tree.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class MemberInputParser {

    public List<String[]> parse(String location) throws IOException {
        Path path = Paths.get(location);
        Stream<String> actionsStream = Files.lines(path);
        List<String> collectedStrings = actionsStream.flatMap(line -> Arrays.stream(line.split("/n"))).collect(toList());
        return collectedStrings
                .stream().map(action -> action.split(" ")).collect(toList());
    }
}
