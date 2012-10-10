package com.github.searls.jasmine.format;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.List;

import org.junit.Test;



public class FormatsCssTagsTest {

    private List<String> emptyList = Collections.emptyList();

    @Test
    public void givesEmptyStringForEmptyList() throws Exception {
        FormatsCssTags formatsCssTags = new FormatsCssTags();
        String text = formatsCssTags.format(emptyList);
        assertThat(text, equalTo(""));
    }

    @Test
    public void convertsSingleEntry() throws Exception {
        FormatsCssTags formatsCssTags = new FormatsCssTags();
        String text = formatsCssTags.format(asList("some/where/is/a/file.css"));
        assertThat(text, equalTo("<link rel=\"stylesheet\" href=\"some/where/is/a/file.css\"></link>\n"));
    }

    @Test
    public void convertsOneEntryToOneLine() throws Exception {
        FormatsCssTags formatsCssTags = new FormatsCssTags();
        String text = formatsCssTags.format(asList("some/where/is/a/file.css", "and/another.css"));
        assertThat(text, equalTo("<link rel=\"stylesheet\" href=\"some/where/is/a/file.css\"></link>\n<link rel=\"stylesheet\" href=\"and/another.css\"></link>\n"));
    }
}
