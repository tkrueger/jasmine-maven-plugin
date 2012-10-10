package com.github.searls.jasmine.runner;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.antlr.stringtemplate.StringTemplate;

public class DefaultSpecRunnerHtmlGenerator extends AbstractSpecRunnerHtmlGenerator implements SpecRunnerHtmlGenerator {

  public static final String DEFAULT_RUNNER_HTML_TEMPLATE_FILE = "/jasmine-templates/SpecRunner.htmltemplate";

  protected DefaultSpecRunnerHtmlGenerator(HtmlGeneratorConfiguration configuration) {
    super(configuration);
  }

  public String generate() {
    try {
      return generateHtml(getConfiguration().getAllScripts(), getConfiguration().getAllCss());
    } catch (IOException e) {
      throw new RuntimeException("Failed to load files for dependencies, sources, or a custom runner", e);
    }
  }

  public String generateWitRelativePaths() {
    try {
      Set<String> allScriptsRelativePath = getConfiguration().getAllScriptsRelativePath();
      Set<String> allCssRelativePath = getConfiguration().getAllCssRelativePath();
      return generateHtml(allScriptsRelativePath, allCssRelativePath);
    } catch (IOException e) {
      throw new RuntimeException("Failed to load files for dependencies, sources, or a custom runner", e);
    }
  }

  private String generateHtml(Set<String> allScriptsRelativePath, Set<String> allCssRelativePath) throws IOException {
    StringTemplate template = resolveHtmlTemplate();
    includeJavaScriptDependencies(asList(JASMINE_JS, JASMINE_HTML_JS), template);
    applyCssToTemplate(asList(JASMINE_CSS), template);
    applyCssTagsToTemplate(setToList(allCssRelativePath), template);
    applyScriptTagsToTemplate(SOURCES_TEMPLATE_ATTR_NAME, allScriptsRelativePath, template);
    template.setAttribute(REPORTER_ATTR_NAME, getConfiguration().getReporterType().name());
    setEncoding(getConfiguration(), template);

    return template.toString();
  }

  private <T> List<T> setToList(Set<T> from) {
    List<T> list = new ArrayList<T>();
    list.addAll(from);
    return list;
  }

  @Override
  protected String getDefaultHtmlTemplatePath() {
    return DEFAULT_RUNNER_HTML_TEMPLATE_FILE;
  }


}
