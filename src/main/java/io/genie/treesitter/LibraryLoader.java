package io.genie.treesitter;

import io.genie.treesitter.exception.TreeSitterException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;

public final class LibraryLoader {

  private static final String LIBRARY_FILE_NAME = "libjava-tree-sitter";

  private LibraryLoader() {}

  private static final class SystemResource {

    private final URL url;
    private final String name;

    private SystemResource(URL url, String name) {
      this.url = url;
      this.name = name;
    }

    private SystemResource(String name) {
      this(LibraryLoader.class.getClassLoader().getResource(name), name);
    }
  }

  public static void load() {
    String filename = String.format("%s.%s", LIBRARY_FILE_NAME, getExtension());
    SystemResource systemResource = new SystemResource(filename);
    if (systemResource.url == null) {
      throw new TreeSitterException("Native library resource not found: " + filename);
    }
    String libPath = getLibPath(systemResource);
    System.load(libPath);
  }

  private static String getLibPath(SystemResource systemResource) {
    String protocol = systemResource.url.getProtocol();
    switch (protocol) {
      case "file":
        return systemResource.url.getPath();
      case "resource":
      case "jar":
        File tmpdir = FileUtils.getTempDirectory();
        File tmpfile = new File(tmpdir, systemResource.name);
        tmpfile.deleteOnExit();
        try (InputStream input = systemResource.url.openStream();
            OutputStream output = new FileOutputStream(tmpfile, false)) {
          IOUtils.copy(input, output);
          return tmpfile.getPath();
        } catch (IOException cause) {
          throw new TreeSitterException(cause);
        }
      default:
        Exception cause = new UnsupportedOperationException("Unsupported protocol: " + protocol);
        throw new TreeSitterException(cause);
    }
  }

  private static String getExtension() {
    if (SystemUtils.IS_OS_LINUX) return "so";
    else if (SystemUtils.IS_OS_MAC) return "dylib";
    else if (SystemUtils.IS_OS_WINDOWS) return "dll";
    else
      throw new TreeSitterException(
          "The tree-sitter library was not compiled for this platform: "
              + SystemUtils.OS_NAME.toLowerCase());
  }
}
