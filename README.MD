# AzulJavaDownloader

This library is used to download the Azul Java binaries from the Azul CDN.

#### Example :

```java
 final AzulJavaDownloader downloader = new AzulJavaDownloader(System.out::println);
 final Path javas = Paths.get("javas"); // The directory where the Java versions will be downloaded.
 final AzulJavaBuildInfo buildInfoWindows = downloader.getBuildInfo(new RequestedJavaInfo("17", AzulJavaType.JDK, "windows", "x64", true)); // jdk 17 with javafx for Windows 64 bits
 final Path javaHomeWindows = downloader.downloadAndInstall(buildInfoWindows, javas);
 System.out.println(javaHomeWindows.toAbsolutePath()); // The path to the Java home directory
 
 final AzulJavaBuildInfo buildInfoLinux = downloader.getBuildInfo(new RequestedJavaInfo("17", AzulJavaType.JDK, "linux", "x64", true)); // jdk 17 with javafx for linux 64 bits
 final Path javaHomeLinux = downloader.downloadAndInstall(buildInfoLinux, javas);
 System.out.println(javaHomeLinux.toAbsolutePath()); // The path to the Java home directory
```