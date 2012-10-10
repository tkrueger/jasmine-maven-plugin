package com.github.searls.jasmine.format;

import java.util.List;

public class FormatsCssTags {

  public String format(List<String> otherDependencies) {
    StringBuilder sb = new StringBuilder();
    for (String location : otherDependencies) {
      sb.append("<link rel=\"stylesheet\" href=\"").append(location).append("\"></link>").append("\n");
    }
    return sb.toString();
  }

}