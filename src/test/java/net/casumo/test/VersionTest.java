package net.casumo.test;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;


public class VersionTest {

    // For checking travis works with osx + oraclejava8
    @Test
    public void javaVersionTest() {
        List<String> names = Arrays.asList("dimi", "fran");
        names.forEach(String::isEmpty);
    }

    @Test
    public void nothing() {
        assertTrue(true);
    }
}
