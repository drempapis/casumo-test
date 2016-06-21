package net.casumo.test;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public class VersionTest {

    // For checking travis works with osx + oraclejava8
    @Test
    public void javaVersionTest() {
        List<String> names = Arrays.asList("dimi", "fran");
        names.forEach(String::isEmpty);
    }
}
