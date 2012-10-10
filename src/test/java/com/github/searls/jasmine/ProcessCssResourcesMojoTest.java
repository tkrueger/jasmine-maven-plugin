package com.github.searls.jasmine;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.logging.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.searls.jasmine.coffee.CompilesAllCoffeeInDirectory;
import com.github.searls.jasmine.io.DirectoryCopier;
import com.github.searls.jasmine.model.ScriptSearch;


@RunWith(PowerMockRunner.class)
@PrepareForTest(ProcessResourcesMojo.class)
public class ProcessCssResourcesMojoTest {

  private static final String SRC_DIR_NAME = "blarh";

  private static final String CSS_SRC_DIR_NAME = "css_blarch";

  @InjectMocks private final ProcessResourcesMojo subject = new ProcessResourcesMojo();

  @Mock private DirectoryCopier directoryCopier;
  @Mock private CompilesAllCoffeeInDirectory compilesAllCoffeeInDirectory;

  @Mock private File jasmineTargetDir;

  @Mock private File existingCssSourceDir;
  @Mock private File sourceDir;
  @Mock private File targetDir;
  @Mock private File cssTargetDir;
  @Mock private ScriptSearch sources;
  @Mock private ScriptSearch cssSources;

  @Mock private Log log;

  @Before
  public void killLogging() {
    subject.setLog(log);
  }

  @Before
  public void isolateSubject() throws Exception {
    subject.jasmineTargetDir = jasmineTargetDir;
    subject.srcDirectoryName = SRC_DIR_NAME;
    subject.cssDirectoryName = CSS_SRC_DIR_NAME;
    subject.sources = sources;
    subject.css = cssSources;

    whenNew(File.class).withArguments(jasmineTargetDir,SRC_DIR_NAME).thenReturn(targetDir);
    whenNew(File.class).withArguments(jasmineTargetDir,CSS_SRC_DIR_NAME).thenReturn(cssTargetDir);
  }

  @Before
  public void stubSources() {
    when(sources.getDirectory()).thenReturn(sourceDir);
    when(cssSources.getDirectory()).thenReturn(existingCssSourceDir);
  }

  @Before
  public void stubExistingCssSourceDir() {
      when(existingCssSourceDir.exists()).thenReturn(true);
  }

  @Test
  public void basicLoggingWhenCssSourceDirectoryNotGiven() throws IOException {
    subject.run();

    verify(log).info("Processing JavaScript Sources");
  }

  @Test
  public void basicLoggingWhenCssSourceDirectoryGiven() throws Exception {
    subject.cssSrcDir = existingCssSourceDir;
    subject.run();

    verify(log).info("Processing JavaScript Sources");
  }

  @Test
  public void whenCssSourceDirectoryGivenAndExistsCopy() throws Exception {
    subject.cssSrcDir = existingCssSourceDir;
    subject.run();

    verify(directoryCopier).copyDirectory(cssSources.getDirectory(), cssTargetDir);
  }


}
