package com.github.searls.jasmine;

import java.io.File;
import java.io.IOException;

import com.github.searls.jasmine.coffee.CompilesAllCoffeeInDirectory;
import com.github.searls.jasmine.io.DirectoryCopier;

/**
 * @goal resources
 * @phase process-resources
 */
public class ProcessResourcesMojo extends AbstractJasmineMojo {

  public static final String MISSING_DIR_WARNING =
    "JavaScript source folder was expected but was not found. " +
    "Set configuration property `jsSrcDir` to the directory containing your JavaScript sources. " +
    "Skipping jasmine:resources processing.";

  public static final CharSequence MISSING_CSS_DIR_WARNING = "Css source folder was given but not found. " +
      "Check configuration property `cssSrcDir`.";

  private final DirectoryCopier directoryCopier = new DirectoryCopier();
  private final CompilesAllCoffeeInDirectory compilesAllCoffeeInDirectory = new CompilesAllCoffeeInDirectory();


  @Override
  public void run() throws IOException {
    getLog().info("Processing JavaScript Sources");
    if (sources.getDirectory().exists()) {
      File destination = new File(jasmineTargetDir, srcDirectoryName);
      directoryCopier.copyDirectory(sources.getDirectory(), destination);
      compilesAllCoffeeInDirectory.compile(destination);
    } else {
      getLog().warn(MISSING_DIR_WARNING);
    }

    if (cssDirConfigured()) {
        copyCssFiles();
    }
  }

  private boolean cssDirConfigured() {
    return cssSrcDir != null;
  }

  private void copyCssFiles() throws IOException {
    getLog().info("Processing CSS Sources");
    if (css.getDirectory().exists()) {
      File destination = new File(jasmineTargetDir, cssDirectoryName);
      directoryCopier.copyDirectory(css.getDirectory(), destination);
    } else {
      getLog().warn(MISSING_CSS_DIR_WARNING);
    }
  }

}
