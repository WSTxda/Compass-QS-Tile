# Disable debug logging, except errors / warnings
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int d(...);
    public static int i(...);
}
-assumenosideeffects class java.io.PrintStream {
     public void println(%);
     public void println(**);
}
